package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.ValidacaoExcepton;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.paciente.PacienteRepository;

public class ValidadorPacienteAtivo {

    private PacienteRepository pacienteRepository;

    public void validar(DadosAgendamentoConsulta dadosAgendamentoConsulta) {
        var pacienteIsActive = pacienteRepository.findAtivoById(dadosAgendamentoConsulta.idPaciente());

        if (!pacienteIsActive) {
            throw new ValidacaoExcepton("Paciente não está ativo para agenda consultas");
        }
    }
}
