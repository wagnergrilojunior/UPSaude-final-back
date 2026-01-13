package com.upsaude.api.response.sia.dashboard;

import com.upsaude.api.response.sia.anomalia.SiaPaAnomaliaResponse;
import com.upsaude.api.response.sia.analytics.SiaPaTendenciaResponse;
import com.upsaude.api.response.sia.financeiro.SiaPaFinanceiroIntegracaoResponse;
import com.upsaude.api.response.sia.kpi.SiaPaKpiResponse;
import com.upsaude.api.response.sia.relatorios.SiaPaRelatorioTopCidResponse;
import com.upsaude.api.response.sia.relatorios.SiaPaRelatorioTopProcedimentosResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SiaPaDashboardResponse {

    private String uf;
    private String competencia;

    private SiaPaKpiResponse kpis;
    private SiaPaRelatorioTopProcedimentosResponse topProcedimentos;
    private SiaPaRelatorioTopCidResponse topCid;
    private SiaPaTendenciaResponse tendencia;

    private List<SiaPaAnomaliaResponse> anomaliasRecentes;

    // Opcional: conciliação com faturamento interno (depende de dados do tenant)
    private SiaPaFinanceiroIntegracaoResponse conciliacaoFinanceira;
}

