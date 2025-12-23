package com.upsaude.api.response.referencia.sigtap;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Componente de rede relacionado ao procedimento")
public class SigtapProcedimentoDetalheRedeResponse {
    @Schema(description = "Código do componente de rede", example = "0101010101")
    private String codigoRede;
    
    @Schema(description = "Nome do componente de rede", example = "COMPONENTE REDE ESPECIAL")
    private String nomeRede;
}
