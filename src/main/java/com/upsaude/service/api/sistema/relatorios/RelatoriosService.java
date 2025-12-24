package com.upsaude.service.api.sistema.relatorios;

import com.upsaude.api.request.sistema.relatorios.RelatorioEstatisticasRequest;
import com.upsaude.api.response.sistema.relatorios.RelatorioEstatisticasResponse;

public interface RelatoriosService {

    RelatorioEstatisticasResponse gerarEstatisticas(RelatorioEstatisticasRequest request);
}
