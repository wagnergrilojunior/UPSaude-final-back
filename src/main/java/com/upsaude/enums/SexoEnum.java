package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

/**
 * Sexo conforme FHIR BR (BRSexo) e HL7 AdministrativeGender.
 * Enriquecido com códigos FHIR para interoperabilidade.
 */
public enum SexoEnum {
    MASCULINO(1, "Masculino", "male", "http://hl7.org/fhir/administrative-gender"),
    FEMININO(2, "Feminino", "female", "http://hl7.org/fhir/administrative-gender"),
    OUTRO(3, "Outro", "other", "http://hl7.org/fhir/administrative-gender"),
    DESCONHECIDO(4, "Desconhecido", "unknown", "http://hl7.org/fhir/administrative-gender");

    private final Integer codigo;
    private final String descricao;
    private final String codigoFhir;
    private final String systemFhir;

    SexoEnum(Integer codigo, String descricao, String codigoFhir, String systemFhir) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.codigoFhir = codigoFhir;
        this.systemFhir = systemFhir;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getCodigoFhir() {
        return codigoFhir;
    }

    public String getSystemFhir() {
        return systemFhir;
    }

    public static SexoEnum fromCodigo(Integer codigo) {
        if (codigo == null)
            return null;
        return Arrays.stream(values()).filter(v -> v.codigo.equals(codigo)).findFirst().orElse(null);
    }

    public static SexoEnum fromCodigoFhir(String codigoFhir) {
        if (codigoFhir == null)
            return null;
        return Arrays.stream(values()).filter(v -> v.codigoFhir.equals(codigoFhir)).findFirst().orElse(null);
    }

    public static SexoEnum fromDescricao(String descricao) {
        if (descricao == null)
            return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values()).filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d)).findFirst()
                .orElse(null);
    }
}
