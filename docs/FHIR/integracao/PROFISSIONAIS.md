# 👨‍⚕️ Integração FHIR - Módulo de Profissionais

## ✅ Status: IMPLEMENTADO (Modelo Canônico Único)

## 1. Arquitetura

### Princípio: Enriquecimento Progressivo

O sistema utiliza a **tabela existente `sigtap_ocupacao`** enriquecida com campos FHIR:

❌ **NÃO criamos tabelas separadas** como `cbo_fhir`, `ocupacoes_fhir`, etc.

✅ **`sigtap_ocupacao`** recebe campos FHIR adicionais mantendo retrocompatibilidade.

---

## 2. Entidade Enriquecida: SigtapOcupacao

```java
@Entity
@Table(name = "sigtap_ocupacao")
public class SigtapOcupacao {
    // Campos SIGTAP originais (mantidos)
    private String codigoOficial;  // Código SIGTAP (6 dígitos)
    private String nome;
    
    // Campos FHIR adicionados (enriquecimento)
    private String codigoCboCompleto;    // Código CBO completo (ex: 2251-01)
    private String grandeGrupo;
    private String subgrupoPrincipal;
    private String subgrupo;
    private String familia;
    private String descricaoFhir;
}
```

---

## 3. Nova Entidade: ConselhoProfissional

Esta é uma entidade **nova e legítima** pois não existia tabela de conselhos no sistema:

```java
@Entity
@Table(name = "conselhos_profissionais")
public class ConselhoProfissional {
    private String codigo;      // Código único
    private String sigla;       // CRM, COREN, CRO, etc.
    private String nome;
    private String uf;
    private String tipo;
}
```

---

## 4. Vinculação com Profissionais

```java
@Entity
@Table(name = "profissionais_saude")
public class ProfissionaisSaude {
    // SIGTAP (mantido - enriquecido com FHIR)
    @ManyToOne
    private SigtapOcupacao sigtapOcupacao;
    
    // Conselho (novo - FHIR)
    @ManyToOne
    private ConselhoProfissional conselhoProfissional;
}
```

---

## 5. Sincronização FHIR

### ProfissionalSyncService

```java
public FhirSyncLog sincronizarCBO() {
    // Busca na mesma tabela SIGTAP
    SigtapOcupacao ocupacao = sigtapOcupacaoRepository
        .findByCodigoCbo(code)
        .orElse(new SigtapOcupacao());
    
    // Enriquece com dados FHIR
    ocupacao.setCodigoCboCompleto(code);
    ocupacao.setGrandeGrupo(grandeGrupo);
    ocupacao.setDescricaoFhir(descricao);
    // ...
}
```

### Endpoints
```http
POST /api/fhir/sync/profissionais/cbo
POST /api/fhir/sync/profissionais/conselhos
POST /api/fhir/sync/profissionais/todos
```

---

## 6. Migração de Banco

### V20260113140000__add_professional_catalogs_fhir.sql

```sql
-- Enriquece tabela existente (NÃO cria nova)
ALTER TABLE sigtap_ocupacao
ADD COLUMN grande_grupo VARCHAR(100),
ADD COLUMN subgrupo_principal VARCHAR(100),
ADD COLUMN subgrupo VARCHAR(100),
ADD COLUMN familia VARCHAR(100),
ADD COLUMN descricao_fhir TEXT,
ADD COLUMN codigo_cbo_completo VARCHAR(10);

-- Nova tabela apenas para conselhos (não existia)
CREATE TABLE conselhos_profissionais (
    id UUID PRIMARY KEY,
    codigo VARCHAR(20) UNIQUE NOT NULL,
    sigla VARCHAR(20) NOT NULL,
    nome VARCHAR(200) NOT NULL,
    uf VARCHAR(2),
    tipo VARCHAR(50)
);
```

---

## 7. Convivência SIGTAP + FHIR

### Regras de Isolamento
- `codigoOficial` = Código SIGTAP original (6 dígitos)
- `codigoCboCompleto` = Código CBO FHIR completo (ex: 2251-01)
- Ambos coexistem na mesma entidade

### Estratégia de Busca
```java
// Busca SIGTAP
sigtapOcupacaoRepository.findByCodigo("225101");

// Busca FHIR
sigtapOcupacaoRepository.findByCodigoCbo("2251-01");

// Ambos retornam o mesmo registro
```

---

## 8. Próximos Passos

- [ ] Implementar BRResponsabilidadeParticipante
- [ ] Implementar BRTipoParticipante
- [ ] Criar validação de registro profissional
- [ ] Integrar com assinatura digital
