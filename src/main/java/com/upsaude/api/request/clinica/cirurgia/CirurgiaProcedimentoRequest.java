package com.upsaude.api.request.clinica.cirurgia;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de procedimento SIGTAP associado à cirurgia")
public class CirurgiaProcedimentoRequest {
    @NotNull(message = "Procedimento SIGTAP é obrigatório")
    @Schema(description = "ID do procedimento SIGTAP")
    private UUID procedimento;

    @NotNull(message = "Quantidade é obrigatória")
    @Positive(message = "Quantidade deve ser maior que zero")
    @Schema(description = "Quantidade do procedimento", example = "1")
    @Builder.Default
    private Integer quantidade = 1;

    @Schema(description = "Valor unitário do procedimento (opcional, pode ser obtido do SIGTAP)")
    private BigDecimal valorUnitario;

    @Schema(description = "Valor total do procedimento (opcional, calculado se não informado)")
    private BigDecimal valorTotal;

    @Schema(description = "Observações específicas do procedimento")
    private String observacoes;
}

