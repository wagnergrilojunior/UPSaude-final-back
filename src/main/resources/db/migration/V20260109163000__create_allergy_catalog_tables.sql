-- Migration: Create allergy catalog tables for FHIR integration
-- Version: V20260109163000
-- Description: Creates tables for allergen catalog (CBARA), adverse reactions (MedDRA), 
--              criticality levels, and agent categories

-- Catálogo de Alérgenos (CBARA + Medicamentos/Vacinas)
CREATE TABLE IF NOT EXISTS alergenos (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    codigo_fhir VARCHAR(50) NOT NULL UNIQUE,
    nome VARCHAR(255) NOT NULL,
    categoria VARCHAR(50),
    fonte VARCHAR(50),
    codigo_sistema VARCHAR(500),
    ativo BOOLEAN DEFAULT TRUE,
    data_sincronizacao TIMESTAMPTZ,
    criado_em TIMESTAMPTZ DEFAULT NOW(),
    atualizado_em TIMESTAMPTZ
);

-- Catálogo de Reações Adversas (MedDRA)
CREATE TABLE IF NOT EXISTS reacoes_adversas_catalogo (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    codigo_fhir VARCHAR(50) NOT NULL UNIQUE,
    nome VARCHAR(255) NOT NULL,
    categoria VARCHAR(100),
    codigo_sistema VARCHAR(500),
    ativo BOOLEAN DEFAULT TRUE,
    data_sincronizacao TIMESTAMPTZ,
    criado_em TIMESTAMPTZ DEFAULT NOW(),
    atualizado_em TIMESTAMPTZ
);

-- Criticidade de Alergias (low, high, unable-to-assess)
CREATE TABLE IF NOT EXISTS criticidade_alergia (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    codigo_fhir VARCHAR(50) NOT NULL UNIQUE,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    ativo BOOLEAN DEFAULT TRUE,
    data_sincronizacao TIMESTAMPTZ,
    criado_em TIMESTAMPTZ DEFAULT NOW()
);

-- Categorias de Agentes (medication, food, environment, biologic)
CREATE TABLE IF NOT EXISTS categoria_agente_alergia (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    codigo_fhir VARCHAR(50) NOT NULL UNIQUE,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    ativo BOOLEAN DEFAULT TRUE,
    data_sincronizacao TIMESTAMPTZ,
    criado_em TIMESTAMPTZ DEFAULT NOW()
);

-- Índices para otimização de buscas
CREATE INDEX IF NOT EXISTS idx_alergenos_nome ON alergenos(nome);
CREATE INDEX IF NOT EXISTS idx_alergenos_categoria ON alergenos(categoria);
CREATE INDEX IF NOT EXISTS idx_alergenos_fonte ON alergenos(fonte);
CREATE INDEX IF NOT EXISTS idx_alergenos_ativo ON alergenos(ativo);

CREATE INDEX IF NOT EXISTS idx_reacoes_adversas_nome ON reacoes_adversas_catalogo(nome);
CREATE INDEX IF NOT EXISTS idx_reacoes_adversas_categoria ON reacoes_adversas_catalogo(categoria);
CREATE INDEX IF NOT EXISTS idx_reacoes_adversas_ativo ON reacoes_adversas_catalogo(ativo);

CREATE INDEX IF NOT EXISTS idx_criticidade_ativo ON criticidade_alergia(ativo);
CREATE INDEX IF NOT EXISTS idx_categoria_agente_ativo ON categoria_agente_alergia(ativo);

-- Comentários nas tabelas
COMMENT ON TABLE alergenos IS 'Catálogo de alérgenos sincronizado do FHIR (CBARA, medicamentos, imunobiológicos)';
COMMENT ON TABLE reacoes_adversas_catalogo IS 'Catálogo de reações adversas sincronizado do FHIR (MedDRA)';
COMMENT ON TABLE criticidade_alergia IS 'Níveis de criticidade de alergias (FHIR: low, high, unable-to-assess)';
COMMENT ON TABLE categoria_agente_alergia IS 'Categorias de agentes causadores (FHIR: medication, food, environment, biologic)';
