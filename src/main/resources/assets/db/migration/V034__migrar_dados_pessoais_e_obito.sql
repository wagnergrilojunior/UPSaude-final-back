-- =====================================================
-- MIGRATION: Migrar dados pessoais complementares e óbito
-- =====================================================
-- Objetivo: Migrar dados existentes de pacientes para as novas tabelas
-- preservando todos os dados e garantindo integridade
-- Autor: UPSaúde
-- =====================================================

-- =====================================================
-- 1. MIGRAR DADOS PESSOAIS COMPLEMENTARES
-- =====================================================

INSERT INTO public.paciente_dados_pessoais_complementares (
    paciente_id, nome_mae, nome_pai, identidade_genero, orientacao_sexual,
    tenant_id, estabelecimento_id, criado_em, atualizado_em, ativo
)
SELECT 
    p.id,
    p.nome_mae,
    p.nome_pai,
    p.identidade_genero,
    p.orientacao_sexual,
    p.tenant_id,
    p.estabelecimento_id,
    p.criado_em,
    p.atualizado_em,
    p.ativo
FROM public.pacientes p
WHERE (
    p.nome_mae IS NOT NULL OR 
    p.nome_pai IS NOT NULL OR 
    p.identidade_genero IS NOT NULL OR 
    p.orientacao_sexual IS NOT NULL
)
AND NOT EXISTS (
    SELECT 1 FROM public.paciente_dados_pessoais_complementares pdpc 
    WHERE pdpc.paciente_id = p.id
);

-- =====================================================
-- 2. MIGRAR DADOS DE ÓBITO
-- =====================================================

INSERT INTO public.paciente_obito (
    paciente_id, data_obito, causa_obito_cid10, data_registro, origem,
    tenant_id, estabelecimento_id, criado_em, atualizado_em, ativo
)
SELECT 
    p.id,
    p.data_obito,
    p.causa_obito_cid10,
    COALESCE(p.atualizado_em, p.criado_em, CURRENT_TIMESTAMP),
    1, -- MANUAL (origem padrão para dados existentes)
    p.tenant_id,
    p.estabelecimento_id,
    p.criado_em,
    p.atualizado_em,
    p.ativo
FROM public.pacientes p
WHERE p.data_obito IS NOT NULL
AND NOT EXISTS (
    SELECT 1 FROM public.paciente_obito po 
    WHERE po.paciente_id = p.id
);

-- =====================================================
-- FIM DA MIGRAÇÃO DE DADOS
-- =====================================================

