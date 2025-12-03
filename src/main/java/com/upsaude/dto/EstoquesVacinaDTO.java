package com.upsaude.dto;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstoquesVacinaDTO {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private EstabelecimentosDTO estabelecimento;
    private VacinasDTO vacina;
    private Integer quantidadeDisponivel;
    private Integer quantidadeMinima;
    private Integer quantidadeMaxima;
    private String localArmazenamento;
    private String temperaturaArmazenamento;
    private LocalDate dataValidade;
}
