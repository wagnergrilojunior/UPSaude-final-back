-- V20260113100000__create_sinais_vitais_table.sql

CREATE TABLE sinais_vitais (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL,
    paciente_id UUID NOT NULL,
    profissional_id UUID,
    atendimento_id UUID,
    data_medicao TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    
    peso_kg DECIMAL(5,2),
    altura_cm INTEGER,
    imc DECIMAL(4,2),
    
    pressao_arterial_sistolica INTEGER,
    pressao_arterial_diastolica INTEGER,
    frequencia_cardiaca_bpm INTEGER,
    frequencia_respiratoria_rpm INTEGER,
    temperatura_celsius DECIMAL(3,1),
    saturacao_o2_percentual INTEGER,
    glicemia_capilar_mg_dl INTEGER,
    
    status_glicemia VARCHAR(20),
    observacoes TEXT,
    
    criado_em TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    ativo BOOLEAN DEFAULT TRUE,
    
    CONSTRAINT fk_sinais_vitais_tenant FOREIGN KEY (tenant_id) REFERENCES tenants(id),
    CONSTRAINT fk_sinais_vitais_paciente FOREIGN KEY (paciente_id) REFERENCES pacientes(id),
    CONSTRAINT fk_sinais_vitais_profissional FOREIGN KEY (profissional_id) REFERENCES profissionais_saude(id),
    CONSTRAINT fk_sinais_vitais_atendimento FOREIGN KEY (atendimento_id) REFERENCES atendimentos(id)
);

CREATE INDEX idx_sinais_vitais_paciente ON sinais_vitais(paciente_id);
CREATE INDEX idx_sinais_vitais_atendimento ON sinais_vitais(atendimento_id);
CREATE INDEX idx_sinais_vitais_tenant ON sinais_vitais(tenant_id);

-- Alter atendimentos to link to the new structured records
ALTER TABLE atendimentos ADD COLUMN sinal_vital_record_id UUID;
ALTER TABLE atendimentos ADD COLUMN main_cid10_id UUID;
ALTER TABLE atendimentos ADD COLUMN main_ciap2_id UUID;
ALTER TABLE atendimentos ADD COLUMN main_clinical_status VARCHAR(20);
ALTER TABLE atendimentos ADD COLUMN main_verification_status VARCHAR(20);

ALTER TABLE atendimentos ADD CONSTRAINT fk_atendimento_sinal_vital_record FOREIGN KEY (sinal_vital_record_id) REFERENCES sinais_vitais(id);
ALTER TABLE atendimentos ADD CONSTRAINT fk_atendimento_main_cid10 FOREIGN KEY (main_cid10_id) REFERENCES cid10_subcategorias(id);
ALTER TABLE atendimentos ADD CONSTRAINT fk_atendimento_main_ciap2 FOREIGN KEY (main_ciap2_id) REFERENCES ciap2(id);

COMMENT ON COLUMN atendimentos.sinal_vital_record_id IS 'Link para o registro estruturado de sinais vitais. O campo sinais_vitais (TEXT) será mantido para compatibilidade temporária.';
COMMENT ON COLUMN atendimentos.main_cid10_id IS 'Diagnóstico principal estruturado em CID-10.';
COMMENT ON COLUMN atendimentos.main_ciap2_id IS 'Diagnóstico principal estruturado em CIAP-2.';
