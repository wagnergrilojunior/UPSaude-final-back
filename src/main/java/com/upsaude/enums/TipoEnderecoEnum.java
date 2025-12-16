package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

public enum TipoEnderecoEnum {
    RESIDENCIAL(1, "Residencial"),
    COMERCIAL(2, "Comercial"),
    CORRESPONDENCIA(3, "Correspondência"),
    RURAL(4, "Rural"),
    OUTRO(99, "Outro");

    private final Integer codigo;
    private final String descricao;

    TipoEnderecoEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoEnderecoEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static TipoEnderecoEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}
