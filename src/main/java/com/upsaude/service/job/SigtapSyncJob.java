package com.upsaude.service.job;

import com.upsaude.service.SigtapSyncService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Job agendado para sincroniza??o mensal do SIGTAP.
 *
 * <p>A compet?ncia pode ser configurada via properties; quando n?o informada, o servi?o calcula
 * automaticamente (m?s anterior).
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SigtapSyncJob {

    private final SigtapSyncService sigtapSyncService;

    @Scheduled(cron = "${sigtap.sync.cron}")
    public void executarSincronizacaoMensal() {
        log.info("SIGTAP job iniciado (agendado)");
        sigtapSyncService.sincronizarTudo(null);
        log.info("SIGTAP job finalizado (agendado)");
    }
}

