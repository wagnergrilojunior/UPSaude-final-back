-- Migration: add_prestador_servico_and_conta_bancaria
-- Description: Adiciona campos de prestador de serviço e cria tabela de contas bancárias
-- Date: 2026-01-14

-- ============================================================================
-- PARTE 1: Adicionar campos à tabela estabelecimentos
-- ============================================================================

-- Adicionar campo prestador_servico
ALTER TABLE public.estabelecimentos
ADD COLUMN IF NOT EXISTS prestador_servico BOOLEAN NOT NULL DEFAULT false;

-- Adicionar campo tipo_prestador
ALTER TABLE public.estabelecimentos
ADD COLUMN IF NOT EXISTS tipo_prestador VARCHAR(50);

-- Criar índice para tipo_prestador
CREATE INDEX IF NOT EXISTS idx_estabelecimentos_tipo_prestador 
ON public.estabelecimentos(tipo_prestador);

-- Adicionar comentários aos campos
COMMENT ON COLUMN public.estabelecimentos.prestador_servico IS 'Indica se o estabelecimento é um prestador de serviço';
COMMENT ON COLUMN public.estabelecimentos.tipo_prestador IS 'Tipo de prestador: CLINICA, LABORATORIO, HOSPITAL, IMAGEM, AMBULATORIO, OUTRO';

-- ============================================================================
-- PARTE 2: Criar tabela conta_bancaria_estabelecimento
-- ============================================================================

CREATE TABLE IF NOT EXISTS public.conta_bancaria_estabelecimento (
    -- Campos herdados de BaseEntityWithoutEstabelecimento
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    criado_em TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMPTZ,
    tenant_id UUID NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT true,
    
    -- Relacionamento com estabelecimento
    estabelecimento_id UUID NOT NULL,
    
    -- Dados bancários
    banco_codigo VARCHAR(10) NOT NULL,
    banco_nome VARCHAR(255) NOT NULL,
    agencia VARCHAR(20) NOT NULL,
    agencia_digito VARCHAR(2),
    numero_conta VARCHAR(50) NOT NULL,
    conta_digito VARCHAR(2),
    tipo_conta VARCHAR(30) NOT NULL,
    conta_principal BOOLEAN NOT NULL DEFAULT false,
    
    -- PIX
    tipo_chave_pix VARCHAR(30),
    chave_pix VARCHAR(255),
    
    -- Titular
    titular_nome VARCHAR(255) NOT NULL,
    titular_cpf_cnpj VARCHAR(18) NOT NULL,
    
    -- Observações
    observacoes TEXT,
    
    -- Constraints
    CONSTRAINT fk_conta_bancaria_estabelecimento 
        FOREIGN KEY (estabelecimento_id) 
        REFERENCES public.estabelecimentos(id) 
        ON DELETE CASCADE,
    
    CONSTRAINT fk_conta_bancaria_tenant 
        FOREIGN KEY (tenant_id) 
        REFERENCES public.tenants(id) 
        ON DELETE CASCADE,
    
    -- Unique constraint para evitar duplicação de conta
    CONSTRAINT uk_conta_bancaria_banco_agencia_conta_estabelecimento 
        UNIQUE (banco_codigo, agencia, numero_conta, estabelecimento_id)
);

-- ============================================================================
-- PARTE 3: Criar índices para performance
-- ============================================================================

CREATE INDEX IF NOT EXISTS idx_conta_bancaria_estabelecimento_id 
ON public.conta_bancaria_estabelecimento(estabelecimento_id);

CREATE INDEX IF NOT EXISTS idx_conta_bancaria_banco_codigo 
ON public.conta_bancaria_estabelecimento(banco_codigo);

CREATE INDEX IF NOT EXISTS idx_conta_bancaria_principal 
ON public.conta_bancaria_estabelecimento(estabelecimento_id, conta_principal);

CREATE INDEX IF NOT EXISTS idx_conta_bancaria_tenant_id 
ON public.conta_bancaria_estabelecimento(tenant_id);

-- ============================================================================
-- PARTE 4: Adicionar comentários à tabela e colunas
-- ============================================================================

COMMENT ON TABLE public.conta_bancaria_estabelecimento IS 'Armazena as contas bancárias dos estabelecimentos';

COMMENT ON COLUMN public.conta_bancaria_estabelecimento.estabelecimento_id IS 'Referência ao estabelecimento';
COMMENT ON COLUMN public.conta_bancaria_estabelecimento.banco_codigo IS 'Código do banco (ex: 001, 237, 341)';
COMMENT ON COLUMN public.conta_bancaria_estabelecimento.banco_nome IS 'Nome do banco';
COMMENT ON COLUMN public.conta_bancaria_estabelecimento.agencia IS 'Número da agência';
COMMENT ON COLUMN public.conta_bancaria_estabelecimento.agencia_digito IS 'Dígito verificador da agência';
COMMENT ON COLUMN public.conta_bancaria_estabelecimento.numero_conta IS 'Número da conta';
COMMENT ON COLUMN public.conta_bancaria_estabelecimento.conta_digito IS 'Dígito verificador da conta';
COMMENT ON COLUMN public.conta_bancaria_estabelecimento.tipo_conta IS 'Tipo de conta: CORRENTE, POUPANCA, PAGAMENTO, SALARIO';
COMMENT ON COLUMN public.conta_bancaria_estabelecimento.conta_principal IS 'Indica se é a conta principal do estabelecimento';
COMMENT ON COLUMN public.conta_bancaria_estabelecimento.tipo_chave_pix IS 'Tipo de chave PIX: CPF, CNPJ, EMAIL, TELEFONE, ALEATORIA';
COMMENT ON COLUMN public.conta_bancaria_estabelecimento.chave_pix IS 'Valor da chave PIX';
COMMENT ON COLUMN public.conta_bancaria_estabelecimento.titular_nome IS 'Nome do titular da conta';
COMMENT ON COLUMN public.conta_bancaria_estabelecimento.titular_cpf_cnpj IS 'CPF ou CNPJ do titular';

-- ============================================================================
-- PARTE 5: Habilitar Row Level Security (RLS)
-- ============================================================================

ALTER TABLE public.conta_bancaria_estabelecimento ENABLE ROW LEVEL SECURITY;

-- Política para SELECT: usuários podem ver contas do seu tenant
CREATE POLICY IF NOT EXISTS "Usuários podem visualizar contas do seu tenant"
ON public.conta_bancaria_estabelecimento
FOR SELECT
USING (tenant_id = current_setting('app.current_tenant_id')::uuid);

-- Política para INSERT: usuários podem inserir contas no seu tenant
CREATE POLICY IF NOT EXISTS "Usuários podem inserir contas no seu tenant"
ON public.conta_bancaria_estabelecimento
FOR INSERT
WITH CHECK (tenant_id = current_setting('app.current_tenant_id')::uuid);

-- Política para UPDATE: usuários podem atualizar contas do seu tenant
CREATE POLICY IF NOT EXISTS "Usuários podem atualizar contas do seu tenant"
ON public.conta_bancaria_estabelecimento
FOR UPDATE
USING (tenant_id = current_setting('app.current_tenant_id')::uuid)
WITH CHECK (tenant_id = current_setting('app.current_tenant_id')::uuid);

-- Política para DELETE: usuários podem deletar contas do seu tenant
CREATE POLICY IF NOT EXISTS "Usuários podem deletar contas do seu tenant"
ON public.conta_bancaria_estabelecimento
FOR DELETE
USING (tenant_id = current_setting('app.current_tenant_id')::uuid);

-- ============================================================================
-- FIM DA MIGRAÇÃO
-- ============================================================================
