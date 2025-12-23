package com.upsaude.api.response.referencia.sigtap;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Serviço e classificação relacionado ao procedimento")
public class SigtapProcedimentoDetalheServicoResponse {
    @Schema(description = "Código do serviço", example = "01")
    private String codigoServico;
    
    @Schema(description = "Nome do serviço", example = "SERVIÇO HOSPITALAR")
    private String nomeServico;
    
    @Schema(description = "Código da classificação", example = "001")
    private String codigoClassificacao;
    
    @Schema(description = "Nome da classificação", example = "CLASSIFICAÇÃO ESPECIAL")
    private String nomeClassificacao;
    
    @Schema(description = "Competência inicial de vigência (formato AAAAMM)", example = "202501")
    private String competenciaInicial;
    
    @Schema(description = "Competência final de vigência (formato AAAAMM). Null se ainda está vigente")
    private String competenciaFinal;
}
