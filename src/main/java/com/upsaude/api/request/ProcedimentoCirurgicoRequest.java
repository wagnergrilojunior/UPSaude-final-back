package com.upsaude.api.request;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcedimentoCirurgicoRequest {
    private UUID cirurgia;
    private String descricao;
    private String codigoProcedimento;
    private String nomeProcedimento;
    private BigDecimal valorUnitario;
    private BigDecimal valorTotal;
    private String lado;
    private String observacoes;
}
