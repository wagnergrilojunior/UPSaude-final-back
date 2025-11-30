-- Script de Seed: Deficiências (Escopo Global)
-- Cria deficiências conforme classificação SUS/e-SUS/APS - dados globais sem tenant
-- Baseado na CIF (Classificação Internacional de Funcionalidade, Incapacidade e Saúde)
-- Executado quando app.seed.enabled=true

-- Deficiência Física - Amputação de Membros Inferiores
INSERT INTO public.deficiencias (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    descricao,
    tipo_deficiencia,
    cid10_relacionado,
    permanente,
    acompanhamento_continuo
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Deficiência Física - Amputação de Membros Inferiores',
    'Perda total ou parcial de um ou ambos os membros inferiores (pernas) que afeta a mobilidade e funcionalidade',
    1,
    'Z89.4',
    true,
    true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Deficiência Física - Amputação de Membros Inferiores');

-- Deficiência Física - Paraplegia
INSERT INTO public.deficiencias (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    descricao,
    tipo_deficiencia,
    cid10_relacionado,
    permanente,
    acompanhamento_continuo
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Deficiência Física - Paraplegia',
    'Paralisia completa ou incompleta dos membros inferiores, resultando em perda de movimento e sensibilidade',
    1,
    'G82.2',
    true,
    true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Deficiência Física - Paraplegia');

-- Deficiência Física - Tetraplegia
INSERT INTO public.deficiencias (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    descricao,
    tipo_deficiencia,
    cid10_relacionado,
    permanente,
    acompanhamento_continuo
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Deficiência Física - Tetraplegia',
    'Paralisia completa ou incompleta dos quatro membros, resultando em perda de movimento e sensibilidade',
    1,
    'G82.5',
    true,
    true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Deficiência Física - Tetraplegia');

-- Deficiência Física - Paralisia Cerebral
INSERT INTO public.deficiencias (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    descricao,
    tipo_deficiencia,
    cid10_relacionado,
    permanente,
    acompanhamento_continuo
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Deficiência Física - Paralisia Cerebral',
    'Grupo de distúrbios permanentes que afetam o movimento e a postura, causados por danos no cérebro em desenvolvimento',
    1,
    'G80',
    true,
    true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Deficiência Física - Paralisia Cerebral');

-- Deficiência Auditiva - Surdez Profunda
INSERT INTO public.deficiencias (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    descricao,
    tipo_deficiencia,
    cid10_relacionado,
    permanente,
    acompanhamento_continuo
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Deficiência Auditiva - Surdez Profunda',
    'Perda auditiva profunda bilateral que impede a compreensão da fala mesmo com aparelho auditivo',
    2,
    'H91.3',
    true,
    true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Deficiência Auditiva - Surdez Profunda');

-- Deficiência Auditiva - Perda Auditiva Severa
INSERT INTO public.deficiencias (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    descricao,
    tipo_deficiencia,
    cid10_relacionado,
    permanente,
    acompanhamento_continuo
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Deficiência Auditiva - Perda Auditiva Severa',
    'Perda auditiva severa que afeta significativamente a comunicação, necessitando de recursos de comunicação alternativa',
    2,
    'H90.3',
    true,
    true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Deficiência Auditiva - Perda Auditiva Severa');

-- Deficiência Visual - Cegueira
INSERT INTO public.deficiencias (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    descricao,
    tipo_deficiencia,
    cid10_relacionado,
    permanente,
    acompanhamento_continuo
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Deficiência Visual - Cegueira',
    'Perda total ou quase total da capacidade de visão, afetando a mobilidade e independência',
    3,
    'H54.0',
    true,
    true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Deficiência Visual - Cegueira');

-- Deficiência Visual - Baixa Visão
INSERT INTO public.deficiencias (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    descricao,
    tipo_deficiencia,
    cid10_relacionado,
    permanente,
    acompanhamento_continuo
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Deficiência Visual - Baixa Visão',
    'Comprometimento significativo da função visual que não pode ser corrigido com óculos ou lentes de contato, mas mantém resíduo visual útil',
    3,
    'H54.2',
    true,
    true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Deficiência Visual - Baixa Visão');

-- Deficiência Intelectual - Leve
INSERT INTO public.deficiencias (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    descricao,
    tipo_deficiencia,
    cid10_relacionado,
    permanente,
    acompanhamento_continuo
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Deficiência Intelectual - Leve',
    'Deficiência intelectual leve caracterizada por limitações significativas no funcionamento intelectual e no comportamento adaptativo',
    4,
    'F70',
    true,
    true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Deficiência Intelectual - Leve');

-- Deficiência Intelectual - Moderada
INSERT INTO public.deficiencias (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    descricao,
    tipo_deficiencia,
    cid10_relacionado,
    permanente,
    acompanhamento_continuo
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Deficiência Intelectual - Moderada',
    'Deficiência intelectual moderada com limitações mais significativas que requerem suporte contínuo',
    4,
    'F71',
    true,
    true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Deficiência Intelectual - Moderada');

-- Deficiência Múltipla
INSERT INTO public.deficiencias (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    descricao,
    tipo_deficiencia,
    cid10_relacionado,
    permanente,
    acompanhamento_continuo
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Deficiência Múltipla',
    'Associação de duas ou mais deficiências simultâneas (física, sensorial, intelectual ou mental)',
    5,
    NULL,
    true,
    true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Deficiência Múltipla');

-- Deficiência Psicossocial
INSERT INTO public.deficiencias (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    descricao,
    tipo_deficiencia,
    cid10_relacionado,
    permanente,
    acompanhamento_continuo
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Deficiência Psicossocial',
    'Deficiência decorrente de transtorno mental que afeta a funcionalidade e participação social',
    6,
    NULL,
    false,
    true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Deficiência Psicossocial');

