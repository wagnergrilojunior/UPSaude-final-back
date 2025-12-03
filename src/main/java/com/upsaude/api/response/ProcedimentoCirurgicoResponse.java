package com.upsaude.api.response;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcedimentoCirurgicoResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private CirurgiaResponse cirurgia;
    private String descricao;
    private String codigoProcedimento;
    private String nomeProcedimento;
    private BigDecimal valorUnitario;
    private BigDecimal valorTotal;
    private String lado;
    private String observacoes;
}
