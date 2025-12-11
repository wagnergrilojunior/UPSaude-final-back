package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

public enum TipoAtendimentoPreferencialEnum {
    NENHUM(1, "Nenhum"),
    IDOSO(2, "Idoso (≥60 anos)"),
    GESTANTE(3, "Gestante"),
    LACTANTE(4, "Lactante"),
    DEFICIENTE_FISICO(5, "Pessoa com deficiência física"),
    DEFICIENTE_VISUAL(6, "Pessoa com deficiência visual"),
    DEFICIENTE_AUDITIVO(7, "Pessoa com deficiência auditiva"),
    OBESIDADE(8, "Obesidade grave"),
    MULHER_AMAMENTANDO(9, "Mulher amamentando"),
    CRIANCA_COLO(10, "Criança de colo"),
    OUTRO(99, "Outro");

    private final Integer codigo;
    private final String descricao;

    TipoAtendimentoPreferencialEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoAtendimentoPreferencialEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static TipoAtendimentoPreferencialEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String descricaoFormatada = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(descricaoFormatada))
                .findFirst()
                .orElse(null);
    }
}
