package com.upsaude.api.response.referencia.sigtap;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Resposta com informações detalhadas completas de um procedimento SIGTAP, incluindo dados adicionais")
public class SigtapProcedimentoDetalhadoResponse {
    @Schema(description = "Informações principais do procedimento")
    private SigtapProcedimentoResponse procedimento;

    @Schema(description = "Detalhes adicionais do procedimento (quando disponíveis)")
    private SigtapProcedimentoDetalheResponse detalhe;
}
