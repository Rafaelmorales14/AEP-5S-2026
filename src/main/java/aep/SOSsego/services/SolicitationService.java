package aep.SOSsego.services;

import aep.SOSsego.enums.StatusSolicitationEnum;
import aep.SOSsego.models.PublicServantModel;
import aep.SOSsego.models.SolicitationModel;
import aep.SOSsego.models.StatusHistoryModel;
import aep.SOSsego.repositories.PublicServantRepository;
import aep.SOSsego.repositories.SolicitationRepository;
import aep.SOSsego.repositories.StatusHistoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SolicitationService {

    private final SolicitationRepository repository;
    private final ProtocolService protocol;
    private final CalculatorSLAService calculator;
    private final PriorityService priorityService;
    private final StatusHistoryRepository historyRepository;
    private final ValidateTransitionService validateTransitionService;
    private final PublicServantRepository publicServantRepository;

    public SolicitationService(SolicitationRepository repository,
                               ProtocolService protocol,
                               CalculatorSLAService calculator,
                               PriorityService priority,
                               StatusHistoryRepository historyRepository,
                               ValidateTransitionService validateTransitionService,
                               PublicServantRepository publicServantRepository) {
        this.repository = repository;
        this.protocol = protocol;
        this.calculator = calculator;
        this.priorityService = priority;
        this.historyRepository = historyRepository;
        this.validateTransitionService = validateTransitionService;
        this.publicServantRepository = publicServantRepository;
    }

    @Transactional
    public SolicitationModel save(SolicitationModel solicitation) {
        solicitation.setProtocol(protocol.generateProtocol());
        solicitation.setDateSLA(calculator.calculateSLA(solicitation.getCategory()));
        solicitation.setPriority(priorityService.definePriority(solicitation.getCategory()));
        solicitation.setCurrentlyStatus(StatusSolicitationEnum.ABERTO);
        if (solicitation.getIsAnonymous()) {
            solicitation.setCitizen(null);
        }

        SolicitationModel saved = repository.save(solicitation);

        StatusHistoryModel history = new StatusHistoryModel();

        history.setSolicitation(saved);
        history.setStatusAnterior(null);
        history.setStatusNovo(StatusSolicitationEnum.ABERTO);
        history.setComentarioObrigatorio("Solicitacao aberta");

        historyRepository.save(history);

        return saved;
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

        if(solicitation.getPriority() != existingSolicitation.getPriority()) {
            throw new IllegalArgumentException("Prioridade nao pode ser alterada");
        }

        if(solicitation.getCurrentlyStatus() != existingSolicitation.getCurrentlyStatus()) {
            throw new IllegalArgumentException("Status nao pode ser alterado");
        }

        existingSolicitation.setCategory(solicitation.getCategory());
        existingSolicitation.setAddress(solicitation.getAddress());

        existingSolicitation.setIsAnonymous(solicitation.getIsAnonymous());
        if (Boolean.TRUE.equals(solicitation.getIsAnonymous())) {
            existingSolicitation.setCitizen(null);
        } else {
            existingSolicitation.setCitizen(
                    solicitation.getCitizen()
            );
        }

        return repository.save(existingSolicitation);
    }

    public SolicitationModel updateStatus(String protocol,
                                          StatusSolicitationEnum newStatus,
                                          String comment,
                                          Long publicServantId) {

        SolicitationModel solicitation = repository.
                findByProtocol(protocol).
                orElseThrow(() ->
                    new RuntimeException("Solicitacao nao existe"));

        PublicServantModel publicServantSaved = publicServantRepository.
                findById(publicServantId).
                orElseThrow(() ->
                        new RuntimeException("Servidor nao existe"));

        validateTransitionService.validateTransition(solicitation.getCurrentlyStatus(), newStatus);

        if(comment == null || comment.isBlank()) {
            throw new RuntimeException("Comentario obrigatorio");
        }

        StatusHistoryModel history = new StatusHistoryModel();

        history.setSolicitation(solicitation);
        history.setStatusAnterior(solicitation.getCurrentlyStatus());
        history.setStatusNovo(newStatus);
        history.setComentarioObrigatorio(comment);
        history.setPublicServant(publicServantSaved);

        historyRepository.save(history);

        solicitation.setCurrentlyStatus(newStatus);

        return repository.save(solicitation);
    }
}
