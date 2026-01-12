package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

/**
 * Tipo de Identificador conforme FHIR BR (BRTipoIdentificador).
 * Descrições em português para consumo pelo frontend.
 */
public enum TipoIdentificadorEnum {
    CPF(1, "CPF", "CPF", "http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoIdentificador"),
    CNPJ(2, "CNPJ", "CNPJ", "http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoIdentificador"),
    CNS(3, "Cartão Nacional de Saúde", "CNS", "http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoIdentificador"),
    RG(4, "Registro Geral", "RG", "http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoIdentificador"),
    PASSAPORTE(5, "Passaporte", "PASSAPORTE", "http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoIdentificador"),
    CTPS(6, "Carteira de Trabalho", "CTPS", "http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoIdentificador"),
    TITULO_ELEITOR(7, "Título de Eleitor", "TE", "http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoIdentificador"),
    CNH(8, "Carteira de Habilitação", "CNH", "http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoIdentificador"),
    CERTIDAO_NASCIMENTO(9, "Certidão de Nascimento", "CN",
            "http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoIdentificador"),
    CERTIDAO_CASAMENTO(10, "Certidão de Casamento", "CC",
            "http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoIdentificador"),
    CERTIDAO_OBITO(11, "Certidão de Óbito", "CO", "http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoIdentificador"),
    PIS_PASEP(12, "PIS/PASEP", "PIS", "http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoIdentificador"),
    NIT(13, "Número de Identificação do Trabalhador", "NIT",
            "http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoIdentificador"),
    CAD_UNICO(14, "Cadastro Único", "CADUNICO", "http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoIdentificador"),
    DNV(15, "Declaração de Nascido Vivo", "DNV", "http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoIdentificador"),
    AUTORIZACAO(16, "Número de Autorização", "AUTH", "http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoIdentificador"),
    OUTROS(99, "Outros", "OUT", "http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoIdentificador");

    private final Integer codigo;
    private final String descricao;
    private final String codigoFhir;
    private final String systemFhir;

    TipoIdentificadorEnum(Integer codigo, String descricao, String codigoFhir, String systemFhir) {
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

    public static TipoIdentificadorEnum fromCodigo(Integer codigo) {
        if (codigo == null)
            return null;
        return Arrays.stream(values()).filter(v -> v.codigo.equals(codigo)).findFirst().orElse(null);
    }

    public static TipoIdentificadorEnum fromCodigoFhir(String codigoFhir) {
        if (codigoFhir == null)
            return null;
        return Arrays.stream(values()).filter(v -> v.codigoFhir.equalsIgnoreCase(codigoFhir)).findFirst().orElse(null);
    }

    public static TipoIdentificadorEnum fromDescricao(String descricao) {
        if (descricao == null)
            return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values()).filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d)).findFirst()
                .orElse(null);
    }
}
