package aep.SOSsego.config;

import aep.SOSsego.enums.RoleEnum;
import aep.SOSsego.models.CitizenModel;
import aep.SOSsego.models.PublicServantModel;
import aep.SOSsego.models.UserModel;
import aep.SOSsego.repositories.CitizenRepository;
import aep.SOSsego.repositories.PublicServantRepository;
import aep.SOSsego.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DatabaseSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CitizenRepository citizenRepository;
    private final PublicServantRepository publicServantRepository;
    private final PasswordEncoder passwordEncoder;

    public DatabaseSeeder(UserRepository userRepository,
                          CitizenRepository citizenRepository,
                          PublicServantRepository publicServantRepository,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.citizenRepository = citizenRepository;
        this.publicServantRepository = publicServantRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        seedAdmin();
        seedServidor();
        seedCidadao();
    }

    private void seedAdmin() {
        String email = "admin@sossego.com";
        if (userRepository.findByEmail(email).isEmpty()) {
            UserModel admin = UserModel.builder()
                    .name("Administrador do Sistema")
                    .email(email)
                    .password(passwordEncoder.encode("admin123"))
                    .cpf("00000000000")
                    .contact("44999999999")
                    .role(RoleEnum.ADMIN)
                    .build();
            userRepository.save(admin);
            System.out.println("[SEED] Usuário ADMIN criado com sucesso.");
        }
    }

    private void seedServidor() {
        String email = "servidor@sossego.com";
        if (publicServantRepository.findByEmail(email).isEmpty()) {
            PublicServantModel servidor = PublicServantModel.builder()
                    .name("Servidor Teste")
                    .email(email)
                    .password(passwordEncoder.encode("servidor123"))
                    .cpf("11111111111")
                    .contact("44888888888")
                    .role(RoleEnum.SERVIDOR_PUBLICO)
                    .registration("123456")
                    .position("Analista")
                    .build();
            publicServantRepository.save(servidor);
            System.out.println("[SEED] Usuário SERVIDOR_PUBLICO criado com sucesso.");
        }
    }

    private void seedCidadao() {
        String email = "cidadao@sossego.com";
        if (citizenRepository.findByEmail(email).isEmpty()) {
            CitizenModel cidadao = CitizenModel.builder()
                    .name("Cidadão Teste")
                    .email(email)
                    .password(passwordEncoder.encode("cidadao123"))
                    .cpf("22222222222")
                    .contact("44777777777")
                    .role(RoleEnum.CIDADAO)
                    .build();
            citizenRepository.save(cidadao);
            System.out.println("[SEED] Usuário CIDADAO criado com sucesso.");
        }
    }
}
