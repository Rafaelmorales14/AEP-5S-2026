package aep.SOSsego.controllers;

import aep.SOSsego.dtos.SolicitationResponseDTO;
import aep.SOSsego.dtos.UpdateStatusDTO;
import aep.SOSsego.dtos.SolicitationCreateDTO;
import aep.SOSsego.models.SolicitationModel;
import aep.SOSsego.models.UserModel;
import aep.SOSsego.services.SolicitationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/solicitacao")
public class SolicitationController {

    private final SolicitationService service;

    public SolicitationController(SolicitationService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<SolicitationModel> save(@Valid @RequestBody SolicitationCreateDTO solicitation,
                                                  @AuthenticationPrincipal UserModel user) {
        SolicitationModel solicitationSaved = service.save(solicitation, user.getEmail());

        return ResponseEntity.status(HttpStatus.CREATED).body(solicitationSaved);
    }

    @PostMapping("/{protocolo}/status")
    public ResponseEntity<SolicitationModel> updateStatus(@PathVariable("protocolo") String protocol,
                                                          @RequestBody UpdateStatusDTO dto,
                                                          @AuthenticationPrincipal UserModel user) {

        return ResponseEntity.status(HttpStatus.CREATED).
                body(service.updateStatus
                        (protocol, dto.newStatus(), dto.comment(), user.getEmail()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SolicitationModel> findById(@PathVariable("id") Long id) {
        return service.
                findById(id).
                map(ResponseEntity::ok).
                orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/my")
    public ResponseEntity<List<SolicitationResponseDTO>> findByCitizen(@AuthenticationPrincipal UserModel user) {

        List<SolicitationModel> solicitations = service.findByCitizen(user.getEmail());

        List<SolicitationResponseDTO> response = solicitations.
                stream().
                map(s -> new SolicitationResponseDTO(
                    s.getProtocol(),
                    s.getDescription(),
                    s.getCategory(),
                    s.getIsAnonymous(),
                    s.getCurrentlyStatus(),
                    s.getAddress(),
                    s.getCreatedAt()
        )).toList();

        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    public ResponseEntity<List<SolicitationModel>> findAll() {
        List<SolicitationModel> solicitations = service.findAll();

        return ResponseEntity.ok().body(solicitations);
    }

    @GetMapping("/protocolo/{protocolo}")
    public ResponseEntity<SolicitationModel> findByProtocol(@PathVariable("protocolo") String protocol) {
        return service.findByProtocol(protocol).
                map(ResponseEntity::ok).
                orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        if(service.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<SolicitationModel> update(@PathVariable("id") Long id,
                                                    @Valid @RequestBody SolicitationCreateDTO dto,
                                                    @AuthenticationPrincipal UserModel user) {
        if(service.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        SolicitationModel solicitationUpdated = service.update(id, dto, user.getEmail());
        return ResponseEntity.ok(solicitationUpdated);
    }
}
