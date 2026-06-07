package aep.SOSsego.auth;

import aep.SOSsego.dtos.LoginDTO;
import aep.SOSsego.dtos.LoginResponseDTO;
import aep.SOSsego.dtos.RegisterDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponseDTO> register(@RequestBody RegisterDTO registerDTO) {
        authService.register(registerDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(registerDTO));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register() {
        System.out.println("CHEGOU");
        return ResponseEntity.ok("funcionou");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO dto) {
        authService.login(dto);

        return ResponseEntity.ok(authService.login(dto));
    }

    @GetMapping("/teste")
    public String teste() {
        return "Autenticado";
    }
}
