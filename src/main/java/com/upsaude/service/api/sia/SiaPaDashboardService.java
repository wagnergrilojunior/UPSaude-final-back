package com.upsaude.service.api.sia;

import com.upsaude.api.response.sia.dashboard.SiaPaDashboardResponse;

public interface SiaPaDashboardService {

    SiaPaDashboardResponse dashboard(String uf, String competencia, String competenciaInicio, String competenciaFim, Boolean incluirFinanceiro);
}

