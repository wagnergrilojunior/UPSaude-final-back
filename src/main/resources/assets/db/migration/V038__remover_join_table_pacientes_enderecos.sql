-- =====================================================
-- MIGRATION: Remover JoinTable pacientes_enderecos
-- =====================================================
-- Objetivo: Remover a JoinTable simples após migração completa para paciente_endereco
-- IMPORTANTE: Esta migration deve ser executada APENAS após validação completa
-- dos dados migrados
-- Autor: UPSaúde
-- =====================================================

-- Remover tabela de junção pacientes_enderecos
DROP TABLE IF EXISTS public.pacientes_enderecos CASCADE;

-- =====================================================
-- FIM DA MIGRATION
-- =====================================================

