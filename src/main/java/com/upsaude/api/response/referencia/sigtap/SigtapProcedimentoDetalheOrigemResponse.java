package com.upsaude.api.response.referencia.sigtap;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Procedimento origem relacionado (SIGTAP ou SIA/SIH)")
public class SigtapProcedimentoDetalheOrigemResponse {
    @Schema(description = "Tipo de origem: SIGTAP ou SIA/SIH", example = "SIGTAP")
    private String tipo;
    
    @Schema(description = "Tipo de procedimento (H=Hospitalar, A=Ambulatorial) - apenas para SIA/SIH", example = "H")
    private String tipoProcedimento;
    
    @Schema(description = "Código do procedimento origem", example = "0301010010")
    private String codigoProcedimentoOrigem;
    
    @Schema(description = "Nome do procedimento origem", example = "CONSULTA MÉDICA")
    private String nomeProcedimentoOrigem;
    
    @Schema(description = "Competência inicial de vigência (formato AAAAMM)", example = "202501")
    private String competenciaInicial;
    
    @Schema(description = "Competência final de vigência (formato AAAAMM). Null se ainda está vigente")
    private String competenciaFinal;
}
