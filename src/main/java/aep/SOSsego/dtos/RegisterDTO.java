package aep.SOSsego.dtos;

public record RegisterDTO(
        String name,
        String email,
        String password,
        String cpf,
        String contact
) {}
