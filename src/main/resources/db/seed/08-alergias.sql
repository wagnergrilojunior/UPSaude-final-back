-- Script de Seed: Alergias (Escopo Global)
-- Cria alergias comuns conforme catálogo médico - dados globais sem tenant
-- Executado quando app.seed.enabled=true

-- Penicilina (Medicamento)
INSERT INTO public.alergias (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    nome_cientifico,
    codigo_interno,
    tipo_alergia,
    categoria,
    subcategoria,
    codigo_cid,
    alergia_comum,
    alergia_grave,
    descricao,
    substancias_relacionadas,
    observacoes,
    epinefrina_necessaria
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Penicilina',
    'Penicillium',
    'ALG-MED-001',
    1,
    'Antibiótico Beta-lactâmico',
    'Penicilina',
    'Z88.0',
    true,
    true,
    'Alergia a penicilina e seus derivados. Reação alérgica comum que pode causar desde urticária até anafilaxia',
    'Ampicilina, Amoxicilina, Cloxacilina, Oxacilina, todas as penicilinas',
    'Alergia comum e importante. Sempre verificar antes de prescrever antibióticos beta-lactâmicos. Alternativas: macrolídeos, quinolonas.',
    true
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-MED-001' OR nome = 'Penicilina');

-- Amoxicilina (Medicamento)
INSERT INTO public.alergias (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    nome_cientifico,
    codigo_interno,
    tipo_alergia,
    categoria,
    subcategoria,
    codigo_cid,
    alergia_comum,
    alergia_grave,
    descricao,
    substancias_relacionadas,
    observacoes,
    epinefrina_necessaria
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Amoxicilina',
    NULL,
    'ALG-MED-002',
    1,
    'Antibiótico Beta-lactâmico',
    'Penicilina',
    'Z88.0',
    true,
    true,
    'Alergia a amoxicilina, derivado da penicilina',
    'Penicilina, Ampicilina, todas as penicilinas',
    'Reação cruzada com penicilina. Usar com cautela em pacientes alérgicos à penicilina.',
    true
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-MED-002' OR nome = 'Amoxicilina');


-- Lactose (Alimento)
INSERT INTO public.alergias (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    nome_cientifico,
    codigo_interno,
    tipo_alergia,
    categoria,
    subcategoria,
    codigo_cid,
    alergia_comum,
    alergia_grave,
    descricao,
    substancias_relacionadas,
    observacoes,
    epinefrina_necessaria
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Lactose',
    'Lactose',
    'ALG-ALI-001',
    2,
    'Intolerância Alimentar',
    'Açúcares',
    'E73.9',
    true,
    false,
    'Intolerância à lactose. Dificuldade em digerir a lactose, açúcar presente no leite e derivados',
    'Leite, queijo, iogurte, manteiga, derivados lácteos',
    'Diferente de alergia ao leite (proteína). Intolerância à lactose é muito comum.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-ALI-001' OR nome = 'Lactose');


-- Amendoim (Alimento)
INSERT INTO public.alergias (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    nome_cientifico,
    codigo_interno,
    tipo_alergia,
    categoria,
    subcategoria,
    codigo_cid,
    alergia_comum,
    alergia_grave,
    descricao,
    substancias_relacionadas,
    observacoes,
    epinefrina_necessaria
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Amendoim',
    'Arachis hypogaea',
    'ALG-ALI-002',
    2,
    'Alergia Alimentar',
    'Oleaginosas',
    'T78.1',
    true,
    true,
    'Alergia ao amendoim. Pode causar reações graves incluindo anafilaxia',
    'Amendoim, pasta de amendoim, óleo de amendoim, produtos que contêm amendoim',
    'Alergia grave e comum. Requer cuidado extremo com alimentos processados que podem conter traços.',
    true
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-ALI-002' OR nome = 'Amendoim');


-- Ovo (Alimento)
INSERT INTO public.alergias (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    nome_cientifico,
    codigo_interno,
    tipo_alergia,
    categoria,
    subcategoria,
    codigo_cid,
    alergia_comum,
    alergia_grave,
    descricao,
    substancias_relacionadas,
    observacoes,
    epinefrina_necessaria
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Ovo',
    NULL,
    'ALG-ALI-003',
    2,
    'Alergia Alimentar',
    'Proteínas Animais',
    'T78.1',
    true,
    false,
    'Alergia ao ovo. Reação alérgica às proteínas do ovo',
    'Ovo inteiro, clara de ovo, gema de ovo, produtos que contêm ovo (bolos, massas, maionese)',
    'Alergia comum em crianças. Muitas vezes se resolve com a idade.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-ALI-003' OR nome = 'Ovo');


-- Ácaros (Inalante)
INSERT INTO public.alergias (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    nome_cientifico,
    codigo_interno,
    tipo_alergia,
    categoria,
    subcategoria,
    codigo_cid,
    alergia_comum,
    alergia_grave,
    descricao,
    substancias_relacionadas,
    observacoes,
    epinefrina_necessaria
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Ácaros',
    'Dermatophagoides',
    'ALG-INAL-001',
    3,
    'Alergia Respiratória',
    'Ácaros da Poeira',
    'J30.1',
    true,
    false,
    'Alergia a ácaros da poeira doméstica. Causa comum de rinite alérgica e asma',
    'Poeira doméstica, colchões, travesseiros, cortinas, tapetes',
    'Muito comum. Medidas ambientais são importantes: capas antiácaros, limpeza frequente, umidade controlada.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-INAL-001' OR nome = 'Ácaros');


-- Látex (Contato)
INSERT INTO public.alergias (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    nome_cientifico,
    codigo_interno,
    tipo_alergia,
    categoria,
    subcategoria,
    codigo_cid,
    alergia_comum,
    alergia_grave,
    descricao,
    substancias_relacionadas,
    observacoes,
    epinefrina_necessaria
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Látex',
    'Hevea brasiliensis',
    'ALG-CONT-001',
    6,
    'Alergia de Contato',
    'Material Natural',
    'T78.4',
    true,
    true,
    'Alergia ao látex natural. Pode causar reações graves incluindo anafilaxia',
    'Luvas de látex, preservativos, balões, produtos de borracha natural',
    'Importante em ambiente hospitalar. Usar luvas sem látex. Reação cruzada com algumas frutas (banana, abacate).',
    true
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-CONT-001' OR nome = 'Látex');


-- Picada de Abelha/Vespa (Inseto)
INSERT INTO public.alergias (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    nome_cientifico,
    codigo_interno,
    tipo_alergia,
    categoria,
    subcategoria,
    codigo_cid,
    alergia_comum,
    alergia_grave,
    descricao,
    substancias_relacionadas,
    observacoes,
    epinefrina_necessaria
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Picada de Abelha/Vespa',
    'Hymenoptera',
    'ALG-INS-001',
    5,
    'Alergia a Veneno',
    'Himenópteros',
    'T63.4',
    false,
    true,
    'Alergia ao veneno de abelhas, vespas e marimbondos. Reação pode ser grave e fatal',
    'Abelha, vespa, marimbondo, formiga-de-fogo',
    'Alergia grave que pode causar anafilaxia fatal. Requer tratamento de emergência imediato. Indicado kit de epinefrina.',
    true
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-INS-001' OR nome = 'Picada de Abelha/Vespa');


