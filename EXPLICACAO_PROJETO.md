# Documentação do Projeto: Observação de Sossego CLI

Este documento explica detalhadamente o funcionamento do sistema de **Observação de Sossego**, desde a sua estrutura de pastas até as regras de negócio aplicadas.

---

## 1. Visão Geral da Arquitetura
O projeto segue os princípios da **Arquitetura Limpa (Clean Architecture)** e **SOLID**, dividindo as responsabilidades em camadas bem definidas para facilitar a manutenção e evolução (como a futura migração para um banco de dados real).

### Estrutura de Pastas
- `model/`: Contém as entidades de domínio (classes que representam o mundo real) e Enums.
- `repository/`: Camada de persistência (armazenamento de dados).
- `service/`: Camada de lógica de negócio (onde as regras são aplicadas).
- `cli/`: Camada de interface de usuário (terminal).
- `Main.java`: Ponto de entrada que orquestra a criação de todos os componentes.

---

## 2. O Fluxo de Dados (Cabo a Rabo)

### Passo 1: Inicialização (`Main.java`)
Quando você executa o programa, a classe `Main` faz a "Injeção de Dependências" manual:
1. Cria o **Repositório** (onde as solicitações ficam guardadas).
2. Cria a **Calculadora de SLA** (regra de prazos).
3. Cria o **Serviço** (passando o repositório e a calculadora para ele).
4. Inicia o **Menu Interativo**.

### Passo 2: Criação de Solicitação
1. O usuário escolhe a opção "Criar Nova Solicitação" no `MenuInterativo`.
2. O Menu chama `servico.criarSolicitacao(...)`.
3. **No Serviço (`ServicoSolicitacoes`):**
   - **Validação de Abuso:** O sistema verifica se a descrição tem pelo menos 10 caracteres e o endereço 5. Se não tiver, lança um erro.
   - **Geração de Protocolo:** Cria um código único de 8 caracteres (ex: `A1B2C3D4`).
   - **Definição de Prioridade:** Baseado na categoria (ex: Som Automotivo = URGENTE).
   - **Cálculo de Prazo (SLA):** A calculadora define a data limite para resolução.
   - **Persistência:** O objeto `Solicitacao` é salvo na `FilaAtendimentoMemoria`.

### Passo 3: Gestão pelo Servidor
Um servidor (agente público) pode listar e atualizar solicitações:
1. **Listagem Ativa:** O sistema retorna todas as solicitações (exceto as encerradas) ordenadas primeiro por **Prioridade** (mais urgentes primeiro) e depois pelo **Prazo SLA** mais curto.
2. **Atualização de Status:**
   - O servidor deve fornecer o protocolo, o novo status e um **comentário obrigatório**.
   - **Regra de Transição Lógica:** O sistema impede saltos impossíveis (ex: de `ABERTO` direto para `ENCERRADO`). Deve seguir o fluxo: `ABERTO` -> `TRIAGEM` -> `EM_EXECUCAO` -> `RESOLVIDO` -> `ENCERRADO`.
   - **Histórico:** Cada mudança gera um `HistoricoStatus` que registra quem mudou, quando mudou, o que mudou e o porquê (comentário).

---

## 3. Detalhes das Classes Principais

### Model (Domínio)
- **`Usuario`**: Classe base com ID, Nome, CPF e Contato.
  - **`Cidadao`**: Herda de Usuario.
  - **`Servidor`**: Herda de Usuario e adiciona Matrícula e Cargo.
- **`Solicitacao`**: A entidade central. Guarda todas as informações da denúncia, incluindo uma lista de `HistoricoStatus`.
- **`HistoricoStatus`**: Um registro imutável de uma mudança de estado na solicitação.

### Service (Regras de Negócio)
- **`ServicoSolicitacoes`**: É o "cérebro". Ele não sabe *como* os dados são salvos (se é em memória ou banco), ele apenas sabe *quais* regras aplicar aos dados.
- **`CalculadoraSLAPadrao`**: Define os prazos. Atualmente:
  - 24h para Veículo com Som Alto.
  - 48h para Bar/Casa Noturna.
  - 72h para Festas Residenciais.
  - 120h (5 dias) para Obras e outros.

### Repository (Dados)
- **`IRepositorioSolicitacoes`**: Uma interface que define os métodos `salvar`, `buscarPorProtocolo` e `listarTodas`.
- **`FilaAtendimentoMemoria`**: Implementação atual que usa um `ArrayList` para guardar os dados enquanto o programa estiver rodando.

---

## 4. Por que o código está assim? (SOLID)
- **S (Responsabilidade Única):** O Menu só cuida de ler/escrever no terminal. O Serviço só cuida das regras. O Repositório só cuida de guardar dados.
- **O (Aberto/Fechado):** Se você quiser mudar como o SLA é calculado, basta criar uma nova classe que implementa `ICalculadoraSLA` sem mexer no código do Serviço.
- **D (Inversão de Dependência):** O Serviço depende da interface `IRepositorioSolicitacoes`, não da classe concreta de memória. Isso permite trocar o armazenamento por um Banco de Dados SQL no futuro apenas mudando uma linha na `Main`.

---

## 5. Como Testar
1. Inicie o programa via `Main`.
2. Crie uma solicitação (opção 1). Anote o protocolo gerado.
3. Tente atualizar o status (opção 4) para `TRIAGEM`.
4. Tente atualizar o status para `ENCERRADO` logo em seguida (o sistema deve bloquear, pois precisa passar por `EM_EXECUCAO` e `RESOLVIDO` antes).
5. Consulte o protocolo (opção 2) para ver o histórico de alterações detalhado.
