package aep.SOSsego.repositories;

import aep.SOSsego.models.CitizenModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CitizenRepository extends JpaRepository<CitizenModel, Long> {
    boolean existsByCpf(String cpf);
    Optional<CitizenModel> findByEmail(String email);
}
