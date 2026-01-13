package com.upsaude.api.request.sia.relatorios;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SiaPaRelatorioRequest {

    private String uf;

    // Quando o relatório for de um único mês
    private String competencia;

    // Quando o relatório for por período
    private String competenciaInicio;
    private String competenciaFim;

    private String codigoCnes;
    private String municipioCodigo;

    @Builder.Default
    private Integer limit = 10;
}

