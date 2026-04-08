package org.example.model;

import org.example.enums.Status;

import java.time.LocalDateTime;

public class HistoricoStatusModel {
    private Status status;
    private String comentario;
    private LocalDateTime data;

    public HistoricoStatusModel(Status status, String comentario, LocalDateTime data) {
        this.status = status;
        this.comentario = comentario;
        this.data = data;
    }

    public String toString() {
        return data + " - " + status + " | " + comentario;
    }
}
