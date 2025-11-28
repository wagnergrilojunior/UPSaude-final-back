package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

/**
 * Enum para status de registro médico (CRM).
 *
 * @author UPSaúde
 */
public enum StatusRegistroMedicoEnum {
    ATIVO(1, "Ativo"),
    SUSPENSO(2, "Suspenso"),
    CANCELADO(3, "Cancelado"),
    PROVISORIO(4, "Provisório"),
    EM_ANALISE(5, "Em Análise"),
    OUTRO(99, "Outro");

    private final Integer codigo;
    private final String descricao;

    StatusRegistroMedicoEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static StatusRegistroMedicoEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static StatusRegistroMedicoEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}

