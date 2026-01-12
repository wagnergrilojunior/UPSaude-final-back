package com.upsaude.dto.vacinacao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoteVacinaRequest {

    @NotNull(message = "Imunobiológico é obrigatório")
    private UUID imunobiologicoId;

    @NotNull(message = "Fabricante é obrigatório")
    private UUID fabricanteId;

    @NotBlank(message = "Número do lote é obrigatório")
    private String numeroLote;

    private LocalDate dataFabricacao;

    @NotNull(message = "Data de validade é obrigatória")
    private LocalDate dataValidade;

    @Positive(message = "Quantidade recebida deve ser positiva")
    private Integer quantidadeRecebida;

    @Positive(message = "Quantidade disponível deve ser positiva")
    private Integer quantidadeDisponivel;

    private BigDecimal precoUnitario;

    private String observacoes;

    private UUID estabelecimentoId;
}
