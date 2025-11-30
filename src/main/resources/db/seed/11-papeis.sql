-- Script de Seed: Papeis (Roles) do Sistema (Escopo Global)
-- Cria papéis/perfis de acesso do sistema - dados globais sem tenant
-- Executado quando app.seed.enabled=true

-- Administrador do Sistema
INSERT INTO public.papeis (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    slug,
    descricao,
    permissoes,
    papel_do_sistema
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Administrador do Sistema',
    'admin',
    'Acesso total ao sistema, incluindo gerenciamento de usuários, configurações e todos os módulos',
    '{"all": true}'::jsonb,
    true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'admin');

-- Médico
INSERT INTO public.papeis (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    slug,
    descricao,
    permissoes,
    papel_do_sistema
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Médico',
    'medico',
    'Acesso a módulos médicos: consultas, prescrições, prontuários, exames, atendimentos',
    '{"consultas": ["read", "write"], "prescricoes": ["read", "write"], "prontuarios": ["read", "write"], "exames": ["read", "write"], "atendimentos": ["read", "write"]}'::jsonb,
    true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'medico');

-- Enfermeiro
INSERT INTO public.papeis (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    slug,
    descricao,
    permissoes,
    papel_do_sistema
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Enfermeiro',
    'enfermeiro',
    'Acesso a módulos de enfermagem: procedimentos, vacinação, curativos, acompanhamento de pacientes',
    '{"procedimentos": ["read", "write"], "vacinacao": ["read", "write"], "pacientes": ["read"], "atendimentos": ["read", "write"]}'::jsonb,
    true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'enfermeiro');

-- Técnico de Enfermagem
INSERT INTO public.papeis (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    slug,
    descricao,
    permissoes,
    papel_do_sistema
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Técnico de Enfermagem',
    'tecnico_enfermagem',
    'Acesso a procedimentos de enfermagem básicos: curativos, medicações, vacinação sob supervisão',
    '{"procedimentos": ["read", "write"], "vacinacao": ["read", "write"], "pacientes": ["read"]}'::jsonb,
    true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'tecnico_enfermagem');

-- Recepcionista
INSERT INTO public.papeis (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    slug,
    descricao,
    permissoes,
    papel_do_sistema
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Recepcionista',
    'recepcionista',
    'Acesso a agendamentos, cadastro de pacientes, triagem inicial',
    '{"agendamentos": ["read", "write"], "pacientes": ["read", "write"], "triagem": ["read", "write"]}'::jsonb,
    true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'recepcionista');

-- Farmacêutico
INSERT INTO public.papeis (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    slug,
    descricao,
    permissoes,
    papel_do_sistema
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Farmacêutico',
    'farmaceutico',
    'Acesso a dispensação de medicamentos, gestão de farmácia, controle de estoque',
    '{"farmacia": ["read", "write"], "medicamentos": ["read", "write"], "dispensacao": ["read", "write"], "estoque": ["read", "write"]}'::jsonb,
    true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'farmaceutico');

-- Gerente de Unidade
INSERT INTO public.papeis (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    slug,
    descricao,
    permissoes,
    papel_do_sistema
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Gerente de Unidade',
    'gerente_unidade',
    'Acesso a gestão da unidade de saúde: relatórios, indicadores, gestão de equipes',
    '{"relatorios": ["read", "write"], "indicadores": ["read", "write"], "equipes": ["read", "write"], "configuracoes_unidade": ["read", "write"]}'::jsonb,
    true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'gerente_unidade');

-- Coordenador de Atenção Básica
INSERT INTO public.papeis (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    slug,
    descricao,
    permissoes,
    papel_do_sistema
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Coordenador de Atenção Básica',
    'coordenador_ab',
    'Acesso a coordenação da atenção básica: gestão de equipes ESF, monitoramento de indicadores, supervisão',
    '{"equipes_esf": ["read", "write"], "indicadores": ["read", "write"], "supervisao": ["read", "write"], "relatorios": ["read", "write"]}'::jsonb,
    true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'coordenador_ab');

-- Dentista
INSERT INTO public.papeis (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    slug,
    descricao,
    permissoes,
    papel_do_sistema
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Dentista',
    'dentista',
    'Acesso a módulos odontológicos: consultas odontológicas, procedimentos, prontuário odontológico',
    '{"consultas_odonto": ["read", "write"], "procedimentos_odonto": ["read", "write"], "prontuarios": ["read", "write"]}'::jsonb,
    true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'dentista');

-- Usuário Básico (Somente Leitura)
INSERT INTO public.papeis (
    id,
    criado_em,
    atualizado_em,
    ativo,
    nome,
    slug,
    descricao,
    permissoes,
    papel_do_sistema
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    true,
    'Usuário Básico',
    'usuario_basico',
    'Acesso somente leitura a informações básicas do sistema',
    '{"pacientes": ["read"], "agendamentos": ["read"]}'::jsonb,
    true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'usuario_basico');

