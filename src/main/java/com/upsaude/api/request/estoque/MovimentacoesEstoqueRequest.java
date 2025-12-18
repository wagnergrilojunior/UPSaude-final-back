package com.upsaude.api.request.estoque;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de movimentacoes estoque")
public class MovimentacoesEstoqueRequest {
    private UUID estoqueVacina;
    private String tipoMovimento;
    private Integer quantidade;
    private String motivo;
    private UUID responsavel;
    private OffsetDateTime dataMovimento;
}
