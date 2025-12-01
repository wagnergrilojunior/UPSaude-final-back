# Módulo: Centro Cirúrgico

## 📋 Visão Geral (Para Product Owner)

O módulo de Centro Cirúrgico permite gerenciar todo o processo cirúrgico, desde o agendamento até a execução da cirurgia, incluindo controle de materiais, equipamentos, equipes e salas cirúrgicas. Este módulo é essencial para unidades que realizam procedimentos cirúrgicos.

### Objetivo de Negócio
- Gerenciar agendamento e execução de cirurgias
- Controlar materiais e equipamentos cirúrgicos
- Organizar equipes cirúrgicas
- Otimizar uso de salas cirúrgicas
- Garantir rastreabilidade completa

### Benefícios
- Otimização do uso de salas cirúrgicas
- Controle rigoroso de materiais
- Rastreabilidade completa
- Melhoria na segurança do paciente
- Dados para análise e melhoria contínua

---

## 🎯 Funcionalidades Necessárias

### 1. Cadastros Básicos

#### 1.1 Cadastro de Caixas (Sub-Kits)
- **Descrição**: Caixas cirúrgicas que contêm materiais específicos
- **Campos necessários**:
  - Código
  - Descrição
  - Tipo de cirurgia
  - Materiais contidos
  - Quantidade de cada material
  - Status (disponível, em uso, esterilização)

#### 1.2 Cadastro de Materiais Esterilizáveis
- **Descrição**: Materiais que podem ser esterilizados e reutilizados
- **Campos necessários**:
  - Código
  - Descrição
  - Tipo de material
  - Tempo de esterilização
  - Validade após esterilização
  - Quantidade em estoque

#### 1.3 Cadastro de Itens de Lavanderia
- **Descrição**: Itens de roupa/tecido utilizados em cirurgias
- **Campos necessários**:
  - Código
  - Descrição
  - Tamanho
  - Quantidade em estoque
  - Status (limpo, sujo, em lavagem)

#### 1.4 Cadastro de Hemocomponentes
- **Descrição**: Componentes sanguíneos para cirurgias
- **Campos necessários**:
  - Tipo de hemocomponente
  - Quantidade disponível
  - Data de validade
  - Tipo sanguíneo
  - Status

#### 1.5 Cadastro de Equipamentos Cirúrgicos
- **Descrição**: Equipamentos específicos para cirurgias
- **Campos necessários**:
  - Código
  - Descrição
  - Tipo de equipamento
  - Número de série
  - Status (disponível, em manutenção, em uso)
  - Localização

#### 1.6 Cadastro de Kits Cirúrgicos
- **Descrição**: Kits completos para tipos específicos de cirurgia
- **Campos necessários**:
  - Código
  - Descrição
  - Tipo de cirurgia
  - Componentes do kit (materiais, equipamentos, caixas)
  - Quantidade de cada componente

#### 1.7 Cadastro de Cirurgias
- **Descrição**: Tipos de cirurgias realizadas
- **Campos necessários**:
  - Código
  - Descrição
  - Especialidade
  - Tempo médio estimado
  - Complexidade
  - Materiais padrão necessários

#### 1.8 Cadastro de Salas Cirúrgicas
- **Descrição**: Salas disponíveis para cirurgias
- **Campos necessários**:
  - Número da sala
  - Tipo de sala (geral, especializada)
  - Equipamentos disponíveis
  - Capacidade
  - Status (disponível, em manutenção, em uso)

#### 1.9 Cadastro de Leitos de Apoio / Mesa Cirúrgica
- **Descrição**: Leitos e mesas específicas para cirurgias
- **Campos necessários**:
  - Código
  - Descrição
  - Tipo
  - Sala vinculada
  - Status

#### 1.10 Cadastro de Unidades Cirúrgicas
- **Descrição**: Agrupamento de salas e recursos
- **Campos necessários**:
  - Nome
  - Descrição
  - Salas vinculadas
  - Responsável

#### 1.11 Cadastro de Técnicas Anestésicas
- **Descrição**: Tipos de anestesia disponíveis
- **Campos necessários**:
  - Código
  - Descrição
  - Tipo (geral, regional, local)
  - Indicações
  - Contraindicações

#### 1.12 Cadastro de Origens
- **Descrição**: Origem do paciente para cirurgia
- **Exemplos**: Ambulatório, Internação, Pronto-socorro, Transferência
- **Campos necessários**:
  - Código
  - Descrição

#### 1.13 Cadastro de Equipes Cirúrgicas
- **Descrição**: Equipes que realizam cirurgias
- **Campos necessários**:
  - Nome da equipe
  - Cirurgião principal
  - Cirurgião auxiliar (se houver)
  - Anestesista
  - Instrumentador
  - Enfermeiro circulante
  - Status (ativa, inativa)

### 2. Processo Cirúrgico

#### 2.1 Agendamento de Cirurgia
- **Descrição**: Agendar procedimento cirúrgico
- **Informações necessárias**:
  - Paciente
  - Tipo de cirurgia
  - Médico cirurgião
  - Equipe cirúrgica
  - Sala cirúrgica
  - Data e horário
  - Técnica anestésica
  - Origem do paciente
  - Urgência
  - Materiais necessários
  - Equipamentos necessários

#### 2.2 Preparação Pré-Cirúrgica
- **Descrição**: Preparar materiais e equipamentos
- **Funcionalidades**:
  - Reservar materiais
  - Reservar equipamentos
  - Preparar kits
  - Verificar disponibilidade de hemocomponentes

#### 2.3 Execução da Cirurgia
- **Descrição**: Registrar execução do procedimento
- **Informações**:
  - Início da cirurgia
  - Início da anestesia
  - Início do procedimento
  - Fim do procedimento
  - Fim da anestesia
  - Fim da cirurgia
  - Complicações (se houver)
  - Observações

#### 2.4 Pós-Operatório
- **Descrição**: Registros pós-cirúrgicos
- **Informações**:
  - Condição do paciente
  - Cuidados necessários
  - Medicações prescritas
  - Alta da sala cirúrgica

### 3. Controle de Materiais

#### 3.1 Movimentação de Materiais
- **Descrição**: Controle de entrada e saída de materiais
- **Funcionalidades**:
  - Retirada de materiais do estoque
  - Devolução após uso
  - Controle de materiais descartáveis vs. reutilizáveis

#### 3.2 Esterilização
- **Descrição**: Controle do processo de esterilização
- **Informações**:
  - Material esterilizado
  - Data de esterilização
  - Método utilizado
  - Validade
  - Responsável

#### 3.3 Controle de Lavanderia
- **Descrição**: Controle de itens de lavanderia
- **Funcionalidades**:
  - Retirada de itens limpos
  - Devolução de itens sujos
  - Controle de lavagem

---

## 📐 Arquitetura e Classes

### Entidades Principais

```java
// CaixaCirurgica.java
@Entity
@Table(name = "caixas_cirurgicas")
public class CaixaCirurgica extends BaseEntity {
    private String codigo;
    private String descricao;
    private String tipoCirurgia;
    
    @OneToMany(mappedBy = "caixa")
    private List<MaterialCaixa> materiais;
    
    @Enumerated(EnumType.STRING)
    private StatusCaixa status;
}

// MaterialCaixa.java
@Entity
@Table(name = "materiais_caixa")
public class MaterialCaixa extends BaseEntity {
    @ManyToOne
    private CaixaCirurgica caixa;
    
    @ManyToOne
    private MaterialEsterilizavel material;
    
    private Integer quantidade;
}

// MaterialEsterilizavel.java
@Entity
@Table(name = "materiais_esterilizaveis")
public class MaterialEsterilizavel extends BaseEntity {
    private String codigo;
    private String descricao;
    private String tipoMaterial;
    private Integer tempoEsterilizacaoMinutos;
    private Integer validadeAposEsterilizacaoDias;
    private Integer quantidadeEstoque;
    
    @OneToMany(mappedBy = "material")
    private List<Esterilizacao> esterilizacoes;
}

// ItemLavanderia.java
@Entity
@Table(name = "itens_lavanderia")
public class ItemLavanderia extends BaseEntity {
    private String codigo;
    private String descricao;
    private String tamanho;
    private Integer quantidadeEstoque;
    
    @Enumerated(EnumType.STRING)
    private StatusLavanderia status;
}

// Hemocomponente.java
@Entity
@Table(name = "hemocomponentes")
public class Hemocomponente extends BaseEntity {
    private String tipo; // SANGUE_TOTAL, CONCENTRADO_HEMACIAS, PLASMA, PLAQUETAS
    private Integer quantidadeDisponivel;
    private LocalDate dataValidade;
    private String tipoSanguineo;
    
    @Enumerated(EnumType.STRING)
    private StatusHemocomponente status;
}

// EquipamentoCirurgico.java
@Entity
@Table(name = "equipamentos_cirurgicos")
public class EquipamentoCirurgico extends BaseEntity {
    private String codigo;
    private String descricao;
    private String tipoEquipamento;
    private String numeroSerie;
    private String localizacao;
    
    @Enumerated(EnumType.STRING)
    private StatusEquipamento status;
}

// KitCirurgico.java
@Entity
@Table(name = "kits_cirurgicos")
public class KitCirurgico extends BaseEntity {
    private String codigo;
    private String descricao;
    private String tipoCirurgia;
    
    @OneToMany(mappedBy = "kit")
    private List<ComponenteKit> componentes;
}

// ComponenteKit.java
@Entity
@Table(name = "componentes_kit")
public class ComponenteKit extends BaseEntity {
    @ManyToOne
    private KitCirurgico kit;
    
    private String tipoComponente; // MATERIAL, EQUIPAMENTO, CAIXA
    private UUID componenteId; // ID do componente específico
    private Integer quantidade;
}

// TipoCirurgia.java
@Entity
@Table(name = "tipos_cirurgia")
public class TipoCirurgia extends BaseEntityWithoutTenant {
    private String codigo;
    private String descricao;
    
    @ManyToOne
    private EspecialidadesMedicas especialidade;
    
    private Integer tempoMedioEstimadoMinutos;
    private String complexidade; // BAIXA, MEDIA, ALTA
    
    @OneToMany(mappedBy = "tipoCirurgia")
    private List<MaterialNecessarioCirurgia> materiaisPadrao;
}

// SalaCirurgica.java
@Entity
@Table(name = "salas_cirurgicas")
public class SalaCirurgica extends BaseEntity {
    @ManyToOne
    private Estabelecimentos estabelecimento;
    
    private String numeroSala;
    private String tipoSala; // GERAL, ESPECIALIZADA
    
    @ManyToMany
    private List<EquipamentoCirurgico> equipamentosDisponiveis;
    
    private Integer capacidade;
    
    @Enumerated(EnumType.STRING)
    private StatusSala status;
    
    @OneToMany(mappedBy = "sala")
    private List<LeitoApoioCirurgico> leitosApoio;
    
    @OneToMany(mappedBy = "sala")
    private List<AgendamentoCirurgia> agendamentos;
}

// LeitoApoioCirurgico.java
@Entity
@Table(name = "leitos_apoio_cirurgico")
public class LeitoApoioCirurgico extends BaseEntity {
    @ManyToOne
    private SalaCirurgica sala;
    
    private String codigo;
    private String descricao;
    private String tipo; // LEITO, MESA_CIRURGICA
    
    @Enumerated(EnumType.STRING)
    private StatusLeitoApoio status;
}

// UnidadeCirurgica.java
@Entity
@Table(name = "unidades_cirurgicas")
public class UnidadeCirurgica extends BaseEntity {
    @ManyToOne
    private Estabelecimentos estabelecimento;
    
    private String nome;
    private String descricao;
    
    @ManyToMany
    private List<SalaCirurgica> salas;
    
    @ManyToOne
    private ProfissionaisSaude responsavel;
}

// TecnicaAnestesica.java
@Entity
@Table(name = "tecnicas_anestesicas")
public class TecnicaAnestesica extends BaseEntityWithoutTenant {
    private String codigo;
    private String descricao;
    private String tipo; // GERAL, REGIONAL, LOCAL
    private String indicacoes;
    private String contraindicacoes;
}

// OrigemCirurgia.java
@Entity
@Table(name = "origens_cirurgia")
public class OrigemCirurgia extends BaseEntityWithoutTenant {
    private String codigo;
    private String descricao;
}

// EquipeCirurgica.java
@Entity
@Table(name = "equipes_cirurgicas")
public class EquipeCirurgica extends BaseEntity {
    private String nome;
    
    @ManyToOne
    private Medicos cirurgiaoPrincipal;
    
    @ManyToOne
    private Medicos cirurgiaoAuxiliar; // opcional
    
    @ManyToOne
    private Medicos anestesista;
    
    @ManyToOne
    private ProfissionaisSaude instrumentador;
    
    @ManyToOne
    private ProfissionaisSaude enfermeiroCirculante;
    
    private Boolean ativa;
    
    @OneToMany(mappedBy = "equipe")
    private List<AgendamentoCirurgia> agendamentos;
}

// AgendamentoCirurgia.java
@Entity
@Table(name = "agendamentos_cirurgia")
public class AgendamentoCirurgia extends BaseEntity {
    @ManyToOne
    private Paciente paciente;
    
    @ManyToOne
    private TipoCirurgia tipoCirurgia;
    
    @ManyToOne
    private Medicos cirurgiao;
    
    @ManyToOne
    private EquipeCirurgica equipe;
    
    @ManyToOne
    private SalaCirurgica sala;
    
    @ManyToOne
    private TecnicaAnestesica tecnicaAnestesica;
    
    @ManyToOne
    private OrigemCirurgia origem;
    
    private LocalDateTime dataHoraAgendada;
    private Integer duracaoEstimadaMinutos;
    
    @Enumerated(EnumType.STRING)
    private UrgenciaCirurgia urgencia;
    
    @Enumerated(EnumType.STRING)
    private StatusAgendamentoCirurgia status;
    
    @OneToMany(mappedBy = "agendamento")
    private List<MaterialReservadoCirurgia> materiaisReservados;
    
    @OneToMany(mappedBy = "agendamento")
    private List<EquipamentoReservadoCirurgia> equipamentosReservados;
    
    @OneToOne(mappedBy = "agendamento")
    private ExecucaoCirurgia execucao;
}

// MaterialReservadoCirurgia.java
@Entity
@Table(name = "materiais_reservados_cirurgia")
public class MaterialReservadoCirurgia extends BaseEntity {
    @ManyToOne
    private AgendamentoCirurgia agendamento;
    
    private String tipoMaterial; // MATERIAL_ESTERILIZAVEL, ITEM_LAVANDERIA, HEMOCOMPONENTE
    private UUID materialId;
    private Integer quantidade;
    
    @Enumerated(EnumType.STRING)
    private StatusReserva status; // RESERVADO, RETIRADO, DEVOLVIDO
}

// ExecucaoCirurgia.java
@Entity
@Table(name = "execucoes_cirurgia")
public class ExecucaoCirurgia extends BaseEntity {
    @OneToOne
    private AgendamentoCirurgia agendamento;
    
    private LocalDateTime inicioCirurgia;
    private LocalDateTime inicioAnestesia;
    private LocalDateTime inicioProcedimento;
    private LocalDateTime fimProcedimento;
    private LocalDateTime fimAnestesia;
    private LocalDateTime fimCirurgia;
    
    private String complicacoes;
    private String observacoes;
    
    @Enumerated(EnumType.STRING)
    private StatusExecucao status;
    
    @OneToMany(mappedBy = "execucao")
    private List<RegistroPosOperatorio> registrosPosOperatorio;
}

// RegistroPosOperatorio.java
@Entity
@Table(name = "registros_pos_operatorio")
public class RegistroPosOperatorio extends BaseEntity {
    @ManyToOne
    private ExecucaoCirurgia execucao;
    
    private LocalDateTime dataHora;
    private String condicaoPaciente;
    private String cuidadosNecessarios;
    private String medicaçõesPrescritas;
    private LocalDateTime dataHoraAltaSala;
}
```

### Enums Necessários

```java
public enum StatusCaixa {
    DISPONIVEL,
    EM_USO,
    ESTERILIZACAO
}

public enum StatusLavanderia {
    LIMPO,
    SUJO,
    EM_LAVAGEM
}

public enum StatusHemocomponente {
    DISPONIVEL,
    RESERVADO,
    UTILIZADO,
    VENCIDO
}

public enum StatusEquipamento {
    DISPONIVEL,
    EM_MANUTENCAO,
    EM_USO
}

public enum StatusSala {
    DISPONIVEL,
    EM_MANUTENCAO,
    EM_USO
}

public enum StatusLeitoApoio {
    DISPONIVEL,
    EM_USO
}

public enum UrgenciaCirurgia {
    ELETIVA,
    URGENTE,
    EMERGENCIAL
}

public enum StatusAgendamentoCirurgia {
    AGENDADA,
    CONFIRMADA,
    EM_ANDAMENTO,
    CONCLUIDA,
    CANCELADA
}

public enum StatusReserva {
    RESERVADO,
    RETIRADO,
    DEVOLVIDO
}

public enum StatusExecucao {
    AGUARDANDO,
    EM_ANDAMENTO,
    CONCLUIDA,
    CANCELADA
}
```

---

## 🔄 Fluxo de Processo

### Fluxo de Cirurgia

```
1. Agendamento da cirurgia
   ↓
2. Reserva de materiais e equipamentos
   ↓
3. Preparação pré-cirúrgica
   ↓
4. Início da cirurgia
   ↓
5. Execução do procedimento
   ↓
6. Fim da cirurgia
   ↓
7. Registro pós-operatório
   ↓
8. Devolução de materiais reutilizáveis
   ↓
9. Esterilização (se necessário)
```

---

## 🔐 Regras de Negócio

### RB-001: Agendamento
- Sala deve estar disponível no horário
- Equipe deve estar disponível
- Materiais e equipamentos devem estar disponíveis
- Não pode haver sobreposição de cirurgias na mesma sala

### RB-002: Reserva de Materiais
- Materiais são reservados no momento do agendamento
- Reserva é liberada se cirurgia for cancelada
- Materiais descartáveis são consumidos
- Materiais reutilizáveis são devolvidos após uso

### RB-003: Esterilização
- Materiais esterilizáveis devem ser esterilizados após uso
- Validade da esterilização deve ser respeitada
- Material vencido não pode ser usado

### RB-004: Hemocomponentes
- Verificar compatibilidade de tipo sanguíneo
- Verificar validade
- Reservar antes da cirurgia

### RB-005: Execução
- Todos os tempos devem ser registrados
- Complicações devem ser documentadas
- Observações são obrigatórias

---

## 📱 APIs REST Necessárias

### Endpoints de Cadastros
- `POST /api/v1/centro-cirurgico/caixas` - Criar caixa
- `POST /api/v1/centro-cirurgico/materiais-esterilizaveis` - Criar material
- `POST /api/v1/centro-cirurgico/kits` - Criar kit
- `POST /api/v1/centro-cirurgico/salas` - Criar sala
- `POST /api/v1/centro-cirurgico/equipes` - Criar equipe

### Endpoints de Agendamento
- `POST /api/v1/centro-cirurgico/agendamentos` - Agendar cirurgia
- `GET /api/v1/centro-cirurgico/agendamentos` - Listar agendamentos
- `POST /api/v1/centro-cirurgico/agendamentos/{id}/confirmar` - Confirmar
- `DELETE /api/v1/centro-cirurgico/agendamentos/{id}` - Cancelar

### Endpoints de Execução
- `POST /api/v1/centro-cirurgico/execucoes` - Iniciar execução
- `PUT /api/v1/centro-cirurgico/execucoes/{id}` - Atualizar tempos
- `POST /api/v1/centro-cirurgico/execucoes/{id}/finalizar` - Finalizar

---

## 🚀 Fases de Implementação

### Fase 1: Cadastros Básicos (2 semanas)
- Todos os cadastros listados

### Fase 2: Agendamento (2 semanas)
- Sistema de agendamento
- Reserva de materiais

### Fase 3: Execução (2 semanas)
- Registro de execução
- Controle de tempos

### Fase 4: Controle de Materiais (2 semanas)
- Movimentação
- Esterilização
- Lavanderia

**Total estimado: 8 semanas**

