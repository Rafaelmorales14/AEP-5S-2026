package aep.SOSsego.services;

import aep.SOSsego.enums.StatusSolicitationEnum;
import aep.SOSsego.dtos.SolicitationCreateDTO;
import aep.SOSsego.models.CitizenModel;
import aep.SOSsego.models.PublicServantModel;
import aep.SOSsego.models.SolicitationModel;
import aep.SOSsego.repositories.CitizenRepository;
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

    private final CitizenRepository citizenRepository;

    public SolicitationService(SolicitationRepository repository,
                               ProtocolService protocol,
                               CalculatorSLAService calculator,
                               PriorityService priority,
                               StatusHistoryRepository historyRepository,
                               ValidateTransitionService validateTransitionService,
                               PublicServantRepository publicServantRepository,
                               CitizenRepository citizenRepository) {
        this.repository = repository;
        this.protocol = protocol;
        this.calculator = calculator;
        this.priorityService = priority;
        this.historyRepository = historyRepository;
        this.validateTransitionService = validateTransitionService;
        this.publicServantRepository = publicServantRepository;
        this.citizenRepository = citizenRepository;
    }

    @Transactional
    public SolicitationModel save(SolicitationCreateDTO dto, String citizenEmail) {
        SolicitationModel solicitation = new SolicitationModel();
        solicitation.setDescription(dto.description());
        solicitation.setCategory(dto.category());
        solicitation.setAddress(dto.address());
        solicitation.setIsAnonymous(dto.isAnonymous());

        solicitation.setProtocol(protocol.generateProtocol());
        solicitation.setDateSLA(calculator.calculateSLA(solicitation.getCategory()));
        solicitation.setPriority(priorityService.definePriority(solicitation.getCategory()));
        solicitation.setCurrentlyStatus(StatusSolicitationEnum.ABERTO);

        if (Boolean.TRUE.equals(solicitation.getIsAnonymous())) {
            solicitation.setCitizen(null);
        } else {
            CitizenModel citizen = citizenRepository.findByEmail(citizenEmail)
                    .orElseThrow(() -> new RuntimeException("Cidadão não encontrado"));
            solicitation.setCitizen(citizen);
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

    @Transactional
    public SolicitationModel update(Long id, SolicitationCreateDTO dto, String citizenEmail) {
        SolicitationModel existingSolicitation = repository.findById(id).
                orElseThrow(() -> new RuntimeException("Solicitacao nao existe"));

        CitizenModel citizen = citizenRepository.findByEmail(citizenEmail)
                .orElseThrow(() -> new RuntimeException("Cidadão não encontrado"));

        if (!existingSolicitation.getIsAnonymous() && 
            (existingSolicitation.getCitizen() == null ||
                    !existingSolicitation.getCitizen().getId().equals(citizen.getId()))) {
            throw new RuntimeException("Você não tem permissão para alterar esta solicitação");
        }

        existingSolicitation.setCategory(dto.category());
        existingSolicitation.setAddress(dto.address());
        existingSolicitation.setDescription(dto.description());

        existingSolicitation.setIsAnonymous(dto.isAnonymous());
        if (Boolean.TRUE.equals(dto.isAnonymous())) {
            existingSolicitation.setCitizen(null);
        } else {
            existingSolicitation.setCitizen(citizen);
        }

        return repository.save(existingSolicitation);
    }

    @Transactional
    public SolicitationModel updateStatus(String protocol,
                                          StatusSolicitationEnum newStatus,
                                          String comment,
                                          String publicServantEmail) {

        SolicitationModel solicitation = repository.
                findByProtocol(protocol).
                orElseThrow(() ->
                    new RuntimeException("Solicitacao nao existe"));

        PublicServantModel publicServantSaved = publicServantRepository.
                findByEmail(publicServantEmail).
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
