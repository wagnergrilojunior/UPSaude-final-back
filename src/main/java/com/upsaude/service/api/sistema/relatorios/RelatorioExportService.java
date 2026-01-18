package com.upsaude.service.api.sistema.relatorios;

import com.upsaude.api.request.sistema.relatorios.RelatorioExportRequest;
import org.springframework.core.io.Resource;


public interface RelatorioExportService {

    
    Resource exportarRelatorio(RelatorioExportRequest request);
}
