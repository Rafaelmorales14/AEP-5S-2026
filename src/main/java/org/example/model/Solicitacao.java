package org.example.model;

import org.example.model.enums.CategoriaSossego;
import org.example.model.enums.Prioridade;
import org.example.model.enums.StatusSolicitacao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Solicitacao {
    private String protocolo;
    private String descricao;
    private CategoriaSossego categoria;
    private StatusSolicitacao statusAtual;
    private String enderecoLocalizacao;
    private boolean isAnonima;
    private Prioridade prioridade;
    private Long cidadaoId;
    private LocalDateTime dataCriacao;
    private LocalDateTime prazoSLA;
    private List<HistoricoStatus> historico;

    public Solicitacao(String protocolo, CategoriaSossego categoria, String descricao, 
                       String enderecoLocalizacao, boolean isAnonima, Long cidadaoId, 
                       StatusSolicitacao statusAtual, Prioridade prioridade, 
                       LocalDateTime dataCriacao, LocalDateTime prazoSLA) {
        this.protocolo = protocolo;
        this.categoria = categoria;
        this.descricao = descricao;
        this.enderecoLocalizacao = enderecoLocalizacao;
        this.isAnonima = isAnonima;
        if(isAnonima){
            this.cidadaoId = null;
        }
        this.cidadaoId = cidadaoId;
        this.statusAtual = statusAtual;
        this.prioridade = prioridade;
        this.dataCriacao = dataCriacao;
        this.prazoSLA = prazoSLA;
        this.historico = new ArrayList<>();
    }

    public String getProtocolo() {
        return protocolo;
    }

    public CategoriaSossego getCategoria() {
        return categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getEnderecoLocalizacao() {
        return enderecoLocalizacao;
    }

    public boolean isAnonima() {
        return isAnonima;
    }

    public Long getCidadaoId() {
        return cidadaoId;
    }

    public StatusSolicitacao getStatusAtual() {
        return statusAtual;
    }

    public void setStatusAtual(StatusSolicitacao statusAtual) {
        this.statusAtual = statusAtual;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public LocalDateTime getPrazoSLA() {
        return prazoSLA;
    }

    public List<HistoricoStatus> getHistorico() {
        return historico;
    }

    public void adicionarHistorico(HistoricoStatus item) {
        this.historico.add(item);
    }
}
