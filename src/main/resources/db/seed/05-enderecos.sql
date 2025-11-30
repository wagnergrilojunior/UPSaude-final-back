-- Script de Seed: Endereços
-- Cria endereços para tenant e estabelecimentos
-- Executado quando app.seed.enabled=true

-- Endereço da Prefeitura (Tenant)
WITH tenant_id AS (
    SELECT id FROM public.tenants WHERE slug = 'prefeitura-santa-rita-do-sapucai' LIMIT 1
),
cidade_id AS (
    SELECT id FROM public.cidades WHERE codigo_ibge = '3543204' LIMIT 1
),
endereco_tenant AS (
    INSERT INTO public.enderecos (
        id,
        criado_em,
        atualizado_em,
        tenant_id,
        estabelecimento_id,
        ativo,
        tipo_logradouro,
        logradouro,
        numero,
        complemento,
        bairro,
        cep,
        pais,
        distrito,
        ponto_referencia,
        sem_numero,
        latitude,
        longitude,
        tipo_endereco,
        zona,
        codigo_ibge_municipio,
        cidade_id
    )
    SELECT 
        gen_random_uuid(),
        NOW(),
        NOW(),
        t.id,
        NULL,
        true,
        'RUA',
        'Rua Monsenhor José Paulino',
        '123',
        'Centro',
        'Centro',
        '37540000',
        'Brasil',
        'Centro',
        'Próximo à Praça da Matriz',
        false,
        -22.2511,
        -45.7056,
        'COMERCIAL',
        'URBANA',
        '3543204',
        c.id
    FROM tenant_id t, cidade_id c
    WHERE NOT EXISTS (
        SELECT 1 FROM public.enderecos e 
        JOIN public.tenants_enderecos te ON te.endereco_id = e.id
        WHERE te.tenant_id = t.id
    )
    RETURNING id
)
INSERT INTO public.tenants_enderecos (tenant_id, endereco_id)
SELECT 
    (SELECT id FROM tenant_id),
    (SELECT id FROM endereco_tenant)
WHERE EXISTS (SELECT 1 FROM endereco_tenant);

-- Endereços dos Estabelecimentos
-- UBS Centro
INSERT INTO public.enderecos (
    id,
    criado_em,
    atualizado_em,
    tenant_id,
    estabelecimento_id,
    ativo,
    tipo_logradouro,
    logradouro,
    numero,
    complemento,
    bairro,
    cep,
    pais,
    distrito,
    sem_numero,
    latitude,
    longitude,
    tipo_endereco,
    zona,
    codigo_ibge_municipio,
    cidade_id
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    t.id,
    e.id,
    true,
    'RUA',
    'Rua Barão do Rio Branco',
    '456',
    NULL,
    'Centro',
    '37540000',
    'Brasil',
    'Centro',
    false,
    -22.2520,
    -45.7060,
    'COMERCIAL',
    'URBANA',
    '3543204',
    c.id
FROM public.tenants t, public.estabelecimentos e, public.cidades c
WHERE t.slug = 'prefeitura-santa-rita-do-sapucai'
  AND e.nome = 'UBS Centro - Santa Rita do Sapucaí'
  AND c.codigo_ibge = '3543204'
  AND NOT EXISTS (
    SELECT 1 FROM public.enderecos end 
    WHERE end.estabelecimento_id = e.id
  );

-- Atualizar endereço principal do estabelecimento
UPDATE public.estabelecimentos
SET endereco_principal_id = (
    SELECT id FROM public.enderecos 
    WHERE estabelecimento_id = estabelecimentos.id 
    LIMIT 1
)
WHERE nome = 'UBS Centro - Santa Rita do Sapucaí'
  AND endereco_principal_id IS NULL;

-- UPA 24h
INSERT INTO public.enderecos (
    id,
    criado_em,
    atualizado_em,
    tenant_id,
    estabelecimento_id,
    ativo,
    tipo_logradouro,
    logradouro,
    numero,
    complemento,
    bairro,
    cep,
    pais,
    distrito,
    sem_numero,
    latitude,
    longitude,
    tipo_endereco,
    zona,
    codigo_ibge_municipio,
    cidade_id
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    t.id,
    e.id,
    true,
    'AVENIDA',
    'Avenida Doutor Antônio de Paula',
    '789',
    NULL,
    'São Cristóvão',
    '37540100',
    'Brasil',
    'São Cristóvão',
    false,
    -22.2600,
    -45.7100,
    'COMERCIAL',
    'URBANA',
    '3543204',
    c.id
FROM public.tenants t, public.estabelecimentos e, public.cidades c
WHERE t.slug = 'prefeitura-santa-rita-do-sapucai'
  AND e.nome = 'UPA 24 Horas - Santa Rita do Sapucaí'
  AND c.codigo_ibge = '3543204'
  AND NOT EXISTS (
    SELECT 1 FROM public.enderecos end 
    WHERE end.estabelecimento_id = e.id
  );

UPDATE public.estabelecimentos
SET endereco_principal_id = (
    SELECT id FROM public.enderecos 
    WHERE estabelecimento_id = estabelecimentos.id 
    LIMIT 1
)
WHERE nome = 'UPA 24 Horas - Santa Rita do Sapucaí'
  AND endereco_principal_id IS NULL;

-- Posto de Saúde São Cristóvão
INSERT INTO public.enderecos (
    id,
    criado_em,
    atualizado_em,
    tenant_id,
    estabelecimento_id,
    ativo,
    tipo_logradouro,
    logradouro,
    numero,
    complemento,
    bairro,
    cep,
    pais,
    distrito,
    sem_numero,
    latitude,
    longitude,
    tipo_endereco,
    zona,
    codigo_ibge_municipio,
    cidade_id
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    t.id,
    e.id,
    true,
    'RUA',
    'Rua João Pinheiro',
    '321',
    NULL,
    'São Cristóvão',
    '37540100',
    'Brasil',
    'São Cristóvão',
    false,
    -22.2400,
    -45.7000,
    'COMERCIAL',
    'URBANA',
    '3543204',
    c.id
FROM public.tenants t, public.estabelecimentos e, public.cidades c
WHERE t.slug = 'prefeitura-santa-rita-do-sapucai'
  AND e.nome = 'Posto de Saúde São Cristóvão'
  AND c.codigo_ibge = '3543204'
  AND NOT EXISTS (
    SELECT 1 FROM public.enderecos end 
    WHERE end.estabelecimento_id = e.id
  );

UPDATE public.estabelecimentos
SET endereco_principal_id = (
    SELECT id FROM public.enderecos 
    WHERE estabelecimento_id = estabelecimentos.id 
    LIMIT 1
)
WHERE nome = 'Posto de Saúde São Cristóvão'
  AND endereco_principal_id IS NULL;

-- Hospital Municipal
INSERT INTO public.enderecos (
    id,
    criado_em,
    atualizado_em,
    tenant_id,
    estabelecimento_id,
    ativo,
    tipo_logradouro,
    logradouro,
    numero,
    complemento,
    bairro,
    cep,
    pais,
    distrito,
    sem_numero,
    latitude,
    longitude,
    tipo_endereco,
    zona,
    codigo_ibge_municipio,
    cidade_id
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    t.id,
    e.id,
    true,
    'AVENIDA',
    'Avenida Doutor João XXIII',
    '1000',
    NULL,
    'Centro',
    '37540000',
    'Brasil',
    'Centro',
    false,
    -22.2550,
    -45.7080,
    'COMERCIAL',
    'URBANA',
    '3543204',
    c.id
FROM public.tenants t, public.estabelecimentos e, public.cidades c
WHERE t.slug = 'prefeitura-santa-rita-do-sapucai'
  AND e.nome = 'Hospital Municipal de Santa Rita do Sapucaí'
  AND c.codigo_ibge = '3543204'
  AND NOT EXISTS (
    SELECT 1 FROM public.enderecos end 
    WHERE end.estabelecimento_id = e.id
  );

UPDATE public.estabelecimentos
SET endereco_principal_id = (
    SELECT id FROM public.enderecos 
    WHERE estabelecimento_id = estabelecimentos.id 
    LIMIT 1
)
WHERE nome = 'Hospital Municipal de Santa Rita do Sapucaí'
  AND endereco_principal_id IS NULL;

-- UBS Novo Horizonte
INSERT INTO public.enderecos (
    id,
    criado_em,
    atualizado_em,
    tenant_id,
    estabelecimento_id,
    ativo,
    tipo_logradouro,
    logradouro,
    numero,
    complemento,
    bairro,
    cep,
    pais,
    distrito,
    sem_numero,
    latitude,
    longitude,
    tipo_endereco,
    zona,
    codigo_ibge_municipio,
    cidade_id
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    t.id,
    e.id,
    true,
    'RUA',
    'Rua das Flores',
    '250',
    NULL,
    'Novo Horizonte',
    '37540200',
    'Brasil',
    'Novo Horizonte',
    false,
    -22.2300,
    -45.6950,
    'COMERCIAL',
    'URBANA',
    '3543204',
    c.id
FROM public.tenants t, public.estabelecimentos e, public.cidades c
WHERE t.slug = 'prefeitura-santa-rita-do-sapucai'
  AND e.nome = 'UBS Novo Horizonte'
  AND c.codigo_ibge = '3543204'
  AND NOT EXISTS (
    SELECT 1 FROM public.enderecos end 
    WHERE end.estabelecimento_id = e.id
  );

UPDATE public.estabelecimentos
SET endereco_principal_id = (
    SELECT id FROM public.enderecos 
    WHERE estabelecimento_id = estabelecimentos.id 
    LIMIT 1
)
WHERE nome = 'UBS Novo Horizonte'
  AND endereco_principal_id IS NULL;

-- UBS Jardim Primavera
INSERT INTO public.enderecos (
    id,
    criado_em,
    atualizado_em,
    tenant_id,
    estabelecimento_id,
    ativo,
    tipo_logradouro,
    logradouro,
    numero,
    complemento,
    bairro,
    cep,
    pais,
    distrito,
    sem_numero,
    latitude,
    longitude,
    tipo_endereco,
    zona,
    codigo_ibge_municipio,
    cidade_id
)
SELECT 
    gen_random_uuid(),
    NOW(),
    NOW(),
    t.id,
    e.id,
    true,
    'RUA',
    'Rua Primavera',
    '180',
    NULL,
    'Jardim Primavera',
    '37540300',
    'Brasil',
    'Jardim Primavera',
    false,
    -22.2650,
    -45.7150,
    'COMERCIAL',
    'URBANA',
    '3543204',
    c.id
FROM public.tenants t, public.estabelecimentos e, public.cidades c
WHERE t.slug = 'prefeitura-santa-rita-do-sapucai'
  AND e.nome = 'UBS Jardim Primavera'
  AND c.codigo_ibge = '3543204'
  AND NOT EXISTS (
    SELECT 1 FROM public.enderecos end 
    WHERE end.estabelecimento_id = e.id
  );

UPDATE public.estabelecimentos
SET endereco_principal_id = (
    SELECT id FROM public.enderecos 
    WHERE estabelecimento_id = estabelecimentos.id 
    LIMIT 1
)
WHERE nome = 'UBS Jardim Primavera'
  AND endereco_principal_id IS NULL;

