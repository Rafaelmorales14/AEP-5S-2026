package aep.SOSsego.controllers;

import aep.SOSsego.dtos.UpdateStatusDTO;
import aep.SOSsego.models.SolicitationModel;
import aep.SOSsego.services.SolicitationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<SolicitationModel> save(@Valid @RequestBody SolicitationModel solicitation) {
        SolicitationModel solicitationSaved = service.save(solicitation);

        return ResponseEntity.status(HttpStatus.CREATED).body(solicitationSaved);
    }

    @PostMapping("/{protocol}/status")
    public ResponseEntity<SolicitationModel> updateStatus(@PathVariable("protocol") String protocol,
                                                          @RequestBody UpdateStatusDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED).
                body(service.updateStatus
                        (protocol, dto.newStatus(), dto.comment(), dto.publicServantId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SolicitationModel> findById(@PathVariable("id") Long id) {
        return service.
                findById(id).
                map(ResponseEntity::ok).
                orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<SolicitationModel>> findAll() {
        List<SolicitationModel> solicitations = service.findAll();

        return ResponseEntity.ok().body(solicitations);
    }

    @GetMapping("/protocol/{protocol}")
    public ResponseEntity<SolicitationModel> findByProtocol(@PathVariable("protocol") String protocol) {
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
                                                    @Valid @RequestBody SolicitationModel solicitation) {
        if(service.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        SolicitationModel solicitationUpdated = service.update(id, solicitation);
        return ResponseEntity.ok(solicitationUpdated);
    }
}
