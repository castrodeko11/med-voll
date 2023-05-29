package med.voll.api.domain.consulta;

import med.voll.api.domain.ValidacaoExcepton;
import med.voll.api.domain.consulta.validacoes.ValidadorAgendamentosDeConsultas;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsultas {

    private final ConsultaRespository consultaRespository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;

    private final List<ValidadorAgendamentosDeConsultas> validadorAgendamentosDeConsultas;

    public AgendaDeConsultas(ConsultaRespository consultaRespository, MedicoRepository medicoRepository, PacienteRepository pacienteRepository, List<ValidadorAgendamentosDeConsultas> validadorAgendamentosDeConsultas) {
        this.consultaRespository = consultaRespository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
        this.validadorAgendamentosDeConsultas = validadorAgendamentosDeConsultas;
    }


    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados) {
        var paciente = pacienteRepository.findById(dados.idPaciente()).orElseThrow(
                () -> new ValidacaoExcepton("ID do paciente informado não existe")
        );

        if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) {
            throw new ValidacaoExcepton("ID do médico informado não existe");
        }

        validadorAgendamentosDeConsultas.forEach(validador -> validador.validar(dados));

        var medico = escolherMedico(dados);

        if (medico == null) {
            throw new ValidacaoExcepton("Não foi possível encontrar um médico para a especialidade informada");
        }

        var consulta = new Consulta(null, medico, paciente, dados.data());
        consultaRespository.save(consulta);

        return new DadosDetalhamentoConsulta(consulta);

    }

    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
        if (dados.idMedico() != null) {
            return medicoRepository.getReferenceById(dados.idMedico());
        }

        if (dados.especialidade() == null) {
            throw new ValidacaoExcepton("Especialidade é obrigatória quando o médico não for escolhido");
        }

        return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
    }

}
