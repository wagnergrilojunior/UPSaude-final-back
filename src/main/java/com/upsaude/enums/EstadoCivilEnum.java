package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

/**
 * Estado Civil conforme FHIR HL7 v3-MaritalStatus.
 * Descrições em português para consumo pelo frontend.
 */
public enum EstadoCivilEnum {
    SOLTEIRO(1, "Solteiro(a)", "S", "http://terminology.hl7.org/CodeSystem/v3-MaritalStatus"),
    CASADO(2, "Casado(a)", "M", "http://terminology.hl7.org/CodeSystem/v3-MaritalStatus"),
    DIVORCIADO(3, "Divorciado(a)", "D", "http://terminology.hl7.org/CodeSystem/v3-MaritalStatus"),
    VIUVO(4, "Viúvo(a)", "W", "http://terminology.hl7.org/CodeSystem/v3-MaritalStatus"),
    SEPARADO(5, "Separado(a) Judicialmente", "L", "http://terminology.hl7.org/CodeSystem/v3-MaritalStatus"),
    UNIAO_ESTAVEL(6, "União Estável", "T", "http://terminology.hl7.org/CodeSystem/v3-MaritalStatus"),
    DESCONHECIDO(9, "Desconhecido", "UNK", "http://terminology.hl7.org/CodeSystem/v3-NullFlavor");

    private final Integer codigo;
    private final String descricao;
    private final String codigoFhir;
    private final String systemFhir;

    EstadoCivilEnum(Integer codigo, String descricao, String codigoFhir, String systemFhir) {
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

    public static EstadoCivilEnum fromCodigo(Integer codigo) {
        if (codigo == null)
            return null;
        return Arrays.stream(values()).filter(v -> v.codigo.equals(codigo)).findFirst().orElse(null);
    }

    public static EstadoCivilEnum fromCodigoFhir(String codigoFhir) {
        if (codigoFhir == null)
            return null;
        return Arrays.stream(values()).filter(v -> v.codigoFhir.equals(codigoFhir)).findFirst().orElse(null);
    }

    public static EstadoCivilEnum fromDescricao(String descricao) {
        if (descricao == null)
            return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values()).filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d)).findFirst()
                .orElse(null);
    }
}
