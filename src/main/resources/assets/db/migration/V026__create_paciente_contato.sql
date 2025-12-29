-- =====================================================
-- MIGRATION: Criar tabela paciente_contato
-- =====================================================
-- Objetivo: Criar tabela para armazenar contatos do paciente
-- (telefone, email, whatsapp) permitindo múltiplos contatos por paciente
-- Autor: UPSaúde
-- =====================================================

-- Criar tabela paciente_contato
CREATE TABLE IF NOT EXISTS public.paciente_contato (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    paciente_id UUID NOT NULL,
    tipo INTEGER NOT NULL, -- 1=TELEFONE, 2=EMAIL, 3=WHATSAPP, 99=OUTRO
    valor VARCHAR(255) NOT NULL,
    principal BOOLEAN NOT NULL DEFAULT false,
    verificado BOOLEAN NOT NULL DEFAULT false,
    data_verificacao TIMESTAMP WITH TIME ZONE,
    observacoes TEXT,
    criado_em TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP WITH TIME ZONE,
    tenant_id UUID NOT NULL,
    estabelecimento_id UUID,
    ativo BOOLEAN NOT NULL DEFAULT true,
    
    CONSTRAINT fk_paciente_contato_paciente 
        FOREIGN KEY (paciente_id) 
        REFERENCES public.pacientes(id) 
        ON DELETE CASCADE,
    CONSTRAINT fk_paciente_contato_tenant 
        FOREIGN KEY (tenant_id) 
        REFERENCES public.tenants(id) 
        ON DELETE CASCADE,
    CONSTRAINT fk_paciente_contato_estabelecimento 
        FOREIGN KEY (estabelecimento_id) 
        REFERENCES public.estabelecimentos(id) 
        ON DELETE SET NULL,
    CONSTRAINT uk_paciente_contato_tipo_valor_tenant 
        UNIQUE (tipo, valor, tenant_id)
);

-- Criar índices
CREATE INDEX IF NOT EXISTS idx_paciente_contato_paciente 
    ON public.paciente_contato(paciente_id);

CREATE INDEX IF NOT EXISTS idx_paciente_contato_tipo 
    ON public.paciente_contato(tipo);

CREATE INDEX IF NOT EXISTS idx_paciente_contato_valor 
    ON public.paciente_contato(valor);

CREATE INDEX IF NOT EXISTS idx_paciente_contato_principal 
    ON public.paciente_contato(paciente_id, principal) 
    WHERE principal = true;

CREATE INDEX IF NOT EXISTS idx_paciente_contato_verificado 
    ON public.paciente_contato(verificado) 
    WHERE verificado = true;

CREATE INDEX IF NOT EXISTS idx_paciente_contato_tenant 
    ON public.paciente_contato(tenant_id);

CREATE INDEX IF NOT EXISTS idx_paciente_contato_ativo 
    ON public.paciente_contato(ativo) 
    WHERE ativo = true;

-- Comentários
COMMENT ON TABLE public.paciente_contato IS 'Armazena contatos do paciente (telefone, email, whatsapp) permitindo múltiplos contatos';
COMMENT ON COLUMN public.paciente_contato.tipo IS '1=TELEFONE, 2=EMAIL, 3=WHATSAPP, 99=OUTRO';
COMMENT ON COLUMN public.paciente_contato.principal IS 'Indica se é o contato principal do tipo';
COMMENT ON COLUMN public.paciente_contato.verificado IS 'Indica se o contato foi verificado';

