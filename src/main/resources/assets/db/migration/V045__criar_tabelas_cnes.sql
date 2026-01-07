-- =====================================================
-- MIGRATION: Criar Tabelas CNES
-- =====================================================
-- Objetivo: Criar tabelas para integração com SOA-CNES (DATASUS)
-- Autor: UPSaúde
-- =====================================================

-- ========== TABELA cnes_sincronizacao ==========
-- Tabela de controle de sincronizações CNES
CREATE TABLE IF NOT EXISTS public.cnes_sincronizacao (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    criado_em TIMESTAMPTZ NOT NULL DEFAULT now(),
    atualizado_em TIMESTAMPTZ NULL,
    ativo BOOLEAN NOT NULL DEFAULT true,
    tenant_id UUID NOT NULL,
    
    -- Identificação
    tipo_entidade INTEGER NOT NULL, -- 1=ESTABELECIMENTO, 2=PROFISSIONAL, 3=EQUIPE, 4=VINCULACAO, 5=EQUIPAMENTO, 6=LEITO
    entidade_id UUID NULL,
    codigo_identificador VARCHAR(50) NULL, -- CNES, CNS, INE, etc
    competencia VARCHAR(6) NULL, -- Formato AAAAMM
    
    -- Status e execução
    status INTEGER NOT NULL, -- 1=PENDENTE, 2=PROCESSANDO, 3=SUCESSO, 4=ERRO
    data_sincronizacao TIMESTAMPTZ NOT NULL DEFAULT now(),
    data_fim TIMESTAMPTZ NULL,
    
    -- Estatísticas
    registros_inseridos INTEGER NOT NULL DEFAULT 0,
    registros_atualizados INTEGER NOT NULL DEFAULT 0,
    registros_erro INTEGER NOT NULL DEFAULT 0,
    
    -- Erros
    mensagem_erro TEXT NULL,
    detalhes_erro JSONB NULL,
    
    -- Relacionamentos
    estabelecimento_id UUID NULL,
    
    CONSTRAINT fk_cnes_sincronizacao_tenant FOREIGN KEY (tenant_id) REFERENCES public.tenants(id),
    CONSTRAINT fk_cnes_sincronizacao_estabelecimento FOREIGN KEY (estabelecimento_id) REFERENCES public.estabelecimentos(id)
);

-- Índices para cnes_sincronizacao
CREATE INDEX IF NOT EXISTS idx_cnes_sincronizacao_tipo_entidade
    ON public.cnes_sincronizacao (tipo_entidade);

CREATE INDEX IF NOT EXISTS idx_cnes_sincronizacao_entidade_id
    ON public.cnes_sincronizacao (entidade_id);

CREATE INDEX IF NOT EXISTS idx_cnes_sincronizacao_status
    ON public.cnes_sincronizacao (status);

CREATE INDEX IF NOT EXISTS idx_cnes_sincronizacao_data_sincronizacao
    ON public.cnes_sincronizacao (data_sincronizacao);

CREATE INDEX IF NOT EXISTS idx_cnes_sincronizacao_codigo_identificador
    ON public.cnes_sincronizacao (codigo_identificador);

CREATE INDEX IF NOT EXISTS idx_cnes_sincronizacao_estabelecimento
    ON public.cnes_sincronizacao (estabelecimento_id);

CREATE INDEX IF NOT EXISTS idx_cnes_sincronizacao_tenant_tipo_status
    ON public.cnes_sincronizacao (tenant_id, tipo_entidade, status);

-- ========== TABELA cnes_historico_estabelecimento ==========
-- Histórico de sincronizações de estabelecimento por competência
CREATE TABLE IF NOT EXISTS public.cnes_historico_estabelecimento (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    criado_em TIMESTAMPTZ NOT NULL DEFAULT now(),
    atualizado_em TIMESTAMPTZ NULL,
    ativo BOOLEAN NOT NULL DEFAULT true,
    tenant_id UUID NOT NULL,
    
    estabelecimento_id UUID NOT NULL,
    competencia VARCHAR(6) NOT NULL, -- Formato AAAAMM
    dados_jsonb JSONB NOT NULL, -- Dados completos da consulta CNES
    data_sincronizacao TIMESTAMPTZ NOT NULL DEFAULT now(),
    
    CONSTRAINT fk_cnes_historico_tenant FOREIGN KEY (tenant_id) REFERENCES public.tenants(id),
    CONSTRAINT fk_cnes_historico_estabelecimento FOREIGN KEY (estabelecimento_id) REFERENCES public.estabelecimentos(id),
    CONSTRAINT uk_cnes_historico_estabelecimento_competencia UNIQUE (estabelecimento_id, competencia)
);

-- Índices para cnes_historico_estabelecimento
CREATE INDEX IF NOT EXISTS idx_cnes_historico_estabelecimento_id
    ON public.cnes_historico_estabelecimento (estabelecimento_id);

CREATE INDEX IF NOT EXISTS idx_cnes_historico_competencia
    ON public.cnes_historico_estabelecimento (competencia);

CREATE INDEX IF NOT EXISTS idx_cnes_historico_data_sincronizacao
    ON public.cnes_historico_estabelecimento (data_sincronizacao);

CREATE INDEX IF NOT EXISTS idx_cnes_historico_tenant_estabelecimento
    ON public.cnes_historico_estabelecimento (tenant_id, estabelecimento_id);

-- ========== TABELA leitos ==========
-- Leitos de estabelecimentos sincronizados do CNES
CREATE TABLE IF NOT EXISTS public.leitos (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    criado_em TIMESTAMPTZ NOT NULL DEFAULT now(),
    atualizado_em TIMESTAMPTZ NULL,
    ativo BOOLEAN NOT NULL DEFAULT true,
    tenant_id UUID NOT NULL,
    
    estabelecimento_id UUID NOT NULL,
    codigo_cnes_leito VARCHAR(50) NULL, -- Código oficial CNES do leito
    numero_leito VARCHAR(50) NULL, -- Número interno do leito
    tipo_leito_id UUID NULL, -- FK para sigtap_tipo_leito
    setor_unidade VARCHAR(255) NULL,
    andar VARCHAR(50) NULL,
    sala VARCHAR(50) NULL,
    status INTEGER NOT NULL, -- 1=DISPONIVEL, 2=OCUPADO, 3=MANUTENCAO, 4=INATIVO
    data_ativacao TIMESTAMPTZ NOT NULL DEFAULT now(),
    data_inativacao TIMESTAMPTZ NULL,
    observacoes TEXT NULL,
    
    CONSTRAINT fk_leitos_tenant FOREIGN KEY (tenant_id) REFERENCES public.tenants(id),
    CONSTRAINT fk_leitos_estabelecimento FOREIGN KEY (estabelecimento_id) REFERENCES public.estabelecimentos(id),
    CONSTRAINT fk_leitos_tipo_leito FOREIGN KEY (tipo_leito_id) REFERENCES public.sigtap_tipo_leito(id)
);

-- Índices para leitos
CREATE INDEX IF NOT EXISTS idx_leitos_codigo_cnes
    ON public.leitos (codigo_cnes_leito);

CREATE INDEX IF NOT EXISTS idx_leitos_estabelecimento
    ON public.leitos (estabelecimento_id);

CREATE INDEX IF NOT EXISTS idx_leitos_status
    ON public.leitos (status);

CREATE INDEX IF NOT EXISTS idx_leitos_tenant_estabelecimento
    ON public.leitos (tenant_id, estabelecimento_id);

CREATE INDEX IF NOT EXISTS idx_leitos_estabelecimento_status
    ON public.leitos (estabelecimento_id, status);

