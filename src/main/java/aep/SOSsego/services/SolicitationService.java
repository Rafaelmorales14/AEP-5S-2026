package aep.SOSsego.services;

import aep.SOSsego.models.CitizenModel;
import aep.SOSsego.models.SolicitationModel;
import aep.SOSsego.repositories.SolicitationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SolicitationService {

    private final SolicitationRepository repository;
    private final ProtocolService protocol;

    public SolicitationService(SolicitationRepository repository, ProtocolService protocol) {
        this.repository = repository;
        this.protocol = protocol;
    }

    public SolicitationModel save(SolicitationModel solicitation) {
        solicitation.setProtocol(protocol.generateProtocol());
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
        existingSolicitation.setProperty(solicitation.getProperty());
        existingSolicitation.setCitizen(solicitation.getCitizen());

        return repository.save(existingSolicitation);
    }

}
