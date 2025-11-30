-- Script de Seed: Medicações Contínuas (Escopo Global)
-- Cria medicações de uso contínuo comuns - dados globais sem tenant
-- Executado quando app.seed.enabled=true

-- Losartana (Anti-hipertensivo)
INSERT INTO public.medicacoes_continuas (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    dosagem,
    fabricante
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Losartana Potássica',
    '50mg',
    'EMS S.A.'
WHERE NOT EXISTS (SELECT 1 FROM public.medicacoes_continuas WHERE nome = 'Losartana Potássica' AND dosagem = '50mg');

-- Atenolol (Anti-hipertensivo)
INSERT INTO public.medicacoes_continuas (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    dosagem,
    fabricante
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Atenolol',
    '50mg',
    'Eurofarma Laboratórios S.A.'
WHERE NOT EXISTS (SELECT 1 FROM public.medicacoes_continuas WHERE nome = 'Atenolol' AND dosagem = '50mg');

-- Metformina (Antidiabético)
INSERT INTO public.medicacoes_continuas (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    dosagem,
    fabricante
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Metformina',
    '850mg',
    'EMS S.A.'
WHERE NOT EXISTS (SELECT 1 FROM public.medicacoes_continuas WHERE nome = 'Metformina' AND dosagem = '850mg');

-- Sinvastatina (Hipolipemiante)
INSERT INTO public.medicacoes_continuas (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    dosagem,
    fabricante
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Sinvastatina',
    '20mg',
    'Aché Laboratórios Farmacêuticos S.A.'
WHERE NOT EXISTS (SELECT 1 FROM public.medicacoes_continuas WHERE nome = 'Sinvastatina' AND dosagem = '20mg');

-- Omeprazol (Protetor Gástrico)
INSERT INTO public.medicacoes_continuas (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    dosagem,
    fabricante
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Omeprazol',
    '20mg',
    'Medley Indústria Farmacêutica Ltda'
WHERE NOT EXISTS (SELECT 1 FROM public.medicacoes_continuas WHERE nome = 'Omeprazol' AND dosagem = '20mg');

-- Hidroclorotiazida (Diurético)
INSERT INTO public.medicacoes_continuas (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    dosagem,
    fabricante
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Hidroclorotiazida',
    '25mg',
    'EMS S.A.'
WHERE NOT EXISTS (SELECT 1 FROM public.medicacoes_continuas WHERE nome = 'Hidroclorotiazida' AND dosagem = '25mg');

-- Ácido Acetilsalicílico (AAS) - Antiagregante Plaquetário
INSERT INTO public.medicacoes_continuas (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    dosagem,
    fabricante
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Ácido Acetilsalicílico (AAS)',
    '100mg',
    'Sanofi-Aventis Farmacêutica Ltda'
WHERE NOT EXISTS (SELECT 1 FROM public.medicacoes_continuas WHERE nome LIKE '%Ácido Acetilsalicílico%' AND dosagem = '100mg');

-- Captopril (Anti-hipertensivo)
INSERT INTO public.medicacoes_continuas (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    dosagem,
    fabricante
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Captopril',
    '25mg',
    'Eurofarma Laboratórios S.A.'
WHERE NOT EXISTS (SELECT 1 FROM public.medicacoes_continuas WHERE nome = 'Captopril' AND dosagem = '25mg');

-- Levotiroxina (Hormônio Tireoidiano)
INSERT INTO public.medicacoes_continuas (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    dosagem,
    fabricante
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Levotiroxina Sódica',
    '75mcg',
    'Sanofi-Aventis Farmacêutica Ltda'
WHERE NOT EXISTS (SELECT 1 FROM public.medicacoes_continuas WHERE nome LIKE '%Levotiroxina%' AND dosagem = '75mcg');

-- Sertralina (Antidepressivo)
INSERT INTO public.medicacoes_continuas (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    dosagem,
    fabricante
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Sertralina',
    '50mg',
    'Pfizer do Brasil'
WHERE NOT EXISTS (SELECT 1 FROM public.medicacoes_continuas WHERE nome = 'Sertralina' AND dosagem = '50mg');

