-- =====================================================
-- MIGRATION: Criar tabela paciente_identificador
-- =====================================================
-- Objetivo: Criar tabela para armazenar identificadores oficiais do paciente
-- (CPF, CNS, RG) permitindo múltiplos identificadores por paciente e múltiplas origens
-- Autor: UPSaúde
-- =====================================================

-- Criar tabela paciente_identificador
CREATE TABLE IF NOT EXISTS public.paciente_identificador (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    paciente_id UUID NOT NULL,
    tipo INTEGER NOT NULL, -- 1=CPF, 2=CNS, 3=RG, 99=OUTRO
    valor VARCHAR(255) NOT NULL,
    origem INTEGER, -- 1=UPSAUDE, 2=CADSUS, 3=ESUS, 4=RNDS, 99=OUTRO
    validado BOOLEAN NOT NULL DEFAULT false,
    data_validacao DATE,
    principal BOOLEAN NOT NULL DEFAULT false,
    observacoes TEXT,
    criado_em TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP WITH TIME ZONE,
    tenant_id UUID NOT NULL,
    estabelecimento_id UUID,
    ativo BOOLEAN NOT NULL DEFAULT true,
    
    CONSTRAINT fk_paciente_identificador_paciente 
        FOREIGN KEY (paciente_id) 
        REFERENCES public.pacientes(id) 
        ON DELETE CASCADE,
    CONSTRAINT fk_paciente_identificador_tenant 
        FOREIGN KEY (tenant_id) 
        REFERENCES public.tenants(id) 
        ON DELETE CASCADE,
    CONSTRAINT fk_paciente_identificador_estabelecimento 
        FOREIGN KEY (estabelecimento_id) 
        REFERENCES public.estabelecimentos(id) 
        ON DELETE SET NULL,
    CONSTRAINT uk_paciente_identificador_tipo_valor_tenant 
        UNIQUE (tipo, valor, tenant_id)
);

-- Criar índices
CREATE INDEX IF NOT EXISTS idx_paciente_identificador_paciente 
    ON public.paciente_identificador(paciente_id);

CREATE INDEX IF NOT EXISTS idx_paciente_identificador_tipo 
    ON public.paciente_identificador(tipo);

CREATE INDEX IF NOT EXISTS idx_paciente_identificador_valor 
    ON public.paciente_identificador(valor);

CREATE INDEX IF NOT EXISTS idx_paciente_identificador_origem 
    ON public.paciente_identificador(origem);

CREATE INDEX IF NOT EXISTS idx_paciente_identificador_principal 
    ON public.paciente_identificador(paciente_id, principal) 
    WHERE principal = true;

CREATE INDEX IF NOT EXISTS idx_paciente_identificador_validado 
    ON public.paciente_identificador(validado) 
    WHERE validado = true;

CREATE INDEX IF NOT EXISTS idx_paciente_identificador_tenant 
    ON public.paciente_identificador(tenant_id);

CREATE INDEX IF NOT EXISTS idx_paciente_identificador_ativo 
    ON public.paciente_identificador(ativo) 
    WHERE ativo = true;

-- Comentários
COMMENT ON TABLE public.paciente_identificador IS 'Armazena identificadores oficiais do paciente (CPF, CNS, RG) permitindo múltiplos identificadores e múltiplas origens';
COMMENT ON COLUMN public.paciente_identificador.tipo IS '1=CPF, 2=CNS, 3=RG, 99=OUTRO';
COMMENT ON COLUMN public.paciente_identificador.origem IS '1=UPSAUDE, 2=CADSUS, 3=ESUS, 4=RNDS, 99=OUTRO';
COMMENT ON COLUMN public.paciente_identificador.principal IS 'Indica se é o identificador principal do tipo (apenas um principal por tipo)';
COMMENT ON COLUMN public.paciente_identificador.validado IS 'Indica se o identificador foi validado';

