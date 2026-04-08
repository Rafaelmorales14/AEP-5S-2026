package org.example;

import org.example.cli.MenuInterativo;
import org.example.repository.FilaAtendimentoMemoria;
import org.example.repository.IRepositorioSolicitacoes;
import org.example.service.CalculadoraSLAPadrao;
import org.example.service.ICalculadoraSLA;
import org.example.service.ServicoSolicitacoes;

public class Main {
    public static void main(String[] args) {
        IRepositorioSolicitacoes repositorio = new FilaAtendimentoMemoria();
        ICalculadoraSLA calculadoraSLA = new CalculadoraSLAPadrao();
        ServicoSolicitacoes servico = new ServicoSolicitacoes(repositorio, calculadoraSLA);

        MenuInterativo menu = new MenuInterativo(servico);
        menu.iniciar();
    }
}