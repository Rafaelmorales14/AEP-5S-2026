package aep.SOSsego.controllers;

import aep.SOSsego.models.StatusHistoryModel;
import aep.SOSsego.services.StatusHistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/historico")
public class StatusHistoryController {

    private final StatusHistoryService service;

    public StatusHistoryController(StatusHistoryService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<StatusHistoryModel> save(@RequestBody StatusHistoryModel statusHistory) {
        StatusHistoryModel statusHistorySaved = service.save(statusHistory);

        return ResponseEntity.status(HttpStatus.CREATED).body(statusHistorySaved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StatusHistoryModel> findById(@PathVariable("id") Long id) {
        return service.
                findById(id).
                map(ResponseEntity::ok).
                orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<StatusHistoryModel>> findAll() {
        List<StatusHistoryModel> statusHistories = service.findAll();

        return ResponseEntity.ok().body(statusHistories);
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
    public ResponseEntity<StatusHistoryModel> update(@PathVariable("id") Long id, @RequestBody StatusHistoryModel statusHistory) {
        if(service.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        StatusHistoryModel statusHistoryUpdated = service.update(id, statusHistory);
        return ResponseEntity.ok(statusHistoryUpdated);
    }
}
