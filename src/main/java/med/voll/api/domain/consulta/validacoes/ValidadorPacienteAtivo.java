package med.voll.api.domain.consulta.validacoes;

import lombok.RequiredArgsConstructor;
import med.voll.api.domain.ValidacaoExcepton;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidadorPacienteAtivo implements ValidadorAgendamentosDeConsultas {

    private final PacienteRepository pacienteRepository;

    public void validar(DadosAgendamentoConsulta dadosAgendamentoConsulta) {
        var pacienteIsActive = pacienteRepository.findByIdAndAtivo(dadosAgendamentoConsulta.idPaciente());

        if (!pacienteIsActive) {
            throw new ValidacaoExcepton("Paciente não está ativo para agenda consultas");
        }
    }
}
