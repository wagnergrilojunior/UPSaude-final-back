# FINANCEIRO — Documentação de Negócio

## Links úteis (para produto/front)

- **Fluxos e ordem de integração**: [FLUXOS_E_SEQUENCIAS.md](./FLUXOS_E_SEQUENCIAS.md)
- **Dados e status para UI**: [DADOS_E_STATUS.md](./DADOS_E_STATUS.md)
- **Catálogo de endpoints (por domínio)**: [ENDPOINTS.md](./ENDPOINTS.md)

## Objetivo do módulo

O módulo **Financeiro** existe para controlar **recursos públicos** destinados aos atendimentos ambulatoriais, com:

- **Saldo por município (tenant)** e por **competência financeira**;
- **Rastreabilidade total** (auditoria) de créditos, reservas, consumos e estornos;
- **Automação**: o usuário final não precisa “lançar financeiro” manualmente no dia a dia;
- **Proibição de saldo negativo** (regra de negócio — o sistema deve bloquear operações que ultrapassem o orçamento);
- **Integração com produção ambulatorial/BPA** (fechamento por competência e consistência dos dados).

> Observação importante: o domínio foi desenhado para suportar um financeiro “completo” (contas, conciliação, títulos, lançamentos, plano de contas). Porém, o **fluxo de BPA/fechamento completo** ainda depende de evoluções específicas (ver [TECNICO.md](./TECNICO.md)).

---

## Conceitos (linguagem de negócio)

### Competência financeira
Período de referência do financeiro (normalmente **mensal**, mas pode ser personalizado).

- Ex.: `2026-01` (01/01/2026 a 31/01/2026)

### Tenant = Município
Cada município é um **tenant** e possui:

- Seu próprio orçamento/saldo por competência;
- Visão segregada dos seus registros financeiros.

### Orçamento por competência
É o “espelho” do saldo do município na competência:

- saldo anterior
- créditos liberados
- reservas ativas (compromissos ainda não consumidos)
- consumos (execução/atendimento concluído)
- estornos (cancelamentos/no-shows/ajustes)
- despesas administrativas (quando aplicável)
- saldo final / saldo disponível (calculado)

### Reserva (compromisso)
Uma reserva é uma “separação do saldo” para um evento futuro.

No modelo atual (híbrido):

- **Reserva** ocorre quando o agendamento é **CONFIRMADO**
- **Consumo** ocorre quando o atendimento é **CONCLUÍDO**
- **Estorno** ocorre quando o agendamento é **CANCELADO/FALTA/REAGENDADO** (ou quando o atendimento é CANCELADO/FALTA_PACIENTE)

### Estorno
Estorno significa “devolver saldo” (ou desfazer um consumo). Ele precisa ser:

- automático quando aplicável
- auditável
- com motivo e vínculo ao evento que originou

---

## Fluxos de negócio (o que o front precisa entender)

## 1) Preparação da competência (backoffice)

Antes de operar o dia a dia, precisa existir:

- Uma **Competência Financeira** cadastrada
- Um **Orçamento da Competência** (do tenant/município)
- Créditos (se houver) para compor o saldo

Sem isso, o sistema não consegue reservar automaticamente.

## 2) Fluxo ambulatorial padrão (modelo híbrido)

### 2.1 Confirmar agendamento → Reservar orçamento

Quando um agendamento entra em `CONFIRMADO`:

- o sistema cria uma **Reserva Orçamentária Assistencial**
- marca o agendamento como `statusFinanceiro = RESERVADO`

**Pré-requisitos para a reserva automática funcionar:**

- `agendamento.competenciaFinanceira` preenchido
- `agendamento.valorEstimadoTotal > 0`

Se o agendamento estiver sem competência/valor, o sistema não reserva (o front deve evitar esse cenário).

### 2.2 Concluir atendimento → Consumir reserva

Quando um atendimento muda para `CONCLUIDO`:

- o sistema procura o agendamento vinculado
- marca a reserva como `CONSUMIDA`
- marca o agendamento como `statusFinanceiro = CONSUMIDO`

### 2.3 Cancelar/No-show → Estornar

Se o agendamento virar:

- `CANCELADO` **ou** `FALTA` **ou** `REAGENDADO`

o sistema:

- registra um **Estorno Financeiro** (quando possível, com paciente)
- marca a reserva como `LIBERADA`
- marca o agendamento como `statusFinanceiro = ESTORNADO`

Se o atendimento virar:

- `CANCELADO` **ou** `FALTA_PACIENTE`

o sistema tenta estornar via o agendamento vinculado.

---

## Exemplo completo (com números)

### Cenário
- Município A (tenant A)
- Competência: `2026-01`
- Saldo inicial: 0
- Crédito liberado na competência: R$ 10.000,00

### Passo a passo

1) Crédito: +10.000 → saldo disponível = 10.000

2) Agendamento CONFIRMADO (estimado R$ 120,50):
- Reserva: -120,50 (como “reservas ativas”)
- saldo disponível passa a 9.879,50

3) Atendimento CONCLUÍDO:
- Reserva vira consumo: “reservas ativas” diminui, “consumos” aumenta
- saldo disponível permanece coerente, mas o uso fica registrado como realizado

4) Agendamento CANCELADO (antes do consumo):
- Reserva é liberada
- Estorno é registrado
- saldo disponível volta ao valor anterior

---

## Regras de negócio críticas (para UI/UX do front)

- **Bloqueio por saldo**: a UI deve impedir (ou alertar) quando não houver saldo para confirmar.
  - Na prática: a confirmação pode falhar (400/409) por regras financeiras.
- **Operação sempre por competência**:
  - agendamento/atendimento devem estar vinculados a uma competência.
- **Auditabilidade**:
  - a UI deve expor histórico (reservas, estornos, logs) e permitir filtragem por competência.
- **Sem alteração retroativa**:
  - correções devem acontecer via **ajustes/estornos**, não “editando o passado”.

---

## Sugestão de “Mapa de Telas” (mínimo recomendado)

- **Painel Financeiro do Município (por competência)**:
  - mostra `saldoDisponivel`, `creditos`, `reservasAtivas`, `consumos`, `estornos`
  - fonte: `GET /v1/financeiro/orcamentos-competencia`
- **Tela de Reservas**:
  - reservas `ATIVA` / `CONSUMIDA` / `LIBERADA` (auditoria e pendências)
  - fonte: `GET /v1/financeiro/reservas-orcamentarias`
- **Tela de Estornos**:
  - fonte: `GET /v1/financeiro/estornos`
- **Detalhe do Agendamento**:
  - exibir `competenciaFinanceira`, `valorEstimadoTotal`, `statusFinanceiro`
  - fonte: `GET /v1/agendamentos/{id}`
- **Detalhe do Atendimento**:
  - exibir `competenciaFinanceira` e status clínico
  - fonte: `GET /v1/atendimentos/{id}`

---

## Exemplos de consultas (para relatórios simples no Front)

### Orçamento por competência (paginado)

```bash
GET /api/v1/financeiro/orcamentos-competencia?page=0&size=20&sort=createdAt,desc
```

### Reservas (paginado)

```bash
GET /api/v1/financeiro/reservas-orcamentarias?page=0&size=20&sort=createdAt,desc
```

### Estornos (paginado)

```bash
GET /api/v1/financeiro/estornos?page=0&size=20&sort=dataEstorno,desc
```

> Nota: filtros avançados (por prestador, procedimento, período) ainda não estão expostos como query params dedicados em todos endpoints; no MVP, o front pode filtrar client-side e depois evoluímos para endpoints de relatório.

---

## O que o front deve exibir (mínimo recomendado)

- No agendamento:
  - `competenciaFinanceira`
  - `valorEstimadoTotal`
  - `statusFinanceiro` (SEM_RESERVA | RESERVADO | CONSUMIDO | ESTORNADO | AJUSTADO)
- Painel financeiro do município:
  - Orçamento por competência (saldo anterior, créditos, reservas, consumos, estornos, saldo disponível)
  - Estornos com motivo e vínculo ao evento
  - Reservas ativas (pendências)

