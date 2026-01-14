package com.upsaude.service.api.sia;

/**
 * Service para gerenciamento de views materializadas do SIA.
 */
public interface SiaMaterializedViewService {

    /**
     * Atualiza todas as views materializadas do SIA.
     */
    void refreshAllViews();

    /**
     * Atualiza uma view materializada específica.
     * 
     * @param viewName Nome da view materializada
     */
    void refreshView(String viewName);

    /**
     * Verifica se uma view materializada existe e está atualizada.
     * 
     * @param viewName Nome da view materializada
     * @return true se a view existe e está atualizada
     */
    boolean isViewUpToDate(String viewName);
}
