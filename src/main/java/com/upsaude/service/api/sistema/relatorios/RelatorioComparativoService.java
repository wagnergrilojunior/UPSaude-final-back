package com.upsaude.service.api.sistema.relatorios;

import com.upsaude.api.request.sistema.relatorios.RelatorioComparativoRequest;
import com.upsaude.api.response.sistema.relatorios.RelatorioComparativoResponse;


public interface RelatorioComparativoService {

    
    RelatorioComparativoResponse gerarRelatorioComparativo(RelatorioComparativoRequest request);
}
