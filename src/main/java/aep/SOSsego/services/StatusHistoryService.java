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

    public void deleteById(Long id) {
        repository.findById(id).
                orElseThrow(() -> new RuntimeException("Historico nao existe"));

        repository.deleteById(id);
    }

    public StatusHistoryModel update(Long id, StatusHistoryModel statusHistory) {
        StatusHistoryModel existingStatusHistory = repository.findById(id).
                orElseThrow(() -> new RuntimeException("Historico nao existe"));

        existingStatusHistory.setStatusAnterior(statusHistory.getStatusAnterior());
        existingStatusHistory.setStatusNovo(statusHistory.getStatusNovo());
        existingStatusHistory.setComentarioObrigatorio(statusHistory.getComentarioObrigatorio());
        existingStatusHistory.setPublicServant(statusHistory.getPublicServant());
        existingStatusHistory.setSolicitation(statusHistory.getSolicitation());

        return repository.save(existingStatusHistory);
    }
}
