package aep.SOSsego.services;

import aep.SOSsego.models.SolicitationModel;
import aep.SOSsego.repositories.SolicitationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SolicitationService {

    private final SolicitationRepository repository;
    private final ProtocolService protocol;
    private final CalculatorSLAService calculator;
    private final PriorityService priorityService;

    public SolicitationService(SolicitationRepository repository,
                               ProtocolService protocol,
                               CalculatorSLAService calculator,
                               PriorityService priority) {
        this.repository = repository;
        this.protocol = protocol;
        this.calculator = calculator;
        this.priorityService = priority;
    }

    public SolicitationModel save(SolicitationModel solicitation) {
        solicitation.setProtocol(protocol.generateProtocol());
        solicitation.setDateSLA(calculator.calculateSLA(solicitation.getCategory()));
        solicitation.setPriority(priorityService.definePriority(solicitation.getCategory()));
        if (solicitation.getIsAnonymous()) {
            solicitation.setCitizen(null);
        }
        return repository.save(solicitation);
    }

    public Optional<SolicitationModel> findById(Long id) {
        return repository.findById(id);
    }

    public List<SolicitationModel> findAll() {
        return repository.findAll();
    }

    public Optional<SolicitationModel> findByProtocol(String protocol) {
        return repository.findByProtocol(protocol);
    }

    public void deleteById(Long id) {
        repository.findById(id).
                orElseThrow(() -> new RuntimeException("Solicitacao nao existe"));

        repository.deleteById(id);
    }

    public SolicitationModel update(Long id, SolicitationModel solicitation) {
        SolicitationModel existingSolicitation = repository.findById(id).
                orElseThrow(() -> new RuntimeException("Solicitacao nao existe"));

        existingSolicitation.setCategory(solicitation.getCategory());
        existingSolicitation.setAddress(solicitation.getAddress());
        existingSolicitation.setIsAnonymous(solicitation.getIsAnonymous());
        if(!solicitation.getIsAnonymous()){
            existingSolicitation.setCitizen(solicitation.getCitizen());
        }

        return repository.save(existingSolicitation);
    }
}
