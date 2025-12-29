-- =====================================================
-- MIGRATION: Refinamento final do schema Paciente
-- =====================================================
-- Objetivo: Ajuste fino de nomenclatura para conformidade estrita com o plano
-- Autor: Antigravity Agent
-- =====================================================

-- 1. Renomear paciente_dados_clinicos para paciente_dados_clinicos_basicos
ALTER TABLE IF EXISTS public.paciente_dados_clinicos 
    RENAME TO paciente_dados_clinicos_basicos;

-- Renomear índices associados para manter consistência
DO $$
BEGIN
    IF EXISTS (SELECT 1 FROM pg_indexes WHERE indexname = 'idx_paciente_dados_clinicos_paciente') THEN
        ALTER INDEX public.idx_paciente_dados_clinicos_paciente 
            RENAME TO idx_paciente_dados_clinicos_basicos_paciente;
    END IF;
END $$;

-- Renomear Constraints de FK
DO $$
DECLARE
    constraint_name_var TEXT;
BEGIN
    FOR constraint_name_var IN 
        SELECT constraint_name 
        FROM information_schema.table_constraints 
        WHERE table_schema = 'public' 
        AND table_name = 'paciente_dados_clinicos_basicos' 
        AND constraint_type = 'FOREIGN KEY'
        AND constraint_name LIKE '%dados_clinicos%'
    LOOP
        EXECUTE format('ALTER TABLE public.paciente_dados_clinicos_basicos RENAME CONSTRAINT %I TO %I', 
            constraint_name_var, 
            replace(constraint_name_var, 'dados_clinicos', 'dados_clinicos_basicos'));
    END LOOP;
END $$;
