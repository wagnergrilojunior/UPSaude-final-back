-- =====================================================
-- MIGRATION: Estruturas SIGTAP (SOA-SIGTAP DATASUS)
-- =====================================================
-- Objetivo: Persistir dados sincronizados do SIGTAP para consumo interno via REST
-- Autor: UPSa?de
-- =====================================================

-- Observa??o: usamos IF NOT EXISTS para suportar ambientes que possam ter sido
-- provisionados manualmente antes do Flyway, sem quebrar a inicializa??o.

CREATE TABLE IF NOT EXISTS public.sigtap_grupo (
    id UUID PRIMARY KEY,
    criado_em TIMESTAMPTZ NOT NULL DEFAULT now(),
    atualizado_em TIMESTAMPTZ NULL,
    ativo BOOLEAN NOT NULL DEFAULT true,

    codigo_oficial VARCHAR(20) NOT NULL,
    nome VARCHAR(255) NULL,
    competencia_inicial VARCHAR(6) NULL,
    competencia_final VARCHAR(6) NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_sigtap_grupo_codigo_oficial
    ON public.sigtap_grupo (codigo_oficial);

CREATE INDEX IF NOT EXISTS idx_sigtap_grupo_nome
    ON public.sigtap_grupo (nome);


CREATE TABLE IF NOT EXISTS public.sigtap_subgrupo (
    id UUID PRIMARY KEY,
    criado_em TIMESTAMPTZ NOT NULL DEFAULT now(),
    atualizado_em TIMESTAMPTZ NULL,
    ativo BOOLEAN NOT NULL DEFAULT true,

    grupo_id UUID NOT NULL REFERENCES public.sigtap_grupo(id) ON DELETE RESTRICT,
    codigo_oficial VARCHAR(20) NOT NULL,
    nome VARCHAR(255) NULL,
    competencia_inicial VARCHAR(6) NULL,
    competencia_final VARCHAR(6) NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_sigtap_subgrupo_grupo_codigo
    ON public.sigtap_subgrupo (grupo_id, codigo_oficial);

CREATE INDEX IF NOT EXISTS idx_sigtap_subgrupo_nome
    ON public.sigtap_subgrupo (nome);

CREATE INDEX IF NOT EXISTS idx_sigtap_subgrupo_grupo_id
    ON public.sigtap_subgrupo (grupo_id);


CREATE TABLE IF NOT EXISTS public.sigtap_forma_organizacao (
    id UUID PRIMARY KEY,
    criado_em TIMESTAMPTZ NOT NULL DEFAULT now(),
    atualizado_em TIMESTAMPTZ NULL,
    ativo BOOLEAN NOT NULL DEFAULT true,

    subgrupo_id UUID NOT NULL REFERENCES public.sigtap_subgrupo(id) ON DELETE RESTRICT,
    codigo_oficial VARCHAR(20) NOT NULL,
    nome VARCHAR(255) NULL,
    competencia_inicial VARCHAR(6) NULL,
    competencia_final VARCHAR(6) NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_sigtap_forma_org_subgrupo_codigo
    ON public.sigtap_forma_organizacao (subgrupo_id, codigo_oficial);

CREATE INDEX IF NOT EXISTS idx_sigtap_forma_org_nome
    ON public.sigtap_forma_organizacao (nome);

CREATE INDEX IF NOT EXISTS idx_sigtap_forma_org_subgrupo_id
    ON public.sigtap_forma_organizacao (subgrupo_id);


CREATE TABLE IF NOT EXISTS public.sigtap_procedimento (
    id UUID PRIMARY KEY,
    criado_em TIMESTAMPTZ NOT NULL DEFAULT now(),
    atualizado_em TIMESTAMPTZ NULL,
    ativo BOOLEAN NOT NULL DEFAULT true,

    forma_organizacao_id UUID NULL REFERENCES public.sigtap_forma_organizacao(id) ON DELETE SET NULL,

    codigo_oficial VARCHAR(20) NOT NULL,
    nome VARCHAR(255) NULL,
    competencia_inicial VARCHAR(6) NULL,
    competencia_final VARCHAR(6) NULL,

    -- Campos comuns do ProcedimentoType (para consulta sem precisar do detalhe)
    sexo_permitido VARCHAR(30) NULL,
    idade_minima SMALLINT NULL,
    idade_maxima SMALLINT NULL,
    media_dias_internacao SMALLINT NULL,
    quantidade_maxima_dias SMALLINT NULL,
    limite_maximo INTEGER NULL,

    valor_servico_hospitalar NUMERIC(14,2) NULL,
    valor_servico_ambulatorial NUMERIC(14,2) NULL,
    valor_servico_profissional NUMERIC(14,2) NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_sigtap_procedimento_codigo_comp_ini
    ON public.sigtap_procedimento (codigo_oficial, competencia_inicial);

CREATE INDEX IF NOT EXISTS idx_sigtap_procedimento_nome
    ON public.sigtap_procedimento (nome);

CREATE INDEX IF NOT EXISTS idx_sigtap_procedimento_forma_org
    ON public.sigtap_procedimento (forma_organizacao_id);


CREATE TABLE IF NOT EXISTS public.sigtap_procedimento_detalhe (
    id UUID PRIMARY KEY,
    criado_em TIMESTAMPTZ NOT NULL DEFAULT now(),
    atualizado_em TIMESTAMPTZ NULL,
    ativo BOOLEAN NOT NULL DEFAULT true,

    procedimento_id UUID NOT NULL REFERENCES public.sigtap_procedimento(id) ON DELETE CASCADE,
    competencia_inicial VARCHAR(6) NULL,
    competencia_final VARCHAR(6) NULL,

    descricao_completa TEXT NULL,

    -- Detalhes adicionais do SIGTAP (listas podem ser grandes; persistimos como JSONB)
    cids JSONB NULL,
    cbos JSONB NULL,
    categorias_cbo JSONB NULL,
    tipos_leito JSONB NULL,
    servicos_classificacoes JSONB NULL,
    habilitacoes JSONB NULL,
    grupos_habilitacao JSONB NULL,
    incrementos JSONB NULL,
    componentes_rede JSONB NULL,
    origens_sigtap JSONB NULL,
    origens_sia_sih JSONB NULL,
    regras_condicionadas JSONB NULL,
    renases JSONB NULL,
    tuss JSONB NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_sigtap_procedimento_detalhe_procedimento
    ON public.sigtap_procedimento_detalhe (procedimento_id);


CREATE TABLE IF NOT EXISTS public.sigtap_compatibilidade_possivel (
    id UUID PRIMARY KEY,
    criado_em TIMESTAMPTZ NOT NULL DEFAULT now(),
    atualizado_em TIMESTAMPTZ NULL,
    ativo BOOLEAN NOT NULL DEFAULT true,

    codigo_oficial VARCHAR(20) NOT NULL,
    nome VARCHAR(255) NULL,
    competencia_inicial VARCHAR(6) NULL,
    competencia_final VARCHAR(6) NULL,

    tipo_compatibilidade VARCHAR(30) NULL,
    instrumento_registro_principal JSONB NULL,
    instrumento_registro_secundario JSONB NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_sigtap_comp_possivel_codigo
    ON public.sigtap_compatibilidade_possivel (codigo_oficial);


CREATE TABLE IF NOT EXISTS public.sigtap_compatibilidade (
    id UUID PRIMARY KEY,
    criado_em TIMESTAMPTZ NOT NULL DEFAULT now(),
    atualizado_em TIMESTAMPTZ NULL,
    ativo BOOLEAN NOT NULL DEFAULT true,

    compatibilidade_possivel_id UUID NOT NULL REFERENCES public.sigtap_compatibilidade_possivel(id) ON DELETE RESTRICT,
    procedimento_principal_id UUID NOT NULL REFERENCES public.sigtap_procedimento(id) ON DELETE RESTRICT,
    procedimento_secundario_id UUID NOT NULL REFERENCES public.sigtap_procedimento(id) ON DELETE RESTRICT,

    competencia_inicial VARCHAR(6) NULL,
    competencia_final VARCHAR(6) NULL,
    quantidade_permitida INTEGER NULL,

    documento_publicacao JSONB NULL,
    documento_revogacao JSONB NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_sigtap_compatibilidade_chave_natural
    ON public.sigtap_compatibilidade (compatibilidade_possivel_id, procedimento_principal_id, procedimento_secundario_id, competencia_inicial);

CREATE INDEX IF NOT EXISTS idx_sigtap_compatibilidade_principal
    ON public.sigtap_compatibilidade (procedimento_principal_id);

CREATE INDEX IF NOT EXISTS idx_sigtap_compatibilidade_secundario
    ON public.sigtap_compatibilidade (procedimento_secundario_id);

