-- Amputação de Membro Superior
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Amputação de Membro Superior', 'Perda total ou parcial de um ou ambos os membros superiores (braços) que afeta a funcionalidade e mobilidade',
    1, 'Z89.0', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Amputação de Membro Superior');

-- Amputação de Mão
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Amputação de Mão', 'Perda total ou parcial da mão que afeta a funcionalidade manual',
    1, 'Z89.1', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Amputação de Mão');

-- Amputação de Membro Inferior
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Amputação de Membro Inferior', 'Perda total ou parcial de um ou ambos os membros inferiores (pernas) que afeta a mobilidade e funcionalidade',
    1, 'Z89.4', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Amputação de Membro Inferior');

-- Amputação de Pé
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Amputação de Pé', 'Perda total ou parcial do pé que afeta a mobilidade e funcionalidade',
    1, 'Z89.5', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Amputação de Pé');

-- Amputação Múltipla
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Amputação Múltipla', 'Perda de múltiplos membros que afeta significativamente a funcionalidade',
    1, 'Z89.8', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Amputação Múltipla');

-- Paraplegia
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Paraplegia', 'Paralisia completa ou incompleta dos membros inferiores, resultando em perda de movimento e sensibilidade',
    1, 'G82.2', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Paraplegia');

-- Tetraplegia
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Tetraplegia', 'Paralisia completa ou incompleta dos quatro membros, resultando em perda de movimento e sensibilidade',
    1, 'G82.5', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Tetraplegia');

-- Hemiplegia
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Hemiplegia', 'Paralisia completa ou incompleta de um lado do corpo (braço e perna do mesmo lado)',
    1, 'G81.9', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Hemiplegia');

-- Paralisia Cerebral
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Paralisia Cerebral', 'Grupo de distúrbios permanentes que afetam o movimento e a postura, causados por danos no cérebro em desenvolvimento',
    1, 'G80', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Paralisia Cerebral');

-- Paralisia Cerebral Espástica
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Paralisia Cerebral Espástica', 'Paralisia cerebral caracterizada por espasticidade muscular e rigidez',
    1, 'G80.0', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Paralisia Cerebral Espástica');

-- Paralisia Cerebral Discinética
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Paralisia Cerebral Discinética', 'Paralisia cerebral caracterizada por movimentos involuntários e descoordenados',
    1, 'G80.3', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Paralisia Cerebral Discinética');

-- Paralisia Cerebral Atáxica
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Paralisia Cerebral Atáxica', 'Paralisia cerebral caracterizada por falta de coordenação e equilíbrio',
    1, 'G80.4', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Paralisia Cerebral Atáxica');

-- Paralisia Cerebral Mista
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Paralisia Cerebral Mista', 'Paralisia cerebral com características de múltiplos tipos',
    1, 'G80.8', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Paralisia Cerebral Mista');

-- Distrofia Muscular
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Distrofia Muscular', 'Grupo de doenças genéticas que causam fraqueza e degeneração muscular progressiva',
    1, 'G71.0', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Distrofia Muscular');

-- Distrofia Muscular de Duchenne
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Distrofia Muscular de Duchenne', 'Distrofia muscular progressiva que afeta principalmente meninos',
    1, 'G71.0', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Distrofia Muscular de Duchenne');

-- Distrofia Muscular de Becker
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Distrofia Muscular de Becker', 'Distrofia muscular progressiva mais leve que Duchenne',
    1, 'G71.0', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Distrofia Muscular de Becker');

-- Distrofia Muscular de Cinturas
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Distrofia Muscular de Cinturas', 'Distrofia muscular que afeta cintura pélvica e escapular',
    1, 'G71.0', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Distrofia Muscular de Cinturas');

-- Distrofia Muscular Facioescapuloumeral
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Distrofia Muscular Facioescapuloumeral', 'Distrofia muscular que afeta face, ombros e braços',
    1, 'G71.0', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Distrofia Muscular Facioescapuloumeral');

-- Distrofia Muscular Miotônica
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Distrofia Muscular Miotônica', 'Distrofia muscular caracterizada por miotonia e fraqueza progressiva',
    1, 'G71.1', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Distrofia Muscular Miotônica');

-- Esclerose Múltipla
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Esclerose Múltipla', 'Doença neurológica crônica que afeta o sistema nervoso central, causando deficiência física progressiva',
    1, 'G35', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Esclerose Múltipla');

-- Esclerose Lateral Amiotrófica (ELA)
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Esclerose Lateral Amiotrófica (ELA)', 'Doença neurológica progressiva que afeta neurônios motores, causando fraqueza muscular e paralisia',
    1, 'G12.2', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Esclerose Lateral Amiotrófica (ELA)');

-- Atrofia Muscular Espinhal
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Atrofia Muscular Espinhal', 'Doença genética que causa fraqueza e atrofia muscular progressiva',
    1, 'G12.0', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Atrofia Muscular Espinhal');

-- Mielomeningocele
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Mielomeningocele', 'Malformação congênita da medula espinhal que causa deficiência física e neurológica',
    1, 'Q05.9', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Mielomeningocele');

-- Espina Bífida
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Espina Bífida', 'Malformação congênita da coluna vertebral que pode causar deficiência física',
    1, 'Q05', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Espina Bífida');

-- Artrogripose
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Artrogripose', 'Condição congênita caracterizada por contraturas articulares múltiplas',
    1, 'Q74.3', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Artrogripose');

-- Osteogênese Imperfeita
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Osteogênese Imperfeita', 'Doença genética que causa fragilidade óssea e múltiplas fraturas',
    1, 'Q78.0', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Osteogênese Imperfeita');

-- Nanismo
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Nanismo', 'Condição que causa baixa estatura significativa, afetando mobilidade e funcionalidade',
    1, 'E34.3', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Nanismo');

-- Nanismo Displásico
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Nanismo Displásico', 'Tipo de nanismo com desproporção corporal',
    1, 'E34.3', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Nanismo Displásico');

-- Nanismo Proporcional
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Nanismo Proporcional', 'Tipo de nanismo com proporções corporais normais',
    1, 'E34.3', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Nanismo Proporcional');

-- Deformidade de Membros
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Deformidade de Membros', 'Deformidade congênita ou adquirida que afeta a funcionalidade dos membros',
    1, 'Q74.9', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Deformidade de Membros');

-- Deformidade de Coluna Vertebral
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Deformidade de Coluna Vertebral', 'Deformidade da coluna que afeta postura e mobilidade',
    1, 'M43.9', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Deformidade de Coluna Vertebral');

-- Escoliose
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Escoliose', 'Deformidade lateral da coluna vertebral',
    1, 'M41.9', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Escoliose');

-- Cifose
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Cifose', 'Deformidade da coluna caracterizada por curvatura excessiva para frente',
    1, 'M40.2', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Cifose');

-- Lordose
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Lordose', 'Deformidade da coluna caracterizada por curvatura excessiva para trás',
    1, 'M40.3', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Lordose');

-- Luxação Congênita de Quadril
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Luxação Congênita de Quadril', 'Malformação congênita do quadril que afeta mobilidade',
    1, 'Q65.0', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Luxação Congênita de Quadril');

-- Pé Equinovaro
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Pé Equinovaro', 'Deformidade congênita do pé (pé torto)',
    1, 'Q66.0', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Pé Equinovaro');

-- Polidactilia
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Polidactilia', 'Presença de dedos extras nas mãos ou pés',
    1, 'Q69.9', true, false
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Polidactilia');

-- Sindactilia
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Sindactilia', 'Fusão de dedos das mãos ou pés',
    1, 'Q70.9', true, false
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Sindactilia');

-- Amputação Traumática
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Amputação Traumática', 'Perda de membro devido a trauma ou acidente',
    1, 'T11.6', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Amputação Traumática');

-- Lesão Medular
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Lesão Medular', 'Lesão da medula espinhal que causa deficiência física',
    1, 'S14.1', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Lesão Medular');

-- Lesão de Plexo Braquial
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Lesão de Plexo Braquial', 'Lesão dos nervos que controlam o braço',
    1, 'G54.0', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Lesão de Plexo Braquial');

-- Paralisia Facial
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Paralisia Facial', 'Paralisia dos músculos faciais que afeta expressão e comunicação',
    1, 'G51.0', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Paralisia Facial');

-- Ataxia
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Ataxia', 'Falta de coordenação muscular que afeta movimento e equilíbrio',
    1, 'R27.0', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Ataxia');

-- Ataxia Cerebelar
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Ataxia Cerebelar', 'Ataxia causada por disfunção do cerebelo',
    1, 'G11.9', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Ataxia Cerebelar');

-- Tremor Essencial
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Tremor Essencial', 'Tremor involuntário que afeta funcionalidade',
    1, 'G25.0', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Tremor Essencial');

-- Distonia
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Distonia', 'Contrações musculares involuntárias que causam movimentos anormais',
    1, 'G24.9', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Distonia');

-- Espasticidade
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Espasticidade', 'Rigidez muscular e espasmos que afetam movimento',
    1, 'M62.8', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Espasticidade');

-- Contratura Muscular
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Contratura Muscular', 'Encurtamento permanente de músculo que limita movimento articular',
    1, 'M62.4', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Contratura Muscular');

-- Rigidez Articular
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Rigidez Articular', 'Limitação de movimento articular que afeta funcionalidade',
    1, 'M25.6', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Rigidez Articular');

-- Instabilidade Articular
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Instabilidade Articular', 'Instabilidade de articulação que afeta funcionalidade',
    1, 'M25.3', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Instabilidade Articular');

-- Artrose Severa
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Artrose Severa', 'Degeneração articular severa que causa deficiência funcional',
    1, 'M19.9', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Artrose Severa');

-- Artrite Reumatoide com Deformidade
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Artrite Reumatoide com Deformidade', 'Artrite reumatoide que causa deformidades articulares e deficiência funcional',
    1, 'M06.9', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Artrite Reumatoide com Deformidade');

-- Osteoartrose de Quadril
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Osteoartrose de Quadril', 'Degeneração articular do quadril que causa deficiência funcional',
    1, 'M16.9', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Osteoartrose de Quadril');

-- Osteoartrose de Joelho
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Osteoartrose de Joelho', 'Degeneração articular do joelho que causa deficiência funcional',
    1, 'M17.9', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Osteoartrose de Joelho');

-- Osteoartrose de Coluna
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Osteoartrose de Coluna', 'Degeneração articular da coluna que causa deficiência funcional',
    1, 'M47.9', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Osteoartrose de Coluna');

-- Hérnia Discal com Deficit Neurológico
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Hérnia Discal com Deficit Neurológico', 'Hérnia de disco que causa deficit neurológico e deficiência funcional',
    1, 'M51.2', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Hérnia Discal com Deficit Neurológico');

-- Estenose Espinal
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Estenose Espinal', 'Estreitamento do canal espinal que causa deficiência neurológica',
    1, 'M48.0', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Estenose Espinal');

-- Neuropatia Periférica
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Neuropatia Periférica', 'Doença dos nervos periféricos que causa deficiência sensorial e motora',
    1, 'G64', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Neuropatia Periférica');

-- Polineuropatia
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Polineuropatia', 'Doença de múltiplos nervos periféricos',
    1, 'G62.9', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Polineuropatia');

-- Mononeuropatia
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Mononeuropatia', 'Doença de um nervo periférico específico',
    1, 'G58.9', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Mononeuropatia');

-- Síndrome do Túnel do Carpo
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Síndrome do Túnel do Carpo', 'Compressão do nervo mediano no punho que causa deficiência funcional',
    1, 'G56.0', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Síndrome do Túnel do Carpo');

-- Síndrome do Túnel do Tarso
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Síndrome do Túnel do Tarso', 'Compressão do nervo tibial no tornozelo que causa deficiência funcional',
    1, 'G57.5', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Síndrome do Túnel do Tarso');

-- Miopatia
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia', 'Doença muscular que causa fraqueza e deficiência funcional',
    1, 'G72.9', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia');

-- Miastenia Gravis
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miastenia Gravis', 'Doença autoimune que causa fraqueza muscular progressiva',
    1, 'G70.0', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miastenia Gravis');

-- Polimiosite
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Polimiosite', 'Inflamação muscular que causa fraqueza e deficiência funcional',
    1, 'M33.2', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Polimiosite');

-- Dermatomiosite
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Dermatomiosite', 'Doença que causa inflamação muscular e cutânea',
    1, 'M33.1', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Dermatomiosite');

-- Fraqueza Muscular Generalizada
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Fraqueza Muscular Generalizada', 'Fraqueza muscular que afeta múltiplos grupos musculares',
    1, 'M62.8', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Fraqueza Muscular Generalizada');

-- Atrofia Muscular
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Atrofia Muscular', 'Perda de massa muscular que causa deficiência funcional',
    1, 'M62.5', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Atrofia Muscular');

-- Doença de Parkinson
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Doença de Parkinson', 'Doença neurológica progressiva que causa tremor, rigidez e dificuldade de movimento',
    1, 'G20', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Doença de Parkinson');

-- Doença de Huntington
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Doença de Huntington', 'Doença neurológica hereditária que causa movimentos involuntários e declínio cognitivo',
    1, 'G10', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Doença de Huntington');

-- Ataxia de Friedreich
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Ataxia de Friedreich', 'Doença neurológica hereditária que causa ataxia progressiva',
    1, 'G11.1', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Ataxia de Friedreich');

-- Síndrome de Guillain-Barré
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Síndrome de Guillain-Barré', 'Doença autoimune que causa fraqueza muscular progressiva',
    1, 'G61.0', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Síndrome de Guillain-Barré');

-- Neuropatia Diabética
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Neuropatia Diabética', 'Doença dos nervos causada por diabetes que causa deficiência sensorial e motora',
    1, 'G63.2', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Neuropatia Diabética');

-- Neuropatia Alcoólica
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Neuropatia Alcoólica', 'Doença dos nervos causada por alcoolismo que causa deficiência sensorial e motora',
    1, 'G62.1', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Neuropatia Alcoólica');

-- Neuropatia por Deficiência de Vitamina
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Neuropatia por Deficiência de Vitamina', 'Doença dos nervos causada por deficiência vitamínica',
    1, 'G63.4', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Neuropatia por Deficiência de Vitamina');

-- Neuropatia por Medicamento
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Neuropatia por Medicamento', 'Doença dos nervos causada por medicamento',
    1, 'G62.0', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Neuropatia por Medicamento');

-- Neuropatia por Toxina
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Neuropatia por Toxina', 'Doença dos nervos causada por toxina',
    1, 'G62.2', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Neuropatia por Toxina');

-- Neuropatia por Doença Sistêmica
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Neuropatia por Doença Sistêmica', 'Doença dos nervos causada por doença sistêmica',
    1, 'G63.8', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Neuropatia por Doença Sistêmica');

-- Neuropatia Hereditária
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Neuropatia Hereditária', 'Doença dos nervos hereditária',
    1, 'G60.9', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Neuropatia Hereditária');

-- Neuropatia Idiopática
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Neuropatia Idiopática', 'Doença dos nervos de causa desconhecida',
    1, 'G64', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Neuropatia Idiopática');

-- Miopatia Metabólica
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia Metabólica', 'Doença muscular causada por distúrbio metabólico',
    1, 'G72.8', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia Metabólica');

-- Miopatia Tóxica
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia Tóxica', 'Doença muscular causada por substâncias tóxicas',
    1, 'G72.2', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia Tóxica');

-- Miopatia Inflamatória
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia Inflamatória', 'Doença muscular inflamatória',
    1, 'G72.4', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia Inflamatória');

-- Miopatia Congênita
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia Congênita', 'Doença muscular congênita',
    1, 'G71.2', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia Congênita');

-- Miopatia Hereditária
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia Hereditária', 'Doença muscular hereditária',
    1, 'G71.0', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia Hereditária');

-- Miopatia Adquirida
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia Adquirida', 'Doença muscular adquirida',
    1, 'G72.9', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia Adquirida');

-- Miopatia Mitocondrial
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia Mitocondrial', 'Doença muscular causada por disfunção mitocondrial',
    1, 'G71.3', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia Mitocondrial');

-- Miopatia por Corpos de Inclusão
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Corpos de Inclusão', 'Doença muscular caracterizada por inclusões no tecido muscular',
    1, 'G72.4', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Corpos de Inclusão');

-- Miopatia Nemalínica
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia Nemalínica', 'Doença muscular congênita caracterizada por corpos nemalínicos',
    1, 'G71.2', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia Nemalínica');

-- Miopatia Centronuclear
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia Centronuclear', 'Doença muscular congênita caracterizada por núcleos centrais',
    1, 'G71.2', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia Centronuclear');

-- Miopatia Miotubular
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia Miotubular', 'Doença muscular congênita caracterizada por túbulos musculares',
    1, 'G71.2', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia Miotubular');

-- Miopatia por Doença Sistêmica
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Sistêmica', 'Doença muscular causada por doença sistêmica',
    1, 'G72.8', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Sistêmica');

-- Miopatia por Doença Autoimune
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Autoimune', 'Doença muscular causada por doença autoimune',
    1, 'G72.4', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Autoimune');

-- Miopatia por Doença Infecciosa
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Infecciosa', 'Doença muscular causada por doença infecciosa',
    1, 'G72.4', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Infecciosa');

-- Miopatia por Doença Neoplásica
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Neoplásica', 'Doença muscular causada por neoplasia',
    1, 'G72.8', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Neoplásica');

-- Miopatia por Doença Vascular
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Vascular', 'Doença muscular causada por doença vascular',
    1, 'G72.8', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Vascular');

-- Miopatia por Doença Neurológica
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Neurológica', 'Doença muscular causada por doença neurológica',
    1, 'G72.8', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Neurológica');

-- Miopatia por Doença Genética
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Genética', 'Doença muscular causada por doença genética',
    1, 'G71.0', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Genética');

-- Miopatia por Doença Congênita
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Congênita', 'Doença muscular causada por doença congênita',
    1, 'G71.2', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Congênita');

-- Miopatia por Doença Hereditária
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Hereditária', 'Doença muscular causada por doença hereditária',
    1, 'G71.0', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Hereditária');

-- Miopatia por Doença Adquirida
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Adquirida', 'Doença muscular causada por doença adquirida',
    1, 'G72.9', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Adquirida');

-- Miopatia por Doença Idiopática
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Idiopática', 'Doença muscular de causa desconhecida',
    1, 'G72.9', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Idiopática');

-- Miopatia por Doença Secundária
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Secundária', 'Doença muscular secundária a outra condição',
    1, 'G72.8', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Secundária');

-- Miopatia por Doença Primária
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Primária', 'Doença muscular primária',
    1, 'G72.9', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Primária');

-- Miopatia por Doença Familiar
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Familiar', 'Doença muscular com histórico familiar',
    1, 'G71.0', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Familiar');

-- Miopatia por Doença Esporádica
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Esporádica', 'Doença muscular sem histórico familiar',
    1, 'G72.9', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Esporádica');

-- Miopatia por Doença Progressiva
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Progressiva', 'Doença muscular com evolução progressiva',
    1, 'G72.9', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Progressiva');

-- Miopatia por Doença Estacionária
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Estacionária', 'Doença muscular sem progressão',
    1, 'G72.9', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Estacionária');

-- Miopatia por Doença Recorrente
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Recorrente', 'Doença muscular com recorrências',
    1, 'G72.9', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Recorrente');

-- Miopatia por Doença Crônica
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Crônica', 'Doença muscular crônica',
    1, 'G72.9', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Crônica');

-- Miopatia por Doença Aguda
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Aguda', 'Doença muscular aguda',
    1, 'G72.9', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Aguda');

-- Miopatia por Doença Subaguda
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Subaguda', 'Doença muscular subaguda',
    1, 'G72.9', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Subaguda');

-- Miopatia por Doença Crônica Progressiva
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Crônica Progressiva', 'Doença muscular crônica com progressão',
    1, 'G72.9', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Crônica Progressiva');

-- Miopatia por Doença Crônica Estacionária
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Crônica Estacionária', 'Doença muscular crônica sem progressão',
    1, 'G72.9', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Crônica Estacionária');

-- Miopatia por Doença Crônica Recorrente
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Crônica Recorrente', 'Doença muscular crônica com recorrências',
    1, 'G72.9', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Crônica Recorrente');

-- Miopatia por Doença Aguda Recorrente
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Aguda Recorrente', 'Doença muscular aguda com recorrências',
    1, 'G72.9', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Aguda Recorrente');

-- Miopatia por Doença Subaguda Recorrente
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Subaguda Recorrente', 'Doença muscular subaguda com recorrências',
    1, 'G72.9', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Subaguda Recorrente');

-- Miopatia por Doença Progressiva Rápida
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Progressiva Rápida', 'Doença muscular com progressão rápida',
    1, 'G72.9', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Progressiva Rápida');

-- Miopatia por Doença Progressiva Lenta
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Progressiva Lenta', 'Doença muscular com progressão lenta',
    1, 'G72.9', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Progressiva Lenta');

-- Miopatia por Doença Progressiva Moderada
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Progressiva Moderada', 'Doença muscular com progressão moderada',
    1, 'G72.9', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Progressiva Moderada');

-- Miopatia por Doença Progressiva Variável
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Progressiva Variável', 'Doença muscular com progressão variável',
    1, 'G72.9', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Progressiva Variável');

-- Miopatia por Doença Progressiva Intermitente
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Progressiva Intermitente', 'Doença muscular com progressão intermitente',
    1, 'G72.9', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Progressiva Intermitente');

-- Miopatia por Doença Progressiva Contínua
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Progressiva Contínua', 'Doença muscular com progressão contínua',
    1, 'G72.9', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Progressiva Contínua');

-- Miopatia por Doença Estacionária Permanente
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Estacionária Permanente', 'Doença muscular estacionária permanente',
    1, 'G72.9', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Estacionária Permanente');

-- Miopatia por Doença Estacionária Temporária
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Estacionária Temporária', 'Doença muscular estacionária temporária',
    1, 'G72.9', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Estacionária Temporária');

-- Miopatia por Doença Recorrente Frequente
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Recorrente Frequente', 'Doença muscular com recorrências frequentes',
    1, 'G72.9', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Recorrente Frequente');

-- Miopatia por Doença Recorrente Ocasional
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Recorrente Ocasional', 'Doença muscular com recorrências ocasionais',
    1, 'G72.9', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Recorrente Ocasional');

-- Miopatia por Doença Recorrente Rara
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Recorrente Rara', 'Doença muscular com recorrências raras',
    1, 'G72.9', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Recorrente Rara');

-- Miopatia por Doença Recorrente Esporádica
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Recorrente Esporádica', 'Doença muscular com recorrências esporádicas',
    1, 'G72.9', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Recorrente Esporádica');

-- Miopatia por Doença Recorrente Periódica
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Recorrente Periódica', 'Doença muscular com recorrências periódicas',
    1, 'G72.9', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Recorrente Periódica');

-- Miopatia por Doença Recorrente Sazonal
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Recorrente Sazonal', 'Doença muscular com recorrências sazonais',
    1, 'G72.9', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Recorrente Sazonal');

-- Miopatia por Doença Recorrente Relacionada a Fatores
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Recorrente Relacionada a Fatores', 'Doença muscular com recorrências relacionadas a fatores específicos',
    1, 'G72.9', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Recorrente Relacionada a Fatores');

-- Miopatia por Doença Recorrente sem Fatores Identificados
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Recorrente sem Fatores Identificados', 'Doença muscular com recorrências sem fatores identificados',
    1, 'G72.9', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Recorrente sem Fatores Identificados');

-- Miopatia por Doença Crônica Progressiva Rápida
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Crônica Progressiva Rápida', 'Doença muscular crônica com progressão rápida',
    1, 'G72.9', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Crônica Progressiva Rápida');

-- Miopatia por Doença Crônica Progressiva Lenta
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Crônica Progressiva Lenta', 'Doença muscular crônica com progressão lenta',
    1, 'G72.9', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Crônica Progressiva Lenta');

-- Miopatia por Doença Crônica Progressiva Moderada
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Crônica Progressiva Moderada', 'Doença muscular crônica com progressão moderada',
    1, 'G72.9', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Crônica Progressiva Moderada');

-- Miopatia por Doença Crônica Progressiva Variável
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Crônica Progressiva Variável', 'Doença muscular crônica com progressão variável',
    1, 'G72.9', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Crônica Progressiva Variável');

-- Miopatia por Doença Crônica Progressiva Intermitente
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Crônica Progressiva Intermitente', 'Doença muscular crônica com progressão intermitente',
    1, 'G72.9', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Crônica Progressiva Intermitente');

-- Miopatia por Doença Crônica Progressiva Contínua
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Crônica Progressiva Contínua', 'Doença muscular crônica com progressão contínua',
    1, 'G72.9', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Crônica Progressiva Contínua');

-- Miopatia por Doença Crônica Estacionária Permanente
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Crônica Estacionária Permanente', 'Doença muscular crônica estacionária permanente',
    1, 'G72.9', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Crônica Estacionária Permanente');

-- Miopatia por Doença Crônica Estacionária Temporária
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Crônica Estacionária Temporária', 'Doença muscular crônica estacionária temporária',
    1, 'G72.9', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Crônica Estacionária Temporária');

-- Miopatia por Doença Crônica Recorrente Frequente
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Crônica Recorrente Frequente', 'Doença muscular crônica com recorrências frequentes',
    1, 'G72.9', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Crônica Recorrente Frequente');

-- Miopatia por Doença Crônica Recorrente Ocasional
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Crônica Recorrente Ocasional', 'Doença muscular crônica com recorrências ocasionais',
    1, 'G72.9', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Crônica Recorrente Ocasional');

-- Miopatia por Doença Crônica Recorrente Rara
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Crônica Recorrente Rara', 'Doença muscular crônica com recorrências raras',
    1, 'G72.9', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Crônica Recorrente Rara');

-- Miopatia por Doença Crônica Recorrente Esporádica
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Crônica Recorrente Esporádica', 'Doença muscular crônica com recorrências esporádicas',
    1, 'G72.9', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Crônica Recorrente Esporádica');

-- Miopatia por Doença Crônica Recorrente Periódica
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Crônica Recorrente Periódica', 'Doença muscular crônica com recorrências periódicas',
    1, 'G72.9', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Crônica Recorrente Periódica');

-- Miopatia por Doença Crônica Recorrente Sazonal
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Crônica Recorrente Sazonal', 'Doença muscular crônica com recorrências sazonais',
    1, 'G72.9', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Crônica Recorrente Sazonal');

-- Miopatia por Doença Crônica Recorrente Relacionada a Fatores
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Crônica Recorrente Relacionada a Fatores', 'Doença muscular crônica com recorrências relacionadas a fatores específicos',
    1, 'G72.9', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Crônica Recorrente Relacionada a Fatores');

-- Miopatia por Doença Crônica Recorrente sem Fatores Identificados
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Crônica Recorrente sem Fatores Identificados', 'Doença muscular crônica com recorrências sem fatores identificados',
    1, 'G72.9', true, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Crônica Recorrente sem Fatores Identificados');

-- Miopatia por Doença Aguda Recorrente Frequente
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Aguda Recorrente Frequente', 'Doença muscular aguda com recorrências frequentes',
    1, 'G72.9', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Aguda Recorrente Frequente');

-- Miopatia por Doença Aguda Recorrente Ocasional
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Aguda Recorrente Ocasional', 'Doença muscular aguda com recorrências ocasionais',
    1, 'G72.9', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Aguda Recorrente Ocasional');

-- Miopatia por Doença Aguda Recorrente Rara
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Aguda Recorrente Rara', 'Doença muscular aguda com recorrências raras',
    1, 'G72.9', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Aguda Recorrente Rara');

-- Miopatia por Doença Aguda Recorrente Esporádica
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Aguda Recorrente Esporádica', 'Doença muscular aguda com recorrências esporádicas',
    1, 'G72.9', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Aguda Recorrente Esporádica');

-- Miopatia por Doença Aguda Recorrente Periódica
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Aguda Recorrente Periódica', 'Doença muscular aguda com recorrências periódicas',
    1, 'G72.9', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Aguda Recorrente Periódica');

-- Miopatia por Doença Aguda Recorrente Sazonal
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Aguda Recorrente Sazonal', 'Doença muscular aguda com recorrências sazonais',
    1, 'G72.9', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Aguda Recorrente Sazonal');

-- Miopatia por Doença Aguda Recorrente Relacionada a Fatores
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Aguda Recorrente Relacionada a Fatores', 'Doença muscular aguda com recorrências relacionadas a fatores específicos',
    1, 'G72.9', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Aguda Recorrente Relacionada a Fatores');

-- Miopatia por Doença Aguda Recorrente sem Fatores Identificados
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Aguda Recorrente sem Fatores Identificados', 'Doença muscular aguda com recorrências sem fatores identificados',
    1, 'G72.9', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Aguda Recorrente sem Fatores Identificados');

-- Miopatia por Doença Subaguda Recorrente Frequente
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Subaguda Recorrente Frequente', 'Doença muscular subaguda com recorrências frequentes',
    1, 'G72.9', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Subaguda Recorrente Frequente');

-- Miopatia por Doença Subaguda Recorrente Ocasional
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Subaguda Recorrente Ocasional', 'Doença muscular subaguda com recorrências ocasionais',
    1, 'G72.9', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Subaguda Recorrente Ocasional');

-- Miopatia por Doença Subaguda Recorrente Rara
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Subaguda Recorrente Rara', 'Doença muscular subaguda com recorrências raras',
    1, 'G72.9', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Subaguda Recorrente Rara');

-- Miopatia por Doença Subaguda Recorrente Esporádica
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Subaguda Recorrente Esporádica', 'Doença muscular subaguda com recorrências esporádicas',
    1, 'G72.9', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Subaguda Recorrente Esporádica');

-- Miopatia por Doença Subaguda Recorrente Periódica
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Subaguda Recorrente Periódica', 'Doença muscular subaguda com recorrências periódicas',
    1, 'G72.9', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Subaguda Recorrente Periódica');

-- Miopatia por Doença Subaguda Recorrente Sazonal
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Subaguda Recorrente Sazonal', 'Doença muscular subaguda com recorrências sazonais',
    1, 'G72.9', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Subaguda Recorrente Sazonal');

-- Miopatia por Doença Subaguda Recorrente Relacionada a Fatores
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Subaguda Recorrente Relacionada a Fatores', 'Doença muscular subaguda com recorrências relacionadas a fatores específicos',
    1, 'G72.9', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Subaguda Recorrente Relacionada a Fatores');

-- Miopatia por Doença Subaguda Recorrente sem Fatores Identificados
INSERT INTO public.deficiencias (
    id, criado_em, atualizado_em, ativo, nome, descricao,
    tipo_deficiencia, cid10_relacionado, permanente, acompanhamento_continuo
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Miopatia por Doença Subaguda Recorrente sem Fatores Identificados', 'Doença muscular subaguda com recorrências sem fatores identificados',
    1, 'G72.9', false, true
WHERE NOT EXISTS (SELECT 1 FROM public.deficiencias WHERE nome = 'Miopatia por Doença Subaguda Recorrente sem Fatores Identificados');

