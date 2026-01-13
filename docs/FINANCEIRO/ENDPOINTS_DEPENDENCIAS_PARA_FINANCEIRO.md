# Endpoints de Dependências (necessários para operar o Financeiro)

Este arquivo lista endpoints **fora do módulo financeiro**, mas que o front normalmente precisa para montar telas e preencher IDs nos requests do Financeiro/Faturamento.

> Importante: estes endpoints são do sistema como um todo. Eles não “pertencem” ao módulo Financeiro, mas são dependências práticas de integração.

## Convenções

- **Base URL**: `http://localhost:8080/api`
- **Auth**: `Authorization: Bearer <TOKEN>`

---

## 1) Pacientes

- **Base path**: `/v1/pacientes`
- Usado por: agendamentos, atendimentos, estornos, guias.

Endpoints principais:

- `POST /v1/pacientes`
- `GET /v1/pacientes`
- `GET /v1/pacientes/{id}`
- `PUT /v1/pacientes/{id}`
- `DELETE /v1/pacientes/{id}`

---

## 2) Estabelecimentos

- **Base path**: `/v1/estabelecimentos`
- Usado por: vínculo de atendimento, prestador, conta financeira (quando aplicável), guias e relatórios.

Endpoints principais:

- `POST /v1/estabelecimentos`
- `GET /v1/estabelecimentos`
- `GET /v1/estabelecimentos/{id}`
- `PUT /v1/estabelecimentos/{id}`
- `DELETE /v1/estabelecimentos/{id}`

---

## 3) Convênios

- **Base path**: `/v1/convenios`
- Usado por: documentos de faturamento, títulos a receber.

Endpoints principais:

- `POST /v1/convenios`
- `GET /v1/convenios`
- `GET /v1/convenios/{id}`
- `PUT /v1/convenios/{id}`
- `DELETE /v1/convenios/{id}`

---

## 4) SIGTAP (procedimentos)

- **Base path**: `/v1/sigtap`
- Usado por: `DocumentoFaturamentoItem` (sigtapProcedimento), procedimentos em atendimento e relatórios.

Exemplos de endpoints (há vários):

- `GET /v1/sigtap/grupos`
- `GET /v1/sigtap/procedimentos` (paginado, dependendo das rotas do controller)

> Dica para o front: use SIGTAP para montar pickers de procedimentos e preencher `sigtapProcedimento` em requests.

---

## 5) Agendamentos e Atendimentos (gatilhos financeiros)

Veja o arquivo:

- [ENDPOINTS_ASSISTENCIAL_RELACIONADOS.md](./ENDPOINTS_ASSISTENCIAL_RELACIONADOS.md)

