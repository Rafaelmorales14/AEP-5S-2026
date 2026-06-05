package aep.SOSsego.services;

import aep.SOSsego.models.StatusHistoryModel;
import aep.SOSsego.repositories.StatusHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatusHistoryService {

    private final StatusHistoryRepository repository;

    public StatusHistoryService(StatusHistoryRepository repository) {
        this.repository = repository;
    }

    public StatusHistoryModel save(StatusHistoryModel statusHistory) {
        return repository.save(statusHistory);
    }

    public Optional<StatusHistoryModel> findById(Long id) {
        return repository.findById(id);
    }

    public List<StatusHistoryModel> findAll() {
        return repository.findAll();
    }
}
