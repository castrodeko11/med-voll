package med.voll.api.domain.consulta.validacoes;

import lombok.RequiredArgsConstructor;
import med.voll.api.domain.ValidacaoExcepton;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.medico.MedicoRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidadorMedicoAtivo implements ValidadorAgendamentosDeConsultas {

    private final MedicoRepository medicoRepository;

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
