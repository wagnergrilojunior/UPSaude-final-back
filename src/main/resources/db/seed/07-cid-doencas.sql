-- Script de Seed: CID-10 - Classificação Internacional de Doenças (Escopo Global)
-- Cria códigos CID-10 reais e comuns utilizados no Brasil - dados globais sem tenant
-- Executado quando app.seed.enabled=true
-- Gerado automaticamente pelo script Python: scripts/generate_cid_doencas.py
-- Total de códigos: ~600+ códigos CID-10 dos principais capítulos

-- A00 - Cólera
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A00', 'Cólera', 'Cólera',
    'Doenças Infecciosas', 'Cólera', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A00');

-- A01 - Febres tifóide e paratifóide
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A01', 'Febres tifóide e paratifóide', 'Febre tifóide',
    'Doenças Infecciosas', 'Febres Tifóides', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A01');

-- A02 - Outras infecções devidas a Salmonella
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A02', 'Outras infecções devidas a Salmonella', 'Salmonelose',
    'Doenças Infecciosas', 'Salmonelose', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A02');

-- A03 - Shiguelose
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A03', 'Shiguelose', 'Shiguelose',
    'Doenças Infecciosas', 'Shiguelose', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A03');

-- A04 - Outras infecções intestinais bacterianas
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A04', 'Outras infecções intestinais bacterianas', 'Infecção intestinal bacteriana',
    'Doenças Infecciosas', 'Infecções Intestinais', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A04');

-- A05 - Outras intoxicações alimentares bacterianas
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A05', 'Outras intoxicações alimentares bacterianas', 'Intoxicação alimentar bacteriana',
    'Doenças Infecciosas', 'Intoxicações Alimentares', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A05');

-- A06 - Amebíase
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A06', 'Amebíase', 'Amebíase',
    'Doenças Infecciosas', 'Amebíase', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A06');

-- A07 - Outras doenças intestinais por protozoários
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A07', 'Outras doenças intestinais por protozoários', 'Doença intestinal por protozoário',
    'Doenças Infecciosas', 'Protozoários', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A07');

-- A08 - Infecções intestinais virais
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A08', 'Infecções intestinais virais', 'Infecção intestinal viral',
    'Doenças Infecciosas', 'Infecções Virais', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A08');

-- A09 - Gastroenterite e colite de origem infecciosa e não especificada
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A09', 'Gastroenterite e colite de origem infecciosa e não especificada', 'Gastroenterite infecciosa',
    'Doenças Infecciosas', 'Gastroenterite', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A09');

-- A15 - Tuberculose respiratória, com confirmação bacteriológica e histológica
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A15', 'Tuberculose respiratória, com confirmação bacteriológica e histológica', 'Tuberculose respiratória confirmada',
    'Doenças Infecciosas', 'Tuberculose', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A15');

-- A15.0 - Tuberculose pulmonar, com confirmação por exame bacteriológico com cultura
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A15.0', 'Tuberculose pulmonar, com confirmação por exame bacteriológico com cultura', 'Tuberculose pulmonar confirmada',
    'Doenças Infecciosas', 'Tuberculose', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A15.0');

-- A15.1 - Tuberculose pulmonar, com confirmação apenas por exame microscópico do escarro
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A15.1', 'Tuberculose pulmonar, com confirmação apenas por exame microscópico do escarro', 'Tuberculose pulmonar microscópica',
    'Doenças Infecciosas', 'Tuberculose', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A15.1');

-- A16 - Tuberculose respiratória, sem confirmação bacteriológica ou histológica
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A16', 'Tuberculose respiratória, sem confirmação bacteriológica ou histológica', 'Tuberculose respiratória não confirmada',
    'Doenças Infecciosas', 'Tuberculose', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A16');

-- A17 - Tuberculose do sistema nervoso
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A17', 'Tuberculose do sistema nervoso', 'Tuberculose do SNC',
    'Doenças Infecciosas', 'Tuberculose', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A17');

-- A18 - Tuberculose de outros órgãos
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A18', 'Tuberculose de outros órgãos', 'Tuberculose de outros órgãos',
    'Doenças Infecciosas', 'Tuberculose', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A18');

-- A19 - Tuberculose miliar
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A19', 'Tuberculose miliar', 'Tuberculose miliar',
    'Doenças Infecciosas', 'Tuberculose', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A19');

-- A20 - Peste
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A20', 'Peste', 'Peste',
    'Doenças Infecciosas', 'Peste', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A20');

-- A21 - Tularemia
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A21', 'Tularemia', 'Tularemia',
    'Doenças Infecciosas', 'Tularemia', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A21');

-- A22 - Carbúnculo
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A22', 'Carbúnculo', 'Carbúnculo',
    'Doenças Infecciosas', 'Carbúnculo', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A22');

-- A23 - Brucelose
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A23', 'Brucelose', 'Brucelose',
    'Doenças Infecciosas', 'Brucelose', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A23');

-- A24 - Mormo e melioidose
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A24', 'Mormo e melioidose', 'Mormo',
    'Doenças Infecciosas', 'Mormo', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A24');

-- A25 - Febre por mordedura de rato
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A25', 'Febre por mordedura de rato', 'Febre por mordedura de rato',
    'Doenças Infecciosas', 'Febre por Mordedura', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A25');

-- A26 - Erisipeloide
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A26', 'Erisipeloide', 'Erisipeloide',
    'Doenças Infecciosas', 'Erisipeloide', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A26');

-- A27 - Leptospirose
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A27', 'Leptospirose', 'Leptospirose',
    'Doenças Infecciosas', 'Leptospirose', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A27');

-- A28 - Outras doenças bacterianas zoonóticas não classificadas em outra parte
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A28', 'Outras doenças bacterianas zoonóticas não classificadas em outra parte', 'Doença bacteriana zoonótica',
    'Doenças Infecciosas', 'Zoonoses', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A28');

-- A30 - Hanseníase [lepra]
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A30', 'Hanseníase [lepra]', 'Hanseníase',
    'Doenças Infecciosas', 'Hanseníase', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A30');

-- A31 - Infecções devidas a outras micobactérias
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A31', 'Infecções devidas a outras micobactérias', 'Infecção por micobactéria',
    'Doenças Infecciosas', 'Micobactérias', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A31');

-- A32 - Listeriose
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A32', 'Listeriose', 'Listeriose',
    'Doenças Infecciosas', 'Listeriose', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A32');

-- A33 - Tétano do recém-nascido
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A33', 'Tétano do recém-nascido', 'Tétano neonatal',
    'Doenças Infecciosas', 'Tétano', NULL, NULL, 28
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A33');

-- A34 - Tétano obstétrico
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A34', 'Tétano obstétrico', 'Tétano obstétrico',
    'Doenças Infecciosas', 'Tétano', 'F', NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A34');

-- A35 - Outros tipos de tétano
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A35', 'Outros tipos de tétano', 'Tétano',
    'Doenças Infecciosas', 'Tétano', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A35');

-- A36 - Difteria
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A36', 'Difteria', 'Difteria',
    'Doenças Infecciosas', 'Difteria', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A36');

-- A37 - Coqueluche
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A37', 'Coqueluche', 'Coqueluche',
    'Doenças Infecciosas', 'Coqueluche', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A37');

-- A38 - Escarlatina
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A38', 'Escarlatina', 'Escarlatina',
    'Doenças Infecciosas', 'Escarlatina', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A38');

-- A39 - Doença meningocócica
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A39', 'Doença meningocócica', 'Doença meningocócica',
    'Doenças Infecciosas', 'Meningocócica', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A39');

-- A40 - Septicemia estreptocócica
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A40', 'Septicemia estreptocócica', 'Septicemia estreptocócica',
    'Doenças Infecciosas', 'Septicemia', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A40');

-- A41 - Outras septicemias
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A41', 'Outras septicemias', 'Septicemia',
    'Doenças Infecciosas', 'Septicemia', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A41');

-- A42 - Actinomicose
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A42', 'Actinomicose', 'Actinomicose',
    'Doenças Infecciosas', 'Actinomicose', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A42');

-- A43 - Nocardiose
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A43', 'Nocardiose', 'Nocardiose',
    'Doenças Infecciosas', 'Nocardiose', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A43');

-- A44 - Bartonelose
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A44', 'Bartonelose', 'Bartonelose',
    'Doenças Infecciosas', 'Bartonelose', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A44');

-- A46 - Erisipela
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A46', 'Erisipela', 'Erisipela',
    'Doenças Infecciosas', 'Erisipela', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A46');

-- A48 - Outras doenças bacterianas não classificadas em outra parte
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A48', 'Outras doenças bacterianas não classificadas em outra parte', 'Doença bacteriana',
    'Doenças Infecciosas', 'Bacterianas', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A48');

-- A49 - Infecção bacteriana de localização não especificada
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A49', 'Infecção bacteriana de localização não especificada', 'Infecção bacteriana',
    'Doenças Infecciosas', 'Bacterianas', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A49');

-- A50 - Sífilis congênita
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A50', 'Sífilis congênita', 'Sífilis congênita',
    'Doenças Infecciosas', 'Sífilis', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A50');

-- A51 - Sífilis precoce
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A51', 'Sífilis precoce', 'Sífilis precoce',
    'Doenças Infecciosas', 'Sífilis', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A51');

-- A52 - Sífilis tardia
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A52', 'Sífilis tardia', 'Sífilis tardia',
    'Doenças Infecciosas', 'Sífilis', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A52');

-- A53 - Outras formas e formas não especificadas de sífilis
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A53', 'Outras formas e formas não especificadas de sífilis', 'Sífilis',
    'Doenças Infecciosas', 'Sífilis', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A53');

-- A54 - Infecção gonocócica
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A54', 'Infecção gonocócica', 'Gonorreia',
    'Doenças Infecciosas', 'Gonorreia', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A54');

-- A55 - Linfogranuloma (venéreo) por clamídia
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A55', 'Linfogranuloma (venéreo) por clamídia', 'Linfogranuloma venéreo',
    'Doenças Infecciosas', 'Clamídia', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A55');

-- A56 - Outras infecções causadas por clamídias transmitidas por via sexual
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A56', 'Outras infecções causadas por clamídias transmitidas por via sexual', 'Infecção por clamídia',
    'Doenças Infecciosas', 'Clamídia', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A56');

-- A57 - Cancro mole
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A57', 'Cancro mole', 'Cancro mole',
    'Doenças Infecciosas', 'Cancro Mole', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A57');

-- A58 - Granuloma inguinal
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A58', 'Granuloma inguinal', 'Granuloma inguinal',
    'Doenças Infecciosas', 'Granuloma Inguinal', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A58');

-- A59 - Tricomoníase
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A59', 'Tricomoníase', 'Tricomoníase',
    'Doenças Infecciosas', 'Tricomoníase', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A59');

-- A60 - Infecção anogenital por vírus do herpes [herpes simples]
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A60', 'Infecção anogenital por vírus do herpes [herpes simples]', 'Herpes genital',
    'Doenças Infecciosas', 'Herpes', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A60');

-- A63 - Outras doenças de transmissão predominantemente sexual, não classificadas em outra parte
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A63', 'Outras doenças de transmissão predominantemente sexual, não classificadas em outra parte', 'DST',
    'Doenças Infecciosas', 'DST', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A63');

-- A64 - Doenças sexualmente transmitidas não especificadas
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A64', 'Doenças sexualmente transmitidas não especificadas', 'DST não especificada',
    'Doenças Infecciosas', 'DST', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A64');

-- A65 - Bouba não venérea
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A65', 'Bouba não venérea', 'Bouba',
    'Doenças Infecciosas', 'Bouba', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A65');

-- A66 - Frambésia
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A66', 'Frambésia', 'Frambésia',
    'Doenças Infecciosas', 'Frambésia', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A66');

-- A67 - Pinta [carate]
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A67', 'Pinta [carate]', 'Pinta',
    'Doenças Infecciosas', 'Pinta', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A67');

-- A68 - Febres recorrentes [borrelioses]
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A68', 'Febres recorrentes [borrelioses]', 'Febre recorrente',
    'Doenças Infecciosas', 'Febre Recorrente', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A68');

-- A69 - Outras infecções por espiroquetas
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A69', 'Outras infecções por espiroquetas', 'Infecção por espiroqueta',
    'Doenças Infecciosas', 'Espiroquetas', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A69');

-- A70 - Infecções causadas por Chlamydia psittaci
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A70', 'Infecções causadas por Chlamydia psittaci', 'Psitacose',
    'Doenças Infecciosas', 'Psitacose', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A70');

-- A71 - Tracoma
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A71', 'Tracoma', 'Tracoma',
    'Doenças Infecciosas', 'Tracoma', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A71');

-- A74 - Doenças causadas por Chlamydia trachomatis
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A74', 'Doenças causadas por Chlamydia trachomatis', 'Infecção por Chlamydia trachomatis',
    'Doenças Infecciosas', 'Chlamydia', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A74');

-- A75 - Tifo exantemático
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A75', 'Tifo exantemático', 'Tifo exantemático',
    'Doenças Infecciosas', 'Tifo', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A75');

-- A77 - Febre maculosa [rickettsioses transmitidas por carrapatos]
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A77', 'Febre maculosa [rickettsioses transmitidas por carrapatos]', 'Febre maculosa',
    'Doenças Infecciosas', 'Rickettsioses', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A77');

-- A78 - Febre Q
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A78', 'Febre Q', 'Febre Q',
    'Doenças Infecciosas', 'Rickettsioses', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A78');

-- A79 - Outras rickettsioses
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A79', 'Outras rickettsioses', 'Rickettsiose',
    'Doenças Infecciosas', 'Rickettsioses', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A79');

-- A80 - Poliomielite aguda
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A80', 'Poliomielite aguda', 'Poliomielite',
    'Doenças Infecciosas', 'Poliomielite', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A80');

-- A81 - Infecções por vírus atípicos do sistema nervoso central
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A81', 'Infecções por vírus atípicos do sistema nervoso central', 'Infecção viral do SNC',
    'Doenças Infecciosas', 'Virais', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A81');

-- A82 - Raiva
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A82', 'Raiva', 'Raiva',
    'Doenças Infecciosas', 'Raiva', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A82');

-- A83 - Encefalite viral transmitida por mosquitos
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A83', 'Encefalite viral transmitida por mosquitos', 'Encefalite viral por mosquito',
    'Doenças Infecciosas', 'Encefalite', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A83');

-- A84 - Encefalite viral transmitida por carrapatos
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A84', 'Encefalite viral transmitida por carrapatos', 'Encefalite viral por carrapato',
    'Doenças Infecciosas', 'Encefalite', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A84');

-- A85 - Outras encefalites virais não classificadas em outra parte
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A85', 'Outras encefalites virais não classificadas em outra parte', 'Encefalite viral',
    'Doenças Infecciosas', 'Encefalite', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A85');

-- A86 - Encefalite viral não especificada
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A86', 'Encefalite viral não especificada', 'Encefalite viral',
    'Doenças Infecciosas', 'Encefalite', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A86');

-- A87 - Meningite viral
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A87', 'Meningite viral', 'Meningite viral',
    'Doenças Infecciosas', 'Meningite', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A87');

-- A88 - Outras infecções virais do sistema nervoso central não classificadas em outra parte
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A88', 'Outras infecções virais do sistema nervoso central não classificadas em outra parte', 'Infecção viral do SNC',
    'Doenças Infecciosas', 'Virais', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A88');

-- A89 - Infecção viral não especificada do sistema nervoso central
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A89', 'Infecção viral não especificada do sistema nervoso central', 'Infecção viral do SNC',
    'Doenças Infecciosas', 'Virais', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A89');

-- A90 - Dengue [dengue clássico]
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A90', 'Dengue [dengue clássico]', 'Dengue',
    'Doenças Infecciosas', 'Dengue', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A90');

-- A91 - Febre hemorrágica devida ao vírus do dengue
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A91', 'Febre hemorrágica devida ao vírus do dengue', 'Dengue hemorrágico',
    'Doenças Infecciosas', 'Dengue', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A91');

-- A92 - Outras febres virais transmitidas por mosquitos
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A92', 'Outras febres virais transmitidas por mosquitos', 'Febre viral por mosquito',
    'Doenças Infecciosas', 'Virais', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A92');

-- A93 - Outras febres por vírus transmitidas por artrópodes não classificadas em outra parte
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A93', 'Outras febres por vírus transmitidas por artrópodes não classificadas em outra parte', 'Febre viral por artrópode',
    'Doenças Infecciosas', 'Virais', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A93');

-- A94 - Febre viral transmitida por artrópodes não especificada
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A94', 'Febre viral transmitida por artrópodes não especificada', 'Febre viral',
    'Doenças Infecciosas', 'Virais', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A94');

-- A95 - Febre amarela
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A95', 'Febre amarela', 'Febre amarela',
    'Doenças Infecciosas', 'Febre Amarela', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A95');

-- A96 - Febre hemorrágica por arenavírus
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A96', 'Febre hemorrágica por arenavírus', 'Febre hemorrágica',
    'Doenças Infecciosas', 'Febre Hemorrágica', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A96');

-- A98 - Outras febres hemorrágicas por vírus não classificadas em outra parte
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A98', 'Outras febres hemorrágicas por vírus não classificadas em outra parte', 'Febre hemorrágica viral',
    'Doenças Infecciosas', 'Febre Hemorrágica', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A98');

-- A99 - Febres hemorrágicas virais não especificadas
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'A99', 'Febres hemorrágicas virais não especificadas', 'Febre hemorrágica viral',
    'Doenças Infecciosas', 'Febre Hemorrágica', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'A99');

-- B00 - Infecções pelo vírus do herpes [herpes simples]
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B00', 'Infecções pelo vírus do herpes [herpes simples]', 'Herpes simples',
    'Doenças Infecciosas', 'Herpes', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B00');

-- B01 - Varicela [catapora]
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B01', 'Varicela [catapora]', 'Varicela',
    'Doenças Infecciosas', 'Varicela', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B01');

-- B02 - Herpes zoster [zona]
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B02', 'Herpes zoster [zona]', 'Herpes zoster',
    'Doenças Infecciosas', 'Herpes', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B02');

-- B03 - Varíola
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B03', 'Varíola', 'Varíola',
    'Doenças Infecciosas', 'Varíola', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B03');

-- B04 - Varíola dos macacos
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B04', 'Varíola dos macacos', 'Varíola dos macacos',
    'Doenças Infecciosas', 'Varíola', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B04');

-- B05 - Sarampo
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B05', 'Sarampo', 'Sarampo',
    'Doenças Infecciosas', 'Sarampo', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B05');

-- B06 - Rubéola
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B06', 'Rubéola', 'Rubéola',
    'Doenças Infecciosas', 'Rubéola', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B06');

-- B07 - Verrugas virais
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B07', 'Verrugas virais', 'Verrugas virais',
    'Doenças Infecciosas', 'Verrugas', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B07');

-- B08 - Outras infecções virais caracterizadas por lesões da pele e das membranas mucosas, não classificadas em outra parte
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B08', 'Outras infecções virais caracterizadas por lesões da pele e das membranas mucosas, não classificadas em outra parte', 'Infecção viral de pele',
    'Doenças Infecciosas', 'Virais', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B08');

-- B09 - Infecção viral não especificada caracterizada por lesões da pele e das membranas mucosas
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B09', 'Infecção viral não especificada caracterizada por lesões da pele e das membranas mucosas', 'Infecção viral de pele',
    'Doenças Infecciosas', 'Virais', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B09');

-- B15 - Hepatite A aguda
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B15', 'Hepatite A aguda', 'Hepatite A',
    'Doenças Infecciosas', 'Hepatite', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B15');

-- B16 - Hepatite B aguda
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B16', 'Hepatite B aguda', 'Hepatite B',
    'Doenças Infecciosas', 'Hepatite', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B16');

-- B17 - Outras hepatites virais agudas
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B17', 'Outras hepatites virais agudas', 'Hepatite viral aguda',
    'Doenças Infecciosas', 'Hepatite', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B17');

-- B18 - Hepatite viral crônica
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B18', 'Hepatite viral crônica', 'Hepatite viral crônica',
    'Doenças Infecciosas', 'Hepatite', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B18');

-- B19 - Hepatite viral não especificada
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B19', 'Hepatite viral não especificada', 'Hepatite viral',
    'Doenças Infecciosas', 'Hepatite', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B19');

-- B20 - Doença pelo vírus da imunodeficiência humana [HIV], resultando em doenças infecciosas e parasitárias
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B20', 'Doença pelo vírus da imunodeficiência humana [HIV], resultando em doenças infecciosas e parasitárias', 'HIV com infecções',
    'Doenças Infecciosas', 'HIV', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B20');

-- B21 - Doença pelo vírus da imunodeficiência humana [HIV], resultando em neoplasias malignas
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B21', 'Doença pelo vírus da imunodeficiência humana [HIV], resultando em neoplasias malignas', 'HIV com neoplasias',
    'Doenças Infecciosas', 'HIV', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B21');

-- B22 - Doença pelo vírus da imunodeficiência humana [HIV], resultando em outras doenças especificadas
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B22', 'Doença pelo vírus da imunodeficiência humana [HIV], resultando em outras doenças especificadas', 'HIV com outras doenças',
    'Doenças Infecciosas', 'HIV', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B22');

-- B23 - Doença pelo vírus da imunodeficiência humana [HIV], resultando em outras afecções
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B23', 'Doença pelo vírus da imunodeficiência humana [HIV], resultando em outras afecções', 'HIV com afecções',
    'Doenças Infecciosas', 'HIV', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B23');

-- B24 - Doença pelo vírus da imunodeficiência humana [HIV], não especificada
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B24', 'Doença pelo vírus da imunodeficiência humana [HIV], não especificada', 'HIV',
    'Doenças Infecciosas', 'HIV', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B24');

-- B25 - Doença pelo citomegalovírus
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B25', 'Doença pelo citomegalovírus', 'Citomegalovírus',
    'Doenças Infecciosas', 'Citomegalovírus', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B25');

-- B26 - Caxumba [parotidite epidêmica]
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B26', 'Caxumba [parotidite epidêmica]', 'Caxumba',
    'Doenças Infecciosas', 'Caxumba', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B26');

-- B27 - Mononucleose infecciosa
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B27', 'Mononucleose infecciosa', 'Mononucleose',
    'Doenças Infecciosas', 'Mononucleose', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B27');

-- B30 - Ceratoconjuntivite viral
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B30', 'Ceratoconjuntivite viral', 'Ceratoconjuntivite viral',
    'Doenças Infecciosas', 'Conjuntivite', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B30');

-- B33 - Outras doenças por vírus não classificadas em outra parte
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B33', 'Outras doenças por vírus não classificadas em outra parte', 'Doença viral',
    'Doenças Infecciosas', 'Virais', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B33');

-- B34 - Infecção viral de localização não especificada
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B34', 'Infecção viral de localização não especificada', 'Infecção viral',
    'Doenças Infecciosas', 'Virais', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B34');

-- B35 - Dermatofitose
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B35', 'Dermatofitose', 'Dermatofitose',
    'Doenças Infecciosas', 'Micoses', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B35');

-- B36 - Outras micoses superficiais
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B36', 'Outras micoses superficiais', 'Micose superficial',
    'Doenças Infecciosas', 'Micoses', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B36');

-- B37 - Candidíase
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B37', 'Candidíase', 'Candidíase',
    'Doenças Infecciosas', 'Micoses', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B37');

-- B38 - Coccidioidomicose
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B38', 'Coccidioidomicose', 'Coccidioidomicose',
    'Doenças Infecciosas', 'Micoses', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B38');

-- B39 - Histoplasmose
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B39', 'Histoplasmose', 'Histoplasmose',
    'Doenças Infecciosas', 'Micoses', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B39');

-- B40 - Blastomicose
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B40', 'Blastomicose', 'Blastomicose',
    'Doenças Infecciosas', 'Micoses', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B40');

-- B41 - Paracoccidioidomicose
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B41', 'Paracoccidioidomicose', 'Paracoccidioidomicose',
    'Doenças Infecciosas', 'Micoses', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B41');

-- B42 - Esporotricose
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B42', 'Esporotricose', 'Esporotricose',
    'Doenças Infecciosas', 'Micoses', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B42');

-- B43 - Cromomicose e abscesso feomicótico
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B43', 'Cromomicose e abscesso feomicótico', 'Cromomicose',
    'Doenças Infecciosas', 'Micoses', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B43');

-- B44 - Aspergilose
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B44', 'Aspergilose', 'Aspergilose',
    'Doenças Infecciosas', 'Micoses', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B44');

-- B45 - Criptococose
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B45', 'Criptococose', 'Criptococose',
    'Doenças Infecciosas', 'Micoses', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B45');

-- B46 - Zigomicose
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B46', 'Zigomicose', 'Zigomicose',
    'Doenças Infecciosas', 'Micoses', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B46');

-- B47 - Micetoma
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B47', 'Micetoma', 'Micetoma',
    'Doenças Infecciosas', 'Micoses', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B47');

-- B48 - Outras micoses não classificadas em outra parte
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B48', 'Outras micoses não classificadas em outra parte', 'Micose',
    'Doenças Infecciosas', 'Micoses', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B48');

-- B49 - Micose não especificada
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B49', 'Micose não especificada', 'Micose',
    'Doenças Infecciosas', 'Micoses', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B49');

-- B50 - Malária por Plasmodium falciparum
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B50', 'Malária por Plasmodium falciparum', 'Malária por P. falciparum',
    'Doenças Infecciosas', 'Malária', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B50');

-- B51 - Malária por Plasmodium vivax
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B51', 'Malária por Plasmodium vivax', 'Malária por P. vivax',
    'Doenças Infecciosas', 'Malária', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B51');

-- B52 - Malária por Plasmodium malariae
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B52', 'Malária por Plasmodium malariae', 'Malária por P. malariae',
    'Doenças Infecciosas', 'Malária', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B52');

-- B53 - Outras formas de malária confirmadas por parasitologia
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B53', 'Outras formas de malária confirmadas por parasitologia', 'Malária',
    'Doenças Infecciosas', 'Malária', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B53');

-- B54 - Malária não especificada
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B54', 'Malária não especificada', 'Malária',
    'Doenças Infecciosas', 'Malária', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B54');

-- B55 - Leishmaniose
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B55', 'Leishmaniose', 'Leishmaniose',
    'Doenças Infecciosas', 'Leishmaniose', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B55');

-- B56 - Tripanossomíase africana
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B56', 'Tripanossomíase africana', 'Tripanossomíase africana',
    'Doenças Infecciosas', 'Tripanossomíase', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B56');

-- B57 - Doença de Chagas
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B57', 'Doença de Chagas', 'Doença de Chagas',
    'Doenças Infecciosas', 'Doença de Chagas', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B57');

-- B58 - Toxoplasmose
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B58', 'Toxoplasmose', 'Toxoplasmose',
    'Doenças Infecciosas', 'Toxoplasmose', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B58');

-- B59 - Pneumocistose
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B59', 'Pneumocistose', 'Pneumocistose',
    'Doenças Infecciosas', 'Pneumocistose', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B59');

-- B60 - Outras doenças devidas a protozoários não classificadas em outra parte
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B60', 'Outras doenças devidas a protozoários não classificadas em outra parte', 'Doença por protozoário',
    'Doenças Infecciosas', 'Protozoários', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B60');

-- B64 - Doença por protozoários não especificada
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B64', 'Doença por protozoários não especificada', 'Doença por protozoário',
    'Doenças Infecciosas', 'Protozoários', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B64');

-- B65 - Esquistossomose [bilharziose]
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B65', 'Esquistossomose [bilharziose]', 'Esquistossomose',
    'Doenças Infecciosas', 'Esquistossomose', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B65');

-- B66 - Outras infecções por trematódeos
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B66', 'Outras infecções por trematódeos', 'Infecção por trematódeo',
    'Doenças Infecciosas', 'Trematódeos', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B66');

-- B67 - Equinococose
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B67', 'Equinococose', 'Equinococose',
    'Doenças Infecciosas', 'Equinococose', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B67');

-- B68 - Taeniose
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B68', 'Taeniose', 'Taeniose',
    'Doenças Infecciosas', 'Taeniose', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B68');

-- B69 - Cisticercose
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B69', 'Cisticercose', 'Cisticercose',
    'Doenças Infecciosas', 'Cisticercose', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B69');

-- B70 - Difilobotríase e esparganose
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B70', 'Difilobotríase e esparganose', 'Difilobotríase',
    'Doenças Infecciosas', 'Cestódeos', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B70');

-- B71 - Outras infecções por cestódeos
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B71', 'Outras infecções por cestódeos', 'Infecção por cestódeo',
    'Doenças Infecciosas', 'Cestódeos', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B71');

-- B72 - Dracontíase
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B72', 'Dracontíase', 'Dracontíase',
    'Doenças Infecciosas', 'Dracontíase', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B72');

-- B73 - Oncocercose
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B73', 'Oncocercose', 'Oncocercose',
    'Doenças Infecciosas', 'Oncocercose', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B73');

-- B74 - Filariose
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B74', 'Filariose', 'Filariose',
    'Doenças Infecciosas', 'Filariose', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B74');

-- B75 - Triquinelose
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B75', 'Triquinelose', 'Triquinelose',
    'Doenças Infecciosas', 'Triquinelose', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B75');

-- B76 - Ancilostomíase
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B76', 'Ancilostomíase', 'Ancilostomíase',
    'Doenças Infecciosas', 'Ancilostomíase', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B76');

-- B77 - Ascaridíase
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B77', 'Ascaridíase', 'Ascaridíase',
    'Doenças Infecciosas', 'Ascaridíase', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B77');

-- B78 - Estrongiloidíase
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B78', 'Estrongiloidíase', 'Estrongiloidíase',
    'Doenças Infecciosas', 'Estrongiloidíase', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B78');

-- B79 - Tricuríase
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B79', 'Tricuríase', 'Tricuríase',
    'Doenças Infecciosas', 'Tricuríase', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B79');

-- B80 - Enterobíase
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B80', 'Enterobíase', 'Enterobíase',
    'Doenças Infecciosas', 'Enterobíase', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B80');

-- B81 - Outras helmintíases intestinais não classificadas em outra parte
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B81', 'Outras helmintíases intestinais não classificadas em outra parte', 'Helmintíase intestinal',
    'Doenças Infecciosas', 'Helmintíases', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B81');

-- B82 - Parasitose intestinal não especificada
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B82', 'Parasitose intestinal não especificada', 'Parasitose intestinal',
    'Doenças Infecciosas', 'Parasitoses', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B82');

-- B83 - Outras helmintíases
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B83', 'Outras helmintíases', 'Helmintíase',
    'Doenças Infecciosas', 'Helmintíases', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B83');

-- B85 - Pediculose e ftiríase
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B85', 'Pediculose e ftiríase', 'Pediculose',
    'Doenças Infecciosas', 'Pediculose', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B85');

-- B86 - Sarna [escabiose]
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B86', 'Sarna [escabiose]', 'Sarna',
    'Doenças Infecciosas', 'Sarna', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B86');

-- B87 - Miíase
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B87', 'Miíase', 'Miíase',
    'Doenças Infecciosas', 'Miíase', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B87');

-- B88 - Outras infestações
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B88', 'Outras infestações', 'Infestação',
    'Doenças Infecciosas', 'Infestações', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B88');

-- B89 - Doença parasitária não especificada
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B89', 'Doença parasitária não especificada', 'Doença parasitária',
    'Doenças Infecciosas', 'Parasitárias', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B89');

-- B90 - Seqüelas de tuberculose
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B90', 'Seqüelas de tuberculose', 'Seqüelas de tuberculose',
    'Doenças Infecciosas', 'Seqüelas', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B90');

-- B91 - Seqüelas de poliomielite
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B91', 'Seqüelas de poliomielite', 'Seqüelas de poliomielite',
    'Doenças Infecciosas', 'Seqüelas', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B91');

-- B92 - Seqüelas de hanseníase [lepra]
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B92', 'Seqüelas de hanseníase [lepra]', 'Seqüelas de hanseníase',
    'Doenças Infecciosas', 'Seqüelas', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B92');

-- B94 - Seqüelas de outras doenças infecciosas e parasitárias e das não especificadas
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B94', 'Seqüelas de outras doenças infecciosas e parasitárias e das não especificadas', 'Seqüelas de doenças infecciosas',
    'Doenças Infecciosas', 'Seqüelas', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B94');

-- B95 - Estreptococos e estafilococos como causa de doenças classificadas em outros capítulos
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B95', 'Estreptococos e estafilococos como causa de doenças classificadas em outros capítulos', 'Infecção por estreptococo/estafilococo',
    'Doenças Infecciosas', 'Bacterianas', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B95');

-- B96 - Outros agentes bacterianos como causa de doenças classificadas em outros capítulos
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B96', 'Outros agentes bacterianos como causa de doenças classificadas em outros capítulos', 'Infecção bacteriana',
    'Doenças Infecciosas', 'Bacterianas', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B96');

-- B97 - Agentes virais como causa de doenças classificadas em outros capítulos
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B97', 'Agentes virais como causa de doenças classificadas em outros capítulos', 'Infecção viral',
    'Doenças Infecciosas', 'Virais', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B97');

-- B99 - Outras doenças infecciosas e parasitárias
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B99', 'Outras doenças infecciosas e parasitárias', 'Doença infecciosa',
    'Doenças Infecciosas', 'Infecciosas', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'B99');

-- C00 - Neoplasia maligna do lábio
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C00', 'Neoplasia maligna do lábio', 'Câncer de lábio',
    'Neoplasias', 'Câncer de Lábio', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C00');

-- C01 - Neoplasia maligna da base da língua
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C01', 'Neoplasia maligna da base da língua', 'Câncer de base da língua',
    'Neoplasias', 'Câncer de Língua', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C01');

-- C02 - Neoplasia maligna de outras partes e de partes não especificadas da língua
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C02', 'Neoplasia maligna de outras partes e de partes não especificadas da língua', 'Câncer de língua',
    'Neoplasias', 'Câncer de Língua', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C02');

-- C03 - Neoplasia maligna da gengiva
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C03', 'Neoplasia maligna da gengiva', 'Câncer de gengiva',
    'Neoplasias', 'Câncer de Boca', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C03');

-- C04 - Neoplasia maligna do assoalho da boca
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C04', 'Neoplasia maligna do assoalho da boca', 'Câncer de assoalho da boca',
    'Neoplasias', 'Câncer de Boca', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C04');

-- C05 - Neoplasia maligna do palato
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C05', 'Neoplasia maligna do palato', 'Câncer de palato',
    'Neoplasias', 'Câncer de Boca', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C05');

-- C06 - Neoplasia maligna de outras partes e de partes não especificadas da boca
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C06', 'Neoplasia maligna de outras partes e de partes não especificadas da boca', 'Câncer de boca',
    'Neoplasias', 'Câncer de Boca', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C06');

-- C07 - Neoplasia maligna da glândula parótida
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C07', 'Neoplasia maligna da glândula parótida', 'Câncer de parótida',
    'Neoplasias', 'Câncer de Glândulas Salivares', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C07');

-- C08 - Neoplasia maligna de outras glândulas salivares maiores e das não especificadas
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C08', 'Neoplasia maligna de outras glândulas salivares maiores e das não especificadas', 'Câncer de glândula salivar',
    'Neoplasias', 'Câncer de Glândulas Salivares', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C08');

-- C09 - Neoplasia maligna da amígdala
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C09', 'Neoplasia maligna da amígdala', 'Câncer de amígdala',
    'Neoplasias', 'Câncer de Amígdala', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C09');

-- C10 - Neoplasia maligna da orofaringe
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C10', 'Neoplasia maligna da orofaringe', 'Câncer de orofaringe',
    'Neoplasias', 'Câncer de Faringe', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C10');

-- C11 - Neoplasia maligna da nasofaringe
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C11', 'Neoplasia maligna da nasofaringe', 'Câncer de nasofaringe',
    'Neoplasias', 'Câncer de Faringe', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C11');

-- C12 - Neoplasia maligna do seio piriforme
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C12', 'Neoplasia maligna do seio piriforme', 'Câncer de seio piriforme',
    'Neoplasias', 'Câncer de Faringe', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C12');

-- C13 - Neoplasia maligna da hipofaringe
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C13', 'Neoplasia maligna da hipofaringe', 'Câncer de hipofaringe',
    'Neoplasias', 'Câncer de Faringe', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C13');

-- C14 - Neoplasia maligna de outras localizações e de localizações mal definidas do lábio, cavidade oral e faringe
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C14', 'Neoplasia maligna de outras localizações e de localizações mal definidas do lábio, cavidade oral e faringe', 'Câncer de boca/faringe',
    'Neoplasias', 'Câncer de Boca', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C14');

-- C15 - Neoplasia maligna do esôfago
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C15', 'Neoplasia maligna do esôfago', 'Câncer de esôfago',
    'Neoplasias', 'Câncer de Esôfago', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C15');

-- C16 - Neoplasia maligna do estômago
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C16', 'Neoplasia maligna do estômago', 'Câncer de estômago',
    'Neoplasias', 'Câncer de Estômago', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C16');

-- C17 - Neoplasia maligna do intestino delgado
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C17', 'Neoplasia maligna do intestino delgado', 'Câncer de intestino delgado',
    'Neoplasias', 'Câncer de Intestino', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C17');

-- C18 - Neoplasia maligna do cólon
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C18', 'Neoplasia maligna do cólon', 'Câncer de cólon',
    'Neoplasias', 'Câncer de Intestino', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C18');

-- C19 - Neoplasia maligna da junção retossigmoide
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C19', 'Neoplasia maligna da junção retossigmoide', 'Câncer de junção retossigmoide',
    'Neoplasias', 'Câncer de Intestino', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C19');

-- C20 - Neoplasia maligna do reto
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C20', 'Neoplasia maligna do reto', 'Câncer de reto',
    'Neoplasias', 'Câncer de Intestino', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C20');

-- C21 - Neoplasia maligna do ânus e do canal anal
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C21', 'Neoplasia maligna do ânus e do canal anal', 'Câncer de ânus',
    'Neoplasias', 'Câncer de Intestino', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C21');

-- C22 - Neoplasia maligna do fígado e das vias biliares intra-hepáticas
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C22', 'Neoplasia maligna do fígado e das vias biliares intra-hepáticas', 'Câncer de fígado',
    'Neoplasias', 'Câncer de Fígado', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C22');

-- C23 - Neoplasia maligna da vesícula biliar
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C23', 'Neoplasia maligna da vesícula biliar', 'Câncer de vesícula biliar',
    'Neoplasias', 'Câncer de Vesícula Biliar', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C23');

-- C24 - Neoplasia maligna de outras partes e de partes não especificadas das vias biliares
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C24', 'Neoplasia maligna de outras partes e de partes não especificadas das vias biliares', 'Câncer de vias biliares',
    'Neoplasias', 'Câncer de Vias Biliares', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C24');

-- C25 - Neoplasia maligna do pâncreas
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C25', 'Neoplasia maligna do pâncreas', 'Câncer de pâncreas',
    'Neoplasias', 'Câncer de Pâncreas', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C25');

-- C26 - Neoplasia maligna de outras localizações e de localizações mal definidas do aparelho digestivo
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C26', 'Neoplasia maligna de outras localizações e de localizações mal definidas do aparelho digestivo', 'Câncer de aparelho digestivo',
    'Neoplasias', 'Câncer de Aparelho Digestivo', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C26');

-- C30 - Neoplasia maligna da cavidade nasal e dos seios paranasais
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C30', 'Neoplasia maligna da cavidade nasal e dos seios paranasais', 'Câncer de cavidade nasal',
    'Neoplasias', 'Câncer de Nariz', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C30');

-- C31 - Neoplasia maligna da laringe
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C31', 'Neoplasia maligna da laringe', 'Câncer de laringe',
    'Neoplasias', 'Câncer de Laringe', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C31');

-- C32 - Neoplasia maligna da traqueia
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C32', 'Neoplasia maligna da traqueia', 'Câncer de traqueia',
    'Neoplasias', 'Câncer de Traqueia', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C32');

-- C33 - Neoplasia maligna dos brônquios e dos pulmões
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C33', 'Neoplasia maligna dos brônquios e dos pulmões', 'Câncer de pulmão',
    'Neoplasias', 'Câncer de Pulmão', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C33');

-- C34 - Neoplasia maligna dos brônquios e dos pulmões
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C34', 'Neoplasia maligna dos brônquios e dos pulmões', 'Câncer de pulmão',
    'Neoplasias', 'Câncer de Pulmão', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C34');

-- C37 - Neoplasia maligna do timo
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C37', 'Neoplasia maligna do timo', 'Câncer de timo',
    'Neoplasias', 'Câncer de Timo', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C37');

-- C38 - Neoplasia maligna do coração, mediastino e pleura
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C38', 'Neoplasia maligna do coração, mediastino e pleura', 'Câncer de coração/mediastino',
    'Neoplasias', 'Câncer de Coração', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C38');

-- C39 - Neoplasia maligna de outras localizações e de localizações mal definidas do aparelho respiratório e dos órgãos intratorácicos
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C39', 'Neoplasia maligna de outras localizações e de localizações mal definidas do aparelho respiratório e dos órgãos intratorácicos', 'Câncer de aparelho respiratório',
    'Neoplasias', 'Câncer de Aparelho Respiratório', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C39');

-- C40 - Neoplasia maligna dos ossos e cartilagens articulares dos membros
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C40', 'Neoplasia maligna dos ossos e cartilagens articulares dos membros', 'Câncer de osso',
    'Neoplasias', 'Câncer de Osso', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C40');

-- C41 - Neoplasia maligna dos ossos e cartilagens articulares de outras localizações e de localizações não especificadas
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C41', 'Neoplasia maligna dos ossos e cartilagens articulares de outras localizações e de localizações não especificadas', 'Câncer de osso',
    'Neoplasias', 'Câncer de Osso', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C41');

-- C43 - Melanoma maligno da pele
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C43', 'Melanoma maligno da pele', 'Melanoma',
    'Neoplasias', 'Câncer de Pele', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C43');

-- C44 - Outras neoplasias malignas da pele
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C44', 'Outras neoplasias malignas da pele', 'Câncer de pele',
    'Neoplasias', 'Câncer de Pele', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C44');

-- C45 - Mesotelioma
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C45', 'Mesotelioma', 'Mesotelioma',
    'Neoplasias', 'Mesotelioma', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C45');

-- C46 - Sarcoma de Kaposi
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C46', 'Sarcoma de Kaposi', 'Sarcoma de Kaposi',
    'Neoplasias', 'Sarcoma de Kaposi', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C46');

-- C47 - Neoplasia maligna dos nervos periféricos e do sistema nervoso autônomo
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C47', 'Neoplasia maligna dos nervos periféricos e do sistema nervoso autônomo', 'Câncer de nervo periférico',
    'Neoplasias', 'Câncer de Nervo', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C47');

-- C48 - Neoplasia maligna do peritônio e do retroperitônio
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C48', 'Neoplasia maligna do peritônio e do retroperitônio', 'Câncer de peritônio',
    'Neoplasias', 'Câncer de Peritônio', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C48');

-- C49 - Neoplasia maligna de outros tecidos conjuntivos e dos tecidos moles
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C49', 'Neoplasia maligna de outros tecidos conjuntivos e dos tecidos moles', 'Câncer de tecido mole',
    'Neoplasias', 'Câncer de Tecido Mole', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C49');

-- C50 - Neoplasia maligna da mama
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C50', 'Neoplasia maligna da mama', 'Câncer de mama',
    'Neoplasias', 'Câncer de Mama', 'F', NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C50');

-- C51 - Neoplasia maligna da vulva
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C51', 'Neoplasia maligna da vulva', 'Câncer de vulva',
    'Neoplasias', 'Câncer de Vulva', 'F', NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C51');

-- C52 - Neoplasia maligna da vagina
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C52', 'Neoplasia maligna da vagina', 'Câncer de vagina',
    'Neoplasias', 'Câncer de Vagina', 'F', NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C52');

-- C53 - Neoplasia maligna do colo do útero
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C53', 'Neoplasia maligna do colo do útero', 'Câncer de colo do útero',
    'Neoplasias', 'Câncer de Colo do Útero', 'F', NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C53');

-- C54 - Neoplasia maligna do corpo do útero
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C54', 'Neoplasia maligna do corpo do útero', 'Câncer de corpo do útero',
    'Neoplasias', 'Câncer de Útero', 'F', NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C54');

-- C55 - Neoplasia maligna do útero, porção não especificada
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C55', 'Neoplasia maligna do útero, porção não especificada', 'Câncer de útero',
    'Neoplasias', 'Câncer de Útero', 'F', NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C55');

-- C56 - Neoplasia maligna do ovário
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C56', 'Neoplasia maligna do ovário', 'Câncer de ovário',
    'Neoplasias', 'Câncer de Ovário', 'F', NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C56');

-- C57 - Neoplasia maligna de outros órgãos genitais femininos e dos não especificados
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C57', 'Neoplasia maligna de outros órgãos genitais femininos e dos não especificados', 'Câncer de órgão genital feminino',
    'Neoplasias', 'Câncer de Órgão Genital Feminino', 'F', NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C57');

-- C58 - Neoplasia maligna da placenta
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C58', 'Neoplasia maligna da placenta', 'Câncer de placenta',
    'Neoplasias', 'Câncer de Placenta', 'F', NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C58');

-- C60 - Neoplasia maligna do pênis
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C60', 'Neoplasia maligna do pênis', 'Câncer de pênis',
    'Neoplasias', 'Câncer de Pênis', 'M', NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C60');

-- C61 - Neoplasia maligna da próstata
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C61', 'Neoplasia maligna da próstata', 'Câncer de próstata',
    'Neoplasias', 'Câncer de Próstata', 'M', NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C61');

-- C62 - Neoplasia maligna do testículo
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C62', 'Neoplasia maligna do testículo', 'Câncer de testículo',
    'Neoplasias', 'Câncer de Testículo', 'M', NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C62');

-- C63 - Neoplasia maligna de outros órgãos genitais masculinos e dos não especificados
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C63', 'Neoplasia maligna de outros órgãos genitais masculinos e dos não especificados', 'Câncer de órgão genital masculino',
    'Neoplasias', 'Câncer de Órgão Genital Masculino', 'M', NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C63');

-- C64 - Neoplasia maligna do rim, exceto pelve renal
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C64', 'Neoplasia maligna do rim, exceto pelve renal', 'Câncer de rim',
    'Neoplasias', 'Câncer de Rim', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C64');

-- C65 - Neoplasia maligna da pelve renal
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C65', 'Neoplasia maligna da pelve renal', 'Câncer de pelve renal',
    'Neoplasias', 'Câncer de Rim', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C65');

-- C66 - Neoplasia maligna do ureter
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C66', 'Neoplasia maligna do ureter', 'Câncer de ureter',
    'Neoplasias', 'Câncer de Ureter', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C66');

-- C67 - Neoplasia maligna da bexiga
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C67', 'Neoplasia maligna da bexiga', 'Câncer de bexiga',
    'Neoplasias', 'Câncer de Bexiga', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C67');

-- C68 - Neoplasia maligna de outros órgãos urinários e dos não especificados
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C68', 'Neoplasia maligna de outros órgãos urinários e dos não especificados', 'Câncer de órgão urinário',
    'Neoplasias', 'Câncer de Órgão Urinário', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C68');

-- C69 - Neoplasia maligna do olho e anexos
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C69', 'Neoplasia maligna do olho e anexos', 'Câncer de olho',
    'Neoplasias', 'Câncer de Olho', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C69');

-- C70 - Neoplasia maligna das meninges
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C70', 'Neoplasia maligna das meninges', 'Câncer de meninge',
    'Neoplasias', 'Câncer de Meninge', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C70');

-- C71 - Neoplasia maligna do encéfalo
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C71', 'Neoplasia maligna do encéfalo', 'Câncer de encéfalo',
    'Neoplasias', 'Câncer de Encéfalo', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C71');

-- C72 - Neoplasia maligna da medula espinhal, dos nervos cranianos e de outras partes do sistema nervoso central
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C72', 'Neoplasia maligna da medula espinhal, dos nervos cranianos e de outras partes do sistema nervoso central', 'Câncer de SNC',
    'Neoplasias', 'Câncer de SNC', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C72');

-- C73 - Neoplasia maligna da glândula tireóide
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C73', 'Neoplasia maligna da glândula tireóide', 'Câncer de tireóide',
    'Neoplasias', 'Câncer de Tireóide', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C73');

-- C74 - Neoplasia maligna da glândula supra-renal
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C74', 'Neoplasia maligna da glândula supra-renal', 'Câncer de supra-renal',
    'Neoplasias', 'Câncer de Supra-renal', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C74');

-- C75 - Neoplasia maligna de outras glândulas endócrinas e de estruturas relacionadas
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C75', 'Neoplasia maligna de outras glândulas endócrinas e de estruturas relacionadas', 'Câncer de glândula endócrina',
    'Neoplasias', 'Câncer de Glândula Endócrina', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C75');

-- C76 - Neoplasia maligna de outras localizações e de localizações mal definidas
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C76', 'Neoplasia maligna de outras localizações e de localizações mal definidas', 'Câncer de localização não especificada',
    'Neoplasias', 'Câncer', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C76');

-- C77 - Neoplasia maligna secundária e não especificada de linfonodos
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C77', 'Neoplasia maligna secundária e não especificada de linfonodos', 'Metástase em linfonodo',
    'Neoplasias', 'Metástase', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C77');

-- C78 - Neoplasia maligna secundária de órgãos respiratórios e digestivos
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C78', 'Neoplasia maligna secundária de órgãos respiratórios e digestivos', 'Metástase em órgão respiratório/digestivo',
    'Neoplasias', 'Metástase', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C78');

-- C79 - Neoplasia maligna secundária de outras localizações
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C79', 'Neoplasia maligna secundária de outras localizações', 'Metástase',
    'Neoplasias', 'Metástase', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C79');

-- C80 - Neoplasia maligna sem especificação de localização
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C80', 'Neoplasia maligna sem especificação de localização', 'Câncer não especificado',
    'Neoplasias', 'Câncer', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C80');

-- C81 - Doença de Hodgkin
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C81', 'Doença de Hodgkin', 'Doença de Hodgkin',
    'Neoplasias', 'Linfoma', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C81');

-- C82 - Linfoma não-Hodgkin folicular [nodular]
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C82', 'Linfoma não-Hodgkin folicular [nodular]', 'Linfoma não-Hodgkin folicular',
    'Neoplasias', 'Linfoma', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C82');

-- C83 - Linfoma não-Hodgkin difuso
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C83', 'Linfoma não-Hodgkin difuso', 'Linfoma não-Hodgkin difuso',
    'Neoplasias', 'Linfoma', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C83');

-- C84 - Linfoma de células T periférico e cutâneo
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C84', 'Linfoma de células T periférico e cutâneo', 'Linfoma de células T',
    'Neoplasias', 'Linfoma', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C84');

-- C85 - Outros linfomas não-Hodgkin e os não especificados
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C85', 'Outros linfomas não-Hodgkin e os não especificados', 'Linfoma não-Hodgkin',
    'Neoplasias', 'Linfoma', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C85');

-- C88 - Doenças imunoproliferativas malignas
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C88', 'Doenças imunoproliferativas malignas', 'Doença imunoproliferativa',
    'Neoplasias', 'Doença Imunoproliferativa', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C88');

-- C90 - Mieloma múltiplo e neoplasias malignas de plasmócitos
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C90', 'Mieloma múltiplo e neoplasias malignas de plasmócitos', 'Mieloma múltiplo',
    'Neoplasias', 'Mieloma', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C90');

-- C91 - Leucemia linfoide
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C91', 'Leucemia linfoide', 'Leucemia linfoide',
    'Neoplasias', 'Leucemia', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C91');

-- C92 - Leucemia mieloide
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C92', 'Leucemia mieloide', 'Leucemia mieloide',
    'Neoplasias', 'Leucemia', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C92');

-- C93 - Leucemia monocítica
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C93', 'Leucemia monocítica', 'Leucemia monocítica',
    'Neoplasias', 'Leucemia', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C93');

-- C94 - Outras leucemias de tipo celular especificado
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C94', 'Outras leucemias de tipo celular especificado', 'Leucemia',
    'Neoplasias', 'Leucemia', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C94');

-- C95 - Leucemia de tipo celular não especificado
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C95', 'Leucemia de tipo celular não especificado', 'Leucemia',
    'Neoplasias', 'Leucemia', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C95');

-- C96 - Outras neoplasias malignas e as não especificadas dos tecidos linfático, hematopoético e tecidos correlatos
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C96', 'Outras neoplasias malignas e as não especificadas dos tecidos linfático, hematopoético e tecidos correlatos', 'Neoplasia maligna hematológica',
    'Neoplasias', 'Neoplasia Hematológica', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C96');

-- C97 - Neoplasias malignas de localizações múltiplas independentes (primárias)
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'C97', 'Neoplasias malignas de localizações múltiplas independentes (primárias)', 'Neoplasia múltipla',
    'Neoplasias', 'Neoplasia Múltipla', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'C97');

-- D00 - Carcinoma in situ da cavidade oral, esôfago e estômago
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D00', 'Carcinoma in situ da cavidade oral, esôfago e estômago', 'Carcinoma in situ',
    'Neoplasias', 'Carcinoma in situ', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D00');

-- D01 - Carcinoma in situ de outros órgãos digestivos e dos não especificados
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D01', 'Carcinoma in situ de outros órgãos digestivos e dos não especificados', 'Carcinoma in situ digestivo',
    'Neoplasias', 'Carcinoma in situ', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D01');

-- D02 - Carcinoma in situ do ouvido médio e do aparelho respiratório
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D02', 'Carcinoma in situ do ouvido médio e do aparelho respiratório', 'Carcinoma in situ respiratório',
    'Neoplasias', 'Carcinoma in situ', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D02');

-- D03 - Melanoma in situ
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D03', 'Melanoma in situ', 'Melanoma in situ',
    'Neoplasias', 'Melanoma in situ', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D03');

-- D04 - Carcinoma in situ da pele
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D04', 'Carcinoma in situ da pele', 'Carcinoma in situ de pele',
    'Neoplasias', 'Carcinoma in situ', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D04');

-- D05 - Carcinoma in situ da mama
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D05', 'Carcinoma in situ da mama', 'Carcinoma in situ de mama',
    'Neoplasias', 'Carcinoma in situ', 'F', NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D05');

-- D06 - Carcinoma in situ do colo do útero
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D06', 'Carcinoma in situ do colo do útero', 'Carcinoma in situ de colo do útero',
    'Neoplasias', 'Carcinoma in situ', 'F', NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D06');

-- D07 - Carcinoma in situ de outros órgãos genitais e dos não especificados
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D07', 'Carcinoma in situ de outros órgãos genitais e dos não especificados', 'Carcinoma in situ genital',
    'Neoplasias', 'Carcinoma in situ', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D07');

-- D09 - Carcinoma in situ de outras localizações e das não especificadas
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D09', 'Carcinoma in situ de outras localizações e das não especificadas', 'Carcinoma in situ',
    'Neoplasias', 'Carcinoma in situ', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D09');

-- D10 - Neoplasia benigna da boca e da faringe
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D10', 'Neoplasia benigna da boca e da faringe', 'Neoplasia benigna boca/faringe',
    'Neoplasias', 'Neoplasia Benigna', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D10');

-- D11 - Neoplasia benigna das glândulas salivares maiores
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D11', 'Neoplasia benigna das glândulas salivares maiores', 'Neoplasia benigna glândula salivar',
    'Neoplasias', 'Neoplasia Benigna', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D11');

-- D12 - Neoplasia benigna do cólon, reto, canal anal e ânus
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D12', 'Neoplasia benigna do cólon, reto, canal anal e ânus', 'Neoplasia benigna intestino',
    'Neoplasias', 'Neoplasia Benigna', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D12');

-- D13 - Neoplasia benigna de outras partes e de partes mal definidas do aparelho digestivo
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D13', 'Neoplasia benigna de outras partes e de partes mal definidas do aparelho digestivo', 'Neoplasia benigna digestivo',
    'Neoplasias', 'Neoplasia Benigna', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D13');

-- D14 - Neoplasia benigna do ouvido médio e do aparelho respiratório
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D14', 'Neoplasia benigna do ouvido médio e do aparelho respiratório', 'Neoplasia benigna respiratório',
    'Neoplasias', 'Neoplasia Benigna', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D14');

-- D15 - Neoplasia benigna de outros órgãos intratorácicos e dos não especificados
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D15', 'Neoplasia benigna de outros órgãos intratorácicos e dos não especificados', 'Neoplasia benigna intratorácica',
    'Neoplasias', 'Neoplasia Benigna', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D15');

-- D16 - Neoplasia benigna do osso e cartilagem articular
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D16', 'Neoplasia benigna do osso e cartilagem articular', 'Neoplasia benigna de osso',
    'Neoplasias', 'Neoplasia Benigna', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D16');

-- D17 - Neoplasia lipomatosa benigna
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D17', 'Neoplasia lipomatosa benigna', 'Lipoma',
    'Neoplasias', 'Neoplasia Benigna', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D17');

-- D18 - Hemangioma e linfangioma de qualquer localização
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D18', 'Hemangioma e linfangioma de qualquer localização', 'Hemangioma',
    'Neoplasias', 'Neoplasia Benigna', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D18');

-- D19 - Neoplasia benigna do tecido mesotelial
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D19', 'Neoplasia benigna do tecido mesotelial', 'Neoplasia benigna mesotelial',
    'Neoplasias', 'Neoplasia Benigna', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D19');

-- D20 - Neoplasia benigna do tecido mole do retroperitônio e do peritônio
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D20', 'Neoplasia benigna do tecido mole do retroperitônio e do peritônio', 'Neoplasia benigna retroperitônio',
    'Neoplasias', 'Neoplasia Benigna', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D20');

-- D21 - Outras neoplasias benignas do tecido conjuntivo e dos tecidos moles
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D21', 'Outras neoplasias benignas do tecido conjuntivo e dos tecidos moles', 'Neoplasia benigna tecido mole',
    'Neoplasias', 'Neoplasia Benigna', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D21');

-- D22 - Nevo melanocítico
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D22', 'Nevo melanocítico', 'Nevo',
    'Neoplasias', 'Neoplasia Benigna', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D22');

-- D23 - Outras neoplasias benignas da pele
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D23', 'Outras neoplasias benignas da pele', 'Neoplasia benigna de pele',
    'Neoplasias', 'Neoplasia Benigna', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D23');

-- D24 - Neoplasia benigna da mama
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D24', 'Neoplasia benigna da mama', 'Neoplasia benigna de mama',
    'Neoplasias', 'Neoplasia Benigna', 'F', NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D24');

-- D25 - Leiomioma do útero
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D25', 'Leiomioma do útero', 'Leiomioma uterino',
    'Neoplasias', 'Neoplasia Benigna', 'F', NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D25');

-- D26 - Outras neoplasias benignas do útero
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D26', 'Outras neoplasias benignas do útero', 'Neoplasia benigna de útero',
    'Neoplasias', 'Neoplasia Benigna', 'F', NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D26');

-- D27 - Neoplasia benigna do ovário
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D27', 'Neoplasia benigna do ovário', 'Neoplasia benigna de ovário',
    'Neoplasias', 'Neoplasia Benigna', 'F', NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D27');

-- D28 - Neoplasia benigna de outros órgãos genitais femininos e dos não especificados
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D28', 'Neoplasia benigna de outros órgãos genitais femininos e dos não especificados', 'Neoplasia benigna genital feminino',
    'Neoplasias', 'Neoplasia Benigna', 'F', NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D28');

-- D29 - Neoplasia benigna dos órgãos genitais masculinos
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D29', 'Neoplasia benigna dos órgãos genitais masculinos', 'Neoplasia benigna genital masculino',
    'Neoplasias', 'Neoplasia Benigna', 'M', NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D29');

-- D30 - Neoplasia benigna dos órgãos urinários
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D30', 'Neoplasia benigna dos órgãos urinários', 'Neoplasia benigna urinária',
    'Neoplasias', 'Neoplasia Benigna', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D30');

-- D31 - Neoplasia benigna do olho e anexos
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D31', 'Neoplasia benigna do olho e anexos', 'Neoplasia benigna de olho',
    'Neoplasias', 'Neoplasia Benigna', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D31');

-- D32 - Neoplasia benigna das meninges
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D32', 'Neoplasia benigna das meninges', 'Neoplasia benigna de meninge',
    'Neoplasias', 'Neoplasia Benigna', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D32');

-- D33 - Neoplasia benigna do encéfalo e de outras partes do sistema nervoso central
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D33', 'Neoplasia benigna do encéfalo e de outras partes do sistema nervoso central', 'Neoplasia benigna de SNC',
    'Neoplasias', 'Neoplasia Benigna', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D33');

-- D34 - Neoplasia benigna da glândula tireóide
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D34', 'Neoplasia benigna da glândula tireóide', 'Neoplasia benigna de tireóide',
    'Neoplasias', 'Neoplasia Benigna', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D34');

-- D35 - Neoplasia benigna de outras glândulas endócrinas e das não especificadas
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D35', 'Neoplasia benigna de outras glândulas endócrinas e das não especificadas', 'Neoplasia benigna endócrina',
    'Neoplasias', 'Neoplasia Benigna', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D35');

-- D36 - Neoplasia benigna de outras localizações e de localizações não especificadas
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D36', 'Neoplasia benigna de outras localizações e de localizações não especificadas', 'Neoplasia benigna',
    'Neoplasias', 'Neoplasia Benigna', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D36');

-- D37 - Neoplasia de comportamento incerto ou desconhecido da cavidade oral e dos órgãos digestivos
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D37', 'Neoplasia de comportamento incerto ou desconhecido da cavidade oral e dos órgãos digestivos', 'Neoplasia incerta digestivo',
    'Neoplasias', 'Neoplasia Incerta', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D37');

-- D38 - Neoplasia de comportamento incerto ou desconhecido do ouvido médio e dos órgãos respiratórios e intratorácicos
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D38', 'Neoplasia de comportamento incerto ou desconhecido do ouvido médio e dos órgãos respiratórios e intratorácicos', 'Neoplasia incerta respiratório',
    'Neoplasias', 'Neoplasia Incerta', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D38');

-- D39 - Neoplasia de comportamento incerto ou desconhecido dos órgãos genitais femininos
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D39', 'Neoplasia de comportamento incerto ou desconhecido dos órgãos genitais femininos', 'Neoplasia incerta genital feminino',
    'Neoplasias', 'Neoplasia Incerta', 'F', NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D39');

-- D40 - Neoplasia de comportamento incerto ou desconhecido dos órgãos genitais masculinos
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D40', 'Neoplasia de comportamento incerto ou desconhecido dos órgãos genitais masculinos', 'Neoplasia incerta genital masculino',
    'Neoplasias', 'Neoplasia Incerta', 'M', NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D40');

-- D41 - Neoplasia de comportamento incerto ou desconhecido dos órgãos urinários
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D41', 'Neoplasia de comportamento incerto ou desconhecido dos órgãos urinários', 'Neoplasia incerta urinária',
    'Neoplasias', 'Neoplasia Incerta', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D41');

-- D42 - Neoplasia de comportamento incerto ou desconhecido das meninges
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D42', 'Neoplasia de comportamento incerto ou desconhecido das meninges', 'Neoplasia incerta de meninge',
    'Neoplasias', 'Neoplasia Incerta', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D42');

-- D43 - Neoplasia de comportamento incerto ou desconhecido do encéfalo e do sistema nervoso central
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D43', 'Neoplasia de comportamento incerto ou desconhecido do encéfalo e do sistema nervoso central', 'Neoplasia incerta de SNC',
    'Neoplasias', 'Neoplasia Incerta', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D43');

-- D44 - Neoplasia de comportamento incerto ou desconhecido das glândulas endócrinas
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D44', 'Neoplasia de comportamento incerto ou desconhecido das glândulas endócrinas', 'Neoplasia incerta endócrina',
    'Neoplasias', 'Neoplasia Incerta', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D44');

-- D45 - Policitemia vera
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D45', 'Policitemia vera', 'Policitemia vera',
    'Neoplasias', 'Neoplasia Hematológica', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D45');

-- D46 - Síndromes mielodisplásicas
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D46', 'Síndromes mielodisplásicas', 'Síndrome mielodisplásica',
    'Neoplasias', 'Neoplasia Hematológica', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D46');

-- D47 - Outras neoplasias de comportamento incerto ou desconhecido dos tecidos linfático, hematopoético e tecidos correlatos
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D47', 'Outras neoplasias de comportamento incerto ou desconhecido dos tecidos linfático, hematopoético e tecidos correlatos', 'Neoplasia incerta hematológica',
    'Neoplasias', 'Neoplasia Incerta', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D47');

-- D50 - Anemia por deficiência de ferro
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D50', 'Anemia por deficiência de ferro', 'Anemia ferropriva',
    'Doenças do Sangue', 'Anemia', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D50');

-- D51 - Anemia por deficiência de vitamina B12
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D51', 'Anemia por deficiência de vitamina B12', 'Anemia por B12',
    'Doenças do Sangue', 'Anemia', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D51');

-- D52 - Anemia por deficiência de folato
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D52', 'Anemia por deficiência de folato', 'Anemia por folato',
    'Doenças do Sangue', 'Anemia', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D52');

-- D53 - Outras anemias nutricionais
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D53', 'Outras anemias nutricionais', 'Anemia nutricional',
    'Doenças do Sangue', 'Anemia', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D53');

-- D55 - Anemia devida a transtornos enzimáticos
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D55', 'Anemia devida a transtornos enzimáticos', 'Anemia enzimática',
    'Doenças do Sangue', 'Anemia', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D55');

-- D56 - Talassemia
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D56', 'Talassemia', 'Talassemia',
    'Doenças do Sangue', 'Anemia', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D56');

-- D57 - Transtornos falciformes
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D57', 'Transtornos falciformes', 'Anemia falciforme',
    'Doenças do Sangue', 'Anemia', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D57');

-- D58 - Outras anemias hemolíticas hereditárias
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D58', 'Outras anemias hemolíticas hereditárias', 'Anemia hemolítica hereditária',
    'Doenças do Sangue', 'Anemia', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D58');

-- D59 - Anemia hemolítica adquirida
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D59', 'Anemia hemolítica adquirida', 'Anemia hemolítica adquirida',
    'Doenças do Sangue', 'Anemia', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D59');

-- D60 - Aplasia adquirida da série vermelha [eritroblastopenia]
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D60', 'Aplasia adquirida da série vermelha [eritroblastopenia]', 'Aplasia de série vermelha',
    'Doenças do Sangue', 'Aplasia', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D60');

-- D61 - Outras anemias aplásticas
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D61', 'Outras anemias aplásticas', 'Anemia aplástica',
    'Doenças do Sangue', 'Aplasia', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D61');

-- D62 - Anemia pós-hemorrágica aguda
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D62', 'Anemia pós-hemorrágica aguda', 'Anemia pós-hemorrágica',
    'Doenças do Sangue', 'Anemia', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D62');

-- D63 - Anemia em doenças crônicas classificadas em outra parte
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D63', 'Anemia em doenças crônicas classificadas em outra parte', 'Anemia em doença crônica',
    'Doenças do Sangue', 'Anemia', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D63');

-- D64 - Outras anemias
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D64', 'Outras anemias', 'Anemia',
    'Doenças do Sangue', 'Anemia', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D64');

-- D65 - Coagulação intravascular disseminada [síndrome de desfibrinação]
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D65', 'Coagulação intravascular disseminada [síndrome de desfibrinação]', 'CID',
    'Doenças do Sangue', 'Coagulopatia', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D65');

-- D66 - Deficiência hereditária do fator VIII
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D66', 'Deficiência hereditária do fator VIII', 'Hemofilia A',
    'Doenças do Sangue', 'Hemofilia', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D66');

-- D67 - Deficiência hereditária do fator IX
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D67', 'Deficiência hereditária do fator IX', 'Hemofilia B',
    'Doenças do Sangue', 'Hemofilia', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D67');

-- D68 - Outros defeitos da coagulação
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D68', 'Outros defeitos da coagulação', 'Defeito de coagulação',
    'Doenças do Sangue', 'Coagulopatia', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D68');

-- D69 - Púrpura e outras afecções hemorrágicas
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D69', 'Púrpura e outras afecções hemorrágicas', 'Púrpura',
    'Doenças do Sangue', 'Púrpura', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D69');

-- D70 - Agranulocitose
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D70', 'Agranulocitose', 'Agranulocitose',
    'Doenças do Sangue', 'Agranulocitose', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D70');

-- D71 - Transtornos funcionais dos neutrófilos polimorfonucleares
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D71', 'Transtornos funcionais dos neutrófilos polimorfonucleares', 'Transtorno de neutrófilos',
    'Doenças do Sangue', 'Transtorno de Leucócitos', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D71');

-- D72 - Outros transtornos dos glóbulos brancos
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D72', 'Outros transtornos dos glóbulos brancos', 'Transtorno de leucócitos',
    'Doenças do Sangue', 'Transtorno de Leucócitos', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D72');

-- D73 - Doenças do baço
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D73', 'Doenças do baço', 'Doença do baço',
    'Doenças do Sangue', 'Doença do Baço', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D73');

-- D74 - Metemoglobinemia
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D74', 'Metemoglobinemia', 'Metemoglobinemia',
    'Doenças do Sangue', 'Metemoglobinemia', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D74');

-- D75 - Outras doenças do sangue e dos órgãos hematopoéticos
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D75', 'Outras doenças do sangue e dos órgãos hematopoéticos', 'Doença do sangue',
    'Doenças do Sangue', 'Doença do Sangue', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D75');

-- D76 - Algumas doenças que envolvem o tecido linforreticular e o sistema reticulohistiocitário
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D76', 'Algumas doenças que envolvem o tecido linforreticular e o sistema reticulohistiocitário', 'Doença linforreticular',
    'Doenças do Sangue', 'Doença Linforreticular', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D76');

-- D77 - Outros transtornos do sangue e dos órgãos hematopoéticos em doenças classificadas em outra parte
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D77', 'Outros transtornos do sangue e dos órgãos hematopoéticos em doenças classificadas em outra parte', 'Transtorno do sangue',
    'Doenças do Sangue', 'Transtorno do Sangue', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D77');

-- D80 - Imunodeficiência com predomínio de defeitos de anticorpos
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D80', 'Imunodeficiência com predomínio de defeitos de anticorpos', 'Imunodeficiência de anticorpos',
    'Doenças do Sangue', 'Imunodeficiência', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D80');

-- D81 - Deficiências imunitárias combinadas
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D81', 'Deficiências imunitárias combinadas', 'Imunodeficiência combinada',
    'Doenças do Sangue', 'Imunodeficiência', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D81');

-- D82 - Imunodeficiência associada com outros defeitos "major"
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D82', 'Imunodeficiência associada com outros defeitos "major"', 'Imunodeficiência associada',
    'Doenças do Sangue', 'Imunodeficiência', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D82');

-- D83 - Imunodeficiência comum variável
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D83', 'Imunodeficiência comum variável', 'Imunodeficiência comum variável',
    'Doenças do Sangue', 'Imunodeficiência', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D83');

-- D84 - Outras deficiências imunitárias
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D84', 'Outras deficiências imunitárias', 'Imunodeficiência',
    'Doenças do Sangue', 'Imunodeficiência', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D84');

-- D86 - Sarcoidose
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D86', 'Sarcoidose', 'Sarcoidose',
    'Doenças do Sangue', 'Sarcoidose', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D86');

-- D89 - Outros transtornos que comprometem o mecanismo imunitário, não classificados em outra parte
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'D89', 'Outros transtornos que comprometem o mecanismo imunitário, não classificados em outra parte', 'Transtorno imunitário',
    'Doenças do Sangue', 'Transtorno Imunitário', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'D89');

-- E00 - Síndrome de deficiência congênita de iodo
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E00', 'Síndrome de deficiência congênita de iodo', 'Deficiência congênita de iodo',
    'Doenças Endócrinas', 'Tireóide', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E00');

-- E01 - Transtornos da tireóide relacionados à deficiência de iodo e afecções relacionadas
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E01', 'Transtornos da tireóide relacionados à deficiência de iodo e afecções relacionadas', 'Transtorno tireóide por deficiência de iodo',
    'Doenças Endócrinas', 'Tireóide', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E01');

-- E02 - Hipotireoidismo subclínico por deficiência de iodo
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E02', 'Hipotireoidismo subclínico por deficiência de iodo', 'Hipotireoidismo subclínico',
    'Doenças Endócrinas', 'Tireóide', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E02');

-- E03 - Outros hipotireoidismos
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E03', 'Outros hipotireoidismos', 'Hipotireoidismo',
    'Doenças Endócrinas', 'Tireóide', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E03');

-- E04 - Outros bócios não-tóxicos
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E04', 'Outros bócios não-tóxicos', 'Bócio não-tóxico',
    'Doenças Endócrinas', 'Tireóide', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E04');

-- E05 - Tireotoxicose [hipertireoidismo]
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E05', 'Tireotoxicose [hipertireoidismo]', 'Hipertireoidismo',
    'Doenças Endócrinas', 'Tireóide', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E05');

-- E06 - Tireoidite
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E06', 'Tireoidite', 'Tireoidite',
    'Doenças Endócrinas', 'Tireóide', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E06');

-- E07 - Outros transtornos da tireóide
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E07', 'Outros transtornos da tireóide', 'Transtorno de tireóide',
    'Doenças Endócrinas', 'Tireóide', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E07');

-- E10 - Diabetes mellitus insulino-dependente
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E10', 'Diabetes mellitus insulino-dependente', 'Diabetes tipo 1',
    'Doenças Endócrinas', 'Diabetes', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E10');

-- E11 - Diabetes mellitus não-insulino-dependente
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E11', 'Diabetes mellitus não-insulino-dependente', 'Diabetes tipo 2',
    'Doenças Endócrinas', 'Diabetes', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E11');

-- E11.0 - Diabetes mellitus não-insulino-dependente com coma
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E11.0', 'Diabetes mellitus não-insulino-dependente com coma', 'Diabetes tipo 2 com coma',
    'Doenças Endócrinas', 'Diabetes', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E11.0');

-- E11.1 - Diabetes mellitus não-insulino-dependente com cetoacidose
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E11.1', 'Diabetes mellitus não-insulino-dependente com cetoacidose', 'Diabetes tipo 2 com cetoacidose',
    'Doenças Endócrinas', 'Diabetes', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E11.1');

-- E11.2 - Diabetes mellitus não-insulino-dependente com complicações renais
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E11.2', 'Diabetes mellitus não-insulino-dependente com complicações renais', 'Diabetes tipo 2 com nefropatia',
    'Doenças Endócrinas', 'Diabetes', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E11.2');

-- E11.3 - Diabetes mellitus não-insulino-dependente com complicações oculares
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E11.3', 'Diabetes mellitus não-insulino-dependente com complicações oculares', 'Diabetes tipo 2 com retinopatia',
    'Doenças Endócrinas', 'Diabetes', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E11.3');

-- E11.4 - Diabetes mellitus não-insulino-dependente com complicações neurológicas
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E11.4', 'Diabetes mellitus não-insulino-dependente com complicações neurológicas', 'Diabetes tipo 2 com neuropatia',
    'Doenças Endócrinas', 'Diabetes', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E11.4');

-- E11.5 - Diabetes mellitus não-insulino-dependente com complicações circulatórias periféricas
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E11.5', 'Diabetes mellitus não-insulino-dependente com complicações circulatórias periféricas', 'Diabetes tipo 2 com angiopatia',
    'Doenças Endócrinas', 'Diabetes', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E11.5');

-- E11.6 - Diabetes mellitus não-insulino-dependente com outras complicações especificadas
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E11.6', 'Diabetes mellitus não-insulino-dependente com outras complicações especificadas', 'Diabetes tipo 2 com outras complicações',
    'Doenças Endócrinas', 'Diabetes', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E11.6');

-- E11.7 - Diabetes mellitus não-insulino-dependente com múltiplas complicações
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E11.7', 'Diabetes mellitus não-insulino-dependente com múltiplas complicações', 'Diabetes tipo 2 com múltiplas complicações',
    'Doenças Endócrinas', 'Diabetes', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E11.7');

-- E11.8 - Diabetes mellitus não-insulino-dependente com complicações não especificadas
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E11.8', 'Diabetes mellitus não-insulino-dependente com complicações não especificadas', 'Diabetes tipo 2 com complicações',
    'Doenças Endócrinas', 'Diabetes', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E11.8');

-- E11.9 - Diabetes mellitus não-insulino-dependente sem complicações
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E11.9', 'Diabetes mellitus não-insulino-dependente sem complicações', 'Diabetes tipo 2 sem complicações',
    'Doenças Endócrinas', 'Diabetes', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E11.9');

-- E12 - Diabetes mellitus relacionado com desnutrição
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E12', 'Diabetes mellitus relacionado com desnutrição', 'Diabetes por desnutrição',
    'Doenças Endócrinas', 'Diabetes', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E12');

-- E13 - Outros tipos especificados de diabetes mellitus
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E13', 'Outros tipos especificados de diabetes mellitus', 'Diabetes especificado',
    'Doenças Endócrinas', 'Diabetes', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E13');

-- E14 - Diabetes mellitus não especificado
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E14', 'Diabetes mellitus não especificado', 'Diabetes',
    'Doenças Endócrinas', 'Diabetes', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E14');

-- E15 - Coma hipoglicêmico não-diabético
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E15', 'Coma hipoglicêmico não-diabético', 'Coma hipoglicêmico',
    'Doenças Endócrinas', 'Hipoglicemia', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E15');

-- E16 - Outros transtornos da secreção pancreática interna
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E16', 'Outros transtornos da secreção pancreática interna', 'Transtorno de secreção pancreática',
    'Doenças Endócrinas', 'Pâncreas', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E16');

-- E20 - Hipoparatireoidismo
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E20', 'Hipoparatireoidismo', 'Hipoparatireoidismo',
    'Doenças Endócrinas', 'Paratireóide', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E20');

-- E21 - Hiperparatireoidismo e outros transtornos da glândula paratireóide
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E21', 'Hiperparatireoidismo e outros transtornos da glândula paratireóide', 'Hiperparatireoidismo',
    'Doenças Endócrinas', 'Paratireóide', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E21');

-- E22 - Hiperfunção da hipófise
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E22', 'Hiperfunção da hipófise', 'Hiperfunção hipofisária',
    'Doenças Endócrinas', 'Hipófise', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E22');

-- E23 - Hipofunção e outros transtornos da hipófise
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E23', 'Hipofunção e outros transtornos da hipófise', 'Hipofunção hipofisária',
    'Doenças Endócrinas', 'Hipófise', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E23');

-- E24 - Síndrome de Cushing
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E24', 'Síndrome de Cushing', 'Síndrome de Cushing',
    'Doenças Endócrinas', 'Supra-renal', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E24');

-- E25 - Transtornos adrenogenitais
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E25', 'Transtornos adrenogenitais', 'Transtorno adrenogenital',
    'Doenças Endócrinas', 'Supra-renal', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E25');

-- E26 - Hiperaldosteronismo
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E26', 'Hiperaldosteronismo', 'Hiperaldosteronismo',
    'Doenças Endócrinas', 'Supra-renal', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E26');

-- E27 - Outros transtornos da glândula supra-renal
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E27', 'Outros transtornos da glândula supra-renal', 'Transtorno de supra-renal',
    'Doenças Endócrinas', 'Supra-renal', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E27');

-- E28 - Disfunção ovariana
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E28', 'Disfunção ovariana', 'Disfunção ovariana',
    'Doenças Endócrinas', 'Ovário', 'F', NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E28');

-- E29 - Disfunção testicular
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E29', 'Disfunção testicular', 'Disfunção testicular',
    'Doenças Endócrinas', 'Testículo', 'M', NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E29');

-- E30 - Transtornos da puberdade não classificados em outra parte
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E30', 'Transtornos da puberdade não classificados em outra parte', 'Transtorno da puberdade',
    'Doenças Endócrinas', 'Puberdade', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E30');

-- E31 - Disfunção poliglandular
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E31', 'Disfunção poliglandular', 'Disfunção poliglandular',
    'Doenças Endócrinas', 'Disfunção Poliglandular', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E31');

-- E32 - Doenças do timo
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E32', 'Doenças do timo', 'Doença do timo',
    'Doenças Endócrinas', 'Timo', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E32');

-- E34 - Outros transtornos endócrinos
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E34', 'Outros transtornos endócrinos', 'Transtorno endócrino',
    'Doenças Endócrinas', 'Transtorno Endócrino', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E34');

-- E40 - Kwashiorkor
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E40', 'Kwashiorkor', 'Kwashiorkor',
    'Doenças Endócrinas', 'Desnutrição', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E40');

-- E41 - Marasmo nutricional
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E41', 'Marasmo nutricional', 'Marasmo',
    'Doenças Endócrinas', 'Desnutrição', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E41');

-- E42 - Kwashiorkor marasmático
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E42', 'Kwashiorkor marasmático', 'Kwashiorkor marasmático',
    'Doenças Endócrinas', 'Desnutrição', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E42');

-- E43 - Desnutrição protéico-calórica grave não especificada
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E43', 'Desnutrição protéico-calórica grave não especificada', 'Desnutrição grave',
    'Doenças Endócrinas', 'Desnutrição', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E43');

-- E44 - Desnutrição protéico-calórica de graus moderado e leve
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E44', 'Desnutrição protéico-calórica de graus moderado e leve', 'Desnutrição moderada/leve',
    'Doenças Endócrinas', 'Desnutrição', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E44');

-- E45 - Atraso do desenvolvimento devido à desnutrição protéico-calórica
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E45', 'Atraso do desenvolvimento devido à desnutrição protéico-calórica', 'Atraso por desnutrição',
    'Doenças Endócrinas', 'Desnutrição', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E45');

-- E46 - Desnutrição não especificada
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E46', 'Desnutrição não especificada', 'Desnutrição',
    'Doenças Endócrinas', 'Desnutrição', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E46');

-- E50 - Deficiência de vitamina A
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E50', 'Deficiência de vitamina A', 'Deficiência de vitamina A',
    'Doenças Endócrinas', 'Deficiência de Vitamina', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E50');

-- E51 - Deficiência de tiamina
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E51', 'Deficiência de tiamina', 'Deficiência de tiamina',
    'Doenças Endócrinas', 'Deficiência de Vitamina', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E51');

-- E52 - Deficiência de niacina [pelagra]
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E52', 'Deficiência de niacina [pelagra]', 'Deficiência de niacina',
    'Doenças Endócrinas', 'Deficiência de Vitamina', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E52');

-- E53 - Deficiência de outras vitaminas do grupo B
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E53', 'Deficiência de outras vitaminas do grupo B', 'Deficiência de vitamina B',
    'Doenças Endócrinas', 'Deficiência de Vitamina', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E53');

-- E54 - Deficiência de ácido ascórbico
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E54', 'Deficiência de ácido ascórbico', 'Deficiência de vitamina C',
    'Doenças Endócrinas', 'Deficiência de Vitamina', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E54');

-- E55 - Deficiência de vitamina D
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E55', 'Deficiência de vitamina D', 'Deficiência de vitamina D',
    'Doenças Endócrinas', 'Deficiência de Vitamina', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E55');

-- E56 - Outras deficiências vitamínicas
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E56', 'Outras deficiências vitamínicas', 'Deficiência vitamínica',
    'Doenças Endócrinas', 'Deficiência de Vitamina', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E56');

-- E58 - Deficiência de cálcio da dieta
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E58', 'Deficiência de cálcio da dieta', 'Deficiência de cálcio',
    'Doenças Endócrinas', 'Deficiência Mineral', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E58');

-- E59 - Deficiência de selênio da dieta
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E59', 'Deficiência de selênio da dieta', 'Deficiência de selênio',
    'Doenças Endócrinas', 'Deficiência Mineral', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E59');

-- E60 - Deficiência de zinco da dieta
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E60', 'Deficiência de zinco da dieta', 'Deficiência de zinco',
    'Doenças Endócrinas', 'Deficiência Mineral', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E60');

-- E61 - Deficiência de outros elementos nutrientes
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E61', 'Deficiência de outros elementos nutrientes', 'Deficiência de nutriente',
    'Doenças Endócrinas', 'Deficiência Mineral', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E61');

-- E63 - Outras deficiências nutricionais
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E63', 'Outras deficiências nutricionais', 'Deficiência nutricional',
    'Doenças Endócrinas', 'Deficiência Nutricional', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E63');

-- E64 - Seqüelas de desnutrição e de outras deficiências nutricionais
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E64', 'Seqüelas de desnutrição e de outras deficiências nutricionais', 'Seqüela de desnutrição',
    'Doenças Endócrinas', 'Seqüela Nutricional', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E64');

-- E65 - Adiposidade localizada
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E65', 'Adiposidade localizada', 'Adiposidade localizada',
    'Doenças Endócrinas', 'Obesidade', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E65');

-- E66 - Obesidade
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E66', 'Obesidade', 'Obesidade',
    'Doenças Endócrinas', 'Obesidade', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E66');

-- E66.0 - Obesidade devida a excesso de calorias
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E66.0', 'Obesidade devida a excesso de calorias', 'Obesidade por excesso calórico',
    'Doenças Endócrinas', 'Obesidade', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E66.0');

-- E66.01 - Obesidade mórbida
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E66.01', 'Obesidade mórbida', 'Obesidade mórbida',
    'Doenças Endócrinas', 'Obesidade', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E66.01');

-- E67 - Hiperalimentação
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E67', 'Hiperalimentação', 'Hiperalimentação',
    'Doenças Endócrinas', 'Hiperalimentação', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E67');

-- E68 - Seqüelas de hiperalimentação
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E68', 'Seqüelas de hiperalimentação', 'Seqüela de hiperalimentação',
    'Doenças Endócrinas', 'Seqüela Nutricional', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E68');

-- E70 - Transtornos do metabolismo de aminoácidos aromáticos
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E70', 'Transtornos do metabolismo de aminoácidos aromáticos', 'Transtorno de aminoácidos aromáticos',
    'Doenças Endócrinas', 'Erro Inato do Metabolismo', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E70');

-- E71 - Transtornos do metabolismo de aminoácidos de cadeia ramificada e do metabolismo dos ácidos graxos
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E71', 'Transtornos do metabolismo de aminoácidos de cadeia ramificada e do metabolismo dos ácidos graxos', 'Transtorno de aminoácidos ramificados',
    'Doenças Endócrinas', 'Erro Inato do Metabolismo', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E71');

-- E72 - Outros transtornos do metabolismo de aminoácidos
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E72', 'Outros transtornos do metabolismo de aminoácidos', 'Transtorno de aminoácidos',
    'Doenças Endócrinas', 'Erro Inato do Metabolismo', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E72');

-- E73 - Intolerância à lactose
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E73', 'Intolerância à lactose', 'Intolerância à lactose',
    'Doenças Endócrinas', 'Intolerância', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E73');

-- E74 - Outros transtornos do metabolismo de carboidratos
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E74', 'Outros transtornos do metabolismo de carboidratos', 'Transtorno de carboidratos',
    'Doenças Endócrinas', 'Erro Inato do Metabolismo', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E74');

-- E75 - Transtornos do metabolismo de esfingolipídeos e outros transtornos de depósito de lipídeos
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E75', 'Transtornos do metabolismo de esfingolipídeos e outros transtornos de depósito de lipídeos', 'Transtorno de esfingolipídeos',
    'Doenças Endócrinas', 'Erro Inato do Metabolismo', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E75');

-- E76 - Transtornos do metabolismo dos glicosaminoglicanos
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E76', 'Transtornos do metabolismo dos glicosaminoglicanos', 'Transtorno de glicosaminoglicanos',
    'Doenças Endócrinas', 'Erro Inato do Metabolismo', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E76');

-- E77 - Transtornos do metabolismo de glicoproteínas
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E77', 'Transtornos do metabolismo de glicoproteínas', 'Transtorno de glicoproteínas',
    'Doenças Endócrinas', 'Erro Inato do Metabolismo', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E77');

-- E78 - Transtornos do metabolismo de lipoproteínas e outras lipidemias
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E78', 'Transtornos do metabolismo de lipoproteínas e outras lipidemias', 'Dislipidemia',
    'Doenças Endócrinas', 'Dislipidemia', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E78');

-- E78.0 - Hipercolesterolemia pura
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E78.0', 'Hipercolesterolemia pura', 'Hipercolesterolemia',
    'Doenças Endócrinas', 'Dislipidemia', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E78.0');

-- E78.1 - Hipertrigliceridemia pura
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E78.1', 'Hipertrigliceridemia pura', 'Hipertrigliceridemia',
    'Doenças Endócrinas', 'Dislipidemia', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E78.1');

-- E78.2 - Hiperlipidemia mista
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E78.2', 'Hiperlipidemia mista', 'Hiperlipidemia mista',
    'Doenças Endócrinas', 'Dislipidemia', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E78.2');

-- E78.4 - Outras hiperlipidemias
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E78.4', 'Outras hiperlipidemias', 'Hiperlipidemia',
    'Doenças Endócrinas', 'Dislipidemia', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E78.4');

-- E78.5 - Hiperlipidemia não especificada
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E78.5', 'Hiperlipidemia não especificada', 'Hiperlipidemia',
    'Doenças Endócrinas', 'Dislipidemia', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E78.5');

-- E79 - Transtornos do metabolismo de purinas e pirimidinas
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E79', 'Transtornos do metabolismo de purinas e pirimidinas', 'Transtorno de purinas',
    'Doenças Endócrinas', 'Erro Inato do Metabolismo', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E79');

-- E80 - Transtornos do metabolismo da porfirina e da bilirrubina
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E80', 'Transtornos do metabolismo da porfirina e da bilirrubina', 'Transtorno de porfirina',
    'Doenças Endócrinas', 'Erro Inato do Metabolismo', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E80');

-- E83 - Transtornos do metabolismo de minerais
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E83', 'Transtornos do metabolismo de minerais', 'Transtorno de minerais',
    'Doenças Endócrinas', 'Erro Inato do Metabolismo', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E83');

-- E84 - Fibrose cística
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E84', 'Fibrose cística', 'Fibrose cística',
    'Doenças Endócrinas', 'Fibrose Cística', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E84');

-- E85 - Amiloidose
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E85', 'Amiloidose', 'Amiloidose',
    'Doenças Endócrinas', 'Amiloidose', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E85');

-- E86 - Depleção do volume
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E86', 'Depleção do volume', 'Depleção de volume',
    'Doenças Endócrinas', 'Depleção', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E86');

-- E87 - Outros transtornos do equilíbrio hidroeletrolítico e ácido-básico
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E87', 'Outros transtornos do equilíbrio hidroeletrolítico e ácido-básico', 'Transtorno hidroeletrolítico',
    'Doenças Endócrinas', 'Transtorno Hidroeletrolítico', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E87');

-- E88 - Outros transtornos metabólicos
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E88', 'Outros transtornos metabólicos', 'Transtorno metabólico',
    'Doenças Endócrinas', 'Transtorno Metabólico', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E88');

-- E89 - Transtornos endócrinos e metabólicos pós-procedimentos, não classificados em outra parte
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'E89', 'Transtornos endócrinos e metabólicos pós-procedimentos, não classificados em outra parte', 'Transtorno endócrino pós-procedimento',
    'Doenças Endócrinas', 'Transtorno Pós-procedimento', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'E89');

-- F00 - Demência na doença de Alzheimer
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F00', 'Demência na doença de Alzheimer', 'Demência de Alzheimer',
    'Transtornos Mentais', 'Demência', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F00');

-- F01 - Demência vascular
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F01', 'Demência vascular', 'Demência vascular',
    'Transtornos Mentais', 'Demência', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F01');

-- F02 - Demência em outras doenças classificadas em outra parte
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F02', 'Demência em outras doenças classificadas em outra parte', 'Demência secundária',
    'Transtornos Mentais', 'Demência', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F02');

-- F03 - Demência não especificada
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F03', 'Demência não especificada', 'Demência',
    'Transtornos Mentais', 'Demência', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F03');

-- F10 - Transtornos mentais e comportamentais devidos ao uso de álcool
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F10', 'Transtornos mentais e comportamentais devidos ao uso de álcool', 'Transtorno por álcool',
    'Transtornos Mentais', 'Transtorno por Substância', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F10');

-- F11 - Transtornos mentais e comportamentais devidos ao uso de opiáceos
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F11', 'Transtornos mentais e comportamentais devidos ao uso de opiáceos', 'Transtorno por opiáceos',
    'Transtornos Mentais', 'Transtorno por Substância', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F11');

-- F12 - Transtornos mentais e comportamentais devidos ao uso de canabinóides
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F12', 'Transtornos mentais e comportamentais devidos ao uso de canabinóides', 'Transtorno por canabinóides',
    'Transtornos Mentais', 'Transtorno por Substância', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F12');

-- F13 - Transtornos mentais e comportamentais devidos ao uso de sedativos e hipnóticos
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F13', 'Transtornos mentais e comportamentais devidos ao uso de sedativos e hipnóticos', 'Transtorno por sedativos',
    'Transtornos Mentais', 'Transtorno por Substância', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F13');

-- F14 - Transtornos mentais e comportamentais devidos ao uso de cocaína
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F14', 'Transtornos mentais e comportamentais devidos ao uso de cocaína', 'Transtorno por cocaína',
    'Transtornos Mentais', 'Transtorno por Substância', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F14');

-- F15 - Transtornos mentais e comportamentais devidos ao uso de outros estimulantes, incluindo a cafeína
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F15', 'Transtornos mentais e comportamentais devidos ao uso de outros estimulantes, incluindo a cafeína', 'Transtorno por estimulantes',
    'Transtornos Mentais', 'Transtorno por Substância', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F15');

-- F16 - Transtornos mentais e comportamentais devidos ao uso de alucinógenos
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F16', 'Transtornos mentais e comportamentais devidos ao uso de alucinógenos', 'Transtorno por alucinógenos',
    'Transtornos Mentais', 'Transtorno por Substância', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F16');

-- F17 - Transtornos mentais e comportamentais devidos ao uso de fumo
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F17', 'Transtornos mentais e comportamentais devidos ao uso de fumo', 'Transtorno por fumo',
    'Transtornos Mentais', 'Transtorno por Substância', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F17');

-- F18 - Transtornos mentais e comportamentais devidos ao uso de solventes voláteis
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F18', 'Transtornos mentais e comportamentais devidos ao uso de solventes voláteis', 'Transtorno por solventes',
    'Transtornos Mentais', 'Transtorno por Substância', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F18');

-- F19 - Transtornos mentais e comportamentais devidos ao uso de múltiplas drogas e de outras substâncias psicoativas
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F19', 'Transtornos mentais e comportamentais devidos ao uso de múltiplas drogas e de outras substâncias psicoativas', 'Transtorno por múltiplas drogas',
    'Transtornos Mentais', 'Transtorno por Substância', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F19');

-- F20 - Esquizofrenia
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F20', 'Esquizofrenia', 'Esquizofrenia',
    'Transtornos Mentais', 'Esquizofrenia', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F20');

-- F21 - Transtorno esquizotípico
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F21', 'Transtorno esquizotípico', 'Transtorno esquizotípico',
    'Transtornos Mentais', 'Esquizofrenia', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F21');

-- F22 - Transtornos delirantes persistentes
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F22', 'Transtornos delirantes persistentes', 'Transtorno delirante',
    'Transtornos Mentais', 'Transtorno Delirante', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F22');

-- F23 - Transtornos psicóticos agudos e transitórios
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F23', 'Transtornos psicóticos agudos e transitórios', 'Transtorno psicótico agudo',
    'Transtornos Mentais', 'Transtorno Psicótico', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F23');

-- F24 - Transtorno delirante induzido
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F24', 'Transtorno delirante induzido', 'Transtorno delirante induzido',
    'Transtornos Mentais', 'Transtorno Delirante', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F24');

-- F25 - Transtornos esquizoafetivos
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F25', 'Transtornos esquizoafetivos', 'Transtorno esquizoafetivo',
    'Transtornos Mentais', 'Transtorno Esquizoafetivo', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F25');

-- F28 - Outros transtornos psicóticos não-orgânicos
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F28', 'Outros transtornos psicóticos não-orgânicos', 'Transtorno psicótico',
    'Transtornos Mentais', 'Transtorno Psicótico', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F28');

-- F29 - Psicose não-orgânica não especificada
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F29', 'Psicose não-orgânica não especificada', 'Psicose',
    'Transtornos Mentais', 'Transtorno Psicótico', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F29');

-- F30 - Episódio maníaco
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F30', 'Episódio maníaco', 'Episódio maníaco',
    'Transtornos Mentais', 'Transtorno do Humor', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F30');

-- F31 - Transtorno afetivo bipolar
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F31', 'Transtorno afetivo bipolar', 'Transtorno bipolar',
    'Transtornos Mentais', 'Transtorno do Humor', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F31');

-- F32 - Episódio depressivo
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F32', 'Episódio depressivo', 'Episódio depressivo',
    'Transtornos Mentais', 'Transtorno do Humor', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F32');

-- F32.0 - Episódio depressivo leve
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F32.0', 'Episódio depressivo leve', 'Depressão leve',
    'Transtornos Mentais', 'Transtorno do Humor', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F32.0');

-- F32.1 - Episódio depressivo moderado
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F32.1', 'Episódio depressivo moderado', 'Depressão moderada',
    'Transtornos Mentais', 'Transtorno do Humor', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F32.1');

-- F32.2 - Episódio depressivo grave sem sintomas psicóticos
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F32.2', 'Episódio depressivo grave sem sintomas psicóticos', 'Depressão grave',
    'Transtornos Mentais', 'Transtorno do Humor', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F32.2');

-- F32.3 - Episódio depressivo grave com sintomas psicóticos
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F32.3', 'Episódio depressivo grave com sintomas psicóticos', 'Depressão grave com psicose',
    'Transtornos Mentais', 'Transtorno do Humor', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F32.3');

-- F32.8 - Outros episódios depressivos
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F32.8', 'Outros episódios depressivos', 'Episódio depressivo',
    'Transtornos Mentais', 'Transtorno do Humor', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F32.8');

-- F32.9 - Episódio depressivo não especificado
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F32.9', 'Episódio depressivo não especificado', 'Depressão',
    'Transtornos Mentais', 'Transtorno do Humor', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F32.9');

-- F33 - Transtorno depressivo recorrente
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F33', 'Transtorno depressivo recorrente', 'Depressão recorrente',
    'Transtornos Mentais', 'Transtorno do Humor', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F33');

-- F34 - Transtornos de humor [afetivos] persistentes
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F34', 'Transtornos de humor [afetivos] persistentes', 'Transtorno de humor persistente',
    'Transtornos Mentais', 'Transtorno do Humor', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F34');

-- F38 - Outros transtornos do humor [afetivos]
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F38', 'Outros transtornos do humor [afetivos]', 'Transtorno do humor',
    'Transtornos Mentais', 'Transtorno do Humor', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F38');

-- F39 - Transtorno do humor [afetivo] não especificado
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F39', 'Transtorno do humor [afetivo] não especificado', 'Transtorno do humor',
    'Transtornos Mentais', 'Transtorno do Humor', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F39');

-- F40 - Transtornos fóbico-ansiosos
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F40', 'Transtornos fóbico-ansiosos', 'Transtorno fóbico',
    'Transtornos Mentais', 'Transtorno de Ansiedade', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F40');

-- F41 - Outros transtornos ansiosos
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F41', 'Outros transtornos ansiosos', 'Transtorno de ansiedade',
    'Transtornos Mentais', 'Transtorno de Ansiedade', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F41');

-- F41.0 - Transtorno de pânico [ansiedade paroxística episódica]
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F41.0', 'Transtorno de pânico [ansiedade paroxística episódica]', 'Transtorno de pânico',
    'Transtornos Mentais', 'Transtorno de Ansiedade', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F41.0');

-- F41.1 - Ansiedade generalizada
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F41.1', 'Ansiedade generalizada', 'Ansiedade generalizada',
    'Transtornos Mentais', 'Transtorno de Ansiedade', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F41.1');

-- F41.2 - Transtorno misto ansioso e depressivo
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F41.2', 'Transtorno misto ansioso e depressivo', 'Transtorno ansioso-depressivo',
    'Transtornos Mentais', 'Transtorno de Ansiedade', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F41.2');

-- F41.3 - Outros transtornos ansiosos mistos
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F41.3', 'Outros transtornos ansiosos mistos', 'Transtorno ansioso',
    'Transtornos Mentais', 'Transtorno de Ansiedade', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F41.3');

-- F41.8 - Outros transtornos ansiosos especificados
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F41.8', 'Outros transtornos ansiosos especificados', 'Transtorno de ansiedade',
    'Transtornos Mentais', 'Transtorno de Ansiedade', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F41.8');

-- F41.9 - Transtorno ansioso não especificado
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F41.9', 'Transtorno ansioso não especificado', 'Transtorno de ansiedade',
    'Transtornos Mentais', 'Transtorno de Ansiedade', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F41.9');

-- F42 - Transtorno obsessivo-compulsivo
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F42', 'Transtorno obsessivo-compulsivo', 'TOC',
    'Transtornos Mentais', 'Transtorno Obsessivo-Compulsivo', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F42');

-- F43 - Reações ao stress grave e transtornos de adaptação
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F43', 'Reações ao stress grave e transtornos de adaptação', 'Transtorno de adaptação',
    'Transtornos Mentais', 'Transtorno de Adaptação', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F43');

-- F43.0 - Reação aguda ao stress
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F43.0', 'Reação aguda ao stress', 'Reação aguda ao stress',
    'Transtornos Mentais', 'Transtorno de Adaptação', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F43.0');

-- F43.1 - Transtorno de stress pós-traumático
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F43.1', 'Transtorno de stress pós-traumático', 'TEPT',
    'Transtornos Mentais', 'Transtorno de Adaptação', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F43.1');

-- F43.2 - Transtornos de adaptação
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F43.2', 'Transtornos de adaptação', 'Transtorno de adaptação',
    'Transtornos Mentais', 'Transtorno de Adaptação', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F43.2');

-- F44 - Transtornos dissociativos [de conversão]
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F44', 'Transtornos dissociativos [de conversão]', 'Transtorno dissociativo',
    'Transtornos Mentais', 'Transtorno Dissociativo', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F44');

-- F45 - Transtornos somatoformes
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F45', 'Transtornos somatoformes', 'Transtorno somatoforme',
    'Transtornos Mentais', 'Transtorno Somatoforme', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F45');

-- F48 - Outros transtornos neuróticos
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F48', 'Outros transtornos neuróticos', 'Transtorno neurótico',
    'Transtornos Mentais', 'Transtorno Neurótico', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F48');

-- F50 - Transtornos da alimentação
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F50', 'Transtornos da alimentação', 'Transtorno alimentar',
    'Transtornos Mentais', 'Transtorno Alimentar', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F50');

-- F50.0 - Anorexia nervosa
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F50.0', 'Anorexia nervosa', 'Anorexia nervosa',
    'Transtornos Mentais', 'Transtorno Alimentar', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F50.0');

-- F50.1 - Anorexia nervosa atípica
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F50.1', 'Anorexia nervosa atípica', 'Anorexia atípica',
    'Transtornos Mentais', 'Transtorno Alimentar', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F50.1');

-- F50.2 - Bulimia nervosa
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F50.2', 'Bulimia nervosa', 'Bulimia nervosa',
    'Transtornos Mentais', 'Transtorno Alimentar', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F50.2');

-- F50.3 - Bulimia nervosa atípica
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F50.3', 'Bulimia nervosa atípica', 'Bulimia atípica',
    'Transtornos Mentais', 'Transtorno Alimentar', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F50.3');

-- F50.4 - Hiperfagia em outras perturbações psicológicas
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F50.4', 'Hiperfagia em outras perturbações psicológicas', 'Hiperfagia',
    'Transtornos Mentais', 'Transtorno Alimentar', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F50.4');

-- F50.5 - Vômitos associados a outras perturbações psicológicas
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F50.5', 'Vômitos associados a outras perturbações psicológicas', 'Vômito psicológico',
    'Transtornos Mentais', 'Transtorno Alimentar', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F50.5');

-- F50.8 - Outros transtornos da alimentação
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F50.8', 'Outros transtornos da alimentação', 'Transtorno alimentar',
    'Transtornos Mentais', 'Transtorno Alimentar', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F50.8');

-- F50.9 - Transtorno da alimentação não especificado
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F50.9', 'Transtorno da alimentação não especificado', 'Transtorno alimentar',
    'Transtornos Mentais', 'Transtorno Alimentar', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F50.9');

-- F51 - Transtornos não-orgânicos do sono devidos a fatores emocionais
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F51', 'Transtornos não-orgânicos do sono devidos a fatores emocionais', 'Transtorno do sono',
    'Transtornos Mentais', 'Transtorno do Sono', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F51');

-- F52 - Disfunção sexual, não causada por transtorno ou doença orgânica
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F52', 'Disfunção sexual, não causada por transtorno ou doença orgânica', 'Disfunção sexual',
    'Transtornos Mentais', 'Disfunção Sexual', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F52');

-- F53 - Transtornos mentais e comportamentais associados ao puerpério, não classificados em outra parte
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F53', 'Transtornos mentais e comportamentais associados ao puerpério, não classificados em outra parte', 'Transtorno puerperal',
    'Transtornos Mentais', 'Transtorno Puerperal', 'F', NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F53');

-- F54 - Fatores psicológicos e comportamentais associados a doenças ou transtornos classificados em outra parte
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F54', 'Fatores psicológicos e comportamentais associados a doenças ou transtornos classificados em outra parte', 'Fator psicológico',
    'Transtornos Mentais', 'Fator Psicológico', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F54');

-- F55 - Abuso de substâncias que não produzem dependência
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F55', 'Abuso de substâncias que não produzem dependência', 'Abuso de substância',
    'Transtornos Mentais', 'Abuso de Substância', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F55');

-- F59 - Síndromes comportamentais associadas a perturbações fisiológicas e a fatores físicos não especificados
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F59', 'Síndromes comportamentais associadas a perturbações fisiológicas e a fatores físicos não especificados', 'Síndrome comportamental',
    'Transtornos Mentais', 'Síndrome Comportamental', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F59');

-- F60 - Transtornos específicos da personalidade
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F60', 'Transtornos específicos da personalidade', 'Transtorno de personalidade',
    'Transtornos Mentais', 'Transtorno de Personalidade', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F60');

-- F60.0 - Personalidade paranóide
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F60.0', 'Personalidade paranóide', 'Personalidade paranóide',
    'Transtornos Mentais', 'Transtorno de Personalidade', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F60.0');

-- F60.1 - Personalidade esquizóide
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F60.1', 'Personalidade esquizóide', 'Personalidade esquizóide',
    'Transtornos Mentais', 'Transtorno de Personalidade', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F60.1');

-- F60.2 - Personalidade dissocial
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F60.2', 'Personalidade dissocial', 'Personalidade dissocial',
    'Transtornos Mentais', 'Transtorno de Personalidade', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F60.2');

-- F60.3 - Personalidade emocionalmente instável
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F60.3', 'Personalidade emocionalmente instável', 'Personalidade instável',
    'Transtornos Mentais', 'Transtorno de Personalidade', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F60.3');

-- F60.4 - Personalidade histriônica
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F60.4', 'Personalidade histriônica', 'Personalidade histriônica',
    'Transtornos Mentais', 'Transtorno de Personalidade', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F60.4');

-- F60.5 - Personalidade anancástica
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F60.5', 'Personalidade anancástica', 'Personalidade anancástica',
    'Transtornos Mentais', 'Transtorno de Personalidade', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F60.5');

-- F60.6 - Personalidade ansiosa [esquiva]
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F60.6', 'Personalidade ansiosa [esquiva]', 'Personalidade ansiosa',
    'Transtornos Mentais', 'Transtorno de Personalidade', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F60.6');

-- F60.7 - Personalidade dependente
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F60.7', 'Personalidade dependente', 'Personalidade dependente',
    'Transtornos Mentais', 'Transtorno de Personalidade', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F60.7');

-- F60.8 - Outros transtornos específicos da personalidade
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F60.8', 'Outros transtornos específicos da personalidade', 'Transtorno de personalidade',
    'Transtornos Mentais', 'Transtorno de Personalidade', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F60.8');

-- F60.9 - Transtorno de personalidade não especificado
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F60.9', 'Transtorno de personalidade não especificado', 'Transtorno de personalidade',
    'Transtornos Mentais', 'Transtorno de Personalidade', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F60.9');

-- F61 - Transtornos mistos da personalidade e outros transtornos da personalidade
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F61', 'Transtornos mistos da personalidade e outros transtornos da personalidade', 'Transtorno misto de personalidade',
    'Transtornos Mentais', 'Transtorno de Personalidade', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F61');

-- F62 - Modificações duradouras da personalidade não atribuíveis a lesão ou doença cerebral
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F62', 'Modificações duradouras da personalidade não atribuíveis a lesão ou doença cerebral', 'Modificação de personalidade',
    'Transtornos Mentais', 'Modificação de Personalidade', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F62');

-- F63 - Transtornos dos hábitos e dos impulsos
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F63', 'Transtornos dos hábitos e dos impulsos', 'Transtorno de impulso',
    'Transtornos Mentais', 'Transtorno de Impulso', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F63');

-- F64 - Transtornos da identidade sexual
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F64', 'Transtornos da identidade sexual', 'Transtorno de identidade sexual',
    'Transtornos Mentais', 'Transtorno de Identidade Sexual', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F64');

-- F65 - Transtornos da preferência sexual
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F65', 'Transtornos da preferência sexual', 'Transtorno de preferência sexual',
    'Transtornos Mentais', 'Transtorno de Preferência Sexual', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F65');

-- F66 - Transtornos psicológicos e comportamentais associados ao desenvolvimento sexual e à sua orientação
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F66', 'Transtornos psicológicos e comportamentais associados ao desenvolvimento sexual e à sua orientação', 'Transtorno relacionado à orientação sexual',
    'Transtornos Mentais', 'Transtorno Sexual', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F66');

-- F68 - Outros transtornos da personalidade e do comportamento do adulto
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F68', 'Outros transtornos da personalidade e do comportamento do adulto', 'Transtorno de personalidade',
    'Transtornos Mentais', 'Transtorno de Personalidade', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F68');

-- F69 - Transtorno da personalidade e do comportamento do adulto não especificado
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F69', 'Transtorno da personalidade e do comportamento do adulto não especificado', 'Transtorno de personalidade',
    'Transtornos Mentais', 'Transtorno de Personalidade', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F69');

-- F70 - Retardo mental leve
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F70', 'Retardo mental leve', 'Retardo mental leve',
    'Transtornos Mentais', 'Retardo Mental', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F70');

-- F71 - Retardo mental moderado
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F71', 'Retardo mental moderado', 'Retardo mental moderado',
    'Transtornos Mentais', 'Retardo Mental', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F71');

-- F72 - Retardo mental grave
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F72', 'Retardo mental grave', 'Retardo mental grave',
    'Transtornos Mentais', 'Retardo Mental', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F72');

-- F73 - Retardo mental profundo
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F73', 'Retardo mental profundo', 'Retardo mental profundo',
    'Transtornos Mentais', 'Retardo Mental', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F73');

-- F78 - Outro retardo mental
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F78', 'Outro retardo mental', 'Retardo mental',
    'Transtornos Mentais', 'Retardo Mental', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F78');

-- F79 - Retardo mental não especificado
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F79', 'Retardo mental não especificado', 'Retardo mental',
    'Transtornos Mentais', 'Retardo Mental', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F79');

-- F80 - Transtornos específicos do desenvolvimento da fala e da linguagem
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F80', 'Transtornos específicos do desenvolvimento da fala e da linguagem', 'Transtorno de linguagem',
    'Transtornos Mentais', 'Transtorno do Desenvolvimento', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F80');

-- F81 - Transtornos específicos do desenvolvimento das habilidades escolares
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F81', 'Transtornos específicos do desenvolvimento das habilidades escolares', 'Transtorno de aprendizagem',
    'Transtornos Mentais', 'Transtorno do Desenvolvimento', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F81');

-- F82 - Transtorno específico do desenvolvimento motor
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F82', 'Transtorno específico do desenvolvimento motor', 'Transtorno motor',
    'Transtornos Mentais', 'Transtorno do Desenvolvimento', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F82');

-- F84 - Transtornos globais do desenvolvimento
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F84', 'Transtornos globais do desenvolvimento', 'Transtorno global do desenvolvimento',
    'Transtornos Mentais', 'Transtorno do Desenvolvimento', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F84');

-- F84.0 - Autismo infantil
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F84.0', 'Autismo infantil', 'Autismo',
    'Transtornos Mentais', 'Transtorno do Desenvolvimento', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F84.0');

-- F84.1 - Autismo atípico
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F84.1', 'Autismo atípico', 'Autismo atípico',
    'Transtornos Mentais', 'Transtorno do Desenvolvimento', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F84.1');

-- F84.2 - Síndrome de Rett
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F84.2', 'Síndrome de Rett', 'Síndrome de Rett',
    'Transtornos Mentais', 'Transtorno do Desenvolvimento', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F84.2');

-- F84.3 - Outro transtorno desintegrativo da infância
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F84.3', 'Outro transtorno desintegrativo da infância', 'Transtorno desintegrativo',
    'Transtornos Mentais', 'Transtorno do Desenvolvimento', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F84.3');

-- F84.4 - Transtorno com hipercinesia associada a retardo mental e a movimentos estereotipados
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F84.4', 'Transtorno com hipercinesia associada a retardo mental e a movimentos estereotipados', 'Transtorno com hipercinesia',
    'Transtornos Mentais', 'Transtorno do Desenvolvimento', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F84.4');

-- F84.5 - Síndrome de Asperger
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F84.5', 'Síndrome de Asperger', 'Síndrome de Asperger',
    'Transtornos Mentais', 'Transtorno do Desenvolvimento', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F84.5');

-- F84.8 - Outros transtornos globais do desenvolvimento
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F84.8', 'Outros transtornos globais do desenvolvimento', 'Transtorno global do desenvolvimento',
    'Transtornos Mentais', 'Transtorno do Desenvolvimento', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F84.8');

-- F84.9 - Transtorno global do desenvolvimento não especificado
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F84.9', 'Transtorno global do desenvolvimento não especificado', 'Transtorno global do desenvolvimento',
    'Transtornos Mentais', 'Transtorno do Desenvolvimento', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F84.9');

-- F88 - Outros transtornos do desenvolvimento psicológico
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F88', 'Outros transtornos do desenvolvimento psicológico', 'Transtorno do desenvolvimento',
    'Transtornos Mentais', 'Transtorno do Desenvolvimento', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F88');

-- F89 - Transtorno do desenvolvimento psicológico não especificado
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F89', 'Transtorno do desenvolvimento psicológico não especificado', 'Transtorno do desenvolvimento',
    'Transtornos Mentais', 'Transtorno do Desenvolvimento', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F89');

-- F90 - Transtornos hipercinéticos
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F90', 'Transtornos hipercinéticos', 'Transtorno hipercinético',
    'Transtornos Mentais', 'Transtorno Hipercinético', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F90');

-- F90.0 - Perturbação da atividade e da atenção
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F90.0', 'Perturbação da atividade e da atenção', 'TDAH',
    'Transtornos Mentais', 'Transtorno Hipercinético', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F90.0');

-- F90.1 - Transtorno hipercinético de conduta
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F90.1', 'Transtorno hipercinético de conduta', 'Transtorno hipercinético de conduta',
    'Transtornos Mentais', 'Transtorno Hipercinético', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F90.1');

-- F90.8 - Outros transtornos hipercinéticos
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F90.8', 'Outros transtornos hipercinéticos', 'Transtorno hipercinético',
    'Transtornos Mentais', 'Transtorno Hipercinético', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F90.8');

-- F90.9 - Transtorno hipercinético não especificado
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F90.9', 'Transtorno hipercinético não especificado', 'Transtorno hipercinético',
    'Transtornos Mentais', 'Transtorno Hipercinético', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F90.9');

-- F91 - Transtornos de conduta
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F91', 'Transtornos de conduta', 'Transtorno de conduta',
    'Transtornos Mentais', 'Transtorno de Conduta', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F91');

-- F92 - Transtornos mistos de conduta e das emoções
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F92', 'Transtornos mistos de conduta e das emoções', 'Transtorno misto de conduta',
    'Transtornos Mentais', 'Transtorno de Conduta', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F92');

-- F93 - Transtornos emocionais com início especificamente na infância
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F93', 'Transtornos emocionais com início especificamente na infância', 'Transtorno emocional infantil',
    'Transtornos Mentais', 'Transtorno Emocional', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F93');

-- F94 - Transtornos do funcionamento social com início especificamente na infância ou na adolescência
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F94', 'Transtornos do funcionamento social com início especificamente na infância ou na adolescência', 'Transtorno social infantil',
    'Transtornos Mentais', 'Transtorno Social', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F94');

-- F95 - Transtornos de tiques
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F95', 'Transtornos de tiques', 'Transtorno de tiques',
    'Transtornos Mentais', 'Transtorno de Tiques', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F95');

-- F95.0 - Tique transitório
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F95.0', 'Tique transitório', 'Tique transitório',
    'Transtornos Mentais', 'Transtorno de Tiques', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F95.0');

-- F95.1 - Tique motor ou vocal crônico
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F95.1', 'Tique motor ou vocal crônico', 'Tique crônico',
    'Transtornos Mentais', 'Transtorno de Tiques', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F95.1');

-- F95.2 - Transtorno de tiques vocais e motores múltiplos combinados [síndrome de Gilles de la Tourette]
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F95.2', 'Transtorno de tiques vocais e motores múltiplos combinados [síndrome de Gilles de la Tourette]', 'Síndrome de Tourette',
    'Transtornos Mentais', 'Transtorno de Tiques', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F95.2');

-- F95.8 - Outros transtornos de tiques
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F95.8', 'Outros transtornos de tiques', 'Transtorno de tiques',
    'Transtornos Mentais', 'Transtorno de Tiques', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F95.8');

-- F95.9 - Transtorno de tiques não especificado
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F95.9', 'Transtorno de tiques não especificado', 'Transtorno de tiques',
    'Transtornos Mentais', 'Transtorno de Tiques', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F95.9');

-- F98 - Outros transtornos comportamentais e emocionais com início habitualmente na infância ou na adolescência
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F98', 'Outros transtornos comportamentais e emocionais com início habitualmente na infância ou na adolescência', 'Transtorno comportamental infantil',
    'Transtornos Mentais', 'Transtorno Comportamental', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F98');

-- F98.0 - Enurese não-orgânica
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F98.0', 'Enurese não-orgânica', 'Enurese',
    'Transtornos Mentais', 'Enurese', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F98.0');

-- F98.1 - Encoprese não-orgânica
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F98.1', 'Encoprese não-orgânica', 'Encoprese',
    'Transtornos Mentais', 'Encoprese', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F98.1');

-- F98.2 - Transtorno da alimentação na infância
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F98.2', 'Transtorno da alimentação na infância', 'Transtorno alimentar infantil',
    'Transtornos Mentais', 'Transtorno Alimentar', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F98.2');

-- F98.3 - Pica do lactente e do criança
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F98.3', 'Pica do lactente e do criança', 'Pica',
    'Transtornos Mentais', 'Pica', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F98.3');

-- F98.4 - Transtorno de movimento estereotipado
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F98.4', 'Transtorno de movimento estereotipado', 'Transtorno de movimento estereotipado',
    'Transtornos Mentais', 'Transtorno de Movimento', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F98.4');

-- F98.5 - Gagueira [tartamudez]
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F98.5', 'Gagueira [tartamudez]', 'Gagueira',
    'Transtornos Mentais', 'Gagueira', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F98.5');

-- F98.6 - Fala precipitada
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F98.6', 'Fala precipitada', 'Fala precipitada',
    'Transtornos Mentais', 'Fala Precipitada', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F98.6');

-- F98.8 - Outros transtornos comportamentais e emocionais especificados com início habitualmente na infância ou na adolescência
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F98.8', 'Outros transtornos comportamentais e emocionais especificados com início habitualmente na infância ou na adolescência', 'Transtorno comportamental infantil',
    'Transtornos Mentais', 'Transtorno Comportamental', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F98.8');

-- F98.9 - Transtorno comportamental ou emocional não especificado com início habitualmente na infância ou na adolescência
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F98.9', 'Transtorno comportamental ou emocional não especificado com início habitualmente na infância ou na adolescência', 'Transtorno comportamental infantil',
    'Transtornos Mentais', 'Transtorno Comportamental', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F98.9');

-- F99 - Transtorno mental não especificado
INSERT INTO public.cid_doencas (
    id, criado_em, atualizado_em, ativo, codigo, descricao, descricao_abreviada,
    categoria, subcategoria, sexo_restricao, idade_minima, idade_maxima
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'F99', 'Transtorno mental não especificado', 'Transtorno mental',
    'Transtornos Mentais', 'Transtorno Mental', NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM public.cid_doencas WHERE codigo = 'F99');

