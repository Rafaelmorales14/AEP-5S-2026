package aep.SOSsego.services;

import aep.SOSsego.enums.CategoryEnum;
import aep.SOSsego.enums.PriorityEnum;
import org.springframework.stereotype.Service;

@Service
public class PriorityService {
    public PriorityEnum definePriority(CategoryEnum category) {
        switch (category) {
            case VEICULO_SOM_ALTO:
                return PriorityEnum.URGENTE;
            case BAR_CASA_NOTURNA:
                return PriorityEnum.ALTA;
            case FESTA_RESIDENCIAL:
                return PriorityEnum.MEDIA;
            case OBRAS_IRREGULARES:
                return PriorityEnum.BAIXA;
            default:
                return PriorityEnum.BAIXA;
        }
    }
}
