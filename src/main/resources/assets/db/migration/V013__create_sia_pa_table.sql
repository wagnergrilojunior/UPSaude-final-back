-- Migration: Criação da tabela sia_pa (Produção Ambulatorial SIA-SUS)
-- Descrição: Tabela para armazenar registros de produção ambulatorial do SIA-SUS

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

-- Índices para melhorar performance de consultas
CREATE INDEX IF NOT EXISTS idx_sia_pa_competencia ON public.sia_pa(competencia);
CREATE INDEX IF NOT EXISTS idx_sia_pa_uf ON public.sia_pa(uf);
CREATE INDEX IF NOT EXISTS idx_sia_pa_procedimento ON public.sia_pa(procedimento_codigo);
CREATE INDEX IF NOT EXISTS idx_sia_pa_cid_principal ON public.sia_pa(cid_principal_codigo);
CREATE INDEX IF NOT EXISTS idx_sia_pa_cnes ON public.sia_pa(codigo_cnes);
CREATE INDEX IF NOT EXISTS idx_sia_pa_competencia_uf ON public.sia_pa(competencia, uf);
CREATE INDEX IF NOT EXISTS idx_sia_pa_mes_movimentacao ON public.sia_pa(mes_movimentacao);

-- Comentários na tabela
COMMENT ON TABLE public.sia_pa IS 'Tabela de produção ambulatorial do SIA-SUS';
COMMENT ON COLUMN public.sia_pa.competencia IS 'Competência no formato AAAAMM';
COMMENT ON COLUMN public.sia_pa.uf IS 'UF (Unidade Federativa) - 2 letras';
COMMENT ON COLUMN public.sia_pa.codigo_cnes IS 'Código CNES do estabelecimento';
COMMENT ON COLUMN public.sia_pa.procedimento_codigo IS 'Código do procedimento SIGTAP';
COMMENT ON COLUMN public.sia_pa.cid_principal_codigo IS 'Código CID-10 principal';
COMMENT ON COLUMN public.sia_pa.cbo_codigo IS 'Código CBO do profissional';
