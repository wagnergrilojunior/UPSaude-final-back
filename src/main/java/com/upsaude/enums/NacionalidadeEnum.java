package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

/**
 * Enum para classificação de nacionalidade conforme padrão brasileiro e SUS/e-SUS.
 */
public enum NacionalidadeEnum {
    BRASILEIRA(1, "Brasileira"),
    BRASILEIRA_NATURALIZADA(2, "Brasileira naturalizada"),
    ESTRANGEIRA(3, "Estrangeira"),
    IGNORADO(9, "Ignorado"),
    NAO_INFORMADO(99, "Não informado");

    private final Integer codigo;
    private final String descricao;

    NacionalidadeEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static NacionalidadeEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static NacionalidadeEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}

