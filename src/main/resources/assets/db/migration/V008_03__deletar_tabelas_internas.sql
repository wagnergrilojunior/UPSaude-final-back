-- =====================================================
-- V008_03: Deletar tabelas internas
-- =====================================================
-- Este script deleta as tabelas internas na ordem correta (filhas primeiro)
-- =====================================================

-- =====================================================
-- 1. Deletar tabelas dependentes (filhas) primeiro
-- =====================================================
DROP TABLE IF EXISTS doencas_paciente CASCADE;
DROP TABLE IF EXISTS medicacoes_paciente CASCADE;
DROP TABLE IF EXISTS receitas_medicacoes CASCADE;
DROP TABLE IF EXISTS dispensacoes_medicamentos CASCADE;

-- =====================================================
-- 2. Deletar tabelas principais
-- =====================================================
DROP TABLE IF EXISTS doencas CASCADE;
DROP TABLE IF EXISTS medicacoes CASCADE;
DROP TABLE IF EXISTS procedimentos CASCADE;
DROP TABLE IF EXISTS catalogo_exames CASCADE;
DROP TABLE IF EXISTS catalogo_procedimentos CASCADE;
DROP TABLE IF EXISTS cid_doencas CASCADE;

-- =====================================================
-- Nota: CASCADE garante que quaisquer objetos dependentes 
-- (índices, triggers, etc.) também sejam removidos
-- =====================================================
