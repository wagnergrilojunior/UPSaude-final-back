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

    /**
     * Consumidor principal da fila (executa a cada 30 minutos).
     * Processa jobs enfileirados de forma geral.
     */
    @Scheduled(fixedDelayString = "#{${import.job.scheduler-interval-seconds:3600} * 1000}")
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
     * Scheduler rápido para processar jobs recém-enfileirados após upload completo.
     * Executa a cada 2 minutos (fixedRate) para processar jobs que acabaram de ser criados/enfileirados.
     * Prioriza jobs que foram enfileirados recentemente (últimos 5 minutos).
     * 
     * Usa fixedRate ao invés de fixedDelay para garantir execução a cada 2 minutos,
     * independente do tempo de execução da iteração anterior.
     */
    @Scheduled(fixedRateString = "#{${import.job.scheduler-rapido-interval-seconds:120} * 1000}")
    public void consumirFilaRapida() {
        if (!jobsEnabled) {
            // Jobs desabilitados - não processa nada
            return;
        }
        try {
            // Busca jobs enfileirados recentemente (últimos 5 minutos)
            OffsetDateTime limiteTempo = OffsetDateTime.now().minusMinutes(5);
            
            // Observação: o limite global no banco é reforçado no claim atômico,
            // mas aqui fazemos um pré-cálculo para reduzir chamadas.
            long processando = importJobJobRepository.countByStatus(ImportJobStatusEnum.PROCESSANDO);
            long slots = (long) maxConcurrentGlobal - processando;
            if (slots <= 0) {
                log.debug("Scheduler rápido: sem slots disponíveis (processando: {})", processando);
                return;
            }

            log.debug("Scheduler rápido: processando jobs recém-enfileirados (slots disponíveis: {})", slots);

            // Tenta preencher os slots com claims de jobs recém-enfileirados
            for (int i = 0; i < slots; i++) {
                UUID jobId = claimNextJobRecemEnfileirado(limiteTempo);
                if (jobId == null) {
                    break;
                }

                try {
                    // Executa fora da transação e fora do ciclo HTTP (thread pool dedicado)
                    importJobExecutor.execute(() -> importJobProcessor.processar(jobId));
                    log.debug("Scheduler rápido: job {} iniciado para processamento", jobId);
                } catch (RejectedExecutionException rex) {
                    // Se o executor estiver cheio, não deixa job travado em PROCESSANDO
                    log.warn("Executor de import jobs cheio no scheduler rápido; re-enfileirando job {}. Motivo: {}", jobId, rex.getMessage());
                    importJobQueueService.reEnfileirarOuFalhar(jobId, "Executor cheio (RejectedExecutionException)");
                    break;
                }
            }
        } catch (Exception e) {
            log.error("Erro no scheduler rápido de import jobs: {}", e.getMessage(), e);
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

    /**
     * Transação curta: claim atômico de 1 job ENFILEIRADO recém-enfileirado (após limiteTempo).
     * Prioriza jobs que foram enfileirados recentemente para processamento rápido após upload.
     * 
     * IMPORTANTE: Usa TransactionManager do JOB explicitamente
     */
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

    /**
     * Inicia manualmente o processamento de jobs enfileirados, sem esperar o próximo schedule.
     * Processa até o limite de jobs simultâneos configurado.
     * 
     * IMPORTANTE: Este método é chamado pela API, mas o processamento é executado em BACKGROUND
     * no pool de threads do JOB (importJobExecutor), não no pool HTTP da API.
     * 
     * Fluxo:
     * 1. API recebe requisição (pool HTTP)
     * 2. Controller chama este método (ainda no pool HTTP, mas retorna imediatamente)
     * 3. importJobExecutor.execute() enfileira o processamento no pool do JOB (assíncrono)
     * 4. API retorna resposta imediatamente (não espera o processamento)
     * 5. Processamento executa em paralelo no pool do JOB (threads dedicadas)
     * 
     * @return Número de jobs iniciados para processamento
     */
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
                    // IMPORTANTE: execute() é assíncrono - enfileira no pool do JOB e retorna imediatamente
                    // O processamento acontece em background no pool dedicado (importJobExecutor)
                    // A thread da API não fica bloqueada esperando o processamento
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


