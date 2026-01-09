-- =====================================================
-- MIGRATION: Tabelas de Refer?ncia e Relacionais SIGTAP
-- =====================================================
-- Objetivo: Criar tabelas de refer?ncia (tb_*) e relacionais (rl_*) para importa??o de arquivos TXT
-- Autor: UPSa?de
-- =====================================================

-- =====================================================
-- TABELAS DE REFER?NCIA (tb_*)
-- =====================================================

-- CID (Classifica??o Internacional de Doen?as)
CREATE TABLE IF NOT EXISTS public.sigtap_cid (
    id UUID PRIMARY KEY,
    criado_em TIMESTAMPTZ NOT NULL DEFAULT now(),
    atualizado_em TIMESTAMPTZ NULL,
    ativo BOOLEAN NOT NULL DEFAULT true,

    codigo_oficial VARCHAR(4) NOT NULL,
    nome VARCHAR(100) NULL,
    tipo_agravo CHAR(1) NULL,
    tipo_sexo CHAR(1) NULL,
    tipo_estadio CHAR(1) NULL,
    valor_campos_irradiados INTEGER NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_sigtap_cid_codigo_oficial
    ON public.sigtap_cid (codigo_oficial);

CREATE INDEX IF NOT EXISTS idx_sigtap_cid_nome
    ON public.sigtap_cid (nome);

-- Ocupa??o (CBO)
CREATE TABLE IF NOT EXISTS public.sigtap_ocupacao (
    id UUID PRIMARY KEY,
    criado_em TIMESTAMPTZ NOT NULL DEFAULT now(),
    atualizado_em TIMESTAMPTZ NULL,
    ativo BOOLEAN NOT NULL DEFAULT true,

    codigo_oficial VARCHAR(6) NOT NULL,
    nome VARCHAR(150) NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_sigtap_ocupacao_codigo_oficial
    ON public.sigtap_ocupacao (codigo_oficial);

CREATE INDEX IF NOT EXISTS idx_sigtap_ocupacao_nome
    ON public.sigtap_ocupacao (nome);

-- Financiamento
CREATE TABLE IF NOT EXISTS public.sigtap_financiamento (
    id UUID PRIMARY KEY,
    criado_em TIMESTAMPTZ NOT NULL DEFAULT now(),
    atualizado_em TIMESTAMPTZ NULL,
    ativo BOOLEAN NOT NULL DEFAULT true,

    codigo_oficial VARCHAR(2) NOT NULL,
    nome VARCHAR(100) NULL,
    competencia_inicial VARCHAR(6) NULL,
    competencia_final VARCHAR(6) NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_sigtap_financiamento_codigo_comp
    ON public.sigtap_financiamento (codigo_oficial, competencia_inicial);

CREATE INDEX IF NOT EXISTS idx_sigtap_financiamento_nome
    ON public.sigtap_financiamento (nome);

-- Rubrica
CREATE TABLE IF NOT EXISTS public.sigtap_rubrica (
    id UUID PRIMARY KEY,
    criado_em TIMESTAMPTZ NOT NULL DEFAULT now(),
    atualizado_em TIMESTAMPTZ NULL,
    ativo BOOLEAN NOT NULL DEFAULT true,

    codigo_oficial VARCHAR(6) NOT NULL,
    nome VARCHAR(100) NULL,
    competencia_inicial VARCHAR(6) NULL,
    competencia_final VARCHAR(6) NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_sigtap_rubrica_codigo_comp
    ON public.sigtap_rubrica (codigo_oficial, competencia_inicial);

CREATE INDEX IF NOT EXISTS idx_sigtap_rubrica_nome
    ON public.sigtap_rubrica (nome);

-- Modalidade
CREATE TABLE IF NOT EXISTS public.sigtap_modalidade (
    id UUID PRIMARY KEY,
    criado_em TIMESTAMPTZ NOT NULL DEFAULT now(),
    atualizado_em TIMESTAMPTZ NULL,
    ativo BOOLEAN NOT NULL DEFAULT true,

    codigo_oficial VARCHAR(2) NOT NULL,
    nome VARCHAR(100) NULL,
    competencia_inicial VARCHAR(6) NULL,
    competencia_final VARCHAR(6) NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_sigtap_modalidade_codigo_comp
    ON public.sigtap_modalidade (codigo_oficial, competencia_inicial);

CREATE INDEX IF NOT EXISTS idx_sigtap_modalidade_nome
    ON public.sigtap_modalidade (nome);

-- Registro
CREATE TABLE IF NOT EXISTS public.sigtap_registro (
    id UUID PRIMARY KEY,
    criado_em TIMESTAMPTZ NOT NULL DEFAULT now(),
    atualizado_em TIMESTAMPTZ NULL,
    ativo BOOLEAN NOT NULL DEFAULT true,

    codigo_oficial VARCHAR(2) NOT NULL,
    nome VARCHAR(100) NULL,
    competencia_inicial VARCHAR(6) NULL,
    competencia_final VARCHAR(6) NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_sigtap_registro_codigo_comp
    ON public.sigtap_registro (codigo_oficial, competencia_inicial);

CREATE INDEX IF NOT EXISTS idx_sigtap_registro_nome
    ON public.sigtap_registro (nome);

-- Tipo Leito
CREATE TABLE IF NOT EXISTS public.sigtap_tipo_leito (
    id UUID PRIMARY KEY,
    criado_em TIMESTAMPTZ NOT NULL DEFAULT now(),
    atualizado_em TIMESTAMPTZ NULL,
    ativo BOOLEAN NOT NULL DEFAULT true,

    codigo_oficial VARCHAR(2) NOT NULL,
    nome VARCHAR(100) NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_sigtap_tipo_leito_codigo_oficial
    ON public.sigtap_tipo_leito (codigo_oficial);

CREATE INDEX IF NOT EXISTS idx_sigtap_tipo_leito_nome
    ON public.sigtap_tipo_leito (nome);

-- Servi?o
CREATE TABLE IF NOT EXISTS public.sigtap_servico (
    id UUID PRIMARY KEY,
    criado_em TIMESTAMPTZ NOT NULL DEFAULT now(),
    atualizado_em TIMESTAMPTZ NULL,
    ativo BOOLEAN NOT NULL DEFAULT true,

    codigo_oficial VARCHAR(3) NOT NULL,
    nome VARCHAR(100) NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_sigtap_servico_codigo_oficial
    ON public.sigtap_servico (codigo_oficial);

CREATE INDEX IF NOT EXISTS idx_sigtap_servico_nome
    ON public.sigtap_servico (nome);

-- Servi?o Classifica??o
CREATE TABLE IF NOT EXISTS public.sigtap_servico_classificacao (
    id UUID PRIMARY KEY,
    criado_em TIMESTAMPTZ NOT NULL DEFAULT now(),
    atualizado_em TIMESTAMPTZ NULL,
    ativo BOOLEAN NOT NULL DEFAULT true,

    servico_id UUID NOT NULL REFERENCES public.sigtap_servico(id) ON DELETE RESTRICT,
    codigo_classificacao VARCHAR(3) NOT NULL,
    nome VARCHAR(150) NULL,
    competencia_inicial VARCHAR(6) NULL,
    competencia_final VARCHAR(6) NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_sigtap_servico_class_serv_cod
    ON public.sigtap_servico_classificacao (servico_id, codigo_classificacao, competencia_inicial);

CREATE INDEX IF NOT EXISTS idx_sigtap_servico_class_nome
    ON public.sigtap_servico_classificacao (nome);

CREATE INDEX IF NOT EXISTS idx_sigtap_servico_class_servico_id
    ON public.sigtap_servico_classificacao (servico_id);

-- Habilitacao
CREATE TABLE IF NOT EXISTS public.sigtap_habilitacao (
    id UUID PRIMARY KEY,
    criado_em TIMESTAMPTZ NOT NULL DEFAULT now(),
    atualizado_em TIMESTAMPTZ NULL,
    ativo BOOLEAN NOT NULL DEFAULT true,

    codigo_oficial VARCHAR(4) NOT NULL,
    nome VARCHAR(150) NULL,
    competencia_inicial VARCHAR(6) NULL,
    competencia_final VARCHAR(6) NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_sigtap_habilitacao_codigo_comp
    ON public.sigtap_habilitacao (codigo_oficial, competencia_inicial);

CREATE INDEX IF NOT EXISTS idx_sigtap_habilitacao_nome
    ON public.sigtap_habilitacao (nome);

-- Grupo Habilitacao
CREATE TABLE IF NOT EXISTS public.sigtap_grupo_habilitacao (
    id UUID PRIMARY KEY,
    criado_em TIMESTAMPTZ NOT NULL DEFAULT now(),
    atualizado_em TIMESTAMPTZ NULL,
    ativo BOOLEAN NOT NULL DEFAULT true,

    codigo_oficial VARCHAR(4) NOT NULL,
    nome VARCHAR(20) NULL,
    descricao VARCHAR(250) NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_sigtap_grupo_habilitacao_codigo_oficial
    ON public.sigtap_grupo_habilitacao (codigo_oficial);

CREATE INDEX IF NOT EXISTS idx_sigtap_grupo_habilitacao_nome
    ON public.sigtap_grupo_habilitacao (nome);

-- Regra Condicionada
CREATE TABLE IF NOT EXISTS public.sigtap_regra_condicionada (
    id UUID PRIMARY KEY,
    criado_em TIMESTAMPTZ NOT NULL DEFAULT now(),
    atualizado_em TIMESTAMPTZ NULL,
    ativo BOOLEAN NOT NULL DEFAULT true,

    codigo_oficial VARCHAR(4) NOT NULL,
    nome VARCHAR(100) NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_sigtap_regra_condicionada_codigo_oficial
    ON public.sigtap_regra_condicionada (codigo_oficial);

CREATE INDEX IF NOT EXISTS idx_sigtap_regra_condicionada_nome
    ON public.sigtap_regra_condicionada (nome);

-- RENASES
CREATE TABLE IF NOT EXISTS public.sigtap_renases (
    id UUID PRIMARY KEY,
    criado_em TIMESTAMPTZ NOT NULL DEFAULT now(),
    atualizado_em TIMESTAMPTZ NULL,
    ativo BOOLEAN NOT NULL DEFAULT true,

    codigo_oficial VARCHAR(10) NOT NULL,
    nome VARCHAR(100) NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_sigtap_renases_codigo_oficial
    ON public.sigtap_renases (codigo_oficial);

CREATE INDEX IF NOT EXISTS idx_sigtap_renases_nome
    ON public.sigtap_renases (nome);

-- TUSS
CREATE TABLE IF NOT EXISTS public.sigtap_tuss (
    id UUID PRIMARY KEY,
    criado_em TIMESTAMPTZ NOT NULL DEFAULT now(),
    atualizado_em TIMESTAMPTZ NULL,
    ativo BOOLEAN NOT NULL DEFAULT true,

    codigo_oficial VARCHAR(10) NOT NULL,
    nome VARCHAR(100) NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_sigtap_tuss_codigo_oficial
    ON public.sigtap_tuss (codigo_oficial);

CREATE INDEX IF NOT EXISTS idx_sigtap_tuss_nome
    ON public.sigtap_tuss (nome);

-- Componente Rede
CREATE TABLE IF NOT EXISTS public.sigtap_componente_rede (
    id UUID PRIMARY KEY,
    criado_em TIMESTAMPTZ NOT NULL DEFAULT now(),
    atualizado_em TIMESTAMPTZ NULL,
    ativo BOOLEAN NOT NULL DEFAULT true,

    codigo_oficial VARCHAR(10) NOT NULL,
    nome VARCHAR(100) NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_sigtap_componente_rede_codigo_oficial
    ON public.sigtap_componente_rede (codigo_oficial);

CREATE INDEX IF NOT EXISTS idx_sigtap_componente_rede_nome
    ON public.sigtap_componente_rede (nome);

-- Rede Aten??o
CREATE TABLE IF NOT EXISTS public.sigtap_rede_atencao (
    id UUID PRIMARY KEY,
    criado_em TIMESTAMPTZ NOT NULL DEFAULT now(),
    atualizado_em TIMESTAMPTZ NULL,
    ativo BOOLEAN NOT NULL DEFAULT true,

    codigo_oficial VARCHAR(3) NOT NULL,
    nome VARCHAR(50) NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_sigtap_rede_atencao_codigo_oficial
    ON public.sigtap_rede_atencao (codigo_oficial);

CREATE INDEX IF NOT EXISTS idx_sigtap_rede_atencao_nome
    ON public.sigtap_rede_atencao (nome);

-- SIA/SIH
CREATE TABLE IF NOT EXISTS public.sigtap_sia_sih (
    id UUID PRIMARY KEY,
    criado_em TIMESTAMPTZ NOT NULL DEFAULT now(),
    atualizado_em TIMESTAMPTZ NULL,
    ativo BOOLEAN NOT NULL DEFAULT true,

    codigo_oficial VARCHAR(10) NOT NULL,
    nome VARCHAR(100) NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_sigtap_sia_sih_codigo_oficial
    ON public.sigtap_sia_sih (codigo_oficial);

CREATE INDEX IF NOT EXISTS idx_sigtap_sia_sih_nome
    ON public.sigtap_sia_sih (nome);

-- Detalhe
CREATE TABLE IF NOT EXISTS public.sigtap_detalhe (
    id UUID PRIMARY KEY,
    criado_em TIMESTAMPTZ NOT NULL DEFAULT now(),
    atualizado_em TIMESTAMPTZ NULL,
    ativo BOOLEAN NOT NULL DEFAULT true,

    codigo_oficial VARCHAR(3) NOT NULL,
    nome VARCHAR(100) NULL,
    competencia_inicial VARCHAR(6) NULL,
    competencia_final VARCHAR(6) NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_sigtap_detalhe_codigo_comp
    ON public.sigtap_detalhe (codigo_oficial, competencia_inicial);

CREATE INDEX IF NOT EXISTS idx_sigtap_detalhe_nome
    ON public.sigtap_detalhe (nome);

-- Descri??o
CREATE TABLE IF NOT EXISTS public.sigtap_descricao (
    id UUID PRIMARY KEY,
    criado_em TIMESTAMPTZ NOT NULL DEFAULT now(),
    atualizado_em TIMESTAMPTZ NULL,
    ativo BOOLEAN NOT NULL DEFAULT true,

    procedimento_id UUID NULL REFERENCES public.sigtap_procedimento(id) ON DELETE SET NULL,
    descricao_completa TEXT NULL,
    competencia_inicial VARCHAR(6) NULL,
    competencia_final VARCHAR(6) NULL
);

CREATE INDEX IF NOT EXISTS idx_sigtap_descricao_procedimento_id
    ON public.sigtap_descricao (procedimento_id);

CREATE INDEX IF NOT EXISTS idx_sigtap_descricao_competencia
    ON public.sigtap_descricao (competencia_inicial);

-- Descri??o Detalhe
CREATE TABLE IF NOT EXISTS public.sigtap_descricao_detalhe (
    id UUID PRIMARY KEY,
    criado_em TIMESTAMPTZ NOT NULL DEFAULT now(),
    atualizado_em TIMESTAMPTZ NULL,
    ativo BOOLEAN NOT NULL DEFAULT true,

    detalhe_id UUID NOT NULL REFERENCES public.sigtap_detalhe(id) ON DELETE RESTRICT,
    descricao_completa TEXT NULL,
    competencia_inicial VARCHAR(6) NULL,
    competencia_final VARCHAR(6) NULL
);

CREATE INDEX IF NOT EXISTS idx_sigtap_descricao_detalhe_detalhe_id
    ON public.sigtap_descricao_detalhe (detalhe_id);

CREATE INDEX IF NOT EXISTS idx_sigtap_descricao_detalhe_competencia
    ON public.sigtap_descricao_detalhe (competencia_inicial);

-- =====================================================
-- TABELAS RELACIONAIS (rl_* ? sigtap_procedimento_*)
-- =====================================================

-- Procedimento CID
CREATE TABLE IF NOT EXISTS public.sigtap_procedimento_cid (
    id UUID PRIMARY KEY,
    criado_em TIMESTAMPTZ NOT NULL DEFAULT now(),
    atualizado_em TIMESTAMPTZ NULL,
    ativo BOOLEAN NOT NULL DEFAULT true,

    procedimento_id UUID NOT NULL REFERENCES public.sigtap_procedimento(id) ON DELETE CASCADE,
    cid_id UUID NOT NULL REFERENCES public.sigtap_cid(id) ON DELETE RESTRICT,
    principal BOOLEAN NOT NULL DEFAULT false,
    competencia_inicial VARCHAR(6) NULL,
    competencia_final VARCHAR(6) NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_sigtap_proc_cid_proc_cid_comp
    ON public.sigtap_procedimento_cid (procedimento_id, cid_id, competencia_inicial);

CREATE INDEX IF NOT EXISTS idx_sigtap_proc_cid_procedimento_id
    ON public.sigtap_procedimento_cid (procedimento_id);

CREATE INDEX IF NOT EXISTS idx_sigtap_proc_cid_cid_id
    ON public.sigtap_procedimento_cid (cid_id);

-- Procedimento Ocupa??o
CREATE TABLE IF NOT EXISTS public.sigtap_procedimento_ocupacao (
    id UUID PRIMARY KEY,
    criado_em TIMESTAMPTZ NOT NULL DEFAULT now(),
    atualizado_em TIMESTAMPTZ NULL,
    ativo BOOLEAN NOT NULL DEFAULT true,

    procedimento_id UUID NOT NULL REFERENCES public.sigtap_procedimento(id) ON DELETE CASCADE,
    ocupacao_id UUID NOT NULL REFERENCES public.sigtap_ocupacao(id) ON DELETE RESTRICT,
    competencia_inicial VARCHAR(6) NULL,
    competencia_final VARCHAR(6) NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_sigtap_proc_ocup_proc_ocup_comp
    ON public.sigtap_procedimento_ocupacao (procedimento_id, ocupacao_id, competencia_inicial);

CREATE INDEX IF NOT EXISTS idx_sigtap_proc_ocup_procedimento_id
    ON public.sigtap_procedimento_ocupacao (procedimento_id);

CREATE INDEX IF NOT EXISTS idx_sigtap_proc_ocup_ocupacao_id
    ON public.sigtap_procedimento_ocupacao (ocupacao_id);

-- Procedimento Habilitacao
CREATE TABLE IF NOT EXISTS public.sigtap_procedimento_habilitacao (
    id UUID PRIMARY KEY,
    criado_em TIMESTAMPTZ NOT NULL DEFAULT now(),
    atualizado_em TIMESTAMPTZ NULL,
    ativo BOOLEAN NOT NULL DEFAULT true,

    procedimento_id UUID NOT NULL REFERENCES public.sigtap_procedimento(id) ON DELETE CASCADE,
    habilitacao_id UUID NOT NULL REFERENCES public.sigtap_habilitacao(id) ON DELETE RESTRICT,
    grupo_habilitacao_id UUID NULL REFERENCES public.sigtap_grupo_habilitacao(id) ON DELETE SET NULL,
    competencia_inicial VARCHAR(6) NULL,
    competencia_final VARCHAR(6) NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_sigtap_proc_hab_proc_hab_comp
    ON public.sigtap_procedimento_habilitacao (procedimento_id, habilitacao_id, competencia_inicial);

CREATE INDEX IF NOT EXISTS idx_sigtap_proc_hab_procedimento_id
    ON public.sigtap_procedimento_habilitacao (procedimento_id);

CREATE INDEX IF NOT EXISTS idx_sigtap_proc_hab_habilitacao_id
    ON public.sigtap_procedimento_habilitacao (habilitacao_id);

-- Procedimento Leito
CREATE TABLE IF NOT EXISTS public.sigtap_procedimento_leito (
    id UUID PRIMARY KEY,
    criado_em TIMESTAMPTZ NOT NULL DEFAULT now(),
    atualizado_em TIMESTAMPTZ NULL,
    ativo BOOLEAN NOT NULL DEFAULT true,

    procedimento_id UUID NOT NULL REFERENCES public.sigtap_procedimento(id) ON DELETE CASCADE,
    tipo_leito_id UUID NOT NULL REFERENCES public.sigtap_tipo_leito(id) ON DELETE RESTRICT,
    competencia_inicial VARCHAR(6) NULL,
    competencia_final VARCHAR(6) NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_sigtap_proc_leito_proc_leito_comp
    ON public.sigtap_procedimento_leito (procedimento_id, tipo_leito_id, competencia_inicial);

CREATE INDEX IF NOT EXISTS idx_sigtap_proc_leito_procedimento_id
    ON public.sigtap_procedimento_leito (procedimento_id);

CREATE INDEX IF NOT EXISTS idx_sigtap_proc_leito_tipo_leito_id
    ON public.sigtap_procedimento_leito (tipo_leito_id);

-- Procedimento Servico
CREATE TABLE IF NOT EXISTS public.sigtap_procedimento_servico (
    id UUID PRIMARY KEY,
    criado_em TIMESTAMPTZ NOT NULL DEFAULT now(),
    atualizado_em TIMESTAMPTZ NULL,
    ativo BOOLEAN NOT NULL DEFAULT true,

    procedimento_id UUID NOT NULL REFERENCES public.sigtap_procedimento(id) ON DELETE CASCADE,
    servico_classificacao_id UUID NOT NULL REFERENCES public.sigtap_servico_classificacao(id) ON DELETE RESTRICT,
    competencia_inicial VARCHAR(6) NULL,
    competencia_final VARCHAR(6) NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_sigtap_proc_serv_proc_serv_comp
    ON public.sigtap_procedimento_servico (procedimento_id, servico_classificacao_id, competencia_inicial);

CREATE INDEX IF NOT EXISTS idx_sigtap_proc_serv_procedimento_id
    ON public.sigtap_procedimento_servico (procedimento_id);

CREATE INDEX IF NOT EXISTS idx_sigtap_proc_serv_servico_class_id
    ON public.sigtap_procedimento_servico (servico_classificacao_id);

-- Procedimento Incremento
CREATE TABLE IF NOT EXISTS public.sigtap_procedimento_incremento (
    id UUID PRIMARY KEY,
    criado_em TIMESTAMPTZ NOT NULL DEFAULT now(),
    atualizado_em TIMESTAMPTZ NULL,
    ativo BOOLEAN NOT NULL DEFAULT true,

    procedimento_id UUID NOT NULL REFERENCES public.sigtap_procedimento(id) ON DELETE CASCADE,
    habilitacao_id UUID NOT NULL REFERENCES public.sigtap_habilitacao(id) ON DELETE RESTRICT,
    valor_percentual_sh NUMERIC(7,2) NULL,
    valor_percentual_sa NUMERIC(7,2) NULL,
    valor_percentual_sp NUMERIC(7,2) NULL,
    competencia_inicial VARCHAR(6) NULL,
    competencia_final VARCHAR(6) NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_sigtap_proc_inc_proc_hab_comp
    ON public.sigtap_procedimento_incremento (procedimento_id, habilitacao_id, competencia_inicial);

CREATE INDEX IF NOT EXISTS idx_sigtap_proc_inc_procedimento_id
    ON public.sigtap_procedimento_incremento (procedimento_id);

CREATE INDEX IF NOT EXISTS idx_sigtap_proc_inc_habilitacao_id
    ON public.sigtap_procedimento_incremento (habilitacao_id);

-- Procedimento Componente Rede
CREATE TABLE IF NOT EXISTS public.sigtap_procedimento_componente_rede (
    id UUID PRIMARY KEY,
    criado_em TIMESTAMPTZ NOT NULL DEFAULT now(),
    atualizado_em TIMESTAMPTZ NULL,
    ativo BOOLEAN NOT NULL DEFAULT true,

    procedimento_id UUID NOT NULL REFERENCES public.sigtap_procedimento(id) ON DELETE CASCADE,
    componente_rede_id UUID NOT NULL REFERENCES public.sigtap_componente_rede(id) ON DELETE RESTRICT
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_sigtap_proc_comp_rede_proc_comp
    ON public.sigtap_procedimento_componente_rede (procedimento_id, componente_rede_id);

CREATE INDEX IF NOT EXISTS idx_sigtap_proc_comp_rede_procedimento_id
    ON public.sigtap_procedimento_componente_rede (procedimento_id);

CREATE INDEX IF NOT EXISTS idx_sigtap_proc_comp_rede_componente_rede_id
    ON public.sigtap_procedimento_componente_rede (componente_rede_id);

-- Procedimento Origem
CREATE TABLE IF NOT EXISTS public.sigtap_procedimento_origem (
    id UUID PRIMARY KEY,
    criado_em TIMESTAMPTZ NOT NULL DEFAULT now(),
    atualizado_em TIMESTAMPTZ NULL,
    ativo BOOLEAN NOT NULL DEFAULT true,

    procedimento_id UUID NOT NULL REFERENCES public.sigtap_procedimento(id) ON DELETE CASCADE,
    procedimento_origem_id UUID NOT NULL REFERENCES public.sigtap_procedimento(id) ON DELETE RESTRICT,
    competencia_inicial VARCHAR(6) NULL,
    competencia_final VARCHAR(6) NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_sigtap_proc_origem_proc_origem_comp
    ON public.sigtap_procedimento_origem (procedimento_id, procedimento_origem_id, competencia_inicial);

CREATE INDEX IF NOT EXISTS idx_sigtap_proc_origem_procedimento_id
    ON public.sigtap_procedimento_origem (procedimento_id);

CREATE INDEX IF NOT EXISTS idx_sigtap_proc_origem_procedimento_origem_id
    ON public.sigtap_procedimento_origem (procedimento_origem_id);

-- Procedimento SIA/SIH
CREATE TABLE IF NOT EXISTS public.sigtap_procedimento_sia_sih (
    id UUID PRIMARY KEY,
    criado_em TIMESTAMPTZ NOT NULL DEFAULT now(),
    atualizado_em TIMESTAMPTZ NULL,
    ativo BOOLEAN NOT NULL DEFAULT true,

    procedimento_id UUID NOT NULL REFERENCES public.sigtap_procedimento(id) ON DELETE CASCADE,
    sia_sih_id UUID NOT NULL REFERENCES public.sigtap_sia_sih(id) ON DELETE RESTRICT,
    tipo_procedimento CHAR(1) NULL,
    competencia_inicial VARCHAR(6) NULL,
    competencia_final VARCHAR(6) NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_sigtap_proc_sia_sih_proc_sia_comp
    ON public.sigtap_procedimento_sia_sih (procedimento_id, sia_sih_id, competencia_inicial);

CREATE INDEX IF NOT EXISTS idx_sigtap_proc_sia_sih_procedimento_id
    ON public.sigtap_procedimento_sia_sih (procedimento_id);

CREATE INDEX IF NOT EXISTS idx_sigtap_proc_sia_sih_sia_sih_id
    ON public.sigtap_procedimento_sia_sih (sia_sih_id);

-- Procedimento Regra Condicionada
CREATE TABLE IF NOT EXISTS public.sigtap_procedimento_regra_condicionada (
    id UUID PRIMARY KEY,
    criado_em TIMESTAMPTZ NOT NULL DEFAULT now(),
    atualizado_em TIMESTAMPTZ NULL,
    ativo BOOLEAN NOT NULL DEFAULT true,

    procedimento_id UUID NOT NULL REFERENCES public.sigtap_procedimento(id) ON DELETE CASCADE,
    regra_condicionada_id UUID NOT NULL REFERENCES public.sigtap_regra_condicionada(id) ON DELETE RESTRICT
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_sigtap_proc_regra_proc_regra
    ON public.sigtap_procedimento_regra_condicionada (procedimento_id, regra_condicionada_id);

CREATE INDEX IF NOT EXISTS idx_sigtap_proc_regra_procedimento_id
    ON public.sigtap_procedimento_regra_condicionada (procedimento_id);

CREATE INDEX IF NOT EXISTS idx_sigtap_proc_regra_regra_condicionada_id
    ON public.sigtap_procedimento_regra_condicionada (regra_condicionada_id);

-- Procedimento RENASES
CREATE TABLE IF NOT EXISTS public.sigtap_procedimento_renases (
    id UUID PRIMARY KEY,
    criado_em TIMESTAMPTZ NOT NULL DEFAULT now(),
    atualizado_em TIMESTAMPTZ NULL,
    ativo BOOLEAN NOT NULL DEFAULT true,

    procedimento_id UUID NOT NULL REFERENCES public.sigtap_procedimento(id) ON DELETE CASCADE,
    renases_id UUID NOT NULL REFERENCES public.sigtap_renases(id) ON DELETE RESTRICT
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_sigtap_proc_renases_proc_renases
    ON public.sigtap_procedimento_renases (procedimento_id, renases_id);

CREATE INDEX IF NOT EXISTS idx_sigtap_proc_renases_procedimento_id
    ON public.sigtap_procedimento_renases (procedimento_id);

CREATE INDEX IF NOT EXISTS idx_sigtap_proc_renases_renases_id
    ON public.sigtap_procedimento_renases (renases_id);

-- Procedimento TUSS
CREATE TABLE IF NOT EXISTS public.sigtap_procedimento_tuss (
    id UUID PRIMARY KEY,
    criado_em TIMESTAMPTZ NOT NULL DEFAULT now(),
    atualizado_em TIMESTAMPTZ NULL,
    ativo BOOLEAN NOT NULL DEFAULT true,

    procedimento_id UUID NOT NULL REFERENCES public.sigtap_procedimento(id) ON DELETE CASCADE,
    tuss_id UUID NOT NULL REFERENCES public.sigtap_tuss(id) ON DELETE RESTRICT
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_sigtap_proc_tuss_proc_tuss
    ON public.sigtap_procedimento_tuss (procedimento_id, tuss_id);

CREATE INDEX IF NOT EXISTS idx_sigtap_proc_tuss_procedimento_id
    ON public.sigtap_procedimento_tuss (procedimento_id);

CREATE INDEX IF NOT EXISTS idx_sigtap_proc_tuss_tuss_id
    ON public.sigtap_procedimento_tuss (tuss_id);

-- Procedimento Modalidade
CREATE TABLE IF NOT EXISTS public.sigtap_procedimento_modalidade (
    id UUID PRIMARY KEY,
    criado_em TIMESTAMPTZ NOT NULL DEFAULT now(),
    atualizado_em TIMESTAMPTZ NULL,
    ativo BOOLEAN NOT NULL DEFAULT true,

    procedimento_id UUID NOT NULL REFERENCES public.sigtap_procedimento(id) ON DELETE CASCADE,
    modalidade_id UUID NOT NULL REFERENCES public.sigtap_modalidade(id) ON DELETE RESTRICT,
    competencia_inicial VARCHAR(6) NULL,
    competencia_final VARCHAR(6) NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_sigtap_proc_modal_proc_modal_comp
    ON public.sigtap_procedimento_modalidade (procedimento_id, modalidade_id, competencia_inicial);

CREATE INDEX IF NOT EXISTS idx_sigtap_proc_modal_procedimento_id
    ON public.sigtap_procedimento_modalidade (procedimento_id);

CREATE INDEX IF NOT EXISTS idx_sigtap_proc_modal_modalidade_id
    ON public.sigtap_procedimento_modalidade (modalidade_id);

-- Procedimento Registro
CREATE TABLE IF NOT EXISTS public.sigtap_procedimento_registro (
    id UUID PRIMARY KEY,
    criado_em TIMESTAMPTZ NOT NULL DEFAULT now(),
    atualizado_em TIMESTAMPTZ NULL,
    ativo BOOLEAN NOT NULL DEFAULT true,

    procedimento_id UUID NOT NULL REFERENCES public.sigtap_procedimento(id) ON DELETE CASCADE,
    registro_id UUID NOT NULL REFERENCES public.sigtap_registro(id) ON DELETE RESTRICT,
    competencia_inicial VARCHAR(6) NULL,
    competencia_final VARCHAR(6) NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_sigtap_proc_reg_proc_reg_comp
    ON public.sigtap_procedimento_registro (procedimento_id, registro_id, competencia_inicial);

CREATE INDEX IF NOT EXISTS idx_sigtap_proc_reg_procedimento_id
    ON public.sigtap_procedimento_registro (procedimento_id);

CREATE INDEX IF NOT EXISTS idx_sigtap_proc_reg_registro_id
    ON public.sigtap_procedimento_registro (registro_id);

-- Procedimento Detalhe Item
CREATE TABLE IF NOT EXISTS public.sigtap_procedimento_detalhe_item (
    id UUID PRIMARY KEY,
    criado_em TIMESTAMPTZ NOT NULL DEFAULT now(),
    atualizado_em TIMESTAMPTZ NULL,
    ativo BOOLEAN NOT NULL DEFAULT true,

    procedimento_id UUID NOT NULL REFERENCES public.sigtap_procedimento(id) ON DELETE CASCADE,
    detalhe_id UUID NOT NULL REFERENCES public.sigtap_detalhe(id) ON DELETE RESTRICT
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_sigtap_proc_det_item_proc_det
    ON public.sigtap_procedimento_detalhe_item (procedimento_id, detalhe_id);

CREATE INDEX IF NOT EXISTS idx_sigtap_proc_det_item_procedimento_id
    ON public.sigtap_procedimento_detalhe_item (procedimento_id);

CREATE INDEX IF NOT EXISTS idx_sigtap_proc_det_item_detalhe_id
    ON public.sigtap_procedimento_detalhe_item (detalhe_id);

-- Exce??o Compatibilidade
CREATE TABLE IF NOT EXISTS public.sigtap_excecao_compatibilidade (
    id UUID PRIMARY KEY,
    criado_em TIMESTAMPTZ NOT NULL DEFAULT now(),
    atualizado_em TIMESTAMPTZ NULL,
    ativo BOOLEAN NOT NULL DEFAULT true,

    procedimento_restricao_id UUID NOT NULL REFERENCES public.sigtap_procedimento(id) ON DELETE RESTRICT,
    procedimento_principal_id UUID NOT NULL REFERENCES public.sigtap_procedimento(id) ON DELETE RESTRICT,
    registro_principal_id UUID NULL REFERENCES public.sigtap_registro(id) ON DELETE SET NULL,
    procedimento_compativel_id UUID NOT NULL REFERENCES public.sigtap_procedimento(id) ON DELETE RESTRICT,
    registro_compativel_id UUID NULL REFERENCES public.sigtap_registro(id) ON DELETE SET NULL,
    tipo_compatibilidade CHAR(1) NULL,
    competencia_inicial VARCHAR(6) NULL,
    competencia_final VARCHAR(6) NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_sigtap_exc_comp_chave_natural
    ON public.sigtap_excecao_compatibilidade (procedimento_restricao_id, procedimento_principal_id, procedimento_compativel_id, competencia_inicial);

CREATE INDEX IF NOT EXISTS idx_sigtap_exc_comp_restricao_id
    ON public.sigtap_excecao_compatibilidade (procedimento_restricao_id);

CREATE INDEX IF NOT EXISTS idx_sigtap_exc_comp_principal_id
    ON public.sigtap_excecao_compatibilidade (procedimento_principal_id);

CREATE INDEX IF NOT EXISTS idx_sigtap_exc_comp_compativel_id
    ON public.sigtap_excecao_compatibilidade (procedimento_compativel_id);
