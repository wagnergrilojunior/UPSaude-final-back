-- Script de Seed: Fabricantes de Equipamentos Médicos (Escopo Global)
-- Cria fabricantes reais de equipamentos médicos - dados globais sem tenant
-- Executado quando app.seed.enabled=true

-- GE Healthcare
INSERT INTO public.fabricantes_equipamento (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    cnpj,
    pais,
    estado,
    cidade,
    endereco,
    telefone,
    email,
    site,
    registro_anvisa,
    observacoes
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'GE Healthcare do Brasil Ltda',
    '06272962000126',
    'Brasil',
    'SP',
    'São Paulo',
    'Av. Engenheiro Luís Carlos Berrini, 1056',
    '(11) 5501-3700',
    'contato@ge.com',
    'https://www.gehealthcare.com.br',
    '80048610001',
    'Fabricante multinacional de equipamentos médicos de imagem, monitoramento e diagnóstico'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '06272962000126' OR nome LIKE '%GE Healthcare%');

-- Philips
INSERT INTO public.fabricantes_equipamento (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    cnpj,
    pais,
    estado,
    cidade,
    endereco,
    telefone,
    email,
    site,
    registro_anvisa,
    observacoes
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Philips do Brasil Ltda',
    '61080724000147',
    'Brasil',
    'SP',
    'São Paulo',
    'Av. Dr. Cardoso de Melo, 1450',
    '(11) 2125-2222',
    'contato@philips.com',
    'https://www.philips.com.br',
    '80048610002',
    'Fabricante multinacional de equipamentos médicos, sistemas de imagem e monitoramento'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '61080724000147' OR nome LIKE '%Philips%');

-- Mindray
INSERT INTO public.fabricantes_equipamento (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    cnpj,
    pais,
    estado,
    cidade,
    endereco,
    telefone,
    email,
    site,
    registro_anvisa,
    observacoes
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Mindray Medical Brasil Ltda',
    '14959305000104',
    'Brasil',
    'SP',
    'São Paulo',
    'Rua Funchal, 538',
    '(11) 3040-5500',
    'contato@mindray.com',
    'https://www.mindray.com.br',
    '80048610003',
    'Fabricante de equipamentos médicos, monitores multiparamétricos, ventiladores e ultrassons'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '14959305000104' OR nome LIKE '%Mindray%');

-- Draeger
INSERT INTO public.fabricantes_equipamento (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    cnpj,
    pais,
    estado,
    cidade,
    endereco,
    telefone,
    email,
    site,
    registro_anvisa,
    observacoes
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Dräger Medical Brasil Ltda',
    '44141070000163',
    'Brasil',
    'SP',
    'São Paulo',
    'Av. Juscelino Kubitschek, 1450',
    '(11) 3039-5800',
    'contato@draeger.com',
    'https://www.draeger.com.br',
    '80048610004',
    'Fabricante alemão de equipamentos médicos, ventiladores pulmonares e monitores'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '44141070000163' OR nome LIKE '%Dräger%');

-- Medtronic
INSERT INTO public.fabricantes_equipamento (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    cnpj,
    pais,
    estado,
    cidade,
    endereco,
    telefone,
    email,
    site,
    registro_anvisa,
    observacoes
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Medtronic Brasil Ltda',
    '01554909000190',
    'Brasil',
    'SP',
    'São Paulo',
    'Av. Engenheiro Luís Carlos Berrini, 1297',
    '(11) 5508-5000',
    'contato@medtronic.com',
    'https://www.medtronic.com.br',
    '80048610005',
    'Fabricante multinacional de dispositivos médicos, monitores e equipamentos para terapia intensiva'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '01554909000190' OR nome LIKE '%Medtronic%');

-- Bionexo
INSERT INTO public.fabricantes_equipamento (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    cnpj,
    pais,
    estado,
    cidade,
    endereco,
    telefone,
    email,
    site,
    registro_anvisa,
    observacoes
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Bionexo Tecnologia em Saúde Ltda',
    '05115972000109',
    'Brasil',
    'SP',
    'São Paulo',
    'Rua Gomes de Carvalho, 1996',
    '(11) 3034-3400',
    'contato@bionexo.com',
    'https://www.bionexo.com.br',
    '80048610006',
    'Empresa brasileira especializada em soluções e equipamentos para gestão de saúde'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '05115972000109' OR nome LIKE '%Bionexo%');

