package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

/**
 * Raça/Cor conforme FHIR BR (BRRacaCor) e IBGE.
 * Enriquecido com códigos FHIR para interoperabilidade.
 */
public enum RacaCorEnum {
    BRANCA(1, "Branca", "01", "http://www.saude.gov.br/fhir/r4/CodeSystem/BRRacaCor"),
    PRETA(2, "Preta", "02", "http://www.saude.gov.br/fhir/r4/CodeSystem/BRRacaCor"),
    PARDA(3, "Parda", "03", "http://www.saude.gov.br/fhir/r4/CodeSystem/BRRacaCor"),
    AMARELA(4, "Amarela", "04", "http://www.saude.gov.br/fhir/r4/CodeSystem/BRRacaCor"),
    INDIGENA(5, "Indígena", "05", "http://www.saude.gov.br/fhir/r4/CodeSystem/BRRacaCor"),
    IGNORADO(9, "Ignorado", "99", "http://www.saude.gov.br/fhir/r4/CodeSystem/BRRacaCor"),
    NAO_INFORMADO(99, "Não informado", "99", "http://www.saude.gov.br/fhir/r4/CodeSystem/BRRacaCor");

    private final Integer codigo;
    private final String descricao;
    private final String codigoFhir;
    private final String systemFhir;

    RacaCorEnum(Integer codigo, String descricao, String codigoFhir, String systemFhir) {
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

    public static RacaCorEnum fromCodigo(Integer codigo) {
        if (codigo == null)
            return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static RacaCorEnum fromCodigoFhir(String codigoFhir) {
        if (codigoFhir == null)
            return null;
        return Arrays.stream(values())
                .filter(v -> v.codigoFhir.equals(codigoFhir))
                .findFirst()
                .orElse(null);
    }

    public static RacaCorEnum fromDescricao(String descricao) {
        if (descricao == null)
            return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}
