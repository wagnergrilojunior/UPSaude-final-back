package com.upsaude.api.response.referencia.sigtap;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "RENASES relacionado ao procedimento")
public class SigtapProcedimentoDetalheRenasesResponse {
    @Schema(description = "Código do RENASES", example = "01")
    private String codigoRenases;
    
    @Schema(description = "Nome do RENASES", example = "CENTRO DE CARDIOLOGIA")
    private String nomeRenases;
}
