package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

public enum EstadoCivilEnum {
    SOLTEIRO(1, "Solteiro"),
    CASADO(2, "Casado"),
    DIVORCIADO(3, "Divorciado"),
    VIUVO(4, "Viúvo"),
    SEPARADO(5, "Separado"),
    UNIAO_ESTAVEL(6, "União estável");

    private final Integer codigo;
    private final String descricao;

    EstadoCivilEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() { return codigo; }
    public String getDescricao() { return descricao; }

    public static EstadoCivilEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values()).filter(v -> v.codigo.equals(codigo)).findFirst().orElse(null);
    }

    public static EstadoCivilEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values()).filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d)).findFirst().orElse(null);
    }
}
