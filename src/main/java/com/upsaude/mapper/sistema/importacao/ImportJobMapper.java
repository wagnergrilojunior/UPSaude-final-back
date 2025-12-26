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

        // Calcula porcentagem de progresso (0.0 a 100.0)
        Double percentualProgresso = calcularPercentualProgresso(job);
        
        // Total de registros estimado (pode ser null se não houver estimativa)
        Long totalRegistrosEstimado = calcularTotalRegistrosEstimado(job);
        
        // Registros processados (equivalente a linhasProcessadas)
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

    /**
     * Calcula a porcentagem de progresso (0.0 a 100.0).
     * Prioriza percentualEstimado se disponível, senão calcula baseado em bytes processados ou status.
     */
    private Double calcularPercentualProgresso(ImportJob job) {
        // Se o job está concluído, retorna 100%
        if (job.getStatus() != null) {
            String statusName = job.getStatus().name();
            if (statusName.equals("CONCLUIDO")) {
                return 100.0;
            }
            if (statusName.equals("ERRO") || statusName.equals("CANCELADO")) {
                // Se tem linhas processadas, calcula baseado nelas
                if (job.getLinhasLidas() != null && job.getLinhasLidas() > 0) {
                    return 100.0; // Considera 100% mesmo com erro (já processou tudo que conseguiu)
                }
                return 0.0;
            }
        }

        // Se já tem percentual estimado, usa ele
        if (job.getPercentualEstimado() != null && job.getPercentualEstimado() > 0) {
            return Math.min(100.0, Math.max(0.0, job.getPercentualEstimado()));
        }

        // Tenta calcular baseado em bytes processados (checkpointByteOffset vs sizeBytes)
        if (job.getCheckpointByteOffset() != null && job.getSizeBytes() != null && job.getSizeBytes() > 0) {
            double percentual = (job.getCheckpointByteOffset().doubleValue() / job.getSizeBytes().doubleValue()) * 100.0;
            return Math.min(100.0, Math.max(0.0, percentual));
        }

        // Se não tem estimativa e não começou a processar, retorna 0
        if (job.getLinhasLidas() == null || job.getLinhasLidas() == 0) {
            return 0.0;
        }

        // Se está processando mas não tem informações suficientes, retorna null
        // O frontend pode usar isso para mostrar "Processando..." sem porcentagem
        return null;
    }

    /**
     * Calcula o total estimado de registros.
     * Pode ser null se não houver estimativa disponível.
     */
    private Long calcularTotalRegistrosEstimado(ImportJob job) {
        // Se o job está concluído, o total é o número de linhas lidas
        if (job.getStatus() != null && job.getStatus().name().equals("CONCLUIDO")) {
            return job.getLinhasLidas() != null ? job.getLinhasLidas() : null;
        }

        // Se tem percentual estimado e linhas lidas, pode calcular o total
        if (job.getPercentualEstimado() != null && job.getPercentualEstimado() > 0 
            && job.getLinhasLidas() != null && job.getLinhasLidas() > 0) {
            double total = (job.getLinhasLidas() * 100.0) / job.getPercentualEstimado();
            return Math.round(total);
        }

        // Se tem bytes processados e tamanho total, pode estimar baseado na proporção
        // Assumindo que o arquivo tem linhas proporcionais ao tamanho
        if (job.getCheckpointByteOffset() != null && job.getSizeBytes() != null 
            && job.getSizeBytes() > 0 && job.getLinhasLidas() != null && job.getLinhasLidas() > 0) {
            double proporcao = job.getCheckpointByteOffset().doubleValue() / job.getSizeBytes().doubleValue();
            if (proporcao > 0) {
                double total = job.getLinhasLidas() / proporcao;
                return Math.round(total);
            }
        }

        // Se não há estimativa disponível, retorna null
        return null;
    }
}

