-- =====================================================
-- MIGRATION: Remover campos antigos da tabela pacientes
-- =====================================================
-- Objetivo: Remover campos migrados para novas tabelas após validação completa
-- IMPORTANTE: Esta migration deve ser executada APENAS após validação completa
-- dos dados migrados. Os campos foram movidos para:
-- - CPF, CNS, RG → paciente_identificador
-- - telefone, email → paciente_contato
-- - raca_cor, nacionalidade, etc → paciente_dados_sociodemograficos
-- - responsavel_* → paciente_responsavel_legal
-- - cns_validado, cartao_sus_ativo, etc → paciente_integracao_gov
-- - acompanhado_por_equipe_esf → paciente_vinculo_territorial
-- Autor: UPSaúde
-- =====================================================

-- =====================================================
-- 1. REMOVER CONSTRAINTS E ÍNDICES OBSOLETOS
-- =====================================================

-- Remover unique constraints de CPF, CNS e email (agora em paciente_identificador e paciente_contato)
ALTER TABLE public.pacientes 
    DROP CONSTRAINT IF EXISTS uk_pacientes_cpf;

ALTER TABLE public.pacientes 
    DROP CONSTRAINT IF EXISTS uk_pacientes_cns;

ALTER TABLE public.pacientes 
    DROP CONSTRAINT IF EXISTS uk_pacientes_email;

-- Remover índices obsoletos
DROP INDEX IF EXISTS public.idx_pacientes_cpf;
DROP INDEX IF EXISTS public.idx_pacientes_email;
DROP INDEX IF EXISTS public.idx_pacientes_cns;
DROP INDEX IF EXISTS public.idx_pacientes_rg;
DROP INDEX IF EXISTS public.idx_pacientes_situacao_rua;
DROP INDEX IF EXISTS public.idx_pacientes_cartao_sus_ativo;
DROP INDEX IF EXISTS public.idx_pacientes_cns_validado;
DROP INDEX IF EXISTS public.idx_pacientes_acompanhado_esf;

-- =====================================================
-- 2. REMOVER COLUNAS MIGRADAS
-- =====================================================

-- Remover identificadores (migrados para paciente_identificador)
ALTER TABLE public.pacientes 
    DROP COLUMN IF EXISTS cpf,
    DROP COLUMN IF EXISTS cns,
    DROP COLUMN IF EXISTS rg;

-- Remover contatos (migrados para paciente_contato)
ALTER TABLE public.pacientes 
    DROP COLUMN IF EXISTS telefone,
    DROP COLUMN IF EXISTS email;

-- Remover dados sociodemográficos (migrados para paciente_dados_sociodemograficos)
ALTER TABLE public.pacientes 
    DROP COLUMN IF EXISTS raca_cor,
    DROP COLUMN IF EXISTS nacionalidade,
    DROP COLUMN IF EXISTS pais_nascimento,
    DROP COLUMN IF EXISTS naturalidade,
    DROP COLUMN IF EXISTS municipio_nascimento_ibge,
    DROP COLUMN IF EXISTS escolaridade,
    DROP COLUMN IF EXISTS ocupacao_profissao,
    DROP COLUMN IF EXISTS situacao_rua;

-- Remover dados de responsável legal (migrados para paciente_responsavel_legal)
ALTER TABLE public.pacientes 
    DROP COLUMN IF EXISTS responsavel_nome,
    DROP COLUMN IF EXISTS responsavel_cpf,
    DROP COLUMN IF EXISTS responsavel_telefone;

-- Remover flags de integração (migrados para paciente_integracao_gov)
ALTER TABLE public.pacientes 
    DROP COLUMN IF EXISTS cns_validado,
    DROP COLUMN IF EXISTS tipo_cns,
    DROP COLUMN IF EXISTS data_atualizacao_cns,
    DROP COLUMN IF EXISTS cartao_sus_ativo,
    DROP COLUMN IF EXISTS origem_cadastro;

-- Remover flag de vínculo territorial (migrado para paciente_vinculo_territorial)
ALTER TABLE public.pacientes 
    DROP COLUMN IF EXISTS acompanhado_por_equipe_esf;

-- Remover campos de deficiência (já em deficiencias_paciente)
-- Manter apenas se necessário para performance, caso contrário remover:
-- ALTER TABLE public.pacientes 
--     DROP COLUMN IF EXISTS possui_deficiencia,
--     DROP COLUMN IF EXISTS tipo_deficiencia;

-- Remover estado_civil se não for necessário no core
-- (avaliar se deve ficar no core ou em dados sociodemográficos)
-- ALTER TABLE public.pacientes 
--     DROP COLUMN IF EXISTS estado_civil;

-- =====================================================
-- FIM DA MIGRATION
-- =====================================================
-- NOTA: Esta migration remove campos que foram migrados para novas tabelas.
-- Certifique-se de que:
-- 1. Todos os dados foram migrados corretamente
-- 2. Nenhum código depende mais desses campos
-- 3. Todos os testes foram executados com sucesso
-- =====================================================

