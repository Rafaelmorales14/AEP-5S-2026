package aep.SOSsego.repositories;

import aep.SOSsego.models.PublicServantModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicServantRepository extends JpaRepository<PublicServantModel, Long> {
    Boolean existsByCpf(String cpf);
}
