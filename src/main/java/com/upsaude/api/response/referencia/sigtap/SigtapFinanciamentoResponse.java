package com.upsaude.api.response.referencia.sigtap;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Resposta com informações de financiamento SIGTAP")
public class SigtapFinanciamentoResponse {
    @Schema(description = "Código oficial do financiamento", example = "01")
    private String codigoOficial;

    @Schema(description = "Nome do financiamento", example = "Atenção Básica")
    private String nome;

    @Schema(description = "Competência inicial de vigência (formato AAAAMM)", example = "202512")
    private String competenciaInicial;

    @Schema(description = "Competência final de vigência (formato AAAAMM). Null se ainda está vigente")
    private String competenciaFinal;
}
