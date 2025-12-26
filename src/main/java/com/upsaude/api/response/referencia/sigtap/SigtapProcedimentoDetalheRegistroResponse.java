package com.upsaude.api.response.referencia.sigtap;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Instrumento de registro relacionado ao procedimento")
public class SigtapProcedimentoDetalheRegistroResponse {
    @Schema(description = "Código do registro", example = "02")
    private String codigoRegistro;
    
    @Schema(description = "Nome do registro", example = "Registro de Procedimentos Ambulatoriais")
    private String nomeRegistro;
    
    @Schema(description = "Competência inicial de vigência (formato AAAAMM)", example = "202512")
    private String competenciaInicial;
    
    @Schema(description = "Competência final de vigência (formato AAAAMM). Null se ainda está vigente")
    private String competenciaFinal;
}

