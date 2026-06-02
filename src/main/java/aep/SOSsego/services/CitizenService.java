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
        if (repository.existsByCpf(citizen.getCpf())) {
            throw new RuntimeException("Cpf ja cadastrado");
        }

        return repository.save(citizen);
    }

    public Optional<CitizenModel> findById(Long id) {
        return repository.findById(id);
    }

    public List<CitizenModel> findAll() {
        return repository.findAll();
    }

    public void deleteById(Long id) {
        repository.
                findById(id).
                orElseThrow(() -> new RuntimeException("Cidadao nao existe"));

        repository.deleteById(id);
    }

    public CitizenModel update(Long id, CitizenModel citizen) {
        CitizenModel citizenExistant  = repository.
                findById(id).
                orElseThrow(() -> new RuntimeException("Cidadao nao existe"));

        citizenExistant.setName(citizen.getName());
        if (citizenExistant.getCpf().equals(citizen.getCpf())
                && repository.existsByCpf(citizen.getCpf())) {
            throw new RuntimeException("CPF ja cadastrado");
        }
        citizenExistant.setCpf(citizen.getCpf());
        citizenExistant.setContact(citizen.getContact());

        return repository.save(citizenExistant);
    }
}
