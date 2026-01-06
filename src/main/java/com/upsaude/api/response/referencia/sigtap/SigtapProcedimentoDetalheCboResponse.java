package com.upsaude.api.response.referencia.sigtap;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "CBO (ocupação) relacionada ao procedimento")
public class SigtapProcedimentoDetalheCboResponse {
    @Schema(description = "Código CBO", example = "225110")
    private String codigoCbo;

    @Schema(description = "Nome da ocupação", example = "MÉDICO CLINICO GERAL")
    private String nomeCbo;

    @Schema(description = "Competência inicial de vigência (formato AAAAMM)", example = "202501")
    private String competenciaInicial;

    @Schema(description = "Competência final de vigência (formato AAAAMM). Null se ainda está vigente")
    private String competenciaFinal;
}
