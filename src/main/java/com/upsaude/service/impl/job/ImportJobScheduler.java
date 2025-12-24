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

/**
 * Scheduler responsável por consumir a fila (import_job) e disparar processamento em background.
 * Importante: mantém transações curtas apenas para \"pegar\" o job e mudar status.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ImportJobScheduler {

    // USO EXCLUSIVO JOB - Scheduler usa pool JOB
    private final ImportJobJobRepository importJobJobRepository;
    private final ImportJobProcessor importJobProcessor;
    private final ImportJobQueueService importJobQueueService;

    @Qualifier("importJobExecutor")
    private final ThreadPoolTaskExecutor importJobExecutor;
    
    // USO EXCLUSIVO JOB - TransactionManager do JOB
    @Qualifier("jobTransactionManager")
    private final PlatformTransactionManager jobTransactionManager;

    @Value("${import.job.max-concurrent-global:1}")
    private int maxConcurrentGlobal;

    @Value("${import.job.max-concurrent-per-tenant:1}")
    private int maxConcurrentPerTenant;

    @Value("${import.job.scheduler-interval-seconds:5}")
    private int schedulerIntervalSeconds;

    @Value("${import.job.heartbeat-timeout-seconds:300}")
    private int heartbeatTimeoutSeconds;

    @Value("${import.job.enabled:true}")
    private boolean jobsEnabled;

    private final String instanceId = UUID.randomUUID().toString();
    private static final long SCHEDULER_CLAIM_LOCK_KEY = 88123499123L;

    /**
     * Consumidor principal da fila.
     */
    @Scheduled(fixedDelayString = "#{${import.job.scheduler-interval-seconds:5} * 1000}")
    public void consumirFila() {
        if (!jobsEnabled) {
            // Jobs desabilitados - não processa nada
            return;
        }
        try {
            // Observação: o limite global no banco é reforçado no claim atômico,
            // mas aqui fazemos um pré-cálculo para reduzir chamadas.
            long processando = importJobJobRepository.countByStatus(ImportJobStatusEnum.PROCESSANDO);
            long slots = (long) maxConcurrentGlobal - processando;
            if (slots <= 0) return;

            // Tenta preencher os slots com claims de 1 job por vez (evita starvation por tenant)
            for (int i = 0; i < slots; i++) {
                UUID jobId = claimNextJobParaProcessar();
                if (jobId == null) {
                    break;
                }

                try {
                    // Executa fora da transação e fora do ciclo HTTP (thread pool dedicado)
                    importJobExecutor.execute(() -> importJobProcessor.processar(jobId));
                } catch (RejectedExecutionException rex) {
                    // Se o executor estiver cheio, não deixa job travado em PROCESSANDO
                    log.warn("Executor de import jobs cheio; re-enfileirando job {}. Motivo: {}", jobId, rex.getMessage());
                    importJobQueueService.reEnfileirarOuFalhar(jobId, "Executor cheio (RejectedExecutionException)");
                    break;
                }
            }
        } catch (Exception e) {
            log.error("Erro no scheduler de import jobs: {}", e.getMessage(), e);
        }
    }

    /**
     * Detecta jobs travados (heartbeat expirado) e re-enfileira com backoff.
     */
    @Scheduled(fixedDelayString = "#{60 * 1000}")
    public void detectarJobsTravados() {
        if (!jobsEnabled) {
            // Jobs desabilitados - não detecta jobs travados
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

    /**
     * Transação curta: claim atômico de 1 job ENFILEIRADO com SKIP LOCKED, respeitando limites global e por tenant.
     * Usa advisory lock transacional para serializar o claim entre instâncias e reduzir race conditions.
     * 
     * IMPORTANTE: Usa TransactionManager do JOB explicitamente
     */
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

}


