package com.upsaude.api.response.referencia.sigtap;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "Resposta com informações de uma ocupação (CBO) SIGTAP")
public class SigtapCboResponse {
    @Schema(description = "Identificador único da ocupação", example = "ee0e8400-e29b-41d4-a716-446655440009")
    private UUID id;

    @Schema(description = "Código CBO (Classificação Brasileira de Ocupações)", example = "225110")
    private String codigoOficial;

    @Schema(description = "Nome da ocupação/profissão", example = "MÉDICO CLINICO GERAL")
    private String nome;
}
