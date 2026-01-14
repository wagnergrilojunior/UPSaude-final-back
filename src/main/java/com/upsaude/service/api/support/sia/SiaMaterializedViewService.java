package com.upsaude.service.api.support.sia;

/**
 * Service para gerenciar views materializadas do SIA-PA.
 */
public interface SiaMaterializedViewService {

    /**
     * Atualiza todas as views materializadas do SIA-PA.
     * Deve ser chamado periodicamente (ex: diariamente ou após importações de dados).
     */
    void refreshAllViews();

    /**
     * Atualiza a view materializada de produção mensal.
     */
    void refreshProducaoMensalView();

    /**
     * Atualiza a view materializada de top procedimentos.
     */
    void refreshTopProcedimentosView();

    /**
     * Atualiza a view materializada de top CID.
     */
    void refreshTopCidView();

    /**
     * Atualiza a view materializada de KPIs gerais.
     */
    void refreshKpiGeralView();
}
