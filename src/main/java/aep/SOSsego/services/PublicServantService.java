package aep.SOSsego.services;

import aep.SOSsego.models.PublicServantModel;
import aep.SOSsego.repositories.PublicServantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PublicServantService {

    private final PublicServantRepository repository;
    private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    public PublicServantService(PublicServantRepository repository, org.springframework.security.crypto.password.PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public PublicServantModel save(PublicServantModel servant) {
        if (repository.existsByCpf(servant.getCpf())){
            throw new RuntimeException("CPF ja cadastrado");
        }

        if (passwordEncoder == null) {
            throw new RuntimeException("PasswordEncoder não injetado");
        }

        servant.setPassword(passwordEncoder.encode(servant.getPassword()));
        servant.setRole(aep.SOSsego.enums.RoleEnum.SERVIDOR_PUBLICO);

        return repository.save(servant);
    }

    public Optional<PublicServantModel> findById(Long id) {
        return repository.findById(id);
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
        PublicServantModel existingServant = repository.
                findById(id).
                orElseThrow(() -> new RuntimeException("Servidor nao existe"));

        existingServant.setName(servant.getName());
        if(!existingServant.getCpf().equals(servant.getCpf())
                && repository.existsByCpf(servant.getCpf())) {
            throw new RuntimeException("CPF ja cadastrado");
        }
        existingServant.setCpf(servant.getCpf());
        existingServant.setContact(servant.getContact());
        existingServant.setRegistration(servant.getRegistration());
        existingServant.setPosition(servant.getPosition());

        return repository.save(existingServant);
    }

}
