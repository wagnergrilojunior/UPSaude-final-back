# Impacto de Relacionamentos com CompetenciaFinanceira após Tenantização

## Objetivo
Mapear todas as entidades e tabelas que referenciam `CompetenciaFinanceira` e identificar os ajustes necessários após a tenantização desta entidade.

---

## Resumo Executivo

### Entidades que Referenciam CompetenciaFinanceira

**Total**: 9 entidades/tabelas

**Categorias**:
- **Entidades Financeiras**: 7 entidades
- **Entidades Assistenciais**: 2 entidades (Agendamento, Atendimento)
- **Entidades de Faturamento**: 1 entidade (DocumentoFaturamento)

### Status dos Relacionamentos

✅ **Bom**: Todas as entidades que referenciam CompetenciaFinanceira **já têm tenant** (herdam BaseEntity ou têm tenant_id explícito).

⚠️ **Atenção**: Após tenantização, será necessário garantir que:
1. As FKs continuem válidas (competência deve pertencer ao mesmo tenant)
2. Os índices sejam atualizados para incluir tenant_id
3. As queries sempre filtrem por tenant ao buscar competências

---

## Mapeamento Detalhado

### 1. Entidades Financeiras

#### 1.1 OrcamentoCompetencia ✅

**Arquivo**: `src/main/java/com/upsaude/entity/financeiro/OrcamentoCompetencia.java`

**Relacionamento**:
```java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "competencia_id", nullable = false)
private CompetenciaFinanceira competencia;
```

**Tenant da Entidade**: ✅ Sim (herda BaseEntity)

**UniqueConstraint**: `{"tenant_id", "competencia_id"}` ✅

**Análise**: 
- Já tem unique constraint que garante uma competência por tenant
- Após tenantização, será necessário garantir que a competência referenciada pertença ao mesmo tenant
- **Ação**: Validação em serviço/repositório para garantir que `competencia.tenant.id == orcamento.tenant.id`

**Índices**: 
- `idx_orcamento_competencia_competencia`: `{"tenant_id", "competencia_id"}` ✅ (já correto)

**Ação Requerida**: 
- ✅ Nenhuma mudança estrutural necessária
- ⚠️ Adicionar validação de tenant em serviços/repositórios

---

#### 1.2 CreditoOrcamentario ✅

**Arquivo**: `src/main/java/com/upsaude/entity/financeiro/CreditoOrcamentario.java`

**Relacionamento**:
```java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "competencia_id", nullable = false)
private CompetenciaFinanceira competencia;
```

**Tenant da Entidade**: ✅ Sim (herda BaseEntity)

**Análise**: 
- Após tenantização, será necessário garantir que a competência referenciada pertença ao mesmo tenant
- **Ação**: Validação em serviço/repositório

**Índices**: 
- `idx_credito_orcamentario_competencia`: `{"tenant_id", "competencia_id"}` ✅ (já correto)

**Ação Requerida**: 
- ✅ Nenhuma mudança estrutural necessária
- ⚠️ Adicionar validação de tenant em serviços/repositórios

---

#### 1.3 ReservaOrcamentariaAssistencial ✅

**Arquivo**: `src/main/java/com/upsaude/entity/financeiro/ReservaOrcamentariaAssistencial.java`

**Relacionamento**:
```java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "competencia_id", nullable = false)
private CompetenciaFinanceira competencia;
```

**Tenant da Entidade**: ✅ Sim (herda BaseEntity)

**Análise**: 
- Após tenantização, será necessário garantir que a competência referenciada pertença ao mesmo tenant
- **Ação**: Validação em serviço/repositório

**Índices**: 
- `idx_reserva_orcamentaria_competencia`: `{"tenant_id", "competencia_id", "status"}` ✅ (já correto)

**Ação Requerida**: 
- ✅ Nenhuma mudança estrutural necessária
- ⚠️ Adicionar validação de tenant em serviços/repositórios

---

#### 1.4 LancamentoFinanceiro ✅

**Arquivo**: `src/main/java/com/upsaude/entity/financeiro/LancamentoFinanceiro.java`

**Relacionamento**:
```java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "competencia_id", nullable = false)
private CompetenciaFinanceira competencia;
```

**Tenant da Entidade**: ✅ Sim (herda BaseEntity)

**Análise**: 
- Após tenantização, será necessário garantir que a competência referenciada pertença ao mesmo tenant
- **Ação**: Validação em serviço/repositório

**Índices**: 
- `idx_lancamento_financeiro_competencia`: `{"tenant_id", "competencia_id", "data_evento"}` ✅ (já correto)

**Ação Requerida**: 
- ✅ Nenhuma mudança estrutural necessária
- ⚠️ Adicionar validação de tenant em serviços/repositórios

---

#### 1.5 EstornoFinanceiro ✅

**Arquivo**: `src/main/java/com/upsaude/entity/financeiro/EstornoFinanceiro.java`

**Relacionamento**:
```java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "competencia_id", nullable = false)
private CompetenciaFinanceira competencia;
```

**Tenant da Entidade**: ✅ Sim (herda BaseEntity)

**Análise**: 
- Após tenantização, será necessário garantir que a competência referenciada pertença ao mesmo tenant
- **Ação**: Validação em serviço/repositório

**Índices**: 
- `idx_estorno_financeiro_competencia_motivo`: `{"tenant_id", "competencia_id", "motivo"}` ✅ (já correto)

**Ação Requerida**: 
- ✅ Nenhuma mudança estrutural necessária
- ⚠️ Adicionar validação de tenant em serviços/repositórios

---

#### 1.6 GuiaAtendimentoAmbulatorial ✅

**Arquivo**: `src/main/java/com/upsaude/entity/financeiro/GuiaAtendimentoAmbulatorial.java`

**Relacionamento**:
```java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "competencia_id", nullable = false)
private CompetenciaFinanceira competencia;
```

**Tenant da Entidade**: ✅ Sim (herda BaseEntity)

**UniqueConstraint**: `{"tenant_id", "competencia_id", "numero"}` ✅

**Análise**: 
- Já tem unique constraint que inclui tenant e competência
- Após tenantização, será necessário garantir que a competência referenciada pertença ao mesmo tenant
- **Ação**: Validação em serviço/repositório

**Índices**: 
- `idx_guia_ambulatorial_status`: `{"tenant_id", "competencia_id", "status"}` ✅ (já correto)

**Ação Requerida**: 
- ✅ Nenhuma mudança estrutural necessária
- ⚠️ Adicionar validação de tenant em serviços/repositórios

---

#### 1.7 CompetenciaFinanceiraTenant ⚠️ **DEPRECATED**

**Arquivo**: `src/main/java/com/upsaude/entity/financeiro/CompetenciaFinanceiraTenant.java`

**Relacionamento**:
```java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "competencia_id", nullable = false)
private CompetenciaFinanceira competencia;
```

**Status**: ⚠️ **DEPRECATED** - Esta entidade será descontinuada após migração.

**Ação Requerida**: 
- ⚠️ Migrar dados para CompetenciaFinanceira
- ⚠️ Remover referências em código após migração

---

### 2. Entidades Assistenciais

#### 2.1 Agendamento ✅

**Arquivo**: `src/main/java/com/upsaude/entity/agendamento/Agendamento.java`

**Relacionamento**:
```java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "competencia_financeira_id")
private CompetenciaFinanceira competenciaFinanceira;
```

**Tenant da Entidade**: ✅ Sim (herda BaseEntity)

**Índice Atual**: 
- `idx_agendamento_competencia`: `{"competencia_financeira_id"}` ⚠️ (deve incluir tenant_id)

**Análise**: 
- Após tenantização, será necessário garantir que a competência referenciada pertença ao mesmo tenant
- **Ação**: 
  - Validação em serviço/repositório
  - Atualizar índice para incluir tenant_id

**Ação Requerida**: 
- ⚠️ Atualizar índice: `idx_agendamento_tenant_competencia`: `{"tenant_id", "competencia_financeira_id"}`
- ⚠️ Adicionar validação de tenant em serviços/repositórios

---

#### 2.2 Atendimento ✅

**Arquivo**: `src/main/java/com/upsaude/entity/clinica/atendimento/Atendimento.java`

**Relacionamento**:
```java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "competencia_financeira_id")
private CompetenciaFinanceira competenciaFinanceira;
```

**Tenant da Entidade**: ✅ Sim (herda BaseEntity)

**Índice Atual**: 
- `idx_atendimento_competencia`: `{"competencia_financeira_id"}` ⚠️ (deve incluir tenant_id)

**Análise**: 
- Após tenantização, será necessário garantir que a competência referenciada pertença ao mesmo tenant
- **Ação**: 
  - Validação em serviço/repositório
  - Atualizar índice para incluir tenant_id

**Ação Requerida**: 
- ⚠️ Atualizar índice: `idx_atendimento_tenant_competencia`: `{"tenant_id", "competencia_financeira_id"}`
- ⚠️ Adicionar validação de tenant em serviços/repositórios

---

### 3. Entidades de Faturamento

#### 3.1 DocumentoFaturamento ✅

**Arquivo**: `src/main/java/com/upsaude/entity/faturamento/DocumentoFaturamento.java`

**Relacionamento**:
```java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "competencia_id", nullable = false)
private CompetenciaFinanceira competencia;
```

**Tenant da Entidade**: ✅ Sim (herda BaseEntity)

**Análise**: 
- Após tenantização, será necessário garantir que a competência referenciada pertença ao mesmo tenant
- **Ação**: Validação em serviço/repositório

**Ação Requerida**: 
- ✅ Nenhuma mudança estrutural necessária (índices não verificados neste escopo)
- ⚠️ Adicionar validação de tenant em serviços/repositórios

---

## Resumo de Ações Necessárias

### Mudanças Estruturais (Banco de Dados)

1. **Agendamento**: Atualizar índice `idx_agendamento_competencia` para incluir `tenant_id`
2. **Atendimento**: Atualizar índice `idx_atendimento_competencia` para incluir `tenant_id`

### Validações em Código (Serviços/Repositórios)

Todas as entidades que referenciam CompetenciaFinanceira precisarão de validação para garantir que:
- A competência referenciada pertença ao mesmo tenant da entidade
- Isso deve ser feito em:
  - Criadores (Creators)
  - Atualizadores (Updaters)
  - Repositórios (queries customizadas)

**Entidades Afetadas**:
1. OrcamentoCompetencia
2. CreditoOrcamentario
3. ReservaOrcamentariaAssistencial
4. LancamentoFinanceiro
5. EstornoFinanceiro
6. GuiaAtendimentoAmbulatorial
7. Agendamento
8. Atendimento
9. DocumentoFaturamento

### Migração de Dados

1. **CompetenciaFinanceiraTenant**: 
   - Migrar dados de status/fechamento para CompetenciaFinanceira
   - Atualizar referências em código
   - Planejar remoção da tabela após migração completa

---

## Estratégia de Validação Recomendada

### Opção 1: Validação Explícita em Serviços (Recomendada)

Criar método utilitário ou adicionar validação em cada Creator/Updater:

```java
private void validarCompetenciaDoTenant(UUID competenciaId, UUID tenantId) {
    CompetenciaFinanceira competencia = competenciaRepository
        .findByIdAndTenant(competenciaId, tenantId)
        .orElseThrow(() -> new BadRequestException(
            "Competência não encontrada ou não pertence ao tenant"));
}
```

### Opção 2: Constraint no Banco (Complementar)

Adicionar constraint CHECK ou trigger no banco para garantir integridade referencial multi-tenant (opcional, mas recomendado para segurança adicional).

---

## Próximos Passos

1. ✅ **Concluído**: Mapeamento de todos os relacionamentos
2. ⏭️ **Próximo**: Gap analysis para entidades do setor público brasileiro
3. ⏭️ **Depois**: Criar migrações SQL para aplicar mudanças estruturais
4. ⏭️ **Depois**: Implementar validações de tenant em serviços/repositórios
