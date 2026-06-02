package aep.SOSsego.services;

import aep.SOSsego.models.CitizenModel;
import aep.SOSsego.repositories.CitizenRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CitizenService {

    private final CitizenRepository repository;

    public CitizenService(CitizenRepository repository) {
        this.repository = repository;
    }

    public CitizenModel save(CitizenModel citizen) {
        return repository.save(citizen);
    }

    public Optional<CitizenModel> findById(Long id) {
        return repository.findById(id);
    }

    public List<CitizenModel> findAll() {
        return repository.findAll();
    }

    public void deleteById(Long id) {
        if(!repository.existsById(id)) {
            throw new RuntimeException("Cidadao nao existe");
        }

        repository.deleteById(id);
    }

    public CitizenModel update(Long id, CitizenModel citizen) {
        CitizenModel citizenExistant  = repository.
                findById(id).
                orElseThrow(() -> new RuntimeException("Cidadao nao existe"));

        citizenExistant.setName(citizen.getName());
        citizenExistant.setCpf(citizen.getCpf());
        citizenExistant.setContact(citizen.getContact());

        return repository.save(citizenExistant);
    }
}
