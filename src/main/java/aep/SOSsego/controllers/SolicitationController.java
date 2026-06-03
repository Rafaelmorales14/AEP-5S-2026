package aep.SOSsego.controllers;

import aep.SOSsego.services.SolicitationService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/solicitacao")
public class SolicitationController {

    private final SolicitationService service;

    public SolicitationController(SolicitationService service) {
        this.service = service;
    }

    
}
