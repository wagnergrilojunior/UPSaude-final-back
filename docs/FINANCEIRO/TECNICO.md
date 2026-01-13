# FINANCEIRO — Documentação Técnica

## Links rápidos

- **Catálogo completo de endpoints (por domínio)**: [ENDPOINTS.md](./ENDPOINTS.md)
- **Fluxos e ordem (front)**: [FLUXOS_E_SEQUENCIAS.md](./FLUXOS_E_SEQUENCIAS.md)
- **Campos e status**: [DADOS_E_STATUS.md](./DADOS_E_STATUS.md)

## Arquitetura (visão rápida)

O módulo financeiro foi modelado para suportar:

- **Orçamento por competência e tenant**
- **Reserva/consumo/estorno automáticos** integrados ao assistencial (Agendamento/Atendimento)
- **Lançamentos financeiros** (partidas, plano de contas, centro de custo)
- **Contas financeiras** (caixa/banco), movimentações, transferências
- **Conciliação bancária**
- **Contas a receber/pagar** (títulos, baixas, pagamentos)
- **Auditoria** (logs)

## Multi-tenancy (Município = Tenant)

O tenant é resolvido via autenticação:

- O filtro `JwtAuthenticationFilter` valida `Authorization: Bearer <token>` no Supabase.
- O `TenantService.validarTenantAtual()` busca `UsuariosSistema` pelo `userId` e retorna `UsuariosSistema.tenant.id`.
- Se não encontrar tenant via autenticação, existe um **fallback temporário** para um UUID fixo (apenas para ambiente/legacy).

Implicação para o frontend:

- Não existe header “X-Tenant” padrão no backend atual.
- O front precisa garantir que o usuário logado tenha registro **ativo** em `UsuariosSistema` e tenant associado.

## Context-path e URLs

Por padrão:

- `server.servlet.context-path=/api`
- endpoints expostos em `/api/v1/...`

## Integração com assistencial (modelo híbrido)

### Reserva automática
Implementada em:

- `AgendamentoCreator`: ao criar com status `CONFIRMADO`
- `AgendamentoUpdater`: ao mudar status para `CONFIRMADO`

Requisitos:

- `Agendamento.competenciaFinanceira` deve estar preenchido
- `Agendamento.valorEstimadoTotal` deve ser > 0

### Estorno automático (agendamento)
Implementado em `AgendamentoUpdater` quando status vira:

- `CANCELADO`, `FALTA`, `REAGENDADO`

### Consumo automático (atendimento)
Implementado em `AtendimentoUpdater` quando status do atendimento vira:

- `CONCLUIDO` → consome a reserva
- `CANCELADO` ou `FALTA_PACIENTE` → estorna

### Implementação atual (resumo)
O serviço `FinanceiroIntegrationServiceImpl` faz:

- **Idempotência simples** para reserva: se já existir reserva para o agendamento, não cria outra.
- **Estorno auditável**: não apaga reserva; marca `status = LIBERADA` e registra `EstornoFinanceiro` quando possível.
- **Consumo**: marca reserva `CONSUMIDA` e ajusta `Agendamento.statusFinanceiro`.

> Limitação atual: `fecharCompetencia(competenciaId)` existe, mas está como **placeholder** (valida `competenciaId` e não executa geração BPA/hashes).

## Onde estão os endpoints (mapa rápido)

- **Financeiro**: todos em `/v1/financeiro/*` (CRUD + ações em `/v1/financeiro/operacoes`)
  - referência detalhada: [ENDPOINTS.md](./ENDPOINTS.md)
- **Faturamento (relacionado)**: `/v1/faturamento/*`
  - referência: [ENDPOINTS_FATURAMENTO_RELACIONADO.md](./ENDPOINTS_FATURAMENTO_RELACIONADO.md)
- **Assistencial (gatilhos)**:
  - agendamento: `/v1/agendamentos`
  - atendimento: `/v1/atendimentos`
  - referência: [ENDPOINTS_ASSISTENCIAL_RELACIONADOS.md](./ENDPOINTS_ASSISTENCIAL_RELACIONADOS.md)

## Entidades / tabelas principais

As tabelas foram criadas em `public` (PostgreSQL). Principais:

- `competencia_financeira`
- `competencia_financeira_tenant`
- `orcamento_competencia`
- `credito_orcamentario`
- `reserva_orcamentaria_assistencial`
- `estorno_financeiro`
- `guia_atendimento_ambulatorial`
- `lancamento_financeiro` / `lancamento_financeiro_item`
- `plano_contas` / `conta_contabil` / `centro_custo`
- `conta_financeira` / `movimentacao_conta` / `transferencia_entre_contas`
- `conciliacao_bancaria` / `extrato_bancario_importado` / `conciliacao_item`
- `parte_financeira`
- `titulo_receber` / `baixa_receber`
- `titulo_pagar` / `pagamento_pagar`
- `regra_classificacao_contabil`
- `log_financeiro`

E ajustes em tabelas existentes:

- `agendamentos`: `competencia_financeira_id`, `valor_estimado_total`, `status_financeiro`
- `atendimentos`: `competencia_financeira_id`
- nova: `atendimento_procedimento`

## Auditoria e rastreabilidade

Camadas de rastreabilidade disponíveis:

- **Status financeiro no agendamento** (`statusFinanceiro`)
- **Reserva/Estorno** (`reserva_orcamentaria_assistencial`, `estorno_financeiro`)
- **Log** (`log_financeiro`)

> Recomendações futuras (não obrigatórias para o front): usar `LogFinanceiro` como trilha “quem/quando/o quê” e amarrar correlation-id por request.

## Padrões REST

Para cada entidade do financeiro:

- `POST /...` cria
- `GET /...` lista (paginado)
- `GET /.../{id}` obtém
- `PUT /.../{id}` atualiza
- `DELETE /.../{id}` exclui
- `PUT /.../{id}/inativar` inativa (soft disable)

Endpoints de ação (orquestração) ficam em:

- `POST /v1/financeiro/operacoes/...` (ver [ENDPOINTS.md](./ENDPOINTS.md))

