package com.upsaude.service;

import com.upsaude.api.request.RelatorioEstatisticasRequest;
import com.upsaude.api.response.RelatorioEstatisticasResponse;

/**
 * Interface de serviço para operações relacionadas a Relatórios e Estatísticas.
 *
 * @author UPSaúde
 */
public interface RelatoriosService {

    /**
     * Gera relatório de estatísticas gerais do sistema.
     *
     * @param request Parâmetros do relatório
     * @return Relatório com estatísticas
     */
    RelatorioEstatisticasResponse gerarEstatisticas(RelatorioEstatisticasRequest request);
}

