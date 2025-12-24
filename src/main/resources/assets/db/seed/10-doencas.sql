-- Script de Seed: Doenças (Escopo Global)
-- Cria catálogo de doenças comuns - dados globais sem tenant
-- Depende de: CID-10 (07-cid-doencas.sql)
-- Gerado automaticamente pelo script generate_doencas.py
-- Executado quando app.seed.enabled=true

-- Hipertensão Arterial
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Hipertensão Arterial', 'Hypertension', 'DOE-001',
    9, 2, 'Doenças do Aparelho Circulatório', 'Hipertensão', 'I10',
    false, false, true,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'I10' LIMIT 1),
    'Pressão arterial persistentemente elevada', 'Fatores genéticos, sedentarismo, alimentação inadequada'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-001' OR nome = 'Hipertensão Arterial');

-- Infarto Agudo do Miocárdio
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Infarto Agudo do Miocárdio', 'Acute Myocardial Infarction', 'DOE-002',
    9, 5, 'Doenças do Aparelho Circulatório', 'Doença Isquêmica do Coração', 'I10',
    false, false, false,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'I10' LIMIT 1),
    'Morte do tecido cardíaco por falta de oxigênio', 'Aterosclerose, trombose coronária'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-002' OR nome = 'Infarto Agudo do Miocárdio');

-- Insuficiência Cardíaca
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Insuficiência Cardíaca', 'Heart Failure', 'DOE-003',
    9, 3, 'Doenças do Aparelho Circulatório', 'Insuficiência Cardíaca', 'I10',
    false, false, true,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'I10' LIMIT 1),
    'Incapacidade do coração de bombear sangue adequadamente', 'Hipertensão, doença coronariana, cardiomiopatias'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-003' OR nome = 'Insuficiência Cardíaca');

-- Arritmia Cardíaca
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Arritmia Cardíaca', 'Cardiac Arrhythmia', 'DOE-004',
    9, 2, 'Doenças do Aparelho Circulatório', 'Arritmias', 'I10',
    false, false, true,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'I10' LIMIT 1),
    'Alteração no ritmo cardíaco', 'Doença cardíaca estrutural, distúrbios eletrolíticos'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-004' OR nome = 'Arritmia Cardíaca');

-- Acidente Vascular Cerebral
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Acidente Vascular Cerebral', 'Stroke', 'DOE-005',
    9, 5, 'Doenças do Aparelho Circulatório', 'AVC', 'I10',
    false, false, false,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'I10' LIMIT 1),
    'Interrupção do fluxo sanguíneo cerebral', 'Hipertensão, aterosclerose, trombose'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-005' OR nome = 'Acidente Vascular Cerebral');

-- Diabetes Mellitus Tipo 2
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Diabetes Mellitus Tipo 2', 'Diabetes Mellitus Type 2', 'DOE-006',
    12, 2, 'Doenças Endocrinológicas', 'Diabetes', 'E11',
    false, false, true,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'E11' LIMIT 1),
    'Doença metabólica caracterizada pela resistência à insulina', 'Genética, obesidade, sedentarismo'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-006' OR nome = 'Diabetes Mellitus Tipo 2');

-- Diabetes Mellitus Tipo 1
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Diabetes Mellitus Tipo 1', 'Diabetes Mellitus Type 1', 'DOE-007',
    12, 2, 'Doenças Endocrinológicas', 'Diabetes', 'E10',
    false, false, true,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'E10' LIMIT 1),
    'Doença autoimune que destrói células beta do pâncreas', 'Autoimunidade, genética'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-007' OR nome = 'Diabetes Mellitus Tipo 1');

-- Hipotireoidismo
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Hipotireoidismo', 'Hypothyroidism', 'DOE-008',
    12, 1, 'Doenças Endocrinológicas', 'Tireoide', 'E03',
    false, false, true,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'E03' LIMIT 1),
    'Deficiência de hormônios tireoidianos', 'Doença autoimune, deficiência de iodo'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-008' OR nome = 'Hipotireoidismo');

-- Hipertireoidismo
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Hipertireoidismo', 'Hyperthyroidism', 'DOE-009',
    12, 2, 'Doenças Endocrinológicas', 'Tireoide', 'E05',
    false, false, true,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'E05' LIMIT 1),
    'Excesso de hormônios tireoidianos', 'Doença de Graves, bócio nodular'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-009' OR nome = 'Hipertireoidismo');

-- Obesidade
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Obesidade', 'Obesity', 'DOE-010',
    13, 2, 'Doenças Metabólicas', 'Obesidade', 'E66',
    false, false, true,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'E66' LIMIT 1),
    'Acúmulo excessivo de gordura corporal', 'Alimentação inadequada, sedentarismo, genética'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-010' OR nome = 'Obesidade');

-- Tuberculose
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Tuberculose', 'Mycobacterium tuberculosis', 'DOE-011',
    3, 3, 'Doenças Infecciosas', 'Tuberculose', 'A15',
    true, true, false,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'A15' LIMIT 1),
    'Doença infectocontagiosa causada por Mycobacterium tuberculosis', 'Infecção bacteriana transmitida por via aérea'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-011' OR nome = 'Tuberculose');

-- Pneumonia
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Pneumonia', 'Pneumonia', 'DOE-012',
    3, 3, 'Doenças do Aparelho Respiratório', 'Pneumonia', 'J18',
    true, true, false,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'J18' LIMIT 1),
    'Inflamação dos pulmões causada por infecção', 'Bactérias, vírus, fungos'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-012' OR nome = 'Pneumonia');

-- Dengue
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Dengue', 'Dengue Fever', 'DOE-013',
    5, 2, 'Doenças Infecciosas', 'Arboviroses', 'A90',
    true, true, false,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'A90' LIMIT 1),
    'Doença viral transmitida por mosquitos', 'Vírus da dengue transmitido por Aedes aegypti'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-013' OR nome = 'Dengue');

-- Chikungunya
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Chikungunya', 'Chikungunya', 'DOE-014',
    5, 2, 'Doenças Infecciosas', 'Arboviroses', 'A92',
    true, true, false,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'A92' LIMIT 1),
    'Doença viral transmitida por mosquitos', 'Vírus chikungunya transmitido por Aedes'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-014' OR nome = 'Chikungunya');

-- Zika
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Zika', 'Zika Virus Disease', 'DOE-015',
    5, 2, 'Doenças Infecciosas', 'Arboviroses', 'A92',
    true, true, false,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'A92' LIMIT 1),
    'Doença viral transmitida por mosquitos', 'Vírus zika transmitido por Aedes'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-015' OR nome = 'Zika');

-- COVID-19
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'COVID-19', 'SARS-CoV-2', 'DOE-016',
    5, 3, 'Doenças Infecciosas', 'Coronavírus', 'J18',
    true, true, false,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'J18' LIMIT 1),
    'Doença respiratória causada pelo coronavírus SARS-CoV-2', 'Vírus SARS-CoV-2 transmitido por gotículas'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-016' OR nome = 'COVID-19');

-- Gripe
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Gripe', 'Influenza', 'DOE-017',
    5, 1, 'Doenças do Aparelho Respiratório', 'Influenza', 'J06',
    false, true, false,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'J06' LIMIT 1),
    'Infecção viral aguda do trato respiratório', 'Vírus influenza transmitido por gotículas'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-017' OR nome = 'Gripe');

-- Hepatite A
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Hepatite A', 'Hepatitis A', 'DOE-018',
    5, 2, 'Doenças Infecciosas', 'Hepatites', 'B15',
    true, true, false,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'B15' LIMIT 1),
    'Inflamação do fígado causada pelo vírus da hepatite A', 'Vírus HAV transmitido por via fecal-oral'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-018' OR nome = 'Hepatite A');

-- Hepatite B
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Hepatite B', 'Hepatitis B', 'DOE-019',
    5, 3, 'Doenças Infecciosas', 'Hepatites', 'B16',
    true, true, false,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'B16' LIMIT 1),
    'Inflamação do fígado causada pelo vírus da hepatite B', 'Vírus HBV transmitido por sangue e fluidos corporais'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-019' OR nome = 'Hepatite B');

-- Hepatite C
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Hepatite C', 'Hepatitis C', 'DOE-020',
    5, 3, 'Doenças Infecciosas', 'Hepatites', 'B17',
    true, true, false,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'B17' LIMIT 1),
    'Inflamação do fígado causada pelo vírus da hepatite C', 'Vírus HCV transmitido por sangue'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-020' OR nome = 'Hepatite C');

-- HIV/AIDS
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'HIV/AIDS', 'Human Immunodeficiency Virus', 'DOE-021',
    5, 4, 'Doenças Infecciosas', 'HIV/AIDS', 'B20',
    true, true, true,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'B20' LIMIT 1),
    'Síndrome da imunodeficiência adquirida', 'Vírus HIV transmitido por sangue e fluidos corporais'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-021' OR nome = 'HIV/AIDS');

-- Sífilis
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Sífilis', 'Syphilis', 'DOE-022',
    6, 3, 'Doenças Infecciosas', 'DST', 'A51',
    true, true, false,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'A51' LIMIT 1),
    'Doença sexualmente transmissível causada por Treponema pallidum', 'Bactéria Treponema pallidum transmitida sexualmente'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-022' OR nome = 'Sífilis');

-- Gonorreia
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Gonorreia', 'Gonorrhea', 'DOE-023',
    6, 2, 'Doenças Infecciosas', 'DST', 'A54',
    true, true, false,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'A54' LIMIT 1),
    'Doença sexualmente transmissível causada por Neisseria gonorrhoeae', 'Bactéria Neisseria gonorrhoeae transmitida sexualmente'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-023' OR nome = 'Gonorreia');

-- Hanseníase
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Hanseníase', 'Leprosy', 'DOE-024',
    6, 2, 'Doenças Infecciosas', 'Hanseníase', 'A30',
    true, true, true,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'A30' LIMIT 1),
    'Doença crônica causada por Mycobacterium leprae', 'Bactéria Mycobacterium leprae transmitida por contato'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-024' OR nome = 'Hanseníase');

-- Leishmaniose
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Leishmaniose', 'Leishmaniasis', 'DOE-025',
    4, 3, 'Doenças Infecciosas', 'Leishmaniose', 'B55',
    true, true, false,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'B55' LIMIT 1),
    'Doença parasitária transmitida por flebotomíneos', 'Protozoário Leishmania transmitido por insetos'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-025' OR nome = 'Leishmaniose');

-- Malária
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Malária', 'Malaria', 'DOE-026',
    4, 3, 'Doenças Infecciosas', 'Malária', 'B50',
    true, true, false,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'B50' LIMIT 1),
    'Doença parasitária transmitida por mosquitos Anopheles', 'Protozoário Plasmodium transmitido por mosquitos'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-026' OR nome = 'Malária');

-- Doença de Chagas
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Doença de Chagas', 'Chagas Disease', 'DOE-027',
    4, 3, 'Doenças Infecciosas', 'Doença de Chagas', 'B57',
    true, true, true,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'B57' LIMIT 1),
    'Doença parasitária causada por Trypanosoma cruzi', 'Protozoário Trypanosoma cruzi transmitido por barbeiros'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-027' OR nome = 'Doença de Chagas');

-- Esquistossomose
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Esquistossomose', 'Schistosomiasis', 'DOE-028',
    4, 2, 'Doenças Infecciosas', 'Esquistossomose', 'B65',
    true, true, true,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'B65' LIMIT 1),
    'Doença parasitária causada por Schistosoma', 'Trematódeo Schistosoma transmitido por água contaminada'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-028' OR nome = 'Esquistossomose');

-- Tétano
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Tétano', 'Tetanus', 'DOE-029',
    6, 5, 'Doenças Infecciosas', 'Tétano', 'A35',
    true, false, false,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'A35' LIMIT 1),
    'Doença bacteriana causada por Clostridium tetani', 'Bactéria Clostridium tetani em feridas'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-029' OR nome = 'Tétano');

-- Coqueluche
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Coqueluche', 'Pertussis', 'DOE-030',
    6, 3, 'Doenças Infecciosas', 'Coqueluche', 'A37',
    true, true, false,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'A37' LIMIT 1),
    'Doença respiratória causada por Bordetella pertussis', 'Bactéria Bordetella pertussis transmitida por gotículas'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-030' OR nome = 'Coqueluche');

-- Meningite
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Meningite', 'Meningitis', 'DOE-031',
    6, 5, 'Doenças Infecciosas', 'Meningite', 'A15',
    true, true, false,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'A15' LIMIT 1),
    'Inflamação das meninges causada por bactérias ou vírus', 'Bactérias ou vírus transmitidos por gotículas'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-031' OR nome = 'Meningite');

-- Leptospirose
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Leptospirose', 'Leptospirosis', 'DOE-032',
    6, 3, 'Doenças Infecciosas', 'Leptospirose', 'A27',
    true, true, false,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'A27' LIMIT 1),
    'Doença bacteriana transmitida por animais', 'Bactéria Leptospira transmitida por urina de animais'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-032' OR nome = 'Leptospirose');

-- Asma
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Asma', 'Asthma', 'DOE-033',
    10, 2, 'Doenças do Aparelho Respiratório', 'Asma', 'J18',
    false, false, true,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'J18' LIMIT 1),
    'Doença inflamatória crônica das vias aéreas', 'Alérgenos, poluição, genética'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-033' OR nome = 'Asma');

-- Doença Pulmonar Obstrutiva Crônica
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Doença Pulmonar Obstrutiva Crônica', 'COPD', 'DOE-034',
    10, 3, 'Doenças do Aparelho Respiratório', 'DPOC', 'J18',
    false, false, true,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'J18' LIMIT 1),
    'Doença pulmonar obstrutiva crônica', 'Tabagismo, poluição, exposição ocupacional'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-034' OR nome = 'Doença Pulmonar Obstrutiva Crônica');

-- Bronquite
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Bronquite', 'Bronchitis', 'DOE-035',
    10, 1, 'Doenças do Aparelho Respiratório', 'Bronquite', 'J06',
    false, false, false,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'J06' LIMIT 1),
    'Inflamação dos brônquios', 'Vírus, bactérias, irritantes'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-035' OR nome = 'Bronquite');

-- Rinite Alérgica
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Rinite Alérgica', 'Allergic Rhinitis', 'DOE-036',
    29, 1, 'Doenças do Aparelho Respiratório', 'Rinite', 'J06',
    false, false, true,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'J06' LIMIT 1),
    'Inflamação da mucosa nasal por alergia', 'Alérgenos, pólen, ácaros'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-036' OR nome = 'Rinite Alérgica');

-- Sinusite
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Sinusite', 'Sinusitis', 'DOE-037',
    3, 1, 'Doenças do Aparelho Respiratório', 'Sinusite', 'J06',
    false, false, false,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'J06' LIMIT 1),
    'Inflamação dos seios paranasais', 'Vírus, bactérias, alergias'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-037' OR nome = 'Sinusite');

-- Gastrite
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Gastrite', 'Gastritis', 'DOE-038',
    11, 1, 'Doenças do Aparelho Digestivo', 'Gastrite', 'K59',
    false, false, true,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'K59' LIMIT 1),
    'Inflamação da mucosa gástrica', 'Helicobacter pylori, uso de AINEs, álcool'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-038' OR nome = 'Gastrite');

-- Úlcera Péptica
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Úlcera Péptica', 'Peptic Ulcer', 'DOE-039',
    11, 2, 'Doenças do Aparelho Digestivo', 'Úlcera', 'K59',
    false, false, true,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'K59' LIMIT 1),
    'Lesão na mucosa do estômago ou duodeno', 'Helicobacter pylori, AINEs, estresse'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-039' OR nome = 'Úlcera Péptica');

-- Hepatite Crônica
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Hepatite Crônica', 'Chronic Hepatitis', 'DOE-040',
    11, 3, 'Doenças do Aparelho Digestivo', 'Hepatite', 'B17',
    false, false, true,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'B17' LIMIT 1),
    'Inflamação crônica do fígado', 'Vírus, álcool, medicamentos'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-040' OR nome = 'Hepatite Crônica');

-- Cirrose Hepática
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Cirrose Hepática', 'Liver Cirrhosis', 'DOE-041',
    11, 4, 'Doenças do Aparelho Digestivo', 'Cirrose', 'B17',
    false, false, true,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'B17' LIMIT 1),
    'Fibrose e destruição do tecido hepático', 'Álcool, hepatite viral, esteatose'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-041' OR nome = 'Cirrose Hepática');

-- Doença de Crohn
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Doença de Crohn', 'Crohn Disease', 'DOE-042',
    11, 3, 'Doenças do Aparelho Digestivo', 'DII', 'K59',
    false, false, true,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'K59' LIMIT 1),
    'Doença inflamatória intestinal crônica', 'Autoimunidade, genética'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-042' OR nome = 'Doença de Crohn');

-- Colite Ulcerativa
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Colite Ulcerativa', 'Ulcerative Colitis', 'DOE-043',
    11, 3, 'Doenças do Aparelho Digestivo', 'DII', 'K59',
    false, false, true,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'K59' LIMIT 1),
    'Doença inflamatória intestinal crônica', 'Autoimunidade, genética'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-043' OR nome = 'Colite Ulcerativa');

-- Síndrome do Intestino Irritável
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Síndrome do Intestino Irritável', 'Irritable Bowel Syndrome', 'DOE-044',
    11, 1, 'Doenças do Aparelho Digestivo', 'SII', 'K59',
    false, false, true,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'K59' LIMIT 1),
    'Distúrbio funcional do intestino', 'Estresse, alimentação, genética'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-044' OR nome = 'Síndrome do Intestino Irritável');

-- Refluxo Gastroesofágico
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Refluxo Gastroesofágico', 'GERD', 'DOE-045',
    11, 1, 'Doenças do Aparelho Digestivo', 'Refluxo', 'K59',
    false, false, true,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'K59' LIMIT 1),
    'Retorno do conteúdo gástrico para o esôfago', 'Hérnia hiatal, obesidade, alimentação'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-045' OR nome = 'Refluxo Gastroesofágico');

-- Epilepsia
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Epilepsia', 'Epilepsy', 'DOE-046',
    14, 2, 'Doenças do Sistema Nervoso', 'Epilepsia', 'A15',
    false, false, true,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'A15' LIMIT 1),
    'Transtorno neurológico caracterizado por crises convulsivas', 'Genética, lesões cerebrais, infecções'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-046' OR nome = 'Epilepsia');

-- Doença de Parkinson
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Doença de Parkinson', 'Parkinson Disease', 'DOE-047',
    14, 3, 'Doenças do Sistema Nervoso', 'Parkinson', 'A15',
    false, false, true,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'A15' LIMIT 1),
    'Doença neurodegenerativa progressiva', 'Genética, fatores ambientais'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-047' OR nome = 'Doença de Parkinson');

-- Alzheimer
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Alzheimer', 'Alzheimer Disease', 'DOE-048',
    14, 4, 'Doenças do Sistema Nervoso', 'Alzheimer', 'A15',
    false, false, true,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'A15' LIMIT 1),
    'Doença neurodegenerativa que causa demência', 'Genética, idade, fatores ambientais'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-048' OR nome = 'Alzheimer');

-- Esclerose Múltipla
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Esclerose Múltipla', 'Multiple Sclerosis', 'DOE-049',
    14, 3, 'Doenças do Sistema Nervoso', 'Esclerose Múltipla', 'A15',
    false, false, true,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'A15' LIMIT 1),
    'Doença autoimune que afeta o sistema nervoso central', 'Autoimunidade, genética'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-049' OR nome = 'Esclerose Múltipla');

-- Enxaqueca
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Enxaqueca', 'Migraine', 'DOE-050',
    14, 1, 'Doenças do Sistema Nervoso', 'Cefaleia', 'A15',
    false, false, true,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'A15' LIMIT 1),
    'Cefaleia primária recorrente', 'Genética, fatores desencadeantes'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-050' OR nome = 'Enxaqueca');

-- AVC Isquêmico
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'AVC Isquêmico', 'Ischemic Stroke', 'DOE-051',
    14, 5, 'Doenças do Sistema Nervoso', 'AVC', 'I10',
    false, false, false,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'I10' LIMIT 1),
    'Interrupção do fluxo sanguíneo cerebral por obstrução', 'Trombose, embolia, aterosclerose'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-051' OR nome = 'AVC Isquêmico');

-- AVC Hemorrágico
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'AVC Hemorrágico', 'Hemorrhagic Stroke', 'DOE-052',
    14, 5, 'Doenças do Sistema Nervoso', 'AVC', 'I10',
    false, false, false,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'I10' LIMIT 1),
    'Ruptura de vaso sanguíneo no cérebro', 'Hipertensão, aneurisma, malformações'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-052' OR nome = 'AVC Hemorrágico');

-- Depressão
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Depressão', 'Major Depressive Disorder', 'DOE-053',
    15, 2, 'Transtornos Mentais', 'Depressão', 'F32',
    false, false, true,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'F32' LIMIT 1),
    'Transtorno do humor caracterizado por tristeza persistente', 'Genética, fatores ambientais, estresse'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-053' OR nome = 'Depressão');

-- Transtorno de Ansiedade
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Transtorno de Ansiedade', 'Anxiety Disorder', 'DOE-054',
    15, 2, 'Transtornos Mentais', 'Ansiedade', 'F41',
    false, false, true,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'F41' LIMIT 1),
    'Transtorno caracterizado por ansiedade excessiva', 'Genética, estresse, trauma'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-054' OR nome = 'Transtorno de Ansiedade');

-- Transtorno Bipolar
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Transtorno Bipolar', 'Bipolar Disorder', 'DOE-055',
    15, 3, 'Transtornos Mentais', 'Bipolar', 'F31',
    false, false, true,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'F31' LIMIT 1),
    'Transtorno do humor com episódios de mania e depressão', 'Genética, fatores ambientais'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-055' OR nome = 'Transtorno Bipolar');

-- Esquizofrenia
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Esquizofrenia', 'Schizophrenia', 'DOE-056',
    15, 3, 'Transtornos Mentais', 'Esquizofrenia', 'F20',
    false, false, true,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'F20' LIMIT 1),
    'Transtorno psicótico crônico', 'Genética, fatores ambientais'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-056' OR nome = 'Esquizofrenia');

-- Transtorno de Déficit de Atenção
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Transtorno de Déficit de Atenção', 'ADHD', 'DOE-057',
    15, 1, 'Transtornos Mentais', 'TDAH', 'F90',
    false, false, true,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'F90' LIMIT 1),
    'Transtorno neurodesenvolvimental', 'Genética, fatores ambientais'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-057' OR nome = 'Transtorno de Déficit de Atenção');

-- Artrite Reumatoide
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Artrite Reumatoide', 'Rheumatoid Arthritis', 'DOE-058',
    16, 2, 'Doenças do Sistema Osteomuscular', 'Artrite', 'M79',
    false, false, true,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'M79' LIMIT 1),
    'Doença autoimune que afeta articulações', 'Autoimunidade, genética'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-058' OR nome = 'Artrite Reumatoide');

-- Osteoartrite
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Osteoartrite', 'Osteoarthritis', 'DOE-059',
    16, 2, 'Doenças do Sistema Osteomuscular', 'Osteoartrite', 'M79',
    false, false, true,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'M79' LIMIT 1),
    'Degeneração das articulações', 'Idade, obesidade, trauma'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-059' OR nome = 'Osteoartrite');

-- Lúpus Eritematoso Sistêmico
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Lúpus Eritematoso Sistêmico', 'Systemic Lupus Erythematosus', 'DOE-060',
    25, 3, 'Doenças do Sistema Osteomuscular', 'Lúpus', 'M79',
    false, false, true,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'M79' LIMIT 1),
    'Doença autoimune sistêmica', 'Autoimunidade, genética'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-060' OR nome = 'Lúpus Eritematoso Sistêmico');

-- Fibromialgia
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Fibromialgia', 'Fibromyalgia', 'DOE-061',
    16, 2, 'Doenças do Sistema Osteomuscular', 'Fibromialgia', 'M79',
    false, false, true,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'M79' LIMIT 1),
    'Síndrome de dor crônica generalizada', 'Genética, fatores ambientais'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-061' OR nome = 'Fibromialgia');

-- Gota
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Gota', 'Gout', 'DOE-062',
    16, 2, 'Doenças do Sistema Osteomuscular', 'Gota', 'M79',
    false, false, true,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'M79' LIMIT 1),
    'Artrite por deposição de cristais de ácido úrico', 'Hiperuricemia, genética'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-062' OR nome = 'Gota');

-- Dermatite Atópica
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Dermatite Atópica', 'Atopic Dermatitis', 'DOE-063',
    17, 1, 'Doenças da Pele', 'Dermatite', 'A15',
    false, false, true,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'A15' LIMIT 1),
    'Inflamação crônica da pele', 'Genética, alérgenos'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-063' OR nome = 'Dermatite Atópica');

-- Psoríase
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Psoríase', 'Psoriasis', 'DOE-064',
    17, 2, 'Doenças da Pele', 'Psoríase', 'A15',
    false, false, true,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'A15' LIMIT 1),
    'Doença inflamatória crônica da pele', 'Autoimunidade, genética'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-064' OR nome = 'Psoríase');

-- Acne
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Acne', 'Acne', 'DOE-065',
    17, 1, 'Doenças da Pele', 'Acne', 'A15',
    false, false, false,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'A15' LIMIT 1),
    'Inflamação das glândulas sebáceas', 'Hormônios, bactérias, genética'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-065' OR nome = 'Acne');

-- Vitiligo
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Vitiligo', 'Vitiligo', 'DOE-066',
    17, 1, 'Doenças da Pele', 'Vitiligo', 'A15',
    false, false, true,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'A15' LIMIT 1),
    'Despigmentação da pele', 'Autoimunidade, genética'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-066' OR nome = 'Vitiligo');

-- Câncer de Mama
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Câncer de Mama', 'Breast Cancer', 'DOE-067',
    8, 4, 'Neoplasias', 'Câncer de Mama', 'C50',
    false, false, false,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'C50' LIMIT 1),
    'Neoplasia maligna da mama', 'Genética, hormônios, fatores ambientais'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-067' OR nome = 'Câncer de Mama');

-- Câncer de Pulmão
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Câncer de Pulmão', 'Lung Cancer', 'DOE-068',
    8, 4, 'Neoplasias', 'Câncer de Pulmão', 'C34',
    false, false, false,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'C34' LIMIT 1),
    'Neoplasia maligna do pulmão', 'Tabagismo, poluição, genética'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-068' OR nome = 'Câncer de Pulmão');

-- Câncer de Próstata
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Câncer de Próstata', 'Prostate Cancer', 'DOE-069',
    8, 4, 'Neoplasias', 'Câncer de Próstata', 'C61',
    false, false, false,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'C61' LIMIT 1),
    'Neoplasia maligna da próstata', 'Idade, genética, hormônios'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-069' OR nome = 'Câncer de Próstata');

-- Câncer de Cólon
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Câncer de Cólon', 'Colon Cancer', 'DOE-070',
    8, 4, 'Neoplasias', 'Câncer de Cólon', 'C18',
    false, false, false,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'C18' LIMIT 1),
    'Neoplasia maligna do cólon', 'Idade, alimentação, genética'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-070' OR nome = 'Câncer de Cólon');

-- Câncer de Estômago
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Câncer de Estômago', 'Stomach Cancer', 'DOE-071',
    8, 4, 'Neoplasias', 'Câncer de Estômago', 'C16',
    false, false, false,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'C16' LIMIT 1),
    'Neoplasia maligna do estômago', 'Helicobacter pylori, alimentação, genética'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-071' OR nome = 'Câncer de Estômago');

-- Leucemia
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Leucemia', 'Leukemia', 'DOE-072',
    8, 4, 'Neoplasias', 'Leucemia', 'C91',
    false, false, false,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'C91' LIMIT 1),
    'Neoplasia maligna das células sanguíneas', 'Genética, radiação, fatores ambientais'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-072' OR nome = 'Leucemia');

-- Linfoma
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Linfoma', 'Lymphoma', 'DOE-073',
    8, 4, 'Neoplasias', 'Linfoma', 'C85',
    false, false, false,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'C85' LIMIT 1),
    'Neoplasia maligna do sistema linfático', 'Genética, infecções, fatores ambientais'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-073' OR nome = 'Linfoma');

-- Anemia Ferropriva
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Anemia Ferropriva', 'Iron Deficiency Anemia', 'DOE-074',
    23, 1, 'Doenças do Sangue', 'Anemia', 'D50',
    false, false, false,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'D50' LIMIT 1),
    'Deficiência de ferro no organismo', 'Perda de sangue, deficiência nutricional'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-074' OR nome = 'Anemia Ferropriva');

-- Anemia Falciforme
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Anemia Falciforme', 'Sickle Cell Anemia', 'DOE-075',
    23, 3, 'Doenças do Sangue', 'Anemia', 'D57',
    false, false, true,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'D57' LIMIT 1),
    'Doença genética que afeta hemácias', 'Genética, mutação genética'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-075' OR nome = 'Anemia Falciforme');

-- Trombose Venosa Profunda
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Trombose Venosa Profunda', 'Deep Vein Thrombosis', 'DOE-076',
    23, 3, 'Doenças do Aparelho Circulatório', 'Trombose', 'I10',
    false, false, false,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'I10' LIMIT 1),
    'Formação de coágulo em veia profunda', 'Imobilização, cirurgia, fatores genéticos'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-076' OR nome = 'Trombose Venosa Profunda');

-- Infecção Urinária
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Infecção Urinária', 'Urinary Tract Infection', 'DOE-077',
    3, 1, 'Doenças do Aparelho Urinário', 'Infecção Urinária', 'N39',
    false, false, false,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'N39' LIMIT 1),
    'Infecção do trato urinário', 'Bactérias, principalmente E. coli'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-077' OR nome = 'Infecção Urinária');

-- Cálculo Renal
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Cálculo Renal', 'Kidney Stone', 'DOE-078',
    20, 2, 'Doenças do Aparelho Urinário', 'Cálculo Renal', 'N39',
    false, false, false,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'N39' LIMIT 1),
    'Formação de pedras nos rins', 'Desidratação, dieta, fatores genéticos'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-078' OR nome = 'Cálculo Renal');

-- Insuficiência Renal Crônica
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Insuficiência Renal Crônica', 'Chronic Kidney Disease', 'DOE-079',
    20, 3, 'Doenças do Aparelho Urinário', 'Insuficiência Renal', 'N39',
    false, false, true,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'N39' LIMIT 1),
    'Perda progressiva da função renal', 'Diabetes, hipertensão, glomerulonefrite'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-079' OR nome = 'Insuficiência Renal Crônica');

-- Endometriose
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Endometriose', 'Endometriosis', 'DOE-080',
    21, 2, 'Doenças do Aparelho Genital Feminino', 'Endometriose', 'N39',
    false, false, true,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'N39' LIMIT 1),
    'Presença de tecido endometrial fora do útero', 'Genética, fatores hormonais'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-080' OR nome = 'Endometriose');

-- Mioma Uterino
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Mioma Uterino', 'Uterine Fibroid', 'DOE-081',
    21, 2, 'Doenças do Aparelho Genital Feminino', 'Mioma', 'D25',
    false, false, false,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'D25' LIMIT 1),
    'Tumor benigno do útero', 'Hormônios, genética'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-081' OR nome = 'Mioma Uterino');

-- Síndrome dos Ovários Policísticos
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Síndrome dos Ovários Policísticos', 'PCOS', 'DOE-082',
    12, 2, 'Doenças do Aparelho Genital Feminino', 'SOP', 'E28',
    false, false, true,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'E28' LIMIT 1),
    'Distúrbio hormonal que afeta ovários', 'Genética, resistência à insulina'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-082' OR nome = 'Síndrome dos Ovários Policísticos');

-- Osteoporose
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Osteoporose', 'Osteoporosis', 'DOE-083',
    22, 2, 'Doenças do Sistema Osteomuscular', 'Osteoporose', 'M79',
    false, false, true,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'M79' LIMIT 1),
    'Perda de massa óssea', 'Idade, menopausa, deficiência de cálcio'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-083' OR nome = 'Osteoporose');

-- Artrose
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Artrose', 'Osteoarthritis', 'DOE-084',
    22, 2, 'Doenças do Sistema Osteomuscular', 'Artrose', 'M79',
    false, false, true,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'M79' LIMIT 1),
    'Degeneração das articulações', 'Idade, obesidade, trauma'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-084' OR nome = 'Artrose');

-- Lombalgia
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Lombalgia', 'Low Back Pain', 'DOE-085',
    22, 1, 'Doenças do Sistema Osteomuscular', 'Lombalgia', 'M79',
    false, false, true,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'M79' LIMIT 1),
    'Dor na região lombar', 'Postura, esforço, degeneração'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-085' OR nome = 'Lombalgia');

-- Catarata
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Catarata', 'Cataract', 'DOE-086',
    18, 2, 'Doenças dos Olhos', 'Catarata', 'A15',
    false, false, true,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'A15' LIMIT 1),
    'Opacidade do cristalino', 'Idade, diabetes, radiação'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-086' OR nome = 'Catarata');

-- Glaucoma
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Glaucoma', 'Glaucoma', 'DOE-087',
    18, 3, 'Doenças dos Olhos', 'Glaucoma', 'A15',
    false, false, true,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'A15' LIMIT 1),
    'Aumento da pressão intraocular', 'Idade, genética, pressão ocular'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-087' OR nome = 'Glaucoma');

-- Retinopatia Diabética
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Retinopatia Diabética', 'Diabetic Retinopathy', 'DOE-088',
    18, 3, 'Doenças dos Olhos', 'Retinopatia', 'A15',
    false, false, true,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'A15' LIMIT 1),
    'Complicação do diabetes que afeta retina', 'Diabetes mal controlado'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-088' OR nome = 'Retinopatia Diabética');

-- Otite Média
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Otite Média', 'Otitis Media', 'DOE-089',
    3, 1, 'Doenças do Ouvido', 'Otite', 'A15',
    false, false, false,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'A15' LIMIT 1),
    'Inflamação do ouvido médio', 'Bactérias, vírus, alergias'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-089' OR nome = 'Otite Média');

-- Sinusite Crônica
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Sinusite Crônica', 'Chronic Sinusitis', 'DOE-090',
    3, 1, 'Doenças do Aparelho Respiratório', 'Sinusite', 'J06',
    false, false, true,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'J06' LIMIT 1),
    'Inflamação crônica dos seios paranasais', 'Infecções, alergias, pólipos'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-090' OR nome = 'Sinusite Crônica');

-- Diabetes Tipo 1
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Diabetes Tipo 1', 'Type 1 Diabetes', 'DOE-091',
    25, 2, 'Doenças Endocrinológicas', 'Diabetes', 'E10',
    false, false, true,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'E10' LIMIT 1),
    'Doença autoimune que destrói células beta', 'Autoimunidade, genética'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-091' OR nome = 'Diabetes Tipo 1');

-- Tireoidite de Hashimoto
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Tireoidite de Hashimoto', 'Hashimoto Thyroiditis', 'DOE-092',
    25, 1, 'Doenças Endocrinológicas', 'Tireoidite', 'E06',
    false, false, true,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'E06' LIMIT 1),
    'Doença autoimune da tireoide', 'Autoimunidade, genética'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-092' OR nome = 'Tireoidite de Hashimoto');

-- Doença de Graves
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Doença de Graves', 'Graves Disease', 'DOE-093',
    25, 2, 'Doenças Endocrinológicas', 'Tireoide', 'E05',
    false, false, true,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'E05' LIMIT 1),
    'Doença autoimune que causa hipertireoidismo', 'Autoimunidade, genética'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-093' OR nome = 'Doença de Graves');

-- Síndrome de Down
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Síndrome de Down', 'Down Syndrome', 'DOE-094',
    26, 2, 'Malformações Congênitas', 'Síndrome de Down', 'E84',
    false, false, true,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'E84' LIMIT 1),
    'Transtorno genético causado por trissomia do cromossomo 21', 'Trissomia do cromossomo 21'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-094' OR nome = 'Síndrome de Down');

-- Fibrose Cística
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Fibrose Cística', 'Cystic Fibrosis', 'DOE-095',
    26, 3, 'Doenças Genéticas', 'Fibrose Cística', 'E84',
    false, false, true,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'E84' LIMIT 1),
    'Doença genética que afeta pulmões e sistema digestivo', 'Mutação genética CFTR'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-095' OR nome = 'Fibrose Cística');

-- Anemia Falciforme
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Anemia Falciforme', 'Sickle Cell Disease', 'DOE-096',
    26, 3, 'Doenças do Sangue', 'Anemia', 'D57',
    false, false, true,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'D57' LIMIT 1),
    'Doença genética que afeta hemácias', 'Mutação genética da hemoglobina'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-096' OR nome = 'Anemia Falciforme');

-- Doença de Alzheimer
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Doença de Alzheimer', 'Alzheimer Disease', 'DOE-097',
    28, 4, 'Doenças do Sistema Nervoso', 'Alzheimer', 'A15',
    false, false, true,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'A15' LIMIT 1),
    'Doença neurodegenerativa progressiva', 'Idade, genética, fatores ambientais'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-097' OR nome = 'Doença de Alzheimer');

-- Esclerose Lateral Amiotrófica
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Esclerose Lateral Amiotrófica', 'ALS', 'DOE-098',
    28, 4, 'Doenças do Sistema Nervoso', 'ELA', 'A15',
    false, false, true,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'A15' LIMIT 1),
    'Doença neurodegenerativa que afeta neurônios motores', 'Genética, fatores ambientais'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-098' OR nome = 'Esclerose Lateral Amiotrófica');

-- Asma Alérgica
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Asma Alérgica', 'Allergic Asthma', 'DOE-099',
    29, 2, 'Doenças do Aparelho Respiratório', 'Asma', 'J18',
    false, false, true,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'J18' LIMIT 1),
    'Asma desencadeada por alérgenos', 'Alérgenos, genética'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-099' OR nome = 'Asma Alérgica');

-- Anafilaxia
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Anafilaxia', 'Anaphylaxis', 'DOE-100',
    29, 5, 'Doenças Alérgicas', 'Anafilaxia', 'E84',
    false, false, false,
    (SELECT id FROM public.cid_doencas WHERE codigo = 'E84' LIMIT 1),
    'Reação alérgica grave e potencialmente fatal', 'Alérgenos, medicamentos, alimentos'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = 'DOE-100' OR nome = 'Anafilaxia');


-- ========== FIM DO SCRIPT ==========
