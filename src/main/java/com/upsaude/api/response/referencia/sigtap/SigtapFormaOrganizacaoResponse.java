package com.upsaude.api.response.referencia.sigtap;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "Resposta com informações de uma forma de organização SIGTAP (terceiro nível da hierarquia)")
public class SigtapFormaOrganizacaoResponse {
    @Schema(description = "Identificador único da forma de organização", example = "dd0e8400-e29b-41d4-a716-446655440008")
    private UUID id;
    
    @Schema(description = "Código oficial da forma de organização (2 dígitos)", example = "01")
    private String codigoOficial;
    
    @Schema(description = "Nome completo da forma de organização", example = "Pequenas cirurgias")
    private String nome;
    
    @Schema(description = "Competência inicial de vigência (formato AAAAMM)", example = "202501")
    private String competenciaInicial;
    
    @Schema(description = "Competência final de vigência (formato AAAAMM). Null se ainda está vigente")
    private String competenciaFinal;
    
    @Schema(description = "Código do subgrupo pai (2 dígitos)", example = "01")
    private String subgrupoCodigo;
    
    @Schema(description = "Nome do subgrupo pai", example = "Pequenas cirurgias e cirurgias de pele, tecido subcutâneo e mucosa")
    private String subgrupoNome;
    
    @Schema(description = "Código do grupo (via subgrupo) (2 dígitos)", example = "04")
    private String grupoCodigo;
    
    @Schema(description = "Nome do grupo (via subgrupo)", example = "Procedimentos cirúrgicos")
    private String grupoNome;
}
