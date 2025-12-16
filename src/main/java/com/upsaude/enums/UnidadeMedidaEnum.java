package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

public enum UnidadeMedidaEnum {
    MILIGRAMA(1, "mg", "Miligrama"),
    GRAMA(2, "g", "Grama"),
    MICROGRAMA(3, "mcg", "Micrograma"),
    MILILITRO(4, "mL", "Mililitro"),
    LITRO(5, "L", "Litro"),
    UNIDADE(6, "UI", "Unidade Internacional"),
    COMPRIMIDO(7, "comp", "Comprimido"),
    CAPSULA(8, "cap", "Cápsula"),
    AMPOLA(9, "amp", "Ampola"),
    FRASCO(10, "frasco", "Frasco"),
    GOTA(11, "gota", "Gota"),
    APLICACAO(12, "aplic", "Aplicação"),
    DOSE(13, "dose", "Dose"),
    OUTROS(99, "outros", "Outros");

    private final Integer codigo;
    private final String sigla;
    private final String descricao;

    UnidadeMedidaEnum(Integer codigo, String sigla, String descricao) {
        this.codigo = codigo;
        this.sigla = sigla;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getSigla() {
        return sigla;
    }

    public String getDescricao() {
        return descricao;
    }

    public static UnidadeMedidaEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static UnidadeMedidaEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d) ||
                           v.sigla.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}
