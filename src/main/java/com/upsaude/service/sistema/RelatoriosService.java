package com.upsaude.service.sistema;

import com.upsaude.api.request.sistema.RelatorioEstatisticasRequest;
import com.upsaude.api.response.sistema.RelatorioEstatisticasResponse;

public interface RelatoriosService {

    RelatorioEstatisticasResponse gerarEstatisticas(RelatorioEstatisticasRequest request);
}
