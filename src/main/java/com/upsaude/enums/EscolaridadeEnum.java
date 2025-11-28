package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

/**
 * Enum para classificação de escolaridade conforme padrão brasileiro e SUS/e-SUS.
 * Baseado na classificação do IBGE.
 */
public enum EscolaridadeEnum {
    ANALFABETO(1, "Analfabeto"),
    FUNDAMENTAL_INCOMPLETO(2, "Fundamental incompleto"),
    FUNDAMENTAL_COMPLETO(3, "Fundamental completo"),
    MEDIO_INCOMPLETO(4, "Médio incompleto"),
    MEDIO_COMPLETO(5, "Médio completo"),
    SUPERIOR_INCOMPLETO(6, "Superior incompleto"),
    SUPERIOR_COMPLETO(7, "Superior completo"),
    POS_GRADUACAO(8, "Pós-graduação"),
    IGNORADO(9, "Ignorado"),
    NAO_INFORMADO(99, "Não informado");

    private final Integer codigo;
    private final String descricao;

    EscolaridadeEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static EscolaridadeEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static EscolaridadeEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}

