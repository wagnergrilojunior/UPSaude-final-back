package com.upsaude.service.api.sistema.relatorios;

import com.upsaude.api.request.sistema.relatorios.RelatorioComparativoRequest;
import com.upsaude.api.response.sistema.relatorios.RelatorioComparativoResponse;

/**
 * Service para geração de relatórios comparativos.
 */
public interface RelatorioComparativoService {

    /**
     * Gera relatório comparativo entre dois períodos.
     * 
     * @param request Dados do relatório comparativo
     * @return Relatório comparativo com dados dos dois períodos e comparações
     */
    RelatorioComparativoResponse gerarRelatorioComparativo(RelatorioComparativoRequest request);
}
