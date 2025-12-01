# Módulo: Internação

## 📋 Visão Geral (Para Product Owner)

O módulo de Internação permite gerenciar todo o ciclo de vida de uma internação hospitalar, desde a admissão até a alta do paciente. Este módulo é essencial para hospitais e unidades que realizam internações, garantindo controle de leitos, acompanhamento clínico e integração com sistemas de faturamento.

### Objetivo de Negócio
- Gerenciar internações de forma eficiente
- Controlar ocupação de leitos
- Acompanhar evolução clínica do paciente internado
- Integrar com prescrições e procedimentos
- Gerar dados para faturamento e relatórios

### Benefícios
- Controle preciso de leitos
- Rastreabilidade completa da internação
- Melhoria na qualidade do cuidado
- Dados para análise e tomada de decisão
- Integração com outros módulos do sistema

---

## 🎯 Funcionalidades Necessárias

### 1. Cadastros Básicos

#### 1.1 Cadastro de Leitos
- **Descrição**: Cadastrar todos os leitos disponíveis na unidade
- **Campos necessários**:
  - Número do leito
  - Tipo de acomodação (enfermaria, apartamento, UTI, etc.)
  - Setor/ala
  - Especialidade (se específico)
  - Equipamentos disponíveis
  - Status (disponível, ocupado, manutenção, bloqueado)
  - Sexo permitido (masculino, feminino, ambos)

#### 1.2 Cadastro de Tipos de Acomodação
- **Descrição**: Classificar tipos de acomodação
- **Exemplos**: Enfermaria coletiva, Apartamento, UTI, UTI Neonatal
- **Campos necessários**:
  - Código
  - Descrição
  - Valor diária (para faturamento)
  - Capacidade máxima

#### 1.3 Cadastro de Setores/Alas
- **Descrição**: Organizar leitos por setores
- **Campos necessários**:
  - Nome do setor
  - Descrição
  - Tipo (clínica médica, cirúrgica, pediatria, etc.)
  - Responsável (enfermeiro chefe)

### 2. Processo de Internação

#### 2.1 Admissão de Paciente
- **Descrição**: Registrar entrada do paciente para internação
- **Informações necessárias**:
  - Paciente (vinculado ao cadastro)
  - Médico responsável
  - Data/hora de admissão
  - Tipo de internação (eletiva, urgência, emergência)
  - Origem (pronto-socorro, ambulatório, transferência)
  - CID principal e secundários
  - Leito designado
  - Convênio (se aplicável)
  - Acompanhante (se permitido)

#### 2.2 Reserva de Leito
- **Descrição**: Reservar leito antes da admissão
- **Funcionalidades**:
  - Buscar leitos disponíveis por critérios
  - Reservar por período determinado
  - Cancelar reserva
  - Listar reservas ativas

#### 2.3 Transferência de Leito
- **Descrição**: Transferir paciente entre leitos
- **Informações**:
  - Leito origem
  - Leito destino
  - Motivo da transferência
  - Data/hora
  - Responsável pela transferência

#### 2.4 Alta do Paciente
- **Descrição**: Registrar saída do paciente
- **Tipos de alta**:
  - Alta médica
  - Alta a pedido
  - Óbito
  - Transferência para outra unidade
  - Evasão
- **Informações**:
  - Data/hora da alta
  - Tipo de alta
  - CID da alta
  - Condição ao sair (cura, melhora, óbito, etc.)
  - Resumo de alta
  - Orientações ao paciente

#### 2.5 Cancelamento de Internação
- **Descrição**: Cancelar internação antes da admissão efetiva
- **Motivos**: Paciente não compareceu, cancelamento médico, etc.

### 3. Acompanhamento Clínico

#### 3.1 Evolução Médica
- **Descrição**: Registrar evolução clínica diária
- **Informações**:
  - Data/hora
  - Médico responsável
  - Evolução (texto livre)
  - Sinais vitais
  - Exames realizados
  - Conduta

#### 3.2 Evolução de Enfermagem
- **Descrição**: Registros de enfermagem
- **Informações**:
  - Data/hora
  - Enfermeiro responsável
  - Sinais vitais
  - Medicações administradas
  - Cuidados realizados
  - Observações

#### 3.3 Prescrições de Internados
- **Descrição**: Prescrições médicas específicas para internados
- **Integração**: Com módulo de prescrição de internados
- **Funcionalidades**:
  - Prescrição de medicamentos
  - Prescrição de exames
  - Prescrição de dietas
  - Prescrição de cuidados

#### 3.4 Procedimentos Realizados
- **Descrição**: Registrar procedimentos durante a internação
- **Integração**: Com catálogo de procedimentos

### 4. Controle de Leitos

#### 4.1 Visualização de Situação de Leitos
- **Descrição**: Dashboard com situação atual dos leitos
- **Informações**:
  - Leitos ocupados/disponíveis
  - Por setor
  - Por tipo de acomodação
  - Tempo médio de ocupação
  - Taxa de ocupação

#### 4.2 Histórico de Ocupação
- **Descrição**: Histórico de ocupação de cada leito
- **Uso**: Análise de utilização

### 5. Manutenção de Contas

#### 5.1 Fechamento de Conta
- **Descrição**: Fechar conta da internação para faturamento
- **Informações**:
  - Data de fechamento
  - Itens faturados:
    - Diárias
    - Procedimentos
    - Medicações
    - Materiais
    - Exames
  - Valor total
  - Status (aberta, fechada, faturada)

#### 5.2 Manutenção de Contas Fechadas
- **Descrição**: Permitir ajustes em contas já fechadas
- **Regras**:
  - Requer autorização especial
  - Gera log de auditoria
  - Justificativa obrigatória

### 6. Relatórios

#### 6.1 Relatório de Internações por Período
- **Descrição**: Lista de internações
- **Filtros**: Data, médico, setor, CID, tipo de alta

#### 6.2 Relatório de Ocupação de Leitos
- **Descrição**: Taxa de ocupação por período
- **Informações**: Por setor, por tipo de acomodação

#### 6.3 Relatório de Tempo Médio de Permanência
- **Descrição**: Tempo médio por CID, por setor

#### 6.4 Relatório de Mortalidade
- **Descrição**: Óbitos por período, CID, setor

#### 6.5 Relatório de Transferências
- **Descrição**: Transferências entre leitos/unidades

---

## 📐 Arquitetura e Classes

### Entidades Principais

```java
// Leito.java
@Entity
@Table(name = "leitos")
public class Leito extends BaseEntity {
    @ManyToOne
    private Estabelecimentos estabelecimento;
    
    private String numeroLeito;
    
    @ManyToOne
    private TipoAcomodacao tipoAcomodacao;
    
    @ManyToOne
    private SetorInternacao setor;
    
    @ManyToOne
    private EspecialidadesMedicas especialidade; // opcional
    
    @Enumerated(EnumType.STRING)
    private StatusLeito status;
    
    @Enumerated(EnumType.STRING)
    private SexoPermitidoLeito sexoPermitido;
    
    private String equipamentos; // JSON ou texto
    private String observacoes;
    
    @OneToMany(mappedBy = "leito")
    private List<Internacao> internacoes;
    
    @OneToMany(mappedBy = "leito")
    private List<ReservaLaito> reservas;
}

// TipoAcomodacao.java
@Entity
@Table(name = "tipos_acomodacao")
public class TipoAcomodacao extends BaseEntityWithoutTenant {
    private String codigo;
    private String descricao;
    private BigDecimal valorDiaria;
    private Integer capacidadeMaxima;
    private String caracteristicas; // JSON
}

// SetorInternacao.java
@Entity
@Table(name = "setores_internacao")
public class SetorInternacao extends BaseEntity {
    @ManyToOne
    private Estabelecimentos estabelecimento;
    
    private String nome;
    private String descricao;
    private String tipo; // CLINICA_MEDICA, CIRURGICA, PEDIATRIA, UTI, etc.
    
    @ManyToOne
    private ProfissionaisSaude enfermeiroChefe;
    
    @OneToMany(mappedBy = "setor")
    private List<Leito> leitos;
}

// Internacao.java
@Entity
@Table(name = "internacoes")
public class Internacao extends BaseEntity {
    @ManyToOne
    private Paciente paciente;
    
    @ManyToOne
    private Medicos medicoResponsavel;
    
    @ManyToOne
    private Leito leito;
    
    @ManyToOne
    private SetorInternacao setor;
    
    @ManyToOne
    private Convenio convenio;
    
    @ManyToOne
    private CidDoencas cidPrincipal;
    
    @ManyToMany
    private List<CidDoencas> cidsSecundarios;
    
    private LocalDateTime dataHoraAdmissao;
    private LocalDateTime dataHoraAlta;
    
    @Enumerated(EnumType.STRING)
    private TipoInternacao tipoInternacao;
    
    @Enumerated(EnumType.STRING)
    private OrigemInternacao origem;
    
    @Enumerated(EnumType.STRING)
    private StatusInternacao status;
    
    @Enumerated(EnumType.STRING)
    private TipoAlta tipoAlta;
    
    @ManyToOne
    private CidDoencas cidAlta;
    
    private String condicaoSair; // CURA, MELHORA, OBITO, etc.
    private String resumoAlta;
    private String orientacoesAlta;
    
    private String motivoAltaPedido; // se alta a pedido
    
    @OneToMany(mappedBy = "internacao")
    private List<EvolucaoMedica> evolucoesMedicas;
    
    @OneToMany(mappedBy = "internacao")
    private List<EvolucaoEnfermagem> evolucoesEnfermagem;
    
    @OneToMany(mappedBy = "internacao")
    private List<PrescricaoInternado> prescricoes;
    
    @OneToMany(mappedBy = "internacao")
    private List<ProcedimentoInternacao> procedimentos;
    
    @OneToMany(mappedBy = "internacao")
    private List<TransferenciaLeito> transferencias;
    
    @OneToOne(mappedBy = "internacao")
    private ContaInternacao conta;
}

// ReservaLaito.java
@Entity
@Table(name = "reservas_leito")
public class ReservaLaito extends BaseEntity {
    @ManyToOne
    private Leito leito;
    
    @ManyToOne
    private Paciente paciente;
    
    @ManyToOne
    private Medicos medicoSolicitante;
    
    private LocalDateTime dataHoraReserva;
    private LocalDateTime dataHoraPrevistaAdmissao;
    private LocalDateTime dataHoraExpiracao;
    
    @Enumerated(EnumType.STRING)
    private StatusReserva status;
    
    private String motivoCancelamento;
}

// EvolucaoMedica.java
@Entity
@Table(name = "evolucoes_medicas")
public class EvolucaoMedica extends BaseEntity {
    @ManyToOne
    private Internacao internacao;
    
    @ManyToOne
    private Medicos medico;
    
    private LocalDateTime dataHora;
    private String evolucao; // texto livre
    
    @Embedded
    private SinaisVitais sinaisVitais;
    
    private String examesRealizados;
    private String conduta;
    private String observacoes;
}

// EvolucaoEnfermagem.java
@Entity
@Table(name = "evolucoes_enfermagem")
public class EvolucaoEnfermagem extends BaseEntity {
    @ManyToOne
    private Internacao internacao;
    
    @ManyToOne
    private ProfissionaisSaude enfermeiro;
    
    private LocalDateTime dataHora;
    
    @Embedded
    private SinaisVitais sinaisVitais;
    
    private String medicaçõesAdministradas; // JSON ou texto
    private String cuidadosRealizados;
    private String observacoes;
}

// TransferenciaLeito.java
@Entity
@Table(name = "transferencias_leito")
public class TransferenciaLeito extends BaseEntity {
    @ManyToOne
    private Internacao internacao;
    
    @ManyToOne
    private Leito leitoOrigem;
    
    @ManyToOne
    private Leito leitoDestino;
    
    private LocalDateTime dataHora;
    private String motivo;
    private UUID responsavelTransferencia;
    private String observacoes;
}

// ContaInternacao.java
@Entity
@Table(name = "contas_internacao")
public class ContaInternacao extends BaseEntity {
    @OneToOne
    private Internacao internacao;
    
    private LocalDate dataFechamento;
    
    @Enumerated(EnumType.STRING)
    private StatusConta status;
    
    private Integer quantidadeDiarias;
    private BigDecimal valorDiarias;
    private BigDecimal valorProcedimentos;
    private BigDecimal valorMedicacoes;
    private BigDecimal valorMateriais;
    private BigDecimal valorExames;
    private BigDecimal valorTotal;
    
    private Boolean ajustada;
    private String justificativaAjuste;
    private UUID ajustadaPor;
    private LocalDateTime dataAjuste;
    
    @OneToMany(mappedBy = "conta")
    private List<ItemContaInternacao> itens;
}

// ItemContaInternacao.java
@Entity
@Table(name = "itens_conta_internacao")
public class ItemContaInternacao extends BaseEntity {
    @ManyToOne
    private ContaInternacao conta;
    
    private String tipoItem; // DIARIA, PROCEDIMENTO, MEDICACAO, MATERIAL, EXAME
    private String descricao;
    private Integer quantidade;
    private BigDecimal valorUnitario;
    private BigDecimal valorTotal;
    private LocalDate dataItem;
}
```

### Enums Necessários

```java
public enum StatusLeito {
    DISPONIVEL,
    OCUPADO,
    MANUTENCAO,
    BLOQUEADO
}

public enum SexoPermitidoLeito {
    MASCULINO,
    FEMININO,
    AMBOS
}

public enum TipoInternacao {
    ELETIVA,
    URGENCIA,
    EMERGENCIA
}

public enum OrigemInternacao {
    PRONTO_SOCORRO,
    AMBULATORIO,
    TRANSFERENCIA,
    OUTRO
}

public enum StatusInternacao {
    RESERVADA,
    ADMITIDA,
    ALTA,
    OBITO,
    CANCELADA
}

public enum TipoAlta {
    ALTA_MEDICA,
    ALTA_PEDIDO,
    OBITO,
    TRANSFERENCIA,
    EVASAO
}

public enum StatusReserva {
    ATIVA,
    UTILIZADA,
    CANCELADA,
    EXPIRADA
}

public enum StatusConta {
    ABERTA,
    FECHADA,
    FATURADA,
    CANCELADA
}
```

### Embeddable Classes

```java
// SinaisVitais.java
@Embeddable
public class SinaisVitais {
    private BigDecimal temperatura; // °C
    private Integer pressaoSistolica;
    private Integer pressaoDiastolica;
    private Integer frequenciaCardiaca; // bpm
    private Integer frequenciaRespiratoria; // rpm
    private BigDecimal saturacaoOxigenio; // %
    private BigDecimal glicemia; // mg/dL
    private String observacoes;
}
```

---

## 🔄 Fluxo de Processo

### Fluxo de Internação

```
1. Reserva de leito (opcional)
   ↓
2. Admissão do paciente
   ↓
3. Atribuição de leito
   ↓
4. Durante internação:
   - Evoluções médicas
   - Evoluções de enfermagem
   - Prescrições
   - Procedimentos
   - Transferências (se necessário)
   ↓
5. Alta do paciente
   ↓
6. Fechamento de conta
   ↓
7. Faturamento
```

---

## 🔐 Regras de Negócio

### RB-001: Reserva de Leito
- Reserva expira em 24 horas se não utilizada
- Leito reservado não pode ser atribuído a outro paciente
- Reserva pode ser cancelada a qualquer momento

### RB-002: Admissão
- Paciente deve estar cadastrado no sistema
- Leito deve estar disponível ou reservado para o paciente
- Médico responsável deve estar ativo
- CID principal é obrigatório

### RB-003: Atribuição de Leito
- Leito deve estar disponível
- Sexo do paciente deve ser compatível com leito
- Especialidade deve ser compatível (se leito específico)

### RB-004: Transferência
- Leito destino deve estar disponível
- Motivo da transferência é obrigatório
- Histórico de transferências deve ser mantido

### RB-005: Alta
- Alta médica requer autorização do médico responsável
- Alta a pedido requer termo de responsabilidade
- Óbito requer registro de data/hora e causa
- Resumo de alta é obrigatório para alta médica

### RB-006: Fechamento de Conta
- Conta só pode ser fechada após alta
- Todos os itens devem ser registrados
- Ajustes em conta fechada requerem autorização especial
- Log de auditoria obrigatório para ajustes

### RB-007: Evoluções
- Evolução médica deve ser feita pelo médico responsável ou designado
- Evolução de enfermagem deve ser feita por enfermeiro
- Mínimo de uma evolução por dia (médica ou enfermagem)

---

## 📱 APIs REST Necessárias

### Endpoints de Leitos
- `POST /api/v1/internacao/leitos` - Criar
- `GET /api/v1/internacao/leitos` - Listar
- `GET /api/v1/internacao/leitos/disponiveis` - Listar disponíveis
- `GET /api/v1/internacao/leitos/{id}` - Obter
- `PUT /api/v1/internacao/leitos/{id}` - Atualizar

### Endpoints de Internação
- `POST /api/v1/internacao/internacoes` - Criar/admitir
- `GET /api/v1/internacao/internacoes` - Listar
- `GET /api/v1/internacao/internacoes/{id}` - Obter
- `POST /api/v1/internacao/internacoes/{id}/alta` - Dar alta
- `DELETE /api/v1/internacao/internacoes/{id}` - Cancelar

### Endpoints de Reservas
- `POST /api/v1/internacao/reservas` - Criar reserva
- `GET /api/v1/internacao/reservas` - Listar
- `DELETE /api/v1/internacao/reservas/{id}` - Cancelar

### Endpoints de Transferências
- `POST /api/v1/internacao/transferencias` - Transferir leito

### Endpoints de Evoluções
- `POST /api/v1/internacao/evolucoes-medicas` - Criar evolução médica
- `POST /api/v1/internacao/evolucoes-enfermagem` - Criar evolução enfermagem
- `GET /api/v1/internacao/internacoes/{id}/evolucoes` - Listar evoluções

### Endpoints de Contas
- `POST /api/v1/internacao/contas/{id}/fechar` - Fechar conta
- `GET /api/v1/internacao/contas/{id}` - Obter conta
- `PUT /api/v1/internacao/contas/{id}/ajustar` - Ajustar conta

### Endpoints de Dashboard
- `GET /api/v1/internacao/dashboard/situacao-leitos` - Situação atual
- `GET /api/v1/internacao/dashboard/ocupacao` - Taxa de ocupação

---

## 🚀 Fases de Implementação

### Fase 1: Cadastros Básicos (1 semana)
- Leitos
- Tipos de acomodação
- Setores

### Fase 2: Processo de Internação (2 semanas)
- Admissão
- Reserva de leitos
- Alta
- Cancelamento

### Fase 3: Acompanhamento Clínico (2 semanas)
- Evoluções médicas
- Evoluções de enfermagem
- Integração com prescrições

### Fase 4: Transferências (1 semana)
- Transferência entre leitos
- Histórico

### Fase 5: Contas (2 semanas)
- Fechamento de conta
- Itens da conta
- Ajustes

### Fase 6: Relatórios (1 semana)
- Todos os relatórios

**Total estimado: 9 semanas**

