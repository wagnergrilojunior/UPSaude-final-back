package com.upsaude.service;

import com.upsaude.api.request.RelatorioEstatisticasRequest;
import com.upsaude.api.response.RelatorioEstatisticasResponse;

public interface RelatoriosService {

    RelatorioEstatisticasResponse gerarEstatisticas(RelatorioEstatisticasRequest request);
}
