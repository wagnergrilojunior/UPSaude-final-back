package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

public enum StatusFuncionamentoEnum {
    EM_FUNCIONAMENTO(1, "Em Funcionamento"),
    PARALISADO_TEMPORARIAMENTE(2, "Paralisado Temporariamente"),
    PARALISADO_DEFINITIVAMENTE(3, "Paralisado Definitivamente"),
    EXTINTO(4, "Extinto"),
    EM_IMPLANTACAO(5, "Em Implantação");

    private final Integer codigo;
    private final String descricao;

    StatusFuncionamentoEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() { return codigo; }
    public String getDescricao() { return descricao; }

    public static StatusFuncionamentoEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values()).filter(v -> v.codigo.equals(codigo)).findFirst().orElse(null);
    }

    public static StatusFuncionamentoEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values()).filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d)).findFirst().orElse(null);
    }
}
