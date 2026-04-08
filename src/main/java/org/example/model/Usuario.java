package org.example.model;

public abstract class Usuario {
    private Long id;
    private String nome;
    private String cpf;
    private String contato;

    public Usuario(Long id, String nome, String cpf, String contato) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.contato = contato;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }
}
