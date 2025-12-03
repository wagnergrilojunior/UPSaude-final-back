package com.upsaude.api.request;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstoquesVacinaRequest {
    private UUID estabelecimento;
    private UUID vacina;
    private Integer quantidadeDisponivel;
    private Integer quantidadeMinima;
    private Integer quantidadeMaxima;
    private String localArmazenamento;
    private String temperaturaArmazenamento;
    private LocalDate dataValidade;
}
