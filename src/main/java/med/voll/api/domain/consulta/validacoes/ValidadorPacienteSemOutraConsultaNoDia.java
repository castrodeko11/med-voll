package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.ValidacaoExcepton;
import med.voll.api.domain.consulta.ConsultaRespository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;

import java.time.LocalTime;

public class ValidadorPacienteSemOutraConsultaNoDia {

    private ConsultaRespository consultaRespository;

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
