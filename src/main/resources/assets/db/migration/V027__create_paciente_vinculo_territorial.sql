-- =====================================================
-- MIGRATION: Criar tabela paciente_vinculo_territorial
-- =====================================================
-- Objetivo: Criar tabela para armazenar vínculos territoriais/ESF do paciente
-- permitindo histórico de vínculos ao longo do tempo
-- Autor: UPSaúde
-- =====================================================

-- Criar tabela paciente_vinculo_territorial
CREATE TABLE IF NOT EXISTS public.paciente_vinculo_territorial (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    paciente_id UUID NOT NULL,
    municipio_ibge VARCHAR(7),
    cnes_estabelecimento VARCHAR(7),
    ine_equipe VARCHAR(10),
    microarea VARCHAR(10),
    data_inicio DATE NOT NULL,
    data_fim DATE,
    origem INTEGER, -- 1=ESUS, 2=RNDS, 3=MANUAL, 99=OUTRO
    ativo BOOLEAN NOT NULL DEFAULT true,
    observacoes TEXT,
    criado_em TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP WITH TIME ZONE,
    tenant_id UUID NOT NULL,
    estabelecimento_id UUID,
    
    CONSTRAINT fk_paciente_vinculo_territorial_paciente 
        FOREIGN KEY (paciente_id) 
        REFERENCES public.pacientes(id) 
        ON DELETE CASCADE,
    CONSTRAINT fk_paciente_vinculo_territorial_tenant 
        FOREIGN KEY (tenant_id) 
        REFERENCES public.tenants(id) 
        ON DELETE CASCADE,
    CONSTRAINT fk_paciente_vinculo_territorial_estabelecimento 
        FOREIGN KEY (estabelecimento_id) 
        REFERENCES public.estabelecimentos(id) 
        ON DELETE SET NULL,
    CONSTRAINT chk_paciente_vinculo_territorial_data_fim 
        CHECK (data_fim IS NULL OR data_fim >= data_inicio)
);

-- Criar índices
CREATE INDEX IF NOT EXISTS idx_paciente_vinculo_territorial_paciente 
    ON public.paciente_vinculo_territorial(paciente_id);

CREATE INDEX IF NOT EXISTS idx_paciente_vinculo_territorial_ativo 
    ON public.paciente_vinculo_territorial(paciente_id, ativo) 
    WHERE ativo = true;

CREATE INDEX IF NOT EXISTS idx_paciente_vinculo_territorial_cnes 
    ON public.paciente_vinculo_territorial(cnes_estabelecimento);

CREATE INDEX IF NOT EXISTS idx_paciente_vinculo_territorial_ine 
    ON public.paciente_vinculo_territorial(ine_equipe);

CREATE INDEX IF NOT EXISTS idx_paciente_vinculo_territorial_microarea 
    ON public.paciente_vinculo_territorial(microarea);

CREATE INDEX IF NOT EXISTS idx_paciente_vinculo_territorial_origem 
    ON public.paciente_vinculo_territorial(origem);

CREATE INDEX IF NOT EXISTS idx_paciente_vinculo_territorial_data_inicio 
    ON public.paciente_vinculo_territorial(data_inicio);

CREATE INDEX IF NOT EXISTS idx_paciente_vinculo_territorial_tenant 
    ON public.paciente_vinculo_territorial(tenant_id);

-- Comentários
COMMENT ON TABLE public.paciente_vinculo_territorial IS 'Armazena vínculos territoriais/ESF do paciente permitindo histórico de vínculos';
COMMENT ON COLUMN public.paciente_vinculo_territorial.origem IS '1=ESUS, 2=RNDS, 3=MANUAL, 99=OUTRO';
COMMENT ON COLUMN public.paciente_vinculo_territorial.ativo IS 'Indica se o vínculo está ativo (apenas um vínculo ativo por paciente)';
COMMENT ON COLUMN public.paciente_vinculo_territorial.data_fim IS 'Data de término do vínculo (NULL se ainda ativo)';

