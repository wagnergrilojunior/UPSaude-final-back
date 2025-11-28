package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

public enum TipoEstabelecimentoEnum {
    HOSPITAL(1, "Hospital"),
    CLINICA(2, "Clínica"),
    POSTO_SAUDE(3, "Posto de Saúde"),
    UBS(4, "Unidade Básica de Saúde"),
    UPA(5, "Unidade de Pronto Atendimento"),
    LABORATORIO(6, "Laboratório"),
    FARMACIA(7, "Farmácia"),
    OUTRO(8, "Outro");

    private final Integer codigo;
    private final String descricao;

    TipoEstabelecimentoEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() { return codigo; }
    public String getDescricao() { return descricao; }

    public static TipoEstabelecimentoEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values()).filter(v -> v.codigo.equals(codigo)).findFirst().orElse(null);
    }

    public static TipoEstabelecimentoEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values()).filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d)).findFirst().orElse(null);
    }
}

