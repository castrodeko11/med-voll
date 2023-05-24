package med.voll.api.domain.consulta;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ConsultaRespository extends JpaRepository<Consulta, Long> {

    boolean existsByPacienteIdAndDataBetween(Long idPaciente, LocalDateTime dataInicio, LocalDateTime dataFim);

    boolean existsByMedicoIdAndData(Long aLong, LocalDateTime data);
}
