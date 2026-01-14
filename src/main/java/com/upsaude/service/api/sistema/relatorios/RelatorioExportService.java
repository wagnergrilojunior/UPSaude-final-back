package com.upsaude.service.api.sistema.relatorios;

import com.upsaude.api.request.sistema.relatorios.RelatorioExportRequest;
import org.springframework.core.io.Resource;

/**
 * Service para exportação de relatórios em diferentes formatos.
 */
public interface RelatorioExportService {

    /**
     * Exporta um relatório no formato especificado.
     * 
     * @param request Dados do relatório a ser exportado
     * @return Resource com o arquivo gerado
     */
    Resource exportarRelatorio(RelatorioExportRequest request);
}
