package com.upsaude.api.request.farmacia;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.upsaude.enums.TipoControleMedicamentoEnum;
import com.upsaude.util.converter.TipoControleMedicamentoEnumDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Dados de receita médica")
public class ReceitaRequest {

    @NotNull(message = "ID do paciente é obrigatório")
    @Schema(description = "ID do paciente", required = true)
    private UUID pacienteId;

    @Schema(description = "ID da consulta (opcional)")
    private UUID consultaId;

    @Schema(description = "ID do médico prescritor (opcional)")
    private UUID medicoId;

    @Size(max = 50, message = "Número da receita deve ter no máximo 50 caracteres")
    @Schema(description = "Número da receita", maxLength = 50)
    private String numeroReceita;

    @NotNull(message = "Data de prescrição é obrigatória")
    @Schema(description = "Data de prescrição", required = true)
    private LocalDate dataPrescricao;

    @Schema(description = "Data de validade da receita")
    private LocalDate dataValidade;

    @JsonDeserialize(using = TipoControleMedicamentoEnumDeserializer.class)
    @Schema(description = "Tipo de controle da receita")
    private TipoControleMedicamentoEnum tipoReceita;

    @Schema(description = "Observações gerais")
    private String observacoes;

    @NotEmpty(message = "Receita deve ter pelo menos um item")
    @Valid
    @Schema(description = "Lista de itens da receita", required = true)
    @Builder.Default
    private List<ReceitaItemRequest> itens = new ArrayList<>();
}

