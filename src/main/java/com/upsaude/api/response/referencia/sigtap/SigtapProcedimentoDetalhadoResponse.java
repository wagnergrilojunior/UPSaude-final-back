package com.upsaude.api.response.referencia.sigtap;

import lombok.Data;

@Data
public class SigtapProcedimentoDetalhadoResponse {
    private SigtapProcedimentoResponse procedimento;
    private SigtapProcedimentoDetalheResponse detalhe;
}
