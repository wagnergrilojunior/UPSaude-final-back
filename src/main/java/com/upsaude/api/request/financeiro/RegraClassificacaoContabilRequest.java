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

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Dados de regra de classificação contábil")
public class RegraClassificacaoContabilRequest {

    @NotNull(message = "Conta contábil destino é obrigatória")
    private UUID contaContabilDestino;

    @NotBlank(message = "Escopo é obrigatório")
    @Size(max = 50, message = "Escopo deve ter no máximo 50 caracteres")
private String escopo;

    @NotNull(message = "Prioridade é obrigatória")
    private Integer prioridade;

    private Boolean ativo;

    private String condicaoJsonb;
}

