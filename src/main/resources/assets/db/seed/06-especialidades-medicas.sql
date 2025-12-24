-- Script de Seed: Especialidades Médicas (Escopo Global)
-- Cria especialidades médicas reconhecidas pelo CFM - dados globais sem tenant
-- Executado quando app.seed.enabled=true
-- Gerado automaticamente pelo script Python: scripts/generate_especialidades_medicas.py
-- Total de especialidades: ~70 especialidades médicas reconhecidas pelo CFM

-- Clínica Médica
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Clínica Médica', '01', NULL,
    1, '01', '01', 'Clínica', 'Medicina Geral',
    false, false, 'Especialidade médica focada no atendimento de adultos, diagnóstico e tratamento de doenças não cirúrgicas',
    'Atendimento ambulatorial e hospitalar de pacientes adultos, com ênfase em medicina preventiva e tratamento de doenças clínicas', 'Residência médica em Clínica Médica ou título de especialista pelo CFM', 'Especialidade base da medicina. Base para outras especialidades médicas.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '01' OR nome = 'Clínica Médica');

-- Cirurgia Geral
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Cirurgia Geral', '02', NULL,
    2, '02', '02', 'Cirúrgica', 'Cirurgia Geral',
    true, true, 'Especialidade médica que realiza procedimentos cirúrgicos em diversas áreas do corpo',
    'Cirurgias abdominais, de emergência, procedimentos cirúrgicos gerais, trauma', 'Residência médica em Cirurgia Geral (3 anos) e título de especialista pelo CFM', 'Base para outras especialidades cirúrgicas.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '02' OR nome = 'Cirurgia Geral');

-- Pediatria
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Pediatria', '03', NULL,
    3, '03', '03', 'Clínica', 'Pediatria Geral',
    true, true, 'Especialidade médica dedicada ao cuidado da saúde de crianças e adolescentes do nascimento até os 18 anos',
    'Atendimento pediátrico preventivo e curativo, acompanhamento do crescimento e desenvolvimento, vacinação, puericultura', 'Residência médica em Pediatria (3 anos) e título de especialista pelo CFM', 'Fundamental para saúde infantil. Integrada ao Programa Saúde da Família (PSF).'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '03' OR nome = 'Pediatria');

-- Ginecologia e Obstetrícia
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Ginecologia e Obstetrícia', '04', NULL,
    4, '04', '04', 'Clínica e Cirúrgica', 'Saúde da Mulher',
    true, true, 'Especialidade médica que trata da saúde da mulher, gestação, parto e puerpério',
    'Pré-natal, parto, puerpério, saúde reprodutiva, planejamento familiar, prevenção de câncer ginecológico', 'Residência médica em Ginecologia e Obstetrícia (3 anos) e título de especialista pelo CFM', 'Fundamental para saúde da mulher e redução da mortalidade materna e infantil.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '04' OR nome = 'Ginecologia e Obstetrícia');

-- Ortopedia
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Ortopedia', '05', NULL,
    5, '05', '05', 'Cirúrgica', 'Ortopedia e Traumatologia',
    true, true, 'Especialidade médica que trata de doenças e lesões do sistema musculoesquelético',
    'Traumatologia, cirurgia ortopédica, tratamento de fraturas, doenças ósseas e articulares', 'Residência médica em Ortopedia (3 anos) e título de especialista pelo CFM', 'Alta demanda no SUS devido a acidentes e traumas.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '05' OR nome = 'Ortopedia');

-- Cardiologia
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Cardiologia', '06', NULL,
    6, '06', '06', 'Clínica', 'Cardiologia Clínica',
    true, true, 'Especialidade médica que trata das doenças do coração e sistema circulatório',
    'Prevenção, diagnóstico e tratamento de doenças cardiovasculares, hipertensão arterial, cardiopatias', 'Residência médica em Clínica Médica (2 anos) + Residência em Cardiologia (2 anos) ou título de especialista pelo CFM', 'Especialidade de alta demanda no SUS. Principal causa de mortalidade no Brasil.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '06' OR nome = 'Cardiologia');

-- Dermatologia
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Dermatologia', '07', NULL,
    7, '07', '07', 'Clínica', 'Dermatologia Clínica',
    true, true, 'Especialidade médica que trata doenças da pele, cabelos e unhas',
    'Diagnóstico e tratamento de doenças dermatológicas, câncer de pele, estética médica', 'Residência médica em Dermatologia (3 anos) ou Clínica Médica (2 anos) + especialização em Dermatologia e título de especialista pelo CFM', 'Alta demanda ambulatorial no SUS.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '07' OR nome = 'Dermatologia');

-- Oftalmologia
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Oftalmologia', '08', NULL,
    8, '08', '08', 'Clínica e Cirúrgica', 'Oftalmologia Geral',
    true, true, 'Especialidade médica que trata doenças dos olhos e sistema visual',
    'Prevenção, diagnóstico e tratamento de doenças oculares, cirurgias oftalmológicas, correção de erros refrativos', 'Residência médica em Oftalmologia (3 anos) e título de especialista pelo CFM', 'Alta demanda no SUS. Fundamental para prevenção da cegueira.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '08' OR nome = 'Oftalmologia');

-- Otorrinolaringologia
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Otorrinolaringologia', '09', NULL,
    9, '09', '09', 'Clínica e Cirúrgica', 'Otorrinolaringologia Geral',
    true, true, 'Especialidade médica que trata doenças do ouvido, nariz e garganta',
    'Diagnóstico e tratamento de doenças do ouvido, nariz, seios paranasais, faringe e laringe', 'Residência médica em Otorrinolaringologia (3 anos) e título de especialista pelo CFM', 'Alta demanda ambulatorial no SUS.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '09' OR nome = 'Otorrinolaringologia');

-- Psiquiatria
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Psiquiatria', '10', NULL,
    10, '10', '10', 'Clínica', 'Psiquiatria Geral',
    true, true, 'Especialidade médica que trata transtornos mentais e comportamentais',
    'Diagnóstico e tratamento de transtornos mentais, psicoterapia, prescrição de psicofármacos', 'Residência médica em Psiquiatria (3 anos) e título de especialista pelo CFM', 'Alta demanda no SUS. Fundamental para saúde mental.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '10' OR nome = 'Psiquiatria');

-- Neurologia
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Neurologia', '11', NULL,
    11, '11', '11', 'Clínica', 'Neurologia Clínica',
    true, true, 'Especialidade médica que trata doenças do sistema nervoso',
    'Diagnóstico e tratamento de doenças neurológicas, epilepsia, doenças cerebrovasculares, demências', 'Residência médica em Neurologia (3 anos) e título de especialista pelo CFM', 'Alta demanda no SUS.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '11' OR nome = 'Neurologia');

-- Urologia
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Urologia', '12', NULL,
    12, '12', '12', 'Clínica e Cirúrgica', 'Urologia Geral',
    true, true, 'Especialidade médica que trata doenças do trato urinário e sistema reprodutor masculino',
    'Diagnóstico e tratamento de doenças urológicas, cirurgias urológicas, andrologia', 'Residência médica em Urologia (3 anos) e título de especialista pelo CFM', 'Alta demanda no SUS.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '12' OR nome = 'Urologia');

-- Anestesiologia
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Anestesiologia', '13', NULL,
    13, '13', '13', 'Perioperatória', 'Anestesia e Terapia Intensiva',
    true, true, 'Especialidade médica que administra anestesia e monitora pacientes durante cirurgias',
    'Anestesia geral e regional, medicina perioperatória, terapia intensiva, tratamento da dor', 'Residência médica em Anestesiologia (3 anos) e título de especialista pelo CFM', 'Essencial para realização de procedimentos cirúrgicos.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '13' OR nome = 'Anestesiologia');

-- Radiologia
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Radiologia', '14', NULL,
    14, '14', '14', 'Diagnóstica', 'Radiologia Geral',
    true, true, 'Especialidade médica que utiliza métodos de imagem para diagnóstico',
    'Radiologia convencional, tomografia computadorizada, ressonância magnética, ultrassonografia', 'Residência médica em Radiologia (3 anos) e título de especialista pelo CFM', 'Fundamental para diagnóstico médico.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '14' OR nome = 'Radiologia');

-- Patologia
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Patologia', '15', NULL,
    15, '15', '15', 'Diagnóstica', 'Patologia Geral',
    true, true, 'Especialidade médica que estuda doenças através de análise de tecidos e células',
    'Anatomia patológica, citopatologia, patologia clínica, necropsia', 'Residência médica em Patologia (3 anos) e título de especialista pelo CFM', 'Fundamental para diagnóstico de câncer e outras doenças.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '15' OR nome = 'Patologia');

-- Medicina de Família e Comunidade
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Medicina de Família e Comunidade', '16', NULL,
    16, '16', '16', 'Clínica', 'Atenção Primária',
    true, true, 'Especialidade médica focada na atenção primária à saúde, cuidado continuado e integral da família',
    'Atuação em unidades básicas de saúde (UBS), estratégia saúde da família (ESF), atenção primária, promoção e prevenção', 'Residência médica em Medicina de Família e Comunidade (2 anos) e título de especialista pelo CFM', 'Especialidade estratégica do SUS. Base do Programa Saúde da Família (PSF).'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '16' OR nome = 'Medicina de Família e Comunidade');

-- Medicina de Urgência
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Medicina de Urgência', '17', NULL,
    17, '17', '17', 'Emergência', 'Urgência e Emergência',
    true, true, 'Especialidade médica dedicada ao atendimento de situações de urgência e emergência',
    'Atendimento em pronto-socorro, UPA, SAMU, triagem, estabilização de pacientes graves', 'Residência médica em Medicina de Urgência (2 anos) e título de especialista pelo CFM', 'Fundamental para UPAs e pronto-socorros. Alta demanda no SUS.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '17' OR nome = 'Medicina de Urgência');

-- Medicina Intensiva
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Medicina Intensiva', '18', NULL,
    18, '18', '18', 'Intensiva', 'Terapia Intensiva',
    true, true, 'Especialidade médica dedicada ao cuidado de pacientes críticos em unidades de terapia intensiva',
    'Cuidado de pacientes graves, suporte avançado de vida, monitorização hemodinâmica, ventilação mecânica', 'Residência médica em Medicina Intensiva (2 anos) ou Anestesiologia/Clínica Médica + especialização e título de especialista pelo CFM', 'Fundamental para unidades de terapia intensiva (UTI).'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '18' OR nome = 'Medicina Intensiva');

-- Medicina Preventiva
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Medicina Preventiva', '19', NULL,
    19, '19', '19', 'Preventiva', 'Medicina Preventiva',
    true, true, 'Especialidade médica focada na prevenção de doenças e promoção da saúde',
    'Epidemiologia, saúde pública, medicina preventiva, programas de saúde', 'Residência médica em Medicina Preventiva (2 anos) e título de especialista pelo CFM', 'Fundamental para saúde pública.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '19' OR nome = 'Medicina Preventiva');

-- Medicina do Trabalho
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Medicina do Trabalho', '20', NULL,
    20, '20', '20', 'Preventiva', 'Medicina do Trabalho',
    true, true, 'Especialidade médica que trata da saúde do trabalhador',
    'Medicina ocupacional, perícia médica trabalhista, prevenção de acidentes de trabalho', 'Residência médica em Medicina do Trabalho (2 anos) ou especialização e título de especialista pelo CFM', 'Fundamental para saúde ocupacional.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '20' OR nome = 'Medicina do Trabalho');

-- Medicina Legal
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Medicina Legal', '21', NULL,
    21, '21', '21', 'Pericial', 'Medicina Legal',
    true, true, 'Especialidade médica que aplica conhecimentos médicos ao direito',
    'Perícia médica, medicina forense, tanatologia, toxicologia forense', 'Residência médica em Medicina Legal (2 anos) e título de especialista pelo CFM', 'Fundamental para perícias médicas e judiciais.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '21' OR nome = 'Medicina Legal');

-- Medicina Esportiva
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Medicina Esportiva', '22', NULL,
    22, '22', '22', 'Clínica', 'Medicina Esportiva',
    true, true, 'Especialidade médica que trata da saúde de atletas e praticantes de atividade física',
    'Medicina do esporte, fisiologia do exercício, prevenção de lesões esportivas', 'Residência médica em Medicina Esportiva (2 anos) ou especialização e título de especialista pelo CFM', 'Crescente demanda no Brasil.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '22' OR nome = 'Medicina Esportiva');

-- Geriatria
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Geriatria', '23', NULL,
    23, '23', '23', 'Clínica', 'Geriatria',
    true, true, 'Especialidade médica que trata da saúde do idoso',
    'Cuidado do idoso, doenças geriátricas, gerontologia, cuidados paliativos', 'Residência médica em Geriatria (2 anos) ou Clínica Médica + especialização e título de especialista pelo CFM', 'Crescente demanda devido ao envelhecimento populacional.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '23' OR nome = 'Geriatria');

-- Endocrinologia
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Endocrinologia', '24', NULL,
    24, '24', '24', 'Clínica', 'Endocrinologia Clínica',
    true, true, 'Especialidade médica que trata doenças do sistema endócrino e metabolismo',
    'Diabetes, doenças da tireóide, obesidade, distúrbios hormonais, metabolismo', 'Residência médica em Clínica Médica (2 anos) + Residência em Endocrinologia (2 anos) ou título de especialista pelo CFM', 'Alta demanda no SUS. Diabetes é epidemia no Brasil.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '24' OR nome = 'Endocrinologia');

-- Gastroenterologia
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Gastroenterologia', '25', NULL,
    25, '25', '25', 'Clínica', 'Gastroenterologia Clínica',
    true, true, 'Especialidade médica que trata doenças do aparelho digestivo',
    'Doenças do esôfago, estômago, intestinos, fígado, pâncreas, endoscopia digestiva', 'Residência médica em Clínica Médica (2 anos) + Residência em Gastroenterologia (2 anos) ou título de especialista pelo CFM', 'Alta demanda no SUS.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '25' OR nome = 'Gastroenterologia');

-- Pneumologia
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Pneumologia', '26', NULL,
    26, '26', '26', 'Clínica', 'Pneumologia Clínica',
    true, true, 'Especialidade médica que trata doenças do aparelho respiratório',
    'Asma, DPOC, pneumonia, tuberculose, doenças pulmonares intersticiais', 'Residência médica em Clínica Médica (2 anos) + Residência em Pneumologia (2 anos) ou título de especialista pelo CFM', 'Alta demanda no SUS.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '26' OR nome = 'Pneumologia');

-- Nefrologia
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Nefrologia', '27', NULL,
    27, '27', '27', 'Clínica', 'Nefrologia Clínica',
    true, true, 'Especialidade médica que trata doenças dos rins',
    'Insuficiência renal, diálise, transplante renal, doenças renais crônicas', 'Residência médica em Clínica Médica (2 anos) + Residência em Nefrologia (2 anos) ou título de especialista pelo CFM', 'Alta demanda no SUS.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '27' OR nome = 'Nefrologia');

-- Hematologia
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Hematologia', '28', NULL,
    28, '28', '28', 'Clínica', 'Hematologia Clínica',
    true, true, 'Especialidade médica que trata doenças do sangue e órgãos hematopoéticos',
    'Anemias, leucemias, linfomas, distúrbios de coagulação, transplante de medula óssea', 'Residência médica em Clínica Médica (2 anos) + Residência em Hematologia (2 anos) ou título de especialista pelo CFM', 'Alta demanda no SUS.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '28' OR nome = 'Hematologia');

-- Oncologia
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Oncologia', '29', NULL,
    29, '29', '29', 'Clínica', 'Oncologia Clínica',
    true, true, 'Especialidade médica que trata câncer',
    'Diagnóstico e tratamento de câncer, quimioterapia, cuidados paliativos oncológicos', 'Residência médica em Clínica Médica (2 anos) + Residência em Oncologia (2 anos) ou título de especialista pelo CFM', 'Alta demanda no SUS. Câncer é segunda causa de morte no Brasil.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '29' OR nome = 'Oncologia');

-- Reumatologia
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Reumatologia', '30', NULL,
    30, '30', '30', 'Clínica', 'Reumatologia Clínica',
    true, true, 'Especialidade médica que trata doenças reumáticas e autoimunes',
    'Artrite reumatóide, lúpus, fibromialgia, osteoporose, doenças autoimunes', 'Residência médica em Clínica Médica (2 anos) + Residência em Reumatologia (2 anos) ou título de especialista pelo CFM', 'Alta demanda no SUS.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '30' OR nome = 'Reumatologia');

-- Infectologia
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Infectologia', '31', NULL,
    31, '31', '31', 'Clínica', 'Infectologia Clínica',
    true, true, 'Especialidade médica que trata doenças infecciosas',
    'HIV/AIDS, tuberculose, hepatites, infecções hospitalares, doenças tropicais', 'Residência médica em Clínica Médica (2 anos) + Residência em Infectologia (2 anos) ou título de especialista pelo CFM', 'Alta demanda no SUS.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '31' OR nome = 'Infectologia');

-- Cirurgia Plástica
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Cirurgia Plástica', '33', NULL,
    99, '33', '33', 'Cirúrgica', 'Cirurgia Plástica',
    true, true, 'Especialidade médica que realiza cirurgias reparadoras e estéticas',
    'Cirurgia plástica reparadora, cirurgia estética, queimaduras, reconstrução', 'Residência médica em Cirurgia Geral (3 anos) + Residência em Cirurgia Plástica (3 anos) e título de especialista pelo CFM', 'Alta demanda no Brasil.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '33' OR nome = 'Cirurgia Plástica');

-- Cirurgia Cardiovascular
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Cirurgia Cardiovascular', '34', NULL,
    99, '34', '34', 'Cirúrgica', 'Cirurgia Cardiovascular',
    true, true, 'Especialidade médica que realiza cirurgias do coração e grandes vasos',
    'Cirurgia cardíaca, cirurgia de grandes vasos, transplante cardíaco', 'Residência médica em Cirurgia Geral (3 anos) + Residência em Cirurgia Cardiovascular (3 anos) e título de especialista pelo CFM', 'Alta complexidade.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '34' OR nome = 'Cirurgia Cardiovascular');

-- Cirurgia Torácica
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Cirurgia Torácica', '35', NULL,
    99, '35', '35', 'Cirúrgica', 'Cirurgia Torácica',
    true, true, 'Especialidade médica que realiza cirurgias do tórax',
    'Cirurgia pulmonar, cirurgia de mediastino, cirurgia de parede torácica', 'Residência médica em Cirurgia Geral (3 anos) + Residência em Cirurgia Torácica (2 anos) e título de especialista pelo CFM', 'Alta complexidade.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '35' OR nome = 'Cirurgia Torácica');

-- Cirurgia do Aparelho Digestivo
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Cirurgia do Aparelho Digestivo', '36', NULL,
    99, '36', '36', 'Cirúrgica', 'Cirurgia Digestiva',
    true, true, 'Especialidade médica que realiza cirurgias do aparelho digestivo',
    'Cirurgia de esôfago, estômago, intestinos, fígado, pâncreas, cirurgia bariátrica', 'Residência médica em Cirurgia Geral (3 anos) + Residência em Cirurgia do Aparelho Digestivo (2 anos) e título de especialista pelo CFM', 'Alta demanda no SUS.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '36' OR nome = 'Cirurgia do Aparelho Digestivo');

-- Cirurgia Vascular
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Cirurgia Vascular', '37', NULL,
    99, '37', '37', 'Cirúrgica', 'Cirurgia Vascular',
    true, true, 'Especialidade médica que realiza cirurgias de vasos sanguíneos',
    'Cirurgia arterial, venosa, tratamento de varizes, aneurismas', 'Residência médica em Cirurgia Geral (3 anos) + Residência em Cirurgia Vascular (2 anos) e título de especialista pelo CFM', 'Alta demanda no SUS.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '37' OR nome = 'Cirurgia Vascular');

-- Neurocirurgia
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Neurocirurgia', '38', NULL,
    99, '38', '38', 'Cirúrgica', 'Neurocirurgia',
    true, true, 'Especialidade médica que realiza cirurgias do sistema nervoso',
    'Cirurgia de cérebro, medula espinhal, nervos periféricos, trauma cranioencefálico', 'Residência médica em Cirurgia Geral (3 anos) + Residência em Neurocirurgia (4 anos) e título de especialista pelo CFM', 'Alta complexidade.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '38' OR nome = 'Neurocirurgia');

-- Cirurgia Pediátrica
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Cirurgia Pediátrica', '39', NULL,
    99, '39', '39', 'Cirúrgica', 'Cirurgia Pediátrica',
    true, true, 'Especialidade médica que realiza cirurgias em crianças',
    'Cirurgia geral pediátrica, cirurgia neonatal, malformações congênitas', 'Residência médica em Cirurgia Geral (3 anos) + Residência em Cirurgia Pediátrica (2 anos) e título de especialista pelo CFM', 'Alta complexidade.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '39' OR nome = 'Cirurgia Pediátrica');

-- Cirurgia de Cabeça e Pescoço
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Cirurgia de Cabeça e Pescoço', '40', NULL,
    99, '40', '40', 'Cirúrgica', 'Cirurgia de Cabeça e Pescoço',
    true, true, 'Especialidade médica que realiza cirurgias de cabeça e pescoço',
    'Cirurgia de tireóide, paratireóide, tumores de cabeça e pescoço', 'Residência médica em Cirurgia Geral (3 anos) + Residência em Cirurgia de Cabeça e Pescoço (2 anos) e título de especialista pelo CFM', 'Alta demanda no SUS.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '40' OR nome = 'Cirurgia de Cabeça e Pescoço');

-- Angiologia
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Angiologia', '41', NULL,
    99, '41', '41', 'Clínica', 'Angiologia',
    true, true, 'Especialidade médica que trata doenças vasculares',
    'Doenças arteriais, venosas, linfáticas, tratamento clínico de varizes', 'Residência médica em Clínica Médica (2 anos) + Residência em Angiologia (2 anos) ou título de especialista pelo CFM', 'Alta demanda no SUS.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '41' OR nome = 'Angiologia');

-- Mastologia
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Mastologia', '42', NULL,
    99, '42', '42', 'Clínica e Cirúrgica', 'Mastologia',
    true, true, 'Especialidade médica que trata doenças da mama',
    'Prevenção, diagnóstico e tratamento de doenças da mama, câncer de mama', 'Residência médica em Ginecologia e Obstetrícia ou Cirurgia Geral (3 anos) + Residência em Mastologia (2 anos) e título de especialista pelo CFM', 'Alta demanda no SUS.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '42' OR nome = 'Mastologia');

-- Coloproctologia
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Coloproctologia', '43', NULL,
    99, '43', '43', 'Clínica e Cirúrgica', 'Coloproctologia',
    true, true, 'Especialidade médica que trata doenças do cólon, reto e ânus',
    'Doenças do intestino grosso, reto, ânus, cirurgia colorretal', 'Residência médica em Cirurgia Geral (3 anos) + Residência em Coloproctologia (2 anos) e título de especialista pelo CFM', 'Alta demanda no SUS.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '43' OR nome = 'Coloproctologia');

-- Cirurgia Oncológica
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Cirurgia Oncológica', '44', NULL,
    99, '44', '44', 'Cirúrgica', 'Cirurgia Oncológica',
    true, true, 'Especialidade médica que realiza cirurgias para tratamento de câncer',
    'Cirurgia oncológica, tratamento cirúrgico de tumores', 'Residência médica em Cirurgia Geral (3 anos) + Residência em Cirurgia Oncológica (2 anos) e título de especialista pelo CFM', 'Alta demanda no SUS.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '44' OR nome = 'Cirurgia Oncológica');

-- Oncologia Clínica
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Oncologia Clínica', '45', NULL,
    99, '45', '45', 'Clínica', 'Oncologia Clínica',
    true, true, 'Especialidade médica que trata câncer com métodos clínicos',
    'Quimioterapia, imunoterapia, tratamento clínico de câncer', 'Residência médica em Clínica Médica (2 anos) + Residência em Oncologia Clínica (2 anos) e título de especialista pelo CFM', 'Alta demanda no SUS.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '45' OR nome = 'Oncologia Clínica');

-- Radioterapia
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Radioterapia', '46', NULL,
    99, '46', '46', 'Terapêutica', 'Radioterapia',
    true, true, 'Especialidade médica que utiliza radiação para tratamento de câncer',
    'Radioterapia, tratamento radioterápico de tumores', 'Residência médica em Radioterapia (3 anos) e título de especialista pelo CFM', 'Alta demanda no SUS.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '46' OR nome = 'Radioterapia');

-- Medicina Nuclear
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Medicina Nuclear', '47', NULL,
    99, '47', '47', 'Diagnóstica e Terapêutica', 'Medicina Nuclear',
    true, true, 'Especialidade médica que utiliza materiais radioativos para diagnóstico e tratamento',
    'Cintilografia, PET scan, tratamento com radioisótopos', 'Residência médica em Medicina Nuclear (3 anos) e título de especialista pelo CFM', 'Alta complexidade.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '47' OR nome = 'Medicina Nuclear');

-- Angiorradiologia e Cirurgia Endovascular
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Angiorradiologia e Cirurgia Endovascular', '48', NULL,
    99, '48', '48', 'Diagnóstica e Cirúrgica', 'Angiorradiologia',
    true, true, 'Especialidade médica que realiza procedimentos endovasculares',
    'Angioplastia, embolização, procedimentos endovasculares', 'Residência médica em Radiologia ou Cirurgia Vascular (3 anos) + especialização e título de especialista pelo CFM', 'Alta complexidade.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '48' OR nome = 'Angiorradiologia e Cirurgia Endovascular');

-- Radiologia Intervencionista
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Radiologia Intervencionista', '49', NULL,
    99, '49', '49', 'Diagnóstica e Cirúrgica', 'Radiologia Intervencionista',
    true, true, 'Especialidade médica que realiza procedimentos guiados por imagem',
    'Procedimentos intervencionistas, biópsias guiadas, drenagens', 'Residência médica em Radiologia (3 anos) + especialização e título de especialista pelo CFM', 'Alta complexidade.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '49' OR nome = 'Radiologia Intervencionista');

-- Pediatria Intensiva
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Pediatria Intensiva', '50', NULL,
    99, '50', '50', 'Intensiva', 'Pediatria Intensiva',
    true, true, 'Especialidade médica que trata crianças críticas em UTI pediátrica',
    'Cuidado de crianças graves, UTI pediátrica, suporte avançado de vida pediátrico', 'Residência médica em Pediatria (3 anos) + Residência em Terapia Intensiva Pediátrica (2 anos) e título de especialista pelo CFM', 'Alta complexidade.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '50' OR nome = 'Pediatria Intensiva');

-- Neonatologia
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Neonatologia', '51', NULL,
    99, '51', '51', 'Intensiva', 'Neonatologia',
    true, true, 'Especialidade médica que trata recém-nascidos',
    'Cuidado de recém-nascidos, UTI neonatal, prematuridade', 'Residência médica em Pediatria (3 anos) + Residência em Neonatologia (2 anos) e título de especialista pelo CFM', 'Fundamental para redução da mortalidade neonatal.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '51' OR nome = 'Neonatologia');

-- Cardiologia Pediátrica
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Cardiologia Pediátrica', '52', NULL,
    99, '52', '52', 'Clínica', 'Cardiologia Pediátrica',
    true, true, 'Especialidade médica que trata doenças cardíacas em crianças',
    'Cardiopatias congênitas, doenças cardíacas pediátricas', 'Residência médica em Pediatria (3 anos) + Residência em Cardiologia Pediátrica (2 anos) e título de especialista pelo CFM', 'Alta complexidade.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '52' OR nome = 'Cardiologia Pediátrica');

-- Neurologia Pediátrica
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Neurologia Pediátrica', '53', NULL,
    99, '53', '53', 'Clínica', 'Neurologia Pediátrica',
    true, true, 'Especialidade médica que trata doenças neurológicas em crianças',
    'Epilepsia pediátrica, doenças neurológicas infantis, paralisia cerebral', 'Residência médica em Pediatria (3 anos) + Residência em Neurologia Pediátrica (2 anos) e título de especialista pelo CFM', 'Alta demanda no SUS.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '53' OR nome = 'Neurologia Pediátrica');

-- Oncologia Pediátrica
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Oncologia Pediátrica', '54', NULL,
    99, '54', '54', 'Clínica', 'Oncologia Pediátrica',
    true, true, 'Especialidade médica que trata câncer em crianças',
    'Leucemias pediátricas, tumores pediátricos, tratamento oncológico infantil', 'Residência médica em Pediatria (3 anos) + Residência em Oncologia Pediátrica (2 anos) e título de especialista pelo CFM', 'Alta complexidade.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '54' OR nome = 'Oncologia Pediátrica');

-- Reprodução Humana
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Reprodução Humana', '55', NULL,
    99, '55', '55', 'Clínica', 'Reprodução Humana',
    true, true, 'Especialidade médica que trata infertilidade e reprodução assistida',
    'Infertilidade, reprodução assistida, fertilização in vitro', 'Residência médica em Ginecologia e Obstetrícia (3 anos) + especialização e título de especialista pelo CFM', 'Crescente demanda no Brasil.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '55' OR nome = 'Reprodução Humana');

-- Ultrassonografia em Ginecologia e Obstetrícia
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Ultrassonografia em Ginecologia e Obstetrícia', '56', NULL,
    99, '56', '56', 'Diagnóstica', 'Ultrassonografia GO',
    true, true, 'Especialidade médica que realiza ultrassonografia ginecológica e obstétrica',
    'Ultrassonografia obstétrica, ginecológica, morfológica', 'Residência médica em Ginecologia e Obstetrícia (3 anos) + especialização e título de especialista pelo CFM', 'Alta demanda no SUS.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '56' OR nome = 'Ultrassonografia em Ginecologia e Obstetrícia');

-- Psiquiatria da Infância e Adolescência
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Psiquiatria da Infância e Adolescência', '57', NULL,
    99, '57', '57', 'Clínica', 'Psiquiatria Infantil',
    true, true, 'Especialidade médica que trata transtornos mentais em crianças e adolescentes',
    'Transtornos mentais infantis, autismo, TDAH, transtornos de desenvolvimento', 'Residência médica em Psiquiatria (3 anos) + Residência em Psiquiatria da Infância e Adolescência (2 anos) e título de especialista pelo CFM', 'Alta demanda no SUS.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '57' OR nome = 'Psiquiatria da Infância e Adolescência');

-- Psiquiatria Forense
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Psiquiatria Forense', '58', NULL,
    99, '58', '58', 'Pericial', 'Psiquiatria Forense',
    true, true, 'Especialidade médica que aplica psiquiatria ao direito',
    'Perícia psiquiátrica, avaliação de capacidade, imputabilidade', 'Residência médica em Psiquiatria (3 anos) + especialização e título de especialista pelo CFM', 'Fundamental para perícias judiciais.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '58' OR nome = 'Psiquiatria Forense');

-- Neuropediatria
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Neuropediatria', '59', NULL,
    99, '59', '59', 'Clínica', 'Neuropediatria',
    true, true, 'Especialidade médica que trata doenças neurológicas em crianças',
    'Epilepsia pediátrica, doenças neurológicas infantis', 'Residência médica em Pediatria (3 anos) + Residência em Neurologia Pediátrica (2 anos) e título de especialista pelo CFM', 'Alta demanda no SUS.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '59' OR nome = 'Neuropediatria');

-- Oftalmopediatria
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Oftalmopediatria', '60', NULL,
    99, '60', '60', 'Clínica', 'Oftalmopediatria',
    true, true, 'Especialidade médica que trata doenças oculares em crianças',
    'Estrabismo, ambliopia, doenças oculares pediátricas', 'Residência médica em Oftalmologia (3 anos) + especialização e título de especialista pelo CFM', 'Alta demanda no SUS.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '60' OR nome = 'Oftalmopediatria');

-- Otorrinolaringologia Pediátrica
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Otorrinolaringologia Pediátrica', '61', NULL,
    99, '61', '61', 'Clínica e Cirúrgica', 'ORL Pediátrica',
    true, true, 'Especialidade médica que trata doenças de ouvido, nariz e garganta em crianças',
    'Adenoidectomia, amigdalectomia, otites, doenças ORL pediátricas', 'Residência médica em Otorrinolaringologia (3 anos) + especialização e título de especialista pelo CFM', 'Alta demanda no SUS.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '61' OR nome = 'Otorrinolaringologia Pediátrica');

-- Urologia Pediátrica
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Urologia Pediátrica', '62', NULL,
    99, '62', '62', 'Clínica e Cirúrgica', 'Urologia Pediátrica',
    true, true, 'Especialidade médica que trata doenças urológicas em crianças',
    'Malformações urológicas, infecções urinárias pediátricas', 'Residência médica em Urologia (3 anos) + especialização e título de especialista pelo CFM', 'Alta demanda no SUS.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '62' OR nome = 'Urologia Pediátrica');

-- Ortopedia Pediátrica
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Ortopedia Pediátrica', '63', NULL,
    99, '63', '63', 'Cirúrgica', 'Ortopedia Pediátrica',
    true, true, 'Especialidade médica que trata doenças ortopédicas em crianças',
    'Malformações congênitas, doenças ortopédicas pediátricas', 'Residência médica em Ortopedia (3 anos) + especialização e título de especialista pelo CFM', 'Alta demanda no SUS.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '63' OR nome = 'Ortopedia Pediátrica');

-- Traumatologia e Ortopedia
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Traumatologia e Ortopedia', '64', NULL,
    99, '64', '64', 'Cirúrgica', 'Traumatologia',
    true, true, 'Especialidade médica que trata traumas e lesões ortopédicas',
    'Traumatologia, tratamento de fraturas, lesões esportivas', 'Residência médica em Ortopedia (3 anos) e título de especialista pelo CFM', 'Alta demanda no SUS.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '64' OR nome = 'Traumatologia e Ortopedia');

-- Anestesiologia Pediátrica
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Anestesiologia Pediátrica', '65', NULL,
    99, '65', '65', 'Perioperatória', 'Anestesia Pediátrica',
    true, true, 'Especialidade médica que administra anestesia em crianças',
    'Anestesia pediátrica, anestesia neonatal', 'Residência médica em Anestesiologia (3 anos) + especialização e título de especialista pelo CFM', 'Alta complexidade.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '65' OR nome = 'Anestesiologia Pediátrica');

-- Medicina da Dor
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Medicina da Dor', '66', NULL,
    99, '66', '66', 'Clínica', 'Medicina da Dor',
    true, true, 'Especialidade médica que trata dor crônica e aguda',
    'Tratamento da dor, clínica da dor, dor crônica', 'Residência médica em Anestesiologia ou Clínica Médica (3 anos) + especialização e título de especialista pelo CFM', 'Crescente demanda no Brasil.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '66' OR nome = 'Medicina da Dor');

-- Ultrassonografia
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Ultrassonografia', '67', NULL,
    99, '67', '67', 'Diagnóstica', 'Ultrassonografia',
    true, true, 'Especialidade médica que realiza exames de ultrassom',
    'Ultrassonografia geral, obstétrica, doppler, ecocardiografia', 'Residência médica em Radiologia (3 anos) + especialização e título de especialista pelo CFM', 'Alta demanda no SUS.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '67' OR nome = 'Ultrassonografia');

-- Ressonância Magnética
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Ressonância Magnética', '68', NULL,
    99, '68', '68', 'Diagnóstica', 'Ressonância Magnética',
    true, true, 'Especialidade médica que realiza exames de ressonância magnética',
    'Ressonância magnética, neuroimagem, imagens por ressonância', 'Residência médica em Radiologia (3 anos) + especialização e título de especialista pelo CFM', 'Alta complexidade.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '68' OR nome = 'Ressonância Magnética');

-- Tomografia Computadorizada
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Tomografia Computadorizada', '69', NULL,
    99, '69', '69', 'Diagnóstica', 'Tomografia Computadorizada',
    true, true, 'Especialidade médica que realiza exames de tomografia computadorizada',
    'Tomografia computadorizada, TC, imagens tomográficas', 'Residência médica em Radiologia (3 anos) + especialização e título de especialista pelo CFM', 'Alta demanda no SUS.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '69' OR nome = 'Tomografia Computadorizada');

-- Anatomia Patológica
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Anatomia Patológica', '70', NULL,
    99, '70', '70', 'Diagnóstica', 'Anatomia Patológica',
    true, true, 'Especialidade médica que estuda doenças através de análise de tecidos',
    'Anatomia patológica, biópsias, diagnóstico histopatológico', 'Residência médica em Patologia (3 anos) e título de especialista pelo CFM', 'Fundamental para diagnóstico de câncer.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '70' OR nome = 'Anatomia Patológica');

-- Citopatologia
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Citopatologia', '71', NULL,
    99, '71', '71', 'Diagnóstica', 'Citopatologia',
    true, true, 'Especialidade médica que estuda doenças através de análise de células',
    'Citopatologia, Papanicolau, citologia', 'Residência médica em Patologia (3 anos) + especialização e título de especialista pelo CFM', 'Fundamental para prevenção de câncer de colo do útero.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '71' OR nome = 'Citopatologia');

-- Acupuntura
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Acupuntura', '72', NULL,
    99, '72', '72', 'Terapêutica', 'Acupuntura',
    false, true, 'Especialidade médica que utiliza acupuntura para tratamento',
    'Acupuntura médica, tratamento com agulhas', 'Especialização em Acupuntura e título de especialista pelo CFM', 'Crescente demanda no Brasil.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '72' OR nome = 'Acupuntura');

-- Homeopatia
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Homeopatia', '73', NULL,
    99, '73', '73', 'Terapêutica', 'Homeopatia',
    false, true, 'Especialidade médica que utiliza homeopatia para tratamento',
    'Homeopatia médica, tratamento homeopático', 'Especialização em Homeopatia e título de especialista pelo CFM', 'Reconhecida pelo CFM.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '73' OR nome = 'Homeopatia');

-- Medicina Paliativa
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Medicina Paliativa', '74', NULL,
    99, '74', '74', 'Clínica', 'Cuidados Paliativos',
    true, true, 'Especialidade médica que trata pacientes com doenças graves e terminais',
    'Cuidados paliativos, tratamento da dor, qualidade de vida', 'Residência médica em Clínica Médica ou especialização e título de especialista pelo CFM', 'Crescente demanda no Brasil.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '74' OR nome = 'Medicina Paliativa');

-- Medicina Aeroespacial
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Medicina Aeroespacial', '75', NULL,
    99, '75', '75', 'Preventiva', 'Medicina Aeroespacial',
    true, true, 'Especialidade médica que trata saúde de tripulantes e passageiros',
    'Medicina aeronáutica, medicina espacial, perícia aeronáutica', 'Especialização em Medicina Aeroespacial e título de especialista pelo CFM', 'Especialidade específica.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '75' OR nome = 'Medicina Aeroespacial');

-- Medicina de Tráfego
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Medicina de Tráfego', '76', NULL,
    99, '76', '76', 'Preventiva', 'Medicina de Tráfego',
    true, true, 'Especialidade médica que trata saúde de condutores',
    'Perícia médica para CNH, medicina de tráfego', 'Especialização em Medicina de Tráfego e título de especialista pelo CFM', 'Alta demanda no Brasil.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '76' OR nome = 'Medicina de Tráfego');

-- Medicina Física e Reabilitação
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Medicina Física e Reabilitação', '77', NULL,
    99, '77', '77', 'Terapêutica', 'Fisiatria',
    true, true, 'Especialidade médica que trata reabilitação e medicina física',
    'Reabilitação, medicina física, fisiatria', 'Residência médica em Medicina Física e Reabilitação (3 anos) e título de especialista pelo CFM', 'Alta demanda no SUS.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '77' OR nome = 'Medicina Física e Reabilitação');

-- Fisiatria
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Fisiatria', '78', NULL,
    99, '78', '78', 'Terapêutica', 'Fisiatria',
    true, true, 'Especialidade médica que trata reabilitação física',
    'Reabilitação física, medicina física', 'Residência médica em Medicina Física e Reabilitação (3 anos) e título de especialista pelo CFM', 'Alta demanda no SUS.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '78' OR nome = 'Fisiatria');

-- Genética Médica
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Genética Médica', '79', NULL,
    99, '79', '79', 'Clínica', 'Genética Médica',
    true, true, 'Especialidade médica que trata doenças genéticas',
    'Doenças genéticas, aconselhamento genético, genética médica', 'Residência médica em Genética Médica (3 anos) e título de especialista pelo CFM', 'Crescente demanda no Brasil.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '79' OR nome = 'Genética Médica');

-- Imunologia Clínica
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Imunologia Clínica', '80', NULL,
    99, '80', '80', 'Clínica', 'Imunologia Clínica',
    true, true, 'Especialidade médica que trata doenças imunológicas',
    'Doenças autoimunes, imunodeficiências, alergias', 'Residência médica em Clínica Médica (2 anos) + Residência em Imunologia (2 anos) ou título de especialista pelo CFM', 'Alta demanda no SUS.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '80' OR nome = 'Imunologia Clínica');

-- Medicina Estética
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Medicina Estética', '81', NULL,
    99, '81', '81', 'Estética', 'Medicina Estética',
    true, true, 'Especialidade médica que trata estética e beleza',
    'Medicina estética, procedimentos estéticos, rejuvenescimento', 'Especialização em Medicina Estética e título de especialista pelo CFM', 'Crescente demanda no Brasil.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '81' OR nome = 'Medicina Estética');

-- Medicina do Sono
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Medicina do Sono', '82', NULL,
    99, '82', '82', 'Clínica', 'Medicina do Sono',
    true, true, 'Especialidade médica que trata distúrbios do sono',
    'Apneia do sono, insônia, distúrbios do sono', 'Residência médica em Clínica Médica, Neurologia ou Pneumologia (2 anos) + especialização e título de especialista pelo CFM', 'Crescente demanda no Brasil.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '82' OR nome = 'Medicina do Sono');

-- Medicina de Emergência
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Medicina de Emergência', '83', NULL,
    99, '83', '83', 'Emergência', 'Medicina de Emergência',
    true, true, 'Especialidade médica dedicada ao atendimento de emergências',
    'Atendimento de emergência, pronto-socorro, SAMU', 'Residência médica em Medicina de Urgência (2 anos) e título de especialista pelo CFM', 'Alta demanda no SUS.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '83' OR nome = 'Medicina de Emergência');

-- Toxicologia Médica
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Toxicologia Médica', '84', NULL,
    99, '84', '84', 'Clínica', 'Toxicologia Médica',
    true, true, 'Especialidade médica que trata intoxicações',
    'Intoxicações, envenenamentos, toxicologia clínica', 'Residência médica em Clínica Médica (2 anos) + especialização e título de especialista pelo CFM', 'Alta demanda no SUS.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '84' OR nome = 'Toxicologia Médica');

-- Hepatologia
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Hepatologia', '85', NULL,
    99, '85', '85', 'Clínica', 'Hepatologia',
    true, true, 'Especialidade médica que trata doenças do fígado',
    'Hepatites, cirrose, doenças hepáticas', 'Residência médica em Gastroenterologia (2 anos) + especialização e título de especialista pelo CFM', 'Alta demanda no SUS.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '85' OR nome = 'Hepatologia');

-- Proctologia
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Proctologia', '86', NULL,
    99, '86', '86', 'Clínica e Cirúrgica', 'Proctologia',
    true, true, 'Especialidade médica que trata doenças do reto e ânus',
    'Doenças do reto, ânus, hemorroidas', 'Residência médica em Cirurgia Geral (3 anos) + especialização e título de especialista pelo CFM', 'Alta demanda no SUS.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '86' OR nome = 'Proctologia');

-- Flebologia
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Flebologia', '87', NULL,
    99, '87', '87', 'Clínica', 'Flebologia',
    true, true, 'Especialidade médica que trata doenças venosas',
    'Varizes, insuficiência venosa, flebologia', 'Residência médica em Angiologia ou Cirurgia Vascular (2 anos) + especialização e título de especialista pelo CFM', 'Alta demanda no SUS.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '87' OR nome = 'Flebologia');

-- Linfologia
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Linfologia', '88', NULL,
    99, '88', '88', 'Clínica', 'Linfologia',
    true, true, 'Especialidade médica que trata doenças do sistema linfático',
    'Linfedema, doenças linfáticas', 'Residência médica em Angiologia (2 anos) + especialização e título de especialista pelo CFM', 'Especialidade específica.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '88' OR nome = 'Linfologia');

-- Medicina Hiperbárica
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Medicina Hiperbárica', '89', NULL,
    99, '89', '89', 'Terapêutica', 'Medicina Hiperbárica',
    true, true, 'Especialidade médica que utiliza oxigenoterapia hiperbárica',
    'Oxigenoterapia hiperbárica, tratamento em câmaras hiperbáricas', 'Especialização em Medicina Hiperbárica e título de especialista pelo CFM', 'Especialidade específica.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '89' OR nome = 'Medicina Hiperbárica');

-- Medicina Tropical
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Medicina Tropical', '90', NULL,
    99, '90', '90', 'Clínica', 'Medicina Tropical',
    true, true, 'Especialidade médica que trata doenças tropicais',
    'Doenças tropicais, doenças infecciosas tropicais', 'Residência médica em Infectologia (2 anos) + especialização e título de especialista pelo CFM', 'Importante para região Norte do Brasil.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '90' OR nome = 'Medicina Tropical');

-- Medicina de Viagem
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Medicina de Viagem', '91', NULL,
    99, '91', '91', 'Preventiva', 'Medicina de Viagem',
    true, true, 'Especialidade médica que trata saúde de viajantes',
    'Medicina de viagem, saúde do viajante, vacinação para viagem', 'Especialização em Medicina de Viagem e título de especialista pelo CFM', 'Crescente demanda no Brasil.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '91' OR nome = 'Medicina de Viagem');

-- Medicina do Adolescente
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Medicina do Adolescente', '92', NULL,
    99, '92', '92', 'Clínica', 'Medicina do Adolescente',
    true, true, 'Especialidade médica que trata saúde de adolescentes',
    'Saúde do adolescente, medicina adolescente', 'Residência médica em Pediatria ou Clínica Médica (2 anos) + especialização e título de especialista pelo CFM', 'Crescente demanda no Brasil.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '92' OR nome = 'Medicina do Adolescente');

-- Medicina Fetal
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Medicina Fetal', '93', NULL,
    99, '93', '93', 'Clínica', 'Medicina Fetal',
    true, true, 'Especialidade médica que trata saúde fetal',
    'Medicina fetal, diagnóstico pré-natal, tratamento fetal', 'Residência médica em Ginecologia e Obstetrícia (3 anos) + especialização e título de especialista pelo CFM', 'Alta complexidade.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '93' OR nome = 'Medicina Fetal');

-- Medicina Perinatal
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Medicina Perinatal', '94', NULL,
    99, '94', '94', 'Clínica', 'Medicina Perinatal',
    true, true, 'Especialidade médica que trata saúde perinatal',
    'Medicina perinatal, período perinatal', 'Residência médica em Ginecologia e Obstetrícia (3 anos) + especialização e título de especialista pelo CFM', 'Fundamental para redução da mortalidade perinatal.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '94' OR nome = 'Medicina Perinatal');

-- Medicina Fetal e Perinatal
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Medicina Fetal e Perinatal', '95', NULL,
    99, '95', '95', 'Clínica', 'Medicina Fetal e Perinatal',
    true, true, 'Especialidade médica que trata saúde fetal e perinatal',
    'Medicina fetal e perinatal, diagnóstico e tratamento pré-natal', 'Residência médica em Ginecologia e Obstetrícia (3 anos) + especialização e título de especialista pelo CFM', 'Alta complexidade.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '95' OR nome = 'Medicina Fetal e Perinatal');

-- Medicina de Longevidade
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Medicina de Longevidade', '96', NULL,
    99, '96', '96', 'Clínica', 'Medicina de Longevidade',
    true, true, 'Especialidade médica focada em longevidade e envelhecimento saudável',
    'Medicina antienvelhecimento, longevidade, envelhecimento saudável', 'Especialização em Medicina de Longevidade e título de especialista pelo CFM', 'Crescente demanda no Brasil.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '96' OR nome = 'Medicina de Longevidade');

-- Medicina Integrativa
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Medicina Integrativa', '97', NULL,
    99, '97', '97', 'Clínica', 'Medicina Integrativa',
    true, true, 'Especialidade médica que integra medicina convencional e complementar',
    'Medicina integrativa, medicina complementar', 'Especialização em Medicina Integrativa e título de especialista pelo CFM', 'Crescente demanda no Brasil.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '97' OR nome = 'Medicina Integrativa');

-- Medicina de Precisão
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Medicina de Precisão', '98', NULL,
    99, '98', '98', 'Clínica', 'Medicina de Precisão',
    true, true, 'Especialidade médica que utiliza genética e biomarcadores para tratamento personalizado',
    'Medicina personalizada, farmacogenética, medicina de precisão', 'Especialização em Medicina de Precisão e título de especialista pelo CFM', 'Especialidade emergente.'
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = '98' OR nome = 'Medicina de Precisão');

