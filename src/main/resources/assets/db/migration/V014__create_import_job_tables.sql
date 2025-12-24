-- =====================================================
-- MIGRATION: Tabelas de Jobs de Importação (Fila de Processamento)
-- =====================================================
-- Objetivo: Criar tabelas para gerenciar fila de processamento de arquivos grandes
-- Autor: UPSaúde
-- =====================================================

-- Tabela principal de jobs de importação
CREATE TABLE IF NOT EXISTS public.import_job (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    criado_em TIMESTAMPTZ NOT NULL DEFAULT now(),
    atualizado_em TIMESTAMPTZ NULL,
    ativo BOOLEAN NOT NULL DEFAULT true,
    tenant_id UUID NOT NULL,
    
    -- Identificação
    tipo VARCHAR(20) NOT NULL, -- SIA_PA, SIGTAP, CID10
    competencia_ano VARCHAR(4) NULL,
    competencia_mes VARCHAR(2) NULL,
    uf VARCHAR(2) NULL,
    
    -- Arquivo no Storage
    storage_bucket VARCHAR(100) NOT NULL,
    storage_path VARCHAR(500) NOT NULL,
    original_filename VARCHAR(255) NOT NULL,
    content_type VARCHAR(100) NULL,
    size_bytes BIGINT NULL,
    checksum VARCHAR(64) NULL,
    
    -- Status e execução
    status VARCHAR(20) NOT NULL DEFAULT 'ENFILEIRADO', -- ENFILEIRADO, PROCESSANDO, CONCLUIDO, ERRO, CANCELADO, PAUSADO
    priority INTEGER NOT NULL DEFAULT 0, -- Maior número = maior prioridade
    attempts INTEGER NOT NULL DEFAULT 0,
    max_attempts INTEGER NOT NULL DEFAULT 3,
    next_run_at TIMESTAMPTZ NULL,
    locked_at TIMESTAMPTZ NULL,
    locked_by VARCHAR(100) NULL,
    heartbeat_at TIMESTAMPTZ NULL,
    
    -- Progresso
    linhas_lidas BIGINT NOT NULL DEFAULT 0,
    linhas_processadas BIGINT NOT NULL DEFAULT 0,
    linhas_inseridas BIGINT NOT NULL DEFAULT 0,
    linhas_erro BIGINT NOT NULL DEFAULT 0,
    percentual_estimado DOUBLE PRECISION NULL,
    started_at TIMESTAMPTZ NULL,
    finished_at TIMESTAMPTZ NULL,
    duration_ms BIGINT NULL,
    
    -- Checkpoint
    checkpoint_linha BIGINT NOT NULL DEFAULT 0,
    checkpoint_byte_offset BIGINT NULL,
    storage_object_version VARCHAR(100) NULL,
    
    -- Erros
    error_summary TEXT NULL,
    error_sample_json JSONB NULL,
    
    -- Auditoria
    created_by_user_id UUID NULL,
    
    CONSTRAINT fk_import_job_tenant FOREIGN KEY (tenant_id) REFERENCES public.tenants(id),
    CONSTRAINT fk_import_job_user FOREIGN KEY (created_by_user_id) REFERENCES auth.users(id)
);

-- Índices para otimização de consultas da fila
CREATE INDEX IF NOT EXISTS idx_import_job_status ON public.import_job (status);
CREATE INDEX IF NOT EXISTS idx_import_job_tipo ON public.import_job (tipo);
CREATE INDEX IF NOT EXISTS idx_import_job_next_run_at ON public.import_job (next_run_at);
CREATE INDEX IF NOT EXISTS idx_import_job_tenant_status ON public.import_job (tenant_id, status);
CREATE INDEX IF NOT EXISTS idx_import_job_status_next_run ON public.import_job (status, next_run_at, priority DESC);
CREATE INDEX IF NOT EXISTS idx_import_job_heartbeat ON public.import_job (heartbeat_at);
CREATE INDEX IF NOT EXISTS idx_import_job_competencia ON public.import_job (competencia_ano, competencia_mes, uf);

-- Tabela de erros detalhados de processamento
CREATE TABLE IF NOT EXISTS public.import_job_error (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    criado_em TIMESTAMPTZ NOT NULL DEFAULT now(),
    atualizado_em TIMESTAMPTZ NULL,
    ativo BOOLEAN NOT NULL DEFAULT true,
    tenant_id UUID NOT NULL,
    
    job_id UUID NOT NULL,
    linha BIGINT NOT NULL,
    codigo_erro VARCHAR(50) NULL,
    mensagem TEXT NOT NULL,
    raw_line_hash VARCHAR(64) NULL,
    raw_line_preview VARCHAR(500) NULL,
    
    CONSTRAINT fk_import_job_error_job FOREIGN KEY (job_id) REFERENCES public.import_job(id) ON DELETE CASCADE,
    CONSTRAINT fk_import_job_error_tenant FOREIGN KEY (tenant_id) REFERENCES public.tenants(id)
);

-- Índices para consulta de erros
CREATE INDEX IF NOT EXISTS idx_import_job_error_job_id ON public.import_job_error (job_id);
CREATE INDEX IF NOT EXISTS idx_import_job_error_linha ON public.import_job_error (job_id, linha);
CREATE INDEX IF NOT EXISTS idx_import_job_error_codigo ON public.import_job_error (codigo_erro);

