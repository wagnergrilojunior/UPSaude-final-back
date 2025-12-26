package com.upsaude.api.response.referencia.sigtap;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Modalidade relacionada ao procedimento")
public class SigtapProcedimentoDetalheModalidadeResponse {
    @Schema(description = "Código da modalidade", example = "01")
    private String codigoModalidade;
    
    @Schema(description = "Nome da modalidade", example = "AMBULATORIAL")
    private String nomeModalidade;
    
    @Schema(description = "Competência inicial de vigência (formato AAAAMM)", example = "202501")
    private String competenciaInicial;
    
    @Schema(description = "Competência final de vigência (formato AAAAMM). Null se ainda está vigente")
    private String competenciaFinal;
}

