-- Migration: Adicionar código IBGE do município na tabela tenants
-- Descrição: Adiciona a coluna codigo_ibge_municipio para armazenar o código IBGE do município do tenant,
--            referenciando a tabela cidades que contém os códigos IBGE oficiais

-- Adicionar coluna codigo_ibge_municipio
ALTER TABLE public.tenants
ADD COLUMN codigo_ibge_municipio VARCHAR(10);

-- Adicionar comentário na coluna
COMMENT ON COLUMN public.tenants.codigo_ibge_municipio IS 'Código IBGE do município do tenant (referência à tabela cidades.codigo_ibge)';

-- Criar índice para melhorar performance de consultas
CREATE INDEX idx_tenants_codigo_ibge_municipio ON public.tenants(codigo_ibge_municipio);
