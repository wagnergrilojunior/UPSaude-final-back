-- =====================================================
-- MIGRATION: Remover campos pessoais complementares e óbito de pacientes
-- =====================================================
-- Objetivo: Remover campos migrados para novas tabelas após validação completa
-- IMPORTANTE: Esta migration deve ser executada APENAS após validação completa
-- dos dados migrados. Os campos foram movidos para:
-- - nome_mae, nome_pai, identidade_genero, orientacao_sexual → paciente_dados_pessoais_complementares
-- - data_obito, causa_obito_cid10 → paciente_obito
-- Autor: UPSaúde
-- =====================================================

-- Remover colunas de dados pessoais complementares
ALTER TABLE public.pacientes 
    DROP COLUMN IF EXISTS nome_mae,
    DROP COLUMN IF EXISTS nome_pai,
    DROP COLUMN IF EXISTS identidade_genero,
    DROP COLUMN IF EXISTS orientacao_sexual;

-- Remover colunas de óbito
ALTER TABLE public.pacientes 
    DROP COLUMN IF EXISTS data_obito,
    DROP COLUMN IF EXISTS causa_obito_cid10;

-- =====================================================
-- FIM DA MIGRATION
-- =====================================================

