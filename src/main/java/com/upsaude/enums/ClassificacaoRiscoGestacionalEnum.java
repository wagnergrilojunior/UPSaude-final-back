package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

/**
 * Enum para classificação de risco gestacional no pré-natal.
 * Baseado nos critérios do Ministério da Saúde para estratificação de risco.
 *
 * @author UPSaúde
 */
public enum ClassificacaoRiscoGestacionalEnum {
    RISCO_HABITUAL(1, "Risco Habitual"),
    ALTO_RISCO(2, "Alto Risco"),
    RISCO_INTERMEDIARIO(3, "Risco Intermediário"),
    NAO_CLASSIFICADO(99, "Não Classificado");

    private final Integer codigo;
    private final String descricao;

    ClassificacaoRiscoGestacionalEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static ClassificacaoRiscoGestacionalEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static ClassificacaoRiscoGestacionalEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}

