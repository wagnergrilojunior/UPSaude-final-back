package com.upsaude.api.response.referencia.sigtap;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "Resposta com informações de um grupo SIGTAP (primeiro nível da hierarquia)")
public class SigtapGrupoResponse {
    @Schema(description = "Identificador único do grupo", example = "aa0e8400-e29b-41d4-a716-446655440005")
    private UUID id;

    @Schema(description = "Código oficial do grupo (2 dígitos). Exemplos: 03 (Procedimentos clínicos), 04 (Procedimentos cirúrgicos), 06 (Medicamentos)", example = "04")
    private String codigoOficial;

    @Schema(description = "Nome completo do grupo", example = "Procedimentos cirúrgicos")
    private String nome;

    @Schema(description = "Competência inicial de vigência (formato AAAAMM)", example = "202501")
    private String competenciaInicial;

    @Schema(description = "Competência final de vigência (formato AAAAMM). Null se ainda está vigente")
    private String competenciaFinal;
}
