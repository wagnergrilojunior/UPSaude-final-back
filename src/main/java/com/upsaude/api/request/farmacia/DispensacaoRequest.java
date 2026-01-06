package com.upsaude.api.request.farmacia;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Dados de dispensação de medicamentos")
public class DispensacaoRequest {

    @NotNull(message = "ID do paciente é obrigatório")
    @Schema(description = "ID do paciente", required = true)
    private UUID pacienteId;

    @Schema(description = "ID da receita (opcional - permite dispensação direta)")
    private UUID receitaId;

    @Schema(description = "ID do profissional de saúde que realizou a dispensação (opcional)")
    private UUID profissionalSaudeId;

    @Size(max = 50, message = "Número da dispensação deve ter no máximo 50 caracteres")
    @Schema(description = "Número da dispensação", maxLength = 50)
    private String numeroDispensacao;

    @NotNull(message = "Data de dispensação é obrigatória")
    @Schema(description = "Data e hora da dispensação", required = true)
    private LocalDateTime dataDispensacao;

    @Size(max = 30, message = "Tipo de dispensação deve ter no máximo 30 caracteres")
    @Schema(description = "Tipo de dispensação", maxLength = 30)
    private String tipoDispensacao;

    @Schema(description = "Observações gerais")
    private String observacoes;

    @NotEmpty(message = "Dispensação deve ter pelo menos um item")
    @Valid
    @Schema(description = "Lista de itens dispensados", required = true)
    @Builder.Default
    private List<DispensacaoItemRequest> itens = new ArrayList<>();
}
