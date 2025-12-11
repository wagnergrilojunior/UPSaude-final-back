package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

@Deprecated
public enum ViaAdministracaoVacinaEnum {
    INTRAMUSCULAR(1, "Intramuscular"),
    SUBCUTANEA(2, "Subcutânea"),
    INTRADERMICA(3, "Intradérmica"),
    ORAL(4, "Oral"),
    NASAL(5, "Nasal"),
    INTRADERMICA_MULTIPUNCAO(6, "Intradérmica (Múltipla punção)"),
    SUBCUTANEA_PROFUNDA(7, "Subcutânea Profunda");

    private final Integer codigo;
    private final String descricao;

    ViaAdministracaoVacinaEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static ViaAdministracaoVacinaEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static ViaAdministracaoVacinaEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}
