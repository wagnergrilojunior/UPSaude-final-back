package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

public enum StatusAnexoEnum {
    PENDENTE(1, "Pendente"),
    ATIVO(2, "Ativo"),
    INATIVO(3, "Inativo"),
    EXCLUIDO(4, "Excluído");

    private final Integer codigo;
    private final String descricao;

    StatusAnexoEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static StatusAnexoEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static StatusAnexoEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }

    public boolean isAtivo() {
        return this == ATIVO;
    }

    public boolean isExcluido() {
        return this == EXCLUIDO;
    }
}
