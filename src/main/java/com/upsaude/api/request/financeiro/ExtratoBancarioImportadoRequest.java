package com.upsaude.api.request.financeiro;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Schema(description = "Dados de extrato bancário importado")
public class ExtratoBancarioImportadoRequest {

    @NotNull(message = "Conta financeira é obrigatória")
    private UUID contaFinanceira;

    @NotBlank(message = "Hash da linha é obrigatório")
    @Size(max = 64, message = "Hash da linha deve ter no máximo 64 caracteres")
    private String hashLinha;

    @Size(max = 500, message = "Descrição deve ter no máximo 500 caracteres")
    private String descricao;

    @NotNull(message = "Valor é obrigatório")
    private BigDecimal valor;

    @NotNull(message = "Data é obrigatória")
    private LocalDate data;

    @Size(max = 100, message = "Documento deve ter no máximo 100 caracteres")
    private String documento;

    private BigDecimal saldoApos;

    @NotBlank(message = "Status de conciliação é obrigatório")
    @Size(max = 30, message = "Status de conciliação deve ter no máximo 30 caracteres")
    private String statusConciliacao; // NAO_CONCILIADO | CONCILIADO | IGNORADO
}

