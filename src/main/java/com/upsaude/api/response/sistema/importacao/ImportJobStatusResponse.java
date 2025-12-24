package com.upsaude.api.response.sistema.importacao;

import com.upsaude.enums.ImportJobStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * DTO simplificado de resposta para status de um job (usado para polling).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImportJobStatusResponse {
    
    private UUID id;
    private ImportJobStatusEnum status;
    
    // Progresso
    private Long linhasLidas;
    private Long linhasProcessadas;
    private Long linhasInseridas;
    private Long linhasErro;
    private Double percentualEstimado;
    
    // Timestamps
    private OffsetDateTime startedAt;
    private OffsetDateTime finishedAt;
    private Long durationMs;
    
    // Erros
    private String errorSummary;
    private Long totalErros; // Contagem total de erros na tabela import_job_error
}

