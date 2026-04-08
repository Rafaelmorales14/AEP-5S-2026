package org.example.repository;

import org.example.model.Solicitacao;
import java.util.List;
import java.util.Optional;

public interface IRepositorioSolicitacoes {
    void salvar(Solicitacao solicitacao);
    List<Solicitacao> listarTodas();
    Optional<Solicitacao> buscarPorProtocolo(String protocolo);
}
