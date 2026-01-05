package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

/**
 * Enum para classificação de risco segundo protocolo Manchester.
 * Utilizado em atendimentos para padronização conforme e-SUS APS.
 */
public enum ClassificacaoRiscoEnum {
    VERMELHO(1, "Vermelho"),
    LARANJA(2, "Laranja"),
    AMARELO(3, "Amarelo"),
    VERDE(4, "Verde"),
    AZUL(5, "Azul"),
    OUTRO(99, "Outro");

    private final Integer codigo;
    private final String descricao;

    ClassificacaoRiscoEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static ClassificacaoRiscoEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static ClassificacaoRiscoEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}

