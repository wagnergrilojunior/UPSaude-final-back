-- Script de Seed: Estados e Cidades
-- Cria dados de referência geográfica (MG e Santa Rita do Sapucaí)
-- Executado quando app.seed.enabled=true

-- Estados: Minas Gerais
INSERT INTO public.estados (
    id,
    criado_em,
    atualizado_em,
    ativo,
    sigla,
    nome,
    codigo_ibge
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'MG',
    'Minas Gerais',
    '31'
WHERE NOT EXISTS (
    SELECT 1 FROM public.estados WHERE sigla = 'MG'
);

-- Cidades: Santa Rita do Sapucaí
INSERT INTO public.cidades (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    codigo_ibge,
    latitude,
    longitude,
    estado_id
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Santa Rita do Sapucaí',
    '3543204',
    -22.2511,
    -45.7056,
    (SELECT id FROM public.estados WHERE sigla = 'MG' LIMIT 1)
WHERE NOT EXISTS (
    SELECT 1 FROM public.cidades WHERE codigo_ibge = '3543204'
);

