package aep.SOSsego.repositories;

import aep.SOSsego.models.StatusHistoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusHistoryRepository extends JpaRepository<StatusHistoryModel, Long> {
}
