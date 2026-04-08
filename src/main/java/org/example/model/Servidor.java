package org.example.model;

public class Servidor extends Usuario {
    private String matricula;
    private String cargo;

    public Servidor(Long id, String nome, String cpf, String contato, String matricula, String cargo) {
        super(id, nome, cpf, contato);
        this.matricula = matricula;
        this.cargo = cargo;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
}
