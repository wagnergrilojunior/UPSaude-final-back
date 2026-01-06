package com.upsaude.api.response.referencia.sigtap;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Regra condicionada relacionada ao procedimento")
public class SigtapProcedimentoDetalheRegraCondicionadaResponse {
    @Schema(description = "Código da regra condicionada", example = "0001")
    private String codigoRegra;

    @Schema(description = "Nome da regra condicionada", example = "REGRAS ESPECIAIS")
    private String nomeRegra;
}
