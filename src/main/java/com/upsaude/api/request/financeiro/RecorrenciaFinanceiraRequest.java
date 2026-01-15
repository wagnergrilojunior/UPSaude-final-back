package com.upsaude.api.request.financeiro;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Dados de recorrência financeira")
public class RecorrenciaFinanceiraRequest {

    @NotBlank(message = "Tipo é obrigatório")
    @Size(max = 20, message = "Tipo deve ter no máximo 20 caracteres")
private String tipo;

    @NotBlank(message = "Periodicidade é obrigatória")
    @Size(max = 20, message = "Periodicidade deve ter no máximo 20 caracteres")
private String periodicidade;

    private Integer diaMes;
private Integer diaSemana;

    private OffsetDateTime proximaGeracaoEm;
    private Boolean ativo;
}

