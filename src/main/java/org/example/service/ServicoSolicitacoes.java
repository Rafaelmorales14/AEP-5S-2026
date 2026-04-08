package org.example.service;

import org.example.model.*;
import org.example.model.enums.*;
import org.example.repository.IRepositorioSolicitacoes;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ServicoSolicitacoes {
    private final IRepositorioSolicitacoes repositorio;
    private final ICalculadoraSLA calculadoraSLA;

    public ServicoSolicitacoes(IRepositorioSolicitacoes repositorio, ICalculadoraSLA calculadoraSLA) {
        this.repositorio = repositorio;
        this.calculadoraSLA = calculadoraSLA;
    }

    public Solicitacao criarSolicitacao(CategoriaSossego categoria, String descricao, String endereco, boolean isAnonima, Cidadao usuarioAutenticado) {
        validarAbuso(descricao, endereco);

        String protocolo = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        Prioridade prioridade = definirPrioridade(categoria);
        LocalDateTime prazoSLA = calculadoraSLA.calcularPrazo(categoria);
        Long cidadaoId = isAnonima ? null : (usuarioAutenticado != null ? usuarioAutenticado.getId() : null);

        Solicitacao nova = new Solicitacao(
                protocolo, categoria, descricao, endereco, isAnonima, cidadaoId,
                StatusSolicitacao.ABERTO, prioridade, LocalDateTime.now(), prazoSLA
        );

        repositorio.salvar(nova);
        return nova;
    }

    public List<Solicitacao> listarSolicitacoesAtivas(Servidor servidor) {
        if (servidor == null) {
            throw new SecurityException("Apenas servidores podem acessar a lista.");
        }

        return repositorio.listarTodas().stream()
                .filter(s -> s.getStatusAtual() != StatusSolicitacao.ENCERRADO)
                .sorted(Comparator.comparing(Solicitacao::getPrioridade).reversed()
                        .thenComparing(Solicitacao::getPrazoSLA))
                .collect(Collectors.toList());
    }

    public Solicitacao buscarPorProtocolo(String protocolo) {
        return repositorio.buscarPorProtocolo(protocolo)
                .orElseThrow(() -> new IllegalArgumentException("Solicitação não encontrada: " + protocolo));
    }

    public void atualizarStatus(String protocolo, StatusSolicitacao novoStatus, String comentario, Servidor servidorResponsavel) {
        Solicitacao solicitacao = buscarPorProtocolo(protocolo);

        validarTransicao(solicitacao.getStatusAtual(), novoStatus);
        if (comentario == null || comentario.trim().isEmpty()) {
            throw new IllegalArgumentException("Comentário é obrigatório na troca de status.");
        }

        HistoricoStatus historico = new HistoricoStatus(
                solicitacao.getStatusAtual(), novoStatus, comentario, LocalDateTime.now(), servidorResponsavel
        );
        solicitacao.adicionarHistorico(historico);
        solicitacao.setStatusAtual(novoStatus);
    }

    private void validarAbuso(String descricao, String endereco) {
        if (descricao == null || descricao.length() < 10) {
            throw new IllegalArgumentException("Descrição muito curta (mínimo 10 caracteres).");
        }
        if (endereco == null || endereco.length() < 5) {
            throw new IllegalArgumentException("Endereço muito curto (mínimo 5 caracteres).");
        }
    }

    private Prioridade definirPrioridade(CategoriaSossego categoria) {
        switch (categoria) {
            case VEICULO_SOM_ALTO:
                return Prioridade.URGENTE;
            case BAR_CASA_NOTURNA:
                return Prioridade.ALTA;
            case FESTA_RESIDENCIAL:
                return Prioridade.MEDIA;
            case OBRAS_IRREGULARES:
                return Prioridade.BAIXA;
            default:
                return Prioridade.BAIXA;
        }
    }

    private void validarTransicao(StatusSolicitacao atual, StatusSolicitacao novo) {
        boolean valida = false;
        switch (atual) {
            case ABERTO:
                valida = (novo == StatusSolicitacao.TRIAGEM);
                break;
            case TRIAGEM:
                valida = (novo == StatusSolicitacao.EM_EXECUCAO || novo == StatusSolicitacao.ENCERRADO);
                break;
            case EM_EXECUCAO:
                valida = (novo == StatusSolicitacao.RESOLVIDO);
                break;
            case RESOLVIDO:
                valida = (novo == StatusSolicitacao.ENCERRADO);
                break;
            case ENCERRADO:
                valida = false;
                break;
        }

        if (!valida) {
            throw new IllegalStateException("Transição de status inválida: " + atual + " -> " + novo);
        }
    }
}
