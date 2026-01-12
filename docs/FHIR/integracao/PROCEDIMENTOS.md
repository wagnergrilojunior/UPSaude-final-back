# 🏥 Integração FHIR - Módulo de Procedimentos

## 1. Visão Geral

O módulo de procedimentos integra com os recursos FHIR para padronização de:

- **Tabela SUS** - Procedimentos, medicamentos e OPM do SUS
- **CBHPM/TUSS** - Procedimentos para convênios e saúde suplementar
- Desfechos de procedimentos
- Motivos de não realização

---

## 2. Recursos FHIR Utilizados

| Recurso | URL | Descrição |
|---------|-----|-----------|
| **BRTabelaSUS** | `/CodeSystem/BRTabelaSUS` | Tabela SUS completa |
| **BRSubgrupoTabelaSUS** | `/CodeSystem/BRSubgrupoTabelaSUS` | Subgrupos da tabela |
| **BRCBHPMTUSS** | `/CodeSystem/BRCBHPMTUSS` | CBHPM e TUSS |
| **BRDesfechoProcedimento** | `/CodeSystem/BRDesfechoProcedimento` | Desfechos |
| **BRMotivoProcedimentoNaoRealizado** | `/CodeSystem/BRMotivoProcedimentoNaoRealizado` | Motivos não realização |

---

## 3. Diferenças entre Tabelas

| Aspecto | Tabela SUS | CBHPM/TUSS |
|---------|-----------|------------|
| **Uso** | Faturamento SUS | Faturamento Convênios |
| **Gestão** | DATASUS/MS | AMB/ANS |
| **Atualização** | SIGTAP mensal | CBHPM periódico |
| **Código** | 10 dígitos | 8 dígitos |

---

## 4. Endpoints do Sistema UPSaude

### 4.1 Sincronização

```http
POST /api/fhir/sincronizar/tabela-sus
POST /api/fhir/sincronizar/cbhpm-tuss
POST /api/fhir/sincronizar/desfecho-procedimento
```

### 4.2 Consulta

```http
GET /api/fhir/consultar/tabela-sus/{codigo}
GET /api/fhir/consultar/tabela-sus?termo={termo}
GET /api/fhir/consultar/cbhpm-tuss/{codigo}
GET /api/fhir/consultar/cbhpm-tuss?termo={termo}
```

---

## 5. Modelagem de Dados

```sql
-- Procedimentos Tabela SUS
CREATE TABLE procedimentos_sus (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    codigo VARCHAR(20) NOT NULL UNIQUE,
    descricao VARCHAR(500) NOT NULL,
    grupo VARCHAR(10),
    grupo_descricao VARCHAR(255),
    subgrupo VARCHAR(10),
    subgrupo_descricao VARCHAR(255),
    forma_organizacao VARCHAR(10),
    valor_sa DECIMAL(12,2),
    valor_sp DECIMAL(12,2),
    ativo BOOLEAN DEFAULT TRUE,
    data_sincronizacao TIMESTAMP,
    criado_em TIMESTAMP DEFAULT NOW()
);

-- Procedimentos CBHPM/TUSS
CREATE TABLE procedimentos_tuss (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    codigo VARCHAR(20) NOT NULL UNIQUE,
    descricao VARCHAR(500) NOT NULL,
    grupo VARCHAR(100),
    subgrupo VARCHAR(100),
    porte VARCHAR(10),
    ativo BOOLEAN DEFAULT TRUE,
    data_sincronizacao TIMESTAMP,
    criado_em TIMESTAMP DEFAULT NOW()
);

-- Procedimentos realizados
CREATE TABLE procedimentos_realizados (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    paciente_id UUID NOT NULL REFERENCES pacientes(id),
    atendimento_id UUID,
    tipo_tabela VARCHAR(10) NOT NULL, -- SUS, TUSS
    codigo_procedimento VARCHAR(20) NOT NULL,
    descricao VARCHAR(500),
    quantidade INTEGER DEFAULT 1,
    data_realizacao TIMESTAMP NOT NULL,
    profissional_id UUID,
    desfecho VARCHAR(50),
    observacoes TEXT,
    tenant_id UUID NOT NULL,
    criado_em TIMESTAMP DEFAULT NOW()
);
```

---

## 6. Observações

- O projeto já possui integração SIGTAP em `/docs/SIGTAP/`
- Verificar possibilidade de unificar com integração FHIR
- CBHPM/TUSS é essencial para convênios
- Manter histórico de valores para faturamento retroativo
