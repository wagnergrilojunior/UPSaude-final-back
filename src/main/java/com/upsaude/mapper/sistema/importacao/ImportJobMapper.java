package com.upsaude.mapper.sistema.importacao;

import com.upsaude.api.response.sistema.importacao.ImportJobResponse;
import com.upsaude.api.response.sistema.importacao.ImportJobStatusResponse;
import com.upsaude.entity.sistema.importacao.ImportJob;
import org.springframework.stereotype.Component;

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

        Double percentualProgresso = calcularPercentualProgresso(job);

        Long totalRegistrosEstimado = calcularTotalRegistrosEstimado(job);

        Long registrosProcessados = job.getLinhasProcessadas() != null ? job.getLinhasProcessadas() : 0L;

        return ImportJobStatusResponse.builder()
                .id(job.getId())
                .status(job.getStatus())
                .linhasLidas(job.getLinhasLidas())
                .linhasProcessadas(job.getLinhasProcessadas())
                .linhasInseridas(job.getLinhasInseridas())
                .linhasErro(job.getLinhasErro())
                .percentualEstimado(job.getPercentualEstimado())
                .percentualProgresso(percentualProgresso)
                .totalRegistrosEstimado(totalRegistrosEstimado)
                .registrosProcessados(registrosProcessados)
                .startedAt(job.getStartedAt())
                .finishedAt(job.getFinishedAt())
                .durationMs(job.getDurationMs())
                .errorSummary(job.getErrorSummary())
                .totalErros(totalErros)
                .build();
    }

    private Double calcularPercentualProgresso(ImportJob job) {

        if (job.getStatus() != null) {
            String statusName = job.getStatus().name();
            if (statusName.equals("CONCLUIDO")) {
                return 100.0;
            }
            if (statusName.equals("ERRO") || statusName.equals("CANCELADO")) {

                if (job.getLinhasLidas() != null && job.getLinhasLidas() > 0) {
                    return 100.0; 
                }
                return 0.0;
            }
        }

        if (job.getPercentualEstimado() != null && job.getPercentualEstimado() > 0) {
            return Math.min(100.0, Math.max(0.0, job.getPercentualEstimado()));
        }

        if (job.getCheckpointByteOffset() != null && job.getSizeBytes() != null && job.getSizeBytes() > 0) {
            double percentual = (job.getCheckpointByteOffset().doubleValue() / job.getSizeBytes().doubleValue()) * 100.0;
            return Math.min(100.0, Math.max(0.0, percentual));
        }

        if (job.getLinhasLidas() == null || job.getLinhasLidas() == 0) {
            return 0.0;
        }

        return null;
    }

    private Long calcularTotalRegistrosEstimado(ImportJob job) {

        if (job.getStatus() != null && job.getStatus().name().equals("CONCLUIDO")) {
            return job.getLinhasLidas() != null ? job.getLinhasLidas() : null;
        }

        if (job.getPercentualEstimado() != null && job.getPercentualEstimado() > 0 
            && job.getLinhasLidas() != null && job.getLinhasLidas() > 0) {
            double total = (job.getLinhasLidas() * 100.0) / job.getPercentualEstimado();
            return Math.round(total);
        }

        if (job.getCheckpointByteOffset() != null && job.getSizeBytes() != null 
            && job.getSizeBytes() > 0 && job.getLinhasLidas() != null && job.getLinhasLidas() > 0) {
            double proporcao = job.getCheckpointByteOffset().doubleValue() / job.getSizeBytes().doubleValue();
            if (proporcao > 0) {
                double total = job.getLinhasLidas() / proporcao;
                return Math.round(total);
            }
        }

        return null;
    }
}
