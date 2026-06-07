package aep.SOSsego.dtos;

import aep.SOSsego.enums.StatusSolicitationEnum;

public record UpdateStatusDTO(
        StatusSolicitationEnum newStatus,
        String comment
) {
}
