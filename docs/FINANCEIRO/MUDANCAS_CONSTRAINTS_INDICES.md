# Mudanças em Constraints e Índices - Multi-Tenant

## Resumo das Alterações

Este documento lista todas as mudanças realizadas em UniqueConstraints e Índices para garantir isolamento multi-tenant adequado.

---

## UniqueConstraints Alteradas

### 1. CompetenciaFinanceira ✅ **ALTERADO**

**Antes**:
```java
@UniqueConstraint(name = "uk_competencia_financeira_codigo", columnNames = {"codigo"})
```

**Depois**:
```java
@UniqueConstraint(name = "uk_competencia_financeira_tenant_codigo", columnNames = {"tenant_id", "codigo"})
```

**Motivo**: Código (AAAAMM) deve ser único apenas dentro do tenant, permitindo que diferentes prefeituras tenham competências com o mesmo código.

---

### 2. ExtratoBancarioImportado ✅ **ALTERADO**

**Antes**:
```java
@UniqueConstraint(name = "uk_extrato_bancario_hash", columnNames = {"hash_linha"})
```

**Depois**:
```java
@UniqueConstraint(name = "uk_extrato_bancario_tenant_hash", columnNames = {"tenant_id", "hash_linha"})
```

**Motivo**: Hash pode colidir entre diferentes tenants. Risco crítico de violação de isolamento multi-tenant.

---

### 3. MovimentacaoConta ✅ **ALTERADO**

**Antes**:
```java
@UniqueConstraint(name = "uk_movimentacao_conta_conta_idempotency", columnNames = {"conta_financeira_id", "idempotency_key"})
```

**Depois**:
```java
@UniqueConstraint(name = "uk_movimentacao_conta_tenant_conta_idempotency", columnNames = {"tenant_id", "conta_financeira_id", "idempotency_key"})
```

**Motivo**: Melhorar segurança explícita multi-tenant, mesmo que ContaFinanceira já tenha tenant.

---

## Índices Alterados

### 1. CompetenciaFinanceira ✅ **ALTERADO**

**Antes**:
```java
@Index(name = "idx_competencia_financeira_data", columnList = "data_inicio, data_fim")
```

**Depois**:
```java
@Index(name = "idx_competencia_financeira_tenant_data", columnList = "tenant_id, data_inicio, data_fim")
@Index(name = "idx_competencia_financeira_tenant_status", columnList = "tenant_id, status")
```

**Motivo**: 
- Queries sempre filtram por tenant primeiro
- Novo índice para status (campo adicionado)

---

### 2. ExtratoBancarioImportado ✅ **ALTERADO**

**Antes**:
```java
@Index(name = "idx_extrato_bancario_conta_data", columnList = "conta_financeira_id, data")
```

**Depois**:
```java
@Index(name = "idx_extrato_bancario_tenant_conta_data", columnList = "tenant_id, conta_financeira_id, data")
```

**Motivo**: Otimizar queries que sempre filtram por tenant primeiro.

---

### 3. MovimentacaoConta ✅ **ALTERADO**

**Antes**:
```java
@Index(name = "idx_movimentacao_conta_data", columnList = "conta_financeira_id, data_movimento")
```

**Depois**:
```java
@Index(name = "idx_movimentacao_conta_tenant_data", columnList = "tenant_id, conta_financeira_id, data_movimento")
```

**Motivo**: Otimizar queries que sempre filtram por tenant primeiro.

---

### 4. ReservaOrcamentariaAssistencial ✅ **ALTERADO**

**Antes**:
```java
@Index(name = "idx_reserva_orcamentaria_agendamento", columnList = "agendamento_id")
```

**Depois**:
```java
@Index(name = "idx_reserva_orcamentaria_tenant_agendamento", columnList = "tenant_id, agendamento_id")
```

**Motivo**: Otimizar queries que sempre filtram por tenant primeiro.

---

### 5. ContaContabil ✅ **ALTERADO**

**Antes**:
```java
@Index(name = "idx_conta_contabil_plano", columnList = "plano_contas_id")
@Index(name = "idx_conta_contabil_pai", columnList = "plano_contas_id, conta_pai_id")
```

**Depois**:
```java
@Index(name = "idx_conta_contabil_tenant_plano", columnList = "tenant_id, plano_contas_id")
@Index(name = "idx_conta_contabil_tenant_pai", columnList = "tenant_id, plano_contas_id, conta_pai_id")
```

**Motivo**: Otimizar queries que sempre filtram por tenant primeiro, mesmo que PlanoContas já tenha tenant.

---

## UniqueConstraints que NÃO Precisam Mudar (Já Corretas)

As seguintes entidades já têm tenant_id em suas UniqueConstraints:

- ✅ `OrcamentoCompetencia`: `{"tenant_id", "competencia_id"}`
- ✅ `CreditoOrcamentario`: `{"tenant_id", "idempotency_key"}`
- ✅ `TituloPagar`: `{"tenant_id", "idempotency_key"}`
- ✅ `TituloReceber`: `{"tenant_id", "idempotency_key"}`
- ✅ `PlanoContas`: `{"tenant_id", "nome", "versao"}`
- ✅ `ContaFinanceira`: `{"tenant_id", "tipo", "nome"}`
- ✅ `CentroCusto`: `{"tenant_id", "codigo"}`
- ✅ `ParteFinanceira`: `{"tenant_id", "tipo", "documento"}`
- ✅ `LancamentoFinanceiro`: `{"tenant_id", "idempotency_key"}`
- ✅ `GuiaAtendimentoAmbulatorial`: `{"tenant_id", "competencia_id", "numero"}`
- ✅ `PagamentoPagar`: `{"tenant_id", "idempotency_key"}`
- ✅ `BaixaReceber`: `{"tenant_id", "idempotency_key"}`
- ✅ `TransferenciaEntreContas`: `{"tenant_id", "idempotency_key"}`

---

## UniqueConstraints que Podem Ser Melhoradas (Opcional)

### ContaContabil ⚠️ **OPCIONAL**

**Atual**:
```java
@UniqueConstraint(name = "uk_conta_contabil_plano_codigo", columnNames = {"plano_contas_id", "codigo"})
```

**Análise**: Como `PlanoContas` já tem tenant obrigatório, o unique está protegido indiretamente. Funcionalmente correto, mas poderia ser mais explícito.

**Recomendação**: Manter como está (funcionalmente correto), mas considerar adicionar tenant_id para clareza em futuras refatorações.

---

## Impacto no Banco de Dados

Todas essas mudanças exigirão migração SQL para:

1. **Dropar constraints antigas**
2. **Criar constraints novas**
3. **Dropar índices antigos**
4. **Criar índices novos**
5. **Migrar dados existentes** (especialmente para CompetenciaFinanceira)

**Nota**: As migrações SQL serão criadas em etapa posterior (fora do escopo desta fase de entidades).

---

## Checklist de Validação

- [x] CompetenciaFinanceira: unique constraint atualizada
- [x] CompetenciaFinanceira: índices atualizados
- [x] ExtratoBancarioImportado: unique constraint atualizada
- [x] ExtratoBancarioImportado: índice atualizado
- [x] MovimentacaoConta: unique constraint atualizada
- [x] MovimentacaoConta: índice atualizado
- [x] ReservaOrcamentariaAssistencial: índice atualizado
- [x] ContaContabil: índices atualizados
- [x] Todas as outras entidades verificadas (já corretas)

---

## Próximos Passos

1. ✅ **Concluído**: Revisão de todas as UniqueConstraints
2. ✅ **Concluído**: Revisão de todos os Índices
3. ⏭️ **Próximo**: Mapear impacto de relacionamentos com CompetenciaFinanceira
4. ⏭️ **Depois**: Criar migrações SQL para aplicar mudanças no banco
