package com.upsaude.integration.ibge.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para mapear resposta da API IBGE de Municípios
 * Endpoints: 
 * - GET /api/v1/localidades/estados/{UF}/municipios
 * - GET /api/v1/localidades/municipios/{codigo_ibge}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class IbgeMunicipioDTO {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("nome")
    private String nome;

    @JsonProperty("microrregiao")
    private MicrorregiaoDTO microrregiao;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MicrorregiaoDTO {
        @JsonProperty("id")
        private Integer id;

        @JsonProperty("nome")
        private String nome;

        @JsonProperty("mesorregiao")
        private MesorregiaoDTO mesorregiao;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MesorregiaoDTO {
        @JsonProperty("id")
        private Integer id;

        @JsonProperty("nome")
        private String nome;

        @JsonProperty("UF")
        private UfDTO uf;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class UfDTO {
        @JsonProperty("id")
        private Integer id;

        @JsonProperty("sigla")
        private String sigla;

        @JsonProperty("nome")
        private String nome;

        @JsonProperty("regiao")
        private IbgeRegiaoDTO regiao;
    }
}

