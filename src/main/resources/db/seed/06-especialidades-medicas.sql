-- Script de Seed: Especialidades Médicas (Escopo Global)
-- Cria especialidades médicas conforme CFM - dados globais sem tenant
-- Executado quando app.seed.enabled=true

-- Clínica Médica
INSERT INTO public.especialidades_medicas (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    codigo,
    nome_cientifico,
    tipo_especialidade,
    codigo_cfm,
    codigo_cnes,
    area_atuacao,
    subarea,
    requer_residencia,
    requer_titulo_especialista,
    descricao,
    area_atuacao_descricao,
    requisitos_formacao,
    observacoes
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Clínica Médica',
    '01',
    NULL,
    1,
    '01',
    '01',
    'Clínica',
    'Medicina Geral',
    false,
    false,
    'Especialidade médica focada no atendimento de adultos, diagnóstico e tratamento de doenças não cirúrgicas',
    'Atendimento ambulatorial e hospitalar de pacientes adultos, com ênfase em medicina preventiva e tratamento de doenças clínicas',
    'Residência médica em Clínica Médica ou título de especialista pelo CFM',
    'Especialidade base da medicina. Base para outras especialidades médicas.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '01' OR nome = 'Clínica Médica');

-- Pediatria
INSERT INTO public.especialidades_medicas (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    codigo,
    nome_cientifico,
    tipo_especialidade,
    codigo_cfm,
    codigo_cnes,
    area_atuacao,
    subarea,
    requer_residencia,
    requer_titulo_especialista,
    descricao,
    area_atuacao_descricao,
    requisitos_formacao,
    observacoes
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Pediatria',
    '03',
    NULL,
    3,
    '03',
    '03',
    'Clínica',
    'Pediatria Geral',
    true,
    true,
    'Especialidade médica dedicada ao cuidado da saúde de crianças e adolescentes do nascimento até os 18 anos',
    'Atendimento pediátrico preventivo e curativo, acompanhamento do crescimento e desenvolvimento, vacinação, puericultura',
    'Residência médica em Pediatria (3 anos) e título de especialista pelo CFM',
    'Fundamental para saúde infantil. Integrada ao Programa Saúde da Família (PSF).'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '03' OR nome = 'Pediatria');

-- Ginecologia e Obstetrícia
INSERT INTO public.especialidades_medicas (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    codigo,
    nome_cientifico,
    tipo_especialidade,
    codigo_cfm,
    codigo_cnes,
    area_atuacao,
    subarea,
    requer_residencia,
    requer_titulo_especialista,
    descricao,
    area_atuacao_descricao,
    requisitos_formacao,
    observacoes
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Ginecologia e Obstetrícia',
    '04',
    NULL,
    4,
    '04',
    '04',
    'Clínica e Cirúrgica',
    'Saúde da Mulher',
    true,
    true,
    'Especialidade médica que trata da saúde da mulher, gestação, parto e puerpério',
    'Pré-natal, parto, puerpério, saúde reprodutiva, planejamento familiar, prevenção de câncer ginecológico',
    'Residência médica em Ginecologia e Obstetrícia (3 anos) e título de especialista pelo CFM',
    'Fundamental para saúde da mulher e redução da mortalidade materna e infantil.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '04' OR nome = 'Ginecologia e Obstetrícia');

-- Medicina de Família e Comunidade
INSERT INTO public.especialidades_medicas (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    codigo,
    nome_cientifico,
    tipo_especialidade,
    codigo_cfm,
    codigo_cnes,
    area_atuacao,
    subarea,
    requer_residencia,
    requer_titulo_especialista,
    descricao,
    area_atuacao_descricao,
    requisitos_formacao,
    observacoes
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Medicina de Família e Comunidade',
    '16',
    NULL,
    16,
    '16',
    '16',
    'Clínica',
    'Atenção Primária',
    true,
    true,
    'Especialidade médica focada na atenção primária à saúde, cuidado continuado e integral da família',
    'Atuação em unidades básicas de saúde (UBS), estratégia saúde da família (ESF), atenção primária, promoção e prevenção',
    'Residência médica em Medicina de Família e Comunidade (2 anos) e título de especialista pelo CFM',
    'Especialidade estratégica do SUS. Base do Programa Saúde da Família (PSF).'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '16' OR nome = 'Medicina de Família e Comunidade');

-- Cardiologia
INSERT INTO public.especialidades_medicas (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    codigo,
    nome_cientifico,
    tipo_especialidade,
    codigo_cfm,
    codigo_cnes,
    area_atuacao,
    subarea,
    requer_residencia,
    requer_titulo_especialista,
    descricao,
    area_atuacao_descricao,
    requisitos_formacao,
    observacoes
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Cardiologia',
    '06',
    NULL,
    6,
    '06',
    '06',
    'Clínica',
    'Cardiologia Clínica',
    true,
    true,
    'Especialidade médica que trata das doenças do coração e sistema circulatório',
    'Prevenção, diagnóstico e tratamento de doenças cardiovasculares, hipertensão arterial, cardiopatias',
    'Residência médica em Clínica Médica (2 anos) + Residência em Cardiologia (2 anos) ou título de especialista pelo CFM',
    'Especialidade de alta demanda no SUS. Principal causa de mortalidade no Brasil.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '06' OR nome = 'Cardiologia');

-- Cirurgia Geral
INSERT INTO public.especialidades_medicas (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    codigo,
    nome_cientifico,
    tipo_especialidade,
    codigo_cfm,
    codigo_cnes,
    area_atuacao,
    subarea,
    requer_residencia,
    requer_titulo_especialista,
    descricao,
    area_atuacao_descricao,
    requisitos_formacao,
    observacoes
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Cirurgia Geral',
    '02',
    NULL,
    2,
    '02',
    '02',
    'Cirúrgica',
    'Cirurgia Geral',
    true,
    true,
    'Especialidade médica que realiza procedimentos cirúrgicos em diversas áreas do corpo',
    'Cirurgias abdominais, de emergência, procedimentos cirúrgicos gerais, trauma',
    'Residência médica em Cirurgia Geral (3 anos) e título de especialista pelo CFM',
    'Base para outras especialidades cirúrgicas.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '02' OR nome = 'Cirurgia Geral');

-- Ortopedia
INSERT INTO public.especialidades_medicas (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    codigo,
    nome_cientifico,
    tipo_especialidade,
    codigo_cfm,
    codigo_cnes,
    area_atuacao,
    subarea,
    requer_residencia,
    requer_titulo_especialista,
    descricao,
    area_atuacao_descricao,
    requisitos_formacao,
    observacoes
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Ortopedia',
    '05',
    NULL,
    5,
    '05',
    '05',
    'Cirúrgica',
    'Ortopedia e Traumatologia',
    true,
    true,
    'Especialidade médica que trata de doenças e lesões do sistema musculoesquelético',
    'Traumatologia, cirurgia ortopédica, tratamento de fraturas, doenças ósseas e articulares',
    'Residência médica em Ortopedia (3 anos) e título de especialista pelo CFM',
    'Alta demanda no SUS devido a acidentes e traumas.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '05' OR nome = 'Ortopedia');

-- Anestesiologia
INSERT INTO public.especialidades_medicas (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    codigo,
    nome_cientifico,
    tipo_especialidade,
    codigo_cfm,
    codigo_cnes,
    area_atuacao,
    subarea,
    requer_residencia,
    requer_titulo_especialista,
    descricao,
    area_atuacao_descricao,
    requisitos_formacao,
    observacoes
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Anestesiologia',
    '13',
    NULL,
    13,
    '13',
    '13',
    'Perioperatória',
    'Anestesia e Terapia Intensiva',
    true,
    true,
    'Especialidade médica que administra anestesia e monitora pacientes durante cirurgias',
    'Anestesia geral e regional, medicina perioperatória, terapia intensiva, tratamento da dor',
    'Residência médica em Anestesiologia (3 anos) e título de especialista pelo CFM',
    'Essencial para realização de procedimentos cirúrgicos.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '13' OR nome = 'Anestesiologia');

-- Medicina de Urgência
INSERT INTO public.especialidades_medicas (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    codigo,
    nome_cientifico,
    tipo_especialidade,
    codigo_cfm,
    codigo_cnes,
    area_atuacao,
    subarea,
    requer_residencia,
    requer_titulo_especialista,
    descricao,
    area_atuacao_descricao,
    requisitos_formacao,
    observacoes
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Medicina de Urgência',
    '17',
    NULL,
    17,
    '17',
    '17',
    'Emergência',
    'Urgência e Emergência',
    true,
    true,
    'Especialidade médica dedicada ao atendimento de situações de urgência e emergência',
    'Atendimento em pronto-socorro, UPA, SAMU, triagem, estabilização de pacientes graves',
    'Residência médica em Medicina de Urgência (2 anos) e título de especialista pelo CFM',
    'Fundamental para UPAs e pronto-socorros. Alta demanda no SUS.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '17' OR nome = 'Medicina de Urgência');

-- Dermatologia
INSERT INTO public.especialidades_medicas (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    codigo,
    nome_cientifico,
    tipo_especialidade,
    codigo_cfm,
    codigo_cnes,
    area_atuacao,
    subarea,
    requer_residencia,
    requer_titulo_especialista,
    descricao,
    area_atuacao_descricao,
    requisitos_formacao,
    observacoes
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Dermatologia',
    '07',
    NULL,
    7,
    '07',
    '07',
    'Clínica',
    'Dermatologia Clínica',
    true,
    true,
    'Especialidade médica que trata doenças da pele, cabelos e unhas',
    'Diagnóstico e tratamento de doenças dermatológicas, câncer de pele, estética médica',
    'Residência médica em Dermatologia (3 anos) ou Clínica Médica (2 anos) + especialização em Dermatologia e título de especialista pelo CFM',
    'Alta demanda ambulatorial no SUS.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '07' OR nome = 'Dermatologia');

