package aep.SOSsego.controllers;

import aep.SOSsego.enums.CategoryEnum;
import aep.SOSsego.enums.PriorityEnum;
import aep.SOSsego.models.SolicitationModel;
import aep.SOSsego.services.ReportsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/relatorio")
public class ReportsController {

    private final ReportsService service;

    public ReportsController(ReportsService service) {
        this.service = service;
    }


    @GetMapping("/prioridade")
    public ResponseEntity<Map<PriorityEnum, Long>> reportByPriority() {
        return ResponseEntity.ok(
                service.reportByPriority()
        );
    }

    @GetMapping("/categorias")
    public ResponseEntity<Map<CategoryEnum, Long>> reportByCategory() {
        return ResponseEntity.ok(
                service.reportByCategory()
        );
    }

    @GetMapping("/ativas")
    public ResponseEntity<List<SolicitationModel>>
    findActiveSolicitations() {

        return ResponseEntity.ok(
                service.findActiveSolicitations()
        );
    }
}
