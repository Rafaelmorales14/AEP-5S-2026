package aep.SOSsego.repositories;

import aep.SOSsego.models.SolicitationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitationRepository extends JpaRepository<SolicitationModel, Long> {
}
