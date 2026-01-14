-- Migration: Tornar campo slug nullable na tabela tenants
-- Data: 2026-01-14
-- Descrição: Remove a obrigatoriedade do campo slug na tabela tenants

-- Remover constraint NOT NULL do campo slug
ALTER TABLE public.tenants 
ALTER COLUMN slug DROP NOT NULL;

-- Comentário explicativo
COMMENT ON COLUMN public.tenants.slug IS 'Slug único do tenant (opcional). Usado para identificação amigável em URLs.';
