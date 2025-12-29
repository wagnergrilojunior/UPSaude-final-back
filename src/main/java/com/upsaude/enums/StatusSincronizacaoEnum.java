package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

public enum StatusSincronizacaoEnum {
    SUCESSO(1, "Sucesso"),
    ERRO(2, "Erro"),
    PENDENTE(3, "Pendente");

    private final Integer codigo;
    private final String descricao;

    StatusSincronizacaoEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static StatusSincronizacaoEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static StatusSincronizacaoEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toUpperCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toUpperCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}

