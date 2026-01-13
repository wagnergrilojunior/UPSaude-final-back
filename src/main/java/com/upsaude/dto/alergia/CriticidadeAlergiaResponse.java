package com.upsaude.dto.alergia;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Resposta de criticidade de alergia")
public class CriticidadeAlergiaResponse {

    @Schema(description = "ID interno da criticidade")
    private UUID id;

    @Schema(description = "Código FHIR da criticidade")
    private String codigoFhir;

    @Schema(description = "Nome da criticidade")
    private String nome;

    @Schema(description = "Descrição detalhada")
    private String descricao;

    @Schema(description = "Indica se o registro está ativo")
    private Boolean ativo;

    @Schema(description = "Data da última sincronização com FHIR")
    private OffsetDateTime dataSincronizacao;
}
