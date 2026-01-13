package com.upsaude.api.response.clinica.atendimento;

import com.upsaude.api.response.referencia.sigtap.ProcedimentoSigtapSimplificadoResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtendimentoProcedimentoSimplificadoResponse {
    private UUID id;
    private ProcedimentoSigtapSimplificadoResponse sigtapProcedimento;
    private Integer quantidade;
    private BigDecimal valorTotal;
}

