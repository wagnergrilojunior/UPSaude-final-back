-- Migration: Create FHIR sync log table
-- Version: V20260109160000
-- Description: Table to track FHIR synchronization operations

CREATE TABLE IF NOT EXISTS fhir_sync_log (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    recurso VARCHAR(100) NOT NULL,
    data_inicio TIMESTAMP NOT NULL,
    data_fim TIMESTAMP,
    status VARCHAR(50) NOT NULL,
    total_encontrados INTEGER DEFAULT 0,
    novos_inseridos INTEGER DEFAULT 0,
    atualizados INTEGER DEFAULT 0,
    erros INTEGER DEFAULT 0,
    mensagem_erro TEXT,
    usuario_id UUID,
    criado_em TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_fhir_sync_log_recurso ON fhir_sync_log(recurso);
CREATE INDEX IF NOT EXISTS idx_fhir_sync_log_data ON fhir_sync_log(data_inicio);
CREATE INDEX IF NOT EXISTS idx_fhir_sync_log_status ON fhir_sync_log(status);

COMMENT ON TABLE fhir_sync_log IS 'Log de sincronizacao com servidor FHIR do Ministerio da Saude';
COMMENT ON COLUMN fhir_sync_log.recurso IS 'Nome do recurso FHIR sincronizado (ex: BRImunobiologico, BRCID10)';
COMMENT ON COLUMN fhir_sync_log.status IS 'Status da sincronizacao: EM_ANDAMENTO, CONCLUIDO, ERRO';
