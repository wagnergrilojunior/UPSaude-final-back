# FINANCEIRO — Catálogo Completo de Endpoints

Este arquivo serve como **índice** do catálogo completo de endpoints do módulo financeiro. Cada domínio tem um arquivo próprio com:

- Endpoints (paths e métodos HTTP)
- Exemplos de request/response
- Erros comuns e observações para integração do front
- Validações e regras de negócio

## 📋 Convenções Globais

### Base URL e Autenticação

- **Base URL**: `http://localhost:8080/api`
- **Autenticação**: `Authorization: Bearer <TOKEN>` (obrigatório em todas as requisições)
- **Content-Type**: `application/json` (para requisições com body)

### Paginação

Endpoints de listagem (`GET`) aceitam parâmetros de paginação:

- `page` (int, default: 0) - Número da página
- `size` (int, default: 20) - Tamanho da página
- `sort` (string) - Ordenação (ex: `createdAt,desc`)

**Exemplo**:
```
GET /api/v1/financeiro/orcamentos-competencia?page=0&size=20&sort=createdAt,desc
```

### CRUD Padrão

Todos os controllers do módulo seguem o padrão REST:

- `POST /...` - Criar novo recurso
- `GET /...` - Listar recursos (paginado)
- `GET /.../{id}` - Obter recurso por ID
- `PUT /.../{id}` - Atualizar recurso existente
- `DELETE /.../{id}` - Excluir recurso (soft delete)
- `PUT /.../{id}/inativar` - Inativar recurso (soft disable)

### Códigos de Resposta HTTP

- `200 OK` - Sucesso (GET, PUT)
- `201 Created` - Recurso criado (POST)
- `204 No Content` - Sucesso sem conteúdo (DELETE, operações)
- `400 Bad Request` - Dados inválidos
- `401 Unauthorized` - Token ausente ou inválido
- `403 Forbidden` - Sem permissão ou tenant inválido
- `404 Not Found` - Recurso não encontrado
- `409 Conflict` - Conflito (ex: saldo insuficiente)
- `500 Internal Server Error` - Erro interno do servidor

## 📚 Catálogo de Endpoints por Domínio

### Financeiro (Core)

#### 1. Operações (Ações Explícitas)

Endpoints para operações explícitas de reserva, estorno e consumo.

- **Documentação**: [ENDPOINTS_FINANCEIRO_01_OPERACOES.md](./ENDPOINTS_FINANCEIRO_01_OPERACOES.md)
- **Base Path**: `/v1/financeiro/operacoes`
- **Endpoints**:
  - `POST /agendamentos/{id}/reservar` - Reservar orçamento para agendamento
  - `POST /agendamentos/{id}/estornar` - Estornar reserva de agendamento
  - `POST /atendimentos/{id}/consumir` - Consumir reserva no atendimento
  - `POST /atendimentos/{id}/estornar` - Estornar consumo de atendimento

#### 2. Core (Competências e Orçamentos)

Endpoints para gestão de competências financeiras e orçamentos.

- **Documentação**: [ENDPOINTS_FINANCEIRO_02_CORE.md](./ENDPOINTS_FINANCEIRO_02_CORE.md)
- **Base Path**: `/v1/financeiro`
- **Endpoints**:
  - Competências: `/competencias`
  - Orçamentos: `/orcamentos-competencia`

#### 3. Orçamento e Créditos

Endpoints para gestão de créditos orçamentários.

- **Documentação**: [ENDPOINTS_FINANCEIRO_03_ORCAMENTO.md](./ENDPOINTS_FINANCEIRO_03_ORCAMENTO.md)
- **Base Path**: `/v1/financeiro`
- **Endpoints**:
  - Créditos: `/creditos-orcamentarios`

#### 4. Assistencial Financeiro (Reservas, Estornos, Guias)

Endpoints para reservas orçamentárias, estornos e guias ambulatoriais.

- **Documentação**: [ENDPOINTS_FINANCEIRO_04_ASSISTENCIAL.md](./ENDPOINTS_FINANCEIRO_04_ASSISTENCIAL.md)
- **Base Path**: `/v1/financeiro`
- **Endpoints**:
  - Reservas: `/reservas-orcamentarias`
  - Estornos: `/estornos`
  - Guias: `/guias-ambulatoriais`

#### 5. Lançamentos Financeiros

Endpoints para lançamentos contábeis e seus itens.

- **Documentação**: [ENDPOINTS_FINANCEIRO_05_LANCAMENTOS.md](./ENDPOINTS_FINANCEIRO_05_LANCAMENTOS.md)
- **Base Path**: `/v1/financeiro`
- **Endpoints**:
  - Lançamentos: `/lancamentos-financeiros`
  - Itens: `/lancamentos-financeiros-itens`

#### 6. Contas a Receber / Pagar (AR/AP)

Endpoints para gestão de títulos a receber e pagar.

- **Documentação**: [ENDPOINTS_FINANCEIRO_06_AR_AP.md](./ENDPOINTS_FINANCEIRO_06_AR_AP.md)
- **Base Path**: `/v1/financeiro`
- **Endpoints**:
  - Títulos a Receber: `/titulos-receber`
  - Baixas a Receber: `/baixas-receber`
  - Títulos a Pagar: `/titulos-pagar`
  - Pagamentos: `/pagamentos-pagar`
  - Renegociações: `/renegociacoes-receber`

#### 7. Contas, Movimentações e Conciliação

Endpoints para gestão de contas financeiras e conciliação bancária.

- **Documentação**: [ENDPOINTS_FINANCEIRO_07_CONTAS_CONCILIACAO.md](./ENDPOINTS_FINANCEIRO_07_CONTAS_CONCILIACAO.md)
- **Base Path**: `/v1/financeiro`
- **Endpoints**:
  - Contas: `/contas-financeiras`
  - Movimentações: `/movimentacoes-conta`
  - Transferências: `/transferencias-entre-contas`
  - Conciliações: `/conciliacoes-bancarias`
  - Extratos: `/extratos-bancarios-importados`

#### 8. Parametrização Contábil e Auditoria

Endpoints para plano de contas, centro de custo, regras e auditoria.

- **Documentação**: [ENDPOINTS_FINANCEIRO_08_PARAMETRIZACAO_AUDITORIA.md](./ENDPOINTS_FINANCEIRO_08_PARAMETRIZACAO_AUDITORIA.md)
- **Base Path**: `/v1/financeiro`
- **Endpoints**:
  - Plano de Contas: `/planos-contas`
  - Contas Contábeis: `/contas-contabeis`
  - Centro de Custo: `/centros-custo`
  - Regras: `/regras-classificacao-contabil`
  - Recorrências: `/recorrencias-financeiras`
  - Logs: `/logs-financeiro`
  - BPA: `/bpa`

### Faturamento (Relacionado ao Financeiro)

Endpoints do módulo de faturamento que impactam o financeiro.

- **Documentação**: [ENDPOINTS_FATURAMENTO_RELACIONADO.md](./ENDPOINTS_FATURAMENTO_RELACIONADO.md)
- **Base Path**: `/v1/faturamento`
- **Relação**: Documentos de faturamento podem estar vinculados a reservas e competências

### Assistencial (Gatilhos Financeiros)

Endpoints do módulo assistencial que disparam eventos financeiros automaticamente.

- **Documentação**: [ENDPOINTS_ASSISTENCIAL_RELACIONADOS.md](./ENDPOINTS_ASSISTENCIAL_RELACIONADOS.md)
- **Base Path**: `/v1`
- **Endpoints**:
  - Agendamentos: `/agendamentos` (status CONFIRMADO/CANCELADO/FALTA/REAGENDADO)
  - Atendimentos: `/atendimentos` (status CONCLUIDO/CANCELADO/FALTA_PACIENTE)

### Dependências (Outros Módulos)

Endpoints de outros módulos usados pelo Financeiro.

- **Documentação**: [ENDPOINTS_DEPENDENCIAS_PARA_FINANCEIRO.md](./ENDPOINTS_DEPENDENCIAS_PARA_FINANCEIRO.md)
- **Módulos**:
  - Pacientes: `/v1/pacientes`
  - Estabelecimentos: `/v1/estabelecimentos`
  - Convênios: `/v1/convenios`
  - SIGTAP: `/v1/sigtap`
  - Profissionais: `/v1/profissionais`

## 🔍 Busca Rápida por Funcionalidade

### Configuração Inicial

- Criar competência: `POST /v1/financeiro/competencias`
- Criar orçamento: `POST /v1/financeiro/orcamentos-competencia`
- Lançar crédito: `POST /v1/financeiro/creditos-orcamentarios`

### Operações do Dia a Dia

- Confirmar agendamento: `POST /v1/agendamentos` (com status CONFIRMADO)
- Encerrar atendimento: `PUT /v1/atendimentos/{id}/encerrar`
- Cancelar agendamento: `PUT /v1/agendamentos/{id}` (com status CANCELADO)

### Consultas e Relatórios

- Orçamento por competência: `GET /v1/financeiro/orcamentos-competencia`
- Reservas: `GET /v1/financeiro/reservas-orcamentarias`
- Estornos: `GET /v1/financeiro/estornos`
- Lançamentos: `GET /v1/financeiro/lancamentos-financeiros`

### Operações Especiais

- Reservar manualmente: `POST /v1/financeiro/operacoes/agendamentos/{id}/reservar`
- Estornar manualmente: `POST /v1/financeiro/operacoes/agendamentos/{id}/estornar`
- Consumir manualmente: `POST /v1/financeiro/operacoes/atendimentos/{id}/consumir`

## 📖 Referências

- [README Principal](./README.md)
- [Guia de Integração Frontend](./INTEGRACAO_FRONT.md)
- [Fluxos e Sequências](./FLUXOS_E_SEQUENCIAS.md)
- [Documentação de Negócio](./NEGOCIO.md)
- [Documentação Técnica](./TECNICO.md)
