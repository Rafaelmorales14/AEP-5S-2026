package aep.SOSsego.controllers;

import aep.SOSsego.models.CitizenModel;
import aep.SOSsego.services.CitizenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cidadao")
public class CitizenController {

    private final CitizenService service;

    public CitizenController(CitizenService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CitizenModel> save(@RequestBody CitizenModel citizen) {
        CitizenModel citizenSaved = service.save(citizen);

        return ResponseEntity.status(HttpStatus.CREATED).body(citizenSaved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CitizenModel> findById(@PathVariable("id") Long id) {
        return service.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<CitizenModel>> findAll() {
        List<CitizenModel> citizens = service.findAll();

        return ResponseEntity.ok().body(citizens);
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
    public ResponseEntity<CitizenModel> update(@PathVariable("id") Long id, @RequestBody CitizenModel citizen) {
        if (service.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        CitizenModel citizenUpdated = service.update(id, citizen);
        return ResponseEntity.ok(citizenUpdated);
    }
}
