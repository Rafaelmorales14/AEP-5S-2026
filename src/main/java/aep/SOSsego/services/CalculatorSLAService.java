package aep.SOSsego.services;

import aep.SOSsego.enums.CategoryEnum;
import aep.SOSsego.repositories.CalculatorSLARepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CalculatorSLAService implements CalculatorSLARepository {

    @Override
    public LocalDateTime calculateSLA(CategoryEnum category) {
        LocalDateTime now = LocalDateTime.now();
        switch (category) {
            case VEICULO_SOM_ALTO:
                return now.plusHours(2);
            case BAR_CASA_NOTURNA:
                return now.plusHours(4);
            case FESTA_RESIDENCIAL:
                return now.plusHours(6);
            case OBRAS_IRREGULARES:
                return now.plusDays(2);
            default:
                return now.plusDays(1);
        }
    }
}
