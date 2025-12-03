-- =====================================================
-- MIGRATION: Refatoração do Sistema de Permissões
-- =====================================================
-- Descrição: Centraliza controle de acesso em usuarios_sistema
--            Remove tabela de papeis e vínculos diretos de profissionais
-- Data: 2025-01-XX
-- Autor: UPSaúde Team
-- =====================================================

-- =====================================================
-- 1. REMOVER TABELAS DE PAPÉIS E PERFIS
-- =====================================================

-- Drop tabelas de vínculos de papéis
DROP TABLE IF EXISTS public.usuarios_perfis CASCADE;
DROP TABLE IF EXISTS public.vinculos_papeis CASCADE;

-- Drop tabela de papéis
DROP TABLE IF EXISTS public.papeis CASCADE;

-- =====================================================
-- 2. REMOVER VÍNCULOS DIRETOS DE PROFISSIONAIS
-- =====================================================

-- Drop vínculos de médicos com estabelecimentos
DROP TABLE IF EXISTS public.medicos_estabelecimentos CASCADE;
DROP TABLE IF EXISTS public.medico_estabelecimento CASCADE;

-- Drop vínculos de profissionais de saúde com estabelecimentos
DROP TABLE IF EXISTS public.profissionais_estabelecimentos CASCADE;
DROP TABLE IF EXISTS public.profissional_estabelecimento CASCADE;

-- Drop vínculos de profissionais com equipes
DROP TABLE IF EXISTS public.vinculo_profissional_equipe CASCADE;
DROP TABLE IF EXISTS public.vinculos_profissionais_equipes CASCADE;

-- =====================================================
-- 3. AJUSTAR TABELA usuarios_sistema
-- =====================================================

-- Remover coluna tipo_usuario (se existir)
ALTER TABLE public.usuarios_sistema 
DROP COLUMN IF EXISTS tipo_usuario CASCADE;

-- Adicionar coluna admin_tenant
ALTER TABLE public.usuarios_sistema 
ADD COLUMN IF NOT EXISTS admin_tenant BOOLEAN NOT NULL DEFAULT false;

-- Adicionar comentário na coluna
COMMENT ON COLUMN public.usuarios_sistema.admin_tenant IS 
'Indica se o usuário é administrador do tenant. Se true: acesso total ao tenant e todos estabelecimentos. Se false: precisa ter vínculos específicos com estabelecimentos.';

-- =====================================================
-- 4. AJUSTAR TABELA usuarios_estabelecimentos
-- =====================================================

-- Adicionar coluna tipo_usuario
ALTER TABLE public.usuarios_estabelecimentos 
ADD COLUMN IF NOT EXISTS tipo_usuario VARCHAR(50);

-- Tornar a coluna NOT NULL após popular (vamos fazer em etapas)
-- Por enquanto deixa nullable para não quebrar dados existentes

-- Adicionar comentário na coluna
COMMENT ON COLUMN public.usuarios_estabelecimentos.tipo_usuario IS 
'Tipo de usuário/papel para este estabelecimento específico. Define as permissões e funcionalidades disponíveis neste estabelecimento. Não pode ser ADMIN_TENANT.';

-- =====================================================
-- 5. MIGRAÇÃO DE DADOS (SE NECESSÁRIO)
-- =====================================================

-- Se existirem dados antigos em usuarios_sistema com tipo_usuario,
-- podemos tentar migrar para a nova estrutura:
-- Exemplo (comentado - ajustar conforme necessidade):

-- UPDATE usuarios_sistema 
-- SET admin_tenant = true 
-- WHERE tipo_usuario = 'ADMIN_TENANT';

-- Para outros tipos, seria necessário criar vínculos em usuarios_estabelecimentos
-- Isso deve ser feito caso a caso dependendo da lógica de negócio

-- =====================================================
-- 6. VALIDAÇÕES E CONSTRAINTS
-- =====================================================

-- Adicionar check constraint para garantir que tipo_usuario não seja ADMIN_TENANT
-- em usuarios_estabelecimentos
ALTER TABLE public.usuarios_estabelecimentos
DROP CONSTRAINT IF EXISTS ck_usuarios_estabelecimentos_tipo_usuario;

ALTER TABLE public.usuarios_estabelecimentos
ADD CONSTRAINT ck_usuarios_estabelecimentos_tipo_usuario 
CHECK (tipo_usuario IS NULL OR tipo_usuario != 'ADMIN_TENANT');

COMMENT ON CONSTRAINT ck_usuarios_estabelecimentos_tipo_usuario ON public.usuarios_estabelecimentos IS 
'Garante que tipo_usuario não seja ADMIN_TENANT, pois esse tipo é exclusivo para adminTenant=true em usuarios_sistema';

-- =====================================================
-- 7. CRIAR ÍNDICES PARA PERFORMANCE
-- =====================================================

-- Índice para admin_tenant
CREATE INDEX IF NOT EXISTS idx_usuarios_sistema_admin_tenant 
ON public.usuarios_sistema(admin_tenant) 
WHERE admin_tenant = true;

-- Índice para tipo_usuario em usuarios_estabelecimentos
CREATE INDEX IF NOT EXISTS idx_usuarios_estabelecimentos_tipo_usuario 
ON public.usuarios_estabelecimentos(tipo_usuario);

-- =====================================================
-- 8. ATUALIZAR POLÍTICAS RLS (Row Level Security)
-- =====================================================

-- Remover políticas antigas de papeis
DROP POLICY IF EXISTS papeis_select_policy ON public.papeis;
DROP POLICY IF EXISTS papeis_insert_policy ON public.papeis;
DROP POLICY IF EXISTS papeis_update_policy ON public.papeis;
DROP POLICY IF EXISTS papeis_delete_policy ON public.papeis;

-- Atualizar políticas de usuarios_sistema (exemplo)
-- Ajustar conforme as regras de negócio do seu sistema

-- Política para permitir usuários verem seus próprios dados
DROP POLICY IF EXISTS usuarios_sistema_select_own ON public.usuarios_sistema;
CREATE POLICY usuarios_sistema_select_own ON public.usuarios_sistema
FOR SELECT
USING (
  auth.uid() = user_id 
  OR 
  EXISTS (
    SELECT 1 FROM public.usuarios_sistema us
    WHERE us.user_id = auth.uid() 
    AND us.admin_tenant = true
    AND us.tenant_id = usuarios_sistema.tenant_id
  )
);

-- =====================================================
-- 9. FUNÇÕES AUXILIARES (OPCIONAL)
-- =====================================================

-- Função para verificar se usuário é admin do tenant
CREATE OR REPLACE FUNCTION public.is_admin_tenant(p_user_id UUID, p_tenant_id UUID)
RETURNS BOOLEAN
LANGUAGE plpgsql
SECURITY DEFINER
AS $$
BEGIN
  RETURN EXISTS (
    SELECT 1 
    FROM public.usuarios_sistema 
    WHERE user_id = p_user_id 
    AND tenant_id = p_tenant_id 
    AND admin_tenant = true 
    AND ativo = true
  );
END;
$$;

COMMENT ON FUNCTION public.is_admin_tenant(UUID, UUID) IS 
'Verifica se o usuário é administrador do tenant especificado';

-- Função para obter estabelecimentos do usuário
CREATE OR REPLACE FUNCTION public.get_user_estabelecimentos(p_user_id UUID)
RETURNS TABLE (
  estabelecimento_id UUID,
  tipo_usuario VARCHAR(50)
)
LANGUAGE plpgsql
SECURITY DEFINER
AS $$
BEGIN
  RETURN QUERY
  SELECT 
    ue.estabelecimento_id,
    ue.tipo_usuario
  FROM public.usuarios_estabelecimentos ue
  INNER JOIN public.usuarios_sistema us ON us.id = ue.usuario_id
  WHERE us.user_id = p_user_id
  AND us.ativo = true;
END;
$$;

COMMENT ON FUNCTION public.get_user_estabelecimentos(UUID) IS 
'Retorna todos os estabelecimentos vinculados ao usuário com seus respectivos tipos de acesso';

-- =====================================================
-- 10. LIMPEZA FINAL
-- =====================================================

-- Remover funções antigas relacionadas a papeis (se existirem)
DROP FUNCTION IF EXISTS public.get_user_papeis(UUID) CASCADE;
DROP FUNCTION IF EXISTS public.has_papel(UUID, VARCHAR) CASCADE;

-- =====================================================
-- FIM DA MIGRATION
-- =====================================================

-- Mensagem de conclusão
DO $$ 
BEGIN 
  RAISE NOTICE 'Migration V001 - Refatoração do Sistema de Permissões concluída com sucesso!';
  RAISE NOTICE 'Próximos passos:';
  RAISE NOTICE '1. Verificar dados migrados';
  RAISE NOTICE '2. Atualizar tipo_usuario em usuarios_estabelecimentos para NOT NULL se necessário';
  RAISE NOTICE '3. Ajustar políticas RLS conforme regras de negócio específicas';
END $$;

