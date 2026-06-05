package aep.SOSsego.repositories;

import aep.SOSsego.enums.CategoryEnum;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

public interface CalculatorSLARepository {
    public LocalDateTime calculateSLA(CategoryEnum category);
}
