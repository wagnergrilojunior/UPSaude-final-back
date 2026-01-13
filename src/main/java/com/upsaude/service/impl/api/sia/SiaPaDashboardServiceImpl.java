package com.upsaude.service.impl.api.sia;

import com.upsaude.api.response.sia.anomalia.SiaPaAnomaliaResponse;
import com.upsaude.api.response.sia.dashboard.SiaPaDashboardResponse;
import com.upsaude.api.response.sia.financeiro.SiaPaFinanceiroIntegracaoResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.service.api.sia.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SiaPaDashboardServiceImpl implements SiaPaDashboardService {

    private final SiaPaKpiService kpiService;
    private final SiaPaRelatorioService relatorioService;
    private final SiaPaAnalyticsService analyticsService;
    private final SiaPaAnomaliaDetectionService anomaliaService;
    private final SiaPaFinanceiroIntegrationService financeiroIntegrationService;

    @Override
    public SiaPaDashboardResponse dashboard(String uf,
                                           String competencia,
                                           String competenciaInicio,
                                           String competenciaFim,
                                           Boolean incluirFinanceiro) {

        if (!StringUtils.hasText(competencia)) {
            throw new BadRequestException("competencia é obrigatória (AAAAMM)");
        }
        if (!StringUtils.hasText(uf)) {
            throw new BadRequestException("uf é obrigatória (2 letras)");
        }

        String ufEfetiva = uf.trim().toUpperCase();

        // Defaults para tendência: últimos 12 meses até a competência atual, se não informado.
        String inicio = competenciaInicio;
        String fim = competenciaFim;
        if (!StringUtils.hasText(inicio) || !StringUtils.hasText(fim)) {
            fim = competencia;
            inicio = calcularCompetenciaMinusMeses(competencia, 11);
        }

        var kpis = kpiService.kpiGeral(competencia, ufEfetiva);
        var topProc = relatorioService.gerarTopProcedimentos(ufEfetiva, competencia, 10);
        var topCid = relatorioService.gerarTopCid(ufEfetiva, competencia, 10);
        var tendencia = analyticsService.calcularTendenciaTemporal(ufEfetiva, inicio, fim);

        List<SiaPaAnomaliaResponse> anomalias = List.of();
        try {
            anomalias = anomaliaService
                    .listar(competencia, ufEfetiva, PageRequest.of(0, 20))
                    .getContent();
        } catch (Exception e) {
            log.debug("Dashboard: anomalias indisponíveis (tabela pode não existir): {}", e.getMessage());
        }

        SiaPaFinanceiroIntegracaoResponse conciliacao = null;
        if (Boolean.TRUE.equals(incluirFinanceiro)) {
            try {
                conciliacao = financeiroIntegrationService.conciliacao(competencia, ufEfetiva, 30);
            } catch (Exception e) {
                log.debug("Dashboard: conciliação financeira indisponível: {}", e.getMessage());
            }
        }

        return SiaPaDashboardResponse.builder()
                .uf(ufEfetiva)
                .competencia(competencia)
                .kpis(kpis)
                .topProcedimentos(topProc)
                .topCid(topCid)
                .tendencia(tendencia)
                .anomaliasRecentes(anomalias)
                .conciliacaoFinanceira(conciliacao)
                .build();
    }

    private String calcularCompetenciaMinusMeses(String competencia, int meses) {
        // competencia = AAAAMM
        if (competencia == null || competencia.length() < 6) return competencia;
        int ano = Integer.parseInt(competencia.substring(0, 4));
        int mes = Integer.parseInt(competencia.substring(4, 6));

        int total = (ano * 12 + (mes - 1)) - meses;
        int novoAno = total / 12;
        int novoMes = (total % 12) + 1;
        return String.format("%04d%02d", novoAno, novoMes);
    }
}

