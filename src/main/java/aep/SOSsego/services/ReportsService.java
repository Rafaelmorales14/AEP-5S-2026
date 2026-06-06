package aep.SOSsego.services;

import aep.SOSsego.enums.CategoryEnum;
import aep.SOSsego.enums.PriorityEnum;
import aep.SOSsego.models.SolicitationModel;
import aep.SOSsego.repositories.SolicitationRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportsService {

    private final SolicitationRepository repository;

    public ReportsService(SolicitationRepository repository) {
        this.repository = repository;
    }


    public Map<PriorityEnum, Long> reportByPriority() {

        List<Object[]> results = repository.countByPriority();

        Map<PriorityEnum, Long> report = new HashMap<>();

        for (Object[] row : results) {
            report.put(
                    (PriorityEnum) row[0],
                    (Long) row[1]
            );
        }

        return report;
    }

    public Map<CategoryEnum, Long> reportByCategory() {

        List<Object[]> results = repository.countByCategory();

        Map<CategoryEnum, Long> report = new HashMap<>();

        for (Object[] row : results) {
            report.put(
                    (CategoryEnum) row[0],
                    (Long) row[1]
            );
        }

        return report;
    }

    public List<SolicitationModel> findActiveSolicitations() {
        return repository.findActiveSolicitations();
    }
}
