-- =====================================================
-- MIGRATION: Migrar dados de pacientes_enderecos para paciente_endereco
-- =====================================================
-- Objetivo: Migrar dados existentes da JoinTable para a nova tabela de vínculo
-- preservando todos os dados e adicionando metadados padrão
-- Autor: UPSaúde
-- =====================================================

-- Migrar dados da JoinTable pacientes_enderecos para paciente_endereco
INSERT INTO public.paciente_endereco (
    paciente_id, endereco_id, tipo_endereco, principal, origem, 
    data_inicio, ativo, tenant_id, estabelecimento_id, criado_em, atualizado_em
)
SELECT DISTINCT
    pe.paciente_id,
    pe.endereco_id,
    COALESCE(e.tipo_endereco, 1), -- RESIDENCIAL como padrão se não tiver tipo
    CASE 
        WHEN ROW_NUMBER() OVER (PARTITION BY pe.paciente_id ORDER BY e.criado_em) = 1 
        THEN true 
        ELSE false 
    END AS principal, -- Primeiro endereço por paciente é principal
    1, -- MANUAL (origem padrão para dados existentes)
    COALESCE(e.criado_em::DATE, CURRENT_DATE),
    true, -- ativo
    p.tenant_id,
    p.estabelecimento_id,
    COALESCE(e.criado_em, CURRENT_TIMESTAMP),
    COALESCE(e.atualizado_em, CURRENT_TIMESTAMP)
FROM public.pacientes_enderecos pe
JOIN public.pacientes p ON p.id = pe.paciente_id
JOIN public.enderecos e ON e.id = pe.endereco_id
WHERE NOT EXISTS (
    SELECT 1 FROM public.paciente_endereco pe2 
    WHERE pe2.paciente_id = pe.paciente_id 
    AND pe2.endereco_id = pe.endereco_id
);

-- =====================================================
-- FIM DA MIGRAÇÃO DE DADOS
-- =====================================================

