-- Script de Seed: Vacinas (Escopo Global)
-- Cria catálogo de vacinas do PNI - dados globais sem tenant
-- Depende de: Fabricantes de Vacina (12-fabricantes-vacina.sql)
-- Gerado automaticamente pelo script generate_vacinas.py
-- Executado quando app.seed.enabled=true

-- BCG
INSERT INTO public.vacinas (
    id, criado_em, atualizado_em, ativo, nome, nome_comercial, codigo_interno,
    codigo_pni, codigo_sus, registro_anvisa, tipo, categoria, grupo_alvo,
    fabricante_id, via_administracao, unidade_medida, status, disponivel_uso,
    numero_doses, intervalo_doses_dias, dose_reforco, intervalo_reforco_anos, dose_ml,
    local_aplicacao_padrao, idade_minima_dias, idade_maxima_dias,
    temperatura_conservacao_min, temperatura_conservacao_max, tipo_conservacao,
    proteger_luz, agitar_antes_uso, calendario_basico, calendario_campanha, obrigatoria,
    gestante_pode, lactante_pode, imunocomprometido_pode, sincronizar_pni, descricao, indicacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'BCG', 'BCG', 'VAC-001',
    'PNI-001', 'SUS-001', '10101010101', 1, 'Rotina', 'Crianças',
    (SELECT id FROM public.fabricantes_vacina WHERE nome = 'Instituto Butantan' LIMIT 1),
    10, 13, 1, true,
    1, NULL, false,
    NULL, 0.1,
    'Braço direito', NULL, NULL,
    2.0, 8.0, 'Refrigerada (2-8°C)',
    false, false, true, false, true,
    false, false, false, true, 'Vacina contra tuberculose. Protege contra formas graves da doença, especialmente meningite tuberculosa e tuberculose miliar.', 'Aplicar ao nascer ou o mais precocemente possível, preferencialmente na maternidade.'
WHERE NOT EXISTS (SELECT 1 FROM public.vacinas WHERE codigo_pni = 'PNI-001' OR codigo_sus = 'SUS-001' OR nome = 'BCG');

-- Hepatite B
INSERT INTO public.vacinas (
    id, criado_em, atualizado_em, ativo, nome, nome_comercial, codigo_interno,
    codigo_pni, codigo_sus, registro_anvisa, tipo, categoria, grupo_alvo,
    fabricante_id, via_administracao, unidade_medida, status, disponivel_uso,
    numero_doses, intervalo_doses_dias, dose_reforco, intervalo_reforco_anos, dose_ml,
    local_aplicacao_padrao, idade_minima_dias, idade_maxima_dias,
    temperatura_conservacao_min, temperatura_conservacao_max, tipo_conservacao,
    proteger_luz, agitar_antes_uso, calendario_basico, calendario_campanha, obrigatoria,
    gestante_pode, lactante_pode, imunocomprometido_pode, sincronizar_pni, descricao, indicacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Hepatite B', 'Hepatite B Recombinante', 'VAC-002',
    'PNI-002', 'SUS-002', '10101010102', 2, 'Rotina', 'Crianças',
    (SELECT id FROM public.fabricantes_vacina WHERE nome = 'Instituto Butantan' LIMIT 1),
    7, 13, 1, true,
    3, 30, false,
    NULL, 0.5,
    'Coxa (lateral)', NULL, NULL,
    2.0, 8.0, 'Refrigerada (2-8°C)',
    true, false, true, false, true,
    false, false, false, true, 'Vacina contra hepatite B. Protege contra infecção pelo vírus da hepatite B.', 'Aplicar ao nascer (preferencialmente nas primeiras 12 horas de vida), 1 mês e 6 meses de idade.'
WHERE NOT EXISTS (SELECT 1 FROM public.vacinas WHERE codigo_pni = 'PNI-002' OR codigo_sus = 'SUS-002' OR nome = 'Hepatite B');

-- Pentavalente
INSERT INTO public.vacinas (
    id, criado_em, atualizado_em, ativo, nome, nome_comercial, codigo_interno,
    codigo_pni, codigo_sus, registro_anvisa, tipo, categoria, grupo_alvo,
    fabricante_id, via_administracao, unidade_medida, status, disponivel_uso,
    numero_doses, intervalo_doses_dias, dose_reforco, intervalo_reforco_anos, dose_ml,
    local_aplicacao_padrao, idade_minima_dias, idade_maxima_dias,
    temperatura_conservacao_min, temperatura_conservacao_max, tipo_conservacao,
    proteger_luz, agitar_antes_uso, calendario_basico, calendario_campanha, obrigatoria,
    gestante_pode, lactante_pode, imunocomprometido_pode, sincronizar_pni, descricao, indicacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Pentavalente', 'DTP + Hib + Hepatite B', 'VAC-003',
    'PNI-003', 'SUS-003', '10101010103', 3, 'Rotina', 'Crianças',
    (SELECT id FROM public.fabricantes_vacina WHERE nome = 'Instituto Butantan' LIMIT 1),
    7, 13, 1, true,
    3, 60, false,
    NULL, 0.5,
    'Coxa (lateral)', 60, NULL,
    2.0, 8.0, 'Refrigerada (2-8°C)',
    true, true, true, false, true,
    false, false, false, true, 'Vacina combinada contra difteria, tétano, coqueluche, Haemophilus influenzae tipo b e hepatite B.', 'Aplicar aos 2, 4 e 6 meses de idade.'
WHERE NOT EXISTS (SELECT 1 FROM public.vacinas WHERE codigo_pni = 'PNI-003' OR codigo_sus = 'SUS-003' OR nome = 'Pentavalente');

-- VIP - Poliomielite Inativada
INSERT INTO public.vacinas (
    id, criado_em, atualizado_em, ativo, nome, nome_comercial, codigo_interno,
    codigo_pni, codigo_sus, registro_anvisa, tipo, categoria, grupo_alvo,
    fabricante_id, via_administracao, unidade_medida, status, disponivel_uso,
    numero_doses, intervalo_doses_dias, dose_reforco, intervalo_reforco_anos, dose_ml,
    local_aplicacao_padrao, idade_minima_dias, idade_maxima_dias,
    temperatura_conservacao_min, temperatura_conservacao_max, tipo_conservacao,
    proteger_luz, agitar_antes_uso, calendario_basico, calendario_campanha, obrigatoria,
    gestante_pode, lactante_pode, imunocomprometido_pode, sincronizar_pni, descricao, indicacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'VIP - Poliomielite Inativada', 'VIP', 'VAC-004',
    'PNI-004', 'SUS-004', '10101010104', 8, 'Rotina', 'Crianças',
    (SELECT id FROM public.fabricantes_vacina WHERE nome = 'Instituto Butantan' LIMIT 1),
    7, 13, 1, true,
    3, 60, true,
    4, 0.5,
    'Coxa (lateral)', 60, NULL,
    2.0, 8.0, 'Refrigerada (2-8°C)',
    true, true, true, false, true,
    false, false, false, true, 'Vacina inativada contra poliomielite. Protege contra paralisia infantil.', 'Aplicar aos 2, 4 e 6 meses de idade. Reforço aos 15 meses e 4 anos.'
WHERE NOT EXISTS (SELECT 1 FROM public.vacinas WHERE codigo_pni = 'PNI-004' OR codigo_sus = 'SUS-004' OR nome = 'VIP - Poliomielite Inativada');

-- VOP - Poliomielite Oral
INSERT INTO public.vacinas (
    id, criado_em, atualizado_em, ativo, nome, nome_comercial, codigo_interno,
    codigo_pni, codigo_sus, registro_anvisa, tipo, categoria, grupo_alvo,
    fabricante_id, via_administracao, unidade_medida, status, disponivel_uso,
    numero_doses, intervalo_doses_dias, dose_reforco, intervalo_reforco_anos, dose_ml,
    local_aplicacao_padrao, idade_minima_dias, idade_maxima_dias,
    temperatura_conservacao_min, temperatura_conservacao_max, tipo_conservacao,
    proteger_luz, agitar_antes_uso, calendario_basico, calendario_campanha, obrigatoria,
    gestante_pode, lactante_pode, imunocomprometido_pode, sincronizar_pni, descricao, indicacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'VOP - Poliomielite Oral', 'Sabin', 'VAC-005',
    'PNI-005', 'SUS-005', '10101010105', 9, 'Rotina', 'Crianças',
    (SELECT id FROM public.fabricantes_vacina WHERE nome = 'Bio-Manguinhos / Fiocruz' LIMIT 1),
    1, 13, 1, true,
    3, 60, true,
    4, 0.1,
    'Oral', 60, NULL,
    -15.0, -15.0, 'Congelada (-15°C)',
    false, false, true, false, true,
    false, false, false, true, 'Vacina oral contra poliomielite. Protege contra paralisia infantil.', 'Aplicar aos 2, 4 e 6 meses de idade. Reforço aos 15 meses e 4 anos.'
WHERE NOT EXISTS (SELECT 1 FROM public.vacinas WHERE codigo_pni = 'PNI-005' OR codigo_sus = 'SUS-005' OR nome = 'VOP - Poliomielite Oral');

-- Rotavírus Humano
INSERT INTO public.vacinas (
    id, criado_em, atualizado_em, ativo, nome, nome_comercial, codigo_interno,
    codigo_pni, codigo_sus, registro_anvisa, tipo, categoria, grupo_alvo,
    fabricante_id, via_administracao, unidade_medida, status, disponivel_uso,
    numero_doses, intervalo_doses_dias, dose_reforco, intervalo_reforco_anos, dose_ml,
    local_aplicacao_padrao, idade_minima_dias, idade_maxima_dias,
    temperatura_conservacao_min, temperatura_conservacao_max, tipo_conservacao,
    proteger_luz, agitar_antes_uso, calendario_basico, calendario_campanha, obrigatoria,
    gestante_pode, lactante_pode, imunocomprometido_pode, sincronizar_pni, descricao, indicacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Rotavírus Humano', 'Rotavírus Monovalente', 'VAC-006',
    'PNI-006', 'SUS-006', '10101010106', 10, 'Rotina', 'Crianças',
    (SELECT id FROM public.fabricantes_vacina WHERE nome = 'GlaxoSmithKline Brasil Ltda' LIMIT 1),
    1, 13, 1, true,
    2, 60, false,
    NULL, 1.5,
    'Oral', 60, 210,
    2.0, 8.0, 'Refrigerada (2-8°C)',
    true, false, true, false, true,
    false, false, false, true, 'Vacina contra rotavírus. Protege contra gastroenterite causada por rotavírus.', 'Aplicar aos 2 e 4 meses de idade. Primeira dose até 3 meses e 15 dias, última dose até 7 meses e 29 dias.'
WHERE NOT EXISTS (SELECT 1 FROM public.vacinas WHERE codigo_pni = 'PNI-006' OR codigo_sus = 'SUS-006' OR nome = 'Rotavírus Humano');

-- Pneumocócica 10-valente
INSERT INTO public.vacinas (
    id, criado_em, atualizado_em, ativo, nome, nome_comercial, codigo_interno,
    codigo_pni, codigo_sus, registro_anvisa, tipo, categoria, grupo_alvo,
    fabricante_id, via_administracao, unidade_medida, status, disponivel_uso,
    numero_doses, intervalo_doses_dias, dose_reforco, intervalo_reforco_anos, dose_ml,
    local_aplicacao_padrao, idade_minima_dias, idade_maxima_dias,
    temperatura_conservacao_min, temperatura_conservacao_max, tipo_conservacao,
    proteger_luz, agitar_antes_uso, calendario_basico, calendario_campanha, obrigatoria,
    gestante_pode, lactante_pode, imunocomprometido_pode, sincronizar_pni, descricao, indicacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Pneumocócica 10-valente', 'Pneumo 10', 'VAC-007',
    'PNI-007', 'SUS-007', '10101010107', 14, 'Rotina', 'Crianças',
    (SELECT id FROM public.fabricantes_vacina WHERE nome = 'GlaxoSmithKline Brasil Ltda' LIMIT 1),
    7, 13, 1, true,
    2, 60, true,
    12, 0.5,
    'Coxa (lateral)', 60, NULL,
    2.0, 8.0, 'Refrigerada (2-8°C)',
    true, true, true, false, true,
    false, false, false, true, 'Vacina contra doenças pneumocócicas (pneumonia, meningite, otite).', 'Aplicar aos 2 e 4 meses de idade. Reforço aos 12 meses.'
WHERE NOT EXISTS (SELECT 1 FROM public.vacinas WHERE codigo_pni = 'PNI-007' OR codigo_sus = 'SUS-007' OR nome = 'Pneumocócica 10-valente');

-- Meningocócica C
INSERT INTO public.vacinas (
    id, criado_em, atualizado_em, ativo, nome, nome_comercial, codigo_interno,
    codigo_pni, codigo_sus, registro_anvisa, tipo, categoria, grupo_alvo,
    fabricante_id, via_administracao, unidade_medida, status, disponivel_uso,
    numero_doses, intervalo_doses_dias, dose_reforco, intervalo_reforco_anos, dose_ml,
    local_aplicacao_padrao, idade_minima_dias, idade_maxima_dias,
    temperatura_conservacao_min, temperatura_conservacao_max, tipo_conservacao,
    proteger_luz, agitar_antes_uso, calendario_basico, calendario_campanha, obrigatoria,
    gestante_pode, lactante_pode, imunocomprometido_pode, sincronizar_pni, descricao, indicacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Meningocócica C', 'Meningo C', 'VAC-008',
    'PNI-008', 'SUS-008', '10101010108', 11, 'Rotina', 'Crianças',
    (SELECT id FROM public.fabricantes_vacina WHERE nome = 'GlaxoSmithKline Brasil Ltda' LIMIT 1),
    7, 13, 1, true,
    2, 60, true,
    12, 0.5,
    'Coxa (lateral)', 90, NULL,
    2.0, 8.0, 'Refrigerada (2-8°C)',
    true, true, true, false, true,
    false, false, false, true, 'Vacina contra meningite meningocócica tipo C.', 'Aplicar aos 3 e 5 meses de idade. Reforço aos 12 meses.'
WHERE NOT EXISTS (SELECT 1 FROM public.vacinas WHERE codigo_pni = 'PNI-008' OR codigo_sus = 'SUS-008' OR nome = 'Meningocócica C');

-- Febre Amarela
INSERT INTO public.vacinas (
    id, criado_em, atualizado_em, ativo, nome, nome_comercial, codigo_interno,
    codigo_pni, codigo_sus, registro_anvisa, tipo, categoria, grupo_alvo,
    fabricante_id, via_administracao, unidade_medida, status, disponivel_uso,
    numero_doses, intervalo_doses_dias, dose_reforco, intervalo_reforco_anos, dose_ml,
    local_aplicacao_padrao, idade_minima_dias, idade_maxima_dias,
    temperatura_conservacao_min, temperatura_conservacao_max, tipo_conservacao,
    proteger_luz, agitar_antes_uso, calendario_basico, calendario_campanha, obrigatoria,
    gestante_pode, lactante_pode, imunocomprometido_pode, sincronizar_pni, descricao, indicacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Febre Amarela', 'Febre Amarela', 'VAC-009',
    'PNI-009', 'SUS-009', '10101010109', 17, 'Rotina', 'Crianças e Adultos',
    (SELECT id FROM public.fabricantes_vacina WHERE nome = 'Instituto Butantan' LIMIT 1),
    9, 13, 1, true,
    1, NULL, true,
    10, 0.5,
    'Braço direito', 270, NULL,
    2.0, 8.0, 'Refrigerada (2-8°C)',
    true, true, true, false, true,
    false, false, false, true, 'Vacina contra febre amarela. Protege contra a febre amarela silvestre e urbana.', 'Aplicar aos 9 meses de idade. Reforço aos 4 anos e a cada 10 anos.'
WHERE NOT EXISTS (SELECT 1 FROM public.vacinas WHERE codigo_pni = 'PNI-009' OR codigo_sus = 'SUS-009' OR nome = 'Febre Amarela');

-- Tríplice Viral
INSERT INTO public.vacinas (
    id, criado_em, atualizado_em, ativo, nome, nome_comercial, codigo_interno,
    codigo_pni, codigo_sus, registro_anvisa, tipo, categoria, grupo_alvo,
    fabricante_id, via_administracao, unidade_medida, status, disponivel_uso,
    numero_doses, intervalo_doses_dias, dose_reforco, intervalo_reforco_anos, dose_ml,
    local_aplicacao_padrao, idade_minima_dias, idade_maxima_dias,
    temperatura_conservacao_min, temperatura_conservacao_max, tipo_conservacao,
    proteger_luz, agitar_antes_uso, calendario_basico, calendario_campanha, obrigatoria,
    gestante_pode, lactante_pode, imunocomprometido_pode, sincronizar_pni, descricao, indicacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Tríplice Viral', 'Sarampo, Caxumba e Rubéola', 'VAC-010',
    'PNI-010', 'SUS-010', '10101010110', 18, 'Rotina', 'Crianças',
    (SELECT id FROM public.fabricantes_vacina WHERE nome = 'Instituto Butantan' LIMIT 1),
    9, 13, 1, true,
    2, 90, false,
    NULL, 0.5,
    'Braço direito', 360, NULL,
    2.0, 8.0, 'Refrigerada (2-8°C)',
    true, true, true, false, true,
    false, false, false, true, 'Vacina contra sarampo, caxumba e rubéola.', 'Aplicar aos 12 meses e 15 meses de idade.'
WHERE NOT EXISTS (SELECT 1 FROM public.vacinas WHERE codigo_pni = 'PNI-010' OR codigo_sus = 'SUS-010' OR nome = 'Tríplice Viral');

-- Tetra Viral
INSERT INTO public.vacinas (
    id, criado_em, atualizado_em, ativo, nome, nome_comercial, codigo_interno,
    codigo_pni, codigo_sus, registro_anvisa, tipo, categoria, grupo_alvo,
    fabricante_id, via_administracao, unidade_medida, status, disponivel_uso,
    numero_doses, intervalo_doses_dias, dose_reforco, intervalo_reforco_anos, dose_ml,
    local_aplicacao_padrao, idade_minima_dias, idade_maxima_dias,
    temperatura_conservacao_min, temperatura_conservacao_max, tipo_conservacao,
    proteger_luz, agitar_antes_uso, calendario_basico, calendario_campanha, obrigatoria,
    gestante_pode, lactante_pode, imunocomprometido_pode, sincronizar_pni, descricao, indicacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Tetra Viral', 'Sarampo, Caxumba, Rubéola e Varicela', 'VAC-011',
    'PNI-011', 'SUS-011', '10101010111', 19, 'Rotina', 'Crianças',
    (SELECT id FROM public.fabricantes_vacina WHERE nome = 'GlaxoSmithKline Brasil Ltda' LIMIT 1),
    9, 13, 1, true,
    1, NULL, false,
    NULL, 0.5,
    'Braço direito', 450, NULL,
    2.0, 8.0, 'Refrigerada (2-8°C)',
    true, true, true, false, true,
    false, false, false, true, 'Vacina combinada contra sarampo, caxumba, rubéola e varicela.', 'Aplicar aos 15 meses de idade, substituindo a segunda dose da tríplice viral.'
WHERE NOT EXISTS (SELECT 1 FROM public.vacinas WHERE codigo_pni = 'PNI-011' OR codigo_sus = 'SUS-011' OR nome = 'Tetra Viral');

-- Varicela
INSERT INTO public.vacinas (
    id, criado_em, atualizado_em, ativo, nome, nome_comercial, codigo_interno,
    codigo_pni, codigo_sus, registro_anvisa, tipo, categoria, grupo_alvo,
    fabricante_id, via_administracao, unidade_medida, status, disponivel_uso,
    numero_doses, intervalo_doses_dias, dose_reforco, intervalo_reforco_anos, dose_ml,
    local_aplicacao_padrao, idade_minima_dias, idade_maxima_dias,
    temperatura_conservacao_min, temperatura_conservacao_max, tipo_conservacao,
    proteger_luz, agitar_antes_uso, calendario_basico, calendario_campanha, obrigatoria,
    gestante_pode, lactante_pode, imunocomprometido_pode, sincronizar_pni, descricao, indicacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Varicela', 'Varicela', 'VAC-012',
    'PNI-012', 'SUS-012', '10101010112', 20, 'Rotina', 'Crianças',
    (SELECT id FROM public.fabricantes_vacina WHERE nome = 'GlaxoSmithKline Brasil Ltda' LIMIT 1),
    9, 13, 1, true,
    1, NULL, false,
    NULL, 0.5,
    'Braço direito', 450, NULL,
    2.0, 8.0, 'Refrigerada (2-8°C)',
    true, true, true, false, true,
    false, false, false, true, 'Vacina contra varicela (catapora).', 'Aplicar aos 15 meses de idade, se não recebeu a tetra viral.'
WHERE NOT EXISTS (SELECT 1 FROM public.vacinas WHERE codigo_pni = 'PNI-012' OR codigo_sus = 'SUS-012' OR nome = 'Varicela');

-- Hepatite A
INSERT INTO public.vacinas (
    id, criado_em, atualizado_em, ativo, nome, nome_comercial, codigo_interno,
    codigo_pni, codigo_sus, registro_anvisa, tipo, categoria, grupo_alvo,
    fabricante_id, via_administracao, unidade_medida, status, disponivel_uso,
    numero_doses, intervalo_doses_dias, dose_reforco, intervalo_reforco_anos, dose_ml,
    local_aplicacao_padrao, idade_minima_dias, idade_maxima_dias,
    temperatura_conservacao_min, temperatura_conservacao_max, tipo_conservacao,
    proteger_luz, agitar_antes_uso, calendario_basico, calendario_campanha, obrigatoria,
    gestante_pode, lactante_pode, imunocomprometido_pode, sincronizar_pni, descricao, indicacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Hepatite A', 'Hepatite A', 'VAC-013',
    'PNI-013', 'SUS-013', '10101010113', 21, 'Rotina', 'Crianças',
    (SELECT id FROM public.fabricantes_vacina WHERE nome = 'Instituto Butantan' LIMIT 1),
    7, 13, 1, true,
    1, NULL, false,
    NULL, 0.5,
    'Braço direito', 540, NULL,
    2.0, 8.0, 'Refrigerada (2-8°C)',
    true, true, true, false, true,
    false, false, false, true, 'Vacina contra hepatite A. Protege contra infecção pelo vírus da hepatite A.', 'Aplicar aos 15 meses de idade.'
WHERE NOT EXISTS (SELECT 1 FROM public.vacinas WHERE codigo_pni = 'PNI-013' OR codigo_sus = 'SUS-013' OR nome = 'Hepatite A');

-- HPV Quadrivalente
INSERT INTO public.vacinas (
    id, criado_em, atualizado_em, ativo, nome, nome_comercial, codigo_interno,
    codigo_pni, codigo_sus, registro_anvisa, tipo, categoria, grupo_alvo,
    fabricante_id, via_administracao, unidade_medida, status, disponivel_uso,
    numero_doses, intervalo_doses_dias, dose_reforco, intervalo_reforco_anos, dose_ml,
    local_aplicacao_padrao, idade_minima_dias, idade_maxima_dias,
    temperatura_conservacao_min, temperatura_conservacao_max, tipo_conservacao,
    proteger_luz, agitar_antes_uso, calendario_basico, calendario_campanha, obrigatoria,
    gestante_pode, lactante_pode, imunocomprometido_pode, sincronizar_pni, descricao, indicacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'HPV Quadrivalente', 'HPV 4', 'VAC-014',
    'PNI-014', 'SUS-014', '10101010114', 22, 'Rotina', 'Adolescentes',
    (SELECT id FROM public.fabricantes_vacina WHERE nome = 'Merck Sharp & Dohme' LIMIT 1),
    7, 13, 1, true,
    2, 180, false,
    NULL, 0.5,
    'Braço direito', 3285, 5110,
    2.0, 8.0, 'Refrigerada (2-8°C)',
    true, true, true, false, true,
    false, false, false, true, 'Vacina contra papilomavírus humano (tipos 6, 11, 16 e 18). Protege contra câncer de colo do útero e verrugas genitais.', 'Aplicar em meninas de 9 a 14 anos e meninos de 11 a 14 anos. Duas doses com intervalo de 6 meses.'
WHERE NOT EXISTS (SELECT 1 FROM public.vacinas WHERE codigo_pni = 'PNI-014' OR codigo_sus = 'SUS-014' OR nome = 'HPV Quadrivalente');

-- DTP
INSERT INTO public.vacinas (
    id, criado_em, atualizado_em, ativo, nome, nome_comercial, codigo_interno,
    codigo_pni, codigo_sus, registro_anvisa, tipo, categoria, grupo_alvo,
    fabricante_id, via_administracao, unidade_medida, status, disponivel_uso,
    numero_doses, intervalo_doses_dias, dose_reforco, intervalo_reforco_anos, dose_ml,
    local_aplicacao_padrao, idade_minima_dias, idade_maxima_dias,
    temperatura_conservacao_min, temperatura_conservacao_max, tipo_conservacao,
    proteger_luz, agitar_antes_uso, calendario_basico, calendario_campanha, obrigatoria,
    gestante_pode, lactante_pode, imunocomprometido_pode, sincronizar_pni, descricao, indicacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'DTP', 'Difteria, Tétano e Coqueluche', 'VAC-015',
    'PNI-015', 'SUS-015', '10101010115', 4, 'Rotina', 'Crianças',
    (SELECT id FROM public.fabricantes_vacina WHERE nome = 'Instituto Butantan' LIMIT 1),
    7, 13, 1, true,
    1, NULL, true,
    4, 0.5,
    'Coxa (lateral)', 540, NULL,
    2.0, 8.0, 'Refrigerada (2-8°C)',
    true, true, true, false, true,
    false, false, false, true, 'Vacina contra difteria, tétano e coqueluche. Reforço da pentavalente.', 'Aplicar aos 15 meses e 4 anos de idade.'
WHERE NOT EXISTS (SELECT 1 FROM public.vacinas WHERE codigo_pni = 'PNI-015' OR codigo_sus = 'SUS-015' OR nome = 'DTP');

-- Dengue
INSERT INTO public.vacinas (
    id, criado_em, atualizado_em, ativo, nome, nome_comercial, codigo_interno,
    codigo_pni, codigo_sus, registro_anvisa, tipo, categoria, grupo_alvo,
    fabricante_id, via_administracao, unidade_medida, status, disponivel_uso,
    numero_doses, intervalo_doses_dias, dose_reforco, intervalo_reforco_anos, dose_ml,
    local_aplicacao_padrao, idade_minima_dias, idade_maxima_dias,
    temperatura_conservacao_min, temperatura_conservacao_max, tipo_conservacao,
    proteger_luz, agitar_antes_uso, calendario_basico, calendario_campanha, obrigatoria,
    gestante_pode, lactante_pode, imunocomprometido_pode, sincronizar_pni, descricao, indicacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Dengue', 'Dengvaxia', 'VAC-016',
    'PNI-016', 'SUS-016', '10101010116', 24, 'Especial', 'Crianças e Adultos',
    (SELECT id FROM public.fabricantes_vacina WHERE nome = 'Sanofi Pasteur Ltda' LIMIT 1),
    9, 13, 1, true,
    3, 180, false,
    NULL, 0.5,
    'Braço direito', 3285, NULL,
    2.0, 8.0, 'Refrigerada (2-8°C)',
    true, true, false, false, false,
    false, false, false, true, 'Vacina contra dengue. Protege contra os 4 sorotipos do vírus da dengue.', 'Aplicar em pessoas de 9 a 45 anos que já tiveram dengue confirmada. Três doses com intervalo de 6 meses.'
WHERE NOT EXISTS (SELECT 1 FROM public.vacinas WHERE codigo_pni = 'PNI-016' OR codigo_sus = 'SUS-016' OR nome = 'Dengue');

-- Dupla Adulto
INSERT INTO public.vacinas (
    id, criado_em, atualizado_em, ativo, nome, nome_comercial, codigo_interno,
    codigo_pni, codigo_sus, registro_anvisa, tipo, categoria, grupo_alvo,
    fabricante_id, via_administracao, unidade_medida, status, disponivel_uso,
    numero_doses, intervalo_doses_dias, dose_reforco, intervalo_reforco_anos, dose_ml,
    local_aplicacao_padrao, idade_minima_dias, idade_maxima_dias,
    temperatura_conservacao_min, temperatura_conservacao_max, tipo_conservacao,
    proteger_luz, agitar_antes_uso, calendario_basico, calendario_campanha, obrigatoria,
    gestante_pode, lactante_pode, imunocomprometido_pode, sincronizar_pni, descricao, indicacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Dupla Adulto', 'DT - Difteria e Tétano', 'VAC-017',
    'PNI-017', 'SUS-017', '10101010117', 30, 'Rotina', 'Adultos',
    (SELECT id FROM public.fabricantes_vacina WHERE nome = 'Instituto Butantan' LIMIT 1),
    7, 13, 1, true,
    1, NULL, true,
    10, 0.5,
    'Braço direito', 5475, NULL,
    2.0, 8.0, 'Refrigerada (2-8°C)',
    true, true, true, false, true,
    false, false, false, true, 'Vacina contra difteria e tétano para adultos.', 'Aplicar a cada 10 anos como reforço.'
WHERE NOT EXISTS (SELECT 1 FROM public.vacinas WHERE codigo_pni = 'PNI-017' OR codigo_sus = 'SUS-017' OR nome = 'Dupla Adulto');

-- Hepatite B Adulto
INSERT INTO public.vacinas (
    id, criado_em, atualizado_em, ativo, nome, nome_comercial, codigo_interno,
    codigo_pni, codigo_sus, registro_anvisa, tipo, categoria, grupo_alvo,
    fabricante_id, via_administracao, unidade_medida, status, disponivel_uso,
    numero_doses, intervalo_doses_dias, dose_reforco, intervalo_reforco_anos, dose_ml,
    local_aplicacao_padrao, idade_minima_dias, idade_maxima_dias,
    temperatura_conservacao_min, temperatura_conservacao_max, tipo_conservacao,
    proteger_luz, agitar_antes_uso, calendario_basico, calendario_campanha, obrigatoria,
    gestante_pode, lactante_pode, imunocomprometido_pode, sincronizar_pni, descricao, indicacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Hepatite B Adulto', 'Hepatite B Recombinante', 'VAC-018',
    'PNI-018', 'SUS-018', '10101010118', 31, 'Rotina', 'Adultos',
    (SELECT id FROM public.fabricantes_vacina WHERE nome = 'Instituto Butantan' LIMIT 1),
    7, 13, 1, true,
    3, 30, false,
    NULL, 1.0,
    'Braço direito', 5475, NULL,
    2.0, 8.0, 'Refrigerada (2-8°C)',
    true, true, true, false, true,
    false, false, false, true, 'Vacina contra hepatite B para adultos não vacinados na infância.', 'Aplicar três doses com intervalo de 1 mês entre a primeira e segunda, e 6 meses entre a primeira e terceira.'
WHERE NOT EXISTS (SELECT 1 FROM public.vacinas WHERE codigo_pni = 'PNI-018' OR codigo_sus = 'SUS-018' OR nome = 'Hepatite B Adulto');

-- Febre Amarela Adulto
INSERT INTO public.vacinas (
    id, criado_em, atualizado_em, ativo, nome, nome_comercial, codigo_interno,
    codigo_pni, codigo_sus, registro_anvisa, tipo, categoria, grupo_alvo,
    fabricante_id, via_administracao, unidade_medida, status, disponivel_uso,
    numero_doses, intervalo_doses_dias, dose_reforco, intervalo_reforco_anos, dose_ml,
    local_aplicacao_padrao, idade_minima_dias, idade_maxima_dias,
    temperatura_conservacao_min, temperatura_conservacao_max, tipo_conservacao,
    proteger_luz, agitar_antes_uso, calendario_basico, calendario_campanha, obrigatoria,
    gestante_pode, lactante_pode, imunocomprometido_pode, sincronizar_pni, descricao, indicacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Febre Amarela Adulto', 'Febre Amarela', 'VAC-019',
    'PNI-019', 'SUS-019', '10101010119', 32, 'Rotina', 'Adultos',
    (SELECT id FROM public.fabricantes_vacina WHERE nome = 'Instituto Butantan' LIMIT 1),
    9, 13, 1, true,
    1, NULL, true,
    10, 0.5,
    'Braço direito', 5475, NULL,
    2.0, 8.0, 'Refrigerada (2-8°C)',
    true, true, true, false, true,
    false, false, false, true, 'Vacina contra febre amarela para adultos. Reforço a cada 10 anos.', 'Aplicar em áreas de risco ou para viagens. Reforço a cada 10 anos.'
WHERE NOT EXISTS (SELECT 1 FROM public.vacinas WHERE codigo_pni = 'PNI-019' OR codigo_sus = 'SUS-019' OR nome = 'Febre Amarela Adulto');

-- Tríplice Viral Adulto
INSERT INTO public.vacinas (
    id, criado_em, atualizado_em, ativo, nome, nome_comercial, codigo_interno,
    codigo_pni, codigo_sus, registro_anvisa, tipo, categoria, grupo_alvo,
    fabricante_id, via_administracao, unidade_medida, status, disponivel_uso,
    numero_doses, intervalo_doses_dias, dose_reforco, intervalo_reforco_anos, dose_ml,
    local_aplicacao_padrao, idade_minima_dias, idade_maxima_dias,
    temperatura_conservacao_min, temperatura_conservacao_max, tipo_conservacao,
    proteger_luz, agitar_antes_uso, calendario_basico, calendario_campanha, obrigatoria,
    gestante_pode, lactante_pode, imunocomprometido_pode, sincronizar_pni, descricao, indicacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Tríplice Viral Adulto', 'Sarampo, Caxumba e Rubéola', 'VAC-020',
    'PNI-020', 'SUS-020', '10101010120', 33, 'Rotina', 'Adultos',
    (SELECT id FROM public.fabricantes_vacina WHERE nome = 'Instituto Butantan' LIMIT 1),
    9, 13, 1, true,
    1, NULL, false,
    NULL, 0.5,
    'Braço direito', 5475, NULL,
    2.0, 8.0, 'Refrigerada (2-8°C)',
    true, true, true, false, true,
    false, false, false, true, 'Vacina contra sarampo, caxumba e rubéola para adultos não vacinados.', 'Aplicar em adultos não vacinados ou com esquema incompleto.'
WHERE NOT EXISTS (SELECT 1 FROM public.vacinas WHERE codigo_pni = 'PNI-020' OR codigo_sus = 'SUS-020' OR nome = 'Tríplice Viral Adulto');

-- Pneumocócica 23-valente
INSERT INTO public.vacinas (
    id, criado_em, atualizado_em, ativo, nome, nome_comercial, codigo_interno,
    codigo_pni, codigo_sus, registro_anvisa, tipo, categoria, grupo_alvo,
    fabricante_id, via_administracao, unidade_medida, status, disponivel_uso,
    numero_doses, intervalo_doses_dias, dose_reforco, intervalo_reforco_anos, dose_ml,
    local_aplicacao_padrao, idade_minima_dias, idade_maxima_dias,
    temperatura_conservacao_min, temperatura_conservacao_max, tipo_conservacao,
    proteger_luz, agitar_antes_uso, calendario_basico, calendario_campanha, obrigatoria,
    gestante_pode, lactante_pode, imunocomprometido_pode, sincronizar_pni, descricao, indicacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Pneumocócica 23-valente', 'Pneumo 23', 'VAC-021',
    'PNI-021', 'SUS-021', '10101010121', 34, 'Rotina', 'Idosos',
    (SELECT id FROM public.fabricantes_vacina WHERE nome = 'Merck Sharp & Dohme' LIMIT 1),
    7, 13, 1, true,
    1, NULL, true,
    5, 0.5,
    'Braço direito', 18250, NULL,
    2.0, 8.0, 'Refrigerada (2-8°C)',
    true, true, true, false, true,
    false, false, false, true, 'Vacina contra doenças pneumocócicas para idosos e grupos de risco.', 'Aplicar em idosos a partir de 60 anos e grupos de risco. Reforço após 5 anos.'
WHERE NOT EXISTS (SELECT 1 FROM public.vacinas WHERE codigo_pni = 'PNI-021' OR codigo_sus = 'SUS-021' OR nome = 'Pneumocócica 23-valente');

-- Influenza
INSERT INTO public.vacinas (
    id, criado_em, atualizado_em, ativo, nome, nome_comercial, codigo_interno,
    codigo_pni, codigo_sus, registro_anvisa, tipo, categoria, grupo_alvo,
    fabricante_id, via_administracao, unidade_medida, status, disponivel_uso,
    numero_doses, intervalo_doses_dias, dose_reforco, intervalo_reforco_anos, dose_ml,
    local_aplicacao_padrao, idade_minima_dias, idade_maxima_dias,
    temperatura_conservacao_min, temperatura_conservacao_max, tipo_conservacao,
    proteger_luz, agitar_antes_uso, calendario_basico, calendario_campanha, obrigatoria,
    gestante_pode, lactante_pode, imunocomprometido_pode, sincronizar_pni, descricao, indicacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Influenza', 'Gripe', 'VAC-022',
    'PNI-022', 'SUS-022', '10101010122', 35, 'Campanha', 'Todas as idades',
    (SELECT id FROM public.fabricantes_vacina WHERE nome = 'Instituto Butantan' LIMIT 1),
    7, 13, 1, true,
    1, NULL, true,
    1, 0.5,
    'Braço direito', 180, NULL,
    2.0, 8.0, 'Refrigerada (2-8°C)',
    true, true, true, false, true,
    false, false, false, true, 'Vacina contra influenza (gripe). Composição atualizada anualmente.', 'Aplicar anualmente em campanhas de vacinação. Prioridade para grupos de risco.'
WHERE NOT EXISTS (SELECT 1 FROM public.vacinas WHERE codigo_pni = 'PNI-022' OR codigo_sus = 'SUS-022' OR nome = 'Influenza');

-- Herpes Zoster
INSERT INTO public.vacinas (
    id, criado_em, atualizado_em, ativo, nome, nome_comercial, codigo_interno,
    codigo_pni, codigo_sus, registro_anvisa, tipo, categoria, grupo_alvo,
    fabricante_id, via_administracao, unidade_medida, status, disponivel_uso,
    numero_doses, intervalo_doses_dias, dose_reforco, intervalo_reforco_anos, dose_ml,
    local_aplicacao_padrao, idade_minima_dias, idade_maxima_dias,
    temperatura_conservacao_min, temperatura_conservacao_max, tipo_conservacao,
    proteger_luz, agitar_antes_uso, calendario_basico, calendario_campanha, obrigatoria,
    gestante_pode, lactante_pode, imunocomprometido_pode, sincronizar_pni, descricao, indicacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Herpes Zoster', 'Herpes Zoster', 'VAC-023',
    'PNI-023', 'SUS-023', '10101010123', 36, 'Especial', 'Idosos',
    (SELECT id FROM public.fabricantes_vacina WHERE nome = 'GlaxoSmithKline Brasil Ltda' LIMIT 1),
    7, 13, 1, true,
    1, NULL, false,
    NULL, 0.5,
    'Braço direito', 18250, NULL,
    2.0, 8.0, 'Refrigerada (2-8°C)',
    true, true, false, false, false,
    false, false, false, true, 'Vacina contra herpes zoster (cobreiro). Reduz risco de neuralgia pós-herpética.', 'Aplicar em idosos a partir de 50 anos, especialmente a partir de 60 anos.'
WHERE NOT EXISTS (SELECT 1 FROM public.vacinas WHERE codigo_pni = 'PNI-023' OR codigo_sus = 'SUS-023' OR nome = 'Herpes Zoster');

-- Raiva
INSERT INTO public.vacinas (
    id, criado_em, atualizado_em, ativo, nome, nome_comercial, codigo_interno,
    codigo_pni, codigo_sus, registro_anvisa, tipo, categoria, grupo_alvo,
    fabricante_id, via_administracao, unidade_medida, status, disponivel_uso,
    numero_doses, intervalo_doses_dias, dose_reforco, intervalo_reforco_anos, dose_ml,
    local_aplicacao_padrao, idade_minima_dias, idade_maxima_dias,
    temperatura_conservacao_min, temperatura_conservacao_max, tipo_conservacao,
    proteger_luz, agitar_antes_uso, calendario_basico, calendario_campanha, obrigatoria,
    gestante_pode, lactante_pode, imunocomprometido_pode, sincronizar_pni, descricao, indicacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Raiva', 'Antirrábica', 'VAC-024',
    'PNI-024', 'SUS-024', '10101010124', 40, 'Especial', 'Todas as idades',
    (SELECT id FROM public.fabricantes_vacina WHERE nome = 'Instituto Butantan' LIMIT 1),
    7, 13, 1, true,
    4, 3, false,
    NULL, 1.0,
    'Braço direito', NULL, NULL,
    2.0, 8.0, 'Refrigerada (2-8°C)',
    true, true, false, false, false,
    false, false, false, true, 'Vacina contra raiva. Usada em profilaxia pré e pós-exposição.', 'Aplicar em casos de exposição a animais suspeitos de raiva ou para profilaxia pré-exposição em grupos de risco.'
WHERE NOT EXISTS (SELECT 1 FROM public.vacinas WHERE codigo_pni = 'PNI-024' OR codigo_sus = 'SUS-024' OR nome = 'Raiva');

-- Antitetânica
INSERT INTO public.vacinas (
    id, criado_em, atualizado_em, ativo, nome, nome_comercial, codigo_interno,
    codigo_pni, codigo_sus, registro_anvisa, tipo, categoria, grupo_alvo,
    fabricante_id, via_administracao, unidade_medida, status, disponivel_uso,
    numero_doses, intervalo_doses_dias, dose_reforco, intervalo_reforco_anos, dose_ml,
    local_aplicacao_padrao, idade_minima_dias, idade_maxima_dias,
    temperatura_conservacao_min, temperatura_conservacao_max, tipo_conservacao,
    proteger_luz, agitar_antes_uso, calendario_basico, calendario_campanha, obrigatoria,
    gestante_pode, lactante_pode, imunocomprometido_pode, sincronizar_pni, descricao, indicacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Antitetânica', 'Toxoide Tetânico', 'VAC-025',
    'PNI-025', 'SUS-025', '10101010125', 42, 'Especial', 'Todas as idades',
    (SELECT id FROM public.fabricantes_vacina WHERE nome = 'Instituto Butantan' LIMIT 1),
    7, 13, 1, true,
    1, NULL, false,
    NULL, 0.5,
    'Braço direito', NULL, NULL,
    2.0, 8.0, 'Refrigerada (2-8°C)',
    true, true, false, false, false,
    false, false, false, true, 'Vacina contra tétano. Usada em casos de ferimentos com risco de tétano.', 'Aplicar em casos de ferimentos com risco de tétano, especialmente se última dose há mais de 5 anos.'
WHERE NOT EXISTS (SELECT 1 FROM public.vacinas WHERE codigo_pni = 'PNI-025' OR codigo_sus = 'SUS-025' OR nome = 'Antitetânica');

-- Hepatite A e B Combinada
INSERT INTO public.vacinas (
    id, criado_em, atualizado_em, ativo, nome, nome_comercial, codigo_interno,
    codigo_pni, codigo_sus, registro_anvisa, tipo, categoria, grupo_alvo,
    fabricante_id, via_administracao, unidade_medida, status, disponivel_uso,
    numero_doses, intervalo_doses_dias, dose_reforco, intervalo_reforco_anos, dose_ml,
    local_aplicacao_padrao, idade_minima_dias, idade_maxima_dias,
    temperatura_conservacao_min, temperatura_conservacao_max, tipo_conservacao,
    proteger_luz, agitar_antes_uso, calendario_basico, calendario_campanha, obrigatoria,
    gestante_pode, lactante_pode, imunocomprometido_pode, sincronizar_pni, descricao, indicacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Hepatite A e B Combinada', 'Twinrix', 'VAC-026',
    'PNI-026', 'SUS-026', '10101010126', 45, 'Especial', 'Adultos',
    (SELECT id FROM public.fabricantes_vacina WHERE nome = 'GlaxoSmithKline Brasil Ltda' LIMIT 1),
    7, 13, 1, true,
    3, 30, false,
    NULL, 1.0,
    'Braço direito', 5475, NULL,
    2.0, 8.0, 'Refrigerada (2-8°C)',
    true, true, false, false, false,
    false, false, false, true, 'Vacina combinada contra hepatite A e B para adultos.', 'Aplicar em adultos não vacinados contra hepatite A e B. Três doses com intervalo de 1 mês entre primeira e segunda, e 6 meses entre primeira e terceira.'
WHERE NOT EXISTS (SELECT 1 FROM public.vacinas WHERE codigo_pni = 'PNI-026' OR codigo_sus = 'SUS-026' OR nome = 'Hepatite A e B Combinada');

-- COVID-19 - Coronavac
INSERT INTO public.vacinas (
    id, criado_em, atualizado_em, ativo, nome, nome_comercial, codigo_interno,
    codigo_pni, codigo_sus, registro_anvisa, tipo, categoria, grupo_alvo,
    fabricante_id, via_administracao, unidade_medida, status, disponivel_uso,
    numero_doses, intervalo_doses_dias, dose_reforco, intervalo_reforco_anos, dose_ml,
    local_aplicacao_padrao, idade_minima_dias, idade_maxima_dias,
    temperatura_conservacao_min, temperatura_conservacao_max, tipo_conservacao,
    proteger_luz, agitar_antes_uso, calendario_basico, calendario_campanha, obrigatoria,
    gestante_pode, lactante_pode, imunocomprometido_pode, sincronizar_pni, descricao, indicacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'COVID-19 - Coronavac', 'Coronavac', 'VAC-027',
    'PNI-027', 'SUS-027', '10101010127', 51, 'Campanha', 'Todas as idades',
    (SELECT id FROM public.fabricantes_vacina WHERE nome = 'Instituto Butantan' LIMIT 1),
    7, 13, 1, true,
    2, 28, true,
    6, 0.5,
    'Braço direito', NULL, NULL,
    2.0, 8.0, 'Refrigerada (2-8°C)',
    true, true, true, false, true,
    false, false, false, true, 'Vacina contra COVID-19 (Coronavac). Vacina de vírus inativado.', 'Aplicar duas doses com intervalo de 28 dias. Reforço após 6 meses.'
WHERE NOT EXISTS (SELECT 1 FROM public.vacinas WHERE codigo_pni = 'PNI-027' OR codigo_sus = 'SUS-027' OR nome = 'COVID-19 - Coronavac');

-- COVID-19 - AstraZeneca
INSERT INTO public.vacinas (
    id, criado_em, atualizado_em, ativo, nome, nome_comercial, codigo_interno,
    codigo_pni, codigo_sus, registro_anvisa, tipo, categoria, grupo_alvo,
    fabricante_id, via_administracao, unidade_medida, status, disponivel_uso,
    numero_doses, intervalo_doses_dias, dose_reforco, intervalo_reforco_anos, dose_ml,
    local_aplicacao_padrao, idade_minima_dias, idade_maxima_dias,
    temperatura_conservacao_min, temperatura_conservacao_max, tipo_conservacao,
    proteger_luz, agitar_antes_uso, calendario_basico, calendario_campanha, obrigatoria,
    gestante_pode, lactante_pode, imunocomprometido_pode, sincronizar_pni, descricao, indicacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'COVID-19 - AstraZeneca', 'Covishield', 'VAC-028',
    'PNI-028', 'SUS-028', '10101010128', 52, 'Campanha', 'Todas as idades',
    (SELECT id FROM public.fabricantes_vacina WHERE nome = 'AstraZeneca do Brasil' LIMIT 1),
    7, 13, 1, true,
    2, 84, true,
    6, 0.5,
    'Braço direito', NULL, NULL,
    2.0, 8.0, 'Refrigerada (2-8°C)',
    true, true, true, false, true,
    false, false, false, true, 'Vacina contra COVID-19 (AstraZeneca). Vacina de vetor viral.', 'Aplicar duas doses com intervalo de 12 semanas. Reforço após 6 meses.'
WHERE NOT EXISTS (SELECT 1 FROM public.vacinas WHERE codigo_pni = 'PNI-028' OR codigo_sus = 'SUS-028' OR nome = 'COVID-19 - AstraZeneca');

-- COVID-19 - Pfizer
INSERT INTO public.vacinas (
    id, criado_em, atualizado_em, ativo, nome, nome_comercial, codigo_interno,
    codigo_pni, codigo_sus, registro_anvisa, tipo, categoria, grupo_alvo,
    fabricante_id, via_administracao, unidade_medida, status, disponivel_uso,
    numero_doses, intervalo_doses_dias, dose_reforco, intervalo_reforco_anos, dose_ml,
    local_aplicacao_padrao, idade_minima_dias, idade_maxima_dias,
    temperatura_conservacao_min, temperatura_conservacao_max, tipo_conservacao,
    proteger_luz, agitar_antes_uso, calendario_basico, calendario_campanha, obrigatoria,
    gestante_pode, lactante_pode, imunocomprometido_pode, sincronizar_pni, descricao, indicacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'COVID-19 - Pfizer', 'Comirnaty', 'VAC-029',
    'PNI-029', 'SUS-029', '10101010129', 53, 'Campanha', 'Todas as idades',
    (SELECT id FROM public.fabricantes_vacina WHERE nome = 'Pfizer do Brasil' LIMIT 1),
    7, 13, 1, true,
    2, 21, true,
    6, 0.3,
    'Braço direito', NULL, NULL,
    -80.0, -60.0, 'Ultra-congelada (-80°C a -60°C)',
    true, true, true, false, true,
    false, false, false, true, 'Vacina contra COVID-19 (Pfizer). Vacina de RNA mensageiro.', 'Aplicar duas doses com intervalo de 21 dias. Reforço após 6 meses. Após descongelamento, conservar entre 2-8°C por até 5 dias.'
WHERE NOT EXISTS (SELECT 1 FROM public.vacinas WHERE codigo_pni = 'PNI-029' OR codigo_sus = 'SUS-029' OR nome = 'COVID-19 - Pfizer');

-- COVID-19 - Janssen
INSERT INTO public.vacinas (
    id, criado_em, atualizado_em, ativo, nome, nome_comercial, codigo_interno,
    codigo_pni, codigo_sus, registro_anvisa, tipo, categoria, grupo_alvo,
    fabricante_id, via_administracao, unidade_medida, status, disponivel_uso,
    numero_doses, intervalo_doses_dias, dose_reforco, intervalo_reforco_anos, dose_ml,
    local_aplicacao_padrao, idade_minima_dias, idade_maxima_dias,
    temperatura_conservacao_min, temperatura_conservacao_max, tipo_conservacao,
    proteger_luz, agitar_antes_uso, calendario_basico, calendario_campanha, obrigatoria,
    gestante_pode, lactante_pode, imunocomprometido_pode, sincronizar_pni, descricao, indicacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'COVID-19 - Janssen', 'Janssen', 'VAC-030',
    'PNI-030', 'SUS-030', '10101010130', 54, 'Campanha', 'Todas as idades',
    (SELECT id FROM public.fabricantes_vacina WHERE nome = 'Janssen-Cilag Farmacêutica Ltda' LIMIT 1),
    7, 13, 1, true,
    1, NULL, true,
    6, 0.5,
    'Braço direito', NULL, NULL,
    2.0, 8.0, 'Refrigerada (2-8°C)',
    true, true, true, false, true,
    false, false, false, true, 'Vacina contra COVID-19 (Janssen). Vacina de dose única com vetor viral.', 'Aplicar dose única. Reforço após 6 meses.'
WHERE NOT EXISTS (SELECT 1 FROM public.vacinas WHERE codigo_pni = 'PNI-030' OR codigo_sus = 'SUS-030' OR nome = 'COVID-19 - Janssen');

-- COVID-19 - Butanvac
INSERT INTO public.vacinas (
    id, criado_em, atualizado_em, ativo, nome, nome_comercial, codigo_interno,
    codigo_pni, codigo_sus, registro_anvisa, tipo, categoria, grupo_alvo,
    fabricante_id, via_administracao, unidade_medida, status, disponivel_uso,
    numero_doses, intervalo_doses_dias, dose_reforco, intervalo_reforco_anos, dose_ml,
    local_aplicacao_padrao, idade_minima_dias, idade_maxima_dias,
    temperatura_conservacao_min, temperatura_conservacao_max, tipo_conservacao,
    proteger_luz, agitar_antes_uso, calendario_basico, calendario_campanha, obrigatoria,
    gestante_pode, lactante_pode, imunocomprometido_pode, sincronizar_pni, descricao, indicacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'COVID-19 - Butanvac', 'Butanvac', 'VAC-031',
    'PNI-031', 'SUS-031', '10101010131', 55, 'Campanha', 'Todas as idades',
    (SELECT id FROM public.fabricantes_vacina WHERE nome = 'Instituto Butantan' LIMIT 1),
    7, 13, 1, true,
    2, 28, true,
    6, 0.5,
    'Braço direito', NULL, NULL,
    2.0, 8.0, 'Refrigerada (2-8°C)',
    true, true, true, false, true,
    false, false, false, true, 'Vacina contra COVID-19 (Butanvac). Vacina de vírus inativado desenvolvida pelo Butantan.', 'Aplicar duas doses com intervalo de 28 dias. Reforço após 6 meses.'
WHERE NOT EXISTS (SELECT 1 FROM public.vacinas WHERE codigo_pni = 'PNI-031' OR codigo_sus = 'SUS-031' OR nome = 'COVID-19 - Butanvac');

-- Febre Tifoide
INSERT INTO public.vacinas (
    id, criado_em, atualizado_em, ativo, nome, nome_comercial, codigo_interno,
    codigo_pni, codigo_sus, registro_anvisa, tipo, categoria, grupo_alvo,
    fabricante_id, via_administracao, unidade_medida, status, disponivel_uso,
    numero_doses, intervalo_doses_dias, dose_reforco, intervalo_reforco_anos, dose_ml,
    local_aplicacao_padrao, idade_minima_dias, idade_maxima_dias,
    temperatura_conservacao_min, temperatura_conservacao_max, tipo_conservacao,
    proteger_luz, agitar_antes_uso, calendario_basico, calendario_campanha, obrigatoria,
    gestante_pode, lactante_pode, imunocomprometido_pode, sincronizar_pni, descricao, indicacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Febre Tifoide', 'Typhim Vi', 'VAC-032',
    'PNI-032', 'SUS-032', '10101010132', 60, 'Viagem', 'Viajantes',
    (SELECT id FROM public.fabricantes_vacina WHERE nome = 'Sanofi Pasteur Ltda' LIMIT 1),
    7, 13, 1, true,
    1, NULL, true,
    3, 0.5,
    'Braço direito', NULL, NULL,
    2.0, 8.0, 'Refrigerada (2-8°C)',
    true, true, false, false, false,
    false, false, false, true, 'Vacina contra febre tifoide. Recomendada para viajantes para áreas endêmicas.', 'Aplicar antes de viagens para áreas endêmicas. Reforço a cada 3 anos.'
WHERE NOT EXISTS (SELECT 1 FROM public.vacinas WHERE codigo_pni = 'PNI-032' OR codigo_sus = 'SUS-032' OR nome = 'Febre Tifoide');

-- Cólera
INSERT INTO public.vacinas (
    id, criado_em, atualizado_em, ativo, nome, nome_comercial, codigo_interno,
    codigo_pni, codigo_sus, registro_anvisa, tipo, categoria, grupo_alvo,
    fabricante_id, via_administracao, unidade_medida, status, disponivel_uso,
    numero_doses, intervalo_doses_dias, dose_reforco, intervalo_reforco_anos, dose_ml,
    local_aplicacao_padrao, idade_minima_dias, idade_maxima_dias,
    temperatura_conservacao_min, temperatura_conservacao_max, tipo_conservacao,
    proteger_luz, agitar_antes_uso, calendario_basico, calendario_campanha, obrigatoria,
    gestante_pode, lactante_pode, imunocomprometido_pode, sincronizar_pni, descricao, indicacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Cólera', 'Dukoral', 'VAC-033',
    'PNI-033', 'SUS-033', '10101010133', 61, 'Viagem', 'Viajantes',
    (SELECT id FROM public.fabricantes_vacina WHERE nome = 'Valneva' LIMIT 1),
    1, 13, 1, true,
    2, 14, true,
    2, 3.0,
    'Oral', NULL, NULL,
    2.0, 8.0, 'Refrigerada (2-8°C)',
    true, false, false, false, false,
    false, false, false, true, 'Vacina contra cólera. Recomendada para viajantes para áreas endêmicas.', 'Aplicar antes de viagens para áreas endêmicas. Duas doses com intervalo de 14 dias. Reforço a cada 2 anos.'
WHERE NOT EXISTS (SELECT 1 FROM public.vacinas WHERE codigo_pni = 'PNI-033' OR codigo_sus = 'SUS-033' OR nome = 'Cólera');

-- Encefalite Japonesa
INSERT INTO public.vacinas (
    id, criado_em, atualizado_em, ativo, nome, nome_comercial, codigo_interno,
    codigo_pni, codigo_sus, registro_anvisa, tipo, categoria, grupo_alvo,
    fabricante_id, via_administracao, unidade_medida, status, disponivel_uso,
    numero_doses, intervalo_doses_dias, dose_reforco, intervalo_reforco_anos, dose_ml,
    local_aplicacao_padrao, idade_minima_dias, idade_maxima_dias,
    temperatura_conservacao_min, temperatura_conservacao_max, tipo_conservacao,
    proteger_luz, agitar_antes_uso, calendario_basico, calendario_campanha, obrigatoria,
    gestante_pode, lactante_pode, imunocomprometido_pode, sincronizar_pni, descricao, indicacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Encefalite Japonesa', 'Ixiaro', 'VAC-034',
    'PNI-034', 'SUS-034', '10101010134', 62, 'Viagem', 'Viajantes',
    (SELECT id FROM public.fabricantes_vacina WHERE nome = 'Valneva' LIMIT 1),
    7, 13, 1, true,
    2, 28, true,
    1, 0.5,
    'Braço direito', NULL, NULL,
    2.0, 8.0, 'Refrigerada (2-8°C)',
    true, true, false, false, false,
    false, false, false, true, 'Vacina contra encefalite japonesa. Recomendada para viajantes para Ásia.', 'Aplicar antes de viagens para áreas endêmicas na Ásia. Duas doses com intervalo de 28 dias. Reforço após 1 ano.'
WHERE NOT EXISTS (SELECT 1 FROM public.vacinas WHERE codigo_pni = 'PNI-034' OR codigo_sus = 'SUS-034' OR nome = 'Encefalite Japonesa');

-- Meningocócica ACWY
INSERT INTO public.vacinas (
    id, criado_em, atualizado_em, ativo, nome, nome_comercial, codigo_interno,
    codigo_pni, codigo_sus, registro_anvisa, tipo, categoria, grupo_alvo,
    fabricante_id, via_administracao, unidade_medida, status, disponivel_uso,
    numero_doses, intervalo_doses_dias, dose_reforco, intervalo_reforco_anos, dose_ml,
    local_aplicacao_padrao, idade_minima_dias, idade_maxima_dias,
    temperatura_conservacao_min, temperatura_conservacao_max, tipo_conservacao,
    proteger_luz, agitar_antes_uso, calendario_basico, calendario_campanha, obrigatoria,
    gestante_pode, lactante_pode, imunocomprometido_pode, sincronizar_pni, descricao, indicacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Meningocócica ACWY', 'Menveo', 'VAC-035',
    'PNI-035', 'SUS-035', '10101010135', 63, 'Viagem', 'Viajantes',
    (SELECT id FROM public.fabricantes_vacina WHERE nome = 'GlaxoSmithKline Brasil Ltda' LIMIT 1),
    7, 13, 1, true,
    1, NULL, true,
    5, 0.5,
    'Braço direito', NULL, NULL,
    2.0, 8.0, 'Refrigerada (2-8°C)',
    true, true, false, false, false,
    false, false, false, true, 'Vacina contra meningite meningocócica tipos A, C, W e Y. Recomendada para viajantes.', 'Aplicar antes de viagens para áreas endêmicas (cinturão da meningite na África, peregrinação a Meca). Reforço a cada 5 anos.'
WHERE NOT EXISTS (SELECT 1 FROM public.vacinas WHERE codigo_pni = 'PNI-035' OR codigo_sus = 'SUS-035' OR nome = 'Meningocócica ACWY');

-- Pneumocócica 13-valente
INSERT INTO public.vacinas (
    id, criado_em, atualizado_em, ativo, nome, nome_comercial, codigo_interno,
    codigo_pni, codigo_sus, registro_anvisa, tipo, categoria, grupo_alvo,
    fabricante_id, via_administracao, unidade_medida, status, disponivel_uso,
    numero_doses, intervalo_doses_dias, dose_reforco, intervalo_reforco_anos, dose_ml,
    local_aplicacao_padrao, idade_minima_dias, idade_maxima_dias,
    temperatura_conservacao_min, temperatura_conservacao_max, tipo_conservacao,
    proteger_luz, agitar_antes_uso, calendario_basico, calendario_campanha, obrigatoria,
    gestante_pode, lactante_pode, imunocomprometido_pode, sincronizar_pni, descricao, indicacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Pneumocócica 13-valente', 'Prevenar 13', 'VAC-036',
    'PNI-036', 'SUS-036', '10101010136', 15, 'Especial', 'Crianças e Adultos',
    (SELECT id FROM public.fabricantes_vacina WHERE nome = 'Pfizer do Brasil' LIMIT 1),
    7, 13, 1, true,
    1, NULL, false,
    NULL, 0.5,
    'Braço direito', NULL, NULL,
    2.0, 8.0, 'Refrigerada (2-8°C)',
    true, true, false, false, false,
    false, false, false, true, 'Vacina contra doenças pneumocócicas 13-valente. Usada em grupos de risco.', 'Aplicar em crianças não vacinadas com pneumo 10 e em grupos de risco.'
WHERE NOT EXISTS (SELECT 1 FROM public.vacinas WHERE codigo_pni = 'PNI-036' OR codigo_sus = 'SUS-036' OR nome = 'Pneumocócica 13-valente');

-- Meningocócica B
INSERT INTO public.vacinas (
    id, criado_em, atualizado_em, ativo, nome, nome_comercial, codigo_interno,
    codigo_pni, codigo_sus, registro_anvisa, tipo, categoria, grupo_alvo,
    fabricante_id, via_administracao, unidade_medida, status, disponivel_uso,
    numero_doses, intervalo_doses_dias, dose_reforco, intervalo_reforco_anos, dose_ml,
    local_aplicacao_padrao, idade_minima_dias, idade_maxima_dias,
    temperatura_conservacao_min, temperatura_conservacao_max, tipo_conservacao,
    proteger_luz, agitar_antes_uso, calendario_basico, calendario_campanha, obrigatoria,
    gestante_pode, lactante_pode, imunocomprometido_pode, sincronizar_pni, descricao, indicacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Meningocócica B', 'Bexsero', 'VAC-037',
    'PNI-037', 'SUS-037', '10101010137', 13, 'Especial', 'Crianças e Adultos',
    (SELECT id FROM public.fabricantes_vacina WHERE nome = 'GlaxoSmithKline Brasil Ltda' LIMIT 1),
    7, 13, 1, true,
    2, 60, false,
    NULL, 0.5,
    'Braço direito', NULL, NULL,
    2.0, 8.0, 'Refrigerada (2-8°C)',
    true, true, false, false, false,
    false, false, false, true, 'Vacina contra meningite meningocócica tipo B.', 'Aplicar em grupos de risco ou em surtos. Duas doses com intervalo de 2 meses.'
WHERE NOT EXISTS (SELECT 1 FROM public.vacinas WHERE codigo_pni = 'PNI-037' OR codigo_sus = 'SUS-037' OR nome = 'Meningocócica B');

-- HPV Bivalente
INSERT INTO public.vacinas (
    id, criado_em, atualizado_em, ativo, nome, nome_comercial, codigo_interno,
    codigo_pni, codigo_sus, registro_anvisa, tipo, categoria, grupo_alvo,
    fabricante_id, via_administracao, unidade_medida, status, disponivel_uso,
    numero_doses, intervalo_doses_dias, dose_reforco, intervalo_reforco_anos, dose_ml,
    local_aplicacao_padrao, idade_minima_dias, idade_maxima_dias,
    temperatura_conservacao_min, temperatura_conservacao_max, tipo_conservacao,
    proteger_luz, agitar_antes_uso, calendario_basico, calendario_campanha, obrigatoria,
    gestante_pode, lactante_pode, imunocomprometido_pode, sincronizar_pni, descricao, indicacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'HPV Bivalente', 'Cervarix', 'VAC-038',
    'PNI-038', 'SUS-038', '10101010138', 23, 'Especial', 'Adolescentes e Adultos',
    (SELECT id FROM public.fabricantes_vacina WHERE nome = 'GlaxoSmithKline Brasil Ltda' LIMIT 1),
    7, 13, 1, true,
    2, 180, false,
    NULL, 0.5,
    'Braço direito', 3285, NULL,
    2.0, 8.0, 'Refrigerada (2-8°C)',
    true, true, false, false, false,
    false, false, false, true, 'Vacina contra papilomavírus humano (tipos 16 e 18). Protege contra câncer de colo do útero.', 'Aplicar em meninas e mulheres de 9 a 45 anos. Duas doses com intervalo de 6 meses.'
WHERE NOT EXISTS (SELECT 1 FROM public.vacinas WHERE codigo_pni = 'PNI-038' OR codigo_sus = 'SUS-038' OR nome = 'HPV Bivalente');

-- DTPa - Difteria, Tétano e Coqueluche Acelular
INSERT INTO public.vacinas (
    id, criado_em, atualizado_em, ativo, nome, nome_comercial, codigo_interno,
    codigo_pni, codigo_sus, registro_anvisa, tipo, categoria, grupo_alvo,
    fabricante_id, via_administracao, unidade_medida, status, disponivel_uso,
    numero_doses, intervalo_doses_dias, dose_reforco, intervalo_reforco_anos, dose_ml,
    local_aplicacao_padrao, idade_minima_dias, idade_maxima_dias,
    temperatura_conservacao_min, temperatura_conservacao_max, tipo_conservacao,
    proteger_luz, agitar_antes_uso, calendario_basico, calendario_campanha, obrigatoria,
    gestante_pode, lactante_pode, imunocomprometido_pode, sincronizar_pni, descricao, indicacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'DTPa - Difteria, Tétano e Coqueluche Acelular', 'DTPa', 'VAC-039',
    'PNI-039', 'SUS-039', '10101010139', 5, 'Especial', 'Crianças e Adultos',
    (SELECT id FROM public.fabricantes_vacina WHERE nome = 'GlaxoSmithKline Brasil Ltda' LIMIT 1),
    7, 13, 1, true,
    1, NULL, true,
    10, 0.5,
    'Braço direito', NULL, NULL,
    2.0, 8.0, 'Refrigerada (2-8°C)',
    true, true, false, false, false,
    false, false, false, true, 'Vacina acelular contra difteria, tétano e coqueluche. Menos reatogênica que DTP.', 'Aplicar como alternativa à DTP em pessoas com reações adversas. Reforço a cada 10 anos.'
WHERE NOT EXISTS (SELECT 1 FROM public.vacinas WHERE codigo_pni = 'PNI-039' OR codigo_sus = 'SUS-039' OR nome = 'DTPa - Difteria, Tétano e Coqueluche Acelular');

-- DTPa + VIP
INSERT INTO public.vacinas (
    id, criado_em, atualizado_em, ativo, nome, nome_comercial, codigo_interno,
    codigo_pni, codigo_sus, registro_anvisa, tipo, categoria, grupo_alvo,
    fabricante_id, via_administracao, unidade_medida, status, disponivel_uso,
    numero_doses, intervalo_doses_dias, dose_reforco, intervalo_reforco_anos, dose_ml,
    local_aplicacao_padrao, idade_minima_dias, idade_maxima_dias,
    temperatura_conservacao_min, temperatura_conservacao_max, tipo_conservacao,
    proteger_luz, agitar_antes_uso, calendario_basico, calendario_campanha, obrigatoria,
    gestante_pode, lactante_pode, imunocomprometido_pode, sincronizar_pni, descricao, indicacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'DTPa + VIP', 'DTPa + VIP', 'VAC-040',
    'PNI-040', 'SUS-040', '10101010140', 7, 'Especial', 'Crianças',
    (SELECT id FROM public.fabricantes_vacina WHERE nome = 'GlaxoSmithKline Brasil Ltda' LIMIT 1),
    7, 13, 1, true,
    1, NULL, true,
    4, 0.5,
    'Coxa (lateral)', 540, NULL,
    2.0, 8.0, 'Refrigerada (2-8°C)',
    true, true, false, false, false,
    false, false, false, true, 'Vacina combinada DTPa + VIP. Reforço da pentavalente com componente acelular.', 'Aplicar aos 15 meses e 4 anos de idade como reforço.'
WHERE NOT EXISTS (SELECT 1 FROM public.vacinas WHERE codigo_pni = 'PNI-040' OR codigo_sus = 'SUS-040' OR nome = 'DTPa + VIP');


-- ========== FIM DO SCRIPT ==========
