package com.upsaude.integration.fhir.scheduler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.upsaude.integration.fhir.service.FhirSyncLogService;
import com.upsaude.integration.fhir.service.diagnostico.DiagnosticoSyncService;
import com.upsaude.integration.fhir.service.geografico.GeografiaFhirSyncService;
import com.upsaude.integration.fhir.service.vacinacao.VacinacaoSyncService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Scheduler para sincronizações periódicas de catálogos FHIR.
 * 
 * Executa sincronizações automáticas com proteção contra execuções muito frequentes.
 */
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "fhir.sync.scheduler.enabled", havingValue = "true", matchIfMissing = false)
public class FhirSyncScheduler {

    private final FhirSyncLogService syncLogService;
    private final VacinacaoSyncService vacinacaoSyncService;
    private final GeografiaFhirSyncService geografiaSyncService;
    private final DiagnosticoSyncService diagnosticoSyncService;

    @Value("${fhir.sync.scheduler.horas-protecao:24}")
    private int horasProtecao;

    /**
     * Sincronização diária de geografia (Estados e Municípios).
     * Executa às 02:00 AM diariamente.
     */
    @Scheduled(cron = "${fhir.sync.scheduler.geografia.cron:0 0 2 * * *}")
    public void sincronizarGeografia() {
        try {
            String recurso = "BRDivisaoGeografica-Estados";
            if (syncLogService.existeSincronizacaoRecente(recurso, horasProtecao)) {
                log.debug("Sincronização de geografia recente, pulando execução agendada");
                return;
            }

            log.info("Iniciando sincronização agendada de geografia FHIR");
            geografiaSyncService.sincronizarEstados();
            geografiaSyncService.sincronizarMunicipios();
            log.info("Sincronização agendada de geografia FHIR concluída");
        } catch (Exception e) {
            log.error("Erro na sincronização agendada de geografia FHIR: {}", e.getMessage(), e);
        }
    }

    /**
     * Sincronização diária de vacinação (catálogos básicos).
     * Executa às 03:00 AM diariamente.
     */
    @Scheduled(cron = "${fhir.sync.scheduler.vacinacao.cron:0 0 3 * * *}")
    public void sincronizarVacinacao() {
        try {
            String recurso = "BRImunobiologico";
            if (syncLogService.existeSincronizacaoRecente(recurso, horasProtecao)) {
                log.debug("Sincronização de vacinação recente, pulando execução agendada");
                return;
            }

            log.info("Iniciando sincronização agendada de vacinação FHIR");
            vacinacaoSyncService.sincronizarImunobiologicos(null);
            vacinacaoSyncService.sincronizarFabricantes(null);
            vacinacaoSyncService.sincronizarTiposDose(null);
            vacinacaoSyncService.sincronizarLocaisAplicacao(null);
            vacinacaoSyncService.sincronizarViasAdministracao(null);
            vacinacaoSyncService.sincronizarEstrategias(null);
            log.info("Sincronização agendada de vacinação FHIR concluída");
        } catch (Exception e) {
            log.error("Erro na sincronização agendada de vacinação FHIR: {}", e.getMessage(), e);
        }
    }

    /**
     * Sincronização semanal de diagnósticos (CID-10 e CIAP-2).
     * Executa domingo às 04:00 AM.
     * 
     * Nota: CID-10 é um catálogo grande (~14.000 códigos), então sincronizamos semanalmente.
     */
    @Scheduled(cron = "${fhir.sync.scheduler.diagnosticos.cron:0 0 4 * * 0}")
    public void sincronizarDiagnosticos() {
        try {
            String recurso = "BRCID10";
            if (syncLogService.existeSincronizacaoRecente(recurso, horasProtecao * 7)) {
                log.debug("Sincronização de diagnósticos recente, pulando execução agendada");
                return;
            }

            log.info("Iniciando sincronização agendada de diagnósticos FHIR");
            diagnosticoSyncService.syncCid10();
            diagnosticoSyncService.syncCiap2();
            log.info("Sincronização agendada de diagnósticos FHIR concluída");
        } catch (Exception e) {
            log.error("Erro na sincronização agendada de diagnósticos FHIR: {}", e.getMessage(), e);
        }
    }
}
