package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

/**
 * Tipo/Categoria de Alergia conforme FHIR (AllergyIntoleranceCategory).
 * Descrições em português para consumo pelo frontend.
 */
public enum TipoAlergiaEnum {
    MEDICAMENTO(1, "Medicamento", "medication", "http://hl7.org/fhir/allergy-intolerance-category"),
    ALIMENTO(2, "Alimento", "food", "http://hl7.org/fhir/allergy-intolerance-category"),
    AMBIENTE(3, "Ambiente", "environment", "http://hl7.org/fhir/allergy-intolerance-category"),
    BIOLOGICO(4, "Biológico", "biologic", "http://hl7.org/fhir/allergy-intolerance-category");

    private final Integer codigo;
    private final String descricao;
    private final String codigoFhir;
    private final String systemFhir;

    TipoAlergiaEnum(Integer codigo, String descricao, String codigoFhir, String systemFhir) {
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

    public static TipoAlergiaEnum fromCodigo(Integer codigo) {
        if (codigo == null)
            return null;
        return Arrays.stream(values()).filter(v -> v.codigo.equals(codigo)).findFirst().orElse(null);
    }

    public static TipoAlergiaEnum fromCodigoFhir(String codigoFhir) {
        if (codigoFhir == null)
            return null;
        return Arrays.stream(values()).filter(v -> v.codigoFhir.equals(codigoFhir)).findFirst().orElse(null);
    }

    public static TipoAlergiaEnum fromDescricao(String descricao) {
        if (descricao == null)
            return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values()).filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d)).findFirst()
                .orElse(null);
    }
}
