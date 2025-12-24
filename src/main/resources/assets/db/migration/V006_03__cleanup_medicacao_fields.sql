-- Migration: Limpeza dos campos antigos da tabela medicacoes
-- Esta migration remove os campos que foram migrados para relações com tabelas oficiais

-- Passo 1: Remover coluna categoria (agora obtida via cid10_subcategorias)
ALTER TABLE medicacoes DROP COLUMN IF EXISTS categoria;

-- Passo 2: Remover coluna subcategoria (agora obtida via cid10_subcategorias)
ALTER TABLE medicacoes DROP COLUMN IF EXISTS subcategoria;

-- Passo 3: Remover coluna codigo_sigtap (agora obtida via sigtap_procedimento)
ALTER TABLE medicacoes DROP COLUMN IF EXISTS codigo_sigtap;

-- Nota: As colunas cid10_subcategorias_id e sigtap_procedimento_id permanecem nullable
-- porque nem toda medicação tem associação obrigatória com CID-10 ou SIGTAP
