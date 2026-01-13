package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

/**
 * Tipo de Convênio/Modalidade Financeira conforme FHIR BR (TipoConvenio).
 * Descrições em português para consumo pelo frontend.
 */
public enum TipoConvenioEnum {
    SUS(1, "Sistema Único de Saúde", "SUS", "http://www.saude.gov.br/fhir/r4/CodeSystem/TipoConvenio"),
    PARTICULAR(2, "Particular", "PARTICULAR", "http://www.saude.gov.br/fhir/r4/CodeSystem/TipoConvenio"),
    PLANO_SAUDE(3, "Plano de Saúde", "PLANO", "http://www.saude.gov.br/fhir/r4/CodeSystem/TipoConvenio"),
    CONVENIO(4, "Convênio", "CONVENIO", "http://www.saude.gov.br/fhir/r4/CodeSystem/TipoConvenio"),
    CORTESIA(5, "Cortesia", "CORTESIA", "http://www.saude.gov.br/fhir/r4/CodeSystem/TipoConvenio"),
    GRATUIDADE(6, "Gratuidade", "GRATUIDADE", "http://www.saude.gov.br/fhir/r4/CodeSystem/TipoConvenio");

    private final Integer codigo;
    private final String descricao;
    private final String codigoFhir;
    private final String systemFhir;

    TipoConvenioEnum(Integer codigo, String descricao, String codigoFhir, String systemFhir) {
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

    public static TipoConvenioEnum fromCodigo(Integer codigo) {
        if (codigo == null)
            return null;
        return Arrays.stream(values()).filter(v -> v.codigo.equals(codigo)).findFirst().orElse(null);
    }

    public static TipoConvenioEnum fromCodigoFhir(String codigoFhir) {
        if (codigoFhir == null)
            return null;
        return Arrays.stream(values()).filter(v -> v.codigoFhir.equalsIgnoreCase(codigoFhir)).findFirst().orElse(null);
    }

    public static TipoConvenioEnum fromDescricao(String descricao) {
        if (descricao == null)
            return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values()).filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d)).findFirst()
                .orElse(null);
    }
}
