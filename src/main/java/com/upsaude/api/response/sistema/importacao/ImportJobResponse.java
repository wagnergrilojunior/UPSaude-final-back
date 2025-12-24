package com.upsaude.api.response.sistema.importacao;

import com.upsaude.enums.ImportJobStatusEnum;
import com.upsaude.enums.ImportJobTipoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * DTO de resposta para informações de um job de importação.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImportJobResponse {
    
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    
    // Identificação
    private ImportJobTipoEnum tipo;
    private String competenciaAno;
    private String competenciaMes;
    private String uf;
    
    // Arquivo no Storage
    private String storageBucket;
    private String storagePath;
    private String originalFilename;
    private String contentType;
    private Long sizeBytes;
    private String checksum;
    
    // Status e execução
    private ImportJobStatusEnum status;
    private Integer priority;
    private Integer attempts;
    private Integer maxAttempts;
    private OffsetDateTime nextRunAt;
    private OffsetDateTime lockedAt;
    private String lockedBy;
    private OffsetDateTime heartbeatAt;
    
    // Progresso
    private Long linhasLidas;
    private Long linhasProcessadas;
    private Long linhasInseridas;
    private Long linhasErro;
    private Double percentualEstimado;
    private OffsetDateTime startedAt;
    private OffsetDateTime finishedAt;
    private Long durationMs;
    
    // Checkpoint
    private Long checkpointLinha;
    private Long checkpointByteOffset;
    
    // Erros
    private String errorSummary;
    private String errorSampleJson;
    
    // Auditoria
    private UUID createdByUserId;
}

