package com.upsaude.api.response.referencia.sigtap;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "Resposta com informações de um RENASES (Rede Nacional de Atenção Especializada em Saúde) SIGTAP")
public class SigtapRenasesResponse {
    @Schema(description = "Identificador único do RENASES", example = "990e8400-e29b-41d4-a716-446655440004")
    private UUID id;
    
    @Schema(description = "Código oficial do RENASES", example = "01")
    private String codigoOficial;
    
    @Schema(description = "Nome do estabelecimento ou serviço RENASES", example = "CENTRO DE CARDIOLOGIA")
    private String nome;
}
