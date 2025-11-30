-- Script de Seed: Equipes de Saúde
-- Cria equipes de saúde vinculadas aos estabelecimentos
-- Depende de: Tenant, Estabelecimentos
-- Executado quando app.seed.enabled=true

-- Equipe ESF - UBS Centro
INSERT INTO public.equipes_saude (
    id,
    criado_em,
    atualizado_em,
    tenant_id,
    estabelecimento_id,
    ativo,
    ine,
    nome_referencia,
    tipo_equipe,
    data_ativacao,
    status,
    observacoes
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    t.id,
    e.id,
    true,
    '000000000000001',
    'Equipe Saúde da Família - UBS Centro',
    2,
    NOW() - INTERVAL '5 years',
    1,
    'Equipe de Saúde da Família responsável pela cobertura do território do Centro'
FROM public.tenants t, public.estabelecimentos e
WHERE t.slug = 'prefeitura-santa-rita-do-sapucai'
  AND e.codigo_cnes = '7101234'
  AND e.tenant_id = t.id
  AND NOT EXISTS (SELECT 1 FROM public.equipes_saude eq WHERE eq.ine = '000000000000001' AND eq.estabelecimento_id = e.id);

-- Equipe ESF - UBS Novo Horizonte
INSERT INTO public.equipes_saude (
    id,
    criado_em,
    atualizado_em,
    tenant_id,
    estabelecimento_id,
    ativo,
    ine,
    nome_referencia,
    tipo_equipe,
    data_ativacao,
    status,
    observacoes
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    t.id,
    e.id,
    true,
    '000000000000002',
    'Equipe Saúde da Família - UBS Novo Horizonte',
    2,
    NOW() - INTERVAL '3 years',
    1,
    'Equipe de Saúde da Família responsável pela cobertura do bairro Novo Horizonte'
FROM public.tenants t, public.estabelecimentos e
WHERE t.slug = 'prefeitura-santa-rita-do-sapucai'
  AND e.codigo_cnes = '7101238'
  AND e.tenant_id = t.id
  AND NOT EXISTS (SELECT 1 FROM public.equipes_saude eq WHERE eq.ine = '000000000000002' AND eq.estabelecimento_id = e.id);

-- Equipe ESF - UBS Jardim Primavera
INSERT INTO public.equipes_saude (
    id,
    criado_em,
    atualizado_em,
    tenant_id,
    estabelecimento_id,
    ativo,
    ine,
    nome_referencia,
    tipo_equipe,
    data_ativacao,
    status,
    observacoes
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    t.id,
    e.id,
    true,
    '000000000000003',
    'Equipe Saúde da Família - UBS Jardim Primavera',
    2,
    NOW() - INTERVAL '2 years',
    1,
    'Equipe de Saúde da Família responsável pela cobertura do bairro Jardim Primavera'
FROM public.tenants t, public.estabelecimentos e
WHERE t.slug = 'prefeitura-santa-rita-do-sapucai'
  AND e.codigo_cnes = '7101239'
  AND e.tenant_id = t.id
  AND NOT EXISTS (SELECT 1 FROM public.equipes_saude eq WHERE eq.ine = '000000000000003' AND eq.estabelecimento_id = e.id);

-- Equipe Médica - UPA 24 Horas
INSERT INTO public.equipes_saude (
    id,
    criado_em,
    atualizado_em,
    tenant_id,
    estabelecimento_id,
    ativo,
    ine,
    nome_referencia,
    tipo_equipe,
    data_ativacao,
    status,
    observacoes
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    t.id,
    e.id,
    true,
    '000000000000004',
    'Equipe Médica Plantonista - UPA 24 Horas',
    5,
    NOW() - INTERVAL '4 years',
    1,
    'Equipe médica plantonista da UPA 24 Horas'
FROM public.tenants t, public.estabelecimentos e
WHERE t.slug = 'prefeitura-santa-rita-do-sapucai'
  AND e.codigo_cnes = '7101235'
  AND e.tenant_id = t.id
  AND NOT EXISTS (SELECT 1 FROM public.equipes_saude eq WHERE eq.ine = '000000000000004' AND eq.estabelecimento_id = e.id);

-- Equipe de Urgência - UPA 24 Horas
INSERT INTO public.equipes_saude (
    id,
    criado_em,
    atualizado_em,
    tenant_id,
    estabelecimento_id,
    ativo,
    ine,
    nome_referencia,
    tipo_equipe,
    data_ativacao,
    status,
    observacoes
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    t.id,
    e.id,
    true,
    '000000000000005',
    'Equipe de Urgência - UPA 24 Horas',
    8,
    NOW() - INTERVAL '4 years',
    1,
    'Equipe multiprofissional de urgência e emergência da UPA'
FROM public.tenants t, public.estabelecimentos e
WHERE t.slug = 'prefeitura-santa-rita-do-sapucai'
  AND e.codigo_cnes = '7101235'
  AND e.tenant_id = t.id
  AND NOT EXISTS (SELECT 1 FROM public.equipes_saude eq WHERE eq.ine = '000000000000005' AND eq.estabelecimento_id = e.id);

-- NASF - Núcleo de Apoio à Saúde da Família - UBS Centro
INSERT INTO public.equipes_saude (
    id,
    criado_em,
    atualizado_em,
    tenant_id,
    estabelecimento_id,
    ativo,
    ine,
    nome_referencia,
    tipo_equipe,
    data_ativacao,
    status,
    observacoes
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    t.id,
    e.id,
    true,
    '000000000000006',
    'NASF - Núcleo de Apoio à Saúde da Família',
    4,
    NOW() - INTERVAL '3 years',
    1,
    'NASF vinculado à UBS Centro, oferece apoio especializado às equipes ESF'
FROM public.tenants t, public.estabelecimentos e
WHERE t.slug = 'prefeitura-santa-rita-do-sapucai'
  AND e.codigo_cnes = '7101234'
  AND e.tenant_id = t.id
  AND NOT EXISTS (SELECT 1 FROM public.equipes_saude eq WHERE eq.ine = '000000000000006' AND eq.estabelecimento_id = e.id);

