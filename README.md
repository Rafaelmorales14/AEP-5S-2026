# SOSsego — Sistema CLI de Denúncias de Perturbação do Sossego

_AEP 2026.1 — Projeto Acadêmico | ODS 16 — Paz, Justiça e Instituições Eficazes_

## Sobre o Projeto
O SOSsego é uma aplicação de terminal (CLI) em Java para registrar e acompanhar denúncias de perturbação do sossego, como som automotivo alto, festas residenciais, bares/casas noturnas e obras fora do horário permitido. Cada denúncia recebe um protocolo único que permite consulta, atualização de status por atendentes e rastreabilidade por histórico de movimentações com comentários.

O sistema foi desenhado para funcionar sem frameworks, com foco em orientação a objetos e arquitetura em camadas, atendendo aos requisitos acadêmicos da AEP 2026.1.

## Perfis do Sistema
| Perfil                               | Descrição                                                                                                                         |
|--------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------|
| Cidadão Incomodado                   | Usuários que precisam relatar barulho de forma simples, com poucas etapas e linguagem direta (ex.: vizinho incomodado com festa). |
| Fiscalizador (PM, GM ou Fiscal)      | São os agentes que estão trabalhando nas ruas, lidando diretamente com as ocorrências e conflitos.                                |
| Funcionário público (Administrativo) | Servidor responsável por triagem, priorização conforme categoria/SLA, atualização de status e produção de relatórios.             |

## Funcionalidades
- Registro de denúncia com categoria, descrição e localização (endereço da ocorrência)
- Opção de denúncia anônima (sem vínculo a dados pessoais do cidadão)
- Geração de protocolo único para consulta posterior
- Consulta de denúncia por protocolo
- Listagem de denúncias ativas, ordenadas por prioridade e prazo de SLA
- Atualização de status com comentários obrigatórios e histórico completo
- Relatórios de quantidade por Prioridade e por Categoria

## Categorias e Prazos (SLA)
Os prazos são calculados automaticamente conforme a categoria da denúncia.
- Veículo com som alto: 2 horas
- Bar/Casa noturna: 4 horas
- Festa residencial: 6 horas
- Obras irregulares: 2 dias

## Fluxo de Status
O atendimento segue um fluxo controlado, permitindo apenas transições válidas:

```
ABERTO -> TRIAGEM -> EM_EXECUCAO -> RESOLVIDO -> ENCERRADO
```

## Regras Importantes
- Denúncias anônimas não armazenam dados pessoais do denunciante.
- Comentários são obrigatórios ao atualizar o status (para garantir transparência do atendimento).
- Transições de status inválidas são bloqueadas pelo serviço de regras de negócio.

## Estrutura do Projeto
A árvore abaixo reflete os principais componentes do código-fonte.

```
src/main/java/org/example/
├─ Main.java                      (ponto de entrada da aplicação)
├─ cli/
│  └─ MenuInterativo.java          (menus e interação via terminal)
├─ model/
│  ├─ Cidadao.java                 (dados do cidadão autenticado)
│  ├─ Servidor.java                (dados do servidor/atendente)
│  ├─ Solicitacao.java             (entidade principal da denúncia)
│  ├─ HistoricoStatus.java         (registro imutável de mudanças de status)
│  ├─ Usuario.java                 (classe base de usuários)
│  └─ enums/
│     ├─ CategoriaSossego.java      (categorias de perturbação do sossego)
│     ├─ Prioridade.java            (níveis: URGENTE, ALTA, MEDIA, BAIXA)
│     └─ StatusSolicitacao.java     (ABERTO, TRIAGEM, EM_EXECUCAO, RESOLVIDO, ENCERRADO)
├─ repository/
│  ├─ IRepositorioSolicitacoes.java (contrato do repositório)
│  └─ FilaAtendimentoMemoria.java   (implementação em memória)
└─ service/
   ├─ ICalculadoraSLA.java          (contrato para cálculo de SLA)
   ├─ CalculadoraSLAPadrao.java     (regras de prazo por categoria)
   └─ ServicoSolicitacoes.java      (regras de negócio e validações)
```

## Como Executar
### Pré-requisitos
- Java 17 ou superior instalado
- Terminal (CMD, PowerShell, Bash ou similar)
- Maven instalado (para o fluxo via Maven)

### Opção A — Executar via Maven
1) Compilar e empacotar

```sh
mvn -q -DskipTests package
```

2) Executar a aplicação

```sh
java -cp target/classes org.example.Main
```

### Opção B — Compilar manualmente (sem Maven)
1) Ir até a pasta src/main/java

```sh
cd src/main/java
```

2) Compilar

```sh
javac -encoding UTF-8 -d ../../../target/classes org/example/Main.java
```

## Uso (CLI)
No menu principal, utilize números para navegar:
1. Criar Nova Solicitação: escolha a categoria, descreva o problema, informe endereço e opte por anonimato.
2. Consultar por Protocolo: informe o código gerado (8 caracteres alfanuméricos, ex.: ABCD1234).
3. Listar Solicitações Ativas (Servidor): exibe ordenado por prioridade e prazo de SLA.
4. Atualizar Status (Servidor): exige comentário obrigatório; o sistema valida as transições permitidas.
5. Relatórios (Servidor): consolida por Prioridade ou por Categoria.

## Arquitetura (POO)
O projeto segue camadas claras e responsabilidades bem definidas:
- CLI (MenuInterativo) -> Orquestra a interação com o usuário
- Service (ServicoSolicitacoes, CalculadoraSLAPadrao) -> Regras de negócio, validações e cálculo de prazos
- Repository (IRepositorioSolicitacoes, FilaAtendimentoMemoria) -> Persistência em memória e consultas
- Model (Solicitacao, HistoricoStatus, Usuario e derivados) -> Entidades e registros de histórico
- Enums (CategoriaSossego, Prioridade, StatusSolicitacao) -> Vocabulário de domínio

## Como a Prioridade é definida
A prioridade das denúncias é atribuída conforme a categoria:
- Veículo com som alto -> URGENTE
- Bar/Casa noturna -> ALTA
- Festa residencial -> MEDIA
- Obras irregulares -> BAIXA

## Geração de Protocolo
Cada solicitação recebe um identificador curto (8 caracteres alfanuméricos em maiúsculas) usado para consultas no sistema.

## ODS 16 — Paz, Justiça e Instituições Eficazes
O SOSsego contribui com o ODS 16 ao:
- Prover um canal acessível e anonimizado para denúncias de perturbação do sossego
- Garantir rastreabilidade por meio de protocolos e histórico de movimentações
- Apoiar a atuação responsiva de órgãos públicos com prazos (SLA) proporcionais à urgência
- Promover transparência: todas as mudanças exigem justificativa (comentários) e ficam registradas

## Licença / Aviso
Projeto acadêmico — AEP 2026.1. Uso para fins educacionais e demonstração de arquitetura em Java sem frameworks.
