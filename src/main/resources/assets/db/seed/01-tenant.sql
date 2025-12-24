-- Script de Seed: Tenant - Prefeitura Municipal de Santa Rita do Sapucaí
-- Executado quando app.seed.enabled=true

-- Inserir Tenant (Prefeitura de Santa Rita do Sapucaí)
-- Usar CTE para gerar UUID e criar com auto-referência desde o início

INSERT INTO public.tenants (
    id,
    criado_em,
    atualizado_em,
    tenant_id,
    estabelecimento_id,
    ativo,
    name,
    slug,
    description,
    logo_url,
    metadata,
    is_active,
    cnpj,
    cnes,
    tipo_instituicao,
    telefone,
    email,
    site,
    inscricao_estadual,
    inscricao_municipal,
    responsavel_nome,
    responsavel_cpf,
    responsavel_telefone,
    horario_funcionamento,
    observacoes
)
SELECT 
    uuid_val,
    NOW(),
    NOW(),
    uuid_val,
    NULL,
    true,
    'Prefeitura Municipal de Santa Rita do Sapucaí',
    'prefeitura-santa-rita-do-sapucai',
    'Prefeitura Municipal responsável pela gestão da saúde pública do município de Santa Rita do Sapucaí - MG',
    NULL,
    NULL::jsonb,
    true,
    '12345678000195',
    '1234567',
    'Órgão Público',
    '3535511234',
    'contato@santaritadosapucai.mg.gov.br',
    'https://www.santaritadosapucai.mg.gov.br',
    '062307904018',
    '123456',
    'João da Silva',
    '12345678901',
    '3535511234',
    'Segunda a Sexta: 08:00 às 17:00',
    'Prefeitura Municipal de Santa Rita do Sapucaí - MG'
FROM (
    SELECT gen_random_uuid() AS uuid_val
) AS uuid_gen
WHERE NOT EXISTS (
    SELECT 1 FROM public.tenants WHERE slug = 'prefeitura-santa-rita-do-sapucai'
);

-- Garantir auto-referência caso o tenant já exista
UPDATE public.tenants
SET tenant_id = id
WHERE slug = 'prefeitura-santa-rita-do-sapucai'
  AND (tenant_id IS NULL OR tenant_id != id);
