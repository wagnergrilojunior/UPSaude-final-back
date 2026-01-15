package com.upsaude.service.api.sia;

import com.upsaude.api.response.sia.analytics.SiaPaComparacaoResponse;
import com.upsaude.api.response.sia.analytics.SiaPaRankingResponse;
import com.upsaude.api.response.sia.analytics.SiaPaSazonalidadeResponse;
import com.upsaude.api.response.sia.analytics.SiaPaTendenciaResponse;

public interface SiaPaAnalyticsService {

    SiaPaTendenciaResponse calcularTendenciaTemporal(String uf, String competenciaInicio, String competenciaFim);

    SiaPaSazonalidadeResponse calcularSazonalidade(String uf, String competenciaInicio, String competenciaFim);

    SiaPaComparacaoResponse compararPeriodos(String uf, String competenciaBase, String competenciaComparacao);

    SiaPaRankingResponse rankingEstabelecimentos(String uf, String competencia, Integer limit);
}

