-- Script de Seed: Vínculos Profissional-Estabelecimento
-- Cria vínculos entre profissionais e estabelecimentos
-- Depende de: Tenant, ProfissionaisSaude, Estabelecimentos
-- Executado quando app.seed.enabled=true

-- Vínculo: Dr. João Silva Santos - UBS Centro
INSERT INTO public.profissionais_estabelecimentos (
    id,
    criado_em,
    atualizado_em,
    tenant_id,
    estabelecimento_id,
    ativo,
    profissional_id,
    data_inicio,
    tipo_vinculo,
    carga_horaria_semanal,
    numero_matricula,
    setor_departamento,
    cargo_funcao,
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
    NOW() - INTERVAL '5 years',
    1,
    40,
    'MAT-001',
    'Atenção Básica',
    'Médico Clínico Geral',
    'Vínculo efetivo, médico responsável pelo atendimento na UBS Centro'
FROM public.tenants t, public.estabelecimentos e, public.profissionais_saude ps
WHERE t.slug = 'prefeitura-santa-rita-do-sapucai'
  AND e.codigo_cnes = '7101234'
  AND e.tenant_id = t.id
  AND ps.cpf = '12345678901'
  AND ps.tenant_id = t.id
  AND NOT EXISTS (SELECT 1 FROM public.profissionais_estabelecimentos pe WHERE pe.profissional_id = ps.id AND pe.estabelecimento_id = e.id);

-- Vínculo: Enf. Maria Oliveira Costa - UBS Centro
INSERT INTO public.profissionais_estabelecimentos (
    id,
    criado_em,
    atualizado_em,
    tenant_id,
    estabelecimento_id,
    ativo,
    profissional_id,
    data_inicio,
    tipo_vinculo,
    carga_horaria_semanal,
    numero_matricula,
    setor_departamento,
    cargo_funcao,
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
    NOW() - INTERVAL '4 years',
    1,
    40,
    'MAT-002',
    'Atenção Básica',
    'Enfermeira Coordenadora',
    'Vínculo efetivo, enfermeira coordenadora da UBS Centro'
FROM public.tenants t, public.estabelecimentos e, public.profissionais_saude ps
WHERE t.slug = 'prefeitura-santa-rita-do-sapucai'
  AND e.codigo_cnes = '7101234'
  AND e.tenant_id = t.id
  AND ps.cpf = '23456789012'
  AND ps.tenant_id = t.id
  AND NOT EXISTS (SELECT 1 FROM public.profissionais_estabelecimentos pe WHERE pe.profissional_id = ps.id AND pe.estabelecimento_id = e.id);

-- Vínculo: Técnica Ana Paula Ferreira - UBS Centro
INSERT INTO public.profissionais_estabelecimentos (
    id,
    criado_em,
    atualizado_em,
    tenant_id,
    estabelecimento_id,
    ativo,
    profissional_id,
    data_inicio,
    tipo_vinculo,
    carga_horaria_semanal,
    numero_matricula,
    setor_departamento,
    cargo_funcao,
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
    NOW() - INTERVAL '3 years',
    1,
    40,
    'MAT-003',
    'Atenção Básica',
    'Técnica de Enfermagem',
    'Vínculo efetivo, técnica de enfermagem da UBS Centro'
FROM public.tenants t, public.estabelecimentos e, public.profissionais_saude ps
WHERE t.slug = 'prefeitura-santa-rita-do-sapucai'
  AND e.codigo_cnes = '7101234'
  AND e.tenant_id = t.id
  AND ps.cpf = '34567890123'
  AND ps.tenant_id = t.id
  AND NOT EXISTS (SELECT 1 FROM public.profissionais_estabelecimentos pe WHERE pe.profissional_id = ps.id AND pe.estabelecimento_id = e.id);

-- Vínculo: Dra. Juliana Rodrigues Lima - UBS Centro
INSERT INTO public.profissionais_estabelecimentos (
    id,
    criado_em,
    atualizado_em,
    tenant_id,
    estabelecimento_id,
    ativo,
    profissional_id,
    data_inicio,
    tipo_vinculo,
    carga_horaria_semanal,
    numero_matricula,
    setor_departamento,
    cargo_funcao,
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
    NOW() - INTERVAL '6 years',
    1,
    30,
    'MAT-004',
    'Atenção Básica',
    'Médica Pediatra',
    'Vínculo efetivo, médica pediatra da UBS Centro'
FROM public.tenants t, public.estabelecimentos e, public.profissionais_saude ps
WHERE t.slug = 'prefeitura-santa-rita-do-sapucai'
  AND e.codigo_cnes = '7101234'
  AND e.tenant_id = t.id
  AND ps.cpf = '45678901234'
  AND ps.tenant_id = t.id
  AND NOT EXISTS (SELECT 1 FROM public.profissionais_estabelecimentos pe WHERE pe.profissional_id = ps.id AND pe.estabelecimento_id = e.id);

-- Vínculo: Dr. Carlos Alberto Souza - UPA 24 Horas
INSERT INTO public.profissionais_estabelecimentos (
    id,
    criado_em,
    atualizado_em,
    tenant_id,
    estabelecimento_id,
    ativo,
    profissional_id,
    data_inicio,
    tipo_vinculo,
    carga_horaria_semanal,
    numero_matricula,
    setor_departamento,
    cargo_funcao,
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
    NOW() - INTERVAL '4 years',
    1,
    40,
    'MAT-005',
    'Urgência e Emergência',
    'Médico Plantonista',
    'Vínculo efetivo, médico plantonista da UPA 24 Horas'
FROM public.tenants t, public.estabelecimentos e, public.profissionais_saude ps
WHERE t.slug = 'prefeitura-santa-rita-do-sapucai'
  AND e.codigo_cnes = '7101235'
  AND e.tenant_id = t.id
  AND ps.cpf = '56789012345'
  AND ps.tenant_id = t.id
  AND NOT EXISTS (SELECT 1 FROM public.profissionais_estabelecimentos pe WHERE pe.profissional_id = ps.id AND pe.estabelecimento_id = e.id);

-- Vínculo: Enf. Patricia Almeida - UPA 24 Horas
INSERT INTO public.profissionais_estabelecimentos (
    id,
    criado_em,
    atualizado_em,
    tenant_id,
    estabelecimento_id,
    ativo,
    profissional_id,
    data_inicio,
    tipo_vinculo,
    carga_horaria_semanal,
    numero_matricula,
    setor_departamento,
    cargo_funcao,
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
    NOW() - INTERVAL '3 years',
    1,
    36,
    'MAT-006',
    'Urgência e Emergência',
    'Enfermeira Plantonista',
    'Vínculo efetivo, enfermeira plantonista da UPA 24 Horas'
FROM public.tenants t, public.estabelecimentos e, public.profissionais_saude ps
WHERE t.slug = 'prefeitura-santa-rita-do-sapucai'
  AND e.codigo_cnes = '7101235'
  AND e.tenant_id = t.id
  AND ps.cpf = '67890123456'
  AND ps.tenant_id = t.id
  AND NOT EXISTS (SELECT 1 FROM public.profissionais_estabelecimentos pe WHERE pe.profissional_id = ps.id AND pe.estabelecimento_id = e.id);

-- Vínculo: Dr. Roberto Mendes Pereira - UBS Novo Horizonte
INSERT INTO public.profissionais_estabelecimentos (
    id,
    criado_em,
    atualizado_em,
    tenant_id,
    estabelecimento_id,
    ativo,
    profissional_id,
    data_inicio,
    tipo_vinculo,
    carga_horaria_semanal,
    numero_matricula,
    setor_departamento,
    cargo_funcao,
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
    NOW() - INTERVAL '2 years',
    1,
    40,
    'MAT-007',
    'Atenção Básica',
    'Médico Clínico Geral',
    'Vínculo efetivo, médico clínico geral da UBS Novo Horizonte'
FROM public.tenants t, public.estabelecimentos e, public.profissionais_saude ps
WHERE t.slug = 'prefeitura-santa-rita-do-sapucai'
  AND e.codigo_cnes = '7101238'
  AND e.tenant_id = t.id
  AND ps.cpf = '78901234567'
  AND ps.tenant_id = t.id
  AND NOT EXISTS (SELECT 1 FROM public.profissionais_estabelecimentos pe WHERE pe.profissional_id = ps.id AND pe.estabelecimento_id = e.id);

