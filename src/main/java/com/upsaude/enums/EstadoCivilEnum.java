package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

public enum EstadoCivilEnum {
    SOLTEIRO("SOL", "Solteiro"),
    CASADO("CAS", "Casado"),
    DIVORCIADO("DIV", "Divorciado"),
    VIUVO("VIU", "Viúvo"),
    SEPARADO("SEP", "Separado"),
    UNIAO_ESTAVEL("UNI", "União estável");

    private final String codigo;
    private final String descricao;

    EstadoCivilEnum(String codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public String getCodigo() { return codigo; }
    public String getDescricao() { return descricao; }

    public static EstadoCivilEnum fromCodigo(String codigo) {
        if (codigo == null) return null;
        String c = codigo.trim().toUpperCase(Locale.ROOT);
        return Arrays.stream(values()).filter(v -> v.codigo.equals(c)).findFirst().orElse(null);
    }

    public static EstadoCivilEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values()).filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d)).findFirst().orElse(null);
    }
}
