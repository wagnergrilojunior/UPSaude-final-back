-- =====================================================
-- MIGRATION: Criar tabela integracao_eventos
-- =====================================================
-- Objetivo: Criar tabela para rastreabilidade de eventos de integração
-- com sistemas externos (RNDS, e-SUS APS, etc.)
-- Autor: UPSaúde
-- =====================================================

CREATE TABLE IF NOT EXISTS public.integracao_eventos (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    
    -- Identificação da entidade origem
    entidade_tipo INTEGER NOT NULL, -- 1=APPOINTMENT, 2=ENCOUNTER, 3=CONSULTA
    entidade_id UUID NOT NULL,
    
    -- Sistema e recurso de destino
    sistema INTEGER NOT NULL, -- 1=ESUS_PEC, 2=ESUS_HORUS, 3=RNDS, etc.
    recurso VARCHAR(100) NOT NULL, -- "Appointment", "Encounter", "Consulta", etc.
    
    -- Versionamento
    versao INTEGER NOT NULL DEFAULT 1,
    
    -- Status do evento
    status INTEGER NOT NULL DEFAULT 1, -- 1=PENDENTE, 2=PROCESSANDO, 3=SUCESSO, 4=ERRO
    
    -- Controle de tentativas
    tentativas INTEGER NOT NULL DEFAULT 0,
    proxima_tentativa_em TIMESTAMP WITH TIME ZONE,
    
    -- Datas de processamento
    data_envio TIMESTAMP WITH TIME ZONE,
    data_conclusao TIMESTAMP WITH TIME ZONE,
    
    -- Auditoria técnica
    correlation_id VARCHAR(100),
    protocolo VARCHAR(200),
    
    -- Classificação de erro
    erro_tipo INTEGER, -- 1=VALIDACAO, 2=COMUNICACAO, 3=AUTENTICACAO
    erro_msg TEXT,
    
    -- Payloads (JSONB)
    payload_request JSONB,
    payload_response JSONB,
    
    -- Auditoria padrão
    criado_em TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP WITH TIME ZONE,
    tenant_id UUID NOT NULL,
    estabelecimento_id UUID,
    ativo BOOLEAN NOT NULL DEFAULT true,
    
    CONSTRAINT fk_integracao_eventos_tenant 
        FOREIGN KEY (tenant_id) 
        REFERENCES public.tenants(id) 
        ON DELETE CASCADE,
    CONSTRAINT fk_integracao_eventos_estabelecimento 
        FOREIGN KEY (estabelecimento_id) 
        REFERENCES public.estabelecimentos(id) 
        ON DELETE SET NULL
);

-- Índices para performance
CREATE INDEX IF NOT EXISTS idx_integracao_evento_entidade 
    ON public.integracao_eventos(entidade_tipo, entidade_id);

CREATE INDEX IF NOT EXISTS idx_integracao_evento_sistema_recurso 
    ON public.integracao_eventos(sistema, recurso);

CREATE INDEX IF NOT EXISTS idx_integracao_evento_status 
    ON public.integracao_eventos(status);

CREATE INDEX IF NOT EXISTS idx_integracao_evento_proxima_tentativa 
    ON public.integracao_eventos(proxima_tentativa_em) 
    WHERE proxima_tentativa_em IS NOT NULL;

CREATE INDEX IF NOT EXISTS idx_integracao_evento_versao 
    ON public.integracao_eventos(entidade_tipo, entidade_id, sistema, recurso, versao);

CREATE INDEX IF NOT EXISTS idx_integracao_evento_correlation 
    ON public.integracao_eventos(correlation_id) 
    WHERE correlation_id IS NOT NULL;

CREATE INDEX IF NOT EXISTS idx_integracao_evento_tenant 
    ON public.integracao_eventos(tenant_id);

CREATE INDEX IF NOT EXISTS idx_integracao_evento_estabelecimento 
    ON public.integracao_eventos(estabelecimento_id) 
    WHERE estabelecimento_id IS NOT NULL;

-- Comentários para documentação
COMMENT ON TABLE public.integracao_eventos IS 'Tabela de rastreabilidade de eventos de integração com sistemas externos';
COMMENT ON COLUMN public.integracao_eventos.entidade_tipo IS 'Tipo da entidade origem: 1=APPOINTMENT, 2=ENCOUNTER, 3=CONSULTA';
COMMENT ON COLUMN public.integracao_eventos.sistema IS 'Sistema de destino: 1=ESUS_PEC, 2=ESUS_HORUS, 3=RNDS, 4=CADSUS, 5=CNES, 99=OUTRO';
COMMENT ON COLUMN public.integracao_eventos.versao IS 'Versão do evento para a mesma entidade+sistema+recurso (incrementa a cada mudança)';
COMMENT ON COLUMN public.integracao_eventos.status IS 'Status: 1=PENDENTE, 2=PROCESSANDO, 3=SUCESSO, 4=ERRO';
COMMENT ON COLUMN public.integracao_eventos.erro_tipo IS 'Tipo de erro: 1=VALIDACAO, 2=COMUNICACAO, 3=AUTENTICACAO';
COMMENT ON COLUMN public.integracao_eventos.payload_request IS 'Payload JSON enviado para o sistema externo';
COMMENT ON COLUMN public.integracao_eventos.payload_response IS 'Payload JSON recebido do sistema externo';
