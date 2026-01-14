-- Migration: add_brevo_template_id_to_templates_notificacao
-- Description: Adiciona coluna brevo_template_id na tabela templates_notificacao para integração com templates do Brevo
-- Date: 2026-01-14

-- Adicionar coluna brevo_template_id
ALTER TABLE public.templates_notificacao
ADD COLUMN IF NOT EXISTS brevo_template_id INTEGER;

-- Adicionar comentário na coluna
COMMENT ON COLUMN public.templates_notificacao.brevo_template_id IS 'ID do template no Brevo (Sendinblue) para envio de e-mails transacionais';

-- Criar índice para busca por template do Brevo
CREATE INDEX IF NOT EXISTS idx_template_brevo_id 
ON public.templates_notificacao(brevo_template_id) 
WHERE brevo_template_id IS NOT NULL;
