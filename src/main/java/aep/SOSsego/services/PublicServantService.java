package aep.SOSsego.services;

import aep.SOSsego.models.PublicServantModel;
import aep.SOSsego.repositories.PublicServantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PublicServantService {

    private final PublicServantRepository repository;

    public PublicServantService(PublicServantRepository repository) {
        this.repository = repository;
    }

    public PublicServantModel save(PublicServantModel servant) {
        if (repository.existsByCpf(servant.getCpf())){
            throw new RuntimeException("CPF ja cadastrado");
        }

        return repository.save(servant);
    }

    public PublicServantModel findById(Long id) {
        return repository.
                findById(id).
                orElseThrow(() -> new RuntimeException("Servidor nao existe"));
    }

    public List<PublicServantModel> findAll() {
        return repository.findAll();
    }

    public void deleteById(Long id) {
        repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servidor não existe"));

        repository.deleteById(id);
    }

    public PublicServantModel update(Long id, PublicServantModel servant) {
        PublicServantModel servantExistant = repository.
                findById(id).
                orElseThrow(() -> new RuntimeException("Servidor nao existe"));

        servantExistant.setName(servant.getName());
        servantExistant.setCpf(servant.getCpf());
        servantExistant.setContact(servant.getContact());
        servantExistant.setRegistration(servant.getRegistration());
        servantExistant.setPosition(servant.getPosition());

        return repository.save(servantExistant);
    }

}
