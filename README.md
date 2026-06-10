# SOSsego — API de Denúncias de Perturbação do Sossego

_AEP 2026.1 — Projeto Acadêmico | ODS 16 — Paz, Justiça e Instituições Eficazes_

## Sobre o Projeto
O SOSsego evoluiu de um sistema CLI para uma **API REST robusta em Java com Spring Boot**. O objetivo permanece o mesmo: registrar e acompanhar denúncias de perturbação do sossego (som alto, festas, obras, etc.). 

Agora, o sistema conta com persistência em banco de dados relacional, autenticação JWT, perfis de acesso diferenciados e uma arquitetura moderna preparada para integração com front-ends. Cada denúncia gera um protocolo único para rastreabilidade e histórico de movimentações.

## Perfis do Sistema e Acesso
O sistema agora utiliza autenticação via Token JWT. O `DatabaseSeeder` pré-carrega usuários para teste:

| Perfil | Descrição | Credenciais (Login/Senha) |
| :--- | :--- | :--- |
| **Cidadão** | Relata ocorrências e acompanha seus protocolos. | `cidadao@sossego.com` / `cidadao123` |
| **Servidor Público** | Atua na triagem, execução e resolução das denúncias. | `servidor@sossego.com` / `servidor123` |
| **Administrador** | Gestão global do sistema e relatórios. | `admin@sossego.com` / `admin123` |

## Funcionalidades Principais (Endpoints)
- **Autenticação:** Login e Registro com geração de Token JWT.
- **Solicitações:** Criação de denúncias (anônimas ou identificadas) e consulta por protocolo.
- **Fluxo de Atendimento:** Atualização de status com comentários obrigatórios.
- **Histórico:** Rastreabilidade completa de todas as mudanças de status de uma denúncia.
- **Relatórios:** Consolidação de dados por Prioridade e Categoria para gestão pública.

## Regras de Negócio e SLA
Os prazos (SLA) e prioridades são definidos automaticamente pela categoria:

| Categoria | Prioridade | SLA (Prazo) |
| :--- | :--- | :--- |
| Veículo com som alto | **URGENTE** | 2 horas |
| Bar / Casa Noturna | **ALTA** | 4 horas |
| Festa Residencial | **MÉDIA** | 6 horas |
| Obras Irregulares | **BAIXA** | 2 dias |

### Fluxo de Status
O ciclo de vida de uma denúncia segue a lógica:
`ABERTO` -> `TRIAGEM` -> `EM_EXECUCAO` -> `RESOLVIDO` -> `ENCERRADO`

## Estrutura do Projeto (Arquitetura)
O projeto segue o padrão MVC/Service Layer do Spring Boot:

```
src/main/java/aep/SOSsego/
├─ auth/                (Segurança, JWT, Filtros e AuthController)
├─ config/              (Configurações e DatabaseSeeder)
├─ controllers/         (Exposição dos Endpoints REST)
├─ dtos/                (Objetos de transferência de dados / Payloads)
├─ enums/               (Domínios fixos: Status, Categoria, Role)
├─ models/              (Entidades JPA / Tabelas do Banco)
├─ repositories/        (Interfaces de acesso ao banco Spring Data JPA)
└─ services/            (Regras de negócio, cálculos de SLA e validações)
```

## Como Executar

### Pré-requisitos
- **Java 17** ou superior
- **Docker e Docker Compose**
- **Maven** (opcional, pode usar o wrapper `./mvnw`)

### Passo 1: Subir o Banco de Dados
O projeto utiliza PostgreSQL. Utilize o Docker Compose para subir a instância configurada:

```sh
docker-compose up -d
```

### Passo 2: Executar a Aplicação
Com o banco rodando, inicie o Spring Boot:

```sh
# Via Maven
mvn spring-boot:run

# Ou via Wrapper (Linux/macOS)
./mvnw spring-boot:run

# Ou via Wrapper (Windows)
.\mvnw.cmd spring-boot:run
```

A API estará disponível em `http://localhost:8080`.

## ODS 16 — Paz, Justiça e Instituições Eficazes
O SOSsego contribui com o ODS 16 ao:
- Prover um canal digital acessível e seguro para denúncias.
- Garantir transparência total com histórico imutável de movimentações.
- Apoiar instituições públicas com dados e métricas de atendimento.
- Promover a ordem pública através de uma gestão eficiente de conflitos de vizinhança.

## Licença / Aviso
Projeto acadêmico — AEP 2026.1. Demonstração de arquitetura Spring Boot, Segurança e Persistência.
