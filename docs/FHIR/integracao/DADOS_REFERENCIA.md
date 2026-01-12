# 🌍 Integração FHIR - Dados de Referência

## ✅ Status: IMPLEMENTADO (Modelo Canônico Único)

## 1. Visão Geral

Este módulo implementa a integração FHIR para:
- **Dados Demográficos**: Raça/Cor (BRRacaCor), Sexo (AdministrativeGender)
- **Geografia**: Estados e Municípios (BRDivisaoGeografica)

### Estratégia: Enriquecimento, Nunca Duplicação

| Categoria | Implementação | Tabela/Classe |
|-----------|---------------|---------------|
| Raça/Cor | Enum enriquecido | `RacaCorEnum.java` |
| Sexo | Enum enriquecido | `SexoEnum.java` |
| Estados | Tabela existente enriquecida | `estados` |
| Municípios | Tabela existente enriquecida | `cidades` |

---

## 2. Dados Demográficos (Enums)

### RacaCorEnum
```java
public enum RacaCorEnum {
    BRANCA(1, "Branca", "01", "BRRacaCor"),
    PRETA(2, "Preta", "02", "BRRacaCor"),
    PARDA(3, "Parda", "03", "BRRacaCor"),
    AMARELA(4, "Amarela", "04", "BRRacaCor"),
    INDIGENA(5, "Indígena", "05", "BRRacaCor"),
    // ...
}
```

**Métodos de conversão:**
- `fromCodigo(Integer)` - Busca por código interno
- `fromCodigoFhir(String)` - Busca por código FHIR
- `fromDescricao(String)` - Busca por descrição

### SexoEnum
```java
public enum SexoEnum {
    MASCULINO(1, "Masculino", "male", "AdministrativeGender"),
    FEMININO(2, "Feminino", "female", "AdministrativeGender"),
    OUTRO(3, "Outro", "other", "AdministrativeGender"),
    DESCONHECIDO(4, "Desconhecido", "unknown", "AdministrativeGender");
}
```

---

## 3. Geografia (Tabelas Enriquecidas)

### Estados
```sql
-- Campos IBGE (existentes)
sigla, nome, codigo_ibge, nome_oficial_ibge, regiao_ibge

-- Campos FHIR (novos)
codigo_fhir, fhir_code_system, data_ultima_sincronizacao_fhir
```

### Cidades
```sql
-- Campos IBGE (existentes)
nome, codigo_ibge, latitude, longitude, populacao_estimada

-- Campos FHIR (novos)
codigo_fhir, fhir_code_system, regiao_saude, macrorregiao_saude, data_ultima_sincronizacao_fhir
```

---

## 4. Endpoints API

### Base: `/fhir/dados-referencia`

#### 4.1 Sincronização
```http
POST /geografia/sincronizar/estados
POST /geografia/sincronizar/municipios
POST /geografia/sincronizar/todos
```

#### 4.2 Consulta Direta FHIR (Live)
```http
GET /geografia/externo/divisoes
```

#### 4.3 Consulta Local (Sincronizado)
```http
GET /geografia/estados
GET /geografia/estados/{sigla}
GET /geografia/municipios?uf=SP&limit=100
GET /geografia/municipios/{codigoIbge}
GET /geografia/municipios/buscar?nome=São Paulo
```

#### 4.4 Dados Demográficos
```http
GET /demografico/raca-cor
GET /demografico/raca-cor/{codigo}
GET /demografico/raca-cor/fhir/{codigoFhir}
GET /demografico/sexo
GET /demografico/sexo/{codigo}
GET /demografico/sexo/fhir/{codigoFhir}
```

#### 4.5 Status
```http
GET /status
```

---

## 5. Migração

### V20260113150000__enrich_geographic_demographic_fhir.sql

Enriquece tabelas existentes:
```sql
ALTER TABLE estados ADD COLUMN codigo_fhir VARCHAR(20);
ALTER TABLE cidades ADD COLUMN regiao_saude VARCHAR(100);
-- ...
```

**NÃO cria novas tabelas para dados demográficos** - Usamos Enums Java.

---

## 6. Convivência IBGE + FHIR

### Estratégia de Sincronização
1. Dados IBGE são a fonte primária
2. FHIR enriquece com `codigo_fhir`, `regiao_saude`, etc.
3. Sincronização FHIR não sobrescreve dados IBGE

### Campos de Controle
```java
// Em Estados e Cidades
private String codigoFhir;
private String fhirCodeSystem;
private OffsetDateTime dataUltimaSincronizacaoFhir;
```

---

## 7. Exemplos de Uso

### Converter Código FHIR para Interno
```java
// Raça/Cor
RacaCorEnum raca = RacaCorEnum.fromCodigoFhir("01"); // BRANCA

// Sexo
SexoEnum sexo = SexoEnum.fromCodigoFhir("male"); // MASCULINO
```

### Buscar Município com Dados FHIR
```java
Cidades cidade = cidadesRepository.findByCodigoIbge("3550308");
String regiaoSaude = cidade.getRegiaoSaude(); // Dados FHIR
```

---

## 8. Próximos Passos

- [ ] Sincronizar regiões de saúde do FHIR
- [ ] Adicionar macrorregiões de saúde
- [ ] Implementar BREtnia para povos indígenas
