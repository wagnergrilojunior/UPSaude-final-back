package com.upsaude.api.response.sistema.importacao;

import com.upsaude.enums.ImportJobStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImportJobStatusResponse {

    private UUID id;
    private ImportJobStatusEnum status;

    private Long linhasLidas;
    private Long linhasProcessadas;
    private Long linhasInseridas;
    private Long linhasErro;
    private Double percentualEstimado;

    private Double percentualProgresso;

    private Long totalRegistrosEstimado;

    private Long registrosProcessados;

    private OffsetDateTime startedAt;
    private OffsetDateTime finishedAt;
    private Long durationMs;

    private String errorSummary;
    private Long totalErros; 
}
