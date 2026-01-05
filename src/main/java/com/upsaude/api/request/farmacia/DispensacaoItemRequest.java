package com.upsaude.api.request.farmacia;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.upsaude.enums.UnidadeMedidaEnum;
import com.upsaude.util.converter.UnidadeMedidaEnumDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Dados de item de dispensação")
public class DispensacaoItemRequest {

    @Schema(description = "ID do item da receita (opcional - se veio de receita)")
    private UUID receitaItemId;

    @NotNull(message = "ID do procedimento SIGTAP é obrigatório")
    @Schema(description = "ID do procedimento SIGTAP", required = true)
    private UUID sigtapProcedimentoId;

    @NotNull(message = "Quantidade dispensada é obrigatória")
    @DecimalMin(value = "0.001", message = "Quantidade dispensada deve ser maior que zero")
    @Schema(description = "Quantidade dispensada", required = true, minimum = "0.001")
    private BigDecimal quantidadeDispensada;

    @JsonDeserialize(using = UnidadeMedidaEnumDeserializer.class)
    @Schema(description = "Unidade de medida")
    private UnidadeMedidaEnum unidadeMedida;

    @Size(max = 50, message = "Lote deve ter no máximo 50 caracteres")
    @Schema(description = "Lote do medicamento", maxLength = 50)
    private String lote;

    @Schema(description = "Validade do lote")
    private LocalDate validadeLote;

    @Schema(description = "Observações do item")
    private String observacoes;
}

