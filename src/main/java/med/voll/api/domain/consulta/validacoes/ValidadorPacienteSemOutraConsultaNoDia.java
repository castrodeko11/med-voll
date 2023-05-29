package med.voll.api.domain.consulta.validacoes;

import lombok.RequiredArgsConstructor;
import med.voll.api.domain.ValidacaoExcepton;
import med.voll.api.domain.consulta.ConsultaRespository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class ValidadorPacienteSemOutraConsultaNoDia implements ValidadorAgendamentosDeConsultas  {

    private final ConsultaRespository consultaRespository;



    public void validar(DadosAgendamentoConsulta dadosAgendamentoConsulta) {
        var primeiroHorario = dadosAgendamentoConsulta.data().with(LocalTime.of(7, 0));
        var ultimoHorario = dadosAgendamentoConsulta.data().with(LocalTime.of(18, 0));
        var pacientePossuiOutraConsultaNoDia = consultaRespository.
                existsByPacienteIdAndDataBetween(dadosAgendamentoConsulta.idPaciente(), primeiroHorario, ultimoHorario);

        if (pacientePossuiOutraConsultaNoDia) {
            throw new ValidacaoExcepton("Paciente possui outra consulta no mesmo dia");
        }
    }
}
