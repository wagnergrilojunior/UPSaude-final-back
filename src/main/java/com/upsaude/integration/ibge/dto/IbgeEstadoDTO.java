package com.upsaude.integration.ibge.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para mapear resposta da API IBGE de Estados
 * Endpoint: GET /api/v1/localidades/estados
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class IbgeEstadoDTO {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("sigla")
    private String sigla;

    @JsonProperty("nome")
    private String nome;

    @JsonProperty("regiao")
    private IbgeRegiaoDTO regiao;
}

