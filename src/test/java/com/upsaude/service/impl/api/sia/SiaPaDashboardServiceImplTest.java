package com.upsaude.service.impl.api.sia;

import com.upsaude.api.response.sia.dashboard.SiaPaDashboardResponse;
import com.upsaude.api.response.sia.kpi.SiaPaKpiResponse;
import com.upsaude.api.response.sia.relatorios.SiaPaRelatorioTopCidResponse;
import com.upsaude.api.response.sia.relatorios.SiaPaRelatorioTopProcedimentosResponse;
import com.upsaude.api.response.sia.analytics.SiaPaTendenciaResponse;
import com.upsaude.service.api.sia.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("null")
class SiaPaDashboardServiceImplTest {

    @Mock private SiaPaKpiService kpiService;
    @Mock private SiaPaRelatorioService relatorioService;
    @Mock private SiaPaAnalyticsService analyticsService;
    @Mock private SiaPaAnomaliaDetectionService anomaliaService;
    @Mock private SiaPaFinanceiroIntegrationService financeiroIntegrationService;

    @InjectMocks
    private SiaPaDashboardServiceImpl service;

    @Test
    void deveMontarDashboardBasicoSemFinanceiro() {
        when(kpiService.kpiGeral("202501", "MG")).thenReturn(SiaPaKpiResponse.builder().competencia("202501").uf("MG").build());
        when(relatorioService.gerarTopProcedimentos("MG", "202501", 10)).thenReturn(SiaPaRelatorioTopProcedimentosResponse.builder().build());
        when(relatorioService.gerarTopCid("MG", "202501", 10)).thenReturn(SiaPaRelatorioTopCidResponse.builder().build());
        when(analyticsService.calcularTendenciaTemporal("MG", "202401", "202501")).thenReturn(SiaPaTendenciaResponse.builder().build());
        when(anomaliaService.listar("202501", "MG", PageRequest.of(0, 20))).thenReturn(new PageImpl<>(Collections.emptyList()));

        SiaPaDashboardResponse resp = service.dashboard("MG", "202501", "202401", "202501", false);
        assertEquals("MG", resp.getUf());
        assertEquals("202501", resp.getCompetencia());
    }
}

