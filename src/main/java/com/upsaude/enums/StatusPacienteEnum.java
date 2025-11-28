package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

/**
 * Enum para status do paciente no sistema de saúde.
 * Utilizado para controle do ciclo de vida do paciente (ativo, óbito, transferido, etc.).
 */
public enum StatusPacienteEnum {
    ATIVO(1, "Ativo"),
    INATIVO(2, "Inativo"),
    OBITO(3, "Óbito"),
    TRANSFERIDO(4, "Transferido"),
    DESATIVADO(5, "Desativado"),
    IGNORADO(6, "Ignorado");

    private final Integer codigo;
    private final String descricao;

    StatusPacienteEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static StatusPacienteEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static StatusPacienteEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}

