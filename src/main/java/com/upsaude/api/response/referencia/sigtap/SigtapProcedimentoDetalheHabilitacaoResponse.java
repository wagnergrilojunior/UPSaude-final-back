package com.upsaude.api.response.referencia.sigtap;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Habilitação relacionada ao procedimento")
public class SigtapProcedimentoDetalheHabilitacaoResponse {
    @Schema(description = "Código da habilitação", example = "01")
    private String codigoHabilitacao;
    
    @Schema(description = "Nome da habilitação", example = "HABILITAÇÃO HOSPITALAR")
    private String nomeHabilitacao;
    
    @Schema(description = "Código do grupo de habilitação", example = "01")
    private String codigoGrupoHabilitacao;
    
    @Schema(description = "Nome do grupo de habilitação", example = "GRUPO HABILITAÇÃO ESPECIAL")
    private String nomeGrupoHabilitacao;
    
    @Schema(description = "Competência inicial de vigência (formato AAAAMM)", example = "202501")
    private String competenciaInicial;
    
    @Schema(description = "Competência final de vigência (formato AAAAMM). Null se ainda está vigente")
    private String competenciaFinal;
}
