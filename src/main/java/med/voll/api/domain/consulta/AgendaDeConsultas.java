package med.voll.api.domain.consulta;

import med.voll.api.domain.ValidacaoExcepton;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AgendaDeConsultas {

    private ConsultaRespository consultaRespository;
    private MedicoRepository medicoRepository;
    private PacienteRepository pacienteRepository;

    public AgendaDeConsultas(ConsultaRespository consultaRespository, MedicoRepository medicoRepository, PacienteRepository pacienteRepository) {
        this.consultaRespository = consultaRespository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
    }


    public void agendar(DadosAgendamentoConsulta dados) throws Exception {
        var paciente = pacienteRepository.findById(dados.idPaciente()).orElseThrow(
                () -> new ValidacaoExcepton("ID do paciente informado não existe")
        );

        if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) {
            throw new ValidacaoExcepton("ID do médico informado não existe");
        }

        var medico = escolherMedico(dados);
        var consulta = new Consulta(null, medico, paciente, dados.data());

        consultaRespository.save(consulta);

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
