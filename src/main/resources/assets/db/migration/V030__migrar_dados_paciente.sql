-- =====================================================
-- MIGRATION: Migrar dados de pacientes para novas tabelas
-- =====================================================
-- Objetivo: Migrar dados existentes de pacientes para as novas tabelas
-- preservando todos os dados e garantindo integridade
-- Autor: UPSaúde
-- =====================================================

-- =====================================================
-- 1. MIGRAR IDENTIFICADORES (CPF, CNS, RG)
-- =====================================================

-- Migrar CPF
INSERT INTO public.paciente_identificador (paciente_id, tipo, valor, origem, validado, principal, tenant_id, estabelecimento_id, criado_em, atualizado_em, ativo)
SELECT 
    id,
    1, -- CPF
    cpf,
    1, -- UPSAUDE (origem padrão)
    false, -- validado (será atualizado depois se necessário)
    true, -- principal
    tenant_id,
    estabelecimento_id,
    criado_em,
    atualizado_em,
    ativo
FROM public.pacientes
WHERE cpf IS NOT NULL AND cpf != ''
ON CONFLICT (tipo, valor, tenant_id) DO NOTHING;

-- Migrar CNS
INSERT INTO public.paciente_identificador (paciente_id, tipo, valor, origem, validado, principal, tenant_id, estabelecimento_id, criado_em, atualizado_em, ativo)
SELECT 
    id,
    2, -- CNS
    cns,
    1, -- UPSAUDE (origem padrão)
    COALESCE(cns_validado, false),
    true, -- principal
    tenant_id,
    estabelecimento_id,
    criado_em,
    atualizado_em,
    ativo
FROM public.pacientes
WHERE cns IS NOT NULL AND cns != ''
ON CONFLICT (tipo, valor, tenant_id) DO NOTHING;

-- Migrar RG
INSERT INTO public.paciente_identificador (paciente_id, tipo, valor, origem, validado, principal, tenant_id, estabelecimento_id, criado_em, atualizado_em, ativo)
SELECT 
    id,
    3, -- RG
    rg,
    1, -- UPSAUDE (origem padrão)
    false, -- validado
    true, -- principal
    tenant_id,
    estabelecimento_id,
    criado_em,
    atualizado_em,
    ativo
FROM public.pacientes
WHERE rg IS NOT NULL AND rg != ''
ON CONFLICT (tipo, valor, tenant_id) DO NOTHING;

-- =====================================================
-- 2. MIGRAR CONTATOS (TELEFONE, EMAIL)
-- =====================================================

-- Migrar telefone
INSERT INTO public.paciente_contato (paciente_id, tipo, valor, principal, verificado, tenant_id, estabelecimento_id, criado_em, atualizado_em, ativo)
SELECT 
    id,
    1, -- TELEFONE
    telefone,
    true, -- principal
    false, -- verificado
    tenant_id,
    estabelecimento_id,
    criado_em,
    atualizado_em,
    ativo
FROM public.pacientes
WHERE telefone IS NOT NULL AND telefone != ''
ON CONFLICT (tipo, valor, tenant_id) DO NOTHING;

-- Migrar email
INSERT INTO public.paciente_contato (paciente_id, tipo, valor, principal, verificado, tenant_id, estabelecimento_id, criado_em, atualizado_em, ativo)
SELECT 
    id,
    2, -- EMAIL
    email,
    true, -- principal
    false, -- verificado
    tenant_id,
    estabelecimento_id,
    criado_em,
    atualizado_em,
    ativo
FROM public.pacientes
WHERE email IS NOT NULL AND email != ''
ON CONFLICT (tipo, valor, tenant_id) DO NOTHING;

-- =====================================================
-- 3. CONSOLIDAR DADOS SOCIODEMOGRÁFICOS
-- =====================================================

-- Atualizar paciente_dados_sociodemograficos com dados de pacientes
-- (priorizar dados da tabela específica, mas preencher com dados de pacientes se não existir)
UPDATE public.paciente_dados_sociodemograficos psd
SET 
    raca_cor = COALESCE(psd.raca_cor, p.raca_cor),
    nacionalidade = COALESCE(psd.nacionalidade, p.nacionalidade),
    pais_nascimento = COALESCE(psd.pais_nascimento, p.pais_nascimento),
    naturalidade = COALESCE(psd.naturalidade, p.naturalidade),
    municipio_nascimento_ibge = COALESCE(psd.municipio_nascimento_ibge, p.municipio_nascimento_ibge),
    escolaridade = COALESCE(psd.escolaridade, p.escolaridade),
    ocupacao_profissao = COALESCE(psd.ocupacao_profissao, p.ocupacao_profissao),
    situacao_rua = COALESCE(psd.situacao_rua, p.situacao_rua)
FROM public.pacientes p
WHERE psd.paciente_id = p.id;

-- Criar registro em paciente_dados_sociodemograficos se não existir
INSERT INTO public.paciente_dados_sociodemograficos (
    paciente_id, raca_cor, nacionalidade, pais_nascimento, naturalidade, 
    municipio_nascimento_ibge, escolaridade, ocupacao_profissao, situacao_rua,
    tenant_id, estabelecimento_id, criado_em, atualizado_em, ativo
)
SELECT 
    p.id,
    p.raca_cor,
    p.nacionalidade,
    p.pais_nascimento,
    p.naturalidade,
    p.municipio_nascimento_ibge,
    p.escolaridade,
    p.ocupacao_profissao,
    COALESCE(p.situacao_rua, false),
    p.tenant_id,
    p.estabelecimento_id,
    p.criado_em,
    p.atualizado_em,
    p.ativo
FROM public.pacientes p
WHERE NOT EXISTS (
    SELECT 1 FROM public.paciente_dados_sociodemograficos psd 
    WHERE psd.paciente_id = p.id
)
AND (
    p.raca_cor IS NOT NULL OR 
    p.nacionalidade IS NOT NULL OR 
    p.pais_nascimento IS NOT NULL OR 
    p.naturalidade IS NOT NULL OR 
    p.municipio_nascimento_ibge IS NOT NULL OR 
    p.escolaridade IS NOT NULL OR 
    p.ocupacao_profissao IS NOT NULL OR 
    p.situacao_rua IS NOT NULL
);

-- =====================================================
-- 4. CONSOLIDAR DADOS DE RESPONSÁVEL LEGAL
-- =====================================================

-- Atualizar paciente_responsavel_legal com dados de pacientes
UPDATE public.paciente_responsavel_legal prl
SET 
    nome = COALESCE(prl.nome, p.responsavel_nome),
    cpf = COALESCE(prl.cpf, p.responsavel_cpf)
FROM public.pacientes p
WHERE prl.paciente_id = p.id
AND (p.responsavel_nome IS NOT NULL OR p.responsavel_cpf IS NOT NULL);

-- Criar registro em paciente_responsavel_legal se não existir mas houver dados em pacientes
INSERT INTO public.paciente_responsavel_legal (
    paciente_id, nome, cpf, tenant_id, estabelecimento_id, criado_em, atualizado_em, ativo
)
SELECT 
    p.id,
    p.responsavel_nome,
    p.responsavel_cpf,
    p.tenant_id,
    p.estabelecimento_id,
    p.criado_em,
    p.atualizado_em,
    p.ativo
FROM public.pacientes p
WHERE NOT EXISTS (
    SELECT 1 FROM public.paciente_responsavel_legal prl 
    WHERE prl.paciente_id = p.id
)
AND (p.responsavel_nome IS NOT NULL OR p.responsavel_cpf IS NOT NULL);

-- Migrar telefone do responsável para paciente_contato (se não existir já)
INSERT INTO public.paciente_contato (paciente_id, tipo, valor, principal, verificado, observacoes, tenant_id, estabelecimento_id, criado_em, atualizado_em, ativo)
SELECT 
    p.id,
    1, -- TELEFONE
    p.responsavel_telefone,
    false, -- não principal (é do responsável)
    false, -- verificado
    'Telefone do responsável legal',
    p.tenant_id,
    p.estabelecimento_id,
    p.criado_em,
    p.atualizado_em,
    p.ativo
FROM public.pacientes p
WHERE p.responsavel_telefone IS NOT NULL 
AND p.responsavel_telefone != ''
AND NOT EXISTS (
    SELECT 1 FROM public.paciente_contato pc 
    WHERE pc.paciente_id = p.id 
    AND pc.tipo = 1 
    AND pc.valor = p.responsavel_telefone
);

-- =====================================================
-- 5. MIGRAR VÍNCULOS TERRITORIAIS
-- =====================================================

-- Migrar de integracao_gov (agora paciente_integracao_gov)
INSERT INTO public.paciente_vinculo_territorial (
    paciente_id, cnes_estabelecimento, ine_equipe, microarea, 
    data_inicio, origem, ativo, tenant_id, estabelecimento_id, criado_em, atualizado_em
)
SELECT 
    pig.paciente_id,
    pig.cnes_estabelecimento_origem,
    pig.ine_equipe,
    pig.microarea,
    COALESCE(pig.criado_em::DATE, CURRENT_DATE),
    CASE 
        WHEN pig.sistema = 1 THEN 1 -- ESUS
        WHEN pig.sistema = 2 THEN 2 -- RNDS
        ELSE 3 -- MANUAL
    END,
    true, -- ativo
    pig.tenant_id,
    pig.estabelecimento_id,
    pig.criado_em,
    pig.atualizado_em
FROM public.paciente_integracao_gov pig
WHERE (pig.cnes_estabelecimento_origem IS NOT NULL 
    OR pig.ine_equipe IS NOT NULL 
    OR pig.microarea IS NOT NULL)
AND NOT EXISTS (
    SELECT 1 FROM public.paciente_vinculo_territorial pvt 
    WHERE pvt.paciente_id = pig.paciente_id 
    AND pvt.ativo = true
);

-- Migrar de enderecos (campos territoriais)
INSERT INTO public.paciente_vinculo_territorial (
    paciente_id, ine_equipe, microarea, data_inicio, origem, ativo, 
    tenant_id, estabelecimento_id, criado_em, atualizado_em
)
SELECT DISTINCT ON (pe.paciente_id)
    pe.paciente_id,
    e.ine_equipe,
    e.microarea,
    COALESCE(e.criado_em::DATE, CURRENT_DATE),
    3, -- MANUAL
    true, -- ativo
    e.tenant_id,
    e.estabelecimento_id,
    e.criado_em,
    e.atualizado_em
FROM public.pacientes_enderecos pe
JOIN public.enderecos e ON e.id = pe.endereco_id
WHERE (e.ine_equipe IS NOT NULL OR e.microarea IS NOT NULL)
AND NOT EXISTS (
    SELECT 1 FROM public.paciente_vinculo_territorial pvt 
    WHERE pvt.paciente_id = pe.paciente_id 
    AND pvt.ativo = true
)
ORDER BY pe.paciente_id, e.criado_em DESC;

-- =====================================================
-- 6. MIGRAR FLAGS DE INTEGRAÇÃO PARA paciente_integracao_gov
-- =====================================================

-- Atualizar paciente_integracao_gov com dados de pacientes
UPDATE public.paciente_integracao_gov pig
SET 
    cns_validado = COALESCE(pig.cns_validado, p.cns_validado),
    tipo_cns = COALESCE(pig.tipo_cns, p.tipo_cns),
    data_atualizacao_cns = COALESCE(pig.data_atualizacao_cns, p.data_atualizacao_cns),
    cartao_sus_ativo = COALESCE(pig.cartao_sus_ativo, p.cartao_sus_ativo),
    origem_cadastro = COALESCE(pig.origem_cadastro, p.origem_cadastro)
FROM public.pacientes p
WHERE pig.paciente_id = p.id;

-- Criar registro em paciente_integracao_gov se não existir mas houver flags em pacientes
INSERT INTO public.paciente_integracao_gov (
    paciente_id, sistema, cns_validado, tipo_cns, data_atualizacao_cns, 
    cartao_sus_ativo, origem_cadastro, tenant_id, estabelecimento_id, criado_em, atualizado_em, ativo
)
SELECT 
    p.id,
    1, -- ESUS (padrão)
    COALESCE(p.cns_validado, false),
    p.tipo_cns,
    p.data_atualizacao_cns,
    COALESCE(p.cartao_sus_ativo, true),
    p.origem_cadastro,
    p.tenant_id,
    p.estabelecimento_id,
    p.criado_em,
    p.atualizado_em,
    p.ativo
FROM public.pacientes p
WHERE NOT EXISTS (
    SELECT 1 FROM public.paciente_integracao_gov pig 
    WHERE pig.paciente_id = p.id
)
AND (
    p.cns_validado IS NOT NULL OR 
    p.tipo_cns IS NOT NULL OR 
    p.data_atualizacao_cns IS NOT NULL OR 
    p.cartao_sus_ativo IS NOT NULL OR 
    p.origem_cadastro IS NOT NULL OR
    p.acompanhado_por_equipe_esf IS NOT NULL
);

-- =====================================================
-- FIM DA MIGRAÇÃO DE DADOS
-- =====================================================

