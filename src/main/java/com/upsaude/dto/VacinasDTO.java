package com.upsaude.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VacinasDTO {
    private UUID id;
    private String nome;
    private String descricao;
    private String fabricante;
    private String lote;
    private LocalDate dataValidade;
    private BigDecimal doseMl;
    private Boolean active;
}

