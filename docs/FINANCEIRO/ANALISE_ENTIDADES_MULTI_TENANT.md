# Análise de Entidades Financeiras - Multi-Tenant

## Objetivo
Documentar o estado atual de todas as entidades do módulo Financeiro e definir as mudanças necessárias para tornar o módulo 100% multi-tenant (por prefeitura), com estabelecimento opcional.

## Resumo Executivo

### Estado Atual
- **Total de entidades**: 27
- **Com tenant (BaseEntity)**: 26 entidades ✅
- **Sem tenant (BaseEntityWithoutTenant)**: 1 entidade ❌ (`CompetenciaFinanceira`)
- **UniqueConstraints sem tenant_id**: 2 problemas identificados
- **Relacionamentos com CompetenciaFinanceira**: 7 entidades + 2 tabelas assistenciais

### Problemas Críticos Identificados

1. **CompetenciaFinanceira é global** (sem tenant)
   - Herda `BaseEntityWithoutTenant`
   - UniqueConstraint em `codigo` é global (deve ser por tenant)
   - Impacta todas as entidades que referenciam competência

2. **ExtratoBancarioImportado** tem unique sem tenant
   - `uk_extrato_bancario_hash` usa apenas `hash_linha`
   - Deve incluir `tenant_id` para evitar colisões entre tenants

3. **MovimentacaoConta** tem unique sem tenant
   - `uk_movimentacao_conta_conta_idempotency` usa apenas `conta_financeira_id` e `idempotency_key`
   - Como `conta_financeira` já tem tenant, tecnicamente está protegido, mas seria mais seguro incluir tenant explicitamente

4. **ContaContabil** tem unique sem tenant explícito
   - `uk_conta_contabil_plano_codigo` usa `plano_contas_id` e `codigo`
   - Como `plano_contas` já tem tenant, está protegido indiretamente, mas idealmente deveria incluir tenant

## Inventário Detalhado por Entidade

### 1. CompetenciaFinanceira ❌ **CRÍTICO**

**Arquivo**: `src/main/java/com/upsaude/entity/financeiro/CompetenciaFinanceira.java`

**Herança Atual**: `BaseEntityWithoutTenant` ❌

**Herança Desejada**: `BaseEntity` ✅

**UniqueConstraints Atuais**:
- `uk_competencia_financeira_codigo`: `{"codigo"}` ❌ (deve incluir tenant_id)

**UniqueConstraints Desejadas**:
- `uk_competencia_financeira_tenant_codigo`: `{"tenant_id", "codigo"}` ✅

**Índices Atuais**:
- `idx_competencia_financeira_data`: `{"data_inicio", "data_fim"}` (deve incluir tenant_id como primeiro)

**Índices Desejados**:
- `idx_competencia_financeira_tenant_data`: `{"tenant_id", "data_inicio", "data_fim"}` ✅

**Campos Atuais**:
- `codigo` (String, unique global) ❌
- `tipo` (String: MENSAL | CUSTOM)
- `dataInicio` (LocalDate)
- `dataFim` (LocalDate)
- `descricao` (String, opcional)

**Campos a Adicionar** (incorporar de CompetenciaFinanceiraTenant):
- `status` (String: ABERTA | FECHADA) ✅
- `fechadaEm` (OffsetDateTime, opcional) ✅
- `fechadaPor` (UUID, opcional) ✅
- `motivoFechamento` (String TEXT, opcional) ✅
- `snapshotHash` (String, opcional) ✅
- `documentoBpaFechamento` (ManyToOne para DocumentoFaturamento, opcional) ✅
- `hashMovimentacoes` (String, opcional) ✅
- `hashBpa` (String, opcional) ✅
- `validacaoIntegridade` (Boolean, opcional) ✅

**Relacionamentos**:
- Nenhum relacionamento ManyToOne (será adicionado tenant e estabelecimento via BaseEntity)

**Entidades que Referenciam**:
- `CompetenciaFinanceiraTenant` (será descontinuada)
- `OrcamentoCompetencia`
- `CreditoOrcamentario`
- `ReservaOrcamentariaAssistencial`
- `LancamentoFinanceiro`
- `EstornoFinanceiro`
- `GuiaAtendimentoAmbulatorial`
- `agendamentos` (tabela assistencial)
- `atendimentos` (tabela assistencial)

**Ação Requerida**: 
- Alterar herança para `BaseEntity`
- Atualizar unique constraint para incluir tenant_id
- Adicionar campos de status/fechamento
- Atualizar índices
- Planejar migração de dados e descontinuação de `CompetenciaFinanceiraTenant`

---

### 2. CompetenciaFinanceiraTenant ⚠️ **A DESCONTINUAR**

**Arquivo**: `src/main/java/com/upsaude/entity/financeiro/CompetenciaFinanceiraTenant.java`

**Herança Atual**: `BaseEntity` ✅

**Status**: Esta entidade será **descontinuada** após incorporar seus campos em `CompetenciaFinanceira`.

**Campos a Migrar para CompetenciaFinanceira**:
- Todos os campos de status/fechamento (já listados acima)

**Ação Requerida**: 
- Marcar como `@Deprecated` após migração
- Planejar remoção futura após migração completa

---

### 3. OrcamentoCompetencia ✅

**Arquivo**: `src/main/java/com/upsaude/entity/financeiro/OrcamentoCompetencia.java`

**Herança**: `BaseEntity` ✅

**UniqueConstraints**: 
- `uk_orcamento_competencia_tenant_competencia`: `{"tenant_id", "competencia_id"}` ✅

**Índices**: 
- `idx_orcamento_competencia_competencia`: `{"tenant_id", "competencia_id"}` ✅

**Relacionamentos**:
- `competencia` → `CompetenciaFinanceira` (será atualizado após tenantização)

**Ação Requerida**: 
- Nenhuma mudança estrutural necessária
- Verificar se relacionamento com CompetenciaFinanceira continua válido após tenantização

---

### 4. CreditoOrcamentario ✅

**Arquivo**: `src/main/java/com/upsaude/entity/financeiro/CreditoOrcamentario.java`

**Herança**: `BaseEntity` ✅

**UniqueConstraints**: 
- `uk_credito_orcamentario_tenant_idempotency`: `{"tenant_id", "idempotency_key"}` ✅

**Índices**: 
- `idx_credito_orcamentario_competencia`: `{"tenant_id", "competencia_id"}` ✅

**Relacionamentos**:
- `competencia` → `CompetenciaFinanceira` (será atualizado após tenantização)

**Ação Requerida**: 
- Nenhuma mudança estrutural necessária

---

### 5. ReservaOrcamentariaAssistencial ✅

**Arquivo**: `src/main/java/com/upsaude/entity/financeiro/ReservaOrcamentariaAssistencial.java`

**Herança**: `BaseEntity` ✅

**UniqueConstraints**: 
- Nenhuma (mas tem índices adequados) ✅

**Índices**: 
- `idx_reserva_orcamentaria_competencia`: `{"tenant_id", "competencia_id", "status"}` ✅
- `idx_reserva_orcamentaria_agendamento`: `{"agendamento_id"}` (deveria incluir tenant_id)
- `idx_reserva_orcamentaria_prestador`: `{"tenant_id", "prestador_tipo", "prestador_id"}` ✅

**Relacionamentos**:
- `competencia` → `CompetenciaFinanceira` (será atualizado após tenantização)
- `agendamento` → `Agendamento` (já tem tenant)
- `documentoFaturamento` → `DocumentoFaturamento` (já tem tenant)
- `guiaAmbulatorial` → `GuiaAtendimentoAmbulatorial` (já tem tenant)

**Campos Especiais**:
- `prestadorId` (UUID) e `prestadorTipo` (String) - modelo híbrido para estabelecimento/profissional

**Ação Requerida**: 
- Atualizar índice de agendamento para incluir tenant_id
- Verificar relacionamento com CompetenciaFinanceira após tenantização

---

### 6. LancamentoFinanceiro ✅

**Arquivo**: `src/main/java/com/upsaude/entity/financeiro/LancamentoFinanceiro.java`

**Herança**: `BaseEntity` ✅

**UniqueConstraints**: 
- `uk_lancamento_financeiro_tenant_idempotency`: `{"tenant_id", "idempotency_key"}` ✅

**Índices**: 
- `idx_lancamento_financeiro_competencia`: `{"tenant_id", "competencia_id", "data_evento"}` ✅
- `idx_lancamento_financeiro_origem`: `{"tenant_id", "origem_tipo", "origem_id"}` ✅
- `idx_lancamento_financeiro_prestador`: `{"tenant_id", "prestador_tipo", "prestador_id"}` ✅
- `idx_lancamento_financeiro_estorno`: `{"tenant_id", "status", "motivo_estorno"}` ✅

**Relacionamentos**:
- `competencia` → `CompetenciaFinanceira` (será atualizado após tenantização)
- Todos os outros relacionamentos já têm tenant

**Campos Especiais**:
- `prestadorId` (UUID) e `prestadorTipo` (String) - modelo híbrido

**Ação Requerida**: 
- Verificar relacionamento com CompetenciaFinanceira após tenantização

---

### 7. EstornoFinanceiro ✅

**Arquivo**: `src/main/java/com/upsaude/entity/financeiro/EstornoFinanceiro.java`

**Herança**: `BaseEntity` ✅

**UniqueConstraints**: 
- Nenhuma (mas tem índices adequados) ✅

**Índices**: 
- `idx_estorno_financeiro_competencia_motivo`: `{"tenant_id", "competencia_id", "motivo"}` ✅
- `idx_estorno_financeiro_data`: `{"tenant_id", "data_estorno"}` ✅

**Relacionamentos**:
- `competencia` → `CompetenciaFinanceira` (será atualizado após tenantização)
- Todos os outros relacionamentos já têm tenant

**Ação Requerida**: 
- Verificar relacionamento com CompetenciaFinanceira após tenantização

---

### 8. GuiaAtendimentoAmbulatorial ✅

**Arquivo**: `src/main/java/com/upsaude/entity/financeiro/GuiaAtendimentoAmbulatorial.java`

**Herança**: `BaseEntity` ✅

**UniqueConstraints**: 
- `uk_guia_ambulatorial_tenant_comp_num`: `{"tenant_id", "competencia_id", "numero"}` ✅

**Índices**: 
- `idx_guia_ambulatorial_status`: `{"tenant_id", "competencia_id", "status"}` ✅

**Relacionamentos**:
- `competencia` → `CompetenciaFinanceira` (será atualizado após tenantização)
- Todos os outros relacionamentos já têm tenant

**Ação Requerida**: 
- Verificar relacionamento com CompetenciaFinanceira após tenantização

---

### 9. TituloPagar ✅

**Arquivo**: `src/main/java/com/upsaude/entity/financeiro/TituloPagar.java`

**Herança**: `BaseEntity` ✅

**UniqueConstraints**: 
- `uk_titulo_pagar_tenant_idempotency`: `{"tenant_id", "idempotency_key"}` ✅

**Índices**: 
- `idx_titulo_pagar_status_vencimento`: `{"tenant_id", "status", "data_vencimento"}` ✅

**Relacionamentos**:
- Todos têm tenant (via BaseEntity ou relacionamentos)

**Ação Requerida**: 
- Nenhuma mudança estrutural necessária

---

### 10. TituloReceber ✅

**Arquivo**: `src/main/java/com/upsaude/entity/financeiro/TituloReceber.java`

**Herança**: `BaseEntity` ✅

**UniqueConstraints**: 
- `uk_titulo_receber_tenant_idempotency`: `{"tenant_id", "idempotency_key"}` ✅

**Índices**: 
- `idx_titulo_receber_status_vencimento`: `{"tenant_id", "status", "data_vencimento"}` ✅

**Relacionamentos**:
- Todos têm tenant

**Ação Requerida**: 
- Nenhuma mudança estrutural necessária

---

### 11. PlanoContas ✅

**Arquivo**: `src/main/java/com/upsaude/entity/financeiro/PlanoContas.java`

**Herança**: `BaseEntity` ✅

**UniqueConstraints**: 
- `uk_plano_contas_tenant_nome_versao`: `{"tenant_id", "nome", "versao"}` ✅

**Índices**: 
- `idx_plano_contas_tenant`: `{"tenant_id"}` ✅
- `idx_plano_contas_padrao`: `{"tenant_id", "padrao"}` ✅

**Ação Requerida**: 
- Nenhuma mudança estrutural necessária

---

### 12. ContaContabil ⚠️ **REVISAR**

**Arquivo**: `src/main/java/com/upsaude/entity/financeiro/ContaContabil.java`

**Herança**: `BaseEntity` ✅

**UniqueConstraints**: 
- `uk_conta_contabil_plano_codigo`: `{"plano_contas_id", "codigo"}` ⚠️

**Análise**: 
- Como `PlanoContas` já tem tenant obrigatório, o unique está protegido indiretamente
- Porém, seria mais explícito e seguro incluir tenant_id diretamente
- **Recomendação**: Manter como está (funcionalmente correto), mas considerar adicionar tenant_id para clareza

**Índices**: 
- `idx_conta_contabil_plano`: `{"plano_contas_id"}` (deveria incluir tenant_id)
- `idx_conta_contabil_pai`: `{"plano_contas_id", "conta_pai_id"}` (deveria incluir tenant_id)

**Ação Requerida**: 
- Considerar adicionar tenant_id aos índices para melhor performance em queries multi-tenant

---

### 13. ContaFinanceira ✅

**Arquivo**: `src/main/java/com/upsaude/entity/financeiro/ContaFinanceira.java`

**Herança**: `BaseEntity` ✅

**UniqueConstraints**: 
- `uk_conta_financeira_tenant_tipo_nome`: `{"tenant_id", "tipo", "nome"}` ✅

**Índices**: 
- `idx_conta_financeira_tipo`: `{"tenant_id", "tipo"}` ✅

**Ação Requerida**: 
- Nenhuma mudança estrutural necessária

---

### 14. CentroCusto ✅

**Arquivo**: `src/main/java/com/upsaude/entity/financeiro/CentroCusto.java`

**Herança**: `BaseEntity` ✅

**UniqueConstraints**: 
- `uk_centro_custo_tenant_codigo`: `{"tenant_id", "codigo"}` ✅

**Índices**: 
- `idx_centro_custo_pai`: `{"tenant_id", "pai_id"}` ✅

**Ação Requerida**: 
- Nenhuma mudança estrutural necessária

---

### 15. ParteFinanceira ✅

**Arquivo**: `src/main/java/com/upsaude/entity/financeiro/ParteFinanceira.java`

**Herança**: `BaseEntity` ✅

**UniqueConstraints**: 
- `uk_parte_financeira_tenant_tipo_doc`: `{"tenant_id", "tipo", "documento"}` ✅

**Índices**: 
- `idx_parte_financeira_tipo`: `{"tenant_id", "tipo"}` ✅

**Ação Requerida**: 
- Nenhuma mudança estrutural necessária

---

### 16. MovimentacaoConta ⚠️ **REVISAR**

**Arquivo**: `src/main/java/com/upsaude/entity/financeiro/MovimentacaoConta.java`

**Herança**: `BaseEntity` ✅

**UniqueConstraints**: 
- `uk_movimentacao_conta_conta_idempotency`: `{"conta_financeira_id", "idempotency_key"}` ⚠️

**Análise**: 
- Como `ContaFinanceira` já tem tenant obrigatório, o unique está protegido indiretamente
- Porém, seria mais seguro incluir tenant_id explicitamente
- **Recomendação**: Adicionar tenant_id ao unique constraint

**Índices**: 
- `idx_movimentacao_conta_data`: `{"conta_financeira_id", "data_movimento"}` (deveria incluir tenant_id)

**Ação Requerida**: 
- Adicionar tenant_id ao unique constraint
- Atualizar índice para incluir tenant_id

---

### 17. ExtratoBancarioImportado ❌ **CRÍTICO**

**Arquivo**: `src/main/java/com/upsaude/entity/financeiro/ExtratoBancarioImportado.java`

**Herança**: `BaseEntity` ✅

**UniqueConstraints**: 
- `uk_extrato_bancario_hash`: `{"hash_linha"}` ❌ **SEM TENANT_ID**

**Problema**: 
- Hash pode colidir entre diferentes tenants
- Risco de violação de isolamento multi-tenant

**UniqueConstraints Desejadas**: 
- `uk_extrato_bancario_tenant_hash`: `{"tenant_id", "hash_linha"}` ✅

**Índices**: 
- `idx_extrato_bancario_conta_data`: `{"conta_financeira_id", "data"}` (deveria incluir tenant_id)

**Ação Requerida**: 
- **URGENTE**: Adicionar tenant_id ao unique constraint
- Atualizar índice para incluir tenant_id

---

### 18-27. Demais Entidades ✅

As seguintes entidades já estão corretas (herdam BaseEntity e têm constraints adequadas):

- `BaixaReceber` ✅
- `PagamentoPagar` ✅
- `TransferenciaEntreContas` ✅
- `RenegociacaoReceber` ✅
- `RegraClassificacaoContabil` ✅
- `RecorrenciaFinanceira` ✅
- `LogFinanceiro` ✅
- `LancamentoFinanceiroItem` ✅
- `ConciliacaoBancaria` ✅
- `ConciliacaoItem` ✅

**Ação Requerida**: 
- Nenhuma mudança estrutural necessária

---

## Tabelas Assistenciais que Referenciam Competência

### Agendamentos
- Campo: `competencia_financeira_id` (FK para `competencia_financeira`)
- Índice: `idx_agendamento_competencia` (apenas `competencia_financeira_id`)
- **Ação Requerida**: 
  - Após tenantização de CompetenciaFinanceira, verificar se FK continua válida
  - Atualizar índice para incluir tenant_id

### Atendimentos
- Campo: `competencia_financeira_id` (FK para `competencia_financeira`)
- Índice: `idx_atendimento_competencia` (apenas `competencia_financeira_id`)
- **Ação Requerida**: 
  - Após tenantização de CompetenciaFinanceira, verificar se FK continua válida
  - Atualizar índice para incluir tenant_id

---

## Resumo de Ações Necessárias

### Prioridade CRÍTICA (Segurança Multi-Tenant)

1. **CompetenciaFinanceira**: Alterar para `BaseEntity` e adicionar tenant_id a todas constraints/índices
2. **ExtratoBancarioImportado**: Adicionar tenant_id ao unique constraint

### Prioridade ALTA (Melhorias de Segurança)

3. **MovimentacaoConta**: Adicionar tenant_id ao unique constraint
4. **ContaContabil**: Considerar adicionar tenant_id aos índices (opcional, mas recomendado)

### Prioridade MÉDIA (Otimizações)

5. **ReservaOrcamentariaAssistencial**: Atualizar índice de agendamento para incluir tenant_id
6. **ContaContabil**: Atualizar índices para incluir tenant_id como primeiro componente
7. **MovimentacaoConta**: Atualizar índice para incluir tenant_id
8. **ExtratoBancarioImportado**: Atualizar índice para incluir tenant_id
9. **Agendamentos/Atendimentos**: Atualizar índices de competência para incluir tenant_id

### Prioridade BAIXA (Limpeza)

10. **CompetenciaFinanceiraTenant**: Marcar como deprecated e planejar remoção após migração

---

## Próximos Passos

1. ✅ **Concluído**: Inventário completo de entidades
2. ⏭️ **Próximo**: Definir modelo final de CompetenciaFinanceira tenant-scoped
3. ⏭️ **Depois**: Revisar todas as UniqueConstraints e Índices
4. ⏭️ **Depois**: Mapear impacto de relacionamentos com CompetenciaFinanceira
5. ⏭️ **Depois**: Gap analysis para entidades do setor público brasileiro
