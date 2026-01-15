# Módulo FINANCEIRO — UPSaúde

## 📋 Visão Geral

O módulo Financeiro do UPSaúde é responsável pelo controle de recursos públicos destinados aos atendimentos ambulatoriais, com foco em:

- **Gestão orçamentária**: controle de saldo por município (tenant) e competência financeira
- **Rastreabilidade total**: auditoria completa de créditos, reservas, consumos e estornos
- **Automação**: integração automática com o fluxo assistencial (agendamentos e atendimentos)
- **Conformidade**: proibição de saldo negativo e validações de integridade

## 🎯 Objetivo do Módulo

Controlar recursos públicos destinados aos atendimentos ambulatoriais com:
- Saldo por município (tenant) e por competência financeira
- Rastreabilidade total (auditoria) de créditos, reservas, consumos e estornos
- Automação: o usuário final não precisa "lançar financeiro" manualmente no dia a dia
- Proibição de saldo negativo (regra de negócio — o sistema bloqueia operações que ultrapassem o orçamento)
- Integração com produção ambulatorial/BPA (fechamento por competência e consistência dos dados)

## 📚 Estrutura da Documentação

### Para Desenvolvedores Frontend

1. **[Guia de Integração](./INTEGRACAO_FRONT.md)** - Ordem correta de chamadas, exemplos e troubleshooting
2. **[Financeiro no Assistencial](./FINANCEIRO_NO_ASSISTENCIAL.md)** - Como funciona automaticamente em Agendamento/Atendimento/Consulta
3. **[Fluxos e Sequências](./FLUXOS_E_SEQUENCIAS.md)** - Ordem e gatilhos que o front deve respeitar
4. **[Dados e Status](./DADOS_E_STATUS.md)** - Campos-chave e status para renderização na UI

### Para Analistas de Negócio

1. **[Regras de Negócio](./NEGOCIO.md)** - Conceitos, fluxos e regras críticas
2. **[Fluxos e Sequências](./FLUXOS_E_SEQUENCIAS.md)** - Fluxos de negócio detalhados

### Para Desenvolvedores Backend

1. **[Documentação Técnica](./TECNICO.md)** - Arquitetura, entidades, multi-tenancy e integrações
2. **[Análise de Entidades Multi-Tenant](./ANALISE_ENTIDADES_MULTI_TENANT.md)** - Estado atual e mudanças necessárias
3. **[Mudanças de Constraints e Índices](./MUDANCAS_CONSTRAINTS_INDICES.md)** - Alterações estruturais

### Catálogo de Endpoints

1. **[Índice de Endpoints](./ENDPOINTS.md)** - Visão geral e links para documentação detalhada
2. **[Endpoints de Operações](./ENDPOINTS_FINANCEIRO_01_OPERACOES.md)** - Ações explícitas (reservar, estornar, consumir)
3. **[Endpoints Core](./ENDPOINTS_FINANCEIRO_02_CORE.md)** - Competências e orçamentos
4. **[Endpoints de Orçamento](./ENDPOINTS_FINANCEIRO_03_ORCAMENTO.md)** - Orçamentos e créditos
5. **[Endpoints Assistencial](./ENDPOINTS_FINANCEIRO_04_ASSISTENCIAL.md)** - Reservas, estornos e guias
6. **[Endpoints de Lançamentos](./ENDPOINTS_FINANCEIRO_05_LANCAMENTOS.md)** - Lançamentos financeiros
7. **[Endpoints AR/AP](./ENDPOINTS_FINANCEIRO_06_AR_AP.md)** - Contas a receber e pagar
8. **[Endpoints Contas e Conciliação](./ENDPOINTS_FINANCEIRO_07_CONTAS_CONCILIACAO.md)** - Contas financeiras e conciliação
9. **[Endpoints Parametrização](./ENDPOINTS_FINANCEIRO_08_PARAMETRIZACAO_AUDITORIA.md)** - Plano de contas, centro de custo, auditoria
10. **[Endpoints Faturamento Relacionado](./ENDPOINTS_FATURAMENTO_RELACIONADO.md)** - Faturamento que impacta o financeiro
11. **[Endpoints Assistencial Relacionados](./ENDPOINTS_ASSISTENCIAL_RELACIONADOS.md)** - Agendamentos e atendimentos que disparam eventos financeiros
12. **[Endpoints de Dependências](./ENDPOINTS_DEPENDENCIAS_PARA_FINANCEIRO.md)** - Pacientes, SIGTAP, estabelecimentos, etc.

### Documentação Adicional

- **[Resumo de Mudanças nas Entidades](./RESUMO_MUDANCAS_ENTIDADES.md)** - Alterações estruturais
- **[Impacto de Relacionamentos e Competência](./IMPACTO_RELACIONAMENTOS_COMPETENCIA.md)** - Análise de dependências
- **[Gap Analysis Setor Público](./GAP_ANALYSIS_SETOR_PUBLICO.md)** - Análise de lacunas

## ⚙️ Pré-requisitos

### Configuração da API

- **Base URL**: Por padrão o backend sobe com `server.servlet.context-path=/api`
  - Exemplo: `http://localhost:8080/api`
- **Autenticação**: Todas as requisições exigem header `Authorization: Bearer <TOKEN>`
- **Content-Type**: `application/json` para requisições com body

### Multi-Tenancy (Município = Tenant)

- O tenant é resolvido automaticamente no backend via usuário autenticado
- Fluxo: `UsuariosSistema -> tenant`
- Se o usuário não tiver `UsuariosSistema` ativo, a API retorna **403 Forbidden**
- Não é necessário enviar header `X-Tenant` - o sistema resolve automaticamente

## 🚀 Início Rápido

### Passo 1: Criar Competência Financeira

A competência financeira representa um período de referência (normalmente mensal) para o controle orçamentário.

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

**Resposta esperada**: Retorna o objeto `CompetenciaFinanceira` criado com `id` (UUID).

### Passo 2: Criar Orçamento da Competência

Cada município (tenant) precisa ter um orçamento para cada competência financeira.

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

**Nota**: O tenant é resolvido automaticamente pelo usuário autenticado.

### Passo 3: Lançar Crédito Orçamentário

Adiciona recursos ao orçamento da competência.

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

### Passo 4: Confirmar Agendamento (Reserva Automática)

Quando um agendamento é confirmado com competência e valor, o sistema cria automaticamente uma reserva orçamentária.

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

**Resultado**: O backend cria uma **Reserva Orçamentária Assistencial** e marca o agendamento com `statusFinanceiro = RESERVADO`.

**Pré-requisitos para reserva automática**:
- `competenciaFinanceira` deve estar preenchida
- `valorEstimadoTotal` deve ser maior que zero

## 🔗 Integração com Outros Módulos

### Módulo Financeiro (Core)

- **Base Path**: `/v1/financeiro/*`
- **Funcionalidades**: CRUD de entidades financeiras + operações explícitas
- **Documentação**: Ver [ENDPOINTS.md](./ENDPOINTS.md)

### Módulo Faturamento (Relacionado)

- **Base Path**: `/v1/faturamento/*`
- **Relação**: Documentos de faturamento podem estar vinculados a reservas e competências
- **Documentação**: Ver [ENDPOINTS_FATURAMENTO_RELACIONADO.md](./ENDPOINTS_FATURAMENTO_RELACIONADO.md)

### Módulo Assistencial (Gatilhos Automáticos)

O módulo financeiro é acionado automaticamente por mudanças de status no assistencial:

- **Agendamentos** (`/v1/agendamentos`):
  - `CONFIRMADO` → Cria reserva orçamentária
  - `CANCELADO` / `FALTA` / `REAGENDADO` → Estorna reserva
  
- **Atendimentos** (`/v1/atendimentos`):
  - `CONCLUIDO` → Consome reserva do agendamento vinculado
  - `CANCELADO` / `FALTA_PACIENTE` → Estorna consumo

**Documentação**: Ver [ENDPOINTS_ASSISTENCIAL_RELACIONADOS.md](./ENDPOINTS_ASSISTENCIAL_RELACIONADOS.md) e [FINANCEIRO_NO_ASSISTENCIAL.md](./FINANCEIRO_NO_ASSISTENCIAL.md)

## 📊 Estrutura do Módulo

### Entidades Principais

- **CompetenciaFinanceira**: Período de referência (ex: 2026-01)
- **OrcamentoCompetencia**: Saldo do município por competência
- **CreditoOrcamentario**: Recursos adicionados ao orçamento
- **ReservaOrcamentariaAssistencial**: Compromisso de saldo para agendamento/atendimento
- **EstornoFinanceiro**: Devolução de saldo (cancelamentos, faltas, etc.)
- **LancamentoFinanceiro**: Lançamentos contábeis (partidas dobradas)
- **ContaFinanceira**: Contas bancárias e caixa
- **TituloReceber / TituloPagar**: Contas a receber e pagar
- **ConciliacaoBancaria**: Conciliação de extratos bancários

### Serviços Principais

- **FinanceiroIntegrationService**: Integração automática com assistencial
- **OrcamentoCompetenciaService**: Gestão de orçamentos
- **ReservaOrcamentariaAssistencialService**: Gestão de reservas
- **LancamentoFinanceiroService**: Lançamentos contábeis
- **ConciliacaoBancariaService**: Conciliação bancária

## ⚠️ Regras Importantes

1. **Saldo Negativo**: O sistema bloqueia operações que resultariam em saldo negativo
2. **Competência Obrigatória**: Agendamentos e atendimentos devem ter competência financeira para reserva/consumo automático
3. **Valor Obrigatório**: Reservas automáticas só ocorrem se `valorEstimadoTotal > 0`
4. **Idempotência**: Operações de reserva são idempotentes (não criam duplicatas)
5. **Auditoria**: Todas as operações são rastreáveis via `LogFinanceiro` e status nas entidades

## 📝 Convenções de API

- **Paginação**: Endpoints de listagem aceitam `page`, `size`, `sort`
- **CRUD Padrão**: Todos os controllers seguem o padrão REST:
  - `POST /...` - Criar
  - `GET /...` - Listar (paginado)
  - `GET /.../{id}` - Obter por ID
  - `PUT /.../{id}` - Atualizar
  - `DELETE /.../{id}` - Excluir (soft delete)
  - `PUT /.../{id}/inativar` - Inativar
- **Operações Especiais**: Endpoints de ação ficam em `/v1/financeiro/operacoes/...`

## 🆘 Suporte

Para dúvidas sobre integração, consulte:
- [Guia de Integração Frontend](./INTEGRACAO_FRONT.md)
- [Fluxos e Sequências](./FLUXOS_E_SEQUENCIAS.md)
- [Troubleshooting](./INTEGRACAO_FRONT.md#checklist-de-troubleshooting-para-o-front)
