package med.voll.api.domain.paciente;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    boolean findAtivoById(Long aLong);
//    Page<Medico> findAllByAtivoTrue(Pageable pageable);
}
