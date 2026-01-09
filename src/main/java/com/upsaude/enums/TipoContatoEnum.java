package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

public enum TipoContatoEnum {
    TELEFONE(1, "Telefone"),
    EMAIL(2, "E-mail"),
    WHATSAPP(3, "WhatsApp"),
    OUTRO(99, "Outro");

    private final Integer codigo;
    private final String descricao;

    TipoContatoEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoContatoEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static TipoContatoEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toUpperCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toUpperCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}
