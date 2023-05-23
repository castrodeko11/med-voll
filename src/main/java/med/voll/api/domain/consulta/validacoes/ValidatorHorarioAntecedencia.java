package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.ValidacaoExcepton;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;

import java.time.Duration;
import java.time.LocalTime;

public class ValidatorHorarioAntecedencia {

    public void validar(DadosAgendamentoConsulta dadosAgendamentoConsulta) {
        var data = dadosAgendamentoConsulta.data();
        var now = LocalTime.now();
        var diferencaEmMinutos = Duration.between(now, data).toMinutes();

        if (diferencaEmMinutos < 30) {
            throw new ValidacaoExcepton("Horário de agendamento deve ser com no mínimo 30 minutos de antecedência");
        }

    }
}
