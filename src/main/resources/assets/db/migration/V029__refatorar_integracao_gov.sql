-- =====================================================
-- MIGRATION: Refatorar integracao_gov para paciente_integracao_gov
-- =====================================================
-- Objetivo: 
-- 1. Renomear tabela integracao_gov para paciente_integracao_gov
-- 2. Mudar relação de OneToOne para OneToMany (remover unique constraint)
-- 3. Adicionar campos novos (sistema, versao_layout, payload_bruto, status_sincronizacao, etc.)
-- 4. Adicionar suporte para múltiplas integrações por paciente
-- Autor: UPSaúde
-- =====================================================

-- Renomear tabela
ALTER TABLE IF EXISTS public.integracao_gov 
    RENAME TO paciente_integracao_gov;

-- Remover constraint UNIQUE de paciente_id (permitir múltiplas integrações)
DO $$
BEGIN
    IF EXISTS (
        SELECT 1 
        FROM information_schema.table_constraints 
        WHERE constraint_schema = 'public' 
        AND table_name = 'paciente_integracao_gov' 
        AND constraint_name LIKE '%paciente_id%'
        AND constraint_type = 'UNIQUE'
    ) THEN
        -- Encontrar e remover constraint unique
        FOR rec IN 
            SELECT constraint_name 
            FROM information_schema.table_constraints 
            WHERE constraint_schema = 'public' 
            AND table_name = 'paciente_integracao_gov' 
            AND constraint_name LIKE '%paciente_id%'
            AND constraint_type = 'UNIQUE'
        LOOP
            EXECUTE format('ALTER TABLE public.paciente_integracao_gov DROP CONSTRAINT IF EXISTS %I', rec.constraint_name);
        END LOOP;
    END IF;
END $$;

-- Adicionar novas colunas
ALTER TABLE public.paciente_integracao_gov
    ADD COLUMN IF NOT EXISTS sistema INTEGER, -- 1=ESUS, 2=RNDS, 3=CADSUS, 99=OUTRO
    ADD COLUMN IF NOT EXISTS versao_layout VARCHAR(50),
    ADD COLUMN IF NOT EXISTS payload_bruto JSONB,
    ADD COLUMN IF NOT EXISTS data_sincronizacao TIMESTAMP WITH TIME ZONE,
    ADD COLUMN IF NOT EXISTS status_sincronizacao INTEGER, -- 1=SUCESSO, 2=ERRO, 3=PENDENTE
    ADD COLUMN IF NOT EXISTS erro_sincronizacao TEXT,
    ADD COLUMN IF NOT EXISTS cns_validado BOOLEAN DEFAULT false,
    ADD COLUMN IF NOT EXISTS tipo_cns INTEGER,
    ADD COLUMN IF NOT EXISTS data_atualizacao_cns DATE,
    ADD COLUMN IF NOT EXISTS cartao_sus_ativo BOOLEAN DEFAULT true;

-- Renomear coluna data_sincronizacao_gov para data_sincronizacao (se existir)
DO $$
BEGIN
    IF EXISTS (
        SELECT 1 
        FROM information_schema.columns 
        WHERE table_schema = 'public' 
        AND table_name = 'paciente_integracao_gov' 
        AND column_name = 'data_sincronizacao_gov'
    ) THEN
        ALTER TABLE public.paciente_integracao_gov 
            RENAME COLUMN data_sincronizacao_gov TO data_sincronizacao;
    END IF;
END $$;

-- Migrar dados existentes: definir sistema baseado em origem_cadastro ou padrão ESUS
UPDATE public.paciente_integracao_gov
SET sistema = CASE 
    WHEN origem_cadastro ILIKE '%esus%' OR origem_cadastro ILIKE '%es-us%' THEN 1 -- ESUS
    WHEN origem_cadastro ILIKE '%rnds%' THEN 2 -- RNDS
    WHEN origem_cadastro ILIKE '%cadsus%' THEN 3 -- CADSUS
    ELSE 1 -- Padrão ESUS
END
WHERE sistema IS NULL;

-- Migrar campos de pacientes para paciente_integracao_gov (será feito em migration posterior)
-- Por enquanto, apenas criar a estrutura

-- Renomear índices
DO $$
BEGIN
    IF EXISTS (SELECT 1 FROM pg_indexes WHERE indexname = 'idx_integracao_gov_paciente') THEN
        ALTER INDEX public.idx_integracao_gov_paciente 
            RENAME TO idx_paciente_integracao_gov_paciente;
    END IF;
    
    IF EXISTS (SELECT 1 FROM pg_indexes WHERE indexname = 'idx_integracao_gov_uuid_rnds') THEN
        ALTER INDEX public.idx_integracao_gov_uuid_rnds 
            RENAME TO idx_paciente_integracao_gov_uuid_rnds;
    END IF;
    
    IF EXISTS (SELECT 1 FROM pg_indexes WHERE indexname = 'idx_integracao_gov_id_integracao') THEN
        ALTER INDEX public.idx_integracao_gov_id_integracao 
            RENAME TO idx_paciente_integracao_gov_id_integracao;
    END IF;
END $$;

-- Criar novos índices
CREATE INDEX IF NOT EXISTS idx_paciente_integracao_gov_sistema 
    ON public.paciente_integracao_gov(sistema);

CREATE INDEX IF NOT EXISTS idx_paciente_integracao_gov_status_sincronizacao 
    ON public.paciente_integracao_gov(status_sincronizacao);

CREATE INDEX IF NOT EXISTS idx_paciente_integracao_gov_data_sincronizacao 
    ON public.paciente_integracao_gov(data_sincronizacao);

-- Renomear foreign keys relacionadas
DO $$
DECLARE
    constraint_name_var TEXT;
BEGIN
    FOR constraint_name_var IN 
        SELECT constraint_name 
        FROM information_schema.table_constraints 
        WHERE table_schema = 'public' 
        AND table_name = 'paciente_integracao_gov' 
        AND constraint_type = 'FOREIGN KEY'
        AND constraint_name LIKE '%integracao_gov%'
    LOOP
        EXECUTE format('ALTER TABLE public.paciente_integracao_gov RENAME CONSTRAINT %I TO %I', 
            constraint_name_var, 
            replace(constraint_name_var, 'integracao_gov', 'paciente_integracao_gov'));
    END LOOP;
END $$;

-- Comentários
COMMENT ON TABLE public.paciente_integracao_gov IS 'Armazena integrações governamentais do paciente permitindo múltiplas integrações (ESUS, RNDS, CADSUS)';
COMMENT ON COLUMN public.paciente_integracao_gov.sistema IS '1=ESUS, 2=RNDS, 3=CADSUS, 99=OUTRO';
COMMENT ON COLUMN public.paciente_integracao_gov.status_sincronizacao IS '1=SUCESSO, 2=ERRO, 3=PENDENTE';
COMMENT ON COLUMN public.paciente_integracao_gov.payload_bruto IS 'Armazena dados brutos recebidos/enviados em formato JSONB para evitar dependência de schema rígido';
COMMENT ON COLUMN public.paciente_integracao_gov.versao_layout IS 'Versão do layout usado na integração';

