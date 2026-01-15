ALTER TABLE IF EXISTS public.baixa_receber
    ADD CONSTRAINT fk_baixa_receber_criado_por
    FOREIGN KEY (criado_por_id) REFERENCES public.usuarios_sistema(id);

ALTER TABLE IF EXISTS public.baixa_receber
    ADD CONSTRAINT fk_baixa_receber_atualizado_por
    FOREIGN KEY (atualizado_por_id) REFERENCES public.usuarios_sistema(id);

ALTER TABLE IF EXISTS public.centro_custo
    ADD CONSTRAINT fk_centro_custo_criado_por
    FOREIGN KEY (criado_por_id) REFERENCES public.usuarios_sistema(id);

ALTER TABLE IF EXISTS public.centro_custo
    ADD CONSTRAINT fk_centro_custo_atualizado_por
    FOREIGN KEY (atualizado_por_id) REFERENCES public.usuarios_sistema(id);

ALTER TABLE IF EXISTS public.competencia_financeira
    ADD CONSTRAINT fk_competencia_financeira_criado_por
    FOREIGN KEY (criado_por_id) REFERENCES public.usuarios_sistema(id);

ALTER TABLE IF EXISTS public.competencia_financeira
    ADD CONSTRAINT fk_competencia_financeira_atualizado_por
    FOREIGN KEY (atualizado_por_id) REFERENCES public.usuarios_sistema(id);

ALTER TABLE IF EXISTS public.competencia_financeira
    ADD CONSTRAINT fk_competencia_financeira_fechada_por
    FOREIGN KEY (fechada_por) REFERENCES public.usuarios_sistema(id);

ALTER TABLE IF EXISTS public.conciliacao_bancaria
    ADD CONSTRAINT fk_conciliacao_bancaria_criado_por
    FOREIGN KEY (criado_por_id) REFERENCES public.usuarios_sistema(id);

ALTER TABLE IF EXISTS public.conciliacao_bancaria
    ADD CONSTRAINT fk_conciliacao_bancaria_atualizado_por
    FOREIGN KEY (atualizado_por_id) REFERENCES public.usuarios_sistema(id);

ALTER TABLE IF EXISTS public.conciliacao_bancaria
    ADD CONSTRAINT fk_conciliacao_bancaria_fechada_por
    FOREIGN KEY (fechada_por) REFERENCES public.usuarios_sistema(id);

ALTER TABLE IF EXISTS public.conciliacao_item
    ADD CONSTRAINT fk_conciliacao_item_criado_por
    FOREIGN KEY (criado_por_id) REFERENCES public.usuarios_sistema(id);

ALTER TABLE IF EXISTS public.conciliacao_item
    ADD CONSTRAINT fk_conciliacao_item_atualizado_por
    FOREIGN KEY (atualizado_por_id) REFERENCES public.usuarios_sistema(id);

ALTER TABLE IF EXISTS public.conta_contabil
    ADD CONSTRAINT fk_conta_contabil_criado_por
    FOREIGN KEY (criado_por_id) REFERENCES public.usuarios_sistema(id);

ALTER TABLE IF EXISTS public.conta_contabil
    ADD CONSTRAINT fk_conta_contabil_atualizado_por
    FOREIGN KEY (atualizado_por_id) REFERENCES public.usuarios_sistema(id);

ALTER TABLE IF EXISTS public.conta_financeira
    ADD CONSTRAINT fk_conta_financeira_criado_por
    FOREIGN KEY (criado_por_id) REFERENCES public.usuarios_sistema(id);

ALTER TABLE IF EXISTS public.conta_financeira
    ADD CONSTRAINT fk_conta_financeira_atualizado_por
    FOREIGN KEY (atualizado_por_id) REFERENCES public.usuarios_sistema(id);

ALTER TABLE IF EXISTS public.credito_orcamentario
    ADD CONSTRAINT fk_credito_orcamentario_criado_por
    FOREIGN KEY (criado_por_id) REFERENCES public.usuarios_sistema(id);

ALTER TABLE IF EXISTS public.credito_orcamentario
    ADD CONSTRAINT fk_credito_orcamentario_atualizado_por
    FOREIGN KEY (atualizado_por_id) REFERENCES public.usuarios_sistema(id);

ALTER TABLE IF EXISTS public.estorno_financeiro
    ADD CONSTRAINT fk_estorno_financeiro_criado_por
    FOREIGN KEY (criado_por_id) REFERENCES public.usuarios_sistema(id);

ALTER TABLE IF EXISTS public.estorno_financeiro
    ADD CONSTRAINT fk_estorno_financeiro_atualizado_por
    FOREIGN KEY (atualizado_por_id) REFERENCES public.usuarios_sistema(id);

ALTER TABLE IF EXISTS public.extrato_bancario_importado
    ADD CONSTRAINT fk_extrato_bancario_importado_criado_por
    FOREIGN KEY (criado_por_id) REFERENCES public.usuarios_sistema(id);

ALTER TABLE IF EXISTS public.extrato_bancario_importado
    ADD CONSTRAINT fk_extrato_bancario_importado_atualizado_por
    FOREIGN KEY (atualizado_por_id) REFERENCES public.usuarios_sistema(id);

ALTER TABLE IF EXISTS public.guia_atendimento_ambulatorial
    ADD CONSTRAINT fk_guia_atendimento_ambulatorial_criado_por
    FOREIGN KEY (criado_por_id) REFERENCES public.usuarios_sistema(id);

ALTER TABLE IF EXISTS public.guia_atendimento_ambulatorial
    ADD CONSTRAINT fk_guia_atendimento_ambulatorial_atualizado_por
    FOREIGN KEY (atualizado_por_id) REFERENCES public.usuarios_sistema(id);

ALTER TABLE IF EXISTS public.guia_atendimento_ambulatorial
    ADD CONSTRAINT fk_guia_atendimento_ambulatorial_cancelada_por
    FOREIGN KEY (cancelada_por) REFERENCES public.usuarios_sistema(id);

ALTER TABLE IF EXISTS public.lancamento_financeiro
    ADD CONSTRAINT fk_lancamento_financeiro_criado_por
    FOREIGN KEY (criado_por_id) REFERENCES public.usuarios_sistema(id);

ALTER TABLE IF EXISTS public.lancamento_financeiro
    ADD CONSTRAINT fk_lancamento_financeiro_atualizado_por
    FOREIGN KEY (atualizado_por_id) REFERENCES public.usuarios_sistema(id);

ALTER TABLE IF EXISTS public.lancamento_financeiro
    ADD CONSTRAINT fk_lancamento_financeiro_travado_por
    FOREIGN KEY (travado_por_id) REFERENCES public.usuarios_sistema(id);

ALTER TABLE IF EXISTS public.lancamento_financeiro_item
    ADD CONSTRAINT fk_lancamento_financeiro_item_criado_por
    FOREIGN KEY (criado_por_id) REFERENCES public.usuarios_sistema(id);

ALTER TABLE IF EXISTS public.lancamento_financeiro_item
    ADD CONSTRAINT fk_lancamento_financeiro_item_atualizado_por
    FOREIGN KEY (atualizado_por_id) REFERENCES public.usuarios_sistema(id);

ALTER TABLE IF EXISTS public.log_financeiro
    ADD CONSTRAINT fk_log_financeiro_criado_por
    FOREIGN KEY (criado_por_id) REFERENCES public.usuarios_sistema(id);

ALTER TABLE IF EXISTS public.log_financeiro
    ADD CONSTRAINT fk_log_financeiro_atualizado_por
    FOREIGN KEY (atualizado_por_id) REFERENCES public.usuarios_sistema(id);

ALTER TABLE IF EXISTS public.log_financeiro
    ADD CONSTRAINT fk_log_financeiro_usuario
    FOREIGN KEY (usuario_id) REFERENCES public.usuarios_sistema(id);

ALTER TABLE IF EXISTS public.movimentacao_conta
    ADD CONSTRAINT fk_movimentacao_conta_criado_por
    FOREIGN KEY (criado_por_id) REFERENCES public.usuarios_sistema(id);

ALTER TABLE IF EXISTS public.movimentacao_conta
    ADD CONSTRAINT fk_movimentacao_conta_atualizado_por
    FOREIGN KEY (atualizado_por_id) REFERENCES public.usuarios_sistema(id);

ALTER TABLE IF EXISTS public.orcamento_competencia
    ADD CONSTRAINT fk_orcamento_competencia_criado_por
    FOREIGN KEY (criado_por_id) REFERENCES public.usuarios_sistema(id);

ALTER TABLE IF EXISTS public.orcamento_competencia
    ADD CONSTRAINT fk_orcamento_competencia_atualizado_por
    FOREIGN KEY (atualizado_por_id) REFERENCES public.usuarios_sistema(id);

ALTER TABLE IF EXISTS public.pagamento_pagar
    ADD CONSTRAINT fk_pagamento_pagar_criado_por
    FOREIGN KEY (criado_por_id) REFERENCES public.usuarios_sistema(id);

ALTER TABLE IF EXISTS public.pagamento_pagar
    ADD CONSTRAINT fk_pagamento_pagar_atualizado_por
    FOREIGN KEY (atualizado_por_id) REFERENCES public.usuarios_sistema(id);

ALTER TABLE IF EXISTS public.parte_financeira
    ADD CONSTRAINT fk_parte_financeira_criado_por
    FOREIGN KEY (criado_por_id) REFERENCES public.usuarios_sistema(id);

ALTER TABLE IF EXISTS public.parte_financeira
    ADD CONSTRAINT fk_parte_financeira_atualizado_por
    FOREIGN KEY (atualizado_por_id) REFERENCES public.usuarios_sistema(id);

ALTER TABLE IF EXISTS public.plano_contas
    ADD CONSTRAINT fk_plano_contas_criado_por
    FOREIGN KEY (criado_por_id) REFERENCES public.usuarios_sistema(id);

ALTER TABLE IF EXISTS public.plano_contas
    ADD CONSTRAINT fk_plano_contas_atualizado_por
    FOREIGN KEY (atualizado_por_id) REFERENCES public.usuarios_sistema(id);

ALTER TABLE IF EXISTS public.recorrencia_financeira
    ADD CONSTRAINT fk_recorrencia_financeira_criado_por
    FOREIGN KEY (criado_por_id) REFERENCES public.usuarios_sistema(id);

ALTER TABLE IF EXISTS public.recorrencia_financeira
    ADD CONSTRAINT fk_recorrencia_financeira_atualizado_por
    FOREIGN KEY (atualizado_por_id) REFERENCES public.usuarios_sistema(id);

ALTER TABLE IF EXISTS public.regra_classificacao_contabil
    ADD CONSTRAINT fk_regra_classificacao_contabil_criado_por
    FOREIGN KEY (criado_por_id) REFERENCES public.usuarios_sistema(id);

ALTER TABLE IF EXISTS public.regra_classificacao_contabil
    ADD CONSTRAINT fk_regra_classificacao_contabil_atualizado_por
    FOREIGN KEY (atualizado_por_id) REFERENCES public.usuarios_sistema(id);

ALTER TABLE IF EXISTS public.renegociacao_receber
    ADD CONSTRAINT fk_renegociacao_receber_criado_por
    FOREIGN KEY (criado_por_id) REFERENCES public.usuarios_sistema(id);

ALTER TABLE IF EXISTS public.renegociacao_receber
    ADD CONSTRAINT fk_renegociacao_receber_atualizado_por
    FOREIGN KEY (atualizado_por_id) REFERENCES public.usuarios_sistema(id);

ALTER TABLE IF EXISTS public.reserva_orcamentaria_assistencial
    ADD CONSTRAINT fk_reserva_orcamentaria_assistencial_criado_por
    FOREIGN KEY (criado_por_id) REFERENCES public.usuarios_sistema(id);

ALTER TABLE IF EXISTS public.reserva_orcamentaria_assistencial
    ADD CONSTRAINT fk_reserva_orcamentaria_assistencial_atualizado_por
    FOREIGN KEY (atualizado_por_id) REFERENCES public.usuarios_sistema(id);

ALTER TABLE IF EXISTS public.titulo_pagar
    ADD CONSTRAINT fk_titulo_pagar_criado_por
    FOREIGN KEY (criado_por_id) REFERENCES public.usuarios_sistema(id);

ALTER TABLE IF EXISTS public.titulo_pagar
    ADD CONSTRAINT fk_titulo_pagar_atualizado_por
    FOREIGN KEY (atualizado_por_id) REFERENCES public.usuarios_sistema(id);

ALTER TABLE IF EXISTS public.titulo_receber
    ADD CONSTRAINT fk_titulo_receber_criado_por
    FOREIGN KEY (criado_por_id) REFERENCES public.usuarios_sistema(id);

ALTER TABLE IF EXISTS public.titulo_receber
    ADD CONSTRAINT fk_titulo_receber_atualizado_por
    FOREIGN KEY (atualizado_por_id) REFERENCES public.usuarios_sistema(id);

ALTER TABLE IF EXISTS public.transferencia_entre_contas
    ADD CONSTRAINT fk_transferencia_entre_contas_criado_por
    FOREIGN KEY (criado_por_id) REFERENCES public.usuarios_sistema(id);

ALTER TABLE IF EXISTS public.transferencia_entre_contas
    ADD CONSTRAINT fk_transferencia_entre_contas_atualizado_por
    FOREIGN KEY (atualizado_por_id) REFERENCES public.usuarios_sistema(id);
