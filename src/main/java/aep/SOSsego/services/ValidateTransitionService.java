package aep.SOSsego.services;

import aep.SOSsego.enums.StatusSolicitationEnum;
import org.springframework.stereotype.Service;

@Service
public class ValidateTransitionService {
    public void validateTransition(
            StatusSolicitationEnum current,
            StatusSolicitationEnum next){

        if (!current.canTransitionTo(next)) {
            throw new IllegalStateException(
                    "Transição inválida: " + current + " -> " + next);
        }
    }
}
