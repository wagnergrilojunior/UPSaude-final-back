package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

public enum TipoLogradouroEnum {
    RUA("RUA", "Rua"),
    AVENIDA("AV", "Avenida"),
    TRAVESSA("TRAV", "Travessa"),
    ALAMEDA("AL", "Alameda"),
    PRACA("PRACA", "Praça"),
    ESTRADA("EST", "Estrada"),
    RODOVIA("ROD", "Rodovia"),
    VIA("VIA", "Via"),
    VIELA("VIELA", "Viela"),
    CONDOMINIO("COND", "Condomínio"),
    LOTEAMENTO("LOT", "Loteamento");

    private final String codigo;
    private final String descricao;

    TipoLogradouroEnum(String codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoLogradouroEnum fromCodigo(String codigo) {
        if (codigo == null) return null;
        String c = codigo.trim().toUpperCase(Locale.ROOT);
        return Arrays.stream(values())
            .filter(v -> v.codigo.equals(c))
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
