package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

public enum SexoEnum {
    MASCULINO(1, "Masculino"),
    FEMININO(2, "Feminino"),
    OUTRO(3, "Outro");

    private final Integer codigo;
    private final String descricao;

    SexoEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() { return codigo; }
    public String getDescricao() { return descricao; }

    public static SexoEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values()).filter(v -> v.codigo.equals(codigo)).findFirst().orElse(null);
    }

    public static SexoEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values()).filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d)).findFirst().orElse(null);
    }
}
