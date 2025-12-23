package com.upsaude.api.response.referencia.sigtap;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "Resposta com informações de um TUSS (Terminologia Unificada da Saúde Suplementar) SIGTAP")
public class SigtapTussResponse {
    @Schema(description = "Identificador único do TUSS", example = "ff0e8400-e29b-41d4-a716-446655440009")
    private UUID id;
    
    @Schema(description = "Código oficial do TUSS", example = "10101010")
    private String codigoOficial;
    
    @Schema(description = "Nome do TUSS", example = "CONSULTA MÉDICA")
    private String nome;
}
