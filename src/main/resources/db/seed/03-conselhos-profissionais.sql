-- Script de Seed: Conselhos Profissionais
-- Cria os principais conselhos profissionais para o tenant
-- Executado quando app.seed.enabled=true

-- CRM
INSERT INTO public.conselhos_profissionais (
    id,
    criado_em,
    atualizado_em,
    tenant_id,
    estabelecimento_id,
    ativo,
    sigla,
    nome,
    descricao
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    t.id,
    NULL,
    true,
    'CRM',
    'Conselho Regional de Medicina',
    'Conselho profissional responsável pela regulamentação e fiscalização da profissão médica'
FROM public.tenants t
WHERE t.slug = 'prefeitura-santa-rita-do-sapucai'
  AND NOT EXISTS (SELECT 1 FROM public.conselhos_profissionais cp WHERE cp.sigla = 'CRM' AND cp.tenant_id = t.id);

-- COREN
INSERT INTO public.conselhos_profissionais (
    id,
    criado_em,
    atualizado_em,
    tenant_id,
    estabelecimento_id,
    ativo,
    sigla,
    nome,
    descricao
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    t.id,
    NULL,
    true,
    'COREN',
    'Conselho Regional de Enfermagem',
    'Conselho profissional responsável pela regulamentação e fiscalização da profissão de enfermagem'
FROM public.tenants t
WHERE t.slug = 'prefeitura-santa-rita-do-sapucai'
  AND NOT EXISTS (SELECT 1 FROM public.conselhos_profissionais cp WHERE cp.sigla = 'COREN' AND cp.tenant_id = t.id);

-- CRF
INSERT INTO public.conselhos_profissionais (
    id,
    criado_em,
    atualizado_em,
    tenant_id,
    estabelecimento_id,
    ativo,
    sigla,
    nome,
    descricao
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    t.id,
    NULL,
    true,
    'CRF',
    'Conselho Regional de Farmácia',
    'Conselho profissional responsável pela regulamentação e fiscalização da profissão farmacêutica'
FROM public.tenants t
WHERE t.slug = 'prefeitura-santa-rita-do-sapucai'
  AND NOT EXISTS (SELECT 1 FROM public.conselhos_profissionais cp WHERE cp.sigla = 'CRF' AND cp.tenant_id = t.id);

-- CRP
INSERT INTO public.conselhos_profissionais (
    id,
    criado_em,
    atualizado_em,
    tenant_id,
    estabelecimento_id,
    ativo,
    sigla,
    nome,
    descricao
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    t.id,
    NULL,
    true,
    'CRP',
    'Conselho Regional de Psicologia',
    'Conselho profissional responsável pela regulamentação e fiscalização da profissão de psicologia'
FROM public.tenants t
WHERE t.slug = 'prefeitura-santa-rita-do-sapucai'
  AND NOT EXISTS (SELECT 1 FROM public.conselhos_profissionais cp WHERE cp.sigla = 'CRP' AND cp.tenant_id = t.id);

-- CRO
INSERT INTO public.conselhos_profissionais (
    id,
    criado_em,
    atualizado_em,
    tenant_id,
    estabelecimento_id,
    ativo,
    sigla,
    nome,
    descricao
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    t.id,
    NULL,
    true,
    'CRO',
    'Conselho Regional de Odontologia',
    'Conselho profissional responsável pela regulamentação e fiscalização da profissão de odontologia'
FROM public.tenants t
WHERE t.slug = 'prefeitura-santa-rita-do-sapucai'
  AND NOT EXISTS (SELECT 1 FROM public.conselhos_profissionais cp WHERE cp.sigla = 'CRO' AND cp.tenant_id = t.id);

-- CREFITO
INSERT INTO public.conselhos_profissionais (
    id,
    criado_em,
    atualizado_em,
    tenant_id,
    estabelecimento_id,
    ativo,
    sigla,
    nome,
    descricao
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    t.id,
    NULL,
    true,
    'CREFITO',
    'Conselho Regional de Fisioterapia e Terapia Ocupacional',
    'Conselho profissional responsável pela regulamentação e fiscalização das profissões de fisioterapia e terapia ocupacional'
FROM public.tenants t
WHERE t.slug = 'prefeitura-santa-rita-do-sapucai'
  AND NOT EXISTS (SELECT 1 FROM public.conselhos_profissionais cp WHERE cp.sigla = 'CREFITO' AND cp.tenant_id = t.id);

-- CRN
INSERT INTO public.conselhos_profissionais (
    id,
    criado_em,
    atualizado_em,
    tenant_id,
    estabelecimento_id,
    ativo,
    sigla,
    nome,
    descricao
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    t.id,
    NULL,
    true,
    'CRN',
    'Conselho Regional de Nutrição',
    'Conselho profissional responsável pela regulamentação e fiscalização da profissão de nutrição'
FROM public.tenants t
WHERE t.slug = 'prefeitura-santa-rita-do-sapucai'
  AND NOT EXISTS (SELECT 1 FROM public.conselhos_profissionais cp WHERE cp.sigla = 'CRN' AND cp.tenant_id = t.id);

-- CRFA
INSERT INTO public.conselhos_profissionais (
    id,
    criado_em,
    atualizado_em,
    tenant_id,
    estabelecimento_id,
    ativo,
    sigla,
    nome,
    descricao
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    t.id,
    NULL,
    true,
    'CRFA',
    'Conselho Regional de Fonoaudiologia',
    'Conselho profissional responsável pela regulamentação e fiscalização da profissão de fonoaudiologia'
FROM public.tenants t
WHERE t.slug = 'prefeitura-santa-rita-do-sapucai'
  AND NOT EXISTS (SELECT 1 FROM public.conselhos_profissionais cp WHERE cp.sigla = 'CRFA' AND cp.tenant_id = t.id);
