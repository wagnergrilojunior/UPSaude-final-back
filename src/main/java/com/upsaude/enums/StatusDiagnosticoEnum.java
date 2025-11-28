package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

/**
 * Enum para status do diagnóstico de doença no paciente.
 *
 * @author UPSaúde
 */
public enum StatusDiagnosticoEnum {
    SUSPEITA(1, "Suspeita"),
    PROVAVEL(2, "Provável"),
    CONFIRMADO(3, "Confirmado"),
    EM_TRATAMENTO(4, "Em Tratamento"),
    CONTROLADO(5, "Controlado"),
    EM_REMISSAO(6, "Em Remissão"),
    CURADO(7, "Curado"),
    CRONICO(8, "Crônico"),
    ATIVO(9, "Ativo"),
    INATIVO(10, "Inativo"),
    EM_ACOMPANHAMENTO(11, "Em Acompanhamento"),
    OUTRO(99, "Outro");

    private final Integer codigo;
    private final String descricao;

    StatusDiagnosticoEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static StatusDiagnosticoEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static StatusDiagnosticoEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}

