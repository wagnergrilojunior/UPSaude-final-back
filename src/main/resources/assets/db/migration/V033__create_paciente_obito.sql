-- =====================================================
-- MIGRATION: Criar tabela paciente_obito
-- =====================================================
-- Objetivo: Criar tabela para armazenar informações de óbito do paciente
-- permitindo rastreabilidade de origem e múltiplas fontes
-- Autor: UPSaúde
-- =====================================================

-- Criar enum para origem do óbito (se não existir)
-- Usaremos INTEGER para flexibilidade, mapeando: 1=MANUAL, 2=ESUS, 3=RNDS, 4=CADSUS, 99=OUTRO

-- Criar tabela paciente_obito
CREATE TABLE IF NOT EXISTS public.paciente_obito (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    paciente_id UUID NOT NULL,
    data_obito DATE NOT NULL,
    causa_obito_cid10 VARCHAR(10),
    data_registro TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    origem INTEGER, -- 1=MANUAL, 2=ESUS, 3=RNDS, 4=CADSUS, 99=OUTRO
    observacoes TEXT,
    criado_em TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP WITH TIME ZONE,
    tenant_id UUID NOT NULL,
    estabelecimento_id UUID,
    ativo BOOLEAN NOT NULL DEFAULT true,
    
    CONSTRAINT fk_paciente_obito_paciente 
        FOREIGN KEY (paciente_id) 
        REFERENCES public.pacientes(id) 
        ON DELETE CASCADE,
    CONSTRAINT fk_paciente_obito_tenant 
        FOREIGN KEY (tenant_id) 
        REFERENCES public.tenants(id) 
        ON DELETE CASCADE,
    CONSTRAINT fk_paciente_obito_estabelecimento 
        FOREIGN KEY (estabelecimento_id) 
        REFERENCES public.estabelecimentos(id) 
        ON DELETE SET NULL,
    CONSTRAINT uk_paciente_obito_paciente 
        UNIQUE (paciente_id)
);

-- Criar índices
CREATE INDEX IF NOT EXISTS idx_paciente_obito_paciente 
    ON public.paciente_obito(paciente_id);

CREATE INDEX IF NOT EXISTS idx_paciente_obito_data_obito 
    ON public.paciente_obito(data_obito);

CREATE INDEX IF NOT EXISTS idx_paciente_obito_origem 
    ON public.paciente_obito(origem);

CREATE INDEX IF NOT EXISTS idx_paciente_obito_tenant 
    ON public.paciente_obito(tenant_id);

CREATE INDEX IF NOT EXISTS idx_paciente_obito_ativo 
    ON public.paciente_obito(ativo) 
    WHERE ativo = true;

-- Comentários
COMMENT ON TABLE public.paciente_obito IS 'Armazena informações de óbito do paciente com rastreabilidade de origem';
COMMENT ON COLUMN public.paciente_obito.origem IS '1=MANUAL, 2=ESUS, 3=RNDS, 4=CADSUS, 99=OUTRO';
COMMENT ON COLUMN public.paciente_obito.data_registro IS 'Data em que o óbito foi registrado no sistema';

