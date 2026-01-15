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
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Dados de item de conciliação")
public class ConciliacaoItemRequest {

    @NotNull(message = "Conciliação bancária é obrigatória")
    private UUID conciliacao;

    private UUID extratoImportado;
    private UUID movimentacaoConta;

    @NotBlank(message = "Tipo de match é obrigatório")
    @Size(max = 20, message = "Tipo de match deve ter no máximo 20 caracteres")
private String tipoMatch;

    private BigDecimal diferenca;
}

