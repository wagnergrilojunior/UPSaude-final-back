-- Migration: create_anexos_table
-- Description: Cria tabela de anexos centralizados para paciente/agendamento/atendimento/consulta/etc
-- Date: 2026-01-14

-- ============================================================================
-- PARTE 1: Criar tabela anexos
-- ============================================================================

CREATE TABLE IF NOT EXISTS public.anexos (
    -- Campos herdados de BaseEntity
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    criado_em TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMPTZ,
    tenant_id UUID NOT NULL,
    estabelecimento_id UUID,
    ativo BOOLEAN NOT NULL DEFAULT true,
    
    -- Vínculo genérico ao recurso alvo
    target_type VARCHAR(50) NOT NULL,
    target_id UUID NOT NULL,
    
    -- Storage (Supabase)
    storage_bucket VARCHAR(100) NOT NULL,
    storage_object_path VARCHAR(500) NOT NULL,
    
    -- Metadados do arquivo
    file_name_original VARCHAR(255) NOT NULL,
    mime_type VARCHAR(100) NOT NULL,
    size_bytes BIGINT NOT NULL,
    checksum VARCHAR(64),
    
    -- Categorização e visibilidade
    categoria INTEGER,
    visivel_para_paciente BOOLEAN NOT NULL DEFAULT false,
    status INTEGER NOT NULL DEFAULT 1, -- 1=PENDENTE, 2=ATIVO, 3=INATIVO, 4=EXCLUIDO
    
    -- Descrição e tags
    descricao TEXT,
    tags VARCHAR(500),
    
    -- Auditoria
    criado_por UUID,
    excluido_por UUID,
    
    -- Constraints
    CONSTRAINT fk_anexo_tenant 
        FOREIGN KEY (tenant_id) 
        REFERENCES public.tenants(id) 
        ON DELETE CASCADE,
    
    CONSTRAINT fk_anexo_estabelecimento 
        FOREIGN KEY (estabelecimento_id) 
        REFERENCES public.estabelecimentos(id) 
        ON DELETE SET NULL,
    
    -- Unique constraint para evitar duplicação de storage path
    CONSTRAINT uk_anexo_storage_path 
        UNIQUE (storage_bucket, storage_object_path)
);

-- ============================================================================
-- PARTE 2: Criar índices para performance
-- ============================================================================

CREATE INDEX IF NOT EXISTS idx_anexo_tenant_target 
ON public.anexos(tenant_id, target_type, target_id);

CREATE INDEX IF NOT EXISTS idx_anexo_target 
ON public.anexos(target_type, target_id);

CREATE INDEX IF NOT EXISTS idx_anexo_status 
ON public.anexos(status);

CREATE INDEX IF NOT EXISTS idx_anexo_criado_em 
ON public.anexos(criado_em);

CREATE INDEX IF NOT EXISTS idx_anexo_checksum 
ON public.anexos(checksum);

CREATE INDEX IF NOT EXISTS idx_anexo_tenant_id 
ON public.anexos(tenant_id);

CREATE INDEX IF NOT EXISTS idx_anexo_visivel_paciente 
ON public.anexos(visivel_para_paciente) 
WHERE visivel_para_paciente = true;

-- ============================================================================
-- PARTE 3: Adicionar comentários à tabela e colunas
-- ============================================================================

COMMENT ON TABLE public.anexos IS 'Armazena metadados de anexos (arquivos) vinculados a qualquer entidade do sistema (paciente, agendamento, atendimento, consulta, etc)';

COMMENT ON COLUMN public.anexos.target_type IS 'Tipo do recurso alvo: PACIENTE, AGENDAMENTO, ATENDIMENTO, CONSULTA, PRONTUARIO_EVENTO, PROFISSIONAL_SAUDE, USUARIO_SISTEMA, FINANCEIRO_FATURAMENTO';
COMMENT ON COLUMN public.anexos.target_id IS 'ID do recurso alvo (UUID)';
COMMENT ON COLUMN public.anexos.storage_bucket IS 'Nome do bucket no Supabase Storage (ex: anexos)';
COMMENT ON COLUMN public.anexos.storage_object_path IS 'Caminho completo do objeto no bucket (ex: tenant/{tenantId}/{targetType}/{targetId}/{anexoId}/{nomeSeguro})';
COMMENT ON COLUMN public.anexos.file_name_original IS 'Nome original do arquivo enviado pelo usuário';
COMMENT ON COLUMN public.anexos.mime_type IS 'Tipo MIME do arquivo (ex: application/pdf, image/jpeg)';
COMMENT ON COLUMN public.anexos.size_bytes IS 'Tamanho do arquivo em bytes';
COMMENT ON COLUMN public.anexos.checksum IS 'Hash SHA-256 do arquivo para deduplicação e integridade';
COMMENT ON COLUMN public.anexos.categoria IS 'Categoria do anexo: 1=LAUDO, 2=EXAME, 3=DOCUMENTO, 4=IMAGEM, 5=RECEITA, 6=ATESTADO, 7=ENCAMINHAMENTO, 99=OUTROS';
COMMENT ON COLUMN public.anexos.visivel_para_paciente IS 'Indica se o anexo é visível para o paciente no portal/app';
COMMENT ON COLUMN public.anexos.status IS 'Status do anexo: 1=PENDENTE, 2=ATIVO, 3=INATIVO, 4=EXCLUIDO';
COMMENT ON COLUMN public.anexos.descricao IS 'Descrição opcional do anexo';
COMMENT ON COLUMN public.anexos.tags IS 'Tags separadas por vírgula para categorização adicional';
COMMENT ON COLUMN public.anexos.criado_por IS 'ID do usuário que criou o anexo';
COMMENT ON COLUMN public.anexos.excluido_por IS 'ID do usuário que excluiu o anexo';

-- ============================================================================
-- PARTE 4: Habilitar Row Level Security (RLS)
-- ============================================================================

ALTER TABLE public.anexos ENABLE ROW LEVEL SECURITY;

-- Política para SELECT: usuários podem ver anexos do seu tenant
CREATE POLICY IF NOT EXISTS "Usuários podem visualizar anexos do seu tenant"
ON public.anexos
FOR SELECT
USING (tenant_id = current_setting('app.current_tenant_id')::uuid);

-- Política para INSERT: usuários podem inserir anexos no seu tenant
CREATE POLICY IF NOT EXISTS "Usuários podem inserir anexos no seu tenant"
ON public.anexos
FOR INSERT
WITH CHECK (tenant_id = current_setting('app.current_tenant_id')::uuid);

-- Política para UPDATE: usuários podem atualizar anexos do seu tenant
CREATE POLICY IF NOT EXISTS "Usuários podem atualizar anexos do seu tenant"
ON public.anexos
FOR UPDATE
USING (tenant_id = current_setting('app.current_tenant_id')::uuid)
WITH CHECK (tenant_id = current_setting('app.current_tenant_id')::uuid);

-- Política para DELETE: usuários podem deletar anexos do seu tenant
CREATE POLICY IF NOT EXISTS "Usuários podem deletar anexos do seu tenant"
ON public.anexos
FOR DELETE
USING (tenant_id = current_setting('app.current_tenant_id')::uuid);

-- ============================================================================
-- FIM DA MIGRAÇÃO
-- ============================================================================
