-- =====================================================
-- MIGRATION: Refatoração do Módulo de Alergias - Remover Legado
-- =====================================================
-- Objetivo: 
-- 1. Atualizar valores antigos do enum tipo em alergias_paciente
-- 2. Alterar tipo de data_registro para TIMESTAMP WITH TIME ZONE
-- 3. Adicionar índices compostos para melhor performance
-- 4. Remover tabela alergias (catálogo global deprecated)
-- 5. Remover índices relacionados à tabela alergias
-- Autor: UPSaúde
-- =====================================================

-- =====================================================
-- 1. ATUALIZAR VALORES ANTIGOS DO ENUM TIPO
-- =====================================================
-- Mapear valores antigos para os novos conforme mapeamento:
-- POEIRA, POLEN, FUNGO, ANIMAL, INALANTE → AMBIENTAL
-- LATEX, METAL, COSMETICO, PRODUTO_QUIMICO → CONTATO
-- SOL, FRIO, CALOR, AGUA, EXERCICIO, ESTRESSE → OUTRO
-- MEDICAMENTO, ALIMENTO, INSETO → manter

UPDATE public.alergias_paciente 
SET tipo = 'AMBIENTAL' 
WHERE tipo IN ('POEIRA', 'POLEN', 'FUNGO', 'ANIMAL', 'INALANTE');

UPDATE public.alergias_paciente 
SET tipo = 'CONTATO' 
WHERE tipo IN ('LATEX', 'METAL', 'COSMETICO', 'PRODUTO_QUIMICO');

UPDATE public.alergias_paciente 
SET tipo = 'OUTRO' 
WHERE tipo IN ('SOL', 'FRIO', 'CALOR', 'AGUA', 'EXERCICIO', 'ESTRESSE');

-- =====================================================
-- 2. ALTERAR TIPO DE DATA_REGISTRO PARA TIMESTAMP WITH TIME ZONE
-- =====================================================
-- Converter coluna data_registro de TIMESTAMP para TIMESTAMP WITH TIME ZONE
-- Se já for TIMESTAMP WITH TIME ZONE, o comando não fará nada

DO $$
BEGIN
    IF EXISTS (
        SELECT 1 
        FROM information_schema.columns 
        WHERE table_schema = 'public' 
        AND table_name = 'alergias_paciente' 
        AND column_name = 'data_registro'
        AND data_type = 'timestamp without time zone'
    ) THEN
        ALTER TABLE public.alergias_paciente 
        ALTER COLUMN data_registro TYPE TIMESTAMP WITH TIME ZONE 
        USING data_registro AT TIME ZONE 'UTC';
    END IF;
END $$;

-- =====================================================
-- 3. ADICIONAR ÍNDICES COMPOSTOS
-- =====================================================
-- Índices para melhorar performance de consultas por paciente + tipo e paciente + substancia

CREATE INDEX IF NOT EXISTS idx_alergia_paciente_paciente_tipo 
    ON public.alergias_paciente(paciente_id, tipo);

CREATE INDEX IF NOT EXISTS idx_alergia_paciente_paciente_substancia 
    ON public.alergias_paciente(paciente_id, substancia);

-- =====================================================
-- 4. REMOVER FOREIGN KEYS E CONSTRAINTS DA TABELA ALERGIAS (SE EXISTIREM)
-- =====================================================
-- Remover foreign keys que referenciam a tabela alergias
-- Verificar se existe tabela alergias_paciente antiga com foreign key para alergias

DO $$
BEGIN
    -- Remover foreign key de alergias_paciente antiga para alergias (se existir)
    IF EXISTS (
        SELECT 1 
        FROM information_schema.table_constraints 
        WHERE constraint_schema = 'public' 
        AND table_name = 'alergias_paciente' 
        AND constraint_name LIKE '%alergia%'
        AND constraint_type = 'FOREIGN KEY'
    ) THEN
        -- Encontrar e remover foreign keys relacionadas
        FOR rec IN 
            SELECT constraint_name 
            FROM information_schema.table_constraints 
            WHERE constraint_schema = 'public' 
            AND table_name = 'alergias_paciente' 
            AND constraint_name LIKE '%alergia%'
            AND constraint_type = 'FOREIGN KEY'
        LOOP
            EXECUTE format('ALTER TABLE public.alergias_paciente DROP CONSTRAINT IF EXISTS %I', rec.constraint_name);
        END LOOP;
    END IF;
END $$;

-- =====================================================
-- 5. REMOVER ÍNDICES RELACIONADOS À TABELA ALERGIAS
-- =====================================================
-- Remover índices da tabela alergias antes de dropar a tabela

DROP INDEX IF EXISTS public.idx_alergia_nome;
DROP INDEX IF EXISTS public.idx_alergia_tipo;
DROP INDEX IF EXISTS public.idx_alergia_codigo_cid;

-- =====================================================
-- 6. DROPAR TABELA ALERGIAS (CATÁLOGO GLOBAL)
-- =====================================================
-- Remover tabela alergias que era o catálogo global deprecated
-- A tabela alergias_paciente é a nova entidade correta

DROP TABLE IF EXISTS public.alergias CASCADE;

-- =====================================================
-- 7. VERIFICAR E REMOVER COLUNAS OBSOLETAS (SE EXISTIREM)
-- =====================================================
-- Se existir coluna alergia_id na tabela alergias_paciente (da entidade antiga), remover

DO $$
BEGIN
    IF EXISTS (
        SELECT 1 
        FROM information_schema.columns 
        WHERE table_schema = 'public' 
        AND table_name = 'alergias_paciente' 
        AND column_name = 'alergia_id'
    ) THEN
        ALTER TABLE public.alergias_paciente DROP COLUMN IF EXISTS alergia_id;
    END IF;
    
    -- Remover outras colunas que possam ter existido na entidade antiga
    ALTER TABLE public.alergias_paciente DROP COLUMN IF EXISTS data_diagnostico;
    ALTER TABLE public.alergias_paciente DROP COLUMN IF EXISTS alerta_medico;
END $$;

-- =====================================================
-- FIM DA MIGRATION
-- =====================================================

