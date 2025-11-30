-- Script de Seed: Fabricantes de Vacinas (Escopo Global)
-- Cria fabricantes reais de vacinas - dados globais sem tenant
-- Executado quando app.seed.enabled=true

-- Instituto Butantan
INSERT INTO public.fabricantes_vacina (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    cnpj,
    pais,
    estado,
    cidade,
    telefone,
    email,
    site,
    registro_anvisa,
    registro_ms,
    observacoes
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Instituto Butantan',
    '60762411000104',
    'Brasil',
    'SP',
    'São Paulo',
    '(11) 3726-7222',
    'contato@butantan.gov.br',
    'https://www.butantan.gov.br',
    '10267030001',
    'MS-001',
    'Principal produtor público de vacinas do Brasil. Produz vacinas para o PNI, incluindo CoronaVac, DTP, DT, dT, hepatite A e B, entre outras.'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_vacina WHERE cnpj = '60762411000104' OR nome = 'Instituto Butantan');

-- Bio-Manguinhos / Fiocruz
INSERT INTO public.fabricantes_vacina (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    cnpj,
    pais,
    estado,
    cidade,
    telefone,
    email,
    site,
    registro_anvisa,
    registro_ms,
    observacoes
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Bio-Manguinhos / Fiocruz',
    '02835657000130',
    'Brasil',
    'RJ',
    'Rio de Janeiro',
    '(21) 3882-9000',
    'contato@fiocruz.br',
    'https://www.bio.fiocruz.br',
    '10267030002',
    'MS-002',
    'Instituto de Tecnologia em Imunobiológicos da Fundação Oswaldo Cruz. Principal produtor público de vacinas. Produz vacinas para o PNI como febre amarela, tríplice viral, entre outras.'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_vacina WHERE cnpj = '02835657000130' OR nome LIKE '%Bio-Manguinhos%');

-- Pfizer do Brasil
INSERT INTO public.fabricantes_vacina (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    cnpj,
    pais,
    estado,
    cidade,
    telefone,
    email,
    site,
    registro_anvisa,
    registro_ms,
    observacoes
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Pfizer do Brasil',
    '43814628000107',
    'Brasil',
    'SP',
    'Guarulhos',
    '(11) 3090-4900',
    'contato@pfizer.com',
    'https://www.pfizer.com.br',
    '10267030003',
    NULL,
    'Fabricante multinacional. Produz vacina contra COVID-19 (Comirnaty), Prevenar 13 (pneumocócica), entre outras.'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_vacina WHERE cnpj = '43814628000107' OR nome LIKE '%Pfizer%');

-- AstraZeneca do Brasil
INSERT INTO public.fabricantes_vacina (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    cnpj,
    pais,
    estado,
    cidade,
    telefone,
    email,
    site,
    registro_anvisa,
    registro_ms,
    observacoes
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'AstraZeneca do Brasil',
    '60651863000160',
    'Brasil',
    'SP',
    'Cotia',
    '(11) 4618-8500',
    'contato@astrazeneca.com',
    'https://www.astrazeneca.com.br',
    '10267030004',
    NULL,
    'Fabricante multinacional. Produz vacina contra COVID-19 (AZD1222) e outras vacinas.'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_vacina WHERE cnpj = '60651863000160' OR nome LIKE '%AstraZeneca%');

-- GlaxoSmithKline (GSK)
INSERT INTO public.fabricantes_vacina (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    cnpj,
    pais,
    estado,
    cidade,
    telefone,
    email,
    site,
    registro_anvisa,
    registro_ms,
    observacoes
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'GlaxoSmithKline Brasil Ltda',
    '58376966000121',
    'Brasil',
    'SP',
    'Rio de Janeiro',
    '(21) 2529-4000',
    'contato@gsk.com',
    'https://www.gsk.com.br',
    '10267030005',
    NULL,
    'Fabricante multinacional. Produz vacinas como Infanrix (DTPa), Rotarix, Engerix-B (hepatite B), entre outras.'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_vacina WHERE cnpj = '58376966000121' OR nome LIKE '%GlaxoSmithKline%');

-- Sanofi Pasteur
INSERT INTO public.fabricantes_vacina (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    cnpj,
    pais,
    estado,
    cidade,
    telefone,
    email,
    site,
    registro_anvisa,
    registro_ms,
    observacoes
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Sanofi Pasteur Ltda',
    '32293159000118',
    'Brasil',
    'SP',
    'São Paulo',
    '(11) 3751-6000',
    'contato@sanofi.com',
    'https://www.sanofi.com.br',
    '10267030006',
    NULL,
    'Fabricante multinacional. Produz vacinas como DTPa, hepatite A e B, raiva, influenza, entre outras.'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_vacina WHERE cnpj = '32293159000118' OR nome LIKE '%Sanofi%');

-- Merck Sharp & Dohme (MSD)
INSERT INTO public.fabricantes_vacina (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    cnpj,
    pais,
    estado,
    cidade,
    telefone,
    email,
    site,
    registro_anvisa,
    registro_ms,
    observacoes
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Merck Sharp & Dohme Farmacêutica Ltda',
    '33899907000185',
    'Brasil',
    'SP',
    'São Paulo',
    '(11) 4689-8000',
    'contato@msd.com',
    'https://www.msd.com.br',
    '10267030007',
    NULL,
    'Fabricante multinacional. Produz vacinas como Gardasil (HPV), Zostavax (herpes zoster), ProQuad (sarampo, caxumba, rubéola e varicela), entre outras.'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_vacina WHERE cnpj = '33899907000185' OR nome LIKE '%Merck%');

