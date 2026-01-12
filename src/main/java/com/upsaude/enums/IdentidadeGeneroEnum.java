package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

/**
 * Identidade de Gênero conforme FHIR BR (BRIdentidadeGenero).
 * Descrições em português para consumo pelo frontend.
 */
public enum IdentidadeGeneroEnum {
    HOMEM(1, "Homem", "male", "http://hl7.org/fhir/administrative-gender"),
    MULHER(2, "Mulher", "female", "http://hl7.org/fhir/administrative-gender"),
    HOMEM_TRANS(3, "Homem Transgênero", "1", "http://www.saude.gov.br/fhir/r4/CodeSystem/BRIdentidadeGenero"),
    MULHER_TRANS(4, "Mulher Transgênero", "2", "http://www.saude.gov.br/fhir/r4/CodeSystem/BRIdentidadeGenero"),
    TRAVESTI(5, "Travesti", "3", "http://www.saude.gov.br/fhir/r4/CodeSystem/BRIdentidadeGenero"),
    NAO_BINARIO(6, "Não-Binário", "other", "http://hl7.org/fhir/administrative-gender"),
    NAO_INFORMADO(9, "Não Informado", "UNK", "http://terminology.hl7.org/CodeSystem/v3-NullFlavor");

    private final Integer codigo;
    private final String descricao;
    private final String codigoFhir;
    private final String systemFhir;

    IdentidadeGeneroEnum(Integer codigo, String descricao, String codigoFhir, String systemFhir) {
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

    public static IdentidadeGeneroEnum fromCodigo(Integer codigo) {
        if (codigo == null)
            return null;
        return Arrays.stream(values()).filter(v -> v.codigo.equals(codigo)).findFirst().orElse(null);
    }

    public static IdentidadeGeneroEnum fromCodigoFhir(String codigoFhir) {
        if (codigoFhir == null)
            return null;
        return Arrays.stream(values()).filter(v -> v.codigoFhir.equals(codigoFhir)).findFirst().orElse(null);
    }

    public static IdentidadeGeneroEnum fromDescricao(String descricao) {
        if (descricao == null)
            return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values()).filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d)).findFirst()
                .orElse(null);
    }
}
