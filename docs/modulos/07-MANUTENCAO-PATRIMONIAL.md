# Módulo: Manutenção Patrimonial

## 📋 Visão Geral (Para Product Owner)

O módulo de Manutenção Patrimonial permite gerenciar todo o ciclo de vida dos patrimônios e equipamentos da unidade de saúde, desde a aquisição até a baixa, incluindo controle de manutenções, depreciação e localização. Este módulo é essencial para controle patrimonial e gestão de ativos.

### Objetivo de Negócio
- Controlar patrimônios e equipamentos
- Gerenciar manutenções preventivas e corretivas
- Calcular depreciação
- Rastrear localização de bens
- Gerar relatórios patrimoniais

### Benefícios
- Controle rigoroso de patrimônio
- Redução de perdas e extravios
- Otimização de manutenções
- Conformidade contábil
- Base para tomada de decisão

---

## 🎯 Funcionalidades Necessárias

### 1. Cadastros Básicos

#### 1.1 Cadastro de Patrimônios e Equipamentos
- **Descrição**: Cadastro completo de todos os bens patrimoniais
- **Campos necessários**:
  - Número patrimonial (único)
  - Descrição
  - Grupo de patrimônio
  - Marca
  - Modelo
  - Número de série
  - Fornecedor
  - Data de aquisição
  - Valor de aquisição
  - Vida útil (anos)
  - Taxa de depreciação (%)
  - Centro de custo
  - Setor atual
  - Localização física
  - Status (ativo, em manutenção, baixado)
  - Conta contábil
  - Fotos/documentos

#### 1.2 Cadastro de Grupos de Patrimônios e Equipamentos
- **Descrição**: Classificação de patrimônios
- **Exemplos**: Equipamentos médicos, Mobiliário, Veículos, Informática
- **Campos necessários**:
  - Código
  - Nome
  - Descrição
  - Taxa de depreciação padrão

#### 1.3 Cadastro de Funcionários da Manutenção
- **Descrição**: Funcionários responsáveis por manutenções
- **Campos necessários**:
  - Nome completo
  - CPF
  - Cargo
  - Especialidade
  - Centro de custo vinculado
  - Status (ativo/inativo)

#### 1.4 Cadastro de Relacionamento Funcionário x Centro de Custo
- **Descrição**: Vincular funcionários a centros de custo
- **Funcionalidade**: Controle de custos por funcionário

#### 1.5 Cadastro de Tipos de Manutenções
- **Descrição**: Tipos de manutenção realizadas
- **Exemplos**: Preventiva, Corretiva, Preditiva, Calibração
- **Campos necessários**:
  - Código
  - Descrição
  - Tipo (preventiva/corretiva)
  - Periodicidade padrão (dias)

#### 1.6 Cadastro de Marcas
- **Descrição**: Marcas dos equipamentos
- **Campos necessários**:
  - Código
  - Nome
  - Descrição

#### 1.7 Cadastro de Proprietários do Equipamento
- **Descrição**: Entidades proprietárias dos equipamentos
- **Exemplos**: Município, Estado, União, Terceiros
- **Campos necessários**:
  - Código
  - Nome
  - Descrição

#### 1.8 Cadastro de Motivos de Baixa
- **Descrição**: Motivos para baixa de patrimônio
- **Exemplos**: Inutilização, Venda, Doação, Roubo/Furto
- **Campos necessários**:
  - Código
  - Descrição

#### 1.9 Cadastro de Setores
- **Descrição**: Setores onde patrimônios estão localizados
- **Campos necessários**:
  - Código
  - Nome
  - Descrição
  - Centro de custo vinculado

### 2. Processo de Manutenção

#### 2.1 Solicitação de Manutenção
- **Descrição**: Solicitar manutenção necessária
- **Informações necessárias**:
  - Patrimônio
  - Tipo de manutenção
  - Descrição do problema
  - Urgência
  - Solicitante
  - Data solicitada

#### 2.2 Gerenciamento de Ordens de Serviço
- **Descrição**: Criar e gerenciar ordens de serviço
- **Informações**:
  - Número da OS
  - Patrimônio
  - Tipo de manutenção
  - Funcionário responsável
  - Fornecedor (se externa)
  - Data início/fim
  - Descrição do serviço
  - Peças utilizadas
  - Mão de obra
  - Valor total
  - Status (aberta, em andamento, concluída, cancelada)

#### 2.3 Contabilização de Gastos
- **Descrição**: Contabilizar gastos nas ordens de serviço
- **Informações**:
  - Peças/materiais
  - Mão de obra interna
  - Mão de obra externa
  - Serviços terceirizados
  - Centro de custo

#### 2.4 Reabertura de Ordens Fechadas
- **Descrição**: Permitir reabrir OS já fechadas
- **Regras**:
  - Requer autorização
  - Justificativa obrigatória
  - Log de auditoria

### 3. Controle Patrimonial

#### 3.1 Transferência de Patrimônios
- **Descrição**: Transferir patrimônio entre setores
- **Informações**:
  - Patrimônio
  - Setor origem
  - Setor destino
  - Data transferência
  - Responsável
  - Motivo

#### 3.2 Baixa de Patrimônios
- **Descrição**: Dar baixa em patrimônios
- **Informações**:
  - Patrimônio
  - Motivo de baixa
  - Data de baixa
  - Valor residual (se houver)
  - Responsável
  - Documentação

#### 3.3 Depreciação
- **Descrição**: Calcular depreciação dos patrimônios
- **Métodos**:
  - Linear
  - Acelerada
- **Cálculo automático mensal**

### 4. Relatórios

#### 4.1 Relatório de Valores por Centro de Custos
- **Descrição**: Valor total de patrimônios por centro de custo

#### 4.2 Relatório de Valores por Patrimônio
- **Descrição**: Lista de patrimônios com valores

#### 4.3 Relatório de Valores por Funcionários
- **Descrição**: Custos de manutenção por funcionário

#### 4.4 Relatório de Valores por Fornecedores
- **Descrição**: Gastos com manutenção por fornecedor

#### 4.5 Relatório de Vida Útil dos Patrimônios
- **Descrição**: Patrimônios próximos ao fim da vida útil

#### 4.6 Relatório de Depreciação por Conta Contábil
- **Descrição**: Depreciação agrupada por conta contábil

#### 4.7 Relatório de Localização dos Patrimônios
- **Descrição**: Onde cada patrimônio está localizado

#### 4.8 Relatório de Bens 100% Depreciados
- **Descrição**: Patrimônios totalmente depreciados

#### 4.9 Relatório de Compras de Patrimônios
- **Descrição**: Histórico de aquisições

#### 4.10 Relatório de Baixas de Patrimônios
- **Descrição**: Histórico de baixas

#### 4.11 Relatório de Transferências de Patrimônios
- **Descrição**: Histórico de transferências

#### 4.12 Relatório de Depreciação
- **Descrição**: Depreciação por período

#### 4.13 Relatório de Manutenções Realizadas
- **Descrição**: Manutenções por data ou funcionário

---

## 📐 Arquitetura e Classes

### Entidades Principais

```java
// GrupoPatrimonio.java
@Entity
@Table(name = "grupos_patrimonio")
public class GrupoPatrimonio extends BaseEntityWithoutTenant {
    private String codigo;
    private String nome;
    private String descricao;
    private BigDecimal taxaDepreciacaoPadrao;
}

// Patrimonio.java
@Entity
@Table(name = "patrimonios")
public class Patrimonio extends BaseEntity {
    private String numeroPatrimonial; // único
    
    private String descricao;
    
    @ManyToOne
    private GrupoPatrimonio grupo;
    
    @ManyToOne
    private Marca marca;
    
    private String modelo;
    private String numeroSerie;
    
    @ManyToOne
    private Fornecedor fornecedor;
    
    private LocalDate dataAquisicao;
    private BigDecimal valorAquisicao;
    private Integer vidaUtilAnos;
    private BigDecimal taxaDepreciacao; // %
    
    @ManyToOne
    private CentroCusto centroCusto;
    
    @ManyToOne
    private Setor setorAtual;
    
    private String localizacaoFisica;
    
    @Enumerated(EnumType.STRING)
    private StatusPatrimonio status;
    
    private String contaContabil;
    
    @ManyToOne
    private ProprietarioEquipamento proprietario;
    
    @OneToMany(mappedBy = "patrimonio")
    private List<ManutencaoPatrimonio> manutencoes;
    
    @OneToMany(mappedBy = "patrimonio")
    private List<TransferenciaPatrimonio> transferencias;
    
    @OneToMany(mappedBy = "patrimonio")
    private List<DepreciacaoPatrimonio> depreciacoes;
}

// Marca.java
@Entity
@Table(name = "marcas")
public class Marca extends BaseEntityWithoutTenant {
    private String codigo;
    private String nome;
    private String descricao;
}

// ProprietarioEquipamento.java
@Entity
@Table(name = "proprietarios_equipamento")
public class ProprietarioEquipamento extends BaseEntityWithoutTenant {
    private String codigo;
    private String nome;
    private String descricao;
}

// MotivoBaixa.java
@Entity
@Table(name = "motivos_baixa")
public class MotivoBaixa extends BaseEntityWithoutTenant {
    private String codigo;
    private String descricao;
}

// Setor.java
@Entity
@Table(name = "setores")
public class Setor extends BaseEntity {
    private String codigo;
    private String nome;
    private String descricao;
    
    @ManyToOne
    private CentroCusto centroCusto;
}

// FuncionarioManutencao.java
@Entity
@Table(name = "funcionarios_manutencao")
public class FuncionarioManutencao extends BaseEntity {
    private String nomeCompleto;
    private String cpf;
    private String cargo;
    private String especialidade;
    
    @ManyToOne
    private CentroCusto centroCusto;
    
    private Boolean ativo;
    
    @OneToMany(mappedBy = "funcionario")
    private List<OrdemServico> ordensServico;
}

// TipoManutencao.java
@Entity
@Table(name = "tipos_manutencao")
public class TipoManutencao extends BaseEntityWithoutTenant {
    private String codigo;
    private String descricao;
    
    @Enumerated(EnumType.STRING)
    private TipoManutencaoEnum tipo; // PREVENTIVA, CORRETIVA
    
    private Integer periodicidadePadraoDias;
}

// SolicitacaoManutencao.java
@Entity
@Table(name = "solicitacoes_manutencao")
public class SolicitacaoManutencao extends BaseEntity {
    @ManyToOne
    private Patrimonio patrimonio;
    
    @ManyToOne
    private TipoManutencao tipoManutencao;
    
    private String descricaoProblema;
    
    @Enumerated(EnumType.STRING)
    private UrgenciaManutencao urgencia;
    
    private UUID solicitanteId;
    private LocalDate dataSolicitada;
    
    @Enumerated(EnumType.STRING)
    private StatusSolicitacao status;
    
    @OneToOne(mappedBy = "solicitacao")
    private OrdemServico ordemServico;
}

// OrdemServico.java
@Entity
@Table(name = "ordens_servico")
public class OrdemServico extends BaseEntity {
    private String numeroOS; // único, gerado automaticamente
    
    @OneToOne
    private SolicitacaoManutencao solicitacao;
    
    @ManyToOne
    private Patrimonio patrimonio;
    
    @ManyToOne
    private TipoManutencao tipoManutencao;
    
    @ManyToOne
    private FuncionarioManutencao funcionario; // se interna
    
    @ManyToOne
    private Fornecedor fornecedor; // se externa
    
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private LocalDateTime dataConclusao;
    
    private String descricaoServico;
    private String observacoes;
    
    @OneToMany(mappedBy = "ordemServico")
    private List<ItemOrdemServico> itens;
    
    private BigDecimal valorTotal;
    
    @ManyToOne
    private CentroCusto centroCusto;
    
    @Enumerated(EnumType.STRING)
    private StatusOrdemServico status;
    
    private Boolean reaberta;
    private String justificativaReabertura;
    private UUID reabertaPor;
    private LocalDateTime dataReabertura;
}

// ItemOrdemServico.java
@Entity
@Table(name = "itens_ordem_servico")
public class ItemOrdemServico extends BaseEntity {
    @ManyToOne
    private OrdemServico ordemServico;
    
    private String tipo; // PECA, MAO_OBRA_INTERNA, MAO_OBRA_EXTERNA, SERVICO
    
    private String descricao;
    private Integer quantidade;
    private BigDecimal valorUnitario;
    private BigDecimal valorTotal;
}

// TransferenciaPatrimonio.java
@Entity
@Table(name = "transferencias_patrimonio")
public class TransferenciaPatrimonio extends BaseEntity {
    @ManyToOne
    private Patrimonio patrimonio;
    
    @ManyToOne
    private Setor setorOrigem;
    
    @ManyToOne
    private Setor setorDestino;
    
    private LocalDate dataTransferencia;
    private UUID responsavelId;
    private String motivo;
    private String observacoes;
}

// BaixaPatrimonio.java
@Entity
@Table(name = "baixas_patrimonio")
public class BaixaPatrimonio extends BaseEntity {
    @ManyToOne
    private Patrimonio patrimonio;
    
    @ManyToOne
    private MotivoBaixa motivoBaixa;
    
    private LocalDate dataBaixa;
    private BigDecimal valorResidual;
    private UUID responsavelId;
    private String documentacao; // caminho do arquivo
    private String observacoes;
}

// DepreciacaoPatrimonio.java
@Entity
@Table(name = "depreciacoes_patrimonio")
public class DepreciacaoPatrimonio extends BaseEntity {
    @ManyToOne
    private Patrimonio patrimonio;
    
    private Integer mes;
    private Integer ano;
    private BigDecimal valorDepreciacao;
    private BigDecimal valorAcumulado;
    private BigDecimal valorContabil; // valor aquisição - depreciação acumulada
}
```

### Enums Necessários

```java
public enum StatusPatrimonio {
    ATIVO,
    EM_MANUTENCAO,
    BAIXADO
}

public enum TipoManutencaoEnum {
    PREVENTIVA,
    CORRETIVA,
    PREDITIVA,
    CALIBRACAO
}

public enum UrgenciaManutencao {
    BAIXA,
    MEDIA,
    ALTA,
    CRITICA
}

public enum StatusSolicitacao {
    PENDENTE,
    EM_ANALISE,
    APROVADA,
    REJEITADA,
    CANCELADA
}

public enum StatusOrdemServico {
    ABERTA,
    EM_ANDAMENTO,
    CONCLUIDA,
    CANCELADA
}
```

---

## 🔄 Fluxo de Processo

### Fluxo de Manutenção

```
1. Solicitação de manutenção
   ↓
2. Análise e aprovação
   ↓
3. Criação de ordem de serviço
   ↓
4. Execução da manutenção
   ↓
5. Registro de peças/serviços
   ↓
6. Fechamento da OS
   ↓
7. Contabilização de gastos
```

### Fluxo de Depreciação

```
1. Processo automático mensal
   ↓
2. Para cada patrimônio ativo:
   - Calcular depreciação do mês
   - Atualizar depreciação acumulada
   - Atualizar valor contábil
   ↓
3. Gerar lançamentos contábeis
```

---

## 🔐 Regras de Negócio

### RB-001: Número Patrimonial
- Deve ser único no sistema
- Formato configurável
- Não pode ser alterado após criação

### RB-002: Depreciação
- Calculada mensalmente de forma automática
- Método linear: (Valor Aquisição / Vida Útil) / 12
- Para quando valor contábil chega a zero ou patrimônio é baixado

### RB-003: Manutenção Preventiva
- Pode ser agendada automaticamente conforme periodicidade
- Alertas quando próxima da data

### RB-004: Ordem de Serviço
- Não pode ser fechada sem todos os campos obrigatórios
- Reabertura requer autorização e justificativa
- Log de todas as alterações

### RB-005: Transferência
- Patrimônio deve estar ativo
- Setor destino deve existir
- Histórico completo deve ser mantido

### RB-006: Baixa
- Patrimônio deve estar ativo
- Motivo é obrigatório
- Documentação pode ser obrigatória dependendo do motivo
- Valor residual deve ser informado se houver

---

## 📱 APIs REST Necessárias

### Endpoints de Patrimônios
- `POST /api/v1/manutencao-patrimonial/patrimonios` - Criar
- `GET /api/v1/manutencao-patrimonial/patrimonios` - Listar
- `GET /api/v1/manutencao-patrimonial/patrimonios/{id}` - Obter
- `PUT /api/v1/manutencao-patrimonial/patrimonios/{id}` - Atualizar

### Endpoints de Manutenção
- `POST /api/v1/manutencao-patrimonial/solicitacoes` - Solicitar manutenção
- `POST /api/v1/manutencao-patrimonial/ordens-servico` - Criar OS
- `PUT /api/v1/manutencao-patrimonial/ordens-servico/{id}` - Atualizar OS
- `POST /api/v1/manutencao-patrimonial/ordens-servico/{id}/fechar` - Fechar OS
- `POST /api/v1/manutencao-patrimonial/ordens-servico/{id}/reabrir` - Reabrir OS

### Endpoints de Transferências
- `POST /api/v1/manutencao-patrimonial/transferencias` - Transferir

### Endpoints de Baixas
- `POST /api/v1/manutencao-patrimonial/baixas` - Dar baixa

### Endpoints de Depreciação
- `POST /api/v1/manutencao-patrimonial/depreciacoes/calcular` - Calcular depreciação

---

## 🚀 Fases de Implementação

### Fase 1: Cadastros Básicos (2 semanas)
- Todos os cadastros listados

### Fase 2: Processo de Manutenção (2 semanas)
- Solicitação
- Ordem de serviço
- Contabilização

### Fase 3: Controle Patrimonial (2 semanas)
- Transferências
- Baixas
- Localização

### Fase 4: Depreciação (1 semana)
- Cálculo automático
- Lançamentos

### Fase 5: Relatórios (2 semanas)
- Todos os relatórios

**Total estimado: 9 semanas**

