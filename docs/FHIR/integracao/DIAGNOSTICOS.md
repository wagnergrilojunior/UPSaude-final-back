# 🩺 Integração FHIR - Módulo de Diagnósticos

## 1. Visão Geral

O módulo de diagnósticos integra com os recursos FHIR para padronização de:

- **CID-10** - Classificação Internacional de Doenças (10ª Revisão)
- **CIAP-2** - Classificação Internacional de Atenção Primária
- Categorias de diagnóstico

---

## 2. Recursos FHIR Utilizados

| Recurso | URL | Descrição |
|---------|-----|-----------|
| **BRCID10** | `/CodeSystem/BRCID10` | CID-10 completo (~14.000 códigos) |
| **BRCIAP2** | `/CodeSystem/BRCIAP2` | CIAP-2 completo (~700 códigos) |
| **BRCategoriaDiagnostico** | `/CodeSystem/BRCategoriaDiagnostico` | Categorias |

---

## 3. Endpoints do Sistema UPSaude

### 3.1 Sincronização

```http
POST /api/fhir/sincronizar/cid10
POST /api/fhir/sincronizar/ciap2
POST /api/fhir/sincronizar/categorias-diagnostico
```

### 3.2 Consulta

```http
GET /api/fhir/consultar/cid10/{codigo}
GET /api/fhir/consultar/cid10?termo={termo}
GET /api/fhir/consultar/ciap2/{codigo}
GET /api/fhir/consultar/ciap2?termo={termo}
```

---

## 4. Modelagem de Dados

```sql
-- CID-10
CREATE TABLE cid10 (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    codigo VARCHAR(10) NOT NULL UNIQUE,
    descricao VARCHAR(500) NOT NULL,
    capitulo VARCHAR(5),
    capitulo_descricao VARCHAR(255),
    ativo BOOLEAN DEFAULT TRUE,
    data_sincronizacao TIMESTAMP,
    criado_em TIMESTAMP DEFAULT NOW()
);

-- CIAP-2
CREATE TABLE ciap2 (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    codigo VARCHAR(10) NOT NULL UNIQUE,
    descricao VARCHAR(500) NOT NULL,
    capitulo CHAR(1) NOT NULL,
    ativo BOOLEAN DEFAULT TRUE,
    data_sincronizacao TIMESTAMP,
    criado_em TIMESTAMP DEFAULT NOW()
);

-- Diagnósticos do Paciente
CREATE TABLE diagnosticos_paciente (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    paciente_id UUID NOT NULL REFERENCES pacientes(id),
    tipo_classificacao VARCHAR(10) NOT NULL, -- CID10, CIAP2
    codigo VARCHAR(20) NOT NULL,
    descricao VARCHAR(500),
    categoria VARCHAR(50), -- principal, secundario
    cronico BOOLEAN DEFAULT FALSE,
    status VARCHAR(50) DEFAULT 'ativo',
    data_diagnostico TIMESTAMP NOT NULL,
    tenant_id UUID NOT NULL,
    criado_em TIMESTAMP DEFAULT NOW()
);
```

---

## 5. Observações

- **CID-10** é usado em todo o Brasil para diagnósticos hospitalares
- **CIAP-2** é usado na Atenção Primária (UBS, ESF)
- Implementar busca full-text para autocomplete
- Volume alto de dados: sincronizar em batch
