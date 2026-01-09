package com.upsaude.service.impl.job;

import com.upsaude.entity.sistema.importacao.ImportJob;
import com.upsaude.enums.ImportJobStatusEnum;
import com.upsaude.repository.sistema.importacao.ImportJobJobRepository;
import com.upsaude.service.job.ImportJobProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.RejectedExecutionException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImportJobScheduler {

    private final ImportJobJobRepository importJobJobRepository;
    private final ImportJobProcessor importJobProcessor;
    private final ImportJobQueueService importJobQueueService;

    @Qualifier("importJobExecutor")
    private final ThreadPoolTaskExecutor importJobExecutor;

    @Qualifier("jobTransactionManager")
    private final PlatformTransactionManager jobTransactionManager;

    @Value("${import.job.max-concurrent-global:1}")
    private int maxConcurrentGlobal;

    @Value("${import.job.max-concurrent-per-tenant:1}")
    private int maxConcurrentPerTenant;

    @Value("${import.job.scheduler-interval-seconds:3600}")
    private int schedulerIntervalSeconds;

    @Value("${import.job.scheduler-rapido-interval-seconds:120}")
    private int schedulerRapidoIntervalSeconds;

    @Value("${import.job.heartbeat-timeout-seconds:300}")
    private int heartbeatTimeoutSeconds;

    @Value("${import.job.enabled:true}")
    private boolean jobsEnabled;

    private final String instanceId = UUID.randomUUID().toString();
    private static final long SCHEDULER_CLAIM_LOCK_KEY = 88123499123L;

    @Scheduled(fixedDelayString = "#{${import.job.scheduler-interval-seconds:3600} * 1000}")
    public void consumirFila() {
        if (!jobsEnabled) {

            return;
        }
        try {

            long processando = importJobJobRepository.countByStatus(ImportJobStatusEnum.PROCESSANDO);
            long slots = (long) maxConcurrentGlobal - processando;
            if (slots <= 0) return;

            for (int i = 0; i < slots; i++) {
                UUID jobId = claimNextJobParaProcessar();
                if (jobId == null) {
                    break;
                }

                try {

                    importJobExecutor.execute(() -> importJobProcessor.processar(jobId));
                } catch (RejectedExecutionException rex) {

                    log.warn("Executor de import jobs cheio; re-enfileirando job {}. Motivo: {}", jobId, rex.getMessage());
                    importJobQueueService.reEnfileirarOuFalhar(jobId, "Executor cheio (RejectedExecutionException)");
                    break;
                }
            }
        } catch (Exception e) {
            log.error("Erro no scheduler de import jobs: {}", e.getMessage(), e);
        }
    }

    @Scheduled(fixedRateString = "#{${import.job.scheduler-rapido-interval-seconds:120} * 1000}")
    public void consumirFilaRapida() {
        if (!jobsEnabled) {

            return;
        }
        try {

            OffsetDateTime limiteTempo = OffsetDateTime.now().minusMinutes(5);

            long processando = importJobJobRepository.countByStatus(ImportJobStatusEnum.PROCESSANDO);
            long slots = (long) maxConcurrentGlobal - processando;
            if (slots <= 0) {
                log.debug("Scheduler rápido: sem slots disponíveis (processando: {})", processando);
                return;
            }

            log.debug("Scheduler rápido: processando jobs recém-enfileirados (slots disponíveis: {})", slots);

            for (int i = 0; i < slots; i++) {
                UUID jobId = claimNextJobRecemEnfileirado(limiteTempo);
                if (jobId == null) {
                    break;
                }

                try {

                    importJobExecutor.execute(() -> importJobProcessor.processar(jobId));
                    log.debug("Scheduler rápido: job {} iniciado para processamento", jobId);
                } catch (RejectedExecutionException rex) {

                    log.warn("Executor de import jobs cheio no scheduler rápido; re-enfileirando job {}. Motivo: {}", jobId, rex.getMessage());
                    importJobQueueService.reEnfileirarOuFalhar(jobId, "Executor cheio (RejectedExecutionException)");
                    break;
                }
            }
        } catch (Exception e) {
            log.error("Erro no scheduler rápido de import jobs: {}", e.getMessage(), e);
        }
    }

    @Scheduled(fixedDelayString = "#{60 * 1000}")
    public void detectarJobsTravados() {
        if (!jobsEnabled) {

            return;
        }
        try {
            OffsetDateTime expiredBefore = OffsetDateTime.now().minusSeconds(heartbeatTimeoutSeconds);
            List<ImportJob> stuck = importJobJobRepository.findStuckJobs(ImportJobStatusEnum.PROCESSANDO, expiredBefore);
            if (stuck.isEmpty()) {
                return;
            }
            for (ImportJob job : stuck) {
                importJobQueueService.reEnfileirarOuFalhar(job.getId(), "Heartbeat expirado (job travado)");
            }
        } catch (Exception e) {
            log.error("Erro ao detectar jobs travados: {}", e.getMessage(), e);
        }
    }

    protected UUID claimNextJobParaProcessar() {
        TransactionTemplate txTemplate = new TransactionTemplate(jobTransactionManager);
        return txTemplate.execute(status -> {
            OffsetDateTime now = OffsetDateTime.now();
            Boolean gotLock = importJobJobRepository.tryAdvisoryXactLock(SCHEDULER_CLAIM_LOCK_KEY);
            if (gotLock == null || !gotLock) {
                return null;
            }

            return importJobJobRepository.claimNextJobForProcessing(
                    ImportJobStatusEnum.ENFILEIRADO.name(),
                    ImportJobStatusEnum.PROCESSANDO.name(),
                    now,
                    instanceId,
                    maxConcurrentGlobal,
                    maxConcurrentPerTenant
            );
        });
    }

    protected UUID claimNextJobRecemEnfileirado(OffsetDateTime limiteTempo) {
        TransactionTemplate txTemplate = new TransactionTemplate(jobTransactionManager);
        return txTemplate.execute(status -> {
            OffsetDateTime now = OffsetDateTime.now();
            Boolean gotLock = importJobJobRepository.tryAdvisoryXactLock(SCHEDULER_CLAIM_LOCK_KEY);
            if (gotLock == null || !gotLock) {
                return null;
            }

            return importJobJobRepository.claimNextJobRecemEnfileirado(
                    ImportJobStatusEnum.ENFILEIRADO.name(),
                    ImportJobStatusEnum.PROCESSANDO.name(),
                    now,
                    limiteTempo,
                    instanceId,
                    maxConcurrentGlobal,
                    maxConcurrentPerTenant
            );
        });
    }

    public int iniciarProcessamentoManual() {
        if (!jobsEnabled) {
            log.warn("Tentativa de iniciar processamento manual com jobs desabilitados");
            return 0;
        }

        try {
            long processando = importJobJobRepository.countByStatus(ImportJobStatusEnum.PROCESSANDO);
            long slots = (long) maxConcurrentGlobal - processando;
            if (slots <= 0) {
                log.debug("Processamento manual: sem slots disponíveis (processando: {})", processando);
                return 0;
            }

            int jobsIniciados = 0;
            for (int i = 0; i < slots; i++) {
                UUID jobId = claimNextJobParaProcessar();
                if (jobId == null) {
                    break;
                }

                try {

                    importJobExecutor.execute(() -> importJobProcessor.processar(jobId));
                    jobsIniciados++;
                    log.info("Processamento manual: job {} enfileirado no pool do JOB para processamento em background", jobId);
                } catch (RejectedExecutionException rex) {
                    log.warn("Executor de import jobs cheio no processamento manual; re-enfileirando job {}. Motivo: {}", jobId, rex.getMessage());
                    importJobQueueService.reEnfileirarOuFalhar(jobId, "Executor cheio (RejectedExecutionException)");
                    break;
                }
            }

            log.info("Processamento manual: {} jobs enfileirados no pool do JOB para processamento em background", jobsIniciados);
            return jobsIniciados;
        } catch (Exception e) {
            log.error("Erro no processamento manual de import jobs: {}", e.getMessage(), e);
            throw new RuntimeException("Erro ao iniciar processamento manual de jobs", e);
        }
    }

}
