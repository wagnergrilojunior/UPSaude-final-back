package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

/**
 * Modalidade Financeira conforme FHIR BR (BRModalidadeFinanceira).
 * Descrições em português para consumo pelo frontend.
 */
public enum ModalidadeFinanceiraEnum {
    MAC(1, "Média e Alta Complexidade", "01", "http://www.saude.gov.br/fhir/r4/CodeSystem/BRModalidadeFinanceira"),
    ATENCAO_BASICA(2, "Atenção Básica", "02", "http://www.saude.gov.br/fhir/r4/CodeSystem/BRModalidadeFinanceira"),
    VIGILANCIA(3, "Vigilância em Saúde", "03", "http://www.saude.gov.br/fhir/r4/CodeSystem/BRModalidadeFinanceira"),
    ASSISTENCIA_FARMACEUTICA(4, "Assistência Farmacêutica", "04",
            "http://www.saude.gov.br/fhir/r4/CodeSystem/BRModalidadeFinanceira"),
    GESTAO_SUS(5, "Gestão do SUS", "05", "http://www.saude.gov.br/fhir/r4/CodeSystem/BRModalidadeFinanceira"),
    INVESTIMENTO(6, "Investimento", "06", "http://www.saude.gov.br/fhir/r4/CodeSystem/BRModalidadeFinanceira"),
    FAEC(7, "Fundo de Ações Estratégicas - FAEC", "07",
            "http://www.saude.gov.br/fhir/r4/CodeSystem/BRModalidadeFinanceira"),
    INCENTIVO(8, "Incentivo", "08", "http://www.saude.gov.br/fhir/r4/CodeSystem/BRModalidadeFinanceira");

    private final Integer codigo;
    private final String descricao;
    private final String codigoFhir;
    private final String systemFhir;

    ModalidadeFinanceiraEnum(Integer codigo, String descricao, String codigoFhir, String systemFhir) {
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

    public static ModalidadeFinanceiraEnum fromCodigo(Integer codigo) {
        if (codigo == null)
            return null;
        return Arrays.stream(values()).filter(v -> v.codigo.equals(codigo)).findFirst().orElse(null);
    }

    public static ModalidadeFinanceiraEnum fromCodigoFhir(String codigoFhir) {
        if (codigoFhir == null)
            return null;
        return Arrays.stream(values()).filter(v -> v.codigoFhir.equals(codigoFhir)).findFirst().orElse(null);
    }

    public static ModalidadeFinanceiraEnum fromDescricao(String descricao) {
        if (descricao == null)
            return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values()).filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d)).findFirst()
                .orElse(null);
    }
}
