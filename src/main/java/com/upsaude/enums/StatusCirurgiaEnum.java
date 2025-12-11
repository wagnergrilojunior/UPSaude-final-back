package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

public enum StatusCirurgiaEnum {
    AGENDADA(1, "Agendada"),
    CONFIRMADA(2, "Confirmada"),
    PREPARACAO(3, "Em Preparação"),
    EM_ANDAMENTO(4, "Em Andamento"),
    FINALIZADA(5, "Finalizada"),
    CANCELADA(6, "Cancelada"),
    ADIADA(7, "Adiada"),
    EM_RECUPERACAO(8, "Em Recuperação"),
    ALTA_RECUPERACAO(9, "Alta da Recuperação");

    private final Integer codigo;
    private final String descricao;

    StatusCirurgiaEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static StatusCirurgiaEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static StatusCirurgiaEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}
