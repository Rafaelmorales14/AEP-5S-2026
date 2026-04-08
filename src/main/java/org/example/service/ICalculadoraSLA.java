package org.example.service;

import org.example.model.enums.CategoriaSossego;
import java.time.LocalDateTime;

public interface ICalculadoraSLA {
    LocalDateTime calcularPrazo(CategoriaSossego categoria);
}
