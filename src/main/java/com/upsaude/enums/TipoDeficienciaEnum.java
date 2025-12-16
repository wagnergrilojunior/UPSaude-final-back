package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

public enum TipoDeficienciaEnum {
    FISICA(1, "Física"),
    AUDITIVA(2, "Auditiva"),
    VISUAL(3, "Visual"),
    INTELECTUAL(4, "Intelectual"),
    MULTIPLA(5, "Múltipla"),
    PSICOSSOCIAL(6, "Psicossocial"),
    NEUROLOGICA(7, "Neurológica"),
    OUTRAS(99, "Outras");

    private final Integer codigo;
    private final String descricao;

    TipoDeficienciaEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoDeficienciaEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static TipoDeficienciaEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}
