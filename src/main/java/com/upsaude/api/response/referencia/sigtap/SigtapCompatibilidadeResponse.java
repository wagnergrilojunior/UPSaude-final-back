package com.upsaude.api.response.referencia.sigtap;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "Resposta com informações de compatibilidade entre procedimentos SIGTAP")
public class SigtapCompatibilidadeResponse {
    @Schema(description = "Identificador único da compatibilidade", example = "ff0e8400-e29b-41d4-a716-446655440010")
    private UUID id;

    @Schema(description = "Código da compatibilidade possível", example = "01")
    private String codigoCompatibilidadePossivel;

    @Schema(description = "Tipo de compatibilidade. Valores: PERMITIDA, INCOMPATÍVEL, EXCLUSIVA", example = "PERMITIDA")
    private String tipoCompatibilidade;

    @Schema(description = "Código do procedimento principal", example = "0301010010")
    private String codigoProcedimentoPrincipal;

    @Schema(description = "Nome do procedimento principal", example = "CONSULTA MÉDICA")
    private String nomeProcedimentoPrincipal;

    @Schema(description = "Código do procedimento secundário (compatível ou incompatível)", example = "0201010010")
    private String codigoProcedimentoSecundario;

    @Schema(description = "Nome do procedimento secundário", example = "EXAME COMPLEMENTAR")
    private String nomeProcedimentoSecundario;

    @Schema(description = "Competência inicial de vigência (formato AAAAMM)", example = "202501")
    private String competenciaInicial;

    @Schema(description = "Competência final de vigência (formato AAAAMM). Null se ainda está vigente")
    private String competenciaFinal;

    @Schema(description = "Quantidade permitida de execuções do procedimento secundário junto com o principal", example = "1")
    private Integer quantidadePermitida;
}
