package com.upsaude.service.impl.job;

import com.upsaude.entity.sistema.importacao.ImportJob;
import com.upsaude.enums.ImportJobStatusEnum;
import com.upsaude.repository.sistema.importacao.ImportJobJobRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.OffsetDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImportJobQueueService {

    private final ImportJobJobRepository importJobJobRepository;

    @Qualifier("jobTransactionManager")
    private final PlatformTransactionManager jobTransactionManager;

    @Value("${import.job.retry-backoff-base-seconds:60}")
    private int retryBackoffBaseSeconds;

    @Value("${import.job.retry-min-interval-seconds:30}")
    private int retryMinIntervalSeconds;

    public void reEnfileirarOuFalhar(UUID jobId, String motivo) {
        TransactionTemplate txTemplate = new TransactionTemplate(jobTransactionManager);
        txTemplate.execute(status -> {
            if (jobId == null) return null;

            ImportJob job = importJobJobRepository.findById(jobId).orElse(null);
            if (job == null) return null;

            int attempts = job.getAttempts() != null ? job.getAttempts() : 0;
            int maxAttempts = job.getMaxAttempts() != null ? job.getMaxAttempts() : 3;

            attempts++;
            job.setAttempts(attempts);

            if (attempts > maxAttempts) {
                job.setStatus(ImportJobStatusEnum.ERRO);
                job.setErrorSummary(motivo + " (max_attempts excedido)");
                job.setFinishedAt(OffsetDateTime.now());
                importJobJobRepository.save(job);
                log.warn("Job {} excedeu max_attempts ({}). Marcado como ERRO.", jobId, maxAttempts);
                return null;
            }

            long backoffSeconds = Math.min((long) retryBackoffBaseSeconds * (1L << Math.max(0, attempts - 1)), 3600L);
            long minInterval = Math.max(0, retryMinIntervalSeconds);
            long effectiveDelaySeconds = Math.max(backoffSeconds, minInterval);

            job.setStatus(ImportJobStatusEnum.ENFILEIRADO);
            job.setNextRunAt(OffsetDateTime.now().plusSeconds(effectiveDelaySeconds));
            job.setLockedAt(null);
            job.setLockedBy(null);
            job.setHeartbeatAt(null);
            job.setErrorSummary(motivo + " (retry " + attempts + "/" + maxAttempts + ")");
            importJobJobRepository.save(job);
            log.info("Job {} re-enfileirado com delay={}s (attempt {}/{})", jobId, effectiveDelaySeconds, attempts, maxAttempts);
            return null;
        });
    }
}
