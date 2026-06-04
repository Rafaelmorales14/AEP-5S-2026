package aep.SOSsego.services;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProtocolService {
    public String generateProtocol() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
