-- Migration: Create vaccination business tables
-- Version: V20260109162000
-- Description: Business tables for vaccination module

-- Lotes de Vacina
CREATE TABLE IF NOT EXISTS lotes_vacina (
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
    estabelecimento_id UUID,
    tenant_id UUID NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    criado_em TIMESTAMP NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMP,
    UNIQUE(numero_lote, imunobiologico_id, fabricante_id, tenant_id)
);

CREATE INDEX IF NOT EXISTS idx_lotes_vacina_numero ON lotes_vacina(numero_lote);
CREATE INDEX IF NOT EXISTS idx_lotes_vacina_validade ON lotes_vacina(data_validade);
CREATE INDEX IF NOT EXISTS idx_lotes_vacina_tenant ON lotes_vacina(tenant_id);
CREATE INDEX IF NOT EXISTS idx_lotes_vacina_estabelecimento ON lotes_vacina(estabelecimento_id);

-- Aplicacoes de Vacina
CREATE TABLE IF NOT EXISTS aplicacoes_vacina (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    
    -- Identificadores FHIR
    fhir_identifier VARCHAR(255),
    fhir_status VARCHAR(50) NOT NULL DEFAULT 'completed',
    fhir_status_reason_codigo VARCHAR(50),
    fhir_status_reason_descricao VARCHAR(255),
    
    -- Paciente
    paciente_id UUID NOT NULL,
    
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
    
    -- Estrategia
    estrategia_id UUID REFERENCES estrategias_vacinacao(id),
    
    -- Data e Hora
    data_aplicacao TIMESTAMP NOT NULL,
    data_registro TIMESTAMP NOT NULL DEFAULT NOW(),
    
    -- Profissional
    profissional_id UUID,
    profissional_funcao VARCHAR(100),
    
    -- Local
    estabelecimento_id UUID,
    
    -- Fonte e Origem
    fonte_primaria BOOLEAN DEFAULT TRUE,
    origem_registro VARCHAR(100),
    
    -- Subpotencia
    dose_subpotente BOOLEAN DEFAULT FALSE,
    motivo_subpotencia TEXT,
    
    -- Programa
    elegibilidade_programa VARCHAR(50),
    fonte_financiamento VARCHAR(100),
    
    -- Observacoes
    observacoes TEXT,
    
    -- Sincronizacao FHIR
    fhir_resource_id VARCHAR(255),
    fhir_sync_status VARCHAR(50),
    fhir_last_sync TIMESTAMP,
    
    -- Auditoria
    tenant_id UUID NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    criado_em TIMESTAMP NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMP,
    criado_por UUID,
    atualizado_por UUID
);

CREATE INDEX IF NOT EXISTS idx_aplicacoes_vacina_paciente ON aplicacoes_vacina(paciente_id);
CREATE INDEX IF NOT EXISTS idx_aplicacoes_vacina_data ON aplicacoes_vacina(data_aplicacao);
CREATE INDEX IF NOT EXISTS idx_aplicacoes_vacina_imunobiologico ON aplicacoes_vacina(imunobiologico_id);
CREATE INDEX IF NOT EXISTS idx_aplicacoes_vacina_tenant ON aplicacoes_vacina(tenant_id);
CREATE INDEX IF NOT EXISTS idx_aplicacoes_vacina_estabelecimento ON aplicacoes_vacina(estabelecimento_id);
CREATE INDEX IF NOT EXISTS idx_aplicacoes_vacina_status ON aplicacoes_vacina(fhir_status);

-- Reacoes Adversas Pos-Vacinacao
CREATE TABLE IF NOT EXISTS aplicacoes_vacina_reacoes (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    aplicacao_id UUID NOT NULL REFERENCES aplicacoes_vacina(id),
    
    -- Dados da Reacao
    codigo_reacao VARCHAR(50),
    nome_reacao VARCHAR(255),
    data_ocorrencia TIMESTAMP NOT NULL,
    descricao TEXT,
    
    -- Classificacao
    criticidade VARCHAR(50),
    grau_certeza VARCHAR(50),
    categoria_agente VARCHAR(50),
    
    -- Acompanhamento
    requer_tratamento BOOLEAN DEFAULT FALSE,
    tratamento_realizado TEXT,
    evolucao TEXT,
    resolvida BOOLEAN DEFAULT FALSE,
    data_resolucao TIMESTAMP,
    
    -- Notificacao
    notificada_anvisa BOOLEAN DEFAULT FALSE,
    data_notificacao TIMESTAMP,
    numero_notificacao VARCHAR(100),
    
    -- Reportado
    reportado_por VARCHAR(100),
    profissional_registro_id UUID,
    
    -- Auditoria
    tenant_id UUID NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    criado_em TIMESTAMP NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_reacoes_aplicacao ON aplicacoes_vacina_reacoes(aplicacao_id);
CREATE INDEX IF NOT EXISTS idx_reacoes_data ON aplicacoes_vacina_reacoes(data_ocorrencia);
CREATE INDEX IF NOT EXISTS idx_reacoes_tenant ON aplicacoes_vacina_reacoes(tenant_id);

-- Comments
COMMENT ON TABLE lotes_vacina IS 'Lotes de vacina recebidos pelo estabelecimento';
COMMENT ON TABLE aplicacoes_vacina IS 'Registro de aplicacoes de vacina em pacientes';
COMMENT ON TABLE aplicacoes_vacina_reacoes IS 'Reacoes adversas pos-vacinacao';
