package com.upsaude.api.response.referencia.sigtap;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "Resposta com informações de uma modalidade SIGTAP")
public class SigtapModalidadeResponse {
    @Schema(description = "Identificador único da modalidade", example = "gg0e8400-e29b-41d4-a716-446655440010")
    private UUID id;

    @Schema(description = "Código oficial da modalidade", example = "01")
    private String codigoOficial;

    @Schema(description = "Nome da modalidade", example = "MODALIDADE AMBULATORIAL")
    private String nome;

    @Schema(description = "Competência inicial de vigência (formato AAAAMM)", example = "202501")
    private String competenciaInicial;

    @Schema(description = "Competência final de vigência (formato AAAAMM). Null se ainda está vigente")
    private String competenciaFinal;
}
