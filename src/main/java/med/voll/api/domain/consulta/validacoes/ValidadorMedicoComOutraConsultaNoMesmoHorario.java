package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.ValidacaoExcepton;
import med.voll.api.domain.consulta.ConsultaRespository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;

public class ValidadorMedicoComOutraConsultaNoMesmoHorario {

    private ConsultaRespository consultaRespository;

    public void validar(DadosAgendamentoConsulta dadosAgendamentoConsulta) {
        var medicoPossuiOutraConsultaNoMesmoHorario = consultaRespository.
                existsByMedicoIdAndData(dadosAgendamentoConsulta.idMedico(), dadosAgendamentoConsulta.data());

        if (medicoPossuiOutraConsultaNoMesmoHorario) {
            throw new ValidacaoExcepton("Médico possui outra consulta no mesmo horário");
        }
    }
}
