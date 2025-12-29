-- =====================================================
-- MIGRATION: Alterar tipo de payload_bruto para JSONB
-- =====================================================
-- Objetivo: Converter coluna payload_bruto de VARCHAR/TEXT para JSONB nativo
-- para melhor suporte a dados estruturados e queries JSON
-- Autor: UPSaúde
-- =====================================================

-- Verificar e converter payload_bruto para JSONB
DO $$
BEGIN
    -- Verificar se a coluna existe e não é JSONB
    IF EXISTS (
        SELECT 1 
        FROM information_schema.columns 
        WHERE table_schema = 'public' 
        AND table_name = 'paciente_integracao_gov' 
        AND column_name = 'payload_bruto'
        AND data_type != 'jsonb'
    ) THEN
        -- Converter para JSONB
        -- Primeiro, tentar validar JSON existente
        -- Se houver dados inválidos, serão tratados como NULL
        ALTER TABLE public.paciente_integracao_gov 
            ALTER COLUMN payload_bruto TYPE JSONB 
            USING CASE 
                WHEN payload_bruto IS NULL OR payload_bruto = '' THEN NULL
                WHEN payload_bruto::text ~ '^[\s]*[\{\[]' THEN payload_bruto::jsonb
                ELSE NULL
            END;
    END IF;
END $$;

-- Adicionar índice GIN para queries JSONB eficientes
CREATE INDEX IF NOT EXISTS idx_paciente_integracao_gov_payload_bruto 
    ON public.paciente_integracao_gov USING GIN (payload_bruto);

-- Comentários
COMMENT ON COLUMN public.paciente_integracao_gov.payload_bruto IS 'Armazena dados brutos recebidos/enviados em formato JSONB para evitar dependência de schema rígido e permitir queries JSON eficientes';

