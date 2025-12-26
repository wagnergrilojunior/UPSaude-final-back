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
    
    // Campos para barras de progresso
    /**
     * Porcentagem de progresso (0.0 a 100.0) para barra de progresso.
     * Calculado baseado no percentualEstimado ou linhas processadas.
     */
    private Double percentualProgresso;
    
    /**
     * Quantidade total de registros estimados (para cálculo de porcentagem).
     * Pode ser null se não houver estimativa disponível.
     */
    private Long totalRegistrosEstimado;
    
    /**
     * Quantidade de registros processados (para barra de progresso).
     * Equivale a linhasProcessadas.
     */
    private Long registrosProcessados;
    
    // Timestamps
    private OffsetDateTime startedAt;
    private OffsetDateTime finishedAt;
    private Long durationMs;
    
    // Erros
    private String errorSummary;
    private Long totalErros; // Contagem total de erros na tabela import_job_error
}

