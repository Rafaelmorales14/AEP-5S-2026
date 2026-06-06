package aep.SOSsego.repositories;

import aep.SOSsego.models.SolicitationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SolicitationRepository extends JpaRepository<SolicitationModel, Long> {
    Optional<SolicitationModel> findByProtocol(String protocol);

    @Query("""
        SELECT s.priority, COUNT(s)
        FROM SolicitationModel  s
        GROUP BY s.priority
""")
    List<Object[]> countByPriority();

    @Query("""
        SELECT s.category, COUNT(s)
        From SolicitationModel s
        GROUP BY s.category
""")
    List<Object[]> countByCategory();

    @Query("""
    SELECT s
    FROM SolicitationModel s
    WHERE s.currentlyStatus <> StatusSolicitationEnum.ENCERRADO
    ORDER BY s.priority DESC, s.dateSLA ASC
""")
    List<SolicitationModel> findActiveSolicitations();
}
