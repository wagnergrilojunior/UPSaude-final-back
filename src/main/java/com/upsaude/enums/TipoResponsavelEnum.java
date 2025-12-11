package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

public enum TipoResponsavelEnum {
    PAI(1, "Pai"),
    MAE(2, "Mãe"),
    TUTOR(3, "Tutor"),
    CURADOR(4, "Curador"),
    AVO(5, "Avô/Avó"),
    TIO(6, "Tio/Tia"),
    IRMAO(7, "Irmão/Irmã"),
    CONJUGE(8, "Cônjuge"),
    OUTRO(99, "Outro");

    private final Integer codigo;
    private final String descricao;

    TipoResponsavelEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoResponsavelEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static TipoResponsavelEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}
