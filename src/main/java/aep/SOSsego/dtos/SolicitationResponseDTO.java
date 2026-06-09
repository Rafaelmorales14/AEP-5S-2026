package aep.SOSsego.dtos;

import aep.SOSsego.enums.CategoryEnum;
import aep.SOSsego.enums.StatusSolicitationEnum;

import java.time.LocalDateTime;

public record SolicitationResponseDTO(
        String protocol,
        String description,
        CategoryEnum category,
        Boolean isAnonymous,
        StatusSolicitationEnum currentlyStatus,
        String address,
        LocalDateTime createdAt
) {
}
