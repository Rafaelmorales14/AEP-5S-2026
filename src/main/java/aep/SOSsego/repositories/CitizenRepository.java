package aep.SOSsego.repositories;

import aep.SOSsego.models.CitizenModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitizenRepository extends JpaRepository<CitizenModel, Long> {
    boolean existsByCpf(String cpf);
}
