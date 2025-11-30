-- Script de Seed: Vacinas do PNI (Programa Nacional de Imunizações) - Escopo Global
-- Cria vacinas do calendário nacional de vacinação - dados globais sem tenant
-- Executado quando app.seed.enabled=true

-- BCG - Tuberculose
INSERT INTO public.vacinas (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    nome_comercial,
    codigo_interno,
    codigo_pni,
    codigo_sus,
    registro_anvisa,
    tipo,
    categoria,
    grupo_alvo,
    fabricante_id,
    via_administracao,
    status,
    disponivel_uso,
    descricao,
    indicacoes,
    observacoes,
    proteger_luz,
    agitar_antes_uso,
    calendario_basico,
    calendario_campanha,
    obrigatoria,
    numero_doses,
    dose_reforco,
    gestante_pode,
    lactante_pode,
    imunocomprometido_pode,
    sincronizar_pni
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'BCG - Id',
    1,
    'VAC-001',
    '01',
    '101001',
    '10267030001',
    1,
    'Rotina',
    'Recém-nascidos',
    (SELECT id FROM public.fabricantes_vacina WHERE nome = 'Instituto Butantan' LIMIT 1),
    10,
    1,
    true,
    'Vacina contra tuberculose. Recomendada para recém-nascidos a partir do nascimento até 1 mês de idade ou até 4 anos 11 meses e 29 dias',
    'Proteção contra formas graves de tuberculose, principalmente meningite tuberculosa e tuberculose miliar',
    'Aplicação intradérmica. Cicatriz característica após aplicação. Parte do calendário básico de vacinação.',
    false,
    false,
    true,   -- calendario_basico
    false,  -- calendario_campanha
    true,   -- obrigatoria
    1,      -- numero_doses
    false,  -- dose_reforco
    true,   -- gestante_pode
    true,   -- lactante_pode
    true,   -- imunocomprometido_pode
    true    -- sincronizar_pni
WHERE NOT EXISTS (SELECT 1 FROM public.vacinas WHERE codigo_pni = '01' OR codigo_sus = '101001');

-- Hepatite B
INSERT INTO public.vacinas (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    nome_comercial,
    codigo_interno,
    codigo_pni,
    codigo_sus,
    registro_anvisa,
    tipo,
    categoria,
    grupo_alvo,
    fabricante_id,
    via_administracao,
    status,
    disponivel_uso,
    descricao,
    indicacoes,
    observacoes,
    proteger_luz,
    agitar_antes_uso,
    calendario_basico,
    calendario_campanha,
    obrigatoria,
    numero_doses,
    dose_reforco,
    gestante_pode,
    lactante_pode,
    imunocomprometido_pode,
    sincronizar_pni
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Hepatite B',
    'Engerix-B',
    'VAC-002',
    '02',
    '101002',
    '10267030002',
    2,
    'Rotina',
    'Recém-nascidos, crianças, adolescentes e adultos',
    (SELECT id FROM public.fabricantes_vacina WHERE nome LIKE '%GlaxoSmithKline%' LIMIT 1),
    7,
    1,
    true,
    'Vacina contra hepatite B. Recomendada para recém-nascidos nas primeiras 12 horas de vida e demais faixas etárias',
    'Prevenção da hepatite B e suas complicações, incluindo cirrose e câncer de fígado',
    'Esquema de 3 doses (0, 1 e 6 meses). Aplicação intramuscular no músculo deltoide. Parte do calendário básico.',
    false,
    false,
    true,   -- calendario_basico
    false,  -- calendario_campanha
    true,   -- obrigatoria
    1,      -- numero_doses
    false,  -- dose_reforco
    true,   -- gestante_pode
    true,   -- lactante_pode
    true,   -- imunocomprometido_pode
    true    -- sincronizar_pni
WHERE NOT EXISTS (SELECT 1 FROM public.vacinas WHERE codigo_pni = '02' OR codigo_sus = '101002');

-- Pentavalente (DTP + Hib + Hepatite B)
INSERT INTO public.vacinas (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    nome_comercial,
    codigo_interno,
    codigo_pni,
    codigo_sus,
    registro_anvisa,
    tipo,
    categoria,
    grupo_alvo,
    fabricante_id,
    via_administracao,
    status,
    disponivel_uso,
    descricao,
    indicacoes,
    observacoes,
    proteger_luz,
    agitar_antes_uso,
    calendario_basico,
    calendario_campanha,
    obrigatoria,
    numero_doses,
    dose_reforco,
    gestante_pode,
    lactante_pode,
    imunocomprometido_pode,
    sincronizar_pni
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Pentavalente',
    'DTP + Hib + Hepatite B',
    'VAC-003',
    '03',
    '101003',
    '10267030003',
    3,
    'Rotina',
    'Crianças de 2, 4 e 6 meses',
    (SELECT id FROM public.fabricantes_vacina WHERE nome = 'Instituto Butantan' LIMIT 1),
    7,
    1,
    true,
    'Vacina combinada contra difteria, tétano, coqueluche, Haemophilus influenzae tipo b (Hib) e hepatite B',
    'Proteção contra difteria, tétano, coqueluche, meningite e outras infecções por Hib, e hepatite B',
    'Esquema de 3 doses aos 2, 4 e 6 meses de idade. Substitui a vacina tetravalente. Parte do calendário básico.',
    false,
    false,
    true,   -- calendario_basico
    false,  -- calendario_campanha
    true,   -- obrigatoria
    1,      -- numero_doses
    false,  -- dose_reforco
    true,   -- gestante_pode
    true,   -- lactante_pode
    true,   -- imunocomprometido_pode
    true    -- sincronizar_pni
WHERE NOT EXISTS (SELECT 1 FROM public.vacinas WHERE codigo_pni = '03' OR codigo_sus = '101003');

-- Poliomielite (VIP)
INSERT INTO public.vacinas (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    nome_comercial,
    codigo_interno,
    codigo_pni,
    codigo_sus,
    registro_anvisa,
    tipo,
    categoria,
    grupo_alvo,
    fabricante_id,
    via_administracao,
    status,
    disponivel_uso,
    descricao,
    indicacoes,
    observacoes,
    proteger_luz,
    agitar_antes_uso,
    calendario_basico,
    calendario_campanha,
    obrigatoria,
    numero_doses,
    dose_reforco,
    gestante_pode,
    lactante_pode,
    imunocomprometido_pode,
    sincronizar_pni
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'VIP - Poliomielite Inativada',
    8,
    'VAC-004',
    '04',
    '101004',
    '10267030004',
    8,
    'Rotina',
    'Crianças de 2, 4 e 6 meses',
    (SELECT id FROM public.fabricantes_vacina WHERE nome LIKE '%Sanofi%' LIMIT 1),
    7,
    1,
    true,
    'Vacina inativada contra poliomielite',
    'Prevenção da poliomielite (paralisia infantil)',
    'Esquema de 3 doses aos 2, 4 e 6 meses, com reforço aos 15 meses. Parte do calendário básico.',
    false,
    false,
    true,   -- calendario_basico
    false,  -- calendario_campanha
    true,   -- obrigatoria
    1,      -- numero_doses
    false,  -- dose_reforco
    true,   -- gestante_pode
    true,   -- lactante_pode
    true,   -- imunocomprometido_pode
    true    -- sincronizar_pni
WHERE NOT EXISTS (SELECT 1 FROM public.vacinas WHERE codigo_pni = '04' OR codigo_sus = '101004');

-- Rotavírus
INSERT INTO public.vacinas (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    nome_comercial,
    codigo_interno,
    codigo_pni,
    codigo_sus,
    registro_anvisa,
    tipo,
    categoria,
    grupo_alvo,
    fabricante_id,
    via_administracao,
    status,
    disponivel_uso,
    descricao,
    indicacoes,
    observacoes,
    proteger_luz,
    agitar_antes_uso,
    calendario_basico,
    calendario_campanha,
    obrigatoria,
    numero_doses,
    dose_reforco,
    gestante_pode,
    lactante_pode,
    imunocomprometido_pode,
    sincronizar_pni
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Rotavírus Humano',
    'Rotarix',
    'VAC-005',
    '05',
    '101005',
    '10267030005',
    10,
    'Rotina',
    'Crianças de 2 e 4 meses',
    (SELECT id FROM public.fabricantes_vacina WHERE nome LIKE '%GlaxoSmithKline%' LIMIT 1),
    1,
    1,
    true,
    'Vacina oral contra rotavírus',
    'Prevenção de gastroenterite grave por rotavírus, principal causa de diarreia grave em crianças',
    'Esquema de 2 doses aos 2 e 4 meses. Máximo até 6 meses e 14 dias. Parte do calendário básico.',
    false,
    false,
    true,   -- calendario_basico
    false,  -- calendario_campanha
    true,   -- obrigatoria
    1,      -- numero_doses
    false,  -- dose_reforco
    true,   -- gestante_pode
    true,   -- lactante_pode
    true,   -- imunocomprometido_pode
    true    -- sincronizar_pni
WHERE NOT EXISTS (SELECT 1 FROM public.vacinas WHERE codigo_pni = '05' OR codigo_sus = '101005');

-- Pneumocócica 10-valente
INSERT INTO public.vacinas (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    nome_comercial,
    codigo_interno,
    codigo_pni,
    codigo_sus,
    registro_anvisa,
    tipo,
    categoria,
    grupo_alvo,
    fabricante_id,
    via_administracao,
    status,
    disponivel_uso,
    descricao,
    indicacoes,
    observacoes,
    proteger_luz,
    agitar_antes_uso,
    calendario_basico,
    calendario_campanha,
    obrigatoria,
    numero_doses,
    dose_reforco,
    gestante_pode,
    lactante_pode,
    imunocomprometido_pode,
    sincronizar_pni
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Pneumocócica 10-valente',
    'Synflorix',
    'VAC-006',
    '06',
    '101006',
    '10267030006',
    14,
    'Rotina',
    'Crianças de 2, 4 e 12 meses',
    (SELECT id FROM public.fabricantes_vacina WHERE nome LIKE '%GlaxoSmithKline%' LIMIT 1),
    7,
    1,
    true,
    'Vacina contra pneumococo (10 sorotipos)',
    'Prevenção de pneumonia, meningite, otite média e outras infecções por pneumococo',
    'Esquema de 2 doses aos 2 e 4 meses, com reforço aos 12 meses. Parte do calendário básico.',
    false,
    false,
    true,   -- calendario_basico
    false,  -- calendario_campanha
    true,   -- obrigatoria
    1,      -- numero_doses
    false,  -- dose_reforco
    true,   -- gestante_pode
    true,   -- lactante_pode
    true,   -- imunocomprometido_pode
    true    -- sincronizar_pni
WHERE NOT EXISTS (SELECT 1 FROM public.vacinas WHERE codigo_pni = '06' OR codigo_sus = '101006');

-- Meningocócica C
INSERT INTO public.vacinas (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    nome_comercial,
    codigo_interno,
    codigo_pni,
    codigo_sus,
    registro_anvisa,
    tipo,
    categoria,
    grupo_alvo,
    fabricante_id,
    via_administracao,
    status,
    disponivel_uso,
    descricao,
    indicacoes,
    observacoes,
    proteger_luz,
    agitar_antes_uso,
    calendario_basico,
    calendario_campanha,
    obrigatoria,
    numero_doses,
    dose_reforco,
    gestante_pode,
    lactante_pode,
    imunocomprometido_pode,
    sincronizar_pni
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Meningocócica C',
    'MenC',
    'VAC-007',
    '07',
    '101007',
    '10267030007',
    11,
    'Rotina',
    'Crianças de 3, 5 e 12 meses',
    (SELECT id FROM public.fabricantes_vacina WHERE nome = 'Instituto Butantan' LIMIT 1),
    7,
    1,
    true,
    'Vacina contra meningite meningocócica do sorogrupo C',
    'Prevenção de meningite e outras infecções graves causadas por Neisseria meningitidis sorogrupo C',
    'Esquema de 2 doses aos 3 e 5 meses, com reforço aos 12 meses. Parte do calendário básico.',
    false,
    false,
    true,   -- calendario_basico
    false,  -- calendario_campanha
    true,   -- obrigatoria
    1,      -- numero_doses
    false,  -- dose_reforco
    true,   -- gestante_pode
    true,   -- lactante_pode
    true,   -- imunocomprometido_pode
    true    -- sincronizar_pni
WHERE NOT EXISTS (SELECT 1 FROM public.vacinas WHERE codigo_pni = '07' OR codigo_sus = '101007');

-- Febre Amarela
INSERT INTO public.vacinas (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    nome_comercial,
    codigo_interno,
    codigo_pni,
    codigo_sus,
    registro_anvisa,
    tipo,
    categoria,
    grupo_alvo,
    fabricante_id,
    via_administracao,
    status,
    disponivel_uso,
    descricao,
    indicacoes,
    observacoes,
    proteger_luz,
    agitar_antes_uso,
    calendario_basico,
    calendario_campanha,
    obrigatoria,
    numero_doses,
    dose_reforco,
    gestante_pode,
    lactante_pode,
    imunocomprometido_pode,
    sincronizar_pni
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Febre Amarela',
    'FA',
    'VAC-008',
    '08',
    '101008',
    '10267030008',
    17,
    'Rotina',
    'Crianças de 9 meses e adultos',
    (SELECT id FROM public.fabricantes_vacina WHERE nome LIKE '%Bio-Manguinhos%' LIMIT 1),
    9,
    1,
    true,
    'Vacina contra febre amarela',
    'Prevenção da febre amarela. Obrigatória em áreas de risco',
    'Dose única aos 9 meses de idade, com reforço aos 4 anos. Para adultos, dose única válida por toda a vida. Parte do calendário básico.',
    false,
    false,
    true,   -- calendario_basico
    false,  -- calendario_campanha
    true,   -- obrigatoria
    1,      -- numero_doses
    false,  -- dose_reforco
    true,   -- gestante_pode
    true,   -- lactante_pode
    true,   -- imunocomprometido_pode
    true    -- sincronizar_pni
WHERE NOT EXISTS (SELECT 1 FROM public.vacinas WHERE codigo_pni = '08' OR codigo_sus = '101008');

-- Tríplice Viral (Sarampo, Caxumba e Rubéola)
INSERT INTO public.vacinas (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    nome_comercial,
    codigo_interno,
    codigo_pni,
    codigo_sus,
    registro_anvisa,
    tipo,
    categoria,
    grupo_alvo,
    fabricante_id,
    via_administracao,
    status,
    disponivel_uso,
    descricao,
    indicacoes,
    observacoes,
    proteger_luz,
    agitar_antes_uso,
    calendario_basico,
    calendario_campanha,
    obrigatoria,
    numero_doses,
    dose_reforco,
    gestante_pode,
    lactante_pode,
    imunocomprometido_pode,
    sincronizar_pni
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Tríplice Viral',
    'SCR',
    'VAC-009',
    '09',
    '101009',
    '10267030009',
    18,
    'Rotina',
    'Crianças de 12 meses e 15 meses',
    (SELECT id FROM public.fabricantes_vacina WHERE nome LIKE '%Bio-Manguinhos%' LIMIT 1),
    9,
    1,
    true,
    'Vacina contra sarampo, caxumba e rubéola',
    'Prevenção de sarampo, caxumba e rubéola',
    'Primeira dose aos 12 meses e segunda dose (Tetra Viral) aos 15 meses. Parte do calendário básico.',
    false,
    false,
    true,   -- calendario_basico
    false,  -- calendario_campanha
    true,   -- obrigatoria
    1,      -- numero_doses
    false,  -- dose_reforco
    true,   -- gestante_pode
    true,   -- lactante_pode
    true,   -- imunocomprometido_pode
    true    -- sincronizar_pni
WHERE NOT EXISTS (SELECT 1 FROM public.vacinas WHERE codigo_pni = '09' OR codigo_sus = '101009');

-- Influenza (Gripe)
INSERT INTO public.vacinas (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    nome_comercial,
    codigo_interno,
    codigo_pni,
    codigo_sus,
    registro_anvisa,
    tipo,
    categoria,
    grupo_alvo,
    fabricante_id,
    via_administracao,
    status,
    disponivel_uso,
    descricao,
    indicacoes,
    observacoes,
    proteger_luz,
    agitar_antes_uso,
    calendario_basico,
    calendario_campanha,
    obrigatoria,
    numero_doses,
    dose_reforco,
    gestante_pode,
    lactante_pode,
    imunocomprometido_pode,
    sincronizar_pni
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Influenza',
    'Gripe',
    'VAC-010',
    '10',
    '101010',
    '10267030010',
    35,
    'Campanha',
    'Crianças de 6 meses a 5 anos, gestantes, idosos, profissionais de saúde, pessoas com comorbidades',
    (SELECT id FROM public.fabricantes_vacina WHERE nome = 'Instituto Butantan' LIMIT 1),
    7,
    1,
    true,
    'Vacina contra influenza (gripe)',
    'Prevenção da gripe e suas complicações. Atualizada anualmente conforme cepas circulantes',
    'Dose anual durante campanha de vacinação. Compõe a campanha nacional de vacinação contra gripe.',
    false,
    false,
    true,   -- calendario_basico
    false,  -- calendario_campanha
    true,   -- obrigatoria
    1,      -- numero_doses
    false,  -- dose_reforco
    true,   -- gestante_pode
    true,   -- lactante_pode
    true,   -- imunocomprometido_pode
    true    -- sincronizar_pni
WHERE NOT EXISTS (SELECT 1 FROM public.vacinas WHERE codigo_pni = '10' OR codigo_sus = '101010');

