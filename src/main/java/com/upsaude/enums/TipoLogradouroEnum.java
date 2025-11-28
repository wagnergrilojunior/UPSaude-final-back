package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

public enum TipoLogradouroEnum {
    RUA(1, "Rua"),
    AVENIDA(2, "Avenida"),
    TRAVESSA(3, "Travessa"),
    ALAMEDA(4, "Alameda"),
    PRACA(5, "Praça"),
    ESTRADA(6, "Estrada"),
    RODOVIA(7, "Rodovia"),
    VIA(8, "Via"),
    VIELA(9, "Viela"),
    CONDOMINIO(10, "Condomínio"),
    LOTEAMENTO(11, "Loteamento");

    private final Integer codigo;
    private final String descricao;

    TipoLogradouroEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoLogradouroEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
            .filter(v -> v.codigo.equals(codigo))
            .findFirst()
            .orElse(null);
    }

    public static TipoLogradouroEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
            .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
            .findFirst()
            .orElse(null);
    }

    public static TipoLogradouroEnum fromLogradouro(String logradouro) {
        if (logradouro == null) return null;
        String l = logradouro.trim().toLowerCase(Locale.ROOT);
        if (l.startsWith("rua")) return RUA;
        if (l.startsWith("avenida")) return AVENIDA;
        if (l.startsWith("travessa")) return TRAVESSA;
        if (l.startsWith("alameda")) return ALAMEDA;
        if (l.startsWith("praça") || l.startsWith("praca")) return PRACA;
        if (l.startsWith("estrada")) return ESTRADA;
        if (l.startsWith("rodovia")) return RODOVIA;
        if (l.startsWith("via")) return VIA;
        if (l.startsWith("viela")) return VIELA;
        if (l.startsWith("condomínio") || l.startsWith("condominio")) return CONDOMINIO;
        if (l.startsWith("loteamento")) return LOTEAMENTO;
        return null;
    }
}
