package com.upsaude.api.response.clinica.cirurgia;

import com.upsaude.api.response.referencia.sigtap.ProcedimentoSigtapSimplificadoResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CirurgiaProcedimentoResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private UUID tenantId;
    private UUID estabelecimentoId;
    private ProcedimentoSigtapSimplificadoResponse procedimento;
    private Integer quantidade;
    private BigDecimal valorUnitario;
    private BigDecimal valorTotal;
    private String observacoes;
}

