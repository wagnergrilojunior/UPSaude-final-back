-- Script de Seed: Conselhos Profissionais
-- Cria os principais conselhos profissionais (escopo global)
-- Executado quando app.seed.enabled=true

-- CRM
INSERT INTO public.conselhos_profissionais (
    id,
    criado_em,
    atualizado_em,
    ativo,
    sigla,
    nome,
    descricao
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'CRM',
    'Conselho Regional de Medicina',
    'Conselho profissional responsável pela regulamentação e fiscalização da profissão médica'
WHERE NOT EXISTS (SELECT 1 FROM public.conselhos_profissionais cp WHERE cp.sigla = 'CRM');

-- COREN
INSERT INTO public.conselhos_profissionais (
    id,
    criado_em,
    atualizado_em,
    ativo,
    sigla,
    nome,
    descricao
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'COREN',
    'Conselho Regional de Enfermagem',
    'Conselho profissional responsável pela regulamentação e fiscalização da profissão de enfermagem'
WHERE NOT EXISTS (SELECT 1 FROM public.conselhos_profissionais cp WHERE cp.sigla = 'COREN');

-- CRF
INSERT INTO public.conselhos_profissionais (
    id,
    criado_em,
    atualizado_em,
    ativo,
    sigla,
    nome,
    descricao
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'CRF',
    'Conselho Regional de Farmácia',
    'Conselho profissional responsável pela regulamentação e fiscalização da profissão farmacêutica'
WHERE NOT EXISTS (SELECT 1 FROM public.conselhos_profissionais cp WHERE cp.sigla = 'CRF');

-- CRP
INSERT INTO public.conselhos_profissionais (
    id,
    criado_em,
    atualizado_em,
    ativo,
    sigla,
    nome,
    descricao
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'CRP',
    'Conselho Regional de Psicologia',
    'Conselho profissional responsável pela regulamentação e fiscalização da profissão de psicologia'
WHERE NOT EXISTS (SELECT 1 FROM public.conselhos_profissionais cp WHERE cp.sigla = 'CRP');

-- CRO
INSERT INTO public.conselhos_profissionais (
    id,
    criado_em,
    atualizado_em,
    ativo,
    sigla,
    nome,
    descricao
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'CRO',
    'Conselho Regional de Odontologia',
    'Conselho profissional responsável pela regulamentação e fiscalização da profissão de odontologia'
WHERE NOT EXISTS (SELECT 1 FROM public.conselhos_profissionais cp WHERE cp.sigla = 'CRO');

-- CREFITO
INSERT INTO public.conselhos_profissionais (
    id,
    criado_em,
    atualizado_em,
    ativo,
    sigla,
    nome,
    descricao
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'CREFITO',
    'Conselho Regional de Fisioterapia e Terapia Ocupacional',
    'Conselho profissional responsável pela regulamentação e fiscalização das profissões de fisioterapia e terapia ocupacional'
WHERE NOT EXISTS (SELECT 1 FROM public.conselhos_profissionais cp WHERE cp.sigla = 'CREFITO');

-- CRN
INSERT INTO public.conselhos_profissionais (
    id,
    criado_em,
    atualizado_em,
    ativo,
    sigla,
    nome,
    descricao
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'CRN',
    'Conselho Regional de Nutrição',
    'Conselho profissional responsável pela regulamentação e fiscalização da profissão de nutrição'
WHERE NOT EXISTS (SELECT 1 FROM public.conselhos_profissionais cp WHERE cp.sigla = 'CRN');

-- CRFA
INSERT INTO public.conselhos_profissionais (
    id,
    criado_em,
    atualizado_em,
    ativo,
    sigla,
    nome,
    descricao
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'CRFA',
    'Conselho Regional de Fonoaudiologia',
    'Conselho profissional responsável pela regulamentação e fiscalização da profissão de fonoaudiologia'
WHERE NOT EXISTS (SELECT 1 FROM public.conselhos_profissionais cp WHERE cp.sigla = 'CRFA');
