# FINANCEIRO — Guia de Integração para o Frontend

## Objetivo deste guia

Este documento é para o time de frontend integrar corretamente com o Financeiro, sem “chutar” a ordem e sem criar efeitos colaterais (reserva duplicada, estorno incorreto, etc.).

> Resumo: o financeiro é **automático** no fluxo assistencial, desde que o front preencha competência e valor e execute transições de status corretas.

## Links rápidos (para não se perder)

- **Fluxos e ordem sugerida**: [FLUXOS_E_SEQUENCIAS.md](./FLUXOS_E_SEQUENCIAS.md)
- **Dados e status** (o que renderizar): [DADOS_E_STATUS.md](./DADOS_E_STATUS.md)
- **Catálogo de endpoints (por domínio)**: [ENDPOINTS.md](./ENDPOINTS.md)

---

## 1) Pré-requisitos (antes de integrar)

## 1.1 Base URL

O backend usa `context-path=/api`, então:

- `http://localhost:8080/api/v1/...`

## 1.2 Autenticação

Todas as chamadas exigem:

- `Authorization: Bearer <TOKEN>`

Se o usuário não tiver `UsuariosSistema` ativo, o backend responde **403**.

## 1.3 Tenant/Município

O tenant é resolvido pelo backend com base no usuário autenticado:

- `UsuariosSistema -> tenant`

Não existe um header padrão “X-Tenant” no modelo atual.

---

## 2) Ordem recomendada (configuração do financeiro)

Esta ordem é a mais simples para um ambiente novo:

1) Criar **Competência Financeira** (`/v1/financeiro/competencias`)
2) Criar **Orçamento por Competência** do município (tenant) (`/v1/financeiro/orcamentos-competencia`)
3) Criar **Créditos Orçamentários** (`/v1/financeiro/creditos-orcamentarios`)
4) (Opcional) Cadastrar **Plano de Contas**, **Centro de Custo**, etc.

> Dica de UX: na UI, trate isso como “setup do município” com checklist.

---

## 3) Ordem recomendada (fluxo do dia a dia)

## 3.1 Criar agendamento com competência e valor

Endpoint:

- `POST /v1/agendamentos`

Campos financeiros relevantes no request (`AgendamentoRequest`):

- `competenciaFinanceira` (**obrigatório para reserva automática**)
- `valorEstimadoTotal` (**obrigatório e > 0 para reservar**)
- `status` (para reserva automática imediata, usar `CONFIRMADO`)

Exemplo:

```json
{
  "paciente": "<UUID_PACIENTE>",
  "dataHora": "2026-01-10T10:00:00-03:00",
  "status": "CONFIRMADO",
  "competenciaFinanceira": "<UUID_COMPETENCIA>",
  "valorEstimadoTotal": 120.50
}
```

Resposta traz:

- `competenciaFinanceira` (objeto)
- `valorEstimadoTotal`
- `statusFinanceiro`

Status financeiro esperado após confirmação:

- `RESERVADO` (quando a reserva foi criada)
- (pode permanecer nulo/SEM_RESERVA se a reserva não foi possível por ausência de valor/competência)

### Como o front valida (sem “adivinhar”)

1) Recarregar o agendamento:

- `GET /v1/agendamentos/{id}`

2) Se precisar mostrar “trilha”:

- Reservas: `GET /v1/financeiro/reservas-orcamentarias`
- Estornos: `GET /v1/financeiro/estornos`
- Logs: `GET /v1/financeiro/logs`

## 3.2 Cancelar agendamento / marcar falta / reagendar

Quando o front mudar o status do agendamento para:

- `CANCELADO`, `FALTA` ou `REAGENDADO`

o backend tenta:

- estornar (liberar) a reserva automaticamente
- registrar um `EstornoFinanceiro` quando possível

Ou seja, o front faz **apenas** o update do agendamento:

- `PUT /v1/agendamentos/{id}` com `status: "CANCELADO"` (ou outro)

Exemplo (cancelar):

```json
{
  "status": "CANCELADO",
  "motivoCancelamento": "Paciente solicitou cancelamento"
}
```

## 3.3 Concluir atendimento (consumo)

Ao concluir o atendimento, o backend consome a reserva automaticamente, se houver agendamento vinculado.

O front deve:

- atualizar o status do atendimento para `CONCLUIDO` (dentro de `informacoes.statusAtendimento`)
- garantir que `AtendimentoRequest.competenciaFinanceira` esteja coerente com o agendamento (recomendado)

### Observação crítica do modelo atual (híbrido)

O consumo procura o **agendamento vinculado ao atendimento**. Se não houver vínculo, o backend não terá reserva para consumir.

## 3.4 Cancelar atendimento (estorno)

Se o atendimento virar `CANCELADO` ou `FALTA_PACIENTE`, o backend tenta estornar via agendamento vinculado.

---

## 4) Operações explícitas (quando usar)

Normalmente você **não precisa** chamar operações explícitas, pois o financeiro é automático nos status.

Use quando:

- precisa reprocessar uma reserva/consumo (ex.: correção operacional)
- precisa fechar competência manualmente
- precisa integrar um fluxo diferente do assistencial (importação, batch)

Endpoints (resumo):

- `POST /v1/financeiro/operacoes/agendamentos/{agendamentoId}/reservar`
- `POST /v1/financeiro/operacoes/agendamentos/{agendamentoId}/estornar` `{ "motivo": "..." }`
- `POST /v1/financeiro/operacoes/atendimentos/{atendimentoId}/consumir`
- `POST /v1/financeiro/operacoes/atendimentos/{atendimentoId}/estornar` `{ "motivo": "..." }`
- `POST /v1/financeiro/operacoes/competencias/{competenciaId}/fechar`

---

## 5) Como montar telas e tirar dúvidas comuns

## 5.1 “Por que não reservou?”

Checklist:

- o agendamento está `CONFIRMADO`?
- `competenciaFinanceira` foi enviado no request?
- `valorEstimadoTotal` é > 0?
- existe tenant no usuário autenticado? (sem `UsuariosSistema`, dá 403)

Além disso, valide:

- o usuário tem permissão/acesso ao estabelecimento/tenant esperado
- o backend pode rejeitar por regras financeiras (ex.: saldo insuficiente) — trate erro 400/409 como “bloqueio de negócio”

## 5.2 “Qual saldo disponível do município?”

Use:

- `GET /v1/financeiro/orcamentos-competencia` (paginado)

Campo calculado no response:

- `saldoDisponivel`

> Recomendação de UI: sempre mostrar `saldoDisponivel` no topo ao confirmar agendamentos.

## 5.3 “Onde vejo estornos?”

Use:

- `GET /v1/financeiro/estornos`

Filtre por competência, período, prestador etc. (a filtragem avançada é futura; hoje é via paginação/listagem e filtros no front).

---

## 6) Boas práticas de integração

- **Não inventar** status financeiro no front: use o que o backend retorna.
- Evitar criar agendamento confirmado sem `competenciaFinanceira` e `valorEstimadoTotal`.
- Ao exibir lista de agendamentos, renderize um badge do `statusFinanceiro`:
  - `RESERVADO` / `CONSUMIDO` / `ESTORNADO` / etc.
- Para auditoria, crie atalhos de navegação:
  - agendamento → reservas → estornos → logs.

## 7) Erros e códigos HTTP (o que o front deve fazer)

- **401**: token ausente/inválido (redir para login)
- **403**: usuário sem `UsuariosSistema` ativo ou sem acesso (exibir “sem acesso ao município/sistema”)
- **400**: validação/regra de negócio (mostrar mensagem ao usuário; ex.: falta competência/valor)
- **404**: entidade não encontrada (ex.: ID inválido/registro removido/inativado)
- **409**: conflito de dados (ex.: chave única, idempotência, duplicidade)
