package aep.SOSsego.controllers;

import aep.SOSsego.models.PublicServantModel;
import aep.SOSsego.services.PublicServantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servidor")
public class PublicServantController {

    private final PublicServantService service;

    public PublicServantController(PublicServantService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PublicServantModel> save(@RequestBody PublicServantModel servant) {
        PublicServantModel servantSaved = service.save(servant);

        return ResponseEntity.status(HttpStatus.CREATED).body(servantSaved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublicServantModel> findById(@PathVariable("id") Long id) {
        return service.
                findById(id).
                map(ResponseEntity::ok).
                orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<PublicServantModel>> findAll() {
        List<PublicServantModel> servants = service.findAll();

        return ResponseEntity.ok().body(servants);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        if (service.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublicServantModel> update(@PathVariable("id") Long id,
                                                     @RequestBody PublicServantModel servant){

        PublicServantModel servantUpdated = service.update(id, servant);

        if(service.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(servantUpdated);
    }

}
