package com.upsaude.api.response.referencia.sigtap;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "Resposta com informações de um subgrupo SIGTAP (segundo nível da hierarquia)")
public class SigtapSubgrupoResponse {
    @Schema(description = "Identificador único do subgrupo", example = "cc0e8400-e29b-41d4-a716-446655440007")
    private UUID id;

    @Schema(description = "Código oficial do subgrupo (2 dígitos)", example = "01")
    private String codigoOficial;

    @Schema(description = "Nome completo do subgrupo", example = "Pequenas cirurgias e cirurgias de pele, tecido subcutâneo e mucosa")
    private String nome;

    @Schema(description = "Competência inicial de vigência (formato AAAAMM)", example = "202501")
    private String competenciaInicial;

    @Schema(description = "Competência final de vigência (formato AAAAMM). Null se ainda está vigente")
    private String competenciaFinal;

    @Schema(description = "Código do grupo pai (2 dígitos)", example = "04")
    private String grupoCodigo;

    @Schema(description = "Nome do grupo pai", example = "Procedimentos cirúrgicos")
    private String grupoNome;
}
