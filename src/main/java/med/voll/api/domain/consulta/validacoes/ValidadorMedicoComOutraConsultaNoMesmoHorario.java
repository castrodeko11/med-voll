package med.voll.api.domain.consulta.validacoes;

import lombok.RequiredArgsConstructor;
import med.voll.api.domain.ValidacaoExcepton;
import med.voll.api.domain.consulta.ConsultaRespository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidadorMedicoComOutraConsultaNoMesmoHorario implements ValidadorAgendamentosDeConsultas {

    private final ConsultaRespository consultaRespository;

    public void validar(DadosAgendamentoConsulta dadosAgendamentoConsulta) {
        var medicoPossuiOutraConsultaNoMesmoHorario = consultaRespository.
                existsByMedicoIdAndData(dadosAgendamentoConsulta.idMedico(), dadosAgendamentoConsulta.data());

        if (medicoPossuiOutraConsultaNoMesmoHorario) {
            throw new ValidacaoExcepton("Médico possui outra consulta no mesmo horário");
        }
    }
}
