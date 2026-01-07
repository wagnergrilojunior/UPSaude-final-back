package com.upsaude.integration.ibge.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para mapear resposta da API IBGE de Projeção de População
 * Endpoint: GET /api/v1/projecoes/populacao
 * 
 * Nota: A estrutura exata da API pode variar. Este DTO será ajustado conforme necessário.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class IbgeProjecaoPopulacaoDTO {

    @JsonProperty("projecao")
    private Long projecao;

    @JsonProperty("ano")
    private Integer ano;

    @JsonProperty("codigoMunicipio")
    private String codigoMunicipio;

    @JsonProperty("populacao")
    private Long populacao;
}

