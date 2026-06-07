package aep.SOSsego.auth;

import aep.SOSsego.dtos.LoginDTO;
import aep.SOSsego.dtos.LoginResponseDTO;
import aep.SOSsego.dtos.RegisterDTO;
import aep.SOSsego.enums.RoleEnum;
import aep.SOSsego.models.CitizenModel;
import aep.SOSsego.models.UserModel;
import aep.SOSsego.repositories.UserRepository;
import aep.SOSsego.services.CitizenService;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthorizationService authorizationService;
    private final CitizenService citizenService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    public AuthService(AuthorizationService authorizationService,
                       CitizenService citizenService,
                       PasswordEncoder passwordEncoder,
                       UserRepository userRepository,
                       TokenService tokenService) {
        this.authorizationService = authorizationService;
        this.citizenService = citizenService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    @Transactional
    public LoginResponseDTO register(RegisterDTO dto) {

        if(userRepository.findByEmail(dto.email()).isPresent()) {
            throw new RuntimeException("Email ja cadastrado");
        }

        CitizenModel citizenSaved = new CitizenModel();

        citizenSaved.setName(dto.name());
        citizenSaved.setCpf(dto.cpf());
        citizenSaved.setEmail(dto.email());
        citizenSaved.setPassword(passwordEncoder.encode(dto.password()));
        citizenSaved.setContact(dto.contact());
        citizenSaved.setRole(RoleEnum.CIDADAO);

        citizenService.save(citizenSaved);

        String token = tokenService.generateToken(citizenSaved);

        return new LoginResponseDTO(token);
    }

    @Transactional
    public LoginResponseDTO login(LoginDTO dto) {
        UserModel userSaved = userRepository.
                findByEmail(dto.email()).
                orElseThrow(()-> new RuntimeException("Usuario nao existe"));

        if(!passwordEncoder.
                matches(dto.password(),
                        userSaved.getPassword())) {

            throw new RuntimeException("Senha invalida");
        }

        String token = tokenService.generateToken(userSaved);

        return new LoginResponseDTO(token);
    }
}
