# Módulo: TFD - Tratamento Fora do Domicílio

## 📋 Visão Geral (Para Product Owner)

O módulo de TFD (Tratamento Fora do Domicílio) permite gerenciar o transporte e despesas de pacientes que precisam se deslocar para realizar tratamentos em outras unidades de saúde. Este módulo é essencial para municípios que precisam garantir o acesso à saúde para pacientes que não têm condições de se deslocar por conta própria.

### Objetivo de Negócio
- Facilitar o acesso ao tratamento de saúde para pacientes que residem longe das unidades especializadas
- Controlar custos de transporte e despesas relacionadas
- Garantir rastreabilidade e auditoria dos processos de TFD
- Integrar com sistemas de faturamento (BPA)

### Benefícios
- Redução de custos através do controle de despesas
- Melhoria no acesso à saúde para população de baixa renda
- Rastreabilidade completa dos processos
- Relatórios gerenciais para tomada de decisão

---

## 🎯 Funcionalidades Necessárias

### 1. Cadastros Básicos

#### 1.1 Cadastro de Unidades Assistenciais
- **Descrição**: Cadastrar unidades de saúde que podem receber pacientes via TFD
- **Campos necessários**:
  - Nome da unidade
  - CNES
  - Endereço completo
  - Tipo de unidade (hospital, clínica, etc.)
  - Especialidades disponíveis
  - Contato (telefone, email)
  - Status (ativo/inativo)

#### 1.2 Cadastro de Tipos de TFD
- **Descrição**: Definir tipos de tratamento que podem ser realizados via TFD
- **Exemplos**: Quimioterapia, Radioterapia, Cirurgias, Consultas especializadas
- **Campos necessários**:
  - Código
  - Descrição
  - Tempo médio de tratamento
  - Periodicidade

#### 1.3 Cadastro de Motoristas
- **Descrição**: Cadastrar motoristas responsáveis pelo transporte
- **Campos necessários**:
  - Nome completo
  - CPF
  - CNH (número e categoria)
  - Telefone
  - Veículo utilizado
  - Status (ativo/inativo)

#### 1.4 Cadastro de Tipos de Despesa
- **Descrição**: Tipos de despesas que podem ser reembolsadas
- **Exemplos**: Passagem, Alimentação, Hospedagem, Taxi
- **Campos necessários**:
  - Código
  - Descrição
  - Valor máximo permitido
  - Documentação necessária

#### 1.5 Cadastro de Tipos de Transporte
- **Descrição**: Meios de transporte disponíveis
- **Exemplos**: Ônibus, Van, Ambulância, Taxi
- **Campos necessários**:
  - Código
  - Descrição
  - Capacidade
  - Custo por km

### 2. Configurações

#### 2.1 Configuração de Despesas por Unidade
- **Descrição**: Definir quais tipos de despesas são permitidas para cada unidade assistencial
- **Regra**: Cada unidade pode ter regras específicas de despesas permitidas

#### 2.2 Perfis de Acesso
- **Descrição**: Definir perfis de usuário com permissões específicas
- **Perfis necessários**:
  - **Usuário**: Pode solicitar TFD
  - **Auditor**: Pode autorizar/rejeitar solicitações
  - **Manutenção**: Pode cadastrar e configurar

### 3. Processo de Solicitação e Autorização

#### 3.1 Registro da Ficha TFD
- **Descrição**: Formulário completo para solicitação de TFD
- **Informações necessárias**:
  - Dados do paciente (vinculado ao cadastro existente)
  - Dados do acompanhante (se necessário)
  - Tratamento a ser realizado
  - CID principal
  - Unidade de destino
  - Justificativa médica
  - Data prevista do tratamento
  - Quantidade de viagens necessárias

#### 3.2 Avaliação do Assistente Social
- **Descrição**: Antes da autorização médica, o assistente social avalia a necessidade
- **Campos**:
  - Renda familiar
  - Condições socioeconômicas
  - Necessidade de acompanhante
  - Recomendação (aprovado/reprovado)
  - Observações

#### 3.3 Autorização/Rejeição pelo Médico Auditor
- **Descrição**: Médico auditor analisa e autoriza ou rejeita a solicitação
- **Campos**:
  - Status (pendente/autorizado/rejeitado)
  - Data de autorização
  - Médico responsável
  - Justificativa (se rejeitado)
  - Prazo de validade da autorização

### 4. Gerenciamento de Viagens

#### 4.1 Registro de Viagens
- **Descrição**: Cadastrar viagens a serem realizadas
- **Informações**:
  - Ficha TFD vinculada
  - Data e horário de saída
  - Data e horário de retorno
  - Rota (origem e destino)
  - Motorista responsável
  - Veículo utilizado
  - Passageiros (paciente + acompanhante)

#### 4.2 Organização de Viagens por Motorista
- **Descrição**: Agrupar viagens por motorista para otimização de rotas
- **Funcionalidade**: Visualizar todas as viagens de um motorista em um período

#### 4.3 Registro de Gastos e Despesas
- **Descrição**: Registrar todas as despesas da viagem
- **Informações**:
  - Tipo de despesa
  - Valor
  - Data
  - Comprovante (upload de arquivo)
  - Status (pendente/aprovado/rejeitado)

### 5. Relatórios

#### 5.1 Relatório de Programação de Viagens
- **Descrição**: Lista de viagens programadas por período
- **Filtros**: Data, motorista, unidade destino, status

#### 5.2 Relatório de Custos das Viagens
- **Descrição**: Análise de custos por período
- **Informações**: Total gasto, média por viagem, por tipo de despesa

#### 5.3 Relatório de Tratamento por CID
- **Descrição**: Pacientes em tratamento agrupados por CID
- **Uso**: Análise epidemiológica

#### 5.4 Relatório de Pacientes em Tratamento
- **Descrição**: Lista de pacientes ativos em TFD
- **Informações**: Paciente, tratamento, unidade destino, data início

#### 5.5 Relatório por Tipo de TFD
- **Descrição**: Estatísticas por tipo de tratamento
- **Informações**: Quantidade, custos, tempo médio

### 6. Integração com Faturamento

#### 6.1 Lançamento de Procedimentos Realizados
- **Descrição**: Registrar procedimentos realizados durante o TFD
- **Integração**: Gerar automaticamente faturamento BPA

---

## 📐 Arquitetura e Classes

### Entidades Principais

```java
// UnidadeAssistencial.java
@Entity
@Table(name = "unidades_assistenciais")
public class UnidadeAssistencial extends BaseEntity {
    private String nome;
    private String cnes;
    private String tipoUnidade;
    @ManyToOne
    private Estabelecimentos estabelecimento;
    @ManyToMany
    private List<EspecialidadesMedicas> especialidades;
    @OneToMany
    private List<TipoDespesaUnidade> despesasPermitidas;
}

// TipoTFD.java
@Entity
@Table(name = "tipos_tfd")
public class TipoTFD extends BaseEntityWithoutTenant {
    private String codigo;
    private String descricao;
    private Integer tempoMedioTratamento; // em dias
    private String periodicidade; // diário, semanal, mensal
}

// Motorista.java
@Entity
@Table(name = "motoristas")
public class Motorista extends BaseEntity {
    private String nomeCompleto;
    private String cpf;
    private String cnh;
    private String categoriaCnh;
    private String telefone;
    private String veiculoUtilizado;
    private Boolean ativo;
}

// TipoDespesa.java
@Entity
@Table(name = "tipos_despesa")
public class TipoDespesa extends BaseEntityWithoutTenant {
    private String codigo;
    private String descricao;
    private BigDecimal valorMaximo;
    private String documentacaoNecessaria;
}

// TipoTransporte.java
@Entity
@Table(name = "tipos_transporte")
public class TipoTransporte extends BaseEntityWithoutTenant {
    private String codigo;
    private String descricao;
    private Integer capacidade;
    private BigDecimal custoPorKm;
}

// FichaTFD.java
@Entity
@Table(name = "fichas_tfd")
public class FichaTFD extends BaseEntity {
    @ManyToOne
    private Paciente paciente;
    
    @ManyToOne
    private Paciente acompanhante; // opcional
    
    @ManyToOne
    private TipoTFD tipoTfd;
    
    @ManyToOne
    private CidDoencas cidPrincipal;
    
    @ManyToOne
    private UnidadeAssistencial unidadeDestino;
    
    @ManyToOne
    private Medicos medicoSolicitante;
    
    private String justificativaMedica;
    private LocalDate dataInicioTratamento;
    private LocalDate dataFimTratamento;
    private Integer quantidadeViagens;
    
    // Avaliação Assistente Social
    private BigDecimal rendaFamiliar;
    private String condicoesSocioeconomicas;
    private Boolean necessitaAcompanhante;
    private String recomendacaoAssistenteSocial;
    private String observacoesAssistenteSocial;
    private UUID assistenteSocialId;
    private LocalDateTime dataAvaliacaoAssistenteSocial;
    
    // Autorização Médica
    @Enumerated(EnumType.STRING)
    private StatusAutorizacaoTFD statusAutorizacao;
    private UUID medicoAuditorId;
    private LocalDateTime dataAutorizacao;
    private String justificativaRejeicao;
    private LocalDate dataValidadeAutorizacao;
    
    @OneToMany(mappedBy = "fichaTfd")
    private List<ViagemTFD> viagens;
    
    @OneToMany(mappedBy = "fichaTfd")
    private List<ProcedimentoTFD> procedimentos;
}

// ViagemTFD.java
@Entity
@Table(name = "viagens_tfd")
public class ViagemTFD extends BaseEntity {
    @ManyToOne
    private FichaTFD fichaTfd;
    
    @ManyToOne
    private Motorista motorista;
    
    @ManyToOne
    private TipoTransporte tipoTransporte;
    
    private LocalDateTime dataHoraSaida;
    private LocalDateTime dataHoraRetorno;
    
    @ManyToOne
    private Estabelecimentos origem;
    
    @ManyToOne
    private UnidadeAssistencial destino;
    
    private String rota;
    private BigDecimal distanciaKm;
    private Integer quantidadePassageiros;
    
    @OneToMany(mappedBy = "viagem")
    private List<DespesaViagem> despesas;
    
    @Enumerated(EnumType.STRING)
    private StatusViagem status;
}

// DespesaViagem.java
@Entity
@Table(name = "despesas_viagem")
public class DespesaViagem extends BaseEntity {
    @ManyToOne
    private ViagemTFD viagem;
    
    @ManyToOne
    private TipoDespesa tipoDespesa;
    
    private BigDecimal valor;
    private LocalDate dataDespesa;
    private String descricao;
    private String caminhoComprovante; // URL do arquivo
    private String nomeArquivoComprovante;
    
    @Enumerated(EnumType.STRING)
    private StatusAprovacaoDespesa status;
    private String justificativaRejeicao;
    private UUID aprovadoPor;
    private LocalDateTime dataAprovacao;
}

// ProcedimentoTFD.java
@Entity
@Table(name = "procedimentos_tfd")
public class ProcedimentoTFD extends BaseEntity {
    @ManyToOne
    private FichaTFD fichaTfd;
    
    @ManyToOne
    private CatalogoProcedimentos procedimento;
    
    private LocalDate dataRealizacao;
    private String observacoes;
    private Boolean faturadoBPA;
    private LocalDateTime dataFaturamentoBPA;
}

// TipoDespesaUnidade.java (Tabela de relacionamento)
@Entity
@Table(name = "tipos_despesa_unidade")
public class TipoDespesaUnidade extends BaseEntity {
    @ManyToOne
    private UnidadeAssistencial unidade;
    
    @ManyToOne
    private TipoDespesa tipoDespesa;
    
    private Boolean ativo;
    private BigDecimal valorMaximoEspecifico; // pode sobrescrever o valor máximo
}
```

### Enums Necessários

```java
public enum StatusAutorizacaoTFD {
    PENDENTE_ASSISTENTE_SOCIAL,
    PENDENTE_AUDITORIA_MEDICA,
    AUTORIZADO,
    REJEITADO,
    CANCELADO,
    EXPIRADO
}

public enum StatusViagem {
    AGENDADA,
    EM_ANDAMENTO,
    CONCLUIDA,
    CANCELADA
}

public enum StatusAprovacaoDespesa {
    PENDENTE,
    APROVADO,
    REJEITADO
}
```

### DTOs Principais

```java
// FichaTFDRequest.java
public class FichaTFDRequest {
    private UUID pacienteId;
    private UUID acompanhanteId; // opcional
    private UUID tipoTfdId;
    private UUID cidPrincipalId;
    private UUID unidadeDestinoId;
    private UUID medicoSolicitanteId;
    private String justificativaMedica;
    private LocalDate dataInicioTratamento;
    private LocalDate dataFimTratamento;
    private Integer quantidadeViagens;
}

// ViagemTFDRequest.java
public class ViagemTFDRequest {
    private UUID fichaTfdId;
    private UUID motoristaId;
    private UUID tipoTransporteId;
    private LocalDateTime dataHoraSaida;
    private LocalDateTime dataHoraRetorno;
    private UUID origemId;
    private UUID destinoId;
    private String rota;
    private BigDecimal distanciaKm;
}

// DespesaViagemRequest.java
public class DespesaViagemRequest {
    private UUID viagemId;
    private UUID tipoDespesaId;
    private BigDecimal valor;
    private LocalDate dataDespesa;
    private String descricao;
    private MultipartFile comprovante; // arquivo
}
```

### Services Necessários

```java
public interface FichaTFDService {
    FichaTFDResponse criar(FichaTFDRequest request);
    FichaTFDResponse obterPorId(UUID id);
    Page<FichaTFDResponse> listar(Pageable pageable, FiltroFichaTFD filtro);
    FichaTFDResponse atualizar(UUID id, FichaTFDRequest request);
    void avaliarAssistenteSocial(UUID id, AvaliacaoAssistenteSocialRequest request);
    FichaTFDResponse autorizarRejeitar(UUID id, AutorizacaoTFDRequest request);
    void cancelar(UUID id, String motivo);
}

public interface ViagemTFDService {
    ViagemTFDResponse criar(ViagemTFDRequest request);
    ViagemTFDResponse obterPorId(UUID id);
    Page<ViagemTFDResponse> listarPorMotorista(UUID motoristaId, LocalDate dataInicio, LocalDate dataFim);
    ViagemTFDResponse atualizar(UUID id, ViagemTFDRequest request);
    void iniciarViagem(UUID id);
    void finalizarViagem(UUID id);
}

public interface DespesaViagemService {
    DespesaViagemResponse criar(DespesaViagemRequest request);
    DespesaViagemResponse aprovar(UUID id);
    DespesaViagemResponse rejeitar(UUID id, String justificativa);
    Page<DespesaViagemResponse> listarPorViagem(UUID viagemId);
}

public interface RelatorioTFDService {
    RelatorioProgramacaoViagens gerarRelatorioProgramacaoViagens(LocalDate dataInicio, LocalDate dataFim);
    RelatorioCustosViagens gerarRelatorioCustos(LocalDate dataInicio, LocalDate dataFim);
    RelatorioTratamentoPorCID gerarRelatorioPorCID(LocalDate dataInicio, LocalDate dataFim);
    RelatorioPacientesTratamento gerarRelatorioPacientesEmTratamento();
    RelatorioPorTipoTFD gerarRelatorioPorTipoTFD(LocalDate dataInicio, LocalDate dataFim);
}
```

---

## 🔄 Fluxo de Processo

### Fluxo de Solicitação e Autorização

```
1. Médico solicita TFD
   ↓
2. Assistente Social avalia necessidade
   ↓
3. Se aprovado pelo Assistente Social:
   ↓
4. Médico Auditor analisa
   ↓
5. Se autorizado:
   ↓
6. Sistema gera autorização válida por X dias
   ↓
7. Viagens podem ser agendadas
```

### Fluxo de Viagem

```
1. Agendar viagem vinculada à ficha TFD
   ↓
2. Atribuir motorista e veículo
   ↓
3. Definir rota e horários
   ↓
4. Iniciar viagem (check-in)
   ↓
5. Registrar despesas durante/período da viagem
   ↓
6. Finalizar viagem (check-out)
   ↓
7. Aprovar/rejeitar despesas
```

---

## 📊 Diagrama de Entidades

```
Paciente
    │
    ├─── FichaTFD
    │       │
    │       ├─── TipoTFD
    │       ├─── CidDoencas
    │       ├─── UnidadeAssistencial
    │       │       └─── TipoDespesaUnidade ─── TipoDespesa
    │       ├─── Medicos (solicitante)
    │       ├─── Medicos (auditor)
    │       ├─── ViagemTFD
    │       │       ├─── Motorista
    │       │       ├─── TipoTransporte
    │       │       └─── DespesaViagem ─── TipoDespesa
    │       └─── ProcedimentoTFD ─── CatalogoProcedimentos
```

---

## 🔐 Regras de Negócio

### RB-001: Validação de Solicitação
- **Descrição**: Uma solicitação de TFD só pode ser criada por médico cadastrado no sistema
- **Validações**:
  - Paciente deve estar ativo
  - CID principal deve ser válido
  - Unidade destino deve estar ativa e ter a especialidade necessária
  - Data de início não pode ser no passado

### RB-002: Avaliação Assistente Social
- **Descrição**: Toda solicitação deve passar por avaliação do assistente social antes da auditoria médica
- **Regras**:
  - Apenas usuários com perfil "ASSISTENTE_SOCIAL" podem avaliar
  - Avaliação deve conter renda familiar e condições socioeconômicas
  - Se rejeitado, solicitação é arquivada

### RB-003: Autorização Médica
- **Descrição**: Apenas médicos auditores podem autorizar/rejeitar
- **Regras**:
  - Autorização tem validade de 90 dias (configurável)
  - Se expirada, nova solicitação deve ser feita
  - Rejeição deve conter justificativa obrigatória

### RB-004: Viagens
- **Descrição**: Viagens só podem ser agendadas para fichas autorizadas
- **Regras**:
  - Motorista deve estar ativo
  - Veículo deve ter capacidade suficiente
  - Não pode haver sobreposição de viagens do mesmo motorista
  - Distância mínima para TFD: 50km (configurável)

### RB-005: Despesas
- **Descrição**: Despesas devem ser aprovadas antes do reembolso
- **Regras**:
  - Valor não pode exceder o máximo permitido do tipo de despesa
  - Comprovante é obrigatório para valores acima de R$ 50,00
  - Apenas despesas permitidas para a unidade podem ser cadastradas
  - Despesas devem ser aprovadas por usuário com permissão de auditoria

### RB-006: Faturamento BPA
- **Descrição**: Procedimentos realizados devem ser faturados automaticamente
- **Regras**:
  - Procedimento só pode ser registrado após viagem concluída
  - Faturamento automático ocorre ao finalizar procedimento
  - Integração com módulo de faturamento (a ser desenvolvido)

---

## 🧪 Casos de Teste Principais

### CT-001: Criar Solicitação TFD
1. Médico cria solicitação com dados válidos
2. Sistema valida todos os campos
3. Status inicial: PENDENTE_ASSISTENTE_SOCIAL
4. Notificação enviada para assistente social

### CT-002: Avaliar como Assistente Social
1. Assistente social acessa solicitação pendente
2. Preenche dados socioeconômicos
3. Aprova solicitação
4. Status muda para PENDENTE_AUDITORIA_MEDICA

### CT-003: Autorizar TFD
1. Médico auditor acessa solicitação
2. Analisa justificativa e avaliação
3. Autoriza solicitação
4. Status muda para AUTORIZADO
5. Data de validade é definida

### CT-004: Agendar Viagem
1. Usuário seleciona ficha TFD autorizada
2. Escolhe motorista disponível
3. Define data e horário
4. Sistema valida disponibilidade do motorista
5. Viagem é criada com status AGENDADA

### CT-005: Registrar Despesa
1. Usuário registra despesa da viagem
2. Anexa comprovante
3. Sistema valida valor máximo
4. Despesa criada com status PENDENTE

---

## 📱 APIs REST Necessárias

### Endpoints de Ficha TFD
- `POST /api/v1/tfd/fichas` - Criar solicitação
- `GET /api/v1/tfd/fichas` - Listar fichas (com filtros)
- `GET /api/v1/tfd/fichas/{id}` - Obter por ID
- `PUT /api/v1/tfd/fichas/{id}` - Atualizar
- `POST /api/v1/tfd/fichas/{id}/avaliar-assistente-social` - Avaliar
- `POST /api/v1/tfd/fichas/{id}/autorizar` - Autorizar/rejeitar
- `DELETE /api/v1/tfd/fichas/{id}` - Cancelar

### Endpoints de Viagem
- `POST /api/v1/tfd/viagens` - Criar viagem
- `GET /api/v1/tfd/viagens` - Listar viagens
- `GET /api/v1/tfd/viagens/motorista/{motoristaId}` - Por motorista
- `POST /api/v1/tfd/viagens/{id}/iniciar` - Iniciar viagem
- `POST /api/v1/tfd/viagens/{id}/finalizar` - Finalizar viagem

### Endpoints de Despesas
- `POST /api/v1/tfd/despesas` - Criar despesa (com upload)
- `GET /api/v1/tfd/despesas/viagem/{viagemId}` - Por viagem
- `POST /api/v1/tfd/despesas/{id}/aprovar` - Aprovar
- `POST /api/v1/tfd/despesas/{id}/rejeitar` - Rejeitar

### Endpoints de Relatórios
- `GET /api/v1/tfd/relatorios/programacao-viagens` - Programação
- `GET /api/v1/tfd/relatorios/custos` - Custos
- `GET /api/v1/tfd/relatorios/por-cid` - Por CID
- `GET /api/v1/tfd/relatorios/pacientes-tratamento` - Pacientes em tratamento
- `GET /api/v1/tfd/relatorios/por-tipo` - Por tipo TFD

---

## 🔧 Tecnologias e Dependências

### Dependências Necessárias
- **Upload de Arquivos**: Spring Boot Multipart
- **Geração de Relatórios**: JasperReports ou Apache POI
- **Validação de CNH**: Integração com Detran (futuro)
- **Cálculo de Rotas**: Google Maps API ou OpenRouteService (futuro)

### Integrações Futuras
- Sistema de Faturamento BPA
- Sistema de Notificações (email/SMS)
- Sistema de Geolocalização para rotas

---

## 📈 Métricas e Indicadores

### KPIs Sugeridos
- Tempo médio de aprovação de solicitações
- Taxa de aprovação/rejeição
- Custo médio por viagem
- Número de viagens por mês
- Tempo médio de viagem
- Taxa de utilização de motoristas

---

## 🚀 Fases de Implementação

### Fase 1: Cadastros Básicos (2 semanas)
- Unidades assistenciais
- Tipos de TFD
- Motoristas
- Tipos de despesa e transporte
- Configurações de despesas por unidade

### Fase 2: Processo de Solicitação (3 semanas)
- Ficha TFD
- Avaliação assistente social
- Autorização médica
- Workflow de aprovação

### Fase 3: Gerenciamento de Viagens (2 semanas)
- Agendamento de viagens
- Organização por motorista
- Controle de status

### Fase 4: Despesas (2 semanas)
- Registro de despesas
- Upload de comprovantes
- Aprovação/rejeição

### Fase 5: Relatórios (2 semanas)
- Todos os relatórios listados
- Exportação em PDF/Excel

### Fase 6: Integração BPA (1 semana)
- Lançamento de procedimentos
- Integração com faturamento

**Total estimado: 12 semanas**

---

## 📝 Observações Importantes

1. **Segurança**: Todas as operações devem ter auditoria (logs)
2. **Performance**: Relatórios podem ser pesados, considerar paginação e cache
3. **Usabilidade**: Interface deve ser intuitiva para usuários não técnicos
4. **Escalabilidade**: Sistema deve suportar múltiplos municípios (multi-tenancy já existe)
5. **Compliance**: Seguir LGPD para dados de pacientes

