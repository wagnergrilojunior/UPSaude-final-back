-- Script de Seed: Vínculos Profissional-Equipe
-- Cria vínculos entre profissionais e equipes de saúde
-- Depende de: Tenant, ProfissionaisSaude, EquipeSaude
-- Executado quando app.seed.enabled=true

-- Vínculo: Dr. João Silva Santos - Equipe ESF UBS Centro
INSERT INTO public.vinculos_profissional_equipe (
    id,
    criado_em,
    atualizado_em,
    tenant_id,
    estabelecimento_id,
    ativo,
    profissional_id,
    equipe_id,
    data_inicio,
    tipo_vinculo,
    funcao_equipe,
    carga_horaria_semanal,
    status,
    observacoes
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    t.id,
    e.id,
    true,
    ps.id,
    eq.id,
    NOW() - INTERVAL '5 years',
    1,
    'Médico da Equipe',
    40,
    1,
    'Médico da Equipe de Saúde da Família - UBS Centro'
FROM public.tenants t, public.estabelecimentos e, public.profissionais_saude ps, public.equipes_saude eq
WHERE t.slug = 'prefeitura-santa-rita-do-sapucai'
  AND e.codigo_cnes = '7101234'
  AND e.tenant_id = t.id
  AND ps.cpf = '12345678901'
  AND ps.tenant_id = t.id
  AND eq.ine = '000000000000001'
  AND eq.estabelecimento_id = e.id
  AND NOT EXISTS (SELECT 1 FROM public.vinculos_profissional_equipe vpe WHERE vpe.profissional_id = ps.id AND vpe.equipe_id = eq.id);

-- Vínculo: Enf. Maria Oliveira Costa - Equipe ESF UBS Centro
INSERT INTO public.vinculos_profissional_equipe (
    id,
    criado_em,
    atualizado_em,
    tenant_id,
    estabelecimento_id,
    ativo,
    profissional_id,
    equipe_id,
    data_inicio,
    tipo_vinculo,
    funcao_equipe,
    carga_horaria_semanal,
    status,
    observacoes
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    t.id,
    e.id,
    true,
    ps.id,
    eq.id,
    NOW() - INTERVAL '4 years',
    1,
    'Enfermeira Coordenadora da Equipe',
    40,
    1,
    'Enfermeira coordenadora da Equipe de Saúde da Família - UBS Centro'
FROM public.tenants t, public.estabelecimentos e, public.profissionais_saude ps, public.equipes_saude eq
WHERE t.slug = 'prefeitura-santa-rita-do-sapucai'
  AND e.codigo_cnes = '7101234'
  AND e.tenant_id = t.id
  AND ps.cpf = '23456789012'
  AND ps.tenant_id = t.id
  AND eq.ine = '000000000000001'
  AND eq.estabelecimento_id = e.id
  AND NOT EXISTS (SELECT 1 FROM public.vinculos_profissional_equipe vpe WHERE vpe.profissional_id = ps.id AND vpe.equipe_id = eq.id);

-- Vínculo: Técnica Ana Paula Ferreira - Equipe ESF UBS Centro
INSERT INTO public.vinculos_profissional_equipe (
    id,
    criado_em,
    atualizado_em,
    tenant_id,
    estabelecimento_id,
    ativo,
    profissional_id,
    equipe_id,
    data_inicio,
    tipo_vinculo,
    funcao_equipe,
    carga_horaria_semanal,
    status,
    observacoes
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    t.id,
    e.id,
    true,
    ps.id,
    eq.id,
    NOW() - INTERVAL '3 years',
    1,
    'Técnica de Enfermagem',
    40,
    1,
    'Técnica de enfermagem da Equipe de Saúde da Família - UBS Centro'
FROM public.tenants t, public.estabelecimentos e, public.profissionais_saude ps, public.equipes_saude eq
WHERE t.slug = 'prefeitura-santa-rita-do-sapucai'
  AND e.codigo_cnes = '7101234'
  AND e.tenant_id = t.id
  AND ps.cpf = '34567890123'
  AND ps.tenant_id = t.id
  AND eq.ine = '000000000000001'
  AND eq.estabelecimento_id = e.id
  AND NOT EXISTS (SELECT 1 FROM public.vinculos_profissional_equipe vpe WHERE vpe.profissional_id = ps.id AND vpe.equipe_id = eq.id);

-- Vínculo: Dra. Juliana Rodrigues Lima - Equipe ESF UBS Centro
INSERT INTO public.vinculos_profissional_equipe (
    id,
    criado_em,
    atualizado_em,
    tenant_id,
    estabelecimento_id,
    ativo,
    profissional_id,
    equipe_id,
    data_inicio,
    tipo_vinculo,
    funcao_equipe,
    carga_horaria_semanal,
    status,
    observacoes
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    t.id,
    e.id,
    true,
    ps.id,
    eq.id,
    NOW() - INTERVAL '6 years',
    1,
    'Médica Pediatra da Equipe',
    30,
    1,
    'Médica pediatra da Equipe de Saúde da Família - UBS Centro'
FROM public.tenants t, public.estabelecimentos e, public.profissionais_saude ps, public.equipes_saude eq
WHERE t.slug = 'prefeitura-santa-rita-do-sapucai'
  AND e.codigo_cnes = '7101234'
  AND e.tenant_id = t.id
  AND ps.cpf = '45678901234'
  AND ps.tenant_id = t.id
  AND eq.ine = '000000000000001'
  AND eq.estabelecimento_id = e.id
  AND NOT EXISTS (SELECT 1 FROM public.vinculos_profissional_equipe vpe WHERE vpe.profissional_id = ps.id AND vpe.equipe_id = eq.id);

-- Vínculo: Dr. Carlos Alberto Souza - Equipe Médica UPA
INSERT INTO public.vinculos_profissional_equipe (
    id,
    criado_em,
    atualizado_em,
    tenant_id,
    estabelecimento_id,
    ativo,
    profissional_id,
    equipe_id,
    data_inicio,
    tipo_vinculo,
    funcao_equipe,
    carga_horaria_semanal,
    status,
    observacoes
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    t.id,
    e.id,
    true,
    ps.id,
    eq.id,
    NOW() - INTERVAL '4 years',
    1,
    'Médico Plantonista',
    40,
    1,
    'Médico plantonista da Equipe Médica - UPA 24 Horas'
FROM public.tenants t, public.estabelecimentos e, public.profissionais_saude ps, public.equipes_saude eq
WHERE t.slug = 'prefeitura-santa-rita-do-sapucai'
  AND e.codigo_cnes = '7101235'
  AND e.tenant_id = t.id
  AND ps.cpf = '56789012345'
  AND ps.tenant_id = t.id
  AND eq.ine = '000000000000004'
  AND eq.estabelecimento_id = e.id
  AND NOT EXISTS (SELECT 1 FROM public.vinculos_profissional_equipe vpe WHERE vpe.profissional_id = ps.id AND vpe.equipe_id = eq.id);

-- Vínculo: Enf. Patricia Almeida - Equipe de Urgência UPA
INSERT INTO public.vinculos_profissional_equipe (
    id,
    criado_em,
    atualizado_em,
    tenant_id,
    estabelecimento_id,
    ativo,
    profissional_id,
    equipe_id,
    data_inicio,
    tipo_vinculo,
    funcao_equipe,
    carga_horaria_semanal,
    status,
    observacoes
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    t.id,
    e.id,
    true,
    ps.id,
    eq.id,
    NOW() - INTERVAL '3 years',
    1,
    'Enfermeira da Equipe de Urgência',
    36,
    1,
    'Enfermeira da Equipe de Urgência - UPA 24 Horas'
FROM public.tenants t, public.estabelecimentos e, public.profissionais_saude ps, public.equipes_saude eq
WHERE t.slug = 'prefeitura-santa-rita-do-sapucai'
  AND e.codigo_cnes = '7101235'
  AND e.tenant_id = t.id
  AND ps.cpf = '67890123456'
  AND ps.tenant_id = t.id
  AND eq.ine = '000000000000005'
  AND eq.estabelecimento_id = e.id
  AND NOT EXISTS (SELECT 1 FROM public.vinculos_profissional_equipe vpe WHERE vpe.profissional_id = ps.id AND vpe.equipe_id = eq.id);

-- Vínculo: Dr. Roberto Mendes Pereira - Equipe ESF UBS Novo Horizonte
INSERT INTO public.vinculos_profissional_equipe (
    id,
    criado_em,
    atualizado_em,
    tenant_id,
    estabelecimento_id,
    ativo,
    profissional_id,
    equipe_id,
    data_inicio,
    tipo_vinculo,
    funcao_equipe,
    carga_horaria_semanal,
    status,
    observacoes
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    t.id,
    e.id,
    true,
    ps.id,
    eq.id,
    NOW() - INTERVAL '2 years',
    1,
    'Médico da Equipe',
    40,
    1,
    'Médico da Equipe de Saúde da Família - UBS Novo Horizonte'
FROM public.tenants t, public.estabelecimentos e, public.profissionais_saude ps, public.equipes_saude eq
WHERE t.slug = 'prefeitura-santa-rita-do-sapucai'
  AND e.codigo_cnes = '7101238'
  AND e.tenant_id = t.id
  AND ps.cpf = '78901234567'
  AND ps.tenant_id = t.id
  AND eq.ine = '000000000000002'
  AND eq.estabelecimento_id = e.id
  AND NOT EXISTS (SELECT 1 FROM public.vinculos_profissional_equipe vpe WHERE vpe.profissional_id = ps.id AND vpe.equipe_id = eq.id);

