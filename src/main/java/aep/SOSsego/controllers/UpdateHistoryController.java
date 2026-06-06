package aep.SOSsego.controllers;

import aep.SOSsego.models.StatusHistoryModel;
import aep.SOSsego.services.UpdateHistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/historico")
public class UpdateHistoryController {

    private final UpdateHistoryService service;

    public UpdateHistoryController(UpdateHistoryService service) {
        this.service = service;
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
}
