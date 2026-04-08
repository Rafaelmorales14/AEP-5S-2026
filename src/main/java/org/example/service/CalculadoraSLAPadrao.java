package org.example.service;

import org.example.model.enums.CategoriaSossego;
import java.time.LocalDateTime;

public class CalculadoraSLAPadrao implements ICalculadoraSLA {
    @Override
    public LocalDateTime calcularPrazo(CategoriaSossego categoria) {
        LocalDateTime agora = LocalDateTime.now();
        switch (categoria) {
            case VEICULO_SOM_ALTO:
                return agora.plusHours(2);
            case BAR_CASA_NOTURNA:
                return agora.plusHours(4);
            case FESTA_RESIDENCIAL:
                return agora.plusHours(6);
            case OBRAS_IRREGULARES:
                return agora.plusDays(2);
            default:
                return agora.plusDays(1);
        }
    }
}
