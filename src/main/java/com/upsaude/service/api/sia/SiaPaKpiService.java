package com.upsaude.service.api.sia;

import com.upsaude.api.response.sia.kpi.SiaPaKpiResponse;

public interface SiaPaKpiService {

    SiaPaKpiResponse kpiGeral(String competencia, String uf);

    SiaPaKpiResponse kpiPorEstabelecimento(String competencia, String uf, String codigoCnes);

    SiaPaKpiResponse kpiPorProcedimento(String competencia, String uf, String procedimentoCodigo);
}

