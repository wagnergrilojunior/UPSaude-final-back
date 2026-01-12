# ⚠️ Integração FHIR - Módulo de Alergias

## 1. Visão Geral

O módulo de alergias e reações adversas integra com os recursos FHIR para padronização de:

- Catálogo de alérgenos (CBARA)
- Reações adversas (MedDRA)
- Criticidade das reações
- Categorias de agentes causadores

---

## 2. Recursos FHIR Utilizados

| Recurso | URL | Descrição |
|---------|-----|-----------|
| **BRAlergenosCBARA** | `/CodeSystem/BRAlergenosCBARA` | Catálogo de alérgenos |
| **BRMedDRA** | `/CodeSystem/BRMedDRA` | Reações adversas |
| **BRAlergenos** | `/ValueSet/BRAlergenos` | Alérgenos (inclui medicamentos) |
| **BRReacoesAdversasMedDRA** | `/ValueSet/BRReacoesAdversasMedDRA` | Reações catalogadas |
| **BRCriticidadeAlergiasReacoesAdversas** | `/ValueSet/BRCriticidadeAlergiasReacoesAdversas` | Criticidade |
| **BRCategoriaAgenteAlergiasReacoesAdversas** | `/ValueSet/BRCategoriaAgenteAlergiasReacoesAdversas` | Categoria |
| **BRGrauCertezaAlergiasReacoesAdversas** | `/ValueSet/BRGrauCertezaAlergiasReacoesAdversas` | Grau de certeza |

---

## 3. Exemplos de Reações Adversas (MedDRA)

| Código | Nome |
|--------|------|
| 10002198 | Anafilaxia |
| 10002424 | Angioedema |
| 10046735 | Urticária |
| 10037087 | Prurido |
| 10006482 | Broncoespasmo |
| 10013968 | Dispnéia |
| 10023845 | Edema de Glote |
| 10042033 | Síndrome de Stevens-Johnson |
| 10044223 | Síndrome de Lyell |
| 10015218 | Eritema Multiforme |
| 10047700 | Vômito |
| 10012735 | Diarréia |

---

## 4. Categorias de Agentes

| Código | Descrição |
|--------|-----------|
| medication | Medicamento |
| biologic | Biológico (vacinas, etc.) |
| environment | Ambiente |
| food | Alimento |
| OTH | Outro |

---

## 5. Criticidade

| Código | Descrição |
|--------|-----------|
| low | Baixo risco |
| high | Alto risco |
| unable-to-assess | Não avaliável |

---

## 6. Grau de Certeza

| Código | Descrição |
|--------|-----------|
| confirmed | Confirmado |
| unconfirmed | Não confirmado |
| refuted | Refutado |

---

## 7. Endpoints do Sistema UPSaude

```http
# Sincronização
POST /api/fhir/sincronizar/alergenos
POST /api/fhir/sincronizar/reacoes-adversas

# Consulta
GET /api/fhir/consultar/alergenos?termo={termo}
GET /api/fhir/consultar/reacoes-adversas?termo={termo}
```

---

## 8. Modelagem de Dados

```sql
-- Catálogo de Alérgenos
CREATE TABLE alergenos (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    codigo_fhir VARCHAR(50) NOT NULL UNIQUE,
    nome VARCHAR(255) NOT NULL,
    categoria VARCHAR(50), -- medication, food, environment, biologic
    fonte VARCHAR(50), -- CBARA, medicamento, imunobiologico
    ativo BOOLEAN DEFAULT TRUE,
    data_sincronizacao TIMESTAMP,
    criado_em TIMESTAMP DEFAULT NOW()
);

-- Catálogo de Reações Adversas
CREATE TABLE reacoes_adversas_catalogo (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    codigo_fhir VARCHAR(50) NOT NULL UNIQUE,
    nome VARCHAR(255) NOT NULL,
    categoria VARCHAR(100),
    gravidade VARCHAR(50),
    ativo BOOLEAN DEFAULT TRUE,
    data_sincronizacao TIMESTAMP,
    criado_em TIMESTAMP DEFAULT NOW()
);

-- Alergias do Paciente
CREATE TABLE alergias_paciente (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    paciente_id UUID NOT NULL REFERENCES pacientes(id),
    alergeno_id UUID REFERENCES alergenos(id),
    alergeno_descricao VARCHAR(255), -- Caso não esteja no catálogo
    categoria VARCHAR(50) NOT NULL,
    criticidade VARCHAR(50),
    grau_certeza VARCHAR(50),
    data_identificacao DATE,
    data_ultima_ocorrencia DATE,
    observacoes TEXT,
    ativo BOOLEAN DEFAULT TRUE,
    tenant_id UUID NOT NULL,
    criado_em TIMESTAMP DEFAULT NOW()
);

-- Reações do Paciente
CREATE TABLE reacoes_paciente (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    alergia_id UUID REFERENCES alergias_paciente(id),
    reacao_id UUID REFERENCES reacoes_adversas_catalogo(id),
    descricao TEXT,
    data_ocorrencia TIMESTAMP,
    gravidade VARCHAR(50),
    tratamento_realizado TEXT,
    ativo BOOLEAN DEFAULT TRUE,
    criado_em TIMESTAMP DEFAULT NOW()
);
```

---

## 9. Casos de Uso

- **Triagem**: identificar alergias conhecidas
- **Prescrição segura**: alertar interações com alergias
- **Vacinação**: verificar contraindicações
- **Administração de medicamentos**: confirmar antes de aplicar
- **Prontuário**: histórico completo de reações

---

## 10. Alertas Importantes

- Sempre exibir **alergias ativas** em vermelho no prontuário
- **Anafilaxia** deve ter destaque especial
- Cruzar com medicamentos na prescrição
- Cruzar com vacinas na imunização
