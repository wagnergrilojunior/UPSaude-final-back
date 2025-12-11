package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

public enum FrequenciaMedicacaoEnum {
    UMA_VEZ_AO_DIA(1, "1x ao dia"),
    DUAS_VEZES_AO_DIA(2, "2x ao dia"),
    TRES_VEZES_AO_DIA(3, "3x ao dia"),
    QUATRO_VEZES_AO_DIA(4, "4x ao dia"),
    DE_6_EM_6_HORAS(5, "De 6 em 6 horas"),
    DE_8_EM_8_HORAS(6, "De 8 em 8 horas"),
    DE_12_EM_12_HORAS(7, "De 12 em 12 horas"),
    DE_4_EM_4_HORAS(8, "De 4 em 4 horas"),
    CONFORME_NECESSARIO(9, "Conforme necessário"),
    UMA_VEZ_AO_DIA_EM_JEJUM(10, "1x ao dia em jejum"),
    UMA_VEZ_AO_DIA_ANTES_DAS_REFEICOES(11, "1x ao dia antes das refeições"),
    UMA_VEZ_AO_DIA_APOS_AS_REFEICOES(12, "1x ao dia após as refeições"),
    DUAS_VEZES_AO_DIA_ANTES_DAS_REFEICOES(13, "2x ao dia antes das refeições"),
    DUAS_VEZES_AO_DIA_APOS_AS_REFEICOES(14, "2x ao dia após as refeições"),
    TRES_VEZES_AO_DIA_ANTES_DAS_REFEICOES(15, "3x ao dia antes das refeições"),
    TRES_VEZES_AO_DIA_APOS_AS_REFEICOES(16, "3x ao dia após as refeições"),
    UMA_VEZ_A_SEMANA(17, "1x à semana"),
    UMA_VEZ_AO_MES(18, "1x ao mês"),
    OUTRAS(99, "Outras");

    private final Integer codigo;
    private final String descricao;

    FrequenciaMedicacaoEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static FrequenciaMedicacaoEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static FrequenciaMedicacaoEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}
