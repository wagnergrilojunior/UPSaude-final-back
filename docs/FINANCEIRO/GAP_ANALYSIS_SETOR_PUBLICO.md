# Gap Analysis: Financeiro vs Necessidades de Órgãos Públicos Brasileiros

## Objetivo
Identificar lacunas no módulo Financeiro atual em relação às necessidades típicas de órgãos públicos brasileiros (prefeituras), especialmente no contexto de gestão financeira pública conforme legislação e práticas nacionais.

---

## Contexto: Sistema Atual

O sistema atual já possui uma base sólida para gestão financeira de prefeituras, com foco em:
- Controle orçamentário por competência
- Reserva/consumo/estorno de recursos
- Integração com produção ambulatorial (BPA)
- Títulos a receber/pagar
- Conciliação bancária
- Plano de contas contábil

---

## Análise por Domínio

### 1. Orçamento Público (PPA/LDO/LOA)

#### 1.1 O que já existe ✅

- **CompetenciaFinanceira**: Período de referência (mensal/custom)
- **OrcamentoCompetencia**: Saldo consolidado por competência
- **CreditoOrcamentario**: Créditos adicionais à competência

#### 1.2 O que falta ❌

**1.2.1 Plano Plurianual (PPA)**
- **Entidade Candidata**: `PlanoPlurianual` ou `PPA`
- **Relacionamento**: `tenant_id` (obrigatório), `estabelecimento_id` (opcional)
- **Campos Sugeridos**:
  - `anoInicio` (Integer)
  - `anoFim` (Integer)
  - `versao` (String)
  - `status` (String: RASCUNHO | APROVADO | VIGENTE | ENCERRADO)
  - `dataAprovacao` (LocalDate)
  - `observacoes` (TEXT)

**1.2.2 Lei de Diretrizes Orçamentárias (LDO)**
- **Entidade Candidata**: `LeiDiretrizesOrcamentarias` ou `LDO`
- **Relacionamento**: `tenant_id` (obrigatório), `ppa_id` (ManyToOne, opcional), `estabelecimento_id` (opcional)
- **Campos Sugeridos**:
  - `ano` (Integer)
  - `numeroLei` (String)
  - `dataPublicacao` (LocalDate)
  - `status` (String: RASCUNHO | ENVIADA | APROVADA | VIGENTE)
  - `metas` (JSONB ou entidade relacionada)
  - `prioridades` (JSONB ou entidade relacionada)

**1.2.3 Lei Orçamentária Anual (LOA)**
- **Entidade Candidata**: `LeiOrcamentariaAnual` ou `LOA`
- **Relacionamento**: `tenant_id` (obrigatório), `ldo_id` (ManyToOne, opcional), `estabelecimento_id` (opcional)
- **Campos Sugeridos**:
  - `ano` (Integer)
  - `numeroLei` (String)
  - `dataPublicacao` (LocalDate)
  - `status` (String: RASCUNHO | ENVIADA | APROVADA | VIGENTE | EXECUTADA)
  - `valorTotalReceita` (BigDecimal)
  - `valorTotalDespesa` (BigDecimal)

**1.2.4 Programas e Ações Orçamentárias**
- **Entidade Candidata**: `ProgramaOrcamentario` ou `AcaoOrcamentaria`
- **Relacionamento**: `tenant_id` (obrigatório), `loa_id` (ManyToOne), `estabelecimento_id` (opcional)
- **Campos Sugeridos**:
  - `codigo` (String) - Código do programa/ação (ex: "01.01.01")
  - `nome` (String)
  - `tipo` (String: PROGRAMA | ACAO | SUBACAO)
  - `valorPrevisto` (BigDecimal)
  - `valorExecutado` (BigDecimal)
  - `objetivo` (TEXT)
  - `indicadores` (JSONB)

**1.2.5 Unidade Orçamentária / Unidade Gestora**
- **Entidade Candidata**: `UnidadeOrcamentaria` ou `UnidadeGestora`
- **Relacionamento**: `tenant_id` (obrigatório), `estabelecimento_id` (opcional, pode ser 1:1)
- **Campos Sugeridos**:
  - `codigo` (String) - Código da unidade (ex: "01.001")
  - `nome` (String)
  - `tipo` (String: ORCAMENTARIA | GESTORA)
  - `responsavel` (String)
  - `ativo` (Boolean)

---

### 2. Execução da Despesa Pública

#### 2.1 O que já existe ✅

- **TituloPagar**: Títulos a pagar (fornecedores)
- **PagamentoPagar**: Pagamentos realizados
- **LancamentoFinanceiro**: Lançamentos contábeis
- **EstornoFinanceiro**: Estornos de despesas

#### 2.2 O que falta ❌

**2.2.1 Dotação Orçamentária**
- **Entidade Candidata**: `DotacaoOrcamentaria`
- **Relacionamento**: `tenant_id` (obrigatório), `competencia_id` (ManyToOne), `programa_id` (ManyToOne, opcional), `unidade_orcamentaria_id` (ManyToOne), `estabelecimento_id` (opcional)
- **Campos Sugeridos**:
  - `codigo` (String) - Código da dotação (classificação completa)
  - `valorDotado` (BigDecimal)
  - `valorEmpenhado` (BigDecimal)
  - `valorLiquidado` (BigDecimal)
  - `valorPago` (BigDecimal)
  - `valorDisponivel` (BigDecimal, calculado)
  - `tipo` (String: ORCAMENTARIA | EXTRA_ORCAMENTARIA)

**2.2.2 Empenho**
- **Entidade Candidata**: `Empenho`
- **Relacionamento**: `tenant_id` (obrigatório), `dotacao_id` (ManyToOne), `fornecedor_id` (ManyToOne para ParteFinanceira), `estabelecimento_id` (opcional)
- **Campos Sugeridos**:
  - `numero` (String) - Número do empenho
  - `tipo` (String: ORDINARIO | ESTIMATIVO | GLOBAL)
  - `dataEmissao` (LocalDate)
  - `valor` (BigDecimal)
  - `valorLiquidado` (BigDecimal)
  - `valorAnulado` (BigDecimal)
  - `status` (String: EMITIDO | LIQUIDADO | ANULADO | PAGO)
  - `observacoes` (TEXT)

**2.2.3 Liquidação**
- **Entidade Candidata**: `Liquidacao`
- **Relacionamento**: `tenant_id` (obrigatório), `empenho_id` (ManyToOne), `estabelecimento_id` (opcional)
- **Campos Sugeridos**:
  - `numero` (String)
  - `dataLiquidacao` (LocalDate)
  - `valor` (BigDecimal)
  - `tipo` (String: TOTAL | PARCIAL)
  - `status` (String: LIQUIDADO | ANULADO)
  - `observacoes` (TEXT)

**2.2.4 Pagamento (já existe, mas pode ser expandido)**
- **Entidade Existente**: `PagamentoPagar` ✅
- **Melhorias Sugeridas**:
  - Adicionar relacionamento com `Liquidacao`
  - Adicionar campo `numeroPagamento` (String) para controle sequencial
  - Adicionar campo `tipoPagamento` (String: ORDINARIO | ANTECIPADO | RESTOS_PAGAR)

**2.2.5 Restos a Pagar**
- **Entidade Candidata**: `RestosAPagar`
- **Relacionamento**: `tenant_id` (obrigatório), `empenho_id` (ManyToOne), `competencia_id` (ManyToOne), `estabelecimento_id` (opcional)
- **Campos Sugeridos**:
  - `tipo` (String: PROCESSADOS | NAO_PROCESSADOS)
  - `valor` (BigDecimal)
  - `dataInscricao` (LocalDate)
  - `dataCancelamento` (LocalDate, opcional)
  - `status` (String: INSCRITO | CANCELADO | PAGO)
  - `observacoes` (TEXT)

---

### 3. Classificação Orçamentária e Contábil

#### 3.1 O que já existe ✅

- **PlanoContas**: Planos de contas contábeis
- **ContaContabil**: Contas contábeis hierárquicas
- **RegraClassificacaoContabil**: Regras de classificação

#### 3.2 O que falta ❌

**3.2.1 Natureza da Despesa**
- **Entidade Candidata**: `NaturezaDespesa`
- **Relacionamento**: `tenant_id` (obrigatório), `estabelecimento_id` (opcional)
- **Campos Sugeridos**:
  - `codigo` (String) - Código completo (ex: "3.3.90.30.00")
  - `categoriaEconomica` (String) - Primeiro dígito (ex: "3")
  - `grupoNatureza` (String) - Segundo dígito (ex: "3")
  - `modalidadeAplicacao` (String) - Terceiro dígito (ex: "90")
  - `elementoDespesa` (String) - Quarto e quinto dígitos (ex: "30")
  - `subelemento` (String) - Sexto e sétimo dígitos (ex: "00")
  - `descricao` (String)
  - `ativo` (Boolean)

**3.2.2 Fonte de Recursos**
- **Entidade Candidata**: `FonteRecursos`
- **Relacionamento**: `tenant_id` (obrigatório), `estabelecimento_id` (opcional)
- **Campos Sugeridos**:
  - `codigo` (String) - Código da fonte (ex: "01.001")
  - `nome` (String)
  - `tipo` (String: RECEITA | DESPESA)
  - `descricao` (TEXT)
  - `ativo` (Boolean)

**3.2.3 Destinação de Recursos**
- **Entidade Candidata**: `DestinacaoRecursos`
- **Relacionamento**: `tenant_id` (obrigatório), `estabelecimento_id` (opcional)
- **Campos Sugeridos**:
  - `codigo` (String)
  - `nome` (String)
  - `descricao` (TEXT)
  - `ativo` (Boolean)

**3.2.4 Elemento de Despesa (detalhamento)**
- **Entidade Candidata**: `ElementoDespesa`
- **Relacionamento**: `tenant_id` (obrigatório), `natureza_despesa_id` (ManyToOne), `estabelecimento_id` (opcional)
- **Campos Sugeridos**:
  - `codigo` (String) - Código do elemento
  - `nome` (String)
  - `descricao` (TEXT)
  - `tipo` (String: PESSOAL | MATERIAL | SERVICOS | OBRAS | OUTROS)
  - `ativo` (Boolean)

**3.2.5 Modalidade de Aplicação**
- **Entidade Candidata**: `ModalidadeAplicacao`
- **Relacionamento**: `tenant_id` (obrigatório), `estabelecimento_id` (opcional)
- **Campos Sugeridos**:
  - `codigo` (String)
  - `nome` (String)
  - `descricao` (TEXT)
  - `ativo` (Boolean)

---

### 4. Contabilidade Pública (PCASP)

#### 4.1 O que já existe ✅

- **PlanoContas**: Base para plano de contas
- **ContaContabil**: Contas hierárquicas
- **LancamentoFinanceiro**: Lançamentos contábeis
- **LancamentoFinanceiroItem**: Itens de lançamento

#### 4.2 O que falta ❌

**4.2.1 Eventos Contábeis**
- **Entidade Candidata**: `EventoContabil`
- **Relacionamento**: `tenant_id` (obrigatório), `competencia_id` (ManyToOne), `estabelecimento_id` (opcional)
- **Campos Sugeridos**:
  - `codigo` (String) - Código do evento conforme PCASP
  - `nome` (String)
  - `tipo` (String: ORCAMENTARIO | PATRIMONIAL | COMPENSADO)
  - `dataEvento` (LocalDate)
  - `descricao` (TEXT)
  - `lancamentos` (OneToMany para LancamentoFinanceiro)

**4.2.2 Plano de Contas Aplicado ao Setor Público (PCASP)**
- **Entidade Candidata**: `PlanoContasPCASP` (pode ser extensão de PlanoContas)
- **Relacionamento**: `tenant_id` (obrigatório), `estabelecimento_id` (opcional)
- **Campos Sugeridos**:
  - `versaoPCASP` (String) - Versão do PCASP (ex: "2024")
  - `dataVigencia` (LocalDate)
  - `tipo` (String: PATRIMONIAL | ORCAMENTARIO | CONTROLE | COMPENSADO)
  - Campos herdados de PlanoContas

**4.2.3 Balanços e Demonstrações Contábeis**
- **Entidade Candidata**: `BalancoContabil` ou `DemonstracaoContabil`
- **Relacionamento**: `tenant_id` (obrigatório), `competencia_id` (ManyToOne), `estabelecimento_id` (opcional)
- **Campos Sugeridos**:
  - `tipo` (String: BALANCO_PATRIMONIAL | BALANCO_ORCAMENTARIO | DRE | DMPL | DVA)
  - `dataReferencia` (LocalDate)
  - `status` (String: RASCUNHO | CONSOLIDADO | PUBLICADO)
  - `dados` (JSONB) - Estrutura dos dados do balanço
  - `observacoes` (TEXT)

---

### 5. Receita Pública

#### 5.1 O que já existe ✅

- **TituloReceber**: Títulos a receber
- **BaixaReceber**: Baixas de recebimento
- **LancamentoFinanceiro**: Lançamentos de receita

#### 5.2 O que falta ❌

**5.2.1 Previsão de Receita**
- **Entidade Candidata**: `PrevisaoReceita`
- **Relacionamento**: `tenant_id` (obrigatório), `competencia_id` (ManyToOne), `fonte_recursos_id` (ManyToOne), `estabelecimento_id` (opcional)
- **Campos Sugeridos**:
  - `categoria` (String: CORRENTE | CAPITAL)
  - `origem` (String: TRIBUTARIA | CONTRIBUICOES | PATRIMONIAL | AGROPECUARIA | INDUSTRIAL | SERVICOS | TRANSFERENCIAS | OUTRAS)
  - `valorPrevisto` (BigDecimal)
  - `valorArrecadado` (BigDecimal)
  - `observacoes` (TEXT)

**5.2.2 Arrecadação de Receita**
- **Entidade Candidata**: `ArrecadacaoReceita`
- **Relacionamento**: `tenant_id` (obrigatório), `previsao_receita_id` (ManyToOne), `titulo_receber_id` (ManyToOne, opcional), `estabelecimento_id` (opcional)
- **Campos Sugeridos**:
  - `numeroArrecadacao` (String)
  - `dataArrecadacao` (LocalDate)
  - `valor` (BigDecimal)
  - `tipo` (String: ORDINARIA | EXTRAORDINARIA)
  - `observacoes` (TEXT)

---

### 6. Controle e Auditoria

#### 6.1 O que já existe ✅

- **LogFinanceiro**: Logs de operações financeiras
- Campos de auditoria em BaseEntity (createdAt, updatedAt)

#### 6.2 O que falta ❌

**6.2.1 Processo de Auditoria**
- **Entidade Candidata**: `ProcessoAuditoria`
- **Relacionamento**: `tenant_id` (obrigatório), `estabelecimento_id` (opcional)
- **Campos Sugeridos**:
  - `numero` (String)
  - `tipo` (String: INTERNA | EXTERNA | TCE | CGU)
  - `dataInicio` (LocalDate)
  - `dataFim` (LocalDate, opcional)
  - `status` (String: ABERTA | EM_ANDAMENTO | CONCLUIDA | CANCELADA)
  - `observacoes` (TEXT)
  - `recomendacoes` (TEXT)

**6.2.2 Tomada de Contas**
- **Entidade Candidata**: `TomadaContas`
- **Relacionamento**: `tenant_id` (obrigatório), `responsavel_id` (ManyToOne para UsuarioSistema), `competencia_id` (ManyToOne), `estabelecimento_id` (opcional)
- **Campos Sugeridos**:
  - `numero` (String)
  - `dataAbertura` (LocalDate)
  - `dataEncerramento` (LocalDate, opcional)
  - `status` (String: ABERTA | EM_ANALISE | APROVADA | REJEITADA)
  - `observacoes` (TEXT)

---

## Resumo de Entidades Novas Recomendadas

### Prioridade ALTA (Essenciais para órgão público)

1. **DotacaoOrcamentaria** - Controle de dotação orçamentária
2. **Empenho** - Empenho de despesa
3. **Liquidacao** - Liquidação de empenho
4. **NaturezaDespesa** - Classificação da natureza da despesa
5. **FonteRecursos** - Fonte de recursos

### Prioridade MÉDIA (Importantes para gestão completa)

6. **ProgramaOrcamentario** - Programas e ações orçamentárias
7. **UnidadeOrcamentaria** - Unidades orçamentárias/gestoras
8. **RestosAPagar** - Restos a pagar
9. **PrevisaoReceita** - Previsão de receita
10. **ArrecadacaoReceita** - Arrecadação de receita

### Prioridade BAIXA (Melhorias e complementos)

11. **PlanoPlurianual** (PPA) - Plano plurianual
12. **LeiDiretrizesOrcamentarias** (LDO) - Lei de diretrizes orçamentárias
13. **LeiOrcamentariaAnual** (LOA) - Lei orçamentária anual
14. **ElementoDespesa** - Detalhamento de elementos
15. **ModalidadeAplicacao** - Modalidades de aplicação
16. **DestinacaoRecursos** - Destinação de recursos
17. **EventoContabil** - Eventos contábeis (PCASP)
18. **BalancoContabil** - Balanços e demonstrações
19. **ProcessoAuditoria** - Processos de auditoria
20. **TomadaContas** - Tomada de contas

---

## Modelagem Multi-Tenant para Novas Entidades

### Padrão Recomendado

Todas as novas entidades devem seguir o padrão:

```java
@Entity
@Table(
    name = "nova_entidade",
    schema = "public",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_nova_entidade_tenant_codigo", 
                         columnNames = {"tenant_id", "codigo"})
    },
    indexes = {
        @Index(name = "idx_nova_entidade_tenant_status", 
               columnList = "tenant_id, status")
    }
)
public class NovaEntidade extends BaseEntity {
    // tenant e estabelecimento herdados de BaseEntity
    // campos específicos da entidade
}
```

### Regras de Relacionamento

1. **Tenant**: Sempre obrigatório (via BaseEntity)
2. **Estabelecimento**: Sempre opcional (via BaseEntity)
3. **UniqueConstraints**: Sempre incluir `tenant_id` como primeiro campo
4. **Índices**: Sempre iniciar com `tenant_id` como primeiro componente
5. **Relacionamentos com CompetenciaFinanceira**: Sempre validar que competência pertence ao mesmo tenant

---

## Próximos Passos

1. ✅ **Concluído**: Gap analysis completo
2. ⏭️ **Próximo**: Priorizar entidades para implementação
3. ⏭️ **Depois**: Criar modelos detalhados das entidades prioritárias
4. ⏭️ **Depois**: Criar migrações SQL para novas tabelas

---

## Referências

- **PCASP**: Plano de Contas Aplicado ao Setor Público
- **PPA**: Plano Plurianual (4 anos)
- **LDO**: Lei de Diretrizes Orçamentárias (anual)
- **LOA**: Lei Orçamentária Anual
- **Classificação Orçamentária**: Portaria STN/MF nº 163/2001 e atualizações
- **Execução da Despesa**: Lei 4.320/64 e Decreto 93.872/86
