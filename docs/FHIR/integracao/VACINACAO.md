# 💉 Integração FHIR - Módulo de Vacinação

## 1. Visão Geral

O módulo de vacinação integra com os recursos FHIR do PNI (Programa Nacional de Imunização) para:

- Catálogo padronizado de vacinas
- Fabricantes autorizados
- Tipos de doses
- Locais de aplicação
- Vias de administração
- Estratégias de vacinação
- Registro de reações adversas

---

## 2. Recursos FHIR Utilizados

### 2.1 CodeSystems Principais

| Recurso | URL | Descrição | Qtd. Aprox. |
|---------|-----|-----------|-------------|
| BRImunobiologico | `/CodeSystem/BRImunobiologico` | Vacinas | ~100 |
| BRFabricantePNI | `/CodeSystem/BRFabricantePNI` | Fabricantes | ~100 |
| BRDose | `/CodeSystem/BRDose` | Tipos de dose | ~80 |
| BRLocalAplicacao | `/CodeSystem/BRLocalAplicacao` | Local anatômico | ~22 |
| BRViaAdministracao | `/CodeSystem/BRViaAdministracao` | Via de admin. | ~70 |
| BREstrategiaVacinacao | `/CodeSystem/BREstrategiaVacinacao` | Estratégias | ~13 |
| BRElegibilidadeImunobiologico | `/CodeSystem/BRElegibilidadeImunobiologico` | CNI | 2 |
| BRResponsabilidadeParticipante | `/CodeSystem/BRResponsabilidadeParticipante` | Quem aplicou | 5 |

### 2.2 ValueSets para Validação

Todos os recursos acima possuem ValueSets correspondentes para validação.

---

## 3. Exemplos de Códigos

### 3.1 Vacinas (BRImunobiologico)

| Código | Nome da Vacina |
|--------|----------------|
| 107 | VPC20 |
| 108 | VVSR-Rec |
| 111 | dTpa/VIP |
| 110 | Vacina Influenza Tetravalente - Alta Dosagem |
| 109 | VVSR-RecAdj |
| 106 | VPC15 |
| 105 | COVID-19 MODERNA - SPIKEVAX BIVALENTE |
| 104 | DNG |
| 103 | COVID-19 PFIZER - COMIRNATY BIVALENTE |
| 102 | COVID-19 PFIZER - COMIRNATY PEDIÁTRICA MENOR DE 5 ANOS |
| 101 | VZR |
| 100 | VVBN |
| 99 | COVID-19 PFIZER - COMIRNATY PEDIÁTRICA |
| 98 | COVID-19 SINOVAC - CORONAVAC |
| 97 | COVID-19 MODERNA |
| 96 | COVID-19 BHARAT - COVAXIN |
| 95 | COVID-19 GAMALEYA - SPUTNIK V |
| 94 | DILCOV |
| 93 | HPV |
| 92 | VRvero |
| 91 | DILVR |
| 90 | DILSCRV |
| 89 | COVID-19 ASTRAZENECA - ChAdOx1-S |
| 88 | COVID-19 JANSSEN - Ad26.COV2.S |
| 87 | COVID-19 PFIZER - COMIRNATY |
| 86 | COVID-19 SINOVAC/BUTANTAN - CORONAVAC |
| 85 | COVID-19 ASTRAZENECA/FIOCRUZ - COVISHIELD |
| 42 | PENTA |
| 15 | BCG |
| 14 | VFA (Febre Amarela) |
| 5 | DT |
| 25 | dT |
| 24 | SCR (Tríplice Viral) |
| 23 | IGHR |
| 22 | VIP |
| 21 | VPP23 |
| 20 | IGHHB |
| 19 | IGHV |
| 18 | VR (Rotavírus) |
| 17 | Hib |
| 16 | SACROT |

### 3.2 Fabricantes (BRFabricantePNI)

| Código | Fabricante |
|--------|------------|
| 152 | BUTANTAN |
| 149 | FIOCRUZ |
| 142 | PFIZER |
| 29909 | ASTRAZENECA |
| 42430 | MODERNA |
| 42489 | CATALENT |
| 42490 | SINOVAC |
| 30587 | JANSSEN |
| 35977 | GAMALEYA |
| 35978 | MODERNA |
| 35979 | BHARAT |
| 163 | SERUM-INDIA |
| 162 | CHIRON SPA |
| 161 | AVENTIS |
| 160 | GREENCROSS |
| 156 | DADO B. |
| 153 | TECPAR |
| 150 | FUNED |
| 26313 | MERCK-GO |
| 2862 | SANPASTEUR |
| 27880 | GSK-BRASIL |

### 3.3 Tipos de Dose (BRDose)

| Código | Descrição |
|--------|-----------|
| 1 | 1ª Dose |
| 2 | 2ª Dose |
| 3 | 3ª Dose |
| 4 | 4ª Dose |
| 5 | 5ª Dose |
| 6 | 1º Reforço |
| 7 | 2º Reforço |
| 39 | 3º Reforço |
| 40 | 4º Reforço |
| 41 | 5º Reforço |
| 42 | 6º Reforço |
| 9 | Única |
| 8 | Dose |
| 10 | Revacinação |
| 36 | Dose Inicial |
| 37 | Dose Adicional |
| 38 | Reforço |
| 5 | Dose Zero |
| 11 | Tratamento com uma dose |
| 12 | Tratamento com duas doses |
| ... | ... (até 31 doses de tratamento) |
| 59-82 | Profilaxia com X frascos-ampolas |

### 3.4 Locais de Aplicação (BRLocalAplicacao)

| Código | Descrição |
|--------|-----------|
| 0 | Sem registro no sistema de informação de origem |
| 1 | Deltóide Direito |
| 2 | Deltóide Esquerdo |
| 3 | Vasto Lateral da Coxa Direita |
| 4 | Vasto Lateral da Coxa Esquerda |
| 5 | Ventroglúteo Direito |
| 6 | Ventroglúteo Esquerdo |
| 7 | Glúteo (descontinuado) |
| 8 | Ferimento Local |
| 9 | Boca |
| 10 | Dorso Glúteo Direito |
| 11 | Dorso Glúteo Esquerdo |
| 12 | Face Externa Inferior do Braço Direito |
| 13 | Face Externa Inferior do Braço Esquerdo |
| 14 | Face Externa Superior do Braço Direito |
| 15 | Face Externa Superior do Braço Esquerdo |
| 16 | Rede Venosa |
| 17 | Face Anterolateral Externa da Coxa Direita |
| 18 | Face Anterolateral Externa da Coxa Esquerda |
| 19 | Face Anterolateral Externa do Braço Direito |
| 20 | Face Anterolateral Externa do Braço Esquerdo |
| 21 | Face Anterolateral Externa do Antebraço Direito |
| 22 | Face Anterolateral Externa do Antebraço Esquerdo |
| 99 | Outro |

### 3.5 Estratégias de Vacinação (BREstrategiaVacinacao)

| Código | Descrição |
|--------|-----------|
| 1 | Rotina |
| 2 | Especial |
| 3 | Bloqueio |
| 4 | Intensificação |
| 5 | Campanha indiscriminada |
| 6 | Campanha seletiva |
| 7 | Soroterapia |
| 8 | Serviço Privado |
| 9 | Monitoramento rápido de cobertura vacinal |
| 10 | Pesquisa |
| 11 | Pré-exposição |
| 12 | Pós-exposição |
| 13 | Reexposição |

### 3.6 Elegibilidade (BRElegibilidadeImunobiologico)

| Código | Descrição |
|--------|-----------|
| CNI-S | Pertence ao CNI |
| CNI-N | Não pertence ao CNI |

---

## 4. Recurso FHIR Immunization (Padrão HL7)

O recurso base **Immunization** do FHIR R4 define a estrutura para registros de vacinação:

```json
{
  "resourceType": "Immunization",
  "id": "example",
  "status": "completed",
  "vaccineCode": {
    "coding": [{
      "system": "https://terminologia.saude.gov.br/fhir/CodeSystem/BRImunobiologico",
      "code": "87",
      "display": "COVID-19 PFIZER - COMIRNATY"
    }]
  },
  "patient": {
    "reference": "Patient/123"
  },
  "occurrenceDateTime": "2024-01-15T10:30:00-03:00",
  "manufacturer": {
    "reference": "Organization/pfizer"
  },
  "lotNumber": "FG2345",
  "expirationDate": "2024-06-30",
  "site": {
    "coding": [{
      "system": "https://terminologia.saude.gov.br/fhir/CodeSystem/BRLocalAplicacao",
      "code": "1",
      "display": "Deltóide Direito"
    }]
  },
  "route": {
    "coding": [{
      "system": "https://terminologia.saude.gov.br/fhir/CodeSystem/BRViaAdministracao",
      "code": "10890",
      "display": "Intramuscular"
    }]
  },
  "performer": [{
    "function": {
      "coding": [{
        "system": "https://terminologia.saude.gov.br/fhir/CodeSystem/BRResponsabilidadeParticipante",
        "code": "atendimento",
        "display": "Profissional responsável pelo atendimento clínico"
      }]
    },
    "actor": {
      "reference": "Practitioner/456"
    }
  }],
  "protocolApplied": [{
    "doseNumberPositiveInt": 1,
    "seriesDosesPositiveInt": 2
  }],
  "reaction": [{
    "date": "2024-01-15T14:00:00-03:00",
    "detail": {
      "reference": "Observation/reaction-123"
    },
    "reported": true
  }]
}
```

---

## 5. Endpoints do Sistema UPSaude

### 5.1 Endpoints de Sincronização (Gravam no Banco)

```http
# Sincronizar catálogo de vacinas
POST /api/fhir/sincronizar/imunobiologicos

# Sincronizar fabricantes
POST /api/fhir/sincronizar/fabricantes

# Sincronizar tipos de dose
POST /api/fhir/sincronizar/doses

# Sincronizar locais de aplicação
POST /api/fhir/sincronizar/locais-aplicacao

# Sincronizar vias de administração
POST /api/fhir/sincronizar/vias-administracao

# Sincronizar estratégias de vacinação
POST /api/fhir/sincronizar/estrategias

# Sincronizar todos os recursos de vacinação
POST /api/fhir/sincronizar/vacinacao/todos
```

### 5.2 Endpoints de Consulta (Apenas Consulta FHIR)

```http
# Listar vacinas (consulta FHIR direto)
GET /api/fhir/consultar/imunobiologicos

# Buscar vacina por código
GET /api/fhir/consultar/imunobiologicos/{codigo}

# Listar fabricantes
GET /api/fhir/consultar/fabricantes

# Buscar fabricante por código
GET /api/fhir/consultar/fabricantes/{codigo}

# Listar tipos de dose
GET /api/fhir/consultar/doses

# Listar locais de aplicação
GET /api/fhir/consultar/locais-aplicacao

# Listar vias de administração
GET /api/fhir/consultar/vias-administracao

# Listar estratégias
GET /api/fhir/consultar/estrategias
```

### 5.3 Endpoints de Gestão de Vacinação

```http
# CRUD de Aplicações de Vacina
GET    /api/vacinacao/aplicacoes
GET    /api/vacinacao/aplicacoes/{id}
POST   /api/vacinacao/aplicacoes
PUT    /api/vacinacao/aplicacoes/{id}
DELETE /api/vacinacao/aplicacoes/{id}

# Carteira de Vacinação
GET /api/vacinacao/paciente/{pacienteId}/carteira
GET /api/vacinacao/paciente/{pacienteId}/historico

# Reações Adversas
GET  /api/vacinacao/aplicacoes/{id}/reacoes
POST /api/vacinacao/aplicacoes/{id}/reacoes

# Lotes de Vacina
GET    /api/vacinacao/lotes
GET    /api/vacinacao/lotes/{id}
POST   /api/vacinacao/lotes
PUT    /api/vacinacao/lotes/{id}
DELETE /api/vacinacao/lotes/{id}

# Estoque
GET /api/vacinacao/estoque
GET /api/vacinacao/estoque/estabelecimento/{estabelecimentoId}
```

---

## 6. Modelagem de Dados

### 6.1 Tabelas de Referência FHIR

```sql
-- Vacinas
CREATE TABLE imunobiologicos (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    codigo_fhir VARCHAR(20) NOT NULL UNIQUE,
    nome VARCHAR(255) NOT NULL,
    nome_abreviado VARCHAR(50),
    descricao TEXT,
    codigo_sistema VARCHAR(500),
    ativo BOOLEAN DEFAULT TRUE,
    data_sincronizacao TIMESTAMP,
    tenant_id UUID NOT NULL REFERENCES tenants(id),
    criado_em TIMESTAMP NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMP
);

-- Fabricantes
CREATE TABLE fabricantes_imunobiologicos (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    codigo_fhir VARCHAR(20) NOT NULL UNIQUE,
    nome VARCHAR(255) NOT NULL,
    nome_fantasia VARCHAR(255),
    pais_origem VARCHAR(100),
    cnpj VARCHAR(20),
    ativo BOOLEAN DEFAULT TRUE,
    data_sincronizacao TIMESTAMP,
    tenant_id UUID NOT NULL REFERENCES tenants(id),
    criado_em TIMESTAMP NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMP
);

-- Tipos de Dose
CREATE TABLE tipos_dose (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    codigo_fhir VARCHAR(20) NOT NULL UNIQUE,
    nome VARCHAR(255) NOT NULL,
    descricao TEXT,
    ordem_sequencia INTEGER,
    ativo BOOLEAN DEFAULT TRUE,
    data_sincronizacao TIMESTAMP,
    criado_em TIMESTAMP NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMP
);

-- Locais de Aplicação
CREATE TABLE locais_aplicacao (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    codigo_fhir VARCHAR(20) NOT NULL UNIQUE,
    nome VARCHAR(255) NOT NULL,
    descricao TEXT,
    ativo BOOLEAN DEFAULT TRUE,
    data_sincronizacao TIMESTAMP,
    criado_em TIMESTAMP NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMP
);

-- Vias de Administração
CREATE TABLE vias_administracao (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    codigo_fhir VARCHAR(20) NOT NULL UNIQUE,
    nome VARCHAR(255) NOT NULL,
    descricao TEXT,
    ativo BOOLEAN DEFAULT TRUE,
    data_sincronizacao TIMESTAMP,
    criado_em TIMESTAMP NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMP
);

-- Estratégias de Vacinação
CREATE TABLE estrategias_vacinacao (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    codigo_fhir VARCHAR(20) NOT NULL UNIQUE,
    nome VARCHAR(255) NOT NULL,
    descricao TEXT,
    ativo BOOLEAN DEFAULT TRUE,
    data_sincronizacao TIMESTAMP,
    criado_em TIMESTAMP NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMP
);

-- Catálogo de Reações Adversas
CREATE TABLE reacoes_adversas_catalogo (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    codigo_fhir VARCHAR(20) NOT NULL UNIQUE,
    nome VARCHAR(255) NOT NULL,
    descricao TEXT,
    categoria VARCHAR(100),
    gravidade VARCHAR(50),
    ativo BOOLEAN DEFAULT TRUE,
    data_sincronizacao TIMESTAMP,
    criado_em TIMESTAMP NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMP
);
```

### 6.2 Tabelas de Negócio

```sql
-- Lotes de Vacina
CREATE TABLE lotes_vacina (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    imunobiologico_id UUID NOT NULL REFERENCES imunobiologicos(id),
    fabricante_id UUID NOT NULL REFERENCES fabricantes_imunobiologicos(id),
    numero_lote VARCHAR(100) NOT NULL,
    data_fabricacao DATE,
    data_validade DATE NOT NULL,
    quantidade_recebida INTEGER,
    quantidade_disponivel INTEGER,
    preco_unitario DECIMAL(12,2),
    observacoes TEXT,
    estabelecimento_id UUID REFERENCES estabelecimentos(id),
    tenant_id UUID NOT NULL REFERENCES tenants(id),
    ativo BOOLEAN DEFAULT TRUE,
    criado_em TIMESTAMP NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMP,
    UNIQUE(numero_lote, imunobiologico_id, fabricante_id, tenant_id)
);

-- Aplicações de Vacina
CREATE TABLE aplicacoes_vacina (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    
    -- Identificadores FHIR
    fhir_identifier VARCHAR(255),
    fhir_status VARCHAR(50) NOT NULL DEFAULT 'completed',
    fhir_status_reason_codigo VARCHAR(50),
    fhir_status_reason_descricao VARCHAR(255),
    
    -- Paciente
    paciente_id UUID NOT NULL REFERENCES pacientes(id),
    
    -- Vacina
    imunobiologico_id UUID NOT NULL REFERENCES imunobiologicos(id),
    
    -- Lote e Fabricante
    lote_id UUID REFERENCES lotes_vacina(id),
    numero_lote VARCHAR(100),
    fabricante_id UUID REFERENCES fabricantes_imunobiologicos(id),
    data_validade DATE,
    
    -- Dose
    tipo_dose_id UUID REFERENCES tipos_dose(id),
    numero_dose INTEGER,
    dose_quantidade DECIMAL(10,2),
    dose_unidade VARCHAR(50),
    
    -- Local e Via
    local_aplicacao_id UUID REFERENCES locais_aplicacao(id),
    via_administracao_id UUID REFERENCES vias_administracao(id),
    
    -- Estratégia
    estrategia_id UUID REFERENCES estrategias_vacinacao(id),
    
    -- Data e Hora
    data_aplicacao TIMESTAMP NOT NULL,
    data_registro TIMESTAMP NOT NULL DEFAULT NOW(),
    
    -- Profissional
    profissional_id UUID REFERENCES profissionais_saude(id),
    profissional_funcao VARCHAR(100),
    
    -- Local
    estabelecimento_id UUID REFERENCES estabelecimentos(id),
    
    -- Fonte e Origem
    fonte_primaria BOOLEAN DEFAULT TRUE,
    origem_registro VARCHAR(100),
    
    -- Subpotência
    dose_subpotente BOOLEAN DEFAULT FALSE,
    motivo_subpotencia TEXT,
    
    -- Programa
    elegibilidade_programa VARCHAR(50),
    fonte_financiamento VARCHAR(100),
    
    -- Observações
    observacoes TEXT,
    
    -- Sincronização FHIR
    fhir_resource_id VARCHAR(255),
    fhir_sync_status VARCHAR(50),
    fhir_last_sync TIMESTAMP,
    
    -- Auditoria
    tenant_id UUID NOT NULL REFERENCES tenants(id),
    ativo BOOLEAN DEFAULT TRUE,
    criado_em TIMESTAMP NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMP,
    criado_por UUID REFERENCES usuarios_sistema(id),
    atualizado_por UUID REFERENCES usuarios_sistema(id)
);

-- Reações Adversas Pós-Vacinação
CREATE TABLE aplicacoes_vacina_reacoes (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    aplicacao_id UUID NOT NULL REFERENCES aplicacoes_vacina(id),
    reacao_catalogo_id UUID REFERENCES reacoes_adversas_catalogo(id),
    
    -- Dados da Reação
    data_ocorrencia TIMESTAMP NOT NULL,
    descricao TEXT,
    
    -- Classificação
    criticidade VARCHAR(50),
    grau_certeza VARCHAR(50),
    categoria_agente VARCHAR(50),
    
    -- Acompanhamento
    requer_tratamento BOOLEAN DEFAULT FALSE,
    tratamento_realizado TEXT,
    evolucao TEXT,
    resolvida BOOLEAN DEFAULT FALSE,
    data_resolucao TIMESTAMP,
    
    -- Notificação
    notificada_anvisa BOOLEAN DEFAULT FALSE,
    data_notificacao TIMESTAMP,
    numero_notificacao VARCHAR(100),
    
    -- Reportado
    reportado_por VARCHAR(100),
    profissional_registro_id UUID REFERENCES profissionais_saude(id),
    
    -- Auditoria
    tenant_id UUID NOT NULL REFERENCES tenants(id),
    ativo BOOLEAN DEFAULT TRUE,
    criado_em TIMESTAMP NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMP
);

-- Protocolo de Vacinação
CREATE TABLE protocolo_vacinacao (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    aplicacao_id UUID NOT NULL REFERENCES aplicacoes_vacina(id),
    serie VARCHAR(100),
    doenca_alvo_codigo VARCHAR(50),
    doenca_alvo_nome VARCHAR(255),
    numero_dose_aplicada INTEGER NOT NULL,
    total_doses_serie INTEGER,
    autoridade VARCHAR(255),
    tenant_id UUID NOT NULL REFERENCES tenants(id),
    ativo BOOLEAN DEFAULT TRUE,
    criado_em TIMESTAMP NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMP
);

-- Carteira de Vacinação
CREATE TABLE carteira_vacinacao (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    paciente_id UUID NOT NULL REFERENCES pacientes(id),
    data_ultima_atualizacao TIMESTAMP NOT NULL DEFAULT NOW(),
    status_geral VARCHAR(50),
    esquema_resumo JSONB,
    idade_referencia INTEGER,
    observacoes TEXT,
    tenant_id UUID NOT NULL REFERENCES tenants(id),
    estabelecimento_id UUID REFERENCES estabelecimentos(id),
    ativo BOOLEAN DEFAULT TRUE,
    criado_em TIMESTAMP NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMP
);

-- Índices
CREATE INDEX idx_aplicacoes_vacina_paciente ON aplicacoes_vacina(paciente_id);
CREATE INDEX idx_aplicacoes_vacina_data ON aplicacoes_vacina(data_aplicacao);
CREATE INDEX idx_aplicacoes_vacina_imunobiologico ON aplicacoes_vacina(imunobiologico_id);
CREATE INDEX idx_aplicacoes_vacina_tenant ON aplicacoes_vacina(tenant_id);
CREATE INDEX idx_lotes_vacina_numero ON lotes_vacina(numero_lote);
CREATE INDEX idx_lotes_vacina_validade ON lotes_vacina(data_validade);
```

---

## 7. DTOs Sugeridos

### 7.1 Request de Aplicação de Vacina

```java
@Data
public class AplicacaoVacinaRequest {
    @NotNull
    private UUID pacienteId;
    
    @NotNull
    private UUID imunobiologicoId;
    
    private UUID loteId;
    private String numeroLote;
    private UUID fabricanteId;
    private LocalDate dataValidade;
    
    @NotNull
    private UUID tipoDoseId;
    private Integer numeroDose;
    private BigDecimal doseQuantidade;
    private String doseUnidade;
    
    private UUID localAplicacaoId;
    private UUID viaAdministracaoId;
    private UUID estrategiaId;
    
    @NotNull
    private OffsetDateTime dataAplicacao;
    
    private UUID profissionalId;
    private String profissionalFuncao;
    private UUID estabelecimentoId;
    
    private Boolean fontePrimaria;
    private String origemRegistro;
    private Boolean doseSubpotente;
    private String motivoSubpotencia;
    private String elegibilidadePrograma;
    private String fonteFinanciamento;
    private String observacoes;
}
```

### 7.2 Response de Aplicação de Vacina

```java
@Data
public class AplicacaoVacinaResponse {
    private UUID id;
    private String fhirIdentifier;
    private String fhirStatus;
    
    private PacienteResumoResponse paciente;
    private ImunobiologicoResponse imunobiologico;
    private LoteVacinaResumoResponse lote;
    private FabricanteResponse fabricante;
    private TipoDoseResponse tipoDose;
    private LocalAplicacaoResponse localAplicacao;
    private ViaAdministracaoResponse viaAdministracao;
    private EstrategiaVacinacaoResponse estrategia;
    
    private OffsetDateTime dataAplicacao;
    private OffsetDateTime dataRegistro;
    private ProfissionalResumoResponse profissional;
    private EstabelecimentoResumoResponse estabelecimento;
    
    private Boolean fontePrimaria;
    private Boolean doseSubpotente;
    private String observacoes;
    
    private List<ReacaoAdversaResponse> reacoes;
    private List<ProtocoloVacinacaoResponse> protocolos;
    
    private OffsetDateTime criadoEm;
    private OffsetDateTime atualizadoEm;
}
```

### 7.3 DTOs FHIR

```java
@Data
public class FhirConceptDTO {
    private String code;
    private String display;
    private String definition;
}

@Data
public class FhirCodeSystemDTO {
    private String resourceType;
    private String id;
    private String url;
    private String version;
    private String name;
    private String status;
    private List<FhirConceptDTO> concept;
}

@Data
public class ImunobiologicoFhirDTO {
    private String codigoFhir;
    private String nome;
    private String sistemaUrl;
}

@Data
public class SincronizacaoResultDTO {
    private String recurso;
    private Integer totalEncontrados;
    private Integer novosInseridos;
    private Integer atualizados;
    private Integer erros;
    private OffsetDateTime dataHora;
    private List<String> mensagensErro;
}
```

---

## 8. Fluxos de Negócio

### 8.1 Registrar Aplicação de Vacina

```
1. Frontend envia dados da aplicação
2. Validar paciente existe
3. Validar imunobiológico existe (tabela local sincronizada)
4. Validar lote (se informado)
5. Validar profissional (se informado)
6. Criar registro de aplicação
7. Atualizar carteira de vacinação do paciente
8. Retornar resposta com dados completos
```

### 8.2 Sincronizar Catálogo de Vacinas

```
1. Chamar endpoint FHIR: GET /CodeSystem-BRImunobiologico.json
2. Parsear resposta JSON
3. Para cada conceito:
   a. Verificar se existe pelo código FHIR
   b. Se existe: atualizar nome/descrição
   c. Se não existe: inserir novo registro
4. Atualizar data_sincronizacao
5. Retornar estatísticas
```

### 8.3 Consultar Carteira de Vacinação

```
1. Buscar paciente pelo ID
2. Buscar todas as aplicações do paciente
3. Agrupar por imunobiológico
4. Calcular status do esquema vacinal
5. Identificar vacinas pendentes por idade
6. Retornar carteira consolidada
```

---

## 9. Considerações de Implementação

### 9.1 Cache
- Cachear dados de referência por 24 horas
- Invalidar cache após sincronização
- Usar Redis para cache distribuído

### 9.2 Multi-tenancy
- Tabelas de referência FHIR compartilhadas entre tenants
- Registros de aplicação são por tenant
- Lotes podem ser por estabelecimento

### 9.3 Auditoria
- Log de todas as operações de vacinação
- Histórico de alterações
- Rastreabilidade de quem aplicou

### 9.4 Validações
- Não permitir aplicação em paciente inativo
- Validar intervalo entre doses
- Alertar sobre vacinas vencidas
- Validar idade mínima/máxima por vacina

---

## 10. Próximos Passos

1. ⏳ Criar migrations para as tabelas
2. ⏳ Criar entidades JPA
3. ⏳ Implementar cliente HTTP FHIR
4. ⏳ Criar mappers FHIR → Entidade
5. ⏳ Implementar serviços de sincronização
6. ⏳ Implementar CRUD de aplicações
7. ⏳ Criar endpoints REST
8. ⏳ Implementar carteira de vacinação
9. ⏳ Testes unitários e de integração
