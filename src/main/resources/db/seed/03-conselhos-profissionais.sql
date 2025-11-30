-- Script de Seed: Conselhos Profissionais (Escopo Global)
-- Cria os principais conselhos profissionais - dados globais sem tenant
-- Executado quando app.seed.enabled=true

-- CRM - Conselho Regional de Medicina
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
    'Conselho profissional responsável pela regulamentação e fiscalização da profissão médica. Órgão autárquico federal vinculado ao CFM.'
WHERE NOT EXISTS (SELECT 1 FROM public.conselhos_profissionais cp WHERE cp.sigla = 'CRM');

-- COREN - Conselho Regional de Enfermagem
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
    'Conselho profissional responsável pela regulamentação e fiscalização da profissão de enfermagem. Órgão autárquico federal vinculado ao COFEN.'
WHERE NOT EXISTS (SELECT 1 FROM public.conselhos_profissionais cp WHERE cp.sigla = 'COREN');

-- CRF - Conselho Regional de Farmácia
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
    'Conselho profissional responsável pela regulamentação e fiscalização da profissão farmacêutica. Órgão autárquico federal vinculado ao CFF.'
WHERE NOT EXISTS (SELECT 1 FROM public.conselhos_profissionais cp WHERE cp.sigla = 'CRF');

-- CRP - Conselho Regional de Psicologia
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
    'Conselho profissional responsável pela regulamentação e fiscalização da profissão de psicologia. Órgão autárquico federal vinculado ao CFP.'
WHERE NOT EXISTS (SELECT 1 FROM public.conselhos_profissionais cp WHERE cp.sigla = 'CRP');

-- CRO - Conselho Regional de Odontologia
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
    'Conselho profissional responsável pela regulamentação e fiscalização da profissão de odontologia. Órgão autárquico federal vinculado ao CFO.'
WHERE NOT EXISTS (SELECT 1 FROM public.conselhos_profissionais cp WHERE cp.sigla = 'CRO');

-- CREFITO - Conselho Regional de Fisioterapia e Terapia Ocupacional
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
    'Conselho profissional responsável pela regulamentação e fiscalização das profissões de fisioterapia e terapia ocupacional. Órgão autárquico federal vinculado ao COFFITO.'
WHERE NOT EXISTS (SELECT 1 FROM public.conselhos_profissionais cp WHERE cp.sigla = 'CREFITO');

-- CRN - Conselho Regional de Nutrição
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
    'Conselho profissional responsável pela regulamentação e fiscalização da profissão de nutrição. Órgão autárquico federal vinculado ao CFN.'
WHERE NOT EXISTS (SELECT 1 FROM public.conselhos_profissionais cp WHERE cp.sigla = 'CRN');

-- CRFA - Conselho Regional de Fonoaudiologia
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
    'Conselho profissional responsável pela regulamentação e fiscalização da profissão de fonoaudiologia. Órgão autárquico federal vinculado ao CFFa.'
WHERE NOT EXISTS (SELECT 1 FROM public.conselhos_profissionais cp WHERE cp.sigla = 'CRFA');
