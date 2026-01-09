package com.upsaude.api.response.referencia.sigtap;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Resposta com informações de rubrica SIGTAP (sub-tipo de financiamento)")
public class SigtapRubricaResponse {
    @Schema(description = "Código oficial da rubrica", example = "010101")
    private String codigoOficial;

    @Schema(description = "Nome da rubrica", example = "Atenção Básica - Ações de Saúde")
    private String nome;

    @Schema(description = "Competência inicial de vigência (formato AAAAMM)", example = "202512")
    private String competenciaInicial;

    @Schema(description = "Competência final de vigência (formato AAAAMM). Null se ainda está vigente")
    private String competenciaFinal;
}
