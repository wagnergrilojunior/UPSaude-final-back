package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

/**
 * Enum para status do acompanhamento pré-natal.
 *
 * @author UPSaúde
 */
public enum StatusPreNatalEnum {
    EM_ACOMPANHAMENTO(1, "Em Acompanhamento"),
    CONCLUIDO_PARTO_NORMAL(2, "Concluído - Parto Normal"),
    CONCLUIDO_PARTO_CESAREA(3, "Concluído - Parto Cesárea"),
    ABORTO(4, "Aborto"),
    TRANSFERIDA(5, "Transferida"),
    ABANDONO(6, "Abandono"),
    OBITO_MATERNO(7, "Óbito Materno"),
    OBITO_FETAL(8, "Óbito Fetal");

    private final Integer codigo;
    private final String descricao;

    StatusPreNatalEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static StatusPreNatalEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static StatusPreNatalEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}

