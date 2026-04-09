package org.example.cli;

import org.example.model.*;
import org.example.model.enums.*;
import org.example.service.ServicoSolicitacoes;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MenuInterativo {
    private final ServicoSolicitacoes servico;
    private final Scanner scanner;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    // Simulação de usuários no sistema
    private final Cidadao cidadaoExemplo = new Cidadao(1L, "João Silva", "123.456.789-00", "joao@email.com");
    private final Servidor servidorExemplo = new Servidor(2L, "Agente Santos", "987.654.321-11", "santos@prefeitura.gov", "MAT-123", "Atendente");

    public MenuInterativo(ServicoSolicitacoes servico) {
        this.servico = servico;
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n=== OBSERVAÇÃO SOSSEGO CLI ===");
            System.out.println("1. Criar Nova Solicitação");
            System.out.println("2. Consultar por Protocolo");
            System.out.println("3. Listar Solicitações Ativas (Servidor)");
            System.out.println("4. Atualizar Status (Servidor)");
            System.out.println("5. Relatórios (Servidor)");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            
            try {
                opcao = Integer.parseInt(scanner.nextLine());
                switch (opcao) {
                    case 1: menuCriarSolicitacao(); break;
                    case 2: menuConsultarProtocolo(); break;
                    case 3: menuListarAtivas(); break;
                    case 4: menuAtualizarStatus(); break;
                    case 5: menuRelatorios(); break;
                    case 0: System.out.println("Saindo..."); break;
                    default: System.out.println("Opção inválida!");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    private void menuCriarSolicitacao() {
        System.out.println("\n--- Nova Solicitação ---");
        System.out.println("Categorias: 1. Som Automotivo, 2. Bar/Noturna, 3. Festa Residencial, 4. Obra");
        int catOp = Integer.parseInt(scanner.nextLine());
        CategoriaSossego categoria = switch (catOp) {
            case 1 -> CategoriaSossego.VEICULO_SOM_ALTO;
            case 2 -> CategoriaSossego.BAR_CASA_NOTURNA;
            case 3 -> CategoriaSossego.FESTA_RESIDENCIAL;
            default -> CategoriaSossego.OBRAS_IRREGULARES;
        };

        exibirInstrucoesCategoria(categoria);

        System.out.print("Descrição do problema: ");
        String desc = scanner.nextLine();
        System.out.print("Endereço da ocorrência: ");
        String end = scanner.nextLine();
        System.out.print("Deseja ser anônimo? (S/N): ");
        boolean anonima = scanner.nextLine().equalsIgnoreCase("S");

        Solicitacao s = servico.criarSolicitacao(categoria, desc, end, anonima, cidadaoExemplo);
        System.out.println("\n✓ Solicitação criada com sucesso!");
        System.out.println("PROTOCOLO: " + s.getProtocolo());
        System.out.println("PRAZO ESTIMADO (SLA): " + s.getPrazoSLA().format(formatter));
    }

    private void menuConsultarProtocolo() {
        System.out.print("\nDigite o protocolo: ");
        String protocolo = scanner.nextLine();
        Solicitacao s = servico.buscarPorProtocolo(protocolo);
        
        exibirDetalhes(s);
    }

    private void menuListarAtivas() {
        List<Solicitacao> lista = servico.listarSolicitacoesAtivas(servidorExemplo);
        System.out.println("\n--- Solicitações Ativas (Ordenadas por Prioridade/SLA) ---");
        for (Solicitacao s : lista) {
            System.out.printf("[%s] %-15s | %-15s | SLA: %s\n", 
                s.getProtocolo(), s.getPrioridade(), s.getStatusAtual(), s.getPrazoSLA().format(formatter));
        }
    }

    private void menuAtualizarStatus() {
        System.out.print("\nDigite o protocolo: ");
        String protocolo = scanner.nextLine();
        System.out.println("Próximos Status possíveis (Simplificado): 1. TRIAGEM, 2. EM_EXECUCAO, 3. RESOLVIDO, 4. ENCERRADO");
        int stOp = Integer.parseInt(scanner.nextLine());
        StatusSolicitacao novo = switch (stOp) {
            case 1 -> StatusSolicitacao.TRIAGEM;
            case 2 -> StatusSolicitacao.EM_EXECUCAO;
            case 3 -> StatusSolicitacao.RESOLVIDO;
            default -> StatusSolicitacao.ENCERRADO;
        };

        System.out.print("Comentário obrigatório: ");
        String comentario = scanner.nextLine();

        servico.atualizarStatus(protocolo, novo, comentario, servidorExemplo);
        System.out.println("✓ Status atualizado!");
    }

    private void menuRelatorios() {
        System.out.println("\n--- Relatórios do Sistema ---");
        System.out.println("1. Quantidade por Prioridade");
        System.out.println("2. Quantidade por Categoria");
        System.out.print("Escolha uma opção de relatório: ");
        
        int relOp = Integer.parseInt(scanner.nextLine());
        if (relOp == 1) {
            Map<Prioridade, Long> relatorio = servico.gerarRelatorioPorPrioridade(servidorExemplo);
            System.out.println("\n--- Solicitações por Prioridade ---");
            relatorio.forEach((prio, qtd) -> System.out.println(prio + ": " + qtd));
        } else if (relOp == 2) {
            Map<CategoriaSossego, Long> relatorio = servico.gerarRelatorioPorCategoria(servidorExemplo);
            System.out.println("\n--- Solicitações por Categoria ---");
            relatorio.forEach((cat, qtd) -> System.out.println(cat + ": " + qtd));
        } else {
            System.out.println("Opção inválida!");
        }
    }

    private void exibirInstrucoesCategoria(CategoriaSossego categoria) {
        System.out.println("\nInstruções para a categoria selecionada:");
        switch (categoria) {
            case VEICULO_SOM_ALTO -> {
                System.out.println("- Informe onde está o veículo.");
                System.out.println("- Descreva se o som está parado ou em movimento e há quanto tempo está alto.");
            }
            case FESTA_RESIDENCIAL -> {
                System.out.println("- Informe o endereço da residência.");
                System.out.println("- Descreva o tipo de som e o horário em que começou.");
            }
            case OBRAS_IRREGULARES -> {
                System.out.println("- Informe o local da obra.");
                System.out.println("- Descreva o tipo de barulho e o horário em que ocorre.");
            }
            case BAR_CASA_NOTURNA -> {
                System.out.println("- Informe o nome ou endereço do local.");
                System.out.println("- Descreva o tipo de som e se acontece com frequência.");
            }
        }
        System.out.println();
    }

    private void exibirDetalhes(Solicitacao s) {
        System.out.println("\n--- Detalhes da Solicitação ---");
        System.out.println("Protocolo: " + s.getProtocolo());
        System.out.println("Status:    " + s.getStatusAtual());
        System.out.println("Prioridade:" + s.getPrioridade());
        System.out.println("Categoria: " + s.getCategoria());
        System.out.println("Endereço:  " + s.getEnderecoLocalizacao());
        System.out.println("Descrição: " + s.getDescricao());
        System.out.println("Criado em: " + s.getDataCriacao().format(formatter));
        System.out.println("Prazo SLA: " + s.getPrazoSLA().format(formatter));
        
        if (!s.getHistorico().isEmpty()) {
            System.out.println("\nHistórico de Alterações:");
            for (HistoricoStatus h : s.getHistorico()) {
                System.out.println(" - " + h.toString());
            }
        }
    }
}
