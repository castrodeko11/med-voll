package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.ValidacaoExcepton;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class ValidadorHorarioFuncionamentoClinica {

    public void validar(DadosAgendamentoConsulta dadosAgendamentoConsulta) {
        var data = dadosAgendamentoConsulta.data();

        var domingo = data.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var antesDaAbertura = data.toLocalTime().isBefore(LocalTime.of(7, 0));
        var depoisDoFechamento = data.toLocalTime().isAfter(LocalTime.of(18, 0));

        if (domingo || antesDaAbertura || depoisDoFechamento) {
            throw new ValidacaoExcepton("Horário de funcionamento da clínica é de segunda a sábado das 7:00 às 18:00");
        }


    }
}
