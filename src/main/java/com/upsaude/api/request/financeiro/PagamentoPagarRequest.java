package com.upsaude.api.request.financeiro;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Dados de pagamento de título a pagar")
public class PagamentoPagarRequest {

    @NotNull(message = "Título a pagar é obrigatório")
    private UUID tituloPagar;

    @NotNull(message = "Conta financeira é obrigatória")
    private UUID contaFinanceira;

    private UUID movimentacaoConta;
    private UUID lancamentoFinanceiro;

    @NotNull(message = "Valor pago é obrigatório")
    @Positive(message = "Valor pago deve ser maior que zero")
    private BigDecimal valorPago;

    @NotNull(message = "Data do pagamento é obrigatória")
    private LocalDate dataPagamento;

    @Size(max = 30, message = "Meio de pagamento deve ter no máximo 30 caracteres")
    private String meioPagamento;

    @NotBlank(message = "Status é obrigatório")
    @Size(max = 30, message = "Status deve ter no máximo 30 caracteres")
    private String status; // EFETIVADO | CANCELADO_POR_REVERSAO

    private String observacao;
}

