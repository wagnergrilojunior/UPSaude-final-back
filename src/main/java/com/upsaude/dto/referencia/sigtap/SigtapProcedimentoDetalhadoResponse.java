package com.upsaude.dto.referencia.sigtap;

import lombok.Data;

@Data
public class SigtapProcedimentoDetalhadoResponse {
    private SigtapProcedimentoResponse procedimento;
    private SigtapProcedimentoDetalheResponse detalhe;
}

