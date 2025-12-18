package com.upsaude.service.sistema;

import com.upsaude.api.request.RelatorioEstatisticasRequest;
import com.upsaude.api.response.RelatorioEstatisticasResponse;

public interface RelatoriosService {

    RelatorioEstatisticasResponse gerarEstatisticas(RelatorioEstatisticasRequest request);
}
