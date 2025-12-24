package com.upsaude.mapper.sistema.importacao;

import com.upsaude.api.response.sistema.importacao.ImportJobResponse;
import com.upsaude.api.response.sistema.importacao.ImportJobStatusResponse;
import com.upsaude.entity.sistema.importacao.ImportJob;
import org.springframework.stereotype.Component;

/**
 * Mapper para converter entidades ImportJob em DTOs de resposta.
 */
@Component
public class ImportJobMapper {

    public ImportJobResponse toResponse(ImportJob job) {
        if (job == null) {
            return null;
        }

        return ImportJobResponse.builder()
                .id(job.getId())
                .createdAt(job.getCreatedAt())
                .updatedAt(job.getUpdatedAt())
                .active(job.getActive())
                .tipo(job.getTipo())
                .competenciaAno(job.getCompetenciaAno())
                .competenciaMes(job.getCompetenciaMes())
                .uf(job.getUf())
                .storageBucket(job.getStorageBucket())
                .storagePath(job.getStoragePath())
                .originalFilename(job.getOriginalFilename())
                .contentType(job.getContentType())
                .sizeBytes(job.getSizeBytes())
                .checksum(job.getChecksum())
                .status(job.getStatus())
                .priority(job.getPriority())
                .attempts(job.getAttempts())
                .maxAttempts(job.getMaxAttempts())
                .nextRunAt(job.getNextRunAt())
                .lockedAt(job.getLockedAt())
                .lockedBy(job.getLockedBy())
                .heartbeatAt(job.getHeartbeatAt())
                .linhasLidas(job.getLinhasLidas())
                .linhasProcessadas(job.getLinhasProcessadas())
                .linhasInseridas(job.getLinhasInseridas())
                .linhasErro(job.getLinhasErro())
                .percentualEstimado(job.getPercentualEstimado())
                .startedAt(job.getStartedAt())
                .finishedAt(job.getFinishedAt())
                .durationMs(job.getDurationMs())
                .checkpointLinha(job.getCheckpointLinha())
                .checkpointByteOffset(job.getCheckpointByteOffset())
                .errorSummary(job.getErrorSummary())
                .errorSampleJson(job.getErrorSampleJson())
                .createdByUserId(job.getCreatedByUserId())
                .build();
    }

    public ImportJobStatusResponse toStatusResponse(ImportJob job, Long totalErros) {
        if (job == null) {
            return null;
        }

        return ImportJobStatusResponse.builder()
                .id(job.getId())
                .status(job.getStatus())
                .linhasLidas(job.getLinhasLidas())
                .linhasProcessadas(job.getLinhasProcessadas())
                .linhasInseridas(job.getLinhasInseridas())
                .linhasErro(job.getLinhasErro())
                .percentualEstimado(job.getPercentualEstimado())
                .startedAt(job.getStartedAt())
                .finishedAt(job.getFinishedAt())
                .durationMs(job.getDurationMs())
                .errorSummary(job.getErrorSummary())
                .totalErros(totalErros)
                .build();
    }
}

