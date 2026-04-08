package org.example.services;

import org.example.model.SolicitacaoModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class SistemaSolicitacoesService {
    private List<SolicitacaoModel> lista = new ArrayList<>();

    public void adicionar(SolicitacaoModel solicitacao) {
        lista.add(solicitacao);
    }

    public void listar() {
        lista.forEach(System.out::println);
    }

    public Stream<SolicitacaoModel> buscar(int protocolo) {
        return (Stream<SolicitacaoModel>) lista.stream()
                .filter(solicitacaoModel -> solicitacaoModel.getProtocolo() == protocolo)
                .findFirst()
                .orElse(null);
    }
}
