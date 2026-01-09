-- =====================================================
-- MIGRATION: Criar tabela paciente_dados_pessoais_complementares
-- =====================================================
-- Objetivo: Criar tabela para armazenar dados pessoais complementares do paciente
-- que podem divergir entre fontes (manual, APS, RNDS) e precisam de rastreabilidade
-- Autor: UPSaúde
-- =====================================================

-- Criar tabela paciente_dados_pessoais_complementares
CREATE TABLE IF NOT EXISTS public.paciente_dados_pessoais_complementares (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    paciente_id UUID NOT NULL,
    nome_mae VARCHAR(100),
    nome_pai VARCHAR(100),
    identidade_genero INTEGER, -- Enum IdentidadeGeneroEnum
    orientacao_sexual INTEGER, -- Enum OrientacaoSexualEnum
    criado_em TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP WITH TIME ZONE,
    tenant_id UUID NOT NULL,
    estabelecimento_id UUID,
    ativo BOOLEAN NOT NULL DEFAULT true,
    
    CONSTRAINT fk_paciente_dados_pessoais_complementares_paciente 
        FOREIGN KEY (paciente_id) 
        REFERENCES public.pacientes(id) 
        ON DELETE CASCADE,
    CONSTRAINT fk_paciente_dados_pessoais_complementares_tenant 
        FOREIGN KEY (tenant_id) 
        REFERENCES public.tenants(id) 
        ON DELETE CASCADE,
    CONSTRAINT fk_paciente_dados_pessoais_complementares_estabelecimento 
        FOREIGN KEY (estabelecimento_id) 
        REFERENCES public.estabelecimentos(id) 
        ON DELETE SET NULL,
    CONSTRAINT uk_paciente_dados_pessoais_complementares_paciente 
        UNIQUE (paciente_id)
);

-- Criar índices
CREATE INDEX IF NOT EXISTS idx_paciente_dados_pessoais_complementares_paciente 
    ON public.paciente_dados_pessoais_complementares(paciente_id);

CREATE INDEX IF NOT EXISTS idx_paciente_dados_pessoais_complementares_tenant 
    ON public.paciente_dados_pessoais_complementares(tenant_id);

CREATE INDEX IF NOT EXISTS idx_paciente_dados_pessoais_complementares_ativo 
    ON public.paciente_dados_pessoais_complementares(ativo) 
    WHERE ativo = true;

-- Comentários
COMMENT ON TABLE public.paciente_dados_pessoais_complementares IS 'Armazena dados pessoais complementares do paciente que podem divergir entre fontes e precisam rastreabilidade';
COMMENT ON COLUMN public.paciente_dados_pessoais_complementares.identidade_genero IS 'Identidade de gênero do paciente (autodeclaração)';
COMMENT ON COLUMN public.paciente_dados_pessoais_complementares.orientacao_sexual IS 'Orientação sexual do paciente (autodeclaração)';

