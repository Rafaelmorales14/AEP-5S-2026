package org.example.model;

import org.example.enums.Categoria;
import org.example.enums.Status;

public class SolicitacaoModel {
    private int protocolo;
    private String descricao;
    private Categoria categoria;
    private Status status;
    private String bairro;

    public SolicitacaoModel(int protocolo, String descricao, Categoria categoria, Status status, String bairro) {
        this.protocolo = protocolo;
        this.descricao = descricao;
        this.categoria = categoria;
        this.status = status;
        this.bairro = bairro;
    }

    public int getProtocolo() {
        return protocolo;
    }

    public void setProtocolo(int protocolo) {
        this.protocolo = protocolo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }
}
