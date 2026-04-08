package org.example.repository;

import org.example.model.Solicitacao;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FilaAtendimentoMemoria implements IRepositorioSolicitacoes {
    private List<Solicitacao> listaSolicitacoes = new ArrayList<>();

    @Override
    public void salvar(Solicitacao solicitacao) {
        listaSolicitacoes.add(solicitacao);
    }

    @Override
    public List<Solicitacao> listarTodas() {
        return new ArrayList<>(listaSolicitacoes);
    }

    @Override
    public Optional<Solicitacao> buscarPorProtocolo(String protocolo) {
        return listaSolicitacoes.stream()
                .filter(s -> s.getProtocolo().equals(protocolo))
                .findFirst();
    }
}
