package com.upsaude.dto.estabelecimento.estoque;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;
import com.upsaude.dto.saude_publica.vacina.EstoquesVacinaDTO;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovimentacoesEstoqueDTO {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private EstoquesVacinaDTO estoqueVacina;
    private String tipoMovimento;
    private Integer quantidade;
    private String motivo;
    private UUID responsavel;
    private OffsetDateTime dataMovimento;
}
