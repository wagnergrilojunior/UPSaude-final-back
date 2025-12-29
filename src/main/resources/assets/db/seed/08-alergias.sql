-- =====================================================
-- DEPRECATED: Este seed está descontinuado
-- =====================================================
-- Script de Seed: Alergias (Escopo Global) - DEPRECATED
-- 
-- Este seed foi descontinuado porque:
-- - Alergia não deve ser um catálogo global
-- - Alergia é informação clínica declarada do paciente
-- - O modelo correto é usar apenas AlergiaPaciente (tabela alergias_paciente)
-- 
-- A tabela alergias (catálogo global) foi removida na migration V024.
-- Este arquivo é mantido apenas para referência histórica.
-- 
-- Para criar alergias, use a API: POST /v1/pacientes/{pacienteId}/alergias
-- =====================================================

-- TODOS OS INSERTS ABAIXO ESTÃO COMENTADOS - NÃO EXECUTAR
/*
-- Script de Seed: Alergias (Escopo Global) - DEPRECATED
-- Cria catálogo completo de alergias comuns conforme padrões médicos - dados globais sem tenant
-- Baseado em catálogos médicos, CID-10 e sistemas de saúde brasileiros
-- Executado quando app.seed.enabled=true

-- ========== MEDICAMENTOS (Tipo 1) ==========

-- Penicilina
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Penicilina', 'Penicillium', 'ALG-MED-001',
    1, 'Antibiótico Beta-lactâmico', 'Penicilina', 'Z88.0', true, true,
    'Alergia a penicilina e seus derivados. Reação alérgica comum que pode causar desde urticária até anafilaxia',
    'Ampicilina, Amoxicilina, Cloxacilina, Oxacilina, todas as penicilinas',
    'Alergia comum e importante. Sempre verificar antes de prescrever antibióticos beta-lactâmicos. Alternativas: macrolídeos, quinolonas.',
    true
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-MED-001');

-- Amoxicilina
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Amoxicilina', NULL, 'ALG-MED-002',
    1, 'Antibiótico Beta-lactâmico', 'Penicilina', 'Z88.0', true, true,
    'Alergia a amoxicilina, derivado da penicilina',
    'Penicilina, Ampicilina, todas as penicilinas',
    'Reação cruzada com penicilina. Usar com cautela em pacientes alérgicos à penicilina.',
    true
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-MED-002');

-- Ampicilina
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Ampicilina', NULL, 'ALG-MED-003',
    1, 'Antibiótico Beta-lactâmico', 'Penicilina', 'Z88.0', true, true,
    'Alergia a ampicilina, antibiótico beta-lactâmico',
    'Penicilina, Amoxicilina, todas as penicilinas',
    'Reação cruzada com penicilina. Alternativas: macrolídeos, quinolonas.',
    true
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-MED-003');

-- Cefalexina
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Cefalexina', NULL, 'ALG-MED-004',
    1, 'Antibiótico Beta-lactâmico', 'Cefalosporina', 'Z88.0', true, true,
    'Alergia a cefalexina, cefalosporina de primeira geração',
    'Outras cefalosporinas, possível reação cruzada com penicilina',
    'Cefalosporinas podem ter reação cruzada com penicilina em 5-10% dos casos.',
    true
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-MED-004');

-- Ceftriaxona
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Ceftriaxona', NULL, 'ALG-MED-005',
    1, 'Antibiótico Beta-lactâmico', 'Cefalosporina', 'Z88.0', true, true,
    'Alergia a ceftriaxona, cefalosporina de terceira geração',
    'Outras cefalosporinas, possível reação cruzada com penicilina',
    'Cefalosporina de amplo espectro. Reação cruzada com penicilina possível.',
    true
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-MED-005');

-- Azitromicina
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Azitromicina', NULL, 'ALG-MED-006',
    1, 'Antibiótico Macrolídeo', 'Macrolídeo', 'Z88.0', false, false,
    'Alergia a azitromicina, antibiótico macrolídeo',
    'Eritromicina, Claritromicina, outros macrolídeos',
    'Alternativa para pacientes alérgicos à penicilina. Reação alérgica rara.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-MED-006');

-- Sulfa (Sulfametoxazol/Trimetoprima)
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Sulfa (Sulfametoxazol/Trimetoprima)', NULL, 'ALG-MED-007',
    1, 'Antibiótico Sulfonamida', 'Sulfonamida', 'Z88.0', true, true,
    'Alergia a sulfametoxazol/trimetoprima (Bactrim, Septra)',
    'Todas as sulfonamidas, alguns diuréticos (furosemida, hidroclorotiazida)',
    'Alergia comum. Evitar todas as sulfonamidas. Reações podem ser graves.',
    true
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-MED-007');

-- Dipirona
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Dipirona', 'Metamizol', 'ALG-MED-008',
    1, 'Analgésico/Antipirético', 'Pirazolona', 'Z88.0', true, true,
    'Alergia a dipirona (Novalgina, Anador). Pode causar agranulocitose',
    'Outros derivados de pirazolona',
    'Alergia comum no Brasil. Pode causar reações graves incluindo agranulocitose. Evitar uso.',
    true
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-MED-008');

-- Ibuprofeno
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Ibuprofeno', NULL, 'ALG-MED-009',
    1, 'Anti-inflamatório Não Esteroidal', 'AINE', 'Z88.0', true, false,
    'Alergia a ibuprofeno, anti-inflamatório não esteroidal',
    'Aspirina, Naproxeno, Diclofenaco, outros AINEs',
    'Reação cruzada com outros AINEs possível. Usar paracetamol ou opioides como alternativa.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-MED-009');

-- Aspirina (AAS)
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Aspirina (AAS)', 'Ácido Acetilsalicílico', 'ALG-MED-010',
    1, 'Anti-inflamatório Não Esteroidal', 'Salicilato', 'Z88.0', true, true,
    'Alergia a aspirina (ácido acetilsalicílico). Pode causar asma e urticária',
    'Outros salicilatos, AINEs (Ibuprofeno, Naproxeno)',
    'Alergia comum. Pode causar asma induzida por aspirina. Evitar todos os AINEs.',
    true
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-MED-010');

-- Paracetamol
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Paracetamol', 'Acetaminofeno', 'ALG-MED-011',
    1, 'Analgésico/Antipirético', 'Anilina', 'Z88.0', false, false,
    'Alergia a paracetamol (Tylenol). Reação alérgica rara',
    'Outros derivados de anilina',
    'Alergia rara. Alternativas: dipirona (se não alérgico), opioides.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-MED-011');

-- Diclofenaco
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Diclofenaco', NULL, 'ALG-MED-012',
    1, 'Anti-inflamatório Não Esteroidal', 'AINE', 'Z88.0', true, false,
    'Alergia a diclofenaco, anti-inflamatório não esteroidal',
    'Ibuprofeno, Naproxeno, Aspirina, outros AINEs',
    'Reação cruzada com outros AINEs possível.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-MED-012');

-- Codeína
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Codeína', NULL, 'ALG-MED-013',
    1, 'Analgésico Opioide', 'Opioide', 'Z88.0', false, false,
    'Alergia a codeína, analgésico opioide',
    'Morfina, Hidromorfona, outros opioides',
    'Alergia rara. Alternativas: outros opioides ou AINEs.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-MED-013');

-- Morfina
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Morfina', NULL, 'ALG-MED-014',
    1, 'Analgésico Opioide', 'Opioide', 'Z88.0', false, true,
    'Alergia a morfina, opioide potente',
    'Codeína, Hidromorfona, Fentanil, outros opioides',
    'Alergia rara mas pode ser grave. Alternativas: outros opioides ou técnicas não farmacológicas.',
    true
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-MED-014');

-- Insulina
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Insulina', NULL, 'ALG-MED-015',
    1, 'Hormônio', 'Insulina', 'Z88.0', false, false,
    'Alergia a insulina. Reação alérgica rara',
    'Todas as preparações de insulina',
    'Alergia rara. Pode ser local (no local da injeção) ou sistêmica. Considerar desensibilização.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-MED-015');

-- ========== ALIMENTOS (Tipo 2) ==========

-- Leite
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Leite', 'Caseína, Lactoalbumina', 'ALG-ALI-001',
    2, 'Alergia Alimentar', 'Proteínas do Leite', 'T78.1', true, true,
    'Alergia à proteína do leite de vaca. Diferente de intolerância à lactose',
    'Leite, queijo, iogurte, manteiga, sorvete, produtos lácteos, caseína, soro de leite',
    'Alergia comum em crianças. Pode se resolver com a idade. Evitar todos os derivados do leite.',
    true
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-ALI-001');

-- Lactose (Intolerância)
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Lactose', 'Lactose', 'ALG-ALI-002',
    2, 'Intolerância Alimentar', 'Açúcares', 'E73.9', true, false,
    'Intolerância à lactose. Dificuldade em digerir a lactose, açúcar presente no leite e derivados',
    'Leite, queijo, iogurte, manteiga, derivados lácteos',
    'Diferente de alergia ao leite (proteína). Intolerância à lactose é muito comum. Produtos sem lactose podem ser tolerados.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-ALI-002');

-- Ovo
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Ovo', 'Ovalbumina, Ovomucoide', 'ALG-ALI-003',
    2, 'Alergia Alimentar', 'Proteínas Animais', 'T78.1', true, false,
    'Alergia ao ovo. Reação alérgica às proteínas do ovo',
    'Ovo inteiro, clara de ovo, gema de ovo, produtos que contêm ovo (bolos, massas, maionese, sorvetes)',
    'Alergia comum em crianças. Muitas vezes se resolve com a idade. Verificar rótulos de alimentos processados.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-ALI-003');

-- Amendoim
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Amendoim', 'Arachis hypogaea', 'ALG-ALI-004',
    2, 'Alergia Alimentar', 'Oleaginosas', 'T78.1', true, true,
    'Alergia ao amendoim. Pode causar reações graves incluindo anafilaxia',
    'Amendoim, pasta de amendoim, óleo de amendoim, produtos que contêm amendoim',
    'Alergia grave e comum. Requer cuidado extremo com alimentos processados que podem conter traços. Kit de epinefrina recomendado.',
    true
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-ALI-004');

-- Castanha de Caju
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Castanha de Caju', 'Anacardium occidentale', 'ALG-ALI-005',
    2, 'Alergia Alimentar', 'Oleaginosas', 'T78.1', true, true,
    'Alergia à castanha de caju. Pode causar reações graves',
    'Caju, castanha de caju, outras oleaginosas (nozes, amêndoas)',
    'Alergia grave. Reação cruzada com outras oleaginosas possível. Kit de epinefrina recomendado.',
    true
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-ALI-005');

-- Nozes
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Nozes', 'Juglans regia', 'ALG-ALI-006',
    2, 'Alergia Alimentar', 'Oleaginosas', 'T78.1', true, true,
    'Alergia a nozes. Pode causar reações graves',
    'Nozes, outras oleaginosas (amêndoas, castanhas, pistache)',
    'Alergia grave. Reação cruzada com outras oleaginosas comum.',
    true
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-ALI-006');

-- Amêndoas
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Amêndoas', 'Prunus dulcis', 'ALG-ALI-007',
    2, 'Alergia Alimentar', 'Oleaginosas', 'T78.1', true, true,
    'Alergia a amêndoas. Pode causar reações graves',
    'Amêndoas, outras oleaginosas (nozes, castanhas)',
    'Alergia grave. Reação cruzada com outras oleaginosas possível.',
    true
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-ALI-007');

-- Soja
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Soja', 'Glycine max', 'ALG-ALI-008',
    2, 'Alergia Alimentar', 'Leguminosas', 'T78.1', true, false,
    'Alergia à soja. Comum em crianças',
    'Soja, leite de soja, tofu, missô, shoyu, produtos com soja',
    'Alergia comum em crianças. Pode se resolver com a idade. Verificar rótulos de alimentos processados.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-ALI-008');

-- Trigo
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Trigo', 'Triticum', 'ALG-ALI-009',
    2, 'Alergia Alimentar', 'Cereais', 'T78.1', true, false,
    'Alergia ao trigo. Diferente de doença celíaca (intolerância ao glúten)',
    'Trigo, farinha de trigo, pão, massas, bolos, produtos com trigo',
    'Diferente de doença celíaca. Alergia ao trigo pode se resolver com a idade.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-ALI-009');

-- Glúten
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Glúten', 'Gluten', 'ALG-ALI-010',
    2, 'Alergia Alimentar', 'Cereais', 'T78.1', true, false,
    'Alergia ao glúten. Diferente de doença celíaca',
    'Trigo, centeio, cevada, aveia (contaminação), produtos com glúten',
    'Alergia ao glúten. Diferente de doença celíaca (intolerância). Dieta sem glúten necessária.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-ALI-010');

-- Camarão
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Camarão', 'Penaeidae', 'ALG-ALI-011',
    2, 'Alergia Alimentar', 'Frutos do Mar', 'T78.1', true, true,
    'Alergia a camarão. Pode causar reações graves',
    'Camarão, lagosta, caranguejo, outros crustáceos',
    'Alergia comum e grave. Reação cruzada com outros crustáceos comum. Kit de epinefrina recomendado.',
    true
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-ALI-011');

-- Peixe
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Peixe', 'Pisces', 'ALG-ALI-012',
    2, 'Alergia Alimentar', 'Frutos do Mar', 'T78.1', true, true,
    'Alergia a peixe. Pode causar reações graves',
    'Todos os tipos de peixe, óleo de peixe',
    'Alergia comum e grave. Reação cruzada entre diferentes tipos de peixe comum.',
    true
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-ALI-012');

-- Frutos do Mar (Geral)
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Frutos do Mar', 'Mariscos', 'ALG-ALI-013',
    2, 'Alergia Alimentar', 'Frutos do Mar', 'T78.1', true, true,
    'Alergia a frutos do mar em geral',
    'Camarão, lagosta, caranguejo, peixe, mariscos, moluscos',
    'Alergia grave. Evitar todos os frutos do mar. Kit de epinefrina recomendado.',
    true
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-ALI-013');

-- Morango
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Morango', 'Fragaria', 'ALG-ALI-014',
    2, 'Alergia Alimentar', 'Frutas', 'T78.1', true, false,
    'Alergia a morango. Comum em crianças',
    'Morango, outras frutas vermelhas (framboesa, amora)',
    'Alergia comum em crianças. Pode se resolver com a idade.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-ALI-014');

-- Kiwi
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Kiwi', 'Actinidia deliciosa', 'ALG-ALI-015',
    2, 'Alergia Alimentar', 'Frutas', 'T78.1', false, false,
    'Alergia a kiwi. Reação cruzada com látex possível',
    'Kiwi, outras frutas tropicais, possível reação cruzada com látex',
    'Alergia rara. Reação cruzada com látex e outras frutas tropicais possível.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-ALI-015');

-- Banana
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Banana', 'Musa', 'ALG-ALI-016',
    2, 'Alergia Alimentar', 'Frutas', 'T78.1', false, false,
    'Alergia a banana. Reação cruzada com látex possível',
    'Banana, outras frutas tropicais, possível reação cruzada com látex',
    'Alergia rara. Reação cruzada com látex e outras frutas tropicais possível.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-ALI-016');

-- Abacate
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Abacate', 'Persea americana', 'ALG-ALI-017',
    2, 'Alergia Alimentar', 'Frutas', 'T78.1', false, false,
    'Alergia a abacate. Reação cruzada com látex possível',
    'Abacate, outras frutas tropicais, possível reação cruzada com látex',
    'Alergia rara. Reação cruzada com látex comum.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-ALI-017');

-- Chocolate
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Chocolate', 'Theobroma cacao', 'ALG-ALI-018',
    2, 'Alergia Alimentar', 'Doces', 'T78.1', false, false,
    'Alergia a chocolate. Pode ser alergia ao cacau ou aos aditivos',
    'Chocolate, cacau, produtos com chocolate',
    'Alergia rara. Pode ser alergia ao cacau ou aos aditivos (leite, soja, nozes) no chocolate.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-ALI-018');

-- Tomate
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Tomate', 'Solanum lycopersicum', 'ALG-ALI-019',
    2, 'Alergia Alimentar', 'Vegetais', 'T78.1', false, false,
    'Alergia a tomate. Pode causar síndrome de alergia oral',
    'Tomate, molho de tomate, ketchup, produtos com tomate',
    'Alergia rara. Pode causar síndrome de alergia oral (coceira na boca).',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-ALI-019');

-- Milho
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Milho', 'Zea mays', 'ALG-ALI-020',
    2, 'Alergia Alimentar', 'Cereais', 'T78.1', false, false,
    'Alergia a milho. Pode estar presente em muitos alimentos processados',
    'Milho, amido de milho, xarope de milho, produtos com milho',
    'Alergia rara. Milho está presente em muitos alimentos processados. Verificar rótulos.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-ALI-020');

-- ========== INALANTES (Tipo 3) ==========

-- Ácaros
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Ácaros', 'Dermatophagoides', 'ALG-INAL-001',
    3, 'Alergia Respiratória', 'Ácaros da Poeira', 'J30.1', true, false,
    'Alergia a ácaros da poeira doméstica. Causa comum de rinite alérgica e asma',
    'Poeira doméstica, colchões, travesseiros, cortinas, tapetes',
    'Muito comum. Medidas ambientais são importantes: capas antiácaros, limpeza frequente, umidade controlada.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-INAL-001');

-- Pólen de Gramíneas
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Pólen de Gramíneas', 'Poaceae', 'ALG-INAL-002',
    3, 'Alergia Respiratória', 'Pólen', 'J30.1', true, false,
    'Alergia ao pólen de gramíneas. Causa comum de rinite alérgica sazonal',
    'Pólen de capim, grama, trigo, milho, outras gramíneas',
    'Muito comum. Sintomas sazonais (primavera/verão). Evitar áreas com grama durante período de polinização.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-INAL-002');

-- Pólen de Árvores
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Pólen de Árvores', 'Várias espécies', 'ALG-INAL-003',
    3, 'Alergia Respiratória', 'Pólen', 'J30.1', true, false,
    'Alergia ao pólen de árvores. Causa comum de rinite alérgica sazonal',
    'Pólen de eucalipto, pinheiro, cipreste, bétula, outras árvores',
    'Comum. Sintomas sazonais (inverno/primavera). Evitar áreas arborizadas durante período de polinização.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-INAL-003');

-- Mofo/Fungos
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Mofo/Fungos', 'Aspergillus, Penicillium', 'ALG-INAL-004',
    3, 'Alergia Respiratória', 'Fungos', 'J30.1', true, false,
    'Alergia a mofo e fungos. Pode causar asma e rinite',
    'Esporos de fungos, mofo doméstico, mofo de alimentos',
    'Comum. Evitar ambientes úmidos, mofados. Manter casa bem ventilada e seca.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-INAL-004');

-- Pelo de Gato
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Pelo de Gato', 'Fel d 1', 'ALG-INAL-005',
    3, 'Alergia Respiratória', 'Animais', 'J30.1', true, false,
    'Alergia ao pelo e caspa de gato. Causa comum de rinite e asma',
    'Pelo de gato, caspa de gato, saliva de gato',
    'Muito comum. Alérgeno presente no pelo, caspa e saliva. Pode persistir no ambiente por meses.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-INAL-005');

-- Pelo de Cachorro
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Pelo de Cachorro', 'Can f 1', 'ALG-INAL-006',
    3, 'Alergia Respiratória', 'Animais', 'J30.1', true, false,
    'Alergia ao pelo e caspa de cachorro. Causa comum de rinite e asma',
    'Pelo de cachorro, caspa de cachorro, saliva de cachorro',
    'Comum. Alérgeno presente no pelo, caspa e saliva.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-INAL-006');

-- Pelo de Cavalo
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Pelo de Cavalo', 'Equ c 1', 'ALG-INAL-007',
    3, 'Alergia Respiratória', 'Animais', 'J30.1', false, false,
    'Alergia ao pelo de cavalo. Pode causar rinite e asma',
    'Pelo de cavalo, caspa de cavalo',
    'Menos comum. Evitar contato com cavalos e estábulos.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-INAL-007');

-- Penas
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Penas', 'Aves', 'ALG-INAL-008',
    3, 'Alergia Respiratória', 'Animais', 'J30.1', false, false,
    'Alergia a penas de aves. Pode causar rinite e asma',
    'Penas de pássaros, travesseiros de penas, cobertores de penas',
    'Menos comum. Evitar travesseiros e cobertores de penas.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-INAL-008');

-- ========== CONTATO (Tipo 4) ==========

-- Látex
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Látex', 'Hevea brasiliensis', 'ALG-CONT-001',
    6, 'Alergia de Contato', 'Material Natural', 'T78.4', true, true,
    'Alergia ao látex natural. Pode causar reações graves incluindo anafilaxia',
    'Luvas de látex, preservativos, balões, produtos de borracha natural',
    'Importante em ambiente hospitalar. Usar luvas sem látex. Reação cruzada com algumas frutas (banana, abacate, kiwi).',
    true
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-CONT-001');

-- Níquel
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Níquel', 'Nickel', 'ALG-CONT-002',
    7, 'Alergia de Contato', 'Metal', 'L23.0', true, false,
    'Alergia ao níquel. Causa comum de dermatite de contato',
    'Bijuterias, botões, fechos, moedas, objetos de metal com níquel',
    'Muito comum, especialmente em mulheres. Evitar contato com objetos contendo níquel.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-CONT-002');

-- Cobalto
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Cobalto', 'Cobalt', 'ALG-CONT-003',
    7, 'Alergia de Contato', 'Metal', 'L23.0', false, false,
    'Alergia ao cobalto. Causa dermatite de contato',
    'Bijuterias, tintas, cerâmicas, objetos com cobalto',
    'Menos comum. Evitar contato com objetos contendo cobalto.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-CONT-003');

-- Cromo
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Cromo', 'Chromium', 'ALG-CONT-004',
    7, 'Alergia de Contato', 'Metal', 'L23.0', false, false,
    'Alergia ao cromo. Causa dermatite de contato',
    'Couro tratado, cimento, tintas, objetos com cromo',
    'Menos comum. Evitar contato com couro tratado e cimento.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-CONT-004');

-- Fragrâncias/Perfumes
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Fragrâncias/Perfumes', 'Fragrance mix', 'ALG-CONT-005',
    8, 'Alergia de Contato', 'Cosmético', 'L23.2', true, false,
    'Alergia a fragrâncias e perfumes. Causa comum de dermatite de contato',
    'Perfumes, produtos de higiene perfumados, detergentes perfumados',
    'Muito comum. Usar produtos sem fragrância. Verificar rótulos de produtos de higiene.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-CONT-005');

-- Conservantes (Parabenos)
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Conservantes (Parabenos)', 'Parabens', 'ALG-CONT-006',
    8, 'Alergia de Contato', 'Cosmético', 'L23.2', false, false,
    'Alergia a parabenos, conservantes usados em cosméticos',
    'Produtos cosméticos, cremes, loções com parabenos',
    'Menos comum. Usar produtos sem parabenos. Verificar rótulos.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-CONT-006');

-- Formaldeído
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Formaldeído', 'Formaldehyde', 'ALG-CONT-007',
    9, 'Alergia de Contato', 'Produto Químico', 'L23.2', false, false,
    'Alergia ao formaldeído. Usado em muitos produtos',
    'Cosméticos, produtos de limpeza, roupas, móveis',
    'Menos comum. Evitar produtos contendo formaldeído.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-CONT-007');

-- ========== INSETOS (Tipo 5) ==========

-- Picada de Abelha
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Picada de Abelha', 'Apis mellifera', 'ALG-INS-001',
    5, 'Alergia a Veneno', 'Himenópteros', 'T63.4', false, true,
    'Alergia ao veneno de abelhas. Reação pode ser grave e fatal',
    'Abelha, possível reação cruzada com vespa',
    'Alergia grave que pode causar anafilaxia fatal. Requer tratamento de emergência imediato. Indicado kit de epinefrina e imunoterapia.',
    true
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-INS-001');

-- Picada de Vespa
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Picada de Vespa', 'Vespula, Polistes', 'ALG-INS-002',
    5, 'Alergia a Veneno', 'Himenópteros', 'T63.4', false, true,
    'Alergia ao veneno de vespas. Reação pode ser grave e fatal',
    'Vespa, marimbondo, possível reação cruzada com abelha',
    'Alergia grave que pode causar anafilaxia fatal. Requer tratamento de emergência imediato. Indicado kit de epinefrina e imunoterapia.',
    true
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-INS-002');

-- Picada de Marimbondo
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Picada de Marimbondo', 'Polybia, Synoeca', 'ALG-INS-003',
    5, 'Alergia a Veneno', 'Himenópteros', 'T63.4', false, true,
    'Alergia ao veneno de marimbondos. Reação pode ser grave',
    'Marimbondo, vespa, possível reação cruzada com abelha',
    'Alergia grave. Requer tratamento de emergência imediato. Indicado kit de epinefrina.',
    true
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-INS-003');

-- Picada de Formiga
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Picada de Formiga', 'Solenopsis, Pachycondyla', 'ALG-INS-004',
    5, 'Alergia a Veneno', 'Formigas', 'T63.4', false, true,
    'Alergia ao veneno de formigas. Pode causar reações graves',
    'Formiga-de-fogo, formiga lava-pés, outras formigas',
    'Alergia grave, especialmente formiga-de-fogo. Requer tratamento de emergência imediato.',
    true
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-INS-004');

-- ========== CONTINUAÇÃO - MAIS ALERGIAS PARA COMPLETAR 100+ ==========

-- Vancomicina
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Vancomicina', NULL, 'ALG-MED-016',
    1, 'Antibiótico Glicopeptídeo', 'Glicopeptídeo', 'Z88.0', false, true,
    'Alergia a vancomicina. Pode causar síndrome do homem vermelho',
    'Teicoplanina, outros glicopeptídeos',
    'Alergia rara. Pode causar síndrome do homem vermelho se infundida rapidamente.',
    true
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-MED-016');

-- Gentamicina
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Gentamicina', NULL, 'ALG-MED-017',
    1, 'Antibiótico Aminoglicosídeo', 'Aminoglicosídeo', 'Z88.0', false, false,
    'Alergia a gentamicina, antibiótico aminoglicosídeo',
    'Amicacina, Tobramicina, outros aminoglicosídeos',
    'Alergia rara. Reação cruzada com outros aminoglicosídeos possível.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-MED-017');

-- Ciprofloxacino
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Ciprofloxacino', NULL, 'ALG-MED-018',
    1, 'Antibiótico Quinolona', 'Quinolona', 'Z88.0', false, false,
    'Alergia a ciprofloxacino, antibiótico quinolona',
    'Levofloxacino, Ofloxacino, outras quinolonas',
    'Alergia rara. Reação cruzada com outras quinolonas possível.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-MED-018');

-- Metronidazol
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Metronidazol', NULL, 'ALG-MED-019',
    1, 'Antibiótico Nitroimidazol', 'Nitroimidazol', 'Z88.0', false, false,
    'Alergia a metronidazol, antibiótico nitroimidazol',
    'Tinidazol, outros nitroimidazóis',
    'Alergia rara. Reação cruzada com outros nitroimidazóis possível.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-MED-019');

-- Clindamicina
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Clindamicina', NULL, 'ALG-MED-020',
    1, 'Antibiótico Lincosamida', 'Lincosamida', 'Z88.0', false, false,
    'Alergia a clindamicina, antibiótico lincosamida',
    'Lincomicina, outros lincosamidas',
    'Alergia rara. Reação cruzada com lincomicina possível.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-MED-020');

-- Omeprazol
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Omeprazol', NULL, 'ALG-MED-021',
    1, 'Inibidor de Bomba de Prótons', 'IBP', 'Z88.0', false, false,
    'Alergia a omeprazol, inibidor de bomba de prótons',
    'Lansoprazol, Pantoprazol, Esomeprazol, outros IBPs',
    'Alergia rara. Reação cruzada com outros IBPs possível.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-MED-021');

-- Losartana
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Losartana', NULL, 'ALG-MED-022',
    1, 'Antagonista de Angiotensina II', 'BRA', 'Z88.0', false, false,
    'Alergia a losartana, bloqueador do receptor de angiotensina',
    'Valsartana, Candesartana, outros BRAs',
    'Alergia rara. Reação cruzada com outros BRAs possível.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-MED-022');

-- Captopril
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Captopril', NULL, 'ALG-MED-023',
    1, 'Inibidor da ECA', 'IECA', 'Z88.0', false, false,
    'Alergia a captopril, inibidor da enzima conversora de angiotensina',
    'Enalapril, Lisinopril, outros IECAs',
    'Alergia rara. Reação cruzada com outros IECAs possível.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-MED-023');

-- Hidroclorotiazida
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Hidroclorotiazida', NULL, 'ALG-MED-024',
    1, 'Diurético Tiazídico', 'Tiazídico', 'Z88.0', false, false,
    'Alergia a hidroclorotiazida, diurético tiazídico',
    'Clortalidona, outros tiazídicos',
    'Alergia rara. Reação cruzada com outros tiazídicos possível. Possível reação cruzada com sulfonamidas.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-MED-024');

-- Furosemida
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Furosemida', NULL, 'ALG-MED-025',
    1, 'Diurético de Alça', 'Diurético de Alça', 'Z88.0', false, false,
    'Alergia a furosemida, diurético de alça',
    'Bumetanida, Torasemida, outros diuréticos de alça',
    'Alergia rara. Possível reação cruzada com sulfonamidas.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-MED-025');

-- Metformina
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Metformina', NULL, 'ALG-MED-026',
    1, 'Antidiabético', 'Biguanida', 'Z88.0', false, false,
    'Alergia a metformina, antidiabético oral',
    'Outros antidiabéticos orais',
    'Alergia rara. Alternativas: outros antidiabéticos orais ou insulina.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-MED-026');

-- Prednisona
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Prednisona', NULL, 'ALG-MED-027',
    1, 'Corticosteroide', 'Corticosteroide', 'Z88.0', false, false,
    'Alergia a prednisona, corticosteroide',
    'Prednisolona, Metilprednisolona, outros corticosteroides',
    'Alergia rara. Reação cruzada com outros corticosteroides possível.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-MED-027');

-- Warfarina
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Warfarina', NULL, 'ALG-MED-028',
    1, 'Anticoagulante', 'Anticoagulante Oral', 'Z88.0', false, false,
    'Alergia a warfarina, anticoagulante oral',
    'Acenocumarol, outros anticoagulantes orais',
    'Alergia rara. Alternativas: anticoagulantes orais diretos (DOACs) ou heparina.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-MED-028');

-- Amiodarona
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Amiodarona', NULL, 'ALG-MED-029',
    1, 'Antiarrítmico', 'Antiarrítmico Classe III', 'Z88.0', false, false,
    'Alergia a amiodarona, antiarrítmico',
    'Outros antiarrítmicos',
    'Alergia rara. Alternativas: outros antiarrítmicos.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-MED-029');

-- Digoxina
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Digoxina', NULL, 'ALG-MED-030',
    1, 'Glicosídeo Cardíaco', 'Glicosídeo Cardíaco', 'Z88.0', false, false,
    'Alergia a digoxina, glicosídeo cardíaco',
    'Digitoxina, outros glicosídeos cardíacos',
    'Alergia rara. Alternativas: outros inotrópicos.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-MED-030');

-- Mais Alimentos

-- Castanha do Pará
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Castanha do Pará', 'Bertholletia excelsa', 'ALG-ALI-021',
    2, 'Alergia Alimentar', 'Oleaginosas', 'T78.1', false, true,
    'Alergia à castanha do Pará. Pode causar reações graves',
    'Outras oleaginosas (nozes, amêndoas, castanha de caju)',
    'Alergia grave. Reação cruzada com outras oleaginosas possível.',
    true
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-ALI-021');

-- Pistache
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Pistache', 'Pistacia vera', 'ALG-ALI-022',
    2, 'Alergia Alimentar', 'Oleaginosas', 'T78.1', false, true,
    'Alergia a pistache. Pode causar reações graves',
    'Outras oleaginosas (nozes, amêndoas)',
    'Alergia grave. Reação cruzada com outras oleaginosas possível.',
    true
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-ALI-022');

-- Avelã
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Avelã', 'Corylus avellana', 'ALG-ALI-023',
    2, 'Alergia Alimentar', 'Oleaginosas', 'T78.1', false, true,
    'Alergia a avelã. Pode causar reações graves',
    'Outras oleaginosas (nozes, amêndoas)',
    'Alergia grave. Reação cruzada com outras oleaginosas possível.',
    true
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-ALI-023');

-- Gergelim
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Gergelim', 'Sesamum indicum', 'ALG-ALI-024',
    2, 'Alergia Alimentar', 'Sementes', 'T78.1', false, true,
    'Alergia a gergelim. Pode causar reações graves',
    'Sementes de gergelim, óleo de gergelim, produtos com gergelim',
    'Alergia grave. Gergelim está presente em muitos alimentos processados. Verificar rótulos.',
    true
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-ALI-024');

-- Mostarda
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Mostarda', 'Brassica', 'ALG-ALI-025',
    2, 'Alergia Alimentar', 'Condimentos', 'T78.1', false, false,
    'Alergia a mostarda. Pode causar reações',
    'Mostarda, produtos com mostarda',
    'Alergia rara. Verificar rótulos de alimentos processados.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-ALI-025');

-- Carne de Porco
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Carne de Porco', 'Sus scrofa', 'ALG-ALI-026',
    2, 'Alergia Alimentar', 'Carnes', 'T78.1', false, false,
    'Alergia à carne de porco. Rara',
    'Carne de porco, produtos derivados de porco',
    'Alergia rara. Pode ser alergia à proteína da carne ou ao alfa-gal (açúcar).',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-ALI-026');

-- Carne de Boi
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Carne de Boi', 'Bos taurus', 'ALG-ALI-027',
    2, 'Alergia Alimentar', 'Carnes', 'T78.1', false, false,
    'Alergia à carne de boi. Pode estar relacionada ao alfa-gal',
    'Carne de boi, produtos derivados de boi',
    'Alergia rara. Pode ser alergia ao alfa-gal (açúcar presente na carne vermelha), geralmente após picada de carrapato.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-ALI-027');

-- Frango
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Frango', 'Gallus gallus', 'ALG-ALI-028',
    2, 'Alergia Alimentar', 'Carnes', 'T78.1', false, false,
    'Alergia a frango. Rara',
    'Carne de frango, produtos derivados de frango',
    'Alergia rara. Pode ser alergia à proteína da carne.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-ALI-028');

-- Lagosta
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Lagosta', 'Homarus', 'ALG-ALI-029',
    2, 'Alergia Alimentar', 'Frutos do Mar', 'T78.1', false, true,
    'Alergia a lagosta. Pode causar reações graves',
    'Camarão, caranguejo, outros crustáceos',
    'Alergia grave. Reação cruzada com outros crustáceos comum.',
    true
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-ALI-029');

-- Caranguejo
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Caranguejo', 'Brachyura', 'ALG-ALI-030',
    2, 'Alergia Alimentar', 'Frutos do Mar', 'T78.1', false, true,
    'Alergia a caranguejo. Pode causar reações graves',
    'Camarão, lagosta, outros crustáceos',
    'Alergia grave. Reação cruzada com outros crustáceos comum.',
    true
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-ALI-030');

-- Mais Inalantes

-- Pólen de Ambrosia
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Pólen de Ambrosia', 'Ambrosia', 'ALG-INAL-009',
    3, 'Alergia Respiratória', 'Pólen', 'J30.1', false, false,
    'Alergia ao pólen de ambrosia. Causa rinite alérgica sazonal',
    'Pólen de ambrosia, outras ervas daninhas',
    'Menos comum no Brasil. Sintomas sazonais (outono).',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-INAL-009');

-- Pólen de Artemísia
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Pólen de Artemísia', 'Artemisia', 'ALG-INAL-010',
    3, 'Alergia Respiratória', 'Pólen', 'J30.1', false, false,
    'Alergia ao pólen de artemísia. Causa rinite alérgica sazonal',
    'Pólen de artemísia, outras ervas daninhas',
    'Menos comum no Brasil. Sintomas sazonais.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-INAL-010');

-- Alternaria (Fungo)
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Alternaria (Fungo)', 'Alternaria alternata', 'ALG-INAL-011',
    3, 'Alergia Respiratória', 'Fungos', 'J30.1', false, false,
    'Alergia ao fungo Alternaria. Pode causar asma',
    'Esporos de Alternaria, outros fungos',
    'Comum em ambientes úmidos. Evitar áreas com mofo.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-INAL-011');

-- Cladosporium (Fungo)
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Cladosporium (Fungo)', 'Cladosporium', 'ALG-INAL-012',
    3, 'Alergia Respiratória', 'Fungos', 'J30.1', false, false,
    'Alergia ao fungo Cladosporium. Pode causar asma',
    'Esporos de Cladosporium, outros fungos',
    'Comum em ambientes úmidos. Evitar áreas com mofo.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-INAL-012');

-- Pelo de Coelho
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Pelo de Coelho', 'Oryctolagus cuniculus', 'ALG-INAL-013',
    3, 'Alergia Respiratória', 'Animais', 'J30.1', false, false,
    'Alergia ao pelo de coelho. Pode causar rinite e asma',
    'Pelo de coelho, caspa de coelho',
    'Menos comum. Evitar contato com coelhos.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-INAL-013');

-- Pelo de Hamster
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Pelo de Hamster', 'Cricetinae', 'ALG-INAL-014',
    3, 'Alergia Respiratória', 'Animais', 'J30.1', false, false,
    'Alergia ao pelo de hamster. Pode causar rinite e asma',
    'Pelo de hamster, caspa de hamster',
    'Menos comum. Evitar contato com hamsters.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-INAL-014');

-- Mais Contatos

-- Bálsamo do Peru
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Bálsamo do Peru', 'Myroxylon balsamum', 'ALG-CONT-008',
    8, 'Alergia de Contato', 'Cosmético', 'L23.2', false, false,
    'Alergia ao bálsamo do Peru. Causa dermatite de contato',
    'Perfumes, produtos de higiene, alimentos com especiarias',
    'Menos comum. Presente em perfumes e alguns alimentos.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-CONT-008');

-- Neomicina
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Neomicina', NULL, 'ALG-CONT-009',
    1, 'Alergia de Contato', 'Medicamento Tópico', 'L23.3', false, false,
    'Alergia à neomicina tópica. Causa dermatite de contato',
    'Neomicina tópica, outros aminoglicosídeos tópicos',
    'Comum em pomadas e cremes. Evitar uso tópico de neomicina.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-CONT-009');

-- Bacitracina
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Bacitracina', NULL, 'ALG-CONT-010',
    1, 'Alergia de Contato', 'Medicamento Tópico', 'L23.3', false, false,
    'Alergia à bacitracina tópica. Causa dermatite de contato',
    'Bacitracina tópica, pomadas com bacitracina',
    'Comum em pomadas. Evitar uso tópico de bacitracina.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-CONT-010');

-- Tintura de Cabelo
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Tintura de Cabelo', 'PPD, Parafenilenodiamina', 'ALG-CONT-011',
    8, 'Alergia de Contato', 'Cosmético', 'L23.2', true, false,
    'Alergia a tintura de cabelo. Causa dermatite de contato',
    'Tinturas de cabelo com PPD, henna preta',
    'Muito comum. Evitar tinturas de cabelo com PPD. Usar tinturas sem PPD ou henna natural.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-CONT-011');

-- Outras Categorias

-- Sol (Fotossensibilidade)
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Sol (Fotossensibilidade)', 'Raios UV', 'ALG-OUT-001',
    14, 'Fotossensibilidade', 'Radiação', 'L56.9', false, false,
    'Fotossensibilidade. Reação alérgica à luz solar',
    'Raios UV, luz solar, alguns medicamentos podem causar fotossensibilidade',
    'Evitar exposição ao sol. Usar protetor solar. Alguns medicamentos podem causar fotossensibilidade.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-OUT-001');

-- Frio (Urticária ao Frio)
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Frio (Urticária ao Frio)', 'Temperatura Baixa', 'ALG-OUT-002',
    15, 'Urticária Física', 'Temperatura', 'L50.2', false, false,
    'Urticária ao frio. Reação alérgica ao frio',
    'Temperatura baixa, água fria, vento frio',
    'Rara. Evitar exposição ao frio. Pode ser grave se exposição for extensa (natação em água fria).',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-OUT-002');

-- Calor (Urticária ao Calor)
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Calor (Urticária ao Calor)', 'Temperatura Alta', 'ALG-OUT-003',
    16, 'Urticária Física', 'Temperatura', 'L50.2', false, false,
    'Urticária ao calor. Reação alérgica ao calor',
    'Temperatura alta, água quente, exercício',
    'Rara. Evitar exposição ao calor excessivo.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-OUT-003');

-- Exercício (Anafilaxia Induzida por Exercício)
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Exercício (Anafilaxia Induzida por Exercício)', 'Exercício Físico', 'ALG-OUT-004',
    18, 'Anafilaxia Induzida', 'Exercício', 'T78.3', false, true,
    'Anafilaxia induzida por exercício. Reação alérgica grave desencadeada por exercício',
    'Exercício físico, pode ser associado a ingestão de alimentos antes do exercício',
    'Rara mas grave. Pode ser associada a ingestão de alimentos antes do exercício. Evitar exercício após refeições. Kit de epinefrina necessário.',
    true
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-OUT-004');

-- Água (Urticária Aquagênica)
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Água (Urticária Aquagênica)', 'Água', 'ALG-OUT-005',
    17, 'Urticária Física', 'Água', 'L50.2', false, false,
    'Urticária aquagênica. Reação alérgica à água',
    'Água de qualquer temperatura, suor, lágrimas',
    'Muito rara. Reação ao contato com água de qualquer temperatura.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-OUT-005');

-- Pressão (Urticária por Pressão)
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Pressão (Urticária por Pressão)', 'Pressão Física', 'ALG-OUT-006',
    99, 'Urticária Física', 'Pressão', 'L50.2', false, false,
    'Urticária por pressão. Reação alérgica à pressão física',
    'Pressão física, roupas apertadas, cintos, sapatos',
    'Rara. Evitar pressão prolongada sobre a pele.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-OUT-006');

-- Vibração (Urticária por Vibração)
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Vibração (Urticária por Vibração)', 'Vibração Física', 'ALG-OUT-007',
    99, 'Urticária Física', 'Vibração', 'L50.2', false, false,
    'Urticária por vibração. Reação alérgica à vibração',
    'Vibração física, ferramentas elétricas, veículos',
    'Muito rara. Evitar exposição a vibração.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-OUT-007');

-- Estresse (Urticária por Estresse)
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Estresse (Urticária por Estresse)', 'Estresse Emocional', 'ALG-OUT-008',
    19, 'Urticária Induzida', 'Estresse', 'L50.8', false, false,
    'Urticária induzida por estresse. Reação alérgica desencadeada por estresse emocional',
    'Estresse emocional, ansiedade, tensão',
    'Comum. Gerenciamento de estresse importante. Pode ser associada a outras condições.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-OUT-008');

-- Ouro
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Ouro', 'Aurum', 'ALG-CONT-012',
    7, 'Alergia de Contato', 'Metal', 'L23.0', false, false,
    'Alergia ao ouro. Causa dermatite de contato',
    'Joias de ouro, objetos de ouro',
    'Menos comum que níquel. Evitar contato com ouro.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-CONT-012');

-- Mercúrio
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Mercúrio', 'Hydrargyrum', 'ALG-CONT-013',
    7, 'Alergia de Contato', 'Metal', 'L23.0', false, false,
    'Alergia ao mercúrio. Causa dermatite de contato',
    'Amálgama dentário, termômetros, objetos com mercúrio',
    'Menos comum. Evitar contato com mercúrio. Amálgama dentário pode causar reação.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-CONT-013');

-- Colofônia (Resina)
INSERT INTO public.alergias (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_alergia, categoria, subcategoria, codigo_cid, alergia_comum, alergia_grave,
    descricao, substancias_relacionadas, observacoes, epinefrina_necessaria
)
SELECT gen_random_uuid(), NOW(), NOW(), true, 'Colofônia (Resina)', 'Colophony', 'ALG-CONT-014',
    9, 'Alergia de Contato', 'Produto Químico', 'L23.2', false, false,
    'Alergia à colofônia. Causa dermatite de contato',
    'Esparadrapo, adesivos, produtos com colofônia',
    'Comum em esparadrapo e adesivos. Evitar contato com produtos contendo colofônia.',
    false
WHERE NOT EXISTS (SELECT 1 FROM public.alergias WHERE codigo_interno = 'ALG-CONT-014');
