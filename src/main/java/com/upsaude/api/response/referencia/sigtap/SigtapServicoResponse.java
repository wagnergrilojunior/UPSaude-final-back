package com.upsaude.api.response.referencia.sigtap;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "Resposta com informações de um serviço/exame SIGTAP")
public class SigtapServicoResponse {
    @Schema(description = "Identificador único do serviço", example = "770e8400-e29b-41d4-a716-446655440002")
    private UUID id;

    @Schema(description = "Código oficial do serviço (geralmente 1-2 dígitos)", example = "01")
    private String codigoOficial;

    @Schema(description = "Nome do serviço/exame", example = "SERVIÇO HOSPITALAR")
    private String nome;
}
