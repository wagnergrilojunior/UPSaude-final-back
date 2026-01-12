# 👨‍⚕️ Integração FHIR - Módulo de Profissionais

## 1. Visão Geral

O módulo de profissionais integra com os recursos FHIR para padronização de:

- CBO - Classificação Brasileira de Ocupações
- Conselhos profissionais de saúde
- Tipos de participantes
- Responsabilidades em atendimentos

---

## 2. Recursos FHIR Utilizados

| Recurso | URL | Descrição |
|---------|-----|-----------|
| **BRCBO** | `/CodeSystem/BRCBO` | Classificação Brasileira de Ocupações |
| **BRConselhoProfissional** | `/CodeSystem/BRConselhoProfissional` | Conselhos de classe |
| **BRResponsabilidadeParticipante** | `/CodeSystem/BRResponsabilidadeParticipante` | Papel do profissional |
| **BRTipoParticipante** | `/CodeSystem/BRTipoParticipante` | Tipo de participante |

---

## 3. Conselhos Profissionais de Saúde

O FHIR BR define NamingSystem para todos os conselhos regionais:

| Conselho | Sigla | Estados |
|----------|-------|---------|
| Conselho Regional de Medicina | CRM | Todos (27 UFs) |
| Conselho Regional de Odontologia | CRO | Todos (27 UFs) |
| Conselho Regional de Enfermagem | COREN | Todos (27 UFs) |
| Conselho Regional de Farmácia | CRF | Todos (27 UFs) |
| Conselho Regional de Psicologia | CRP | Todos (27 UFs) |
| Conselho Regional de Nutricionista | CRN | Por região |
| Conselho Regional de Fisioterapia | CREFITO | Por região |
| Conselho Regional de Fonoaudiologia | CREFONO | Por região |

---

## 4. CBO - Classificação Brasileira de Ocupações

Códigos CBO relevantes para saúde:

| CBO | Ocupação |
|-----|----------|
| 2251-01 | Médico clínico |
| 2251-25 | Médico generalista |
| 2252-10 | Médico cirurgião geral |
| 2232-04 | Cirurgião-dentista clínico geral |
| 2235-05 | Enfermeiro |
| 2234-05 | Farmacêutico |
| 2237-10 | Nutricionista |
| 2236-05 | Fisioterapeuta geral |
| 2238-10 | Fonoaudiólogo geral |
| 2239-05 | Psicólogo clínico |
| 3222-05 | Técnico de enfermagem |

---

## 5. Responsabilidades do Participante

| Código | Descrição |
|--------|-----------|
| atendimento | Profissional responsável pelo atendimento clínico |
| alta | Profissional que realizou a alta |
| admissao | Profissional que admitiu o indivíduo |
| autorizador | Profissional que autorizou o procedimento |
| solicitante | Profissional que solicitou o atendimento |

---

## 6. Endpoints do Sistema UPSaude

### 6.1 Sincronização

```http
POST /api/fhir/sincronizar/cbo
POST /api/fhir/sincronizar/conselhos-profissionais
POST /api/fhir/sincronizar/responsabilidades
```

### 6.2 Consulta

```http
GET /api/fhir/consultar/cbo?termo={termo}
GET /api/fhir/consultar/cbo/{codigo}
GET /api/fhir/consultar/conselhos/{sigla}/{uf}
```

---

## 7. Modelagem de Dados

```sql
-- Classificação Brasileira de Ocupações
CREATE TABLE cbo (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    codigo VARCHAR(10) NOT NULL UNIQUE,
    descricao VARCHAR(255) NOT NULL,
    familia VARCHAR(10),
    familia_descricao VARCHAR(255),
    ativo BOOLEAN DEFAULT TRUE,
    data_sincronizacao TIMESTAMP,
    criado_em TIMESTAMP DEFAULT NOW()
);

-- Conselhos Profissionais
CREATE TABLE conselhos_profissionais (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    sigla VARCHAR(20) NOT NULL, -- CRM, CRO, COREN, etc.
    uf VARCHAR(2) NOT NULL,
    nome VARCHAR(255) NOT NULL,
    url_validacao VARCHAR(500),
    ativo BOOLEAN DEFAULT TRUE,
    criado_em TIMESTAMP DEFAULT NOW(),
    UNIQUE(sigla, uf)
);

-- Responsabilidades
CREATE TABLE responsabilidades_participante (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    codigo_fhir VARCHAR(50) NOT NULL UNIQUE,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    ativo BOOLEAN DEFAULT TRUE,
    criado_em TIMESTAMP DEFAULT NOW()
);
```

---

## 8. Integração com Cadastro de Profissionais

A tabela `profissionais_saude` do sistema deve incluir:

```sql
ALTER TABLE profissionais_saude ADD COLUMN IF NOT EXISTS cbo_id UUID REFERENCES cbo(id);
ALTER TABLE profissionais_saude ADD COLUMN IF NOT EXISTS conselho_sigla VARCHAR(20);
ALTER TABLE profissionais_saude ADD COLUMN IF NOT EXISTS conselho_uf VARCHAR(2);
ALTER TABLE profissionais_saude ADD COLUMN IF NOT EXISTS conselho_numero VARCHAR(20);
```

---

## 9. Casos de Uso

- **Validação de CRM/COREN**: verificar número do conselho
- **Escalas de trabalho**: filtrar por CBO
- **Relatórios de produção**: agrupar por ocupação
- **Assinatura digital**: identificar profissional responsável
