# FINANCEIRO — Documentação Técnica

## 📚 Links Rápidos

- **Catálogo completo de endpoints (por domínio)**: [ENDPOINTS.md](./ENDPOINTS.md)
- **Fluxos e ordem (front)**: [FLUXOS_E_SEQUENCIAS.md](./FLUXOS_E_SEQUENCIAS.md)
- **Campos e status**: [DADOS_E_STATUS.md](./DADOS_E_STATUS.md)
- **Análise Multi-Tenant**: [ANALISE_ENTIDADES_MULTI_TENANT.md](./ANALISE_ENTIDADES_MULTI_TENANT.md)

## 🏗️ Arquitetura

O módulo financeiro foi modelado para suportar:

- **Orçamento por competência e tenant**
- **Reserva/consumo/estorno automáticos** integrados ao assistencial (Agendamento/Atendimento)
- **Lançamentos financeiros** (partidas, plano de contas, centro de custo)
- **Contas financeiras** (caixa/banco), movimentações, transferências
- **Conciliação bancária**
- **Contas a receber/pagar** (títulos, baixas, pagamentos)
- **Auditoria** (logs)

## 📦 Estrutura de Pacotes

### Camada de Entidades

```
com.upsaude.entity.financeiro
├── BaseEntityFinanceiro (herda BaseEntity com tenant)
├── CompetenciaFinanceira
├── OrcamentoCompetencia
├── CreditoOrcamentario
├── ReservaOrcamentariaAssistencial
├── EstornoFinanceiro
├── LancamentoFinanceiro
├── LancamentoFinanceiroItem
├── ContaFinanceira
├── MovimentacaoConta
├── TransferenciaEntreContas
├── ConciliacaoBancaria
├── ConciliacaoItem
├── ExtratoBancarioImportado
├── TituloReceber
├── TituloPagar
├── BaixaReceber
├── PagamentoPagar
├── RenegociacaoReceber
├── PlanoContas
├── ContaContabil
├── CentroCusto
├── ParteFinanceira
├── GuiaAtendimentoAmbulatorial
├── RecorrenciaFinanceira
├── RegraClassificacaoContabil
└── LogFinanceiro
```

### Camada de Serviços

```
com.upsaude.service.api.financeiro
├── FinanceiroIntegrationService (integração automática)
├── CompetenciaFinanceiraService
├── OrcamentoCompetenciaService
├── CreditoOrcamentarioService
├── ReservaOrcamentariaAssistencialService
├── EstornoFinanceiroService
├── LancamentoFinanceiroService
├── ContaFinanceiraService
├── ConciliacaoBancariaService
├── TituloReceberService
├── TituloPagarService
└── ... (outros serviços)

com.upsaude.service.impl.api.financeiro
└── (Implementações dos serviços acima)
```

### Camada de Controllers

```
com.upsaude.controller.api.financeiro
├── FinanceiroOperacoesController (operações explícitas)
├── CompetenciaFinanceiraController
├── OrcamentoCompetenciaController
├── CreditoOrcamentarioController
├── ReservaOrcamentariaAssistencialController
├── EstornoFinanceiroController
└── ... (outros controllers)
```

### Camada de Repositórios

```
com.upsaude.repository.financeiro
├── CompetenciaFinanceiraRepository
├── OrcamentoCompetenciaRepository
├── CreditoOrcamentarioRepository
├── ReservaOrcamentariaAssistencialRepository
└── ... (outros repositórios)
```

## 🔐 Multi-Tenancy (Município = Tenant)

### Resolução de Tenant

O tenant é resolvido via autenticação:

1. O filtro `JwtAuthenticationFilter` valida `Authorization: Bearer <token>` no Supabase
2. O `TenantService.validarTenantAtual()` busca `UsuariosSistema` pelo `userId` e retorna `UsuariosSistema.tenant.id`
3. Se não encontrar tenant via autenticação, existe um **fallback temporário** para um UUID fixo (apenas para ambiente/legacy)

### Implicações para o Frontend

- Não existe header "X-Tenant" padrão no backend atual
- O front precisa garantir que o usuário logado tenha registro **ativo** em `UsuariosSistema` e tenant associado
- Se o usuário não tiver tenant válido, a API retorna **403 Forbidden**

### Base Entity

Todas as entidades financeiras (exceto `CompetenciaFinanceira` que ainda está em migração) herdam de `BaseEntityFinanceiro`, que por sua vez herda de `BaseEntity` com suporte a tenant.

## 🌐 Context-Path e URLs

Por padrão:

- `server.servlet.context-path=/api`
- Endpoints expostos em `/api/v1/...`
- Base URL completa: `http://localhost:8080/api/v1/financeiro/...`

## 🔄 Integração com Assistencial (Modelo Híbrido)

### Reserva Automática

**Implementação**:
- `AgendamentoCreator`: ao criar com status `CONFIRMADO`
- `AgendamentoUpdater`: ao mudar status para `CONFIRMADO`

**Requisitos**:
- `Agendamento.competenciaFinanceira` deve estar preenchido
- `Agendamento.valorEstimadoTotal` deve ser > 0

**Fluxo**:
1. Backend detecta status `CONFIRMADO`
2. Chama `FinanceiroIntegrationService.reservarOrcamento(agendamentoId)`
3. Valida idempotência (não cria reserva duplicada)
4. Cria `ReservaOrcamentariaAssistencial` com status `ATIVA`
5. Atualiza `Agendamento.statusFinanceiro = RESERVADO`

### Estorno Automático (Agendamento)

**Implementação**: `AgendamentoUpdater` quando status vira:
- `CANCELADO`
- `FALTA`
- `REAGENDADO`

**Fluxo**:
1. Backend detecta mudança de status
2. Chama `FinanceiroIntegrationService.estornarReserva(agendamentoId, motivo)`
3. Marca reserva como `LIBERADA` (não apaga para auditoria)
4. Cria `EstornoFinanceiro` quando possível
5. Atualiza `Agendamento.statusFinanceiro = ESTORNADO`

### Consumo Automático (Atendimento)

**Implementação**: `AtendimentoUpdater` quando status do atendimento vira:
- `CONCLUIDO` → consome a reserva
- `CANCELADO` ou `FALTA_PACIENTE` → estorna

**Fluxo de Consumo**:
1. Backend detecta status `CONCLUIDO`
2. Localiza `Agendamento` vinculado ao `Atendimento`
3. Chama `FinanceiroIntegrationService.consumirReserva(atendimentoId)`
4. Marca `ReservaOrcamentariaAssistencial.status = CONSUMIDA`
5. Atualiza `Agendamento.statusFinanceiro = CONSUMIDO`

### Implementação Atual (Resumo)

O serviço `FinanceiroIntegrationServiceImpl` implementa:

- **Idempotência simples** para reserva: se já existir reserva para o agendamento, não cria outra
- **Estorno auditável**: não apaga reserva; marca `status = LIBERADA` e registra `EstornoFinanceiro` quando possível
- **Consumo**: marca reserva `CONSUMIDA` e ajusta `Agendamento.statusFinanceiro`

> **Limitação atual**: `fecharCompetencia(competenciaId)` existe, mas está como **placeholder** (valida `competenciaId` e não executa geração BPA/hashes).

## 📍 Mapa de Endpoints

### Financeiro (Core)

- **Base Path**: `/v1/financeiro/*`
- **Operações Especiais**: `/v1/financeiro/operacoes/*`
- **Referência detalhada**: [ENDPOINTS.md](./ENDPOINTS.md)

### Faturamento (Relacionado)

- **Base Path**: `/v1/faturamento/*`
- **Referência**: [ENDPOINTS_FATURAMENTO_RELACIONADO.md](./ENDPOINTS_FATURAMENTO_RELACIONADO.md)

### Assistencial (Gatilhos)

- **Agendamentos**: `/v1/agendamentos`
- **Atendimentos**: `/v1/atendimentos`
- **Referência**: [ENDPOINTS_ASSISTENCIAL_RELACIONADOS.md](./ENDPOINTS_ASSISTENCIAL_RELACIONADOS.md)

## 🗄️ Entidades / Tabelas Principais

As tabelas foram criadas em `public` (PostgreSQL). Principais:

### Orçamento e Competência

- `competencia_financeira` - Períodos de referência
- `competencia_financeira_tenant` - Status da competência por tenant
- `orcamento_competencia` - Saldo do município por competência
- `credito_orcamentario` - Créditos adicionados ao orçamento

### Assistencial Financeiro

- `reserva_orcamentaria_assistencial` - Reservas de saldo
- `estorno_financeiro` - Estornos registrados
- `guia_atendimento_ambulatorial` - Guias ambulatoriais

### Lançamentos Contábeis

- `lancamento_financeiro` - Lançamentos (cabeçalho)
- `lancamento_financeiro_item` - Itens do lançamento (partidas)
- `plano_contas` - Plano de contas
- `conta_contabil` - Contas contábeis
- `centro_custo` - Centros de custo
- `parte_financeira` - Partes financeiras (devedor/credor)

### Contas e Conciliação

- `conta_financeira` - Contas bancárias e caixa
- `movimentacao_conta` - Movimentações nas contas
- `transferencia_entre_contas` - Transferências
- `conciliacao_bancaria` - Conciliações
- `extrato_bancario_importado` - Extratos importados
- `conciliacao_item` - Itens de conciliação

### Contas a Receber/Pagar

- `titulo_receber` - Títulos a receber
- `baixa_receber` - Baixas de receber
- `titulo_pagar` - Títulos a pagar
- `pagamento_pagar` - Pagamentos
- `renegociacao_receber` - Renegociações

### Parametrização e Auditoria

- `regra_classificacao_contabil` - Regras de classificação
- `recorrencia_financeira` - Recorrências
- `log_financeiro` - Logs de auditoria

### Ajustes em Tabelas Existentes

- `agendamentos`: 
  - `competencia_financeira_id` (FK)
  - `valor_estimado_total` (DECIMAL)
  - `status_financeiro` (VARCHAR)
- `atendimentos`: 
  - `competencia_financeira_id` (FK)
- Nova tabela: `atendimento_procedimento`

## 🔍 Auditoria e Rastreabilidade

### Camadas de Rastreabilidade

1. **Status Financeiro no Agendamento** (`statusFinanceiro`)
   - Valores: `SEM_RESERVA`, `RESERVADO`, `CONSUMIDO`, `ESTORNADO`, `AJUSTADO`

2. **Reserva/Estorno** 
   - `reserva_orcamentaria_assistencial` - Histórico de reservas
   - `estorno_financeiro` - Registro de estornos com motivo

3. **Log Financeiro** (`log_financeiro`)
   - Trilha completa de "quem/quando/o quê"
   - Recomendação: amarrar correlation-id por request

### Campos de Auditoria Padrão

Todas as entidades herdam de `BaseEntity` que possui:
- `id` (UUID)
- `createdAt` (OffsetDateTime)
- `updatedAt` (OffsetDateTime)
- `active` (Boolean)
- `tenant` (Tenant - para multi-tenancy)

## 📋 Padrões REST

### CRUD Padrão

Para cada entidade do financeiro:

- `POST /...` - Cria
- `GET /...` - Lista (paginado)
- `GET /.../{id}` - Obtém por ID
- `PUT /.../{id}` - Atualiza
- `DELETE /.../{id}` - Exclui (soft delete)
- `PUT /.../{id}/inativar` - Inativa (soft disable)

### Paginação

Endpoints de listagem aceitam:
- `page` (int, default: 0)
- `size` (int, default: 20)
- `sort` (string, ex: "createdAt,desc")

### Endpoints de Ação

Endpoints de orquestração/operações especiais ficam em:
- `POST /v1/financeiro/operacoes/...`

Ver [ENDPOINTS_FINANCEIRO_01_OPERACOES.md](./ENDPOINTS_FINANCEIRO_01_OPERACOES.md) para detalhes.

## 🔧 Tecnologias e Dependências

### Stack Principal

- **Java 17+**
- **Spring Boot 3.x**
- **JPA/Hibernate** - Persistência
- **PostgreSQL** - Banco de dados
- **MapStruct** - Mapeamento DTO ↔ Entity
- **Lombok** - Redução de boilerplate
- **Bean Validation** - Validações

### Padrões de Código

- **Arquitetura em Camadas**: Controller → Service → Repository
- **DTOs**: Request/Response separados das entidades
- **Mappers**: MapStruct para conversão
- **Services de Suporte**: Creator, Updater, ResponseBuilder, TenantEnforcer
- **Validações**: Bean Validation nos DTOs
- **Exceções Customizadas**: Para regras de negócio

## 🚨 Limitações Conhecidas

1. **Fechamento de Competência**: `fecharCompetencia()` está como placeholder
2. **CompetenciaFinanceira**: Ainda em migração para multi-tenant (ver [ANALISE_ENTIDADES_MULTI_TENANT.md](./ANALISE_ENTIDADES_MULTI_TENANT.md))
3. **BPA Completo**: Geração de arquivo BPA e fechamento com hash/snapshot ainda em desenvolvimento

## 📖 Referências

- [README Principal](./README.md)
- [Documentação de Negócio](./NEGOCIO.md)
- [Análise Multi-Tenant](./ANALISE_ENTIDADES_MULTI_TENANT.md)
- [Catálogo de Endpoints](./ENDPOINTS.md)
