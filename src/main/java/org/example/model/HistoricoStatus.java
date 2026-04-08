package org.example.model;

import org.example.model.enums.StatusSolicitacao;
import java.time.LocalDateTime;

public class HistoricoStatus {
    private LocalDateTime dataHora;
    private StatusSolicitacao statusAnterior;
    private StatusSolicitacao statusNovo;
    private String comentarioObrigatorio;
    private Servidor servidorResponsavel;

    public HistoricoStatus(StatusSolicitacao statusAnterior, StatusSolicitacao statusNovo, String comentarioObrigatorio, LocalDateTime dataHora, Servidor servidorResponsavel) {
        this.statusAnterior = statusAnterior;
        this.statusNovo = statusNovo;
        this.comentarioObrigatorio = comentarioObrigatorio;
        this.dataHora = dataHora;
        this.servidorResponsavel = servidorResponsavel;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s -> %s | Resp: %s | Obs: %s", 
                dataHora.toString(), statusAnterior, statusNovo, servidorResponsavel.getNome(), comentarioObrigatorio);
    }
}
