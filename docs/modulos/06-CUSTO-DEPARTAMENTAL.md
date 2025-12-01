# Módulo: Custo Departamental

## 📋 Visão Geral (Para Product Owner)

O módulo de Custo Departamental permite calcular e analisar os custos por centro de custo, departamento e unidade de negócio, fornecendo informações essenciais para gestão financeira e tomada de decisão estratégica. Este módulo é fundamental para entender onde os recursos estão sendo aplicados e otimizar a alocação de custos.

### Objetivo de Negócio
- Calcular custos por departamento/centro de custo
- Ratear custos indiretos de forma justa
- Fornecer base para precificação de procedimentos
- Apoiar decisões de investimento e otimização
- Gerar relatórios gerenciais de custos

### Benefícios
- Transparência nos custos
- Base sólida para precificação
- Identificação de oportunidades de economia
- Apoio à tomada de decisão
- Conformidade com normas contábeis

---

## 🎯 Funcionalidades Necessárias

### 1. Cadastros Básicos

#### 1.1 Cadastro de Unidades de Negócio
- **Descrição**: Unidades organizacionais principais
- **Campos necessários**:
  - Código
  - Nome
  - Descrição
  - Status (ativa/inativa)

#### 1.2 Cadastro de Sub-Unidades de Negócio
- **Descrição**: Subdivisões das unidades de negócio
- **Campos necessários**:
  - Código
  - Nome
  - Unidade de negócio pai
  - Descrição

#### 1.3 Cadastro de Grupos de Centros de Custo
- **Descrição**: Agrupamento lógico de centros de custo
- **Campos necessários**:
  - Código
  - Nome
  - Descrição

#### 1.4 Cadastro de Sub-Grupos de Centros de Custo
- **Descrição**: Subdivisão dos grupos
- **Campos necessários**:
  - Código
  - Nome
  - Grupo pai
  - Descrição

#### 1.5 Cadastro de Centros de Custo
- **Descrição**: Centros de custo hierarquizados
- **Estrutura hierárquica**:
  - Unidade de Negócio
    - Grupo de Centro de Custo
      - Sub-Grupo de Centro de Custo
        - Centro de Custo
- **Classificação**:
  - Administrativo
  - Auxiliar
  - Produtivo
- **Campos necessários**:
  - Código
  - Nome
  - Descrição
  - Hierarquia completa
  - Classificação
  - Unidade de produção vinculada
  - Status

#### 1.6 Cadastro de Moedas
- **Descrição**: Moedas para registro de valores
- **Campos necessários**:
  - Código (ISO)
  - Nome
  - Símbolo
  - Taxa de conversão (para moeda estrangeira)

#### 1.7 Cadastro de Itens de Receitas Financeiras
- **Descrição**: Itens que geram receita
- **Campos necessários**:
  - Código
  - Descrição
  - Tipo de receita
  - Centro de custo vinculado

#### 1.8 Cadastro de Grupos de Itens de Custo
- **Descrição**: Agrupamento de itens de custo
- **Campos necessários**:
  - Código
  - Nome
  - Descrição

#### 1.9 Cadastro de Itens de Custo
- **Descrição**: Itens que compõem os custos
- **Campos necessários**:
  - Código
  - Descrição
  - Grupo de itens de custo
  - Tipo (direto, indireto)
  - Unidade de medida
  - Valor padrão (opcional)

#### 1.10 Cadastro de Índices Econômicos
- **Descrição**: Índices para reajustes de amortizações
- **Exemplos**: IPCA, IGPM, INPC
- **Campos necessários**:
  - Código
  - Nome
  - Descrição
  - Valores históricos por mês/ano

#### 1.11 Cadastro de Pesos das Unidades de Produção
- **Descrição**: Pesos relativos para rateio
- **Campos necessários**:
  - Unidade de produção
  - Peso
  - Período de vigência

#### 1.12 Cadastro de Critérios de Rateio
- **Descrição**: Critérios para rateio de custos
- **Exemplos**: Por área, por número de funcionários, por produção
- **Campos necessários**:
  - Código
  - Nome
  - Descrição
  - Tipo de critério

#### 1.13 Cadastro de Bases de Rateio
- **Descrição**: Bases utilizadas para rateio
- **Campos necessários**:
  - Código
  - Nome
  - Descrição
  - Critério de rateio vinculado

#### 1.14 Cadastro de Unidades de Produção
- **Descrição**: Unidades que produzem serviços/produtos
- **Campos necessários**:
  - Código
  - Nome
  - Descrição
  - Centro de custo vinculado

#### 1.15 Cadastro de Itens de Produção
- **Descrição**: Itens produzidos pelas unidades
- **Campos necessários**:
  - Código
  - Nome
  - Descrição
  - Unidade de produção
  - Unidade de medida

### 2. Configurações de Rateio

#### 2.1 Configuração de Rateio por Acomodação
- **Descrição**: Definir forma de rateio por tipo de acomodação
- **Funcionalidade**: Configurar como custos são rateados entre diferentes tipos de acomodação

#### 2.2 Configuração de Critério de Rateio por Centro de Custo
- **Descrição**: Definir critério de rateio para cada centro de custo administrativo e auxiliar
- **Funcionalidade**: Especificar como custos indiretos são distribuídos

#### 2.3 Configuração de Unidade de Produção por Centro de Custo
- **Descrição**: Vincular unidade de produção a cada centro de custo
- **Funcionalidade**: Definir o que cada centro de custo produz

#### 2.4 Configuração de Custos Fixos e Variáveis
- **Descrição**: Definir custos fixos e variáveis de cada centro de custo
- **Funcionalidade**: Permitir que item seja parte fixa e parte variável
- **Campos**:
  - Item de custo
  - Centro de custo
  - Percentual fixo
  - Percentual variável

#### 2.5 Configuração de Custos Diretos
- **Descrição**: Definir custos diretos de cada centro de custos
- **Funcionalidade**: Custos que podem ser atribuídos diretamente

#### 2.6 Configuração de Base de Rateio para Itens Indiretos
- **Descrição**: Definir base de rateio para cada item de custo indireto
- **Funcionalidade**: Como distribuir custos indiretos

#### 2.7 Configuração de Itens de Custo Opcionais
- **Descrição**: Itens opcionais para composição de custos
- **Funcionalidade**: Incluir ou excluir itens específicos

#### 2.8 Configuração de Itens Gerados Automaticamente
- **Descrição**: Itens gerados a partir de outro item
- **Exemplo**: Encargos trabalhistas gerados a partir de salários
- **Funcionalidade**: Cálculo automático de itens derivados

#### 2.9 Configuração de Comandos SQL
- **Descrição**: Definir comandos SQL para buscar informações de outros módulos
- **Funcionalidade**: Integração com outros sistemas/módulos

### 3. Lançamentos

#### 3.1 Informar Cotação das Moedas
- **Descrição**: Registrar cotação das moedas no mês
- **Funcionalidade**: Atualizar taxas de conversão

#### 3.2 Informar Receita
- **Descrição**: Registrar receita de cada centro de custo
- **Funcionalidade**: Lançar receitas por período

#### 3.3 Lançar Custos
- **Descrição**: Registrar custos por centro de custo
- **Funcionalidade**: Lançar custos diretos e indiretos

### 4. Cálculos e Apurações

#### 4.1 Apuração de Custos por Centro de Custo
- **Descrição**: Calcular custos totais e unitários
- **Processo**:
  1. Somar custos diretos
  2. Ratear custos indiretos
  3. Calcular custo total
  4. Calcular custo unitário (se houver unidade de produção)

#### 4.2 Rateio de Custos Indiretos
- **Descrição**: Distribuir custos indiretos conforme critérios
- **Processo**:
  1. Identificar custos indiretos
  2. Aplicar critérios de rateio
  3. Distribuir proporcionalmente

#### 4.3 Cálculo de Custos Fixos e Variáveis
- **Descrição**: Separar custos fixos e variáveis
- **Uso**: Análise de margem de contribuição

---

## 📐 Arquitetura e Classes

### Entidades Principais

```java
// UnidadeNegocio.java
@Entity
@Table(name = "unidades_negocio")
public class UnidadeNegocio extends BaseEntity {
    private String codigo;
    private String nome;
    private String descricao;
    private Boolean ativa;
    
    @OneToMany(mappedBy = "unidadeNegocio")
    private List<SubUnidadeNegocio> subUnidades;
}

// SubUnidadeNegocio.java
@Entity
@Table(name = "sub_unidades_negocio")
public class SubUnidadeNegocio extends BaseEntity {
    @ManyToOne
    private UnidadeNegocio unidadeNegocio;
    
    private String codigo;
    private String nome;
    private String descricao;
}

// GrupoCentroCusto.java
@Entity
@Table(name = "grupos_centro_custo")
public class GrupoCentroCusto extends BaseEntity {
    private String codigo;
    private String nome;
    private String descricao;
    
    @OneToMany(mappedBy = "grupo")
    private List<SubGrupoCentroCusto> subGrupos;
}

// SubGrupoCentroCusto.java
@Entity
@Table(name = "sub_grupos_centro_custo")
public class SubGrupoCentroCusto extends BaseEntity {
    @ManyToOne
    private GrupoCentroCusto grupo;
    
    private String codigo;
    private String nome;
    private String descricao;
}

// CentroCusto.java
@Entity
@Table(name = "centros_custo")
public class CentroCusto extends BaseEntity {
    private String codigo;
    private String nome;
    private String descricao;
    
    @ManyToOne
    private UnidadeNegocio unidadeNegocio;
    
    @ManyToOne
    private SubUnidadeNegocio subUnidadeNegocio;
    
    @ManyToOne
    private GrupoCentroCusto grupo;
    
    @ManyToOne
    private SubGrupoCentroCusto subGrupo;
    
    @Enumerated(EnumType.STRING)
    private ClassificacaoCentroCusto classificacao;
    
    @ManyToOne
    private UnidadeProducao unidadeProducao;
    
    private Boolean ativo;
    
    @OneToMany(mappedBy = "centroCusto")
    private List<ConfiguracaoCustoCentroCusto> configuracoesCusto;
    
    @OneToMany(mappedBy = "centroCusto")
    private List<LancamentoCusto> lancamentos;
}

// Moeda.java
@Entity
@Table(name = "moedas")
public class Moeda extends BaseEntityWithoutTenant {
    private String codigoISO; // BRL, USD, EUR
    private String nome;
    private String simbolo;
    private BigDecimal taxaConversaoPadrao; // para BRL
}

// CotacaoMoeda.java
@Entity
@Table(name = "cotacoes_moeda")
public class CotacaoMoeda extends BaseEntity {
    @ManyToOne
    private Moeda moeda;
    
    private Integer mes;
    private Integer ano;
    private BigDecimal taxaConversao;
    private LocalDate dataCotacao;
}

// ItemReceitaFinanceira.java
@Entity
@Table(name = "itens_receita_financeira")
public class ItemReceitaFinanceira extends BaseEntity {
    private String codigo;
    private String descricao;
    private String tipoReceita;
    
    @ManyToOne
    private CentroCusto centroCusto;
}

// GrupoItemCusto.java
@Entity
@Table(name = "grupos_item_custo")
public class GrupoItemCusto extends BaseEntity {
    private String codigo;
    private String nome;
    private String descricao;
    
    @OneToMany(mappedBy = "grupo")
    private List<ItemCusto> itens;
}

// ItemCusto.java
@Entity
@Table(name = "itens_custo")
public class ItemCusto extends BaseEntity {
    private String codigo;
    private String descricao;
    
    @ManyToOne
    private GrupoItemCusto grupo;
    
    @Enumerated(EnumType.STRING)
    private TipoItemCusto tipo;
    
    private String unidadeMedida;
    private BigDecimal valorPadrao;
    
    @OneToMany(mappedBy = "itemCusto")
    private List<ItemCustoGerado> itensGerados;
}

// ItemCustoGerado.java
@Entity
@Table(name = "itens_custo_gerados")
public class ItemCustoGerado extends BaseEntity {
    @ManyToOne
    private ItemCusto itemOrigem;
    
    @ManyToOne
    private ItemCusto itemGerado;
    
    private String formula; // como calcular
    private String condicao; // quando gerar
}

// IndiceEconomico.java
@Entity
@Table(name = "indices_economicos")
public class IndiceEconomico extends BaseEntityWithoutTenant {
    private String codigo;
    private String nome;
    private String descricao;
    
    @OneToMany(mappedBy = "indice")
    private List<ValorIndiceEconomico> valores;
}

// ValorIndiceEconomico.java
@Entity
@Table(name = "valores_indice_economico")
public class ValorIndiceEconomico extends BaseEntity {
    @ManyToOne
    private IndiceEconomico indice;
    
    private Integer mes;
    private Integer ano;
    private BigDecimal valor;
}

// PesoUnidadeProducao.java
@Entity
@Table(name = "pesos_unidade_producao")
public class PesoUnidadeProducao extends BaseEntity {
    @ManyToOne
    private UnidadeProducao unidadeProducao;
    
    private BigDecimal peso;
    private LocalDate dataInicioVigencia;
    private LocalDate dataFimVigencia;
}

// CriterioRateio.java
@Entity
@Table(name = "criterios_rateio")
public class CriterioRateio extends BaseEntity {
    private String codigo;
    private String nome;
    private String descricao;
    private String tipo; // AREA, FUNCIONARIOS, PRODUCAO, CUSTOMIZADO
    
    @OneToMany(mappedBy = "criterio")
    private List<BaseRateio> bases;
}

// BaseRateio.java
@Entity
@Table(name = "bases_rateio")
public class BaseRateio extends BaseEntity {
    @ManyToOne
    private CriterioRateio criterio;
    
    private String codigo;
    private String nome;
    private String descricao;
    private String configuracao; // JSON com parâmetros
}

// UnidadeProducao.java
@Entity
@Table(name = "unidades_producao")
public class UnidadeProducao extends BaseEntity {
    private String codigo;
    private String nome;
    private String descricao;
    
    @OneToMany(mappedBy = "unidadeProducao")
    private List<ItemProducao> itens;
}

// ItemProducao.java
@Entity
@Table(name = "itens_producao")
public class ItemProducao extends BaseEntity {
    @ManyToOne
    private UnidadeProducao unidadeProducao;
    
    private String codigo;
    private String nome;
    private String descricao;
    private String unidadeMedida;
}

// ConfiguracaoCustoCentroCusto.java
@Entity
@Table(name = "configuracoes_custo_centro_custo")
public class ConfiguracaoCustoCentroCusto extends BaseEntity {
    @ManyToOne
    private CentroCusto centroCusto;
    
    @ManyToOne
    private ItemCusto itemCusto;
    
    private BigDecimal percentualFixo;
    private BigDecimal percentualVariavel;
    
    @ManyToOne
    private BaseRateio baseRateio; // para itens indiretos
    
    private Boolean opcional;
    private Boolean ativo;
}

// LancamentoCusto.java
@Entity
@Table(name = "lancamentos_custo")
public class LancamentoCusto extends BaseEntity {
    @ManyToOne
    private CentroCusto centroCusto;
    
    @ManyToOne
    private ItemCusto itemCusto;
    
    private Integer mes;
    private Integer ano;
    private BigDecimal valor;
    
    @ManyToOne
    private Moeda moeda;
    
    private String origem; // MANUAL, INTEGRACAO, CALCULADO
    private String observacoes;
}

// LancamentoReceita.java
@Entity
@Table(name = "lancamentos_receita")
public class LancamentoReceita extends BaseEntity {
    @ManyToOne
    private CentroCusto centroCusto;
    
    @ManyToOne
    private ItemReceitaFinanceira itemReceita;
    
    private Integer mes;
    private Integer ano;
    private BigDecimal valor;
    
    @ManyToOne
    private Moeda moeda;
    
    private String origem;
    private String observacoes;
}

// ApuracaoCustoCentroCusto.java
@Entity
@Table(name = "apuracoes_custo_centro_custo")
public class ApuracaoCustoCentroCusto extends BaseEntity {
    @ManyToOne
    private CentroCusto centroCusto;
    
    private Integer mes;
    private Integer ano;
    
    private BigDecimal custoDiretoTotal;
    private BigDecimal custoIndiretoTotal;
    private BigDecimal custoFixoTotal;
    private BigDecimal custoVariavelTotal;
    private BigDecimal custoTotal;
    private BigDecimal custoUnitario;
    
    private BigDecimal receitaTotal;
    private BigDecimal resultado; // receita - custo
    
    private Boolean calculado;
    private LocalDateTime dataCalculo;
}
```

### Enums Necessários

```java
public enum ClassificacaoCentroCusto {
    ADMINISTRATIVO,
    AUXILIAR,
    PRODUTIVO
}

public enum TipoItemCusto {
    DIRETO,
    INDIRETO
}
```

---

## 🔄 Fluxo de Processo

### Fluxo de Apuração de Custos

```
1. Configurar estrutura de centros de custo
   ↓
2. Configurar itens de custo e receita
   ↓
3. Configurar critérios e bases de rateio
   ↓
4. Lançar custos e receitas do período
   ↓
5. Executar apuração:
   - Somar custos diretos
   - Ratear custos indiretos
   - Calcular custos totais
   - Calcular custos unitários
   ↓
6. Gerar relatórios e análises
```

---

## 🔐 Regras de Negócio

### RB-001: Hierarquia de Centros de Custo
- Centro de custo deve pertencer a uma hierarquia completa
- Não pode haver centro de custo órfão

### RB-002: Rateio de Custos Indiretos
- Custos indiretos só podem ser rateados para centros produtivos
- Rateio segue critério configurado
- Soma dos rateios deve ser igual ao custo total

### RB-003: Custos Fixos e Variáveis
- Percentual fixo + variável deve ser igual a 100%
- Cálculo considera produção do período

### RB-004: Apuração
- Apuração é feita por mês/ano
- Não pode apurar período futuro
- Apuração pode ser recalculada

### RB-005: Moedas
- Cotação deve ser informada mensalmente
- Valores são convertidos para moeda base (BRL)

---

## 📱 APIs REST Necessárias

### Endpoints de Cadastros
- `POST /api/v1/custo-departamental/unidades-negocio` - Criar
- `POST /api/v1/custo-departamental/centros-custo` - Criar centro de custo
- `POST /api/v1/custo-departamental/itens-custo` - Criar item de custo

### Endpoints de Lançamentos
- `POST /api/v1/custo-departamental/lancamentos-custo` - Lançar custo
- `POST /api/v1/custo-departamental/lancamentos-receita` - Lançar receita
- `POST /api/v1/custo-departamental/cotacoes-moeda` - Informar cotação

### Endpoints de Apuração
- `POST /api/v1/custo-departamental/apuracoes/calcular` - Calcular apuração
- `GET /api/v1/custo-departamental/apuracoes` - Listar apurações

---

## 🚀 Fases de Implementação

### Fase 1: Cadastros Básicos (3 semanas)
- Estrutura hierárquica completa
- Itens de custo e receita
- Moedas e cotações

### Fase 2: Configurações (2 semanas)
- Critérios e bases de rateio
- Configurações de custos por centro

### Fase 3: Lançamentos (2 semanas)
- Lançamento de custos
- Lançamento de receitas

### Fase 4: Apuração (2 semanas)
- Cálculo de custos
- Rateio de indiretos
- Cálculo de unitários

### Fase 5: Relatórios (1 semana)
- Relatórios gerenciais

**Total estimado: 10 semanas**

