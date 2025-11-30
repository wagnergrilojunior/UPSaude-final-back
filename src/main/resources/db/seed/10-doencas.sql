-- Script de Seed: Doenças (Escopo Global)
-- Cria catálogo de doenças comuns - dados globais sem tenant
-- Depende de: CID-10 (07-cid-doencas.sql)
-- Executado quando app.seed.enabled=true

-- Hipertensão Arterial
INSERT INTO public.doencas (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    nome_cientifico,
    codigo_interno,
    tipo_doenca,
    gravidade,
    categoria,
    subcategoria,
    codigo_cid_principal,
    doenca_notificavel,
    doenca_transmissivel,
    cronica,
    cid_principal_id,
    descricao,
    causas,
    observacoes
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Hipertensão Arterial',
    'Hypertension',
    'DOE-001',
    9,
    2,
    'Doenças do Aparelho Circulatório',
    'Hipertensão',
    'I10',
    false,
    false,
    true,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'I10' LIMIT 1),
    'Pressão arterial persistentemente elevada. Doença crônica que afeta milhões de pessoas no Brasil',
    'Fatores genéticos, sedentarismo, alimentação inadequada, obesidade, estresse, consumo excessivo de sal e álcool',
    'Principal fator de risco para doenças cardiovasculares. Requer controle contínuo e mudanças no estilo de vida.'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-001' OR nome = 'Hipertensão Arterial');

-- Diabetes Mellitus Tipo 2
INSERT INTO public.doencas (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    nome_cientifico,
    codigo_interno,
    tipo_doenca,
    gravidade,
    categoria,
    subcategoria,
    codigo_cid_principal,
    doenca_notificavel,
    doenca_transmissivel,
    cronica,
    cid_principal_id,
    descricao,
    causas,
    observacoes
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Diabetes Mellitus Tipo 2',
    'Diabetes Mellitus Type 2',
    'DOE-002',
    12,
    2,
    'Doenças Endocrinológicas',
    'Diabetes',
    'E11',
    false,
    false,
    true,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'E11' LIMIT 1),
    'Doença metabólica caracterizada pela resistência à insulina e deficiência relativa de insulina',
    'Genética, obesidade, sedentarismo, alimentação inadequada, idade avançada, histórico familiar',
    'Doença crônica em expansão no Brasil. Requer monitoramento constante da glicemia e controle adequado.'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-002' OR nome = 'Diabetes Mellitus Tipo 2');

-- Tuberculose
INSERT INTO public.doencas (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    nome_cientifico,
    codigo_interno,
    tipo_doenca,
    gravidade,
    categoria,
    subcategoria,
    codigo_cid_principal,
    doenca_notificavel,
    doenca_transmissivel,
    cronica,
    cid_principal_id,
    descricao,
    causas,
    observacoes
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Tuberculose',
    'Mycobacterium tuberculosis',
    'DOE-003',
    3,
    3,
    'Doenças Infecciosas',
    'Tuberculose',
    'A15',
    true,
    true,
    false,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'A15' LIMIT 1),
    'Doença infectocontagiosa causada pela bactéria Mycobacterium tuberculosis, que afeta principalmente os pulmões',
    'Infecção pela bactéria Mycobacterium tuberculosis, transmitida por via aérea através de gotículas',
    'Doença de notificação compulsória. Tratamento diretamente observado (TDO) é fundamental para cura.'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-003' OR nome = 'Tuberculose');

-- Infecção das Vias Aéreas Superiores
INSERT INTO public.doencas (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    nome_cientifico,
    codigo_interno,
    tipo_doenca,
    gravidade,
    categoria,
    subcategoria,
    codigo_cid_principal,
    doenca_notificavel,
    doenca_transmissivel,
    cronica,
    cid_principal_id,
    descricao,
    causas,
    observacoes
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Infecção das Vias Aéreas Superiores',
    'Upper Respiratory Tract Infection',
    'DOE-004',
    3,
    1,
    'Doenças do Aparelho Respiratório',
    'Infecções Respiratórias',
    'J06',
    false,
    true,
    false,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'J06' LIMIT 1),
    'Infecções agudas das vias aéreas superiores, incluindo resfriados e faringites',
    'Vírus (rinovírus, coronavírus, adenovírus), bactérias (Streptococcus, Haemophilus)',
    'Muito comum, especialmente em crianças. Autolimitada na maioria dos casos.'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-004' OR nome = 'Infecção das Vias Aéreas Superiores');

-- Pneumonia
INSERT INTO public.doencas (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    nome_cientifico,
    codigo_interno,
    tipo_doenca,
    gravidade,
    categoria,
    subcategoria,
    codigo_cid_principal,
    doenca_notificavel,
    doenca_transmissivel,
    cronica,
    cid_principal_id,
    descricao,
    causas,
    observacoes
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Pneumonia',
    'Pneumonia',
    'DOE-005',
    3,
    3,
    'Doenças do Aparelho Respiratório',
    'Pneumonia',
    'J18',
    true,
    true,
    false,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'J18' LIMIT 1),
    'Inflamação dos pulmões causada por infecção, resultando em acúmulo de líquido nos alvéolos',
    'Bactérias (Streptococcus pneumoniae, Haemophilus influenzae), vírus, fungos, agentes químicos',
    'Principal causa de morte por infecção em crianças. Requer tratamento antibiótico imediato.'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-005' OR nome = 'Pneumonia');

