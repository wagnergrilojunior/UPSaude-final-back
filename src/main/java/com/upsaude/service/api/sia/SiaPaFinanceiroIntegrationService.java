package com.upsaude.service.api.sia;

import com.upsaude.api.response.sia.financeiro.SiaPaFinanceiroIntegracaoResponse;
import com.upsaude.api.response.sia.financeiro.SiaPaFinanceiroReceitaPorCompetenciaResponse;

public interface SiaPaFinanceiroIntegrationService {

    SiaPaFinanceiroIntegracaoResponse conciliacao(String competencia, String uf, Integer limitNaoFaturados);

    SiaPaFinanceiroReceitaPorCompetenciaResponse receitaPorCompetencia(String uf, String competenciaInicio, String competenciaFim);
}

