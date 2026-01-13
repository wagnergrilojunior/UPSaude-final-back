-- Migration para Módulo de Diagnósticos (CID-10 / CIAP-2)
-- Integração com FHIR (RNDS)

-- 1. Tabela de Catálogo CIAP-2 (Classificação Internacional de Atenção Primária)
CREATE TABLE ciap2 (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    codigo VARCHAR(20) NOT NULL UNIQUE,
    descricao VARCHAR(500) NOT NULL,
    capitulo CHAR(1),
    ativo BOOLEAN DEFAULT TRUE,
    data_sincronizacao TIMESTAMPTZ,
    criado_em TIMESTAMPTZ DEFAULT NOW(),
    atualizado_em TIMESTAMPTZ
);

-- 2. Atualização de CID-10 Subcategorias para padrões de integração
ALTER TABLE cid10_subcategorias 
ADD COLUMN data_sincronizacao TIMESTAMPTZ,
ADD COLUMN ativo BOOLEAN DEFAULT TRUE;

-- 3. Histórico Unificado de Diagnósticos/Condições do Paciente
CREATE TABLE diagnosticos_paciente (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    prontuario_id UUID NOT NULL REFERENCES prontuarios(id),
    tipo_catalogo VARCHAR(10) NOT NULL, -- CID10, CIAP2, OUTRO
    codigo VARCHAR(20) NOT NULL,
    descricao_personalizada VARCHAR(500),
    cid10_id UUID REFERENCES cid10_subcategorias(id),
    ciap2_id UUID REFERENCES ciap2(id),
    data_diagnostico DATE NOT NULL,
    status VARCHAR(20) DEFAULT 'ativo', -- ativo, resolvido, erro
    cronico BOOLEAN DEFAULT FALSE,
    observacoes TEXT,
    tenant_id UUID NOT NULL REFERENCES tenants(id),
    criado_em TIMESTAMPTZ DEFAULT NOW(),
    atualizado_em TIMESTAMPTZ
);

-- Índices
CREATE INDEX idx_ciap2_nome ON ciap2(descricao);
CREATE INDEX idx_ciap2_codigo ON ciap2(codigo);
CREATE INDEX idx_diagnosticos_paciente_prontuario ON diagnosticos_paciente(prontuario_id);
CREATE INDEX idx_diagnosticos_paciente_tenant ON diagnosticos_paciente(tenant_id);
CREATE INDEX idx_diagnosticos_paciente_cid10 ON diagnosticos_paciente(cid10_id);
CREATE INDEX idx_diagnosticos_paciente_ciap2 ON diagnosticos_paciente(ciap2_id);
