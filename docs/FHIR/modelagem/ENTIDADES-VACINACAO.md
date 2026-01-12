# 🗄️ Entidades de Vacinação - Modelagem Completa

## 1. Visão Geral

Este documento detalha todas as entidades JPA necessárias para o módulo de vacinação.

---

## 2. Entidades de Referência (FHIR)

### 2.1 Imunobiologico

```java
@Entity
@Table(name = "imunobiologicos")
@Getter @Setter
public class Imunobiologico {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "codigo_fhir", nullable = false, unique = true, length = 20)
    private String codigoFhir;

    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @Column(name = "nome_abreviado", length = 50)
    private String nomeAbreviado;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "codigo_sistema", length = 500)
    private String codigoSistema;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;

    @Column(name = "data_sincronizacao")
    private OffsetDateTime dataSincronizacao;

    @CreatedDate
    @Column(name = "criado_em", nullable = false, updatable = false)
    private OffsetDateTime criadoEm;

    @LastModifiedDate
    @Column(name = "atualizado_em")
    private OffsetDateTime atualizadoEm;
}
```

### 2.2 FabricanteImunobiologico

```java
@Entity
@Table(name = "fabricantes_imunobiologicos")
@Getter @Setter
public class FabricanteImunobiologico {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "codigo_fhir", nullable = false, unique = true, length = 20)
    private String codigoFhir;

    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @Column(name = "nome_fantasia", length = 255)
    private String nomeFantasia;

    @Column(name = "pais_origem", length = 100)
    private String paisOrigem;

    @Column(name = "cnpj", length = 20)
    private String cnpj;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;

    @Column(name = "data_sincronizacao")
    private OffsetDateTime dataSincronizacao;

    @CreatedDate
    @Column(name = "criado_em", nullable = false, updatable = false)
    private OffsetDateTime criadoEm;

    @LastModifiedDate
    @Column(name = "atualizado_em")
    private OffsetDateTime atualizadoEm;
}
```

### 2.3 TipoDose

```java
@Entity
@Table(name = "tipos_dose")
@Getter @Setter
public class TipoDose {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "codigo_fhir", nullable = false, unique = true, length = 20)
    private String codigoFhir;

    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "ordem_sequencia")
    private Integer ordemSequencia;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;

    @Column(name = "data_sincronizacao")
    private OffsetDateTime dataSincronizacao;

    @CreatedDate
    @Column(name = "criado_em", nullable = false, updatable = false)
    private OffsetDateTime criadoEm;

    @LastModifiedDate
    @Column(name = "atualizado_em")
    private OffsetDateTime atualizadoEm;
}
```

### 2.4 LocalAplicacao

```java
@Entity
@Table(name = "locais_aplicacao")
@Getter @Setter
public class LocalAplicacao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "codigo_fhir", nullable = false, unique = true, length = 20)
    private String codigoFhir;

    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;

    @Column(name = "data_sincronizacao")
    private OffsetDateTime dataSincronizacao;

    @CreatedDate
    @Column(name = "criado_em", nullable = false, updatable = false)
    private OffsetDateTime criadoEm;

    @LastModifiedDate
    @Column(name = "atualizado_em")
    private OffsetDateTime atualizadoEm;
}
```

### 2.5 ViaAdministracao (já pode existir, reaproveitar)

### 2.6 EstrategiaVacinacao

```java
@Entity
@Table(name = "estrategias_vacinacao")
@Getter @Setter
public class EstrategiaVacinacao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "codigo_fhir", nullable = false, unique = true, length = 20)
    private String codigoFhir;

    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;

    @Column(name = "data_sincronizacao")
    private OffsetDateTime dataSincronizacao;

    @CreatedDate
    @Column(name = "criado_em", nullable = false, updatable = false)
    private OffsetDateTime criadoEm;

    @LastModifiedDate
    @Column(name = "atualizado_em")
    private OffsetDateTime atualizadoEm;
}
```

---

## 3. Entidades de Negócio

### 3.1 LoteVacina

```java
@Entity
@Table(name = "lotes_vacina", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"numero_lote", "imunobiologico_id", "fabricante_id", "tenant_id"})
})
@Getter @Setter
public class LoteVacina extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "imunobiologico_id", nullable = false)
    private Imunobiologico imunobiologico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fabricante_id", nullable = false)
    private FabricanteImunobiologico fabricante;

    @Column(name = "numero_lote", nullable = false, length = 100)
    private String numeroLote;

    @Column(name = "data_fabricacao")
    private LocalDate dataFabricacao;

    @Column(name = "data_validade", nullable = false)
    private LocalDate dataValidade;

    @Column(name = "quantidade_recebida")
    private Integer quantidadeRecebida;

    @Column(name = "quantidade_disponivel")
    private Integer quantidadeDisponivel;

    @Column(name = "preco_unitario", precision = 12, scale = 2)
    private BigDecimal precoUnitario;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
}
```

### 3.2 AplicacaoVacina

```java
@Entity
@Table(name = "aplicacoes_vacina")
@Getter @Setter
public class AplicacaoVacina extends BaseEntity {

    // === Identificadores FHIR ===
    @Column(name = "fhir_identifier", length = 255)
    private String fhirIdentifier;

    @Enumerated(EnumType.STRING)
    @Column(name = "fhir_status", nullable = false, length = 50)
    private StatusAplicacao fhirStatus = StatusAplicacao.COMPLETED;

    @Column(name = "fhir_status_reason_codigo", length = 50)
    private String fhirStatusReasonCodigo;

    @Column(name = "fhir_status_reason_descricao", length = 255)
    private String fhirStatusReasonDescricao;

    // === Paciente ===
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    // === Vacina ===
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "imunobiologico_id", nullable = false)
    private Imunobiologico imunobiologico;

    // === Lote e Fabricante ===
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lote_id")
    private LoteVacina lote;

    @Column(name = "numero_lote", length = 100)
    private String numeroLote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fabricante_id")
    private FabricanteImunobiologico fabricante;

    @Column(name = "data_validade")
    private LocalDate dataValidade;

    // === Dose ===
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_dose_id")
    private TipoDose tipoDose;

    @Column(name = "numero_dose")
    private Integer numeroDose;

    @Column(name = "dose_quantidade", precision = 10, scale = 2)
    private BigDecimal doseQuantidade;

    @Column(name = "dose_unidade", length = 50)
    private String doseUnidade;

    // === Local e Via ===
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "local_aplicacao_id")
    private LocalAplicacao localAplicacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "via_administracao_id")
    private ViaAdministracao viaAdministracao;

    // === Estratégia ===
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estrategia_id")
    private EstrategiaVacinacao estrategia;

    // === Data e Hora ===
    @Column(name = "data_aplicacao", nullable = false)
    private OffsetDateTime dataAplicacao;

    @Column(name = "data_registro", nullable = false)
    private OffsetDateTime dataRegistro = OffsetDateTime.now();

    // === Profissional ===
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_id")
    private ProfissionaisSaude profissional;

    @Column(name = "profissional_funcao", length = 100)
    private String profissionalFuncao;

    // === Fonte e Origem ===
    @Column(name = "fonte_primaria")
    private Boolean fontePrimaria = true;

    @Column(name = "origem_registro", length = 100)
    private String origemRegistro;

    // === Subpotência ===
    @Column(name = "dose_subpotente")
    private Boolean doseSubpotente = false;

    @Column(name = "motivo_subpotencia", columnDefinition = "TEXT")
    private String motivoSubpotencia;

    // === Programa ===
    @Column(name = "elegibilidade_programa", length = 50)
    private String elegibilidadePrograma;

    @Column(name = "fonte_financiamento", length = 100)
    private String fonteFinanciamento;

    // === Observações ===
    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    // === Sincronização FHIR ===
    @Column(name = "fhir_resource_id", length = 255)
    private String fhirResourceId;

    @Enumerated(EnumType.STRING)
    @Column(name = "fhir_sync_status", length = 50)
    private SyncStatus fhirSyncStatus;

    @Column(name = "fhir_last_sync")
    private OffsetDateTime fhirLastSync;

    // === Auditoria ===
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "criado_por")
    private UsuariosSistema criadoPor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atualizado_por")
    private UsuariosSistema atualizadoPor;

    // === Relacionamentos ===
    @OneToMany(mappedBy = "aplicacao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AplicacaoVacinaReacao> reacoes = new ArrayList<>();

    @OneToMany(mappedBy = "aplicacao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProtocoloVacinacao> protocolos = new ArrayList<>();
}
```

### 3.3 AplicacaoVacinaReacao

```java
@Entity
@Table(name = "aplicacoes_vacina_reacoes")
@Getter @Setter
public class AplicacaoVacinaReacao extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aplicacao_id", nullable = false)
    private AplicacaoVacina aplicacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reacao_catalogo_id")
    private ReacaoAdversaCatalogo reacaoCatalogo;

    @Column(name = "data_ocorrencia", nullable = false)
    private OffsetDateTime dataOcorrencia;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "criticidade", length = 50)
    private String criticidade; // low, high, unable-to-assess

    @Column(name = "grau_certeza", length = 50)
    private String grauCerteza; // confirmed, unconfirmed, refuted

    @Column(name = "categoria_agente", length = 50)
    private String categoriaAgente;

    @Column(name = "requer_tratamento")
    private Boolean requerTratamento = false;

    @Column(name = "tratamento_realizado", columnDefinition = "TEXT")
    private String tratamentoRealizado;

    @Column(name = "evolucao", columnDefinition = "TEXT")
    private String evolucao;

    @Column(name = "resolvida")
    private Boolean resolvida = false;

    @Column(name = "data_resolucao")
    private OffsetDateTime dataResolucao;

    @Column(name = "notificada_anvisa")
    private Boolean notificadaAnvisa = false;

    @Column(name = "data_notificacao")
    private OffsetDateTime dataNotificacao;

    @Column(name = "numero_notificacao", length = 100)
    private String numeroNotificacao;

    @Column(name = "reportado_por", length = 100)
    private String reportadoPor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_registro_id")
    private ProfissionaisSaude profissionalRegistro;
}
```

---

## 4. Enums

```java
public enum StatusAplicacao {
    COMPLETED("completed"),
    ENTERED_IN_ERROR("entered-in-error"),
    NOT_DONE("not-done");

    private final String fhirCode;
    // constructor, getter
}

public enum SyncStatus {
    PENDING,
    SYNCED,
    ERROR
}
```

---

## 5. Repositories

```java
public interface ImunobiologicoRepository extends JpaRepository<Imunobiologico, UUID> {
    Optional<Imunobiologico> findByCodigoFhir(String codigoFhir);
    List<Imunobiologico> findByAtivoTrue();
    List<Imunobiologico> findByNomeContainingIgnoreCase(String nome);
}

public interface AplicacaoVacinaRepository extends EmpresaAwareRepository<AplicacaoVacina, UUID> {
    List<AplicacaoVacina> findByPacienteIdOrderByDataAplicacaoDesc(UUID pacienteId);
    List<AplicacaoVacina> findByPacienteIdAndImunobiologicoId(UUID pacienteId, UUID imunobiologicoId);
}

public interface LoteVacinaRepository extends EmpresaAwareRepository<LoteVacina, UUID> {
    Optional<LoteVacina> findByNumeroLoteAndImunobiologicoIdAndFabricanteId(
        String numeroLote, UUID imunobiologicoId, UUID fabricanteId
    );
    List<LoteVacina> findByDataValidadeBeforeAndQuantidadeDisponivelGreaterThan(
        LocalDate data, Integer quantidade
    );
}
```
