package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

public enum TipoPlantaoEnum {
    PLANTAO_DIURNO(1, "Plantão Diurno"),
    PLANTAO_NOTURNO(2, "Plantão Noturno"),
    PLANTAO_12H_36H(3, "Plantão 12x36"),
    PLANTAO_24H_72H(4, "Plantão 24x72"),
    PLANTAO_EXTRA(5, "Plantão Extra"),
    PLANTAO_FERIADO(6, "Plantão Feriado"),
    PLANTAO_FINAL_SEMANA(7, "Plantão Final de Semana"),
    PLANTAO_DIURNO_NOTURNO(8, "Plantão Diurno e Noturno"),
    PLANTAO_SOBREAVISO(9, "Plantão Sobreaviso"),
    PLANTAO_ON_CALL(10, "Plantão On-Call"),
    OUTRO(99, "Outro");

    private final Integer codigo;
    private final String descricao;

    TipoPlantaoEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoPlantaoEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static TipoPlantaoEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}
