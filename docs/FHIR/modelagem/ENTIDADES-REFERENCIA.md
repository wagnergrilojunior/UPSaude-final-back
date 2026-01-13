# 🗄️ Entidades de Referência FHIR

## 1. Visão Geral

Este documento lista as entidades de referência que serão sincronizadas do servidor FHIR.

---

## 2. Estrutura Base para Entidades FHIR

```java
@MappedSuperclass
@Getter @Setter
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseFhirReferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "codigo_fhir", nullable = false, unique = true, length = 50)
    private String codigoFhir;

    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "sistema_url", length = 500)
    private String sistemaUrl;

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

## 3. Entidades por Módulo

### 3.1 CID-10

```java
@Entity
@Table(name = "cid10")
public class Cid10 {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "codigo", nullable = false, unique = true, length = 10)
    private String codigo;

    @Column(name = "codigo_sem_ponto", length = 10)
    private String codigoSemPonto;

    @Column(name = "descricao", nullable = false, length = 500)
    private String descricao;

    @Column(name = "capitulo", length = 5)
    private String capitulo;

    @Column(name = "capitulo_descricao", length = 255)
    private String capituloDescricao;

    @Column(name = "grupo", length = 10)
    private String grupo;

    @Column(name = "grupo_descricao", length = 255)
    private String grupoDescricao;

    @Column(name = "ativo")
    private Boolean ativo = true;

    @Column(name = "data_sincronizacao")
    private OffsetDateTime dataSincronizacao;
}
```

### 3.2 CIAP-2

```java
@Entity
@Table(name = "ciap2")
public class Ciap2 {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "codigo", nullable = false, unique = true, length = 10)
    private String codigo;

    @Column(name = "descricao", nullable = false, length = 500)
    private String descricao;

    @Column(name = "capitulo", length = 1)
    private String capitulo;

    @Column(name = "capitulo_descricao", length = 255)
    private String capituloDescricao;

    @Column(name = "ativo")
    private Boolean ativo = true;

    @Column(name = "data_sincronizacao")
    private OffsetDateTime dataSincronizacao;
}
```

### 3.3 CBO

```java
@Entity
@Table(name = "cbo")
public class Cbo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "codigo", nullable = false, unique = true, length = 10)
    private String codigo;

    @Column(name = "descricao", nullable = false, length = 255)
    private String descricao;

    @Column(name = "familia", length = 10)
    private String familia;

    @Column(name = "familia_descricao", length = 255)
    private String familiaDescricao;

    @Column(name = "ativo")
    private Boolean ativo = true;

    @Column(name = "data_sincronizacao")
    private OffsetDateTime dataSincronizacao;
}
```

### 3.4 Alérgenos

```java
@Entity
@Table(name = "alergenos")
public class Alergeno {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "codigo_fhir", nullable = false, unique = true, length = 50)
    private String codigoFhir;

    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @Column(name = "categoria", length = 50)
    private String categoria; // medication, food, environment, biologic

    @Column(name = "fonte", length = 50)
    private String fonte; // CBARA, medicamento, imunobiologico

    @Column(name = "ativo")
    private Boolean ativo = true;

    @Column(name = "data_sincronizacao")
    private OffsetDateTime dataSincronizacao;
}
```

### 3.5 Reações Adversas (MedDRA)

```java
@Entity
@Table(name = "reacoes_adversas_catalogo")
public class ReacaoAdversaCatalogo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "codigo_fhir", nullable = false, unique = true, length = 50)
    private String codigoFhir;

    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @Column(name = "categoria", length = 100)
    private String categoria;

    @Column(name = "gravidade", length = 50)
    private String gravidade;

    @Column(name = "ativo")
    private Boolean ativo = true;

    @Column(name = "data_sincronizacao")
    private OffsetDateTime dataSincronizacao;
}
```

---

## 4. Tabela de Log de Sincronização

```java
@Entity
@Table(name = "fhir_sync_log")
public class FhirSyncLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "recurso", nullable = false, length = 100)
    private String recurso; // BRImunobiologico, BRCID10, etc.

    @Column(name = "data_inicio", nullable = false)
    private OffsetDateTime dataInicio;

    @Column(name = "data_fim")
    private OffsetDateTime dataFim;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private SyncLogStatus status;

    @Column(name = "total_encontrados")
    private Integer totalEncontrados;

    @Column(name = "novos_inseridos")
    private Integer novosInseridos;

    @Column(name = "atualizados")
    private Integer atualizados;

    @Column(name = "erros")
    private Integer erros;

    @Column(name = "mensagem_erro", columnDefinition = "TEXT")
    private String mensagemErro;

    @Column(name = "usuario_id")
    private UUID usuarioId;
}

public enum SyncLogStatus {
    EM_ANDAMENTO,
    CONCLUIDO,
    ERRO
}
```

---

## 5. Repositories Genéricos

```java
public interface FhirReferenciaRepository<T, ID> extends JpaRepository<T, ID> {

    Optional<T> findByCodigoFhir(String codigoFhir);
    
    List<T> findByAtivoTrue();
    
    @Query("SELECT e FROM #{#entityName} e WHERE " +
           "LOWER(e.nome) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
           "LOWER(e.codigoFhir) LIKE LOWER(CONCAT('%', :termo, '%'))")
    List<T> buscarPorTermo(@Param("termo") String termo);
}
```

---

## 6. Migration Base

```sql
-- Template para criar tabela de referência FHIR
CREATE TABLE template_fhir_referencia (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    codigo_fhir VARCHAR(50) NOT NULL UNIQUE,
    nome VARCHAR(255) NOT NULL,
    descricao TEXT,
    sistema_url VARCHAR(500),
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    data_sincronizacao TIMESTAMP,
    criado_em TIMESTAMP NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMP
);

-- Índices padrão
CREATE INDEX idx_template_codigo ON template_fhir_referencia(codigo_fhir);
CREATE INDEX idx_template_nome ON template_fhir_referencia USING gin(to_tsvector('portuguese', nome));
CREATE INDEX idx_template_ativo ON template_fhir_referencia(ativo);
```

---

## 7. Observações

- Tabelas de referência FHIR **NÃO** precisam de tenant_id
- São compartilhadas entre todos os tenants
- Sincronização pode ser agendada (job)
- Manter histórico de sincronizações no log
