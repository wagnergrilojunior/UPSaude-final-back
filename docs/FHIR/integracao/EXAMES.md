# 🔬 Integração FHIR - Módulo de Exames

## ✅ Status: IMPLEMENTADO (Modelo Canônico Único)

## 1. Arquitetura

### Princípio: Modelo Canônico Único

O sistema utiliza uma **única tabela `catalogo_exames`** que suporta múltiplas origens:
- **SIGTAP** - Tabela SUS (faturamento)
- **LOINC** - Padrão internacional
- **GAL** - Gerenciador de Ambiente Laboratorial
- **TUSS** - Terminologia Unificada da Saúde Suplementar

❌ **NÃO criamos tabelas separadas** como `exames_loinc`, `exames_gal`, etc.

✅ **Enriquecimento progressivo** - Cada integração adiciona dados à mesma tabela.

---

## 2. Entidade Unificada: CatalogoExame

```java
@Entity
@Table(name = "catalogo_exames")
public class CatalogoExame {
    // Controle de origem
    private String sourceSystem;     // SIGTAP, LOINC, GAL, TUSS
    private String externalCode;     // Código na origem
    private String externalVersion;  // Versão do CodeSystem
    private OffsetDateTime lastSyncAt;
    
    // Códigos multi-fonte (mesmo exame pode ter vários códigos)
    private String codigoLoinc;
    private String codigoGal;
    private String codigoSigtap;
    private String codigoTuss;
    
    // Dados do exame
    private String nome;
    private String descricao;
    private String categoria;
    private String tipoAmostra;
    private String material;
    private String metodo;
}
```

---

## 3. Convivência de Integrações

### Regras de Isolamento
- Atualização FHIR **não sobrescreve** dados SIGTAP
- Atualização SIGTAP **não apaga** dados FHIR
- Cada registro identifica sua origem via `source_system`

### Estratégia de Merge
```sql
-- Cada fonte tem sua própria entrada
UNIQUE(source_system, external_code)

-- Mesmo exame pode ter códigos em múltiplos sistemas
codigo_loinc: "2345-7"
codigo_sigtap: "0202010503"
codigo_gal: "GAL-001"
```

---

## 4. Sincronização FHIR

### ExameSyncService

```java
public FhirSyncLog sincronizarExamesLoinc() {
    // Busca no catálogo por SOURCE + CODE
    CatalogoExame exame = repository.findBySourceAndCode("LOINC", code)
        .orElse(new CatalogoExame());
    
    exame.setSourceSystem("LOINC");
    exame.setCodigoLoinc(code);
    exame.setLastSyncAt(now());
    // ...
}
```

### Endpoints
```http
POST /api/fhir/sync/exames/loinc
POST /api/fhir/sync/exames/gal
POST /api/fhir/sync/exames/todos
```

---

## 5. Migração de Banco

### V20260113130000__add_exame_catalogs_loinc_gal.sql

```sql
CREATE TABLE catalogo_exames (
    id UUID PRIMARY KEY,
    
    -- Controle de origem
    source_system VARCHAR(20) NOT NULL,
    external_code VARCHAR(50) NOT NULL,
    
    -- Multi-fonte
    codigo_loinc VARCHAR(20),
    codigo_gal VARCHAR(20),
    codigo_sigtap VARCHAR(20),
    
    UNIQUE(source_system, external_code)
);
```

---

## 6. Vinculação com Exames do Paciente

A entidade `ExamePaciente` referencia o catálogo:

```java
@Entity
public class ExamePaciente {
    // SIGTAP (mantido para retrocompatibilidade)
    @ManyToOne
    private SigtapProcedimento procedimento;
    
    // Catálogo unificado (FHIR)
    @ManyToOne
    private CatalogoExame catalogoExame;
}
```

---

## 7. Casos de Uso

### Solicitação de Exame
```java
ExamePacienteRequest request = new ExamePacienteRequest();
request.setCatalogoExameId(catalogoId); // Catálogo unificado
request.setProcedimento(sigtapId);       // SIGTAP (faturamento)
```

### Busca por Código
```java
// Busca independente da origem
catalogoRepository.findByCodigoLoinc("2345-7");
catalogoRepository.findByCodigoSigtap("0202010503");
catalogoRepository.findBySourceAndCode("GAL", "GAL-001");
```

---

## 8. Próximos Passos

- [ ] Implementar mapeamento LOINC <-> SIGTAP
- [ ] Adicionar suporte a TUSS
- [ ] Criar endpoint de busca unificada
