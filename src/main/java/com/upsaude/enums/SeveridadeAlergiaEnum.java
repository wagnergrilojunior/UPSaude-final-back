package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

/**
 * Severidade/Criticidade de Alergia conforme FHIR
 * (AllergyIntoleranceCriticality).
 * Descrições em português para consumo pelo frontend.
 */
public enum SeveridadeAlergiaEnum {
    BAIXA(1, "Baixa", "low", "http://hl7.org/fhir/allergy-intolerance-criticality"),
    ALTA(2, "Alta", "high", "http://hl7.org/fhir/allergy-intolerance-criticality"),
    INCAPAZ_AVALIAR(3, "Incapaz de Avaliar", "unable-to-assess", "http://hl7.org/fhir/allergy-intolerance-criticality");

    private final Integer codigo;
    private final String descricao;
    private final String codigoFhir;
    private final String systemFhir;

    SeveridadeAlergiaEnum(Integer codigo, String descricao, String codigoFhir, String systemFhir) {
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

    public static SeveridadeAlergiaEnum fromCodigo(Integer codigo) {
        if (codigo == null)
            return null;
        return Arrays.stream(values()).filter(v -> v.codigo.equals(codigo)).findFirst().orElse(null);
    }

    public static SeveridadeAlergiaEnum fromCodigoFhir(String codigoFhir) {
        if (codigoFhir == null)
            return null;
        return Arrays.stream(values()).filter(v -> v.codigoFhir.equals(codigoFhir)).findFirst().orElse(null);
    }

    public static SeveridadeAlergiaEnum fromDescricao(String descricao) {
        if (descricao == null)
            return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values()).filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d)).findFirst()
                .orElse(null);
    }
}
