package com.upsaude.api.response.referencia.sigtap;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "TUSS relacionado ao procedimento")
public class SigtapProcedimentoDetalheTussResponse {
    @Schema(description = "Código do TUSS", example = "10101010")
    private String codigoTuss;
    
    @Schema(description = "Nome do TUSS", example = "CONSULTA MÉDICA")
    private String nomeTuss;
}
