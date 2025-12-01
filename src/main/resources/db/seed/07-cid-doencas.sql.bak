-- Script de Seed: CID-10 - Classificação Internacional de Doenças (Escopo Global)
-- Cria códigos CID-10 mais comuns conforme OMS - dados globais sem tenant
-- Executado quando app.seed.enabled=true

-- Capítulo I - Algumas doenças infecciosas e parasitárias (A00-B99)
-- A15 - Tuberculose respiratória, com confirmação bacteriológica e histológica
INSERT INTO public.cid_doencas (
    id,
    criado_em,
    atualizado_em,
    ativo,
    codigo,
    descricao,
    descricao_abreviada,
    categoria,
    subcategoria,
    sexo_restricao,
    idade_minima,
    idade_maxima
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'A15',
    'Tuberculose respiratória, com confirmação bacteriológica e histológica',
    'Tuberculose respiratória confirmada',
    'Doenças Infecciosas',
    'Tuberculose',
    NULL,
    NULL,
    NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A15');

-- I10 - Hipertensão essencial (primária)
INSERT INTO public.cid_doencas (
    id,
    criado_em,
    atualizado_em,
    ativo,
    codigo,
    descricao,
    descricao_abreviada,
    categoria,
    subcategoria,
    sexo_restricao,
    idade_minima,
    idade_maxima
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'I10',
    'Hipertensão essencial (primária)',
    'Hipertensão arterial',
    'Doenças do Aparelho Circulatório',
    'Hipertensão',
    NULL,
    NULL,
    NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'I10');

-- E11 - Diabetes mellitus não-insulino-dependente
INSERT INTO public.cid_doencas (
    id,
    criado_em,
    atualizado_em,
    ativo,
    codigo,
    descricao,
    descricao_abreviada,
    categoria,
    subcategoria,
    sexo_restricao,
    idade_minima,
    idade_maxima
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'E11',
    'Diabetes mellitus não-insulino-dependente',
    'Diabetes tipo 2',
    'Doenças Endocrinológicas',
    'Diabetes',
    NULL,
    NULL,
    NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E11');

-- J06 - Infecções agudas das vias aéreas superiores, de localizações múltiplas e não especificadas
INSERT INTO public.cid_doencas (
    id,
    criado_em,
    atualizado_em,
    ativo,
    codigo,
    descricao,
    descricao_abreviada,
    categoria,
    subcategoria,
    sexo_restricao,
    idade_minima,
    idade_maxima
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'J06',
    'Infecções agudas das vias aéreas superiores, de localizações múltiplas e não especificadas',
    'Infecção vias aéreas superiores',
    'Doenças do Aparelho Respiratório',
    'Infecções Respiratórias',
    NULL,
    NULL,
    NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'J06');

-- J18 - Pneumonia por organismo não especificado
INSERT INTO public.cid_doencas (
    id,
    criado_em,
    atualizado_em,
    ativo,
    codigo,
    descricao,
    descricao_abreviada,
    categoria,
    subcategoria,
    sexo_restricao,
    idade_minima,
    idade_maxima
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'J18',
    'Pneumonia por organismo não especificado',
    'Pneumonia',
    'Doenças do Aparelho Respiratório',
    'Pneumonia',
    NULL,
    NULL,
    NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'J18');

-- K59 - Outros transtornos funcionais do intestino
INSERT INTO public.cid_doencas (
    id,
    criado_em,
    atualizado_em,
    ativo,
    codigo,
    descricao,
    descricao_abreviada,
    categoria,
    subcategoria,
    sexo_restricao,
    idade_minima,
    idade_maxima
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'K59',
    'Outros transtornos funcionais do intestino',
    'Transtorno funcional intestino',
    'Doenças do Aparelho Digestivo',
    'Transtornos Funcionais',
    NULL,
    NULL,
    NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'K59');

-- M79 - Outros transtornos dos tecidos moles, não classificados em outra parte
INSERT INTO public.cid_doencas (
    id,
    criado_em,
    atualizado_em,
    ativo,
    codigo,
    descricao,
    descricao_abreviada,
    categoria,
    subcategoria,
    sexo_restricao,
    idade_minima,
    idade_maxima
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'M79',
    'Outros transtornos dos tecidos moles, não classificados em outra parte',
    'Transtorno tecidos moles',
    'Doenças do Sistema Osteomuscular',
    'Transtornos Tecidos Moles',
    NULL,
    NULL,
    NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'M79');

-- N39 - Outros transtornos do trato urinário
INSERT INTO public.cid_doencas (
    id,
    criado_em,
    atualizado_em,
    ativo,
    codigo,
    descricao,
    descricao_abreviada,
    categoria,
    subcategoria,
    sexo_restricao,
    idade_minima,
    idade_maxima
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'N39',
    'Outros transtornos do trato urinário',
    'Transtorno trato urinário',
    'Doenças do Aparelho Geniturinário',
    'Transtornos Urinários',
    NULL,
    NULL,
    NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'N39');

-- Z00 - Exame médico geral e investigação de pessoas sem queixas ou diagnóstico relatado
INSERT INTO public.cid_doencas (
    id,
    criado_em,
    atualizado_em,
    ativo,
    codigo,
    descricao,
    descricao_abreviada,
    categoria,
    subcategoria,
    sexo_restricao,
    idade_minima,
    idade_maxima
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Z00',
    'Exame médico geral e investigação de pessoas sem queixas ou diagnóstico relatado',
    'Exame médico geral',
    'Fatores que Influenciam o Estado de Saúde',
    'Exames Gerais',
    NULL,
    NULL,
    NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'Z00');

-- Z23 - Necessidade de imunização contra doença bacteriana única
INSERT INTO public.cid_doencas (
    id,
    criado_em,
    atualizado_em,
    ativo,
    codigo,
    descricao,
    descricao_abreviada,
    categoria,
    subcategoria,
    sexo_restricao,
    idade_minima,
    idade_maxima
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Z23',
    'Necessidade de imunização contra doença bacteriana única',
    'Imunização doença bacteriana',
    'Fatores que Influenciam o Estado de Saúde',
    'Imunização',
    NULL,
    NULL,
    NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'Z23');

