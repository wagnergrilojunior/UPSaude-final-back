package com.upsaude.api.request.farmacia;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.upsaude.enums.UnidadeMedidaEnum;
import com.upsaude.util.converter.UnidadeMedidaEnumDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Dados de item de receita")
public class ReceitaItemRequest {

    @NotNull(message = "ID do procedimento SIGTAP é obrigatório")
    @Schema(description = "ID do procedimento SIGTAP", required = true)
    private UUID sigtapProcedimentoId;

    @NotNull(message = "Posição do item é obrigatória")
    @Min(value = 1, message = "Posição deve ser maior que zero")
    @Schema(description = "Posição do item na receita", required = true, minimum = "1")
    private Integer posicao;

    @Schema(description = "Quantidade prescrita")
    private BigDecimal quantidadePrescrita;

    @JsonDeserialize(using = UnidadeMedidaEnumDeserializer.class)
    @Schema(description = "Unidade de medida")
    private UnidadeMedidaEnum unidadeMedida;

    @Size(max = 500, message = "Posologia deve ter no máximo 500 caracteres")
    @Schema(description = "Posologia (como tomar)", maxLength = 500)
    private String posologia;

    @Schema(description = "Duração do tratamento em dias")
    private Integer duracaoTratamento;

    @Schema(description = "Observações do item")
    private String observacoes;
}
