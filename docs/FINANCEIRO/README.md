# Módulo FINANCEIRO — UPSaúde (Documentação Completa)

Esta documentação cobre o **módulo Financeiro** com foco em:

- **Integração Frontend**: ordem correta de chamadas, “o que chamar quando”, exemplos e troubleshooting.
- **Negócio**: competência, saldo por município (tenant), reserva/consumo/estorno, rastreabilidade e auditoria.
- **Técnico**: entidades/tabelas, multi-tenancy, integrações com Assistencial e Faturamento.
- **Catálogo completo de endpoints**: tudo que o módulo expõe via REST (Financeiro + Faturamento relacionado + pontos do Assistencial que disparam eventos financeiros).

## Leitura recomendada (ordem)

- **1) Integração Front**: [INTEGRACAO_FRONT.md](./INTEGRACAO_FRONT.md)
- **1.1) Como o Financeiro roda no Assistencial (Agendamento/Atendimento/Consulta)**: [FINANCEIRO_NO_ASSISTENCIAL.md](./FINANCEIRO_NO_ASSISTENCIAL.md)
- **2) Fluxos e Sequências**: [FLUXOS_E_SEQUENCIAS.md](./FLUXOS_E_SEQUENCIAS.md)
- **3) Regras de Negócio**: [NEGOCIO.md](./NEGOCIO.md)
- **4) Técnico (arquitetura + dados)**: [TECNICO.md](./TECNICO.md) e [DADOS_E_STATUS.md](./DADOS_E_STATUS.md)
- **5) Endpoints (catálogo completo)**: [ENDPOINTS.md](./ENDPOINTS.md)
  - **Dependências úteis** (pacientes, sigtap, etc.): [ENDPOINTS_DEPENDENCIAS_PARA_FINANCEIRO.md](./ENDPOINTS_DEPENDENCIAS_PARA_FINANCEIRO.md)

## Pré-requisitos (para qualquer chamada)

- **Base URL**: por padrão o backend sobe com `server.servlet.context-path=/api`
  - `http://localhost:8080/api`
- **Autenticação**: `Authorization: Bearer <TOKEN>`
- **Município = Tenant**
  - O tenant é resolvido no backend via usuário autenticado (`UsuariosSistema -> tenant`).
  - Se o usuário não tiver `UsuariosSistema` ativo, a API retorna **403**.

## Início rápido (end-to-end)

### 1) Criar competência financeira

```bash
curl -X POST "http://localhost:8080/api/v1/financeiro/competencias" \
  -H "Authorization: Bearer <TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{
    "codigo": "2026-01",
    "tipo": "MENSAL",
    "dataInicio": "2026-01-01",
    "dataFim": "2026-01-31",
    "descricao": "Competência janeiro/2026"
  }'
```

### 2) Criar orçamento da competência (por tenant/município)

```bash
curl -X POST "http://localhost:8080/api/v1/financeiro/orcamentos-competencia" \
  -H "Authorization: Bearer <TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{
    "competencia": "<UUID_COMPETENCIA>",
    "saldoAnterior": 0,
    "creditos": 0,
    "reservasAtivas": 0,
    "consumos": 0,
    "estornos": 0,
    "despesasAdmin": 0,
    "saldoFinal": 0
  }'
```

### 3) Lançar crédito orçamentário

```bash
curl -X POST "http://localhost:8080/api/v1/financeiro/creditos-orcamentarios" \
  -H "Authorization: Bearer <TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{
    "competencia": "<UUID_COMPETENCIA>",
    "valor": 10000,
    "fonte": "RECURSO_PUBLICO",
    "documentoReferencia": "Empenho 123/2026",
    "dataCredito": "2026-01-02"
  }'
```

### 4) Confirmar agendamento (dispara reserva automática)

```bash
curl -X POST "http://localhost:8080/api/v1/agendamentos" \
  -H "Authorization: Bearer <TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{
    "paciente": "<UUID_PACIENTE>",
    "dataHora": "2026-01-10T10:00:00-03:00",
    "status": "CONFIRMADO",
    "competenciaFinanceira": "<UUID_COMPETENCIA>",
    "valorEstimadoTotal": 120.50
  }'
```

O backend tenta criar uma **Reserva Orçamentária Assistencial** e marca o agendamento com `statusFinanceiro = RESERVADO`.

## Visão geral do que é “relacionado ao Financeiro”

- **Financeiro (REST)**: `/v1/financeiro/*` (CRUD + operações explícitas)
- **Faturamento (relacionado)**: `/v1/faturamento/*`
- **Assistencial (gatilhos financeiros)**:
  - `Agendamentos`: `/v1/agendamentos` (CONFIRMADO/CANCELADO/FALTA/REAGENDADO)
  - `Atendimentos`: `/v1/atendimentos` (ENCERRAR/CONCLUIR/CANCELAR/FALTA_PACIENTE)


