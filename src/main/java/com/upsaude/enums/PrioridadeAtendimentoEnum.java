package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

public enum PrioridadeAtendimentoEnum {
    NORMAL(1, "Normal"),
    PRIORITARIO(2, "Prioritário"),
    URGENTE(3, "Urgente"),
    EMERGENCIA(4, "Emergência"),
    CONVENIO(5, "Convênio"),
    RETORNO(6, "Retorno"),
    ENCAIXE(7, "Encaixe"),
    OUTRO(99, "Outro");

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
