-- =====================================================
-- MIGRATION: Renomear tabelas do domínio paciente
-- =====================================================
-- Objetivo: Renomear tabelas existentes para seguir padrão paciente_*
-- Autor: UPSaúde
-- =====================================================

-- Renomear dados_sociodemograficos para paciente_dados_sociodemograficos
ALTER TABLE IF EXISTS public.dados_sociodemograficos 
    RENAME TO paciente_dados_sociodemograficos;

-- Renomear dados_clinicos_basicos para paciente_dados_clinicos
ALTER TABLE IF EXISTS public.dados_clinicos_basicos 
    RENAME TO paciente_dados_clinicos;

-- Renomear responsaveis_legais para paciente_responsavel_legal
ALTER TABLE IF EXISTS public.responsaveis_legais 
    RENAME TO paciente_responsavel_legal;

-- Renomear lgpd_consentimentos para paciente_lgpd_consentimento
ALTER TABLE IF EXISTS public.lgpd_consentimentos 
    RENAME TO paciente_lgpd_consentimento;

-- Renomear índices relacionados
DO $$
BEGIN
    -- Renomear índices de dados_sociodemograficos
    IF EXISTS (SELECT 1 FROM pg_indexes WHERE indexname = 'idx_dados_sociodemograficos_paciente') THEN
        ALTER INDEX public.idx_dados_sociodemograficos_paciente 
            RENAME TO idx_paciente_dados_sociodemograficos_paciente;
    END IF;
    
    -- Renomear índices de dados_clinicos_basicos
    IF EXISTS (SELECT 1 FROM pg_indexes WHERE indexname = 'idx_dados_clinicos_basicos_paciente') THEN
        ALTER INDEX public.idx_dados_clinicos_basicos_paciente 
            RENAME TO idx_paciente_dados_clinicos_paciente;
    END IF;
    
    -- Renomear índices de responsaveis_legais
    IF EXISTS (SELECT 1 FROM pg_indexes WHERE indexname = 'idx_responsaveis_legais_paciente') THEN
        ALTER INDEX public.idx_responsaveis_legais_paciente 
            RENAME TO idx_paciente_responsavel_legal_paciente;
    END IF;
    
    IF EXISTS (SELECT 1 FROM pg_indexes WHERE indexname = 'idx_responsaveis_legais_cpf') THEN
        ALTER INDEX public.idx_responsaveis_legais_cpf 
            RENAME TO idx_paciente_responsavel_legal_cpf;
    END IF;
    
    -- Renomear índices de lgpd_consentimentos
    IF EXISTS (SELECT 1 FROM pg_indexes WHERE indexname = 'idx_lgpd_consentimentos_paciente') THEN
        ALTER INDEX public.idx_lgpd_consentimentos_paciente 
            RENAME TO idx_paciente_lgpd_consentimento_paciente;
    END IF;
END $$;

-- Renomear foreign keys relacionadas
DO $$
DECLARE
    constraint_name_var TEXT;
BEGIN
    -- Renomear FK de dados_sociodemograficos
    FOR constraint_name_var IN 
        SELECT constraint_name 
        FROM information_schema.table_constraints 
        WHERE table_schema = 'public' 
        AND table_name = 'paciente_dados_sociodemograficos' 
        AND constraint_type = 'FOREIGN KEY'
        AND constraint_name LIKE '%dados_sociodemograficos%'
    LOOP
        EXECUTE format('ALTER TABLE public.paciente_dados_sociodemograficos RENAME CONSTRAINT %I TO %I', 
            constraint_name_var, 
            replace(constraint_name_var, 'dados_sociodemograficos', 'paciente_dados_sociodemograficos'));
    END LOOP;
    
    -- Renomear FK de dados_clinicos_basicos
    FOR constraint_name_var IN 
        SELECT constraint_name 
        FROM information_schema.table_constraints 
        WHERE table_schema = 'public' 
        AND table_name = 'paciente_dados_clinicos' 
        AND constraint_type = 'FOREIGN KEY'
        AND constraint_name LIKE '%dados_clinicos_basicos%'
    LOOP
        EXECUTE format('ALTER TABLE public.paciente_dados_clinicos RENAME CONSTRAINT %I TO %I', 
            constraint_name_var, 
            replace(constraint_name_var, 'dados_clinicos_basicos', 'paciente_dados_clinicos'));
    END LOOP;
    
    -- Renomear FK de responsaveis_legais
    FOR constraint_name_var IN 
        SELECT constraint_name 
        FROM information_schema.table_constraints 
        WHERE table_schema = 'public' 
        AND table_name = 'paciente_responsavel_legal' 
        AND constraint_type = 'FOREIGN KEY'
        AND constraint_name LIKE '%responsaveis_legais%'
    LOOP
        EXECUTE format('ALTER TABLE public.paciente_responsavel_legal RENAME CONSTRAINT %I TO %I', 
            constraint_name_var, 
            replace(constraint_name_var, 'responsaveis_legais', 'paciente_responsavel_legal'));
    END LOOP;
    
    -- Renomear FK de lgpd_consentimentos
    FOR constraint_name_var IN 
        SELECT constraint_name 
        FROM information_schema.table_constraints 
        WHERE table_schema = 'public' 
        AND table_name = 'paciente_lgpd_consentimento' 
        AND constraint_type = 'FOREIGN KEY'
        AND constraint_name LIKE '%lgpd_consentimentos%'
    LOOP
        EXECUTE format('ALTER TABLE public.paciente_lgpd_consentimento RENAME CONSTRAINT %I TO %I', 
            constraint_name_var, 
            replace(constraint_name_var, 'lgpd_consentimentos', 'paciente_lgpd_consentimento'));
    END LOOP;
END $$;

