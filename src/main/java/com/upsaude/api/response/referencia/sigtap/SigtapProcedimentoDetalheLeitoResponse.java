package com.upsaude.api.response.referencia.sigtap;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Tipo de leito relacionado ao procedimento")
public class SigtapProcedimentoDetalheLeitoResponse {
    @Schema(description = "Código do tipo de leito", example = "01")
    private String codigoLeito;

    @Schema(description = "Nome do tipo de leito", example = "LEITO DE INTERNAÇÃO")
    private String nomeLeito;

    @Schema(description = "Competência inicial de vigência (formato AAAAMM)", example = "202501")
    private String competenciaInicial;

    @Schema(description = "Competência final de vigência (formato AAAAMM). Null se ainda está vigente")
    private String competenciaFinal;
}
