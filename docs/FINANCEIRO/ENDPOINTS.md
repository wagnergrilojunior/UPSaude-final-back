# FINANCEIRO — Catálogo Completo de Endpoints (por domínio)

Este arquivo virou um **índice** do catálogo completo. Cada domínio tem um arquivo próprio com:

- endpoints (paths e métodos)
- exemplos de request/response
- erros comuns e observações para integração do front

## Convenções globais

- **Base URL**: `http://localhost:8080/api`
- **Autenticação**: `Authorization: Bearer <TOKEN>`
- **Paginação**: `GET` de listagem aceita `page`, `size`, `sort`
- **CRUD padrão**: todos os controllers do módulo seguem:
  - `POST /...`
  - `GET /...`
  - `GET /.../{id}`
  - `PUT /.../{id}`
  - `DELETE /.../{id}`
  - `PUT /.../{id}/inativar` (soft disable)

## Financeiro

- **Operações (ação explícita)**: [ENDPOINTS_FINANCEIRO_01_OPERACOES.md](./ENDPOINTS_FINANCEIRO_01_OPERACOES.md)
- **Core (competências e competência-tenant)**: [ENDPOINTS_FINANCEIRO_02_CORE.md](./ENDPOINTS_FINANCEIRO_02_CORE.md)
- **Orçamento e créditos**: [ENDPOINTS_FINANCEIRO_03_ORCAMENTO.md](./ENDPOINTS_FINANCEIRO_03_ORCAMENTO.md)
- **Assistencial financeiro (reservas/estornos/guia)**: [ENDPOINTS_FINANCEIRO_04_ASSISTENCIAL.md](./ENDPOINTS_FINANCEIRO_04_ASSISTENCIAL.md)
- **Lançamentos (lançamento + itens)**: [ENDPOINTS_FINANCEIRO_05_LANCAMENTOS.md](./ENDPOINTS_FINANCEIRO_05_LANCAMENTOS.md)
- **Contas a receber / pagar (AR/AP)**: [ENDPOINTS_FINANCEIRO_06_AR_AP.md](./ENDPOINTS_FINANCEIRO_06_AR_AP.md)
- **Contas, movimentações e conciliação**: [ENDPOINTS_FINANCEIRO_07_CONTAS_CONCILIACAO.md](./ENDPOINTS_FINANCEIRO_07_CONTAS_CONCILIACAO.md)
- **Parametrização contábil e auditoria**: [ENDPOINTS_FINANCEIRO_08_PARAMETRIZACAO_AUDITORIA.md](./ENDPOINTS_FINANCEIRO_08_PARAMETRIZACAO_AUDITORIA.md)

## Faturamento (relacionado ao financeiro)

- **Faturamento**: [ENDPOINTS_FATURAMENTO_RELACIONADO.md](./ENDPOINTS_FATURAMENTO_RELACIONADO.md)

## Assistencial (gatilhos financeiros)

- **Agendamentos e Atendimentos**: [ENDPOINTS_ASSISTENCIAL_RELACIONADOS.md](./ENDPOINTS_ASSISTENCIAL_RELACIONADOS.md)

## Dependências (outros módulos usados pelo Financeiro)

- **Pacientes / Estabelecimentos / Convênios / SIGTAP**: [ENDPOINTS_DEPENDENCIAS_PARA_FINANCEIRO.md](./ENDPOINTS_DEPENDENCIAS_PARA_FINANCEIRO.md)

