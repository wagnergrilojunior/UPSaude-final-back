package com.upsaude.api.response.sistema.importacao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * DTO de resposta para erros detalhados de processamento de jobs.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImportJobErrorResponse {
    
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    
    private UUID jobId;
    private Long linha;
    private String codigoErro;
    private String mensagem;
    private String rawLineHash;
    private String rawLinePreview;
}

