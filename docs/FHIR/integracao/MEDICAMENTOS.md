# 💊 Integração FHIR - Módulo de Medicamentos

## 1. Visão Geral

O módulo de medicamentos integra com os recursos FHIR para padronização de:

- Catálogo de medicamentos
- Princípios ativos
- Registros ANVISA
- Unidades de medida
- Vias de administração

---

## 2. Recursos FHIR Utilizados

| Recurso | URL | Descrição |
|---------|-----|-----------|
| **BRMedicamento** | `/CodeSystem/BRMedicamento` | Catálogo de medicamentos |
| **BRObmVMP** | `/CodeSystem/BRObmVMP` | Produtos Medicinais Virtuais |
| **BRObmVTM** | `/CodeSystem/BRObmVtm` | Princípios Ativos Virtuais |
| **BRObmANVISA** | `/CodeSystem/BRObmANVISA` | Registros ANVISA |
| **BRObmCATMAT** | `/CodeSystem/BRObmCATMAT` | Catálogo de Materiais |
| **BRObmEAN** | `/CodeSystem/BRObmEAN` | Códigos EAN/Barcode |
| **BRUnidadeMedida** | `/CodeSystem/BRUnidadeMedida` | Unidades de medida |
| **BRViaAdministracao** | `/CodeSystem/BRViaAdministracao` | Vias de administração |

---

## 3. Ontologia Brasileira de Medicamentos (OBM)

A OBM organiza medicamentos em níveis:

| Nível | Sigla | Descrição | Exemplo |
|-------|-------|-----------|---------|
| 1 | VTM | Princípio Ativo Virtual | Paracetamol |
| 2 | VMP | Produto Medicinal Virtual | Paracetamol 500mg comprimido |
| 3 | AMP | Produto Medicinal Comercial | Tylenol 500mg |
| 4 | AMPP | Produto com Apresentação | Tylenol 500mg caixa 20 comp |

---

## 4. Endpoints do Sistema UPSaude

### 4.1 Sincronização

```http
POST /api/fhir/sincronizar/medicamentos
POST /api/fhir/sincronizar/principios-ativos
POST /api/fhir/sincronizar/unidades-medida
POST /api/fhir/sincronizar/vias-administracao
```

### 4.2 Consulta

```http
GET /api/fhir/consultar/medicamentos?termo={termo}
GET /api/fhir/consultar/medicamentos/{codigo}
GET /api/fhir/consultar/principios-ativos?termo={termo}
GET /api/fhir/consultar/unidades-medida
GET /api/fhir/consultar/vias-administracao
```

---

## 5. Modelagem de Dados

```sql
-- Princípios Ativos
CREATE TABLE principios_ativos (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    codigo_fhir VARCHAR(50) NOT NULL UNIQUE,
    nome VARCHAR(255) NOT NULL,
    dcb VARCHAR(100), -- Denominação Comum Brasileira
    dci VARCHAR(100), -- Denominação Comum Internacional
    ativo BOOLEAN DEFAULT TRUE,
    data_sincronizacao TIMESTAMP,
    criado_em TIMESTAMP DEFAULT NOW()
);

-- Medicamentos
CREATE TABLE medicamentos (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    codigo_fhir VARCHAR(50) NOT NULL UNIQUE,
    nome VARCHAR(500) NOT NULL,
    apresentacao VARCHAR(255),
    concentracao VARCHAR(100),
    forma_farmaceutica VARCHAR(100),
    principio_ativo_id UUID REFERENCES principios_ativos(id),
    registro_anvisa VARCHAR(50),
    codigo_ean VARCHAR(50),
    fabricante VARCHAR(255),
    ativo BOOLEAN DEFAULT TRUE,
    data_sincronizacao TIMESTAMP,
    criado_em TIMESTAMP DEFAULT NOW()
);

-- Unidades de Medida
CREATE TABLE unidades_medida (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    codigo_fhir VARCHAR(20) NOT NULL UNIQUE,
    nome VARCHAR(100) NOT NULL,
    sigla VARCHAR(20),
    ativo BOOLEAN DEFAULT TRUE,
    data_sincronizacao TIMESTAMP,
    criado_em TIMESTAMP DEFAULT NOW()
);

-- Vias de Administração
CREATE TABLE vias_administracao (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    codigo_fhir VARCHAR(20) NOT NULL UNIQUE,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    ativo BOOLEAN DEFAULT TRUE,
    data_sincronizacao TIMESTAMP,
    criado_em TIMESTAMP DEFAULT NOW()
);
```

---

## 6. Casos de Uso

- **Prescrição eletrônica**: busca de medicamentos padronizados
- **Dispensação de farmácia**: controle de estoque por código
- **Interação medicamentosa**: verificar por princípio ativo
- **Alertas de alergia**: cruzar com alergias do paciente

---

## 7. Observações

- Volume alto de dados: medicamentos têm milhares de itens
- Considerar busca por princípio ativo (mais estável)
- Integrar com módulo de farmácia existente
- Validar código EAN para leitura de código de barras
