package com.upsaude.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcedimentoCirurgicoDTO {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private CirurgiaDTO cirurgia;
    private String descricao;
    private String codigoProcedimento;
    private String nomeProcedimento;
    private BigDecimal valorUnitario;
    private BigDecimal valorTotal;
    private String lado;
    private String observacoes;
}
