package aep.SOSsego.auth;

import aep.SOSsego.dtos.RegisterDTO;
import aep.SOSsego.enums.RoleEnum;
import aep.SOSsego.models.CitizenModel;
import aep.SOSsego.services.CitizenService;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthorizationService authorizationService;
    private final CitizenService citizenService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(AuthorizationService authorizationService, CitizenService citizenService, PasswordEncoder passwordEncoder) {
        this.authorizationService = authorizationService;
        this.citizenService = citizenService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(RegisterDTO dto) {
        authorizationService.loadUserByUsername(dto.email());

        CitizenModel citizenSaved = new CitizenModel();

        citizenSaved.setName(dto.name());
        citizenSaved.setCpf(dto.cpf());
        citizenSaved.setEmail(dto.email());
        citizenSaved.setPassword(passwordEncoder.encode(dto.password()));
        citizenSaved.setContact(dto.contact());
        citizenSaved.setRole(RoleEnum.CIDADAO);

        citizenService.save(citizenSaved);
    }
}
