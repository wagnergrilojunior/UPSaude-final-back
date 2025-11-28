package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

/**
 * Enum para situação familiar conforme padrão SUS/e-SUS.
 * Utilizado para identificar a composição familiar do paciente.
 */
public enum SituacaoFamiliarEnum {
    SOZINHO(1, "Vive sozinho"),
    COM_FAMILIA(2, "Vive com família"),
    INSTITUCIONALIZADO(3, "Institucionalizado"),
    RUA(4, "Situação de rua"),
    OUTRO(5, "Outro"),
    IGNORADO(9, "Ignorado"),
    NAO_INFORMADO(99, "Não informado");

    private final Integer codigo;
    private final String descricao;

    SituacaoFamiliarEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static SituacaoFamiliarEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static SituacaoFamiliarEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}

