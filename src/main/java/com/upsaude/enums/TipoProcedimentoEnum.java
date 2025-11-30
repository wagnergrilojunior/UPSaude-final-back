package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

public enum TipoProcedimentoEnum {
    CURATIVO(1, "Curativo"),
    SUTURA(2, "Sutura"),
    COLETA_EXAME(3, "Coleta de Exame"),
    VACINACAO(4, "Vacinação"),
    INJECAO(5, "Injeção"),
    MEDICAO_PRESSAO(6, "Medição de Pressão"),
    MEDICAO_GLICEMIA(7, "Medição de Glicemia"),
    APLICACAO_MEDICAMENTO(8, "Aplicação de Medicamento"),
    DRENAGEM(9, "Drenagem"),
    ASPIRACAO(10, "Aspiração"),
    OXIGENOTERAPIA(11, "Oxigenoterapia"),
    FISIOTERAPIA(12, "Fisioterapia"),
    OUTRO(99, "Outro");

    private final Integer codigo;
    private final String descricao;

    TipoProcedimentoEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoProcedimentoEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static TipoProcedimentoEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}

