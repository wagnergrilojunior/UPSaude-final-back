package com.upsaude.api.response.sistema.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Representa um valor individual de um enum com suporte a FHIR")
public class EnumItemResponse {

    @Schema(description = "Nome do valor do enum (ex: MASCULINO, ATIVO)", example = "MASCULINO")
    private String nome;

    @Schema(description = "Código numérico do enum (quando disponível)", example = "1")
    private Integer codigo;

    @Schema(description = "Descrição legível do valor do enum em português", example = "Masculino")
    private String descricao;

    @Schema(description = "Código FHIR oficial (quando aplicável)", example = "male")
    private String codigoFhir;

    @Schema(description = "URL do CodeSystem FHIR (quando aplicável)", example = "http://hl7.org/fhir/administrative-gender")
    private String systemFhir;
}
