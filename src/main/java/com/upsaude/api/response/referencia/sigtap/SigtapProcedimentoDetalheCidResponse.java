package com.upsaude.api.response.referencia.sigtap;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "CID relacionado ao procedimento")
public class SigtapProcedimentoDetalheCidResponse {
    @Schema(description = "Código CID-10 (subcategoria)", example = "A00.0")
    private String codigoCid;

    @Schema(description = "Descrição do CID", example = "Cólera devida a Vibrio cholerae 01, biótipo cholerae")
    private String nomeCid;

    @Schema(description = "Indica se é o CID principal do procedimento", example = "true")
    private Boolean principal;

    @Schema(description = "Competência inicial de vigência (formato AAAAMM)", example = "202501")
    private String competenciaInicial;

    @Schema(description = "Competência final de vigência (formato AAAAMM). Null se ainda está vigente")
    private String competenciaFinal;
}
