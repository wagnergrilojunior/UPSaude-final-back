package com.upsaude.api.response;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovimentacoesEstoqueResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private EstoquesVacinaResponse estoqueVacina;
    private String tipoMovimento;
    private Integer quantidade;
    private String motivo;
    private UUID responsavel;
    private OffsetDateTime dataMovimento;
}
