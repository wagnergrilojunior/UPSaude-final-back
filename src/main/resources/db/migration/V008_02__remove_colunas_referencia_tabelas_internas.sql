-- =====================================================
-- V008_02: Remover colunas que referenciam tabelas internas a serem deletadas
-- =====================================================
-- Este script remove as colunas que referenciam as tabelas que serão deletadas
-- Nota: Algumas colunas podem ser mantidas para histórico ou migradas para cid10_subcategorias
-- =====================================================

-- =====================================================
-- 1. Remover colunas que referenciam cid_doencas
-- =====================================================
-- Nota: Idealmente, essas colunas deveriam ser migradas para referenciar cid10_subcategorias
-- Mas como o plano é deletar, vamos remover as colunas
ALTER TABLE atendimentos DROP COLUMN IF EXISTS cid_principal_id;
ALTER TABLE consultas DROP COLUMN IF EXISTS cid_principal_id;
ALTER TABLE receitas_medicas DROP COLUMN IF EXISTS cid_principal_id;

-- =====================================================
-- 2. Remover colunas que referenciam catalogo_exames
-- =====================================================
ALTER TABLE exames DROP COLUMN IF EXISTS catalogo_exame_id;

-- =====================================================
-- Nota: As tabelas doencas_paciente, medicacoes_paciente, receitas_medicacoes, 
-- dispensacoes_medicamentos serão deletadas integralmente na próxima migração,
-- então não precisamos remover suas colunas individualmente
-- =====================================================
