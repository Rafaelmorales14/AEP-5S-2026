package org.example.model;

public class UsuarioModel {
    public String nome;
    public String cpf;
    public String cidade;
    public String estado;

    public UsuarioModel(String nome, String cpf, String cidade, String estado) {
        this.nome = nome;
        this.cpf = cpf;
        this.cidade = cidade;
        this.estado = estado;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
