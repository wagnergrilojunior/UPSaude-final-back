-- Migration para Módulo de Medicamentos (RNDS/OBM)
-- Criação de tabelas de catálogo baseadas na Ontologia Brasileira de Medicamentos

-- 1. Princípios Ativos (Virtual Therapeutic Moiety - VTM)
CREATE TABLE principios_ativos (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    codigo_fhir VARCHAR(50) NOT NULL UNIQUE,
    nome VARCHAR(255) NOT NULL,
    dcb VARCHAR(100), -- Denominação Comum Brasileira
    dci VARCHAR(100), -- Denominação Comum Internacional
    ativo BOOLEAN DEFAULT TRUE,
    data_sincronizacao TIMESTAMPTZ,
    criado_em TIMESTAMPTZ DEFAULT NOW(),
    atualizado_em TIMESTAMPTZ
);

-- 2. Unidades de Medida (UCUM / BRUnidadeMedida)
CREATE TABLE unidades_medida (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    codigo_fhir VARCHAR(50) NOT NULL UNIQUE,
    nome VARCHAR(100) NOT NULL,
    sigla VARCHAR(20),
    ativo BOOLEAN DEFAULT TRUE,
    data_sincronizacao TIMESTAMPTZ,
    criado_em TIMESTAMPTZ DEFAULT NOW(),
    atualizado_em TIMESTAMPTZ
);

-- 3. Medicamentos (Virtual Medicinal Product - VMP / CATMAT / ANVISA)
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
    data_sincronizacao TIMESTAMPTZ,
    criado_em TIMESTAMPTZ DEFAULT NOW(),
    atualizado_em TIMESTAMPTZ
);

-- Índices para performance de busca
CREATE INDEX idx_principios_ativos_nome ON principios_ativos(nome);
CREATE INDEX idx_medicamentos_nome ON medicamentos(nome);
CREATE INDEX idx_medicamentos_principio_ativo ON medicamentos(principio_ativo_id);
CREATE INDEX idx_medicamentos_ean ON medicamentos(codigo_ean);
CREATE INDEX idx_unidades_medida_nome ON unidades_medida(nome);
