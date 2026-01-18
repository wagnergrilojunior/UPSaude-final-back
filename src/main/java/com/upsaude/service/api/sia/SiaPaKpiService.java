package com.upsaude.service.api.sia;

import com.upsaude.api.response.sia.kpi.SiaPaKpiResponse;

import java.util.UUID;

public interface SiaPaKpiService {

    SiaPaKpiResponse kpiGeral(String competencia, String uf);

    SiaPaKpiResponse kpiPorEstabelecimento(String competencia, String uf, String codigoCnes);

    SiaPaKpiResponse kpiPorProcedimento(String competencia, String uf, String procedimentoCodigo);

    
    SiaPaKpiResponse kpiPorTenant(String competencia, String uf);

    
    SiaPaKpiResponse kpiPorEstabelecimentoId(UUID estabelecimentoId, String competencia, String uf);

    
    SiaPaKpiResponse kpiPorMedicoId(UUID medicoId, String competencia, String uf);
}

