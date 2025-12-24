package com.upsaude.service.impl.job;

import com.upsaude.entity.sistema.importacao.ImportJob;
import com.upsaude.enums.ImportJobStatusEnum;
import com.upsaude.exception.ImportJobFatalException;
import com.upsaude.exception.ImportJobRecoverableException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.sistema.importacao.ImportJobJobRepository;
import com.upsaude.service.job.ImportJobProcessor;
import com.upsaude.service.job.ImportJobWorker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Dispatcher de processamento: carrega o job, encontra o worker correto por tipo
 * e executa fora do ciclo HTTP.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ImportJobProcessorImpl implements ImportJobProcessor {

    // USO EXCLUSIVO JOB - Processor usa pool JOB
    private final ImportJobJobRepository importJobJobRepository;
    private final List<ImportJobWorker> workers;
    private final ImportJobQueueService importJobQueueService;

    @Override
    public void processar(UUID jobId) {
        if (jobId == null) return;
        ImportJob job = importJobJobRepository.findById(jobId)
                .orElseThrow(() -> new NotFoundException("Job de importação não encontrado: " + jobId));

        ImportJobWorker worker = workers.stream()
                .filter(w -> w.getTipo() == job.getTipo())
                .findFirst()
                .orElse(null);

        if (worker == null) {
            log.error("Nenhum worker registrado para o tipo {}. Job: {}", job.getTipo(), jobId);
            marcarErro(jobId, "Nenhum worker registrado para o tipo: " + job.getTipo());
            return;
        }

        try {
            worker.processar(job.getId());
        } catch (ImportJobRecoverableException e) {
            log.warn("Falha recuperável ao processar job {} (tipo {}): {}", jobId, job.getTipo(), e.getMessage());
            importJobQueueService.reEnfileirarOuFalhar(jobId, "Falha recuperável: " + e.getMessage());
        } catch (ImportJobFatalException e) {
            log.error("Falha fatal ao processar job {} (tipo {}): {}", jobId, job.getTipo(), e.getMessage(), e);
            marcarErro(jobId, "Falha fatal: " + e.getMessage());
        } catch (Exception e) {
            log.error("Erro fatal ao processar job {} (tipo {}): {}", jobId, job.getTipo(), e.getMessage(), e);
            marcarErro(jobId, "Erro fatal: " + e.getMessage());
        }
    }

    /**
     * IMPORTANTE: Este método não usa @Transactional porque pode ser chamado de contextos diferentes.
     * Se necessário, usar TransactionTemplate com jobTransactionManager.
     */
    protected void marcarErro(UUID jobId, String resumo) {
        if (jobId == null) return;
        ImportJob job = importJobJobRepository.findById(jobId)
                .orElseThrow(() -> new NotFoundException("Job de importação não encontrado: " + jobId));
        job.setStatus(ImportJobStatusEnum.ERRO);
        job.setErrorSummary(resumo);
        job.setNextRunAt(null);
        job.setLockedAt(null);
        job.setLockedBy(null);
        job.setHeartbeatAt(null);
        job.setFinishedAt(OffsetDateTime.now());
        if (job.getStartedAt() != null) {
            job.setDurationMs(java.time.Duration.between(job.getStartedAt(), job.getFinishedAt()).toMillis());
        }
        importJobJobRepository.save(job);
    }
}


