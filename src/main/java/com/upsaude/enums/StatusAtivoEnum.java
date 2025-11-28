package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

public enum StatusAtivoEnum {
    ATIVO(1, "Ativo"),
    SUSPENSO(2, "Suspenso"),
    INATIVO(3, "Inativo");

    private final Integer codigo;
    private final String descricao;

    StatusAtivoEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static StatusAtivoEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static StatusAtivoEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }

    public static StatusAtivoEnum fromBoolean(Boolean ativo) {
        return ativo != null && ativo ? ATIVO : INATIVO;
    }

    public boolean isAtivo() {
        return this == ATIVO;
    }

    public boolean isSuspenso() {
        return this == SUSPENSO;
    }

    public boolean isInativo() {
        return this == INATIVO;
    }

    public Boolean toBoolean() {
        return this == ATIVO;
    }
}