package aep.SOSsego.repositories;

import aep.SOSsego.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    Boolean existsByCpf(String cpf);
}
