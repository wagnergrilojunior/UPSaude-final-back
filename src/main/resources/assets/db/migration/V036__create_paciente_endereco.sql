-- =====================================================
-- MIGRATION: Criar tabela paciente_endereco
-- =====================================================
-- Objetivo: Criar tabela de vínculo entre paciente e endereço
-- com metadados completos (tipo, principal, origem, vigência)
-- Autor: UPSaúde
-- =====================================================

-- Criar tabela paciente_endereco
CREATE TABLE IF NOT EXISTS public.paciente_endereco (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    paciente_id UUID NOT NULL,
    endereco_id UUID NOT NULL,
    tipo_endereco INTEGER, -- 1=RESIDENCIAL, 2=COMERCIAL, 3=CORRESPONDENCIA, 4=RURAL, 99=OUTRO
    principal BOOLEAN NOT NULL DEFAULT false,
    origem INTEGER, -- 1=MANUAL, 2=ESUS, 3=RNDS, 4=CADSUS, 99=OUTRO
    data_inicio DATE NOT NULL DEFAULT CURRENT_DATE,
    data_fim DATE,
    ativo BOOLEAN NOT NULL DEFAULT true,
    observacoes TEXT,
    criado_em TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP WITH TIME ZONE,
    tenant_id UUID NOT NULL,
    estabelecimento_id UUID,
    
    CONSTRAINT fk_paciente_endereco_paciente 
        FOREIGN KEY (paciente_id) 
        REFERENCES public.pacientes(id) 
        ON DELETE CASCADE,
    CONSTRAINT fk_paciente_endereco_endereco 
        FOREIGN KEY (endereco_id) 
        REFERENCES public.enderecos(id) 
        ON DELETE CASCADE,
    CONSTRAINT fk_paciente_endereco_tenant 
        FOREIGN KEY (tenant_id) 
        REFERENCES public.tenants(id) 
        ON DELETE CASCADE,
    CONSTRAINT fk_paciente_endereco_estabelecimento 
        FOREIGN KEY (estabelecimento_id) 
        REFERENCES public.estabelecimentos(id) 
        ON DELETE SET NULL,
    CONSTRAINT chk_paciente_endereco_data_fim 
        CHECK (data_fim IS NULL OR data_fim >= data_inicio)
);

-- Criar índices
CREATE INDEX IF NOT EXISTS idx_paciente_endereco_paciente 
    ON public.paciente_endereco(paciente_id);

CREATE INDEX IF NOT EXISTS idx_paciente_endereco_endereco 
    ON public.paciente_endereco(endereco_id);

CREATE INDEX IF NOT EXISTS idx_paciente_endereco_principal 
    ON public.paciente_endereco(paciente_id, principal) 
    WHERE principal = true;

CREATE INDEX IF NOT EXISTS idx_paciente_endereco_ativo 
    ON public.paciente_endereco(paciente_id, ativo) 
    WHERE ativo = true;

CREATE INDEX IF NOT EXISTS idx_paciente_endereco_tipo 
    ON public.paciente_endereco(tipo_endereco);

CREATE INDEX IF NOT EXISTS idx_paciente_endereco_origem 
    ON public.paciente_endereco(origem);

CREATE INDEX IF NOT EXISTS idx_paciente_endereco_tenant 
    ON public.paciente_endereco(tenant_id);

-- Comentários
COMMENT ON TABLE public.paciente_endereco IS 'Vínculo entre paciente e endereço com metadados completos (tipo, principal, origem, vigência)';
COMMENT ON COLUMN public.paciente_endereco.tipo_endereco IS '1=RESIDENCIAL, 2=COMERCIAL, 3=CORRESPONDENCIA, 4=RURAL, 99=OUTRO';
COMMENT ON COLUMN public.paciente_endereco.origem IS '1=MANUAL, 2=ESUS, 3=RNDS, 4=CADSUS, 99=OUTRO';
COMMENT ON COLUMN public.paciente_endereco.principal IS 'Indica se é o endereço principal do paciente';
COMMENT ON COLUMN public.paciente_endereco.data_fim IS 'Data de término do vínculo (NULL se ainda ativo)';

