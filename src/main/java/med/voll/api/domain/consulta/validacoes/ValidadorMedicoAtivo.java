package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.ValidacaoExcepton;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.medico.MedicoRepository;

public class ValidadorMedicoAtivo {

    private MedicoRepository medicoRepository;

    public void validar(DadosAgendamentoConsulta dadosAgendamentoConsulta) {

        if (dadosAgendamentoConsulta.idMedico() == null) {
            return;
        }

        var medicoIsActive = medicoRepository.findAtivoById(dadosAgendamentoConsulta.idMedico());
        if (!medicoIsActive) {
            throw new ValidacaoExcepton("Médico não está ativo para agenda consultas");
        }

    }
}
