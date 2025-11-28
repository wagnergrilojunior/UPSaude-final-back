package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

public enum TipoEquipeEnum {
    EQUIPE_ATENCAO_BASICA(1, "Equipe de Atenção Básica"),
    EQUIPE_SAUDE_FAMILIA(2, "Equipe de Saúde da Família"),
    EQUIPE_SAUDE_BUCAL(3, "Equipe de Saúde Bucal"),
    EQUIPE_NASF(4, "Núcleo de Apoio à Saúde da Família"),
    EQUIPE_MEDICA(5, "Equipe Médica"),
    EQUIPE_ENFERMAGEM(6, "Equipe de Enfermagem"),
    EQUIPE_MULTIPROFISSIONAL(7, "Equipe Multiprofissional"),
    EQUIPE_URGENCIA(8, "Equipe de Urgência"),
    EQUIPE_AMBULATORIAL(9, "Equipe Ambulatorial"),
    OUTRO(99, "Outro");

    private final Integer codigo;
    private final String descricao;

    TipoEquipeEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() { return codigo; }
    public String getDescricao() { return descricao; }

    public static TipoEquipeEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values()).filter(v -> v.codigo.equals(codigo)).findFirst().orElse(null);
    }

    public static TipoEquipeEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values()).filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d)).findFirst().orElse(null);
    }
}

