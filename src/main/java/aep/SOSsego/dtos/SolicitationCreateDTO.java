package aep.SOSsego.dtos;

import aep.SOSsego.enums.CategoryEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SolicitationCreateDTO(
        @NotBlank(message = "A descrição é obrigatória")
        @Size(min = 10, message = "A descrição deve ter no mínimo 10 caracteres")
        String description,

        @NotNull(message = "A categoria é obrigatória")
        CategoryEnum category,

        @NotBlank(message = "O endereço é obrigatório")
        @Size(min = 10, message = "Endereço muito curto (mínimo 10 caracteres)")
        String address,

        @NotNull(message = "Informe se a solicitação é anônima")
        Boolean isAnonymous
) {
}
