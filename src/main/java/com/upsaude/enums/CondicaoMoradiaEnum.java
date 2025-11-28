package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

/**
 * Enum para condições de moradia conforme padrão SUS/e-SUS.
 * Utilizado para identificar condições habitacionais do paciente.
 */
public enum CondicaoMoradiaEnum {
    PROPRIO_QUITADO(1, "Próprio quitado"),
    PROPRIO_FINANCIADO(2, "Próprio financiado"),
    ALUGADO(3, "Alugado"),
    CEDIDO(4, "Cedido"),
    INVADIDO(5, "Invadido"),
    OUTRO(6, "Outro"),
    IGNORADO(9, "Ignorado"),
    NAO_INFORMADO(99, "Não informado");

    private final Integer codigo;
    private final String descricao;

    CondicaoMoradiaEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static CondicaoMoradiaEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static CondicaoMoradiaEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}

