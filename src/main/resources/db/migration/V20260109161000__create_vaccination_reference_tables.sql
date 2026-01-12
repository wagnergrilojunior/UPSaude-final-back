-- Migration: Create vaccination reference tables
-- Version: V20260109161000
-- Description: FHIR reference tables for vaccination module

-- Imunobiologicos (Vaccines)
CREATE TABLE IF NOT EXISTS imunobiologicos (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    codigo_fhir VARCHAR(20) NOT NULL UNIQUE,
    nome VARCHAR(255) NOT NULL,
    nome_abreviado VARCHAR(50),
    descricao TEXT,
    codigo_sistema VARCHAR(500),
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    data_sincronizacao TIMESTAMP,
    criado_em TIMESTAMP NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_imunobiologicos_codigo ON imunobiologicos(codigo_fhir);
CREATE INDEX IF NOT EXISTS idx_imunobiologicos_nome ON imunobiologicos USING gin(to_tsvector('portuguese', nome));
CREATE INDEX IF NOT EXISTS idx_imunobiologicos_ativo ON imunobiologicos(ativo);

-- Fabricantes de Imunobiologicos
CREATE TABLE IF NOT EXISTS fabricantes_imunobiologicos (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    codigo_fhir VARCHAR(20) NOT NULL UNIQUE,
    nome VARCHAR(255) NOT NULL,
    nome_fantasia VARCHAR(255),
    pais_origem VARCHAR(100),
    cnpj VARCHAR(20),
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    data_sincronizacao TIMESTAMP,
    criado_em TIMESTAMP NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_fabricantes_codigo ON fabricantes_imunobiologicos(codigo_fhir);
CREATE INDEX IF NOT EXISTS idx_fabricantes_nome ON fabricantes_imunobiologicos USING gin(to_tsvector('portuguese', nome));

-- Tipos de Dose
CREATE TABLE IF NOT EXISTS tipos_dose (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    codigo_fhir VARCHAR(20) NOT NULL UNIQUE,
    nome VARCHAR(255) NOT NULL,
    descricao TEXT,
    ordem_sequencia INTEGER,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    data_sincronizacao TIMESTAMP,
    criado_em TIMESTAMP NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_tipos_dose_codigo ON tipos_dose(codigo_fhir);

-- Locais de Aplicacao
CREATE TABLE IF NOT EXISTS locais_aplicacao (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    codigo_fhir VARCHAR(20) NOT NULL UNIQUE,
    nome VARCHAR(255) NOT NULL,
    descricao TEXT,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    data_sincronizacao TIMESTAMP,
    criado_em TIMESTAMP NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_locais_aplicacao_codigo ON locais_aplicacao(codigo_fhir);

-- Vias de Administracao
CREATE TABLE IF NOT EXISTS vias_administracao (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    codigo_fhir VARCHAR(20) NOT NULL UNIQUE,
    nome VARCHAR(255) NOT NULL,
    descricao TEXT,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    data_sincronizacao TIMESTAMP,
    criado_em TIMESTAMP NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_vias_administracao_codigo ON vias_administracao(codigo_fhir);

-- Estrategias de Vacinacao
CREATE TABLE IF NOT EXISTS estrategias_vacinacao (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    codigo_fhir VARCHAR(20) NOT NULL UNIQUE,
    nome VARCHAR(255) NOT NULL,
    descricao TEXT,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    data_sincronizacao TIMESTAMP,
    criado_em TIMESTAMP NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_estrategias_codigo ON estrategias_vacinacao(codigo_fhir);

-- Comments
COMMENT ON TABLE imunobiologicos IS 'Catalogo de vacinas sincronizado do FHIR BRImunobiologico';
COMMENT ON TABLE fabricantes_imunobiologicos IS 'Fabricantes de vacinas sincronizado do FHIR BRFabricantePNI';
COMMENT ON TABLE tipos_dose IS 'Tipos de dose sincronizado do FHIR BRDose';
COMMENT ON TABLE locais_aplicacao IS 'Locais de aplicacao sincronizado do FHIR BRLocalAplicacao';
COMMENT ON TABLE vias_administracao IS 'Vias de administracao sincronizado do FHIR BRViaAdministracao';
COMMENT ON TABLE estrategias_vacinacao IS 'Estrategias de vacinacao sincronizado do FHIR BREstrategiaVacinacao';
