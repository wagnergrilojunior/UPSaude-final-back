-- =====================================================
-- MIGRATION: SIA-PA (enriquecimento + agregações)
-- =====================================================
-- Objetivo:
-- 1) Garantir existência da tabela base public.sia_pa (caso não exista)
-- 2) Criar view enriquecida (joins com SIGTAP/CID e dados descritivos de estabelecimento)
-- 3) Criar views materializadas de agregação para performance (KPIs, relatórios, analytics)
-- Data: 2026-01-14
-- =====================================================

-- Dependência comum para UUID
CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- 1) Tabela base (defensivo: alguns ambientes tinham esse DDL fora de db/migration)
CREATE TABLE IF NOT EXISTS public.sia_pa (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    criado_em TIMESTAMPTZ NOT NULL DEFAULT now(),
    atualizado_em TIMESTAMPTZ,
    ativo BOOLEAN NOT NULL DEFAULT true,

    -- Competência e controle
    competencia VARCHAR(6) NOT NULL,
    uf VARCHAR(2) NOT NULL,
    mes_movimentacao VARCHAR(6),

    -- Estabelecimento
    codigo_cnes VARCHAR(7) NOT NULL,
    municipio_ufmun_codigo VARCHAR(6),
    municipio_gestao_codigo VARCHAR(6),
    condicao_estabelecimento VARCHAR(2),
    regiao_controle VARCHAR(4),
    inconsistencia_saida VARCHAR(4),
    inconsistencia_urgencia VARCHAR(4),
    tipo_unidade VARCHAR(2),
    tipo_prestador VARCHAR(2),
    indicador_manual VARCHAR(1),
    cnpj_cpf VARCHAR(14),
    cnpj_mantenedor VARCHAR(14),
    cnpj_contratante VARCHAR(14),
    natureza_juridica VARCHAR(4),
    codigo_orgao_contratante TEXT,
    servico_contratualizado TEXT,
    codigo_ine TEXT,

    -- Procedimento
    procedimento_codigo VARCHAR(10) NOT NULL,
    nivel_complexidade VARCHAR(1),
    indicador VARCHAR(5),

    -- Financiamento
    tipo_financiamento VARCHAR(2),
    subfinanciamento VARCHAR(4),

    -- Documentação e autorização
    documento_origem VARCHAR(13),
    numero_autorizacao VARCHAR(15),
    tipo_documento_origem VARCHAR(1),

    -- Profissional
    cns_profissional VARCHAR(15),
    cbo_codigo VARCHAR(6),

    -- CID (Diagnóstico)
    cid_principal_codigo VARCHAR(10),
    cid_secundario_codigo VARCHAR(10),
    cid_causa_codigo VARCHAR(10),

    -- Atendimento
    carater_atendimento VARCHAR(2),

    -- Usuário/Paciente
    idade INTEGER,
    idade_minima INTEGER,
    idade_maxima INTEGER,
    flag_idade VARCHAR(1),
    sexo VARCHAR(1),
    raca_cor VARCHAR(2),
    etnia TEXT,
    municipio_paciente_codigo VARCHAR(6),

    -- Controle de fluxo/status
    motivo_saida VARCHAR(2),
    flag_obito VARCHAR(1),
    flag_encerramento VARCHAR(1),
    flag_permanencia VARCHAR(1),
    flag_alta VARCHAR(1),
    flag_transferencia VARCHAR(1),

    -- Produção e quantidades
    quantidade_produzida INTEGER,
    quantidade_aprovada INTEGER,
    flag_quantidade VARCHAR(1),
    flag_erro VARCHAR(1),

    -- Valores financeiros
    valor_produzido NUMERIC(14, 2),
    valor_aprovado NUMERIC(14, 2),
    valor_cofinanciado NUMERIC(14, 2),
    valor_clinico NUMERIC(14, 2),
    valor_incrementado NUMERIC(14, 2),
    valor_total_vpa NUMERIC(14, 2),
    total_pa NUMERIC(14, 2),

    -- Diferenças
    uf_diferente VARCHAR(1),
    municipio_diferente VARCHAR(1),
    diferenca_valor NUMERIC(14, 2)
);

CREATE INDEX IF NOT EXISTS idx_sia_pa_competencia ON public.sia_pa(competencia);
CREATE INDEX IF NOT EXISTS idx_sia_pa_uf ON public.sia_pa(uf);
CREATE INDEX IF NOT EXISTS idx_sia_pa_procedimento ON public.sia_pa(procedimento_codigo);
CREATE INDEX IF NOT EXISTS idx_sia_pa_cid_principal ON public.sia_pa(cid_principal_codigo);
CREATE INDEX IF NOT EXISTS idx_sia_pa_cnes ON public.sia_pa(codigo_cnes);
CREATE INDEX IF NOT EXISTS idx_sia_pa_competencia_uf ON public.sia_pa(competencia, uf);
CREATE INDEX IF NOT EXISTS idx_sia_pa_mes_movimentacao ON public.sia_pa(mes_movimentacao);

-- 2) View enriquecida
-- Observação importante: estabelecimentos é multi-tenant (cnes + tenant_id).
-- Para evitar duplicidade, usamos LATERAL e escolhemos um registro "mais recente" por CNES
-- apenas para fins descritivos. Para precisão por tenant, a camada de serviço pode sobrescrever
-- esses dados usando o tenant autenticado.
CREATE OR REPLACE VIEW public.sia_pa_enriquecido_view AS
SELECT
    s.id,
    s.criado_em,
    s.atualizado_em,
    s.ativo,

    s.competencia,
    s.uf,
    s.mes_movimentacao,

    s.codigo_cnes,
    s.municipio_ufmun_codigo,
    s.municipio_gestao_codigo,

    s.procedimento_codigo,
    s.cid_principal_codigo,
    s.sexo,
    s.idade,

    s.quantidade_produzida,
    s.quantidade_aprovada,
    s.flag_erro,

    s.valor_produzido,
    s.valor_aprovado,

    -- SIGTAP (procedimento)
    p.nome AS procedimento_nome,
    p.tipo_complexidade AS procedimento_tipo_complexidade,
    p.sexo_permitido AS procedimento_sexo_permitido,
    p.idade_minima AS procedimento_idade_minima,
    p.idade_maxima AS procedimento_idade_maxima,
    p.valor_servico_ambulatorial AS procedimento_valor_sigtap_ambulatorial,

    fo.nome AS procedimento_forma_organizacao_nome,
    sg.nome AS procedimento_subgrupo_nome,
    g.nome AS procedimento_grupo_nome,

    -- CID
    cid.descricao AS cid_principal_descricao,

    -- Estabelecimento (descritivo)
    est.nome AS estabelecimento_nome,
    est.cnpj AS estabelecimento_cnpj,
    est.codigo_ibge_municipio AS estabelecimento_codigo_ibge_municipio,
    est.esfera_administrativa AS estabelecimento_esfera_administrativa
FROM public.sia_pa s
LEFT JOIN LATERAL (
    SELECT p.*
    FROM public.sigtap_procedimento p
    WHERE p.codigo_oficial = s.procedimento_codigo
      AND (p.competencia_inicial IS NULL OR p.competencia_inicial <= s.competencia)
      AND (p.competencia_final IS NULL OR p.competencia_final >= s.competencia)
    ORDER BY p.competencia_inicial DESC NULLS LAST
    LIMIT 1
) p ON true
LEFT JOIN public.sigtap_forma_organizacao fo ON fo.id = p.forma_organizacao_id
LEFT JOIN public.sigtap_subgrupo sg ON sg.id = fo.subgrupo_id
LEFT JOIN public.sigtap_grupo g ON g.id = sg.grupo_id
LEFT JOIN public.cid10_subcategorias cid ON cid.subcat = s.cid_principal_codigo
LEFT JOIN LATERAL (
    SELECT
        e.nome,
        e.cnpj,
        e.codigo_ibge_municipio,
        e.esfera_administrativa
    FROM public.estabelecimentos e
    WHERE e.cnes = s.codigo_cnes
    ORDER BY e.data_ultima_sincronizacao_cnes DESC NULLS LAST
    LIMIT 1
) est ON true;

-- 3) Views materializadas de agregação

-- 3.1 Por estabelecimento (CNES) e competência
DROP MATERIALIZED VIEW IF EXISTS public.sia_pa_agregado_estabelecimento;
CREATE MATERIALIZED VIEW public.sia_pa_agregado_estabelecimento AS
SELECT
    md5(s.competencia || '|' || s.uf || '|' || COALESCE(s.codigo_cnes, '')) AS id,
    s.competencia,
    s.uf,
    s.codigo_cnes,
    COUNT(*) AS total_registros,
    COALESCE(SUM(COALESCE(s.quantidade_produzida, 0)), 0) AS quantidade_produzida_total,
    COALESCE(SUM(COALESCE(s.quantidade_aprovada, 0)), 0) AS quantidade_aprovada_total,
    COUNT(DISTINCT s.procedimento_codigo) AS procedimentos_unicos,
    COALESCE(SUM(COALESCE(s.valor_produzido, 0)), 0) AS valor_produzido_total,
    COALESCE(SUM(COALESCE(s.valor_aprovado, 0)), 0) AS valor_aprovado_total,
    CASE
        WHEN COALESCE(SUM(COALESCE(s.valor_produzido, 0)), 0) > 0
            THEN COALESCE(SUM(COALESCE(s.valor_aprovado, 0)), 0) / COALESCE(SUM(COALESCE(s.valor_produzido, 0)), 0)
        ELSE NULL
    END AS taxa_aprovacao_valor
FROM public.sia_pa s
GROUP BY s.competencia, s.uf, s.codigo_cnes;

CREATE UNIQUE INDEX IF NOT EXISTS uk_sia_pa_ag_estab_comp_uf_cnes
    ON public.sia_pa_agregado_estabelecimento (competencia, uf, codigo_cnes);

CREATE INDEX IF NOT EXISTS idx_sia_pa_ag_estab_comp_uf
    ON public.sia_pa_agregado_estabelecimento (competencia, uf);

-- 3.2 Por procedimento e competência
DROP MATERIALIZED VIEW IF EXISTS public.sia_pa_agregado_procedimento;
CREATE MATERIALIZED VIEW public.sia_pa_agregado_procedimento AS
SELECT
    md5(s.competencia || '|' || s.uf || '|' || COALESCE(s.procedimento_codigo, '')) AS id,
    s.competencia,
    s.uf,
    s.procedimento_codigo,
    COUNT(*) AS total_registros,
    COUNT(DISTINCT s.codigo_cnes) AS estabelecimentos_unicos,
    COUNT(DISTINCT s.municipio_ufmun_codigo) AS municipios_unicos,
    COALESCE(SUM(COALESCE(s.quantidade_produzida, 0)), 0) AS quantidade_produzida_total,
    COALESCE(SUM(COALESCE(s.quantidade_aprovada, 0)), 0) AS quantidade_aprovada_total,
    COALESCE(SUM(COALESCE(s.valor_aprovado, 0)), 0) AS valor_aprovado_total,
    AVG(s.valor_aprovado) AS valor_aprovado_medio
FROM public.sia_pa s
GROUP BY s.competencia, s.uf, s.procedimento_codigo;

CREATE UNIQUE INDEX IF NOT EXISTS uk_sia_pa_ag_proc_comp_uf_proc
    ON public.sia_pa_agregado_procedimento (competencia, uf, procedimento_codigo);

CREATE INDEX IF NOT EXISTS idx_sia_pa_ag_proc_comp_uf
    ON public.sia_pa_agregado_procedimento (competencia, uf);

-- 3.3 Por CID e competência (inclui top procedimento e top município)
DROP MATERIALIZED VIEW IF EXISTS public.sia_pa_agregado_cid;
CREATE MATERIALIZED VIEW public.sia_pa_agregado_cid AS
WITH base AS (
    SELECT
        s.competencia,
        s.uf,
        s.cid_principal_codigo,
        COUNT(*) AS total_registros,
        COALESCE(SUM(COALESCE(s.quantidade_produzida, 0)), 0) AS quantidade_produzida_total,
        COALESCE(SUM(COALESCE(s.valor_aprovado, 0)), 0) AS valor_aprovado_total,
        COUNT(DISTINCT s.procedimento_codigo) AS procedimentos_unicos,
        COUNT(DISTINCT s.municipio_ufmun_codigo) AS municipios_unicos
    FROM public.sia_pa s
    GROUP BY s.competencia, s.uf, s.cid_principal_codigo
)
SELECT
    md5(base.competencia || '|' || base.uf || '|' || COALESCE(base.cid_principal_codigo, '')) AS id,
    base.competencia,
    base.uf,
    base.cid_principal_codigo,
    base.total_registros,
    base.quantidade_produzida_total,
    base.valor_aprovado_total,
    base.procedimentos_unicos,
    base.municipios_unicos,
    top_proc.procedimento_codigo AS top_procedimento_codigo,
    top_proc.total AS top_procedimento_total,
    top_mun.municipio_ufmun_codigo AS top_municipio_ufmun_codigo,
    top_mun.total AS top_municipio_total
FROM base
LEFT JOIN LATERAL (
    SELECT
        s.procedimento_codigo,
        COALESCE(SUM(COALESCE(s.quantidade_produzida, 0)), 0) AS total
    FROM public.sia_pa s
    WHERE s.competencia = base.competencia
      AND s.uf = base.uf
      AND (
        (s.cid_principal_codigo IS NULL AND base.cid_principal_codigo IS NULL)
        OR s.cid_principal_codigo = base.cid_principal_codigo
      )
    GROUP BY s.procedimento_codigo
    ORDER BY total DESC NULLS LAST
    LIMIT 1
) top_proc ON true
LEFT JOIN LATERAL (
    SELECT
        s.municipio_ufmun_codigo,
        COALESCE(SUM(COALESCE(s.quantidade_produzida, 0)), 0) AS total
    FROM public.sia_pa s
    WHERE s.competencia = base.competencia
      AND s.uf = base.uf
      AND (
        (s.cid_principal_codigo IS NULL AND base.cid_principal_codigo IS NULL)
        OR s.cid_principal_codigo = base.cid_principal_codigo
      )
    GROUP BY s.municipio_ufmun_codigo
    ORDER BY total DESC NULLS LAST
    LIMIT 1
) top_mun ON true;

CREATE UNIQUE INDEX IF NOT EXISTS uk_sia_pa_ag_cid_comp_uf_cid
    ON public.sia_pa_agregado_cid (competencia, uf, cid_principal_codigo);

CREATE INDEX IF NOT EXISTS idx_sia_pa_ag_cid_comp_uf
    ON public.sia_pa_agregado_cid (competencia, uf);

-- 3.4 Temporal (tendência por competência)
DROP MATERIALIZED VIEW IF EXISTS public.sia_pa_agregado_temporal;
CREATE MATERIALIZED VIEW public.sia_pa_agregado_temporal AS
WITH base AS (
    SELECT
        s.uf,
        s.competencia,
        COALESCE(SUM(COALESCE(s.quantidade_produzida, 0)), 0) AS quantidade_produzida_total,
        COALESCE(SUM(COALESCE(s.valor_produzido, 0)), 0) AS valor_produzido_total,
        COALESCE(SUM(COALESCE(s.valor_aprovado, 0)), 0) AS valor_aprovado_total
    FROM public.sia_pa s
    GROUP BY s.uf, s.competencia
)
SELECT
    md5(base.uf || '|' || base.competencia) AS id,
    base.uf,
    base.competencia,
    base.quantidade_produzida_total,
    base.valor_produzido_total,
    base.valor_aprovado_total,
    LAG(base.quantidade_produzida_total) OVER (PARTITION BY base.uf ORDER BY base.competencia) AS quantidade_produzida_prev,
    LAG(base.valor_aprovado_total) OVER (PARTITION BY base.uf ORDER BY base.competencia) AS valor_aprovado_prev,
    (base.valor_aprovado_total - LAG(base.valor_aprovado_total) OVER (PARTITION BY base.uf ORDER BY base.competencia)) AS delta_valor_aprovado,
    CASE
        WHEN LAG(base.valor_aprovado_total) OVER (PARTITION BY base.uf ORDER BY base.competencia) > 0
            THEN (base.valor_aprovado_total / LAG(base.valor_aprovado_total) OVER (PARTITION BY base.uf ORDER BY base.competencia)) - 1
        ELSE NULL
    END AS crescimento_valor_aprovado_pct
FROM base;

CREATE UNIQUE INDEX IF NOT EXISTS uk_sia_pa_ag_tempo_uf_comp
    ON public.sia_pa_agregado_temporal (uf, competencia);

CREATE INDEX IF NOT EXISTS idx_sia_pa_ag_tempo_uf
    ON public.sia_pa_agregado_temporal (uf);

