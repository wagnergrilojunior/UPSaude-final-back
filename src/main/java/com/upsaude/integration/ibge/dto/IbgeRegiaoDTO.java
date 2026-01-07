package com.upsaude.integration.ibge.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para mapear resposta da API IBGE de Regiões
 * Endpoint: GET /api/v1/localidades/regioes
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class IbgeRegiaoDTO {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("sigla")
    private String sigla;

    @JsonProperty("nome")
    private String nome;
}

