package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

/**
 * Tipo de Logradouro conforme FHIR BR (BRTipoLogradouro).
 * Descrições em português para consumo pelo frontend.
 */
public enum TipoLogradouroEnum {
    RUA(1, "Rua", "R", "http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoLogradouro"),
    AVENIDA(2, "Avenida", "AV", "http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoLogradouro"),
    TRAVESSA(3, "Travessa", "TV", "http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoLogradouro"),
    ALAMEDA(4, "Alameda", "AL", "http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoLogradouro"),
    PRACA(5, "Praça", "PC", "http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoLogradouro"),
    ESTRADA(6, "Estrada", "EST", "http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoLogradouro"),
    RODOVIA(7, "Rodovia", "ROD", "http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoLogradouro"),
    VIA(8, "Via", "VIA", "http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoLogradouro"),
    VIELA(9, "Viela", "VL", "http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoLogradouro"),
    CONDOMINIO(10, "Condomínio", "COND", "http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoLogradouro"),
    LOTEAMENTO(11, "Loteamento", "LOT", "http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoLogradouro"),
    BECO(12, "Beco", "BC", "http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoLogradouro"),
    LARGO(13, "Largo", "LG", "http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoLogradouro"),
    JARDIM(14, "Jardim", "JD", "http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoLogradouro"),
    LADEIRA(15, "Ladeira", "LD", "http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoLogradouro"),
    QUADRA(16, "Quadra", "QD", "http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoLogradouro"),
    SETOR(17, "Setor", "ST", "http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoLogradouro"),
    CONJUNTO(18, "Conjunto", "CJ", "http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoLogradouro"),
    PASSAGEM(19, "Passagem", "PSG", "http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoLogradouro"),
    OUTROS(99, "Outros", "OUT", "http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoLogradouro");

    private final Integer codigo;
    private final String descricao;
    private final String codigoFhir;
    private final String systemFhir;

    TipoLogradouroEnum(Integer codigo, String descricao, String codigoFhir, String systemFhir) {
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

    public static TipoLogradouroEnum fromCodigo(Integer codigo) {
        if (codigo == null)
            return null;
        return Arrays.stream(values()).filter(v -> v.codigo.equals(codigo)).findFirst().orElse(null);
    }

    public static TipoLogradouroEnum fromCodigoFhir(String codigoFhir) {
        if (codigoFhir == null)
            return null;
        return Arrays.stream(values()).filter(v -> v.codigoFhir.equals(codigoFhir)).findFirst().orElse(null);
    }

    public static TipoLogradouroEnum fromDescricao(String descricao) {
        if (descricao == null)
            return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values()).filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d)).findFirst()
                .orElse(null);
    }

    public static TipoLogradouroEnum fromLogradouro(String logradouro) {
        if (logradouro == null)
            return null;
        String l = logradouro.trim().toLowerCase(Locale.ROOT);
        if (l.startsWith("rua"))
            return RUA;
        if (l.startsWith("avenida") || l.startsWith("av ") || l.startsWith("av."))
            return AVENIDA;
        if (l.startsWith("travessa"))
            return TRAVESSA;
        if (l.startsWith("alameda"))
            return ALAMEDA;
        if (l.startsWith("praça") || l.startsWith("praca"))
            return PRACA;
        if (l.startsWith("estrada"))
            return ESTRADA;
        if (l.startsWith("rodovia"))
            return RODOVIA;
        if (l.startsWith("via"))
            return VIA;
        if (l.startsWith("viela"))
            return VIELA;
        if (l.startsWith("condomínio") || l.startsWith("condominio"))
            return CONDOMINIO;
        if (l.startsWith("loteamento"))
            return LOTEAMENTO;
        if (l.startsWith("beco"))
            return BECO;
        if (l.startsWith("largo"))
            return LARGO;
        if (l.startsWith("jardim") || l.startsWith("jd ") || l.startsWith("jd."))
            return JARDIM;
        if (l.startsWith("ladeira"))
            return LADEIRA;
        if (l.startsWith("quadra"))
            return QUADRA;
        if (l.startsWith("setor"))
            return SETOR;
        if (l.startsWith("conjunto"))
            return CONJUNTO;
        if (l.startsWith("passagem"))
            return PASSAGEM;
        return null;
    }
}
