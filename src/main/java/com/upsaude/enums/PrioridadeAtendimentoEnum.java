package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

public enum PrioridadeAtendimentoEnum {
    CRITICA(1, "Crítica"),
    ALTA(2, "Alta"),
    MEDIA(3, "Média"),
    BAIXA(4, "Baixa"),
    ROTINA(5, "Rotina");

    private final Integer codigo;
    private final String descricao;

    PrioridadeAtendimentoEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static PrioridadeAtendimentoEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static PrioridadeAtendimentoEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}
