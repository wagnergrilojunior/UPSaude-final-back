-- Script de Seed: Fabricantes de Medicamentos (Escopo Global)
-- Cria fabricantes reais de medicamentos - dados globais sem tenant
-- Executado quando app.seed.enabled=true

-- EMS - Empresa Médica e Farmacêutica
INSERT INTO public.fabricantes_medicamento (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    pais,
    contato_json
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'EMS S.A.',
    'Brasil',
    '{"telefone": "(19) 3881-6000", "email": "contato@ems.com.br", "site": "https://www.ems.com.br", "cnpj": "61181260000100"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'EMS S.A.');

-- Aché Laboratórios
INSERT INTO public.fabricantes_medicamento (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    pais,
    contato_json
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Aché Laboratórios Farmacêuticos S.A.',
    'Brasil',
    '{"telefone": "(11) 4879-6600", "email": "falecom@ache.com.br", "site": "https://www.ache.com.br", "cnpj": "61798717000148"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome LIKE '%Aché%');

-- Eurofarma
INSERT INTO public.fabricantes_medicamento (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    pais,
    contato_json
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Eurofarma Laboratórios S.A.',
    'Brasil',
    '{"telefone": "(11) 3645-3300", "email": "contato@eurofarma.com.br", "site": "https://www.eurofarma.com.br", "cnpj": "61014105000157"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Eurofarma Laboratórios S.A.');

-- Sanofi
INSERT INTO public.fabricantes_medicamento (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    pais,
    contato_json
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Sanofi-Aventis Farmacêutica Ltda',
    'Brasil',
    '{"telefone": "(11) 3751-6000", "email": "contato@sanofi.com", "site": "https://www.sanofi.com.br", "cnpj": "32293159000118"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome LIKE '%Sanofi%');

-- Novartis
INSERT INTO public.fabricantes_medicamento (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    pais,
    contato_json
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Novartis Biociências S.A.',
    'Brasil',
    '{"telefone": "(11) 5524-7000", "email": "contato@novartis.com", "site": "https://www.novartis.com.br", "cnpj": "47380461000105"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome LIKE '%Novartis%');

-- Pfizer
INSERT INTO public.fabricantes_medicamento (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    pais,
    contato_json
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Pfizer do Brasil',
    'Brasil',
    '{"telefone": "(11) 3090-4900", "email": "contato@pfizer.com", "site": "https://www.pfizer.com.br", "cnpj": "43814628000107"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome LIKE '%Pfizer%');

-- GlaxoSmithKline
INSERT INTO public.fabricantes_medicamento (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    pais,
    contato_json
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'GlaxoSmithKline Brasil Ltda',
    'Brasil',
    '{"telefone": "(21) 2529-4000", "email": "contato@gsk.com", "site": "https://www.gsk.com.br", "cnpj": "58376966000121"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome LIKE '%GlaxoSmithKline%');

-- Medley
INSERT INTO public.fabricantes_medicamento (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    pais,
    contato_json
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Medley Indústria Farmacêutica Ltda',
    'Brasil',
    '{"telefone": "(11) 4617-8000", "email": "contato@medley.com.br", "site": "https://www.medley.com.br", "cnpj": "05294029000159"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome LIKE '%Medley%');

