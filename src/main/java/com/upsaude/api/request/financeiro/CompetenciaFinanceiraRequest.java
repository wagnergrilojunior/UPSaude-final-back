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

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Dados de competência financeira")
public class CompetenciaFinanceiraRequest {

    @NotBlank(message = "Código é obrigatório")
    @Size(max = 20, message = "Código deve ter no máximo 20 caracteres")
    private String codigo;

    @NotBlank(message = "Tipo é obrigatório")
    @Size(max = 20, message = "Tipo deve ter no máximo 20 caracteres")
private String tipo;

    @NotNull(message = "Data início é obrigatória")
    private LocalDate dataInicio;

    @NotNull(message = "Data fim é obrigatória")
    private LocalDate dataFim;

    @Size(max = 500, message = "Descrição deve ter no máximo 500 caracteres")
    private String descricao;
}

