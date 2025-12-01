# Módulo: Regulação e PPI (Programação Pactuada e Integrada)

## 📋 Visão Geral (Para Product Owner)

O módulo de Regulação e PPI permite gerenciar a autorização e controle de procedimentos de média e alta complexidade, garantindo que os recursos sejam utilizados de forma eficiente e dentro das cotas estabelecidas pelos contratos com prestadores de serviço. Este módulo é essencial para o controle financeiro e operacional do sistema de saúde.

### Objetivo de Negócio
- Controlar a execução de procedimentos de média e alta complexidade
- Gerenciar cotas contratuais com prestadores
- Garantir que procedimentos sejam autorizados antes da execução
- Otimizar a utilização de recursos de saúde
- Integrar com sistemas de faturamento

### Benefícios
- Controle financeiro rigoroso
- Otimização de recursos
- Rastreabilidade completa de autorizações
- Relatórios gerenciais para tomada de decisão
- Conformidade com contratos SUS

---

## 🎯 Funcionalidades Necessárias

### 1. Cadastros Básicos

#### 1.1 Cadastro de Prestadores de Serviço
- **Descrição**: Cadastrar prestadores que executam procedimentos
- **Campos necessários**:
  - Razão social
  - CNPJ
  - CNES
  - Endereço completo
  - Contatos
  - Especialidades oferecidas
  - Status (ativo/inativo)

#### 1.2 Cadastro de Contratos
- **Descrição**: Contratos com prestadores definindo cotas e valores
- **Campos necessários**:
  - Prestador
  - Número do contrato
  - Data início/fim
  - Valor total contratado
  - Tipo de contrato (SUS, particular, etc.)
  - Status

#### 1.3 Cadastro de Cotas
- **Descrição**: Cotas de procedimentos por prestador/unidade/município
- **Tipos de cotas**:
  - Por prestador (grupo/subgrupo/procedimento)
  - Por unidade executora
  - Por município origem do paciente
- **Campos necessários**:
  - Contrato vinculado
  - Grupo/subgrupo/procedimento (SIGTAP)
  - Quantidade autorizada
  - Período (mês/ano)
  - Valor unitário

#### 1.4 Cadastro de Procedimentos SIGTAP
- **Descrição**: Integração com tabela SIGTAP do SUS
- **Funcionalidades**:
  - Importação da tabela SIGTAP
  - Atualização periódica
  - Busca por código/descrição
  - Sub-tipos de procedimentos (mesmo código, tipo diferente)

#### 1.5 Cadastro de Procedimentos Extra-SUS
- **Descrição**: Procedimentos fora da tabela SUS
- **Campos necessários**:
  - Código interno
  - Descrição
  - Valor (com vigência de datas)
  - Tipo de procedimento
  - Prestador autorizado

### 2. Configurações

#### 2.1 Configuração de Permissões por CBO
- **Descrição**: Restringir solicitação de procedimentos por CBO do solicitante
- **Funcionalidade**: Definir quais CBOs podem solicitar quais procedimentos

#### 2.2 Configuração de Procedimentos sem Autorização
- **Descrição**: Procedimentos que não necessitam autorização (desde que estejam nas cotas)
- **Regra**: Lista de grupos/subgrupos/procedimentos isentos

#### 2.3 Configuração de Exceções em Contratos
- **Descrição**: Procedimentos excluídos de contratos configurados por grupo/subgrupo
- **Uso**: Casos especiais que fogem da regra geral

#### 2.4 Critérios de Priorização
- **Descrição**: Regras para priorizar pacientes na fila
- **Critérios possíveis**:
  - Idade
  - Gravidade (CID)
  - Tempo de espera
  - Urgência médica
  - Ordem de chegada

### 3. Processo de Solicitação e Autorização

#### 3.1 Solicitação de Procedimentos Especiais
- **Descrição**: Formulário para solicitar procedimentos de média/alta complexidade
- **Informações necessárias**:
  - Paciente
  - Procedimento solicitado (SIGTAP ou extra-SUS)
  - CID principal
  - Justificativa médica
  - Médico solicitante
  - Unidade solicitante
  - Anexos (exames, laudos, etc.)
  - Urgência

#### 3.2 Gerenciamento de Fila de Solicitações
- **Descrição**: Fila de solicitações aguardando autorização
- **Funcionalidades**:
  - Visualização ordenada por prioridade
  - Filtros diversos
  - Seleção automática conforme prioridades
  - Atribuição de auditor

#### 3.3 Autorização de Execução
- **Descrição**: Gerar autorização para execução do procedimento
- **Informações**:
  - Número da autorização
  - Data de validade
  - Prestador autorizado
  - Valores aprovados
  - Condições especiais
  - QR Code para validação

#### 3.4 Registro de Execução
- **Descrição**: Registrar procedimento executado conforme autorização
- **Informações**:
  - Autorização vinculada
  - Data de execução
  - Prestador executante
  - Resultado do procedimento
  - Complicações (se houver)
  - Laudos anexados

### 4. Upload e Visualização de Laudos
- **Descrição**: Sistema de upload e visualização de laudos
- **Funcionalidades**:
  - Upload de arquivos (PDF, imagens)
  - Armazenamento seguro
  - Visualização no prontuário do paciente
  - Download controlado

### 5. Relatórios

#### 5.1 Relatório de Autorizações por Período
- **Descrição**: Lista de autorizações emitidas
- **Filtros**: Data, prestador, procedimento, status

#### 5.2 Relatório de Utilização de Cotas
- **Descrição**: Análise de uso das cotas contratadas
- **Informações**: Quantidade utilizada vs. autorizada, percentual

#### 5.3 Relatório de Valores Contratados por Prestador
- **Descrição**: Valores contratados e executados por prestador
- **Uso**: Controle financeiro

#### 5.4 Relatório de Fila de Espera
- **Descrição**: Pacientes aguardando autorização
- **Informações**: Tempo de espera, procedimento, prioridade

#### 5.5 Relatório de Procedimentos por CID
- **Descrição**: Estatísticas por CID
- **Uso**: Análise epidemiológica

---

## 📐 Arquitetura e Classes

### Entidades Principais

```java
// PrestadorServico.java
@Entity
@Table(name = "prestadores_servico")
public class PrestadorServico extends BaseEntity {
    private String razaoSocial;
    private String cnpj;
    private String cnes;
    private String tipoPrestador; // hospital, clinica, laboratorio, etc.
    @ManyToMany
    private List<EspecialidadesMedicas> especialidades;
    @OneToMany(mappedBy = "prestador")
    private List<ContratoPrestador> contratos;
    private Boolean ativo;
}

// ContratoPrestador.java
@Entity
@Table(name = "contratos_prestador")
public class ContratoPrestador extends BaseEntity {
    @ManyToOne
    private PrestadorServico prestador;
    
    private String numeroContrato;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private BigDecimal valorTotalContratado;
    private String tipoContrato; // SUS, PARTICULAR, CONVENIO
    private String status; // ATIVO, ENCERRADO, SUSPENSO
    
    @OneToMany(mappedBy = "contrato")
    private List<CotaContrato> cotas;
}

// CotaContrato.java
@Entity
@Table(name = "cotas_contrato")
public class CotaContrato extends BaseEntity {
    @ManyToOne
    private ContratoPrestador contrato;
    
    private String tipoCota; // PRESTADOR, UNIDADE_EXECUTORA, MUNICIPIO_ORIGEM
    
    // Para cotas por grupo/subgrupo/procedimento
    private String codigoGrupo;
    private String codigoSubgrupo;
    private String codigoProcedimento;
    
    // Para cotas por unidade
    @ManyToOne
    private Estabelecimentos unidadeExecutora;
    
    // Para cotas por município origem
    @ManyToOne
    private Cidades municipioOrigem;
    
    private Integer quantidadeAutorizada;
    private Integer quantidadeUtilizada;
    private Integer mes;
    private Integer ano;
    private BigDecimal valorUnitario;
    
    @OneToMany(mappedBy = "cota")
    private List<AutorizacaoProcedimento> autorizacoes;
}

// ProcedimentoSIGTAP.java
@Entity
@Table(name = "procedimentos_sigtap")
public class ProcedimentoSIGTAP extends BaseEntityWithoutTenant {
    private String codigo;
    private String descricao;
    private String grupo;
    private String subgrupo;
    private String formaOrganizacao;
    private BigDecimal valorSUS;
    private LocalDate dataVigencia;
    private Boolean ativo;
}

// ProcedimentoExtraSUS.java
@Entity
@Table(name = "procedimentos_extra_sus")
public class ProcedimentoExtraSUS extends BaseEntity {
    private String codigoInterno;
    private String descricao;
    private String tipoProcedimento;
    private BigDecimal valor;
    private LocalDate dataInicioVigencia;
    private LocalDate dataFimVigencia;
    
    @ManyToMany
    private List<PrestadorServico> prestadoresAutorizados;
}

// ConfiguracaoPermissaoCBO.java
@Entity
@Table(name = "configuracoes_permissao_cbo")
public class ConfiguracaoPermissaoCBO extends BaseEntity {
    private String codigoCBO;
    private String descricaoCBO;
    
    // Se null, aplica a todos os procedimentos
    private String codigoGrupo;
    private String codigoSubgrupo;
    private String codigoProcedimento;
    
    private Boolean permitido;
    private String observacoes;
}

// ConfiguracaoProcedimentoSemAutorizacao.java
@Entity
@Table(name = "configuracoes_procedimento_sem_autorizacao")
public class ConfiguracaoProcedimentoSemAutorizacao extends BaseEntity {
    private String codigoGrupo;
    private String codigoSubgrupo;
    private String codigoProcedimento;
    private String justificativa;
    private Boolean ativo;
}

// CriterioPriorizacao.java
@Entity
@Table(name = "criterios_priorizacao")
public class CriterioPriorizacao extends BaseEntity {
    private String nome;
    private String descricao;
    private Integer ordem; // ordem de aplicação
    private String tipo; // IDADE, GRAVIDADE_CID, TEMPO_ESPERA, URGENCIA, ORDEM_CHEGADA
    private String configuracao; // JSON com parâmetros específicos
    private Boolean ativo;
}

// SolicitacaoProcedimento.java
@Entity
@Table(name = "solicitacoes_procedimento")
public class SolicitacaoProcedimento extends BaseEntity {
    @ManyToOne
    private Paciente paciente;
    
    @ManyToOne
    private Medicos medicoSolicitante;
    
    @ManyToOne
    private Estabelecimentos unidadeSolicitante;
    
    // Procedimento SIGTAP ou Extra-SUS
    @ManyToOne
    private ProcedimentoSIGTAP procedimentoSIGTAP;
    
    @ManyToOne
    private ProcedimentoExtraSUS procedimentoExtraSUS;
    
    @ManyToOne
    private CidDoencas cidPrincipal;
    
    private String justificativaMedica;
    private String urgencia; // ROTINA, URGENTE, EMERGENCIAL
    
    @Enumerated(EnumType.STRING)
    private StatusSolicitacao status;
    
    private Integer pontuacaoPrioridade; // calculada pelos critérios
    
    @ManyToOne
    private Medicos medicoAuditor;
    private LocalDateTime dataAuditoria;
    private String parecerAuditor;
    
    @OneToOne(mappedBy = "solicitacao")
    private AutorizacaoProcedimento autorizacao;
    
    @OneToMany(mappedBy = "solicitacao")
    private List<AnexoSolicitacao> anexos;
}

// AnexoSolicitacao.java
@Entity
@Table(name = "anexos_solicitacao")
public class AnexoSolicitacao extends BaseEntity {
    @ManyToOne
    private SolicitacaoProcedimento solicitacao;
    
    private String nomeArquivo;
    private String caminhoArquivo;
    private String tipoArquivo; // PDF, JPG, PNG, etc.
    private Long tamanhoBytes;
    private String descricao;
}

// AutorizacaoProcedimento.java
@Entity
@Table(name = "autorizacoes_procedimento")
public class AutorizacaoProcedimento extends BaseEntity {
    @OneToOne
    private SolicitacaoProcedimento solicitacao;
    
    private String numeroAutorizacao; // único, gerado automaticamente
    
    @ManyToOne
    private PrestadorServico prestadorAutorizado;
    
    @ManyToOne
    private CotaContrato cota;
    
    private LocalDate dataValidade;
    private BigDecimal valorAprovado;
    private String condicoesEspeciais;
    private String qrCode; // para validação
    
    @Enumerated(EnumType.STRING)
    private StatusAutorizacao status;
    
    @OneToOne(mappedBy = "autorizacao")
    private ExecucaoProcedimento execucao;
}

// ExecucaoProcedimento.java
@Entity
@Table(name = "execucoes_procedimento")
public class ExecucaoProcedimento extends BaseEntity {
    @OneToOne
    private AutorizacaoProcedimento autorizacao;
    
    private LocalDate dataExecucao;
    
    @ManyToOne
    private PrestadorServico prestadorExecutante;
    
    private String resultado; // SUCESSO, COMPLICACAO, NAO_REALIZADO
    private String complicacoes;
    private String observacoes;
    
    @OneToMany(mappedBy = "execucao")
    private List<LaudoExecucao> laudos;
    
    private Boolean faturado;
    private LocalDateTime dataFaturamento;
}

// LaudoExecucao.java
@Entity
@Table(name = "laudos_execucao")
public class LaudoExecucao extends BaseEntity {
    @ManyToOne
    private ExecucaoProcedimento execucao;
    
    private String nomeArquivo;
    private String caminhoArquivo;
    private String tipoArquivo;
    private Long tamanhoBytes;
    private String descricao;
    private LocalDateTime dataUpload;
    private UUID uploadadoPor;
}
```

### Enums Necessários

```java
public enum StatusSolicitacao {
    PENDENTE,
    EM_ANALISE,
    AUTORIZADA,
    REJEITADA,
    CANCELADA
}

public enum StatusAutorizacao {
    EMITIDA,
    UTILIZADA,
    EXPIRADA,
    CANCELADA
}
```

### Services Necessários

```java
public interface PrestadorServicoService {
    PrestadorServicoResponse criar(PrestadorServicoRequest request);
    PrestadorServicoResponse obterPorId(UUID id);
    Page<PrestadorServicoResponse> listar(Pageable pageable);
}

public interface ContratoPrestadorService {
    ContratoPrestadorResponse criar(ContratoPrestadorRequest request);
    void replicarCotas(UUID contratoId, Integer mesOrigem, Integer anoOrigem, Integer quantidadeMeses);
    Page<CotaContratoResponse> listarCotas(UUID contratoId, Pageable pageable);
}

public interface SolicitacaoProcedimentoService {
    SolicitacaoProcedimentoResponse criar(SolicitacaoProcedimentoRequest request);
    SolicitacaoProcedimentoResponse obterPorId(UUID id);
    Page<SolicitacaoProcedimentoResponse> listarFila(FiltroFilaSolicitacao filtro, Pageable pageable);
    void calcularPrioridade(UUID solicitacaoId);
    SolicitacaoProcedimentoResponse autorizar(UUID id, AutorizacaoRequest request);
    SolicitacaoProcedimentoResponse rejeitar(UUID id, String justificativa);
    void uploadAnexo(UUID solicitacaoId, MultipartFile arquivo, String descricao);
}

public interface AutorizacaoProcedimentoService {
    AutorizacaoProcedimentoResponse gerarAutorizacao(UUID solicitacaoId, UUID prestadorId);
    AutorizacaoProcedimentoResponse obterPorNumero(String numeroAutorizacao);
    void validarAutorizacao(String numeroAutorizacao, String qrCode);
}

public interface ExecucaoProcedimentoService {
    ExecucaoProcedimentoResponse registrar(UUID autorizacaoId, ExecucaoRequest request);
    void uploadLaudo(UUID execucaoId, MultipartFile arquivo, String descricao);
    void integrarFaturamentoBPA(UUID execucaoId);
}
```

---

## 🔄 Fluxo de Processo

### Fluxo de Solicitação e Autorização

```
1. Médico solicita procedimento especial
   ↓
2. Sistema valida permissões (CBO, cotas)
   ↓
3. Sistema calcula prioridade
   ↓
4. Solicitação entra na fila ordenada
   ↓
5. Médico auditor analisa
   ↓
6. Se autorizado:
   - Gera autorização
   - Vincula a cota
   - Define prestador
   ↓
7. Prestador executa procedimento
   ↓
8. Registra execução
   ↓
9. Upload de laudos
   ↓
10. Integração com faturamento BPA
```

---

## 🔐 Regras de Negócio

### RB-001: Validação de Permissão por CBO
- Médico só pode solicitar procedimentos permitidos para seu CBO
- Exceções podem ser configuradas caso a caso

### RB-002: Validação de Cotas
- Antes de autorizar, verificar se há cota disponível
- Considerar cotas por prestador, unidade ou município origem
- Se sem cota, solicitação pode ser rejeitada ou aguardar próxima cota

### RB-003: Procedimentos sem Autorização
- Procedimentos configurados como isentos não precisam de autorização
- Mas devem estar dentro das cotas contratadas

### RB-004: Cálculo de Prioridade
- Sistema aplica critérios de priorização em ordem
- Gera pontuação final
- Fila é ordenada por pontuação (maior primeiro)

### RB-005: Validade da Autorização
- Autorização tem validade de 90 dias (configurável)
- Após expirar, nova solicitação deve ser feita

### RB-006: Registro de Execução
- Execução só pode ser registrada com autorização válida
- QR Code deve ser validado no momento da execução
- Prestador executante deve ser o autorizado (ou ter permissão)

---

## 📱 APIs REST Necessárias

### Endpoints de Prestadores
- `POST /api/v1/regulacao/prestadores` - Criar
- `GET /api/v1/regulacao/prestadores` - Listar
- `GET /api/v1/regulacao/prestadores/{id}` - Obter

### Endpoints de Contratos
- `POST /api/v1/regulacao/contratos` - Criar
- `POST /api/v1/regulacao/contratos/{id}/replicar-cotas` - Replicar cotas
- `GET /api/v1/regulacao/contratos/{id}/cotas` - Listar cotas

### Endpoints de Solicitações
- `POST /api/v1/regulacao/solicitacoes` - Criar
- `GET /api/v1/regulacao/solicitacoes/fila` - Listar fila
- `POST /api/v1/regulacao/solicitacoes/{id}/autorizar` - Autorizar
- `POST /api/v1/regulacao/solicitacoes/{id}/rejeitar` - Rejeitar
- `POST /api/v1/regulacao/solicitacoes/{id}/anexos` - Upload anexo

### Endpoints de Autorizações
- `GET /api/v1/regulacao/autorizacoes/{numero}` - Obter por número
- `POST /api/v1/regulacao/autorizacoes/{numero}/validar` - Validar

### Endpoints de Execuções
- `POST /api/v1/regulacao/execucoes` - Registrar execução
- `POST /api/v1/regulacao/execucoes/{id}/laudos` - Upload laudo

---

## 🔧 Tecnologias e Dependências

### Dependências Necessárias
- **Upload de Arquivos**: Spring Boot Multipart
- **Geração de QR Code**: ZXing ou similar
- **Integração SIGTAP**: Web Service ou importação de arquivo
- **Geração de Relatórios**: JasperReports

### Integrações Necessárias
- Sistema SIGTAP (importação de tabela)
- Sistema de Faturamento BPA
- Sistema de Notificações

---

## 📈 Métricas e Indicadores

### KPIs Sugeridos
- Tempo médio de análise de solicitações
- Taxa de aprovação/rejeição
- Utilização de cotas (%)
- Tempo médio de espera na fila
- Custo médio por procedimento
- Número de autorizações por mês

---

## 🚀 Fases de Implementação

### Fase 1: Cadastros Básicos (2 semanas)
- Prestadores de serviço
- Contratos
- Cotas
- Procedimentos SIGTAP e Extra-SUS

### Fase 2: Configurações (1 semana)
- Permissões por CBO
- Procedimentos sem autorização
- Critérios de priorização

### Fase 3: Solicitação e Fila (2 semanas)
- Formulário de solicitação
- Sistema de fila
- Cálculo de prioridade

### Fase 4: Autorização (2 semanas)
- Geração de autorização
- Validação (QR Code)
- Controle de cotas

### Fase 5: Execução (1 semana)
- Registro de execução
- Upload de laudos

### Fase 6: Relatórios (2 semanas)
- Todos os relatórios

### Fase 7: Integração SIGTAP (1 semana)
- Importação de tabela
- Atualização periódica

**Total estimado: 11 semanas**

