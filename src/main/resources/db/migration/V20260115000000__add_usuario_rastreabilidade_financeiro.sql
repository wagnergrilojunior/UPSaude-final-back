ALTER TABLE IF EXISTS public.baixa_receber
    ADD COLUMN IF NOT EXISTS criado_por_id UUID,
    ADD COLUMN IF NOT EXISTS atualizado_por_id UUID;

ALTER TABLE IF EXISTS public.centro_custo
    ADD COLUMN IF NOT EXISTS criado_por_id UUID,
    ADD COLUMN IF NOT EXISTS atualizado_por_id UUID;

ALTER TABLE IF EXISTS public.competencia_financeira
    ADD COLUMN IF NOT EXISTS criado_por_id UUID,
    ADD COLUMN IF NOT EXISTS atualizado_por_id UUID;

ALTER TABLE IF EXISTS public.conciliacao_bancaria
    ADD COLUMN IF NOT EXISTS criado_por_id UUID,
    ADD COLUMN IF NOT EXISTS atualizado_por_id UUID,
    ADD COLUMN IF NOT EXISTS fechada_por UUID;

ALTER TABLE IF EXISTS public.conciliacao_item
    ADD COLUMN IF NOT EXISTS criado_por_id UUID,
    ADD COLUMN IF NOT EXISTS atualizado_por_id UUID;

ALTER TABLE IF EXISTS public.conta_contabil
    ADD COLUMN IF NOT EXISTS criado_por_id UUID,
    ADD COLUMN IF NOT EXISTS atualizado_por_id UUID;

ALTER TABLE IF EXISTS public.conta_financeira
    ADD COLUMN IF NOT EXISTS criado_por_id UUID,
    ADD COLUMN IF NOT EXISTS atualizado_por_id UUID;

ALTER TABLE IF EXISTS public.credito_orcamentario
    ADD COLUMN IF NOT EXISTS criado_por_id UUID,
    ADD COLUMN IF NOT EXISTS atualizado_por_id UUID;

ALTER TABLE IF EXISTS public.estorno_financeiro
    ADD COLUMN IF NOT EXISTS criado_por_id UUID,
    ADD COLUMN IF NOT EXISTS atualizado_por_id UUID;

ALTER TABLE IF EXISTS public.extrato_bancario_importado
    ADD COLUMN IF NOT EXISTS criado_por_id UUID,
    ADD COLUMN IF NOT EXISTS atualizado_por_id UUID;

ALTER TABLE IF EXISTS public.guia_atendimento_ambulatorial
    ADD COLUMN IF NOT EXISTS criado_por_id UUID,
    ADD COLUMN IF NOT EXISTS atualizado_por_id UUID;

ALTER TABLE IF EXISTS public.lancamento_financeiro
    ADD COLUMN IF NOT EXISTS criado_por_id UUID,
    ADD COLUMN IF NOT EXISTS atualizado_por_id UUID;

ALTER TABLE IF EXISTS public.lancamento_financeiro_item
    ADD COLUMN IF NOT EXISTS criado_por_id UUID,
    ADD COLUMN IF NOT EXISTS atualizado_por_id UUID;

ALTER TABLE IF EXISTS public.log_financeiro
    ADD COLUMN IF NOT EXISTS criado_por_id UUID,
    ADD COLUMN IF NOT EXISTS atualizado_por_id UUID;

ALTER TABLE IF EXISTS public.movimentacao_conta
    ADD COLUMN IF NOT EXISTS criado_por_id UUID,
    ADD COLUMN IF NOT EXISTS atualizado_por_id UUID;

ALTER TABLE IF EXISTS public.orcamento_competencia
    ADD COLUMN IF NOT EXISTS criado_por_id UUID,
    ADD COLUMN IF NOT EXISTS atualizado_por_id UUID;

ALTER TABLE IF EXISTS public.pagamento_pagar
    ADD COLUMN IF NOT EXISTS criado_por_id UUID,
    ADD COLUMN IF NOT EXISTS atualizado_por_id UUID;

ALTER TABLE IF EXISTS public.parte_financeira
    ADD COLUMN IF NOT EXISTS criado_por_id UUID,
    ADD COLUMN IF NOT EXISTS atualizado_por_id UUID;

ALTER TABLE IF EXISTS public.plano_contas
    ADD COLUMN IF NOT EXISTS criado_por_id UUID,
    ADD COLUMN IF NOT EXISTS atualizado_por_id UUID;

ALTER TABLE IF EXISTS public.recorrencia_financeira
    ADD COLUMN IF NOT EXISTS criado_por_id UUID,
    ADD COLUMN IF NOT EXISTS atualizado_por_id UUID;

ALTER TABLE IF EXISTS public.regra_classificacao_contabil
    ADD COLUMN IF NOT EXISTS criado_por_id UUID,
    ADD COLUMN IF NOT EXISTS atualizado_por_id UUID;

ALTER TABLE IF EXISTS public.renegociacao_receber
    ADD COLUMN IF NOT EXISTS criado_por_id UUID,
    ADD COLUMN IF NOT EXISTS atualizado_por_id UUID;

ALTER TABLE IF EXISTS public.reserva_orcamentaria_assistencial
    ADD COLUMN IF NOT EXISTS criado_por_id UUID,
    ADD COLUMN IF NOT EXISTS atualizado_por_id UUID;

ALTER TABLE IF EXISTS public.titulo_pagar
    ADD COLUMN IF NOT EXISTS criado_por_id UUID,
    ADD COLUMN IF NOT EXISTS atualizado_por_id UUID;

ALTER TABLE IF EXISTS public.titulo_receber
    ADD COLUMN IF NOT EXISTS criado_por_id UUID,
    ADD COLUMN IF NOT EXISTS atualizado_por_id UUID;

ALTER TABLE IF EXISTS public.transferencia_entre_contas
    ADD COLUMN IF NOT EXISTS criado_por_id UUID,
    ADD COLUMN IF NOT EXISTS atualizado_por_id UUID;

