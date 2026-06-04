package aep.SOSsego.controllers;

import aep.SOSsego.models.SolicitationModel;
import aep.SOSsego.services.SolicitationService;
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
    public ResponseEntity<SolicitationModel> save(SolicitationModel solicitation) {
        SolicitationModel solicitationSaved = service.save(solicitation);

        return ResponseEntity.status(HttpStatus.CREATED).body(solicitationSaved);
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        if(service.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<SolicitationModel> update(@PathVariable("id") Long id, @RequestBody SolicitationModel solicitation) {
        if(service.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        SolicitationModel solicitationUpdated = service.update(id, solicitation);
        return ResponseEntity.ok(solicitationUpdated);
    }

    
}
