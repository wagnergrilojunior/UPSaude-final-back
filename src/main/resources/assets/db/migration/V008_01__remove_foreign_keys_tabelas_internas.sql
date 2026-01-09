-- =====================================================
-- V008_01: Remover Foreign Keys que referenciam tabelas internas a serem deletadas
-- =====================================================
-- Este script remove todas as foreign keys que referenciam as tabelas:
-- - cid_doencas
-- - doencas
-- - medicacoes
-- - catalogo_exames
-- - catalogo_procedimentos
-- - procedimentos
-- =====================================================

-- =====================================================
-- 1. Remover FKs que referenciam cid_doencas
-- =====================================================
ALTER TABLE atendimentos DROP CONSTRAINT IF EXISTS fk1j6n12l67odm50wk0bjtbrtwt;
ALTER TABLE consultas DROP CONSTRAINT IF EXISTS fk7x4a0s331f4qngap40rq8242h;
ALTER TABLE doencas DROP CONSTRAINT IF EXISTS fkhqrmrbi072021swgrfk1qlmut;
ALTER TABLE doencas_paciente DROP CONSTRAINT IF EXISTS fk5j8wjt8q00j1au3j7w8h3r5cg;
ALTER TABLE medicacoes_paciente DROP CONSTRAINT IF EXISTS fkpmgang3nfrcsu3a7okgwex14m;
ALTER TABLE receitas_medicas DROP CONSTRAINT IF EXISTS fka7p74h8qegpk5l8rkn0svml4p;

-- =====================================================
-- 2. Remover FKs que referenciam doencas
-- =====================================================
ALTER TABLE doencas_paciente DROP CONSTRAINT IF EXISTS fkb53s417hyilqj0rckyt8yex1i;

-- =====================================================
-- 3. Remover FKs que referenciam medicacoes
-- =====================================================
ALTER TABLE medicacoes_paciente DROP CONSTRAINT IF EXISTS fkb9bonj16wmxj7vkpn5ewt7n54;
ALTER TABLE receitas_medicacoes DROP CONSTRAINT IF EXISTS fkfadhcrvqohc8p24qmr1y4xbcx;
ALTER TABLE dispensacoes_medicamentos DROP CONSTRAINT IF EXISTS fkc1triydci870ke8ls7oqfghc4;

-- =====================================================
-- 4. Remover FKs que referenciam catalogo_exames
-- =====================================================
ALTER TABLE exames DROP CONSTRAINT IF EXISTS fkd02d6d04x63pcee1o1rlu2vnk;

-- =====================================================
-- Nota: catalogo_procedimentos e procedimentos não possuem FKs referenciando-as
-- =====================================================
