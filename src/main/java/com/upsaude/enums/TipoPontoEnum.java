package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

public enum TipoPontoEnum {
    ENTRADA(1, "Entrada"),
    SAIDA(2, "Saída"),
    ENTRADA_INTERVALO(3, "Entrada Intervalo"),
    SAIDA_INTERVALO(4, "Saída Intervalo"),
    ENTRADA_ALMOCO(5, "Entrada Almoço"),
    SAIDA_ALMOCO(6, "Saída Almoço"),
    ENTRADA_PLANTAO(7, "Entrada Plantão"),
    SAIDA_PLANTAO(8, "Saída Plantão"),
    ENTRADA_HORA_EXTRA(9, "Entrada Hora Extra"),
    SAIDA_HORA_EXTRA(10, "Saída Hora Extra");

    private final Integer codigo;
    private final String descricao;

    TipoPontoEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoPontoEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static TipoPontoEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}
