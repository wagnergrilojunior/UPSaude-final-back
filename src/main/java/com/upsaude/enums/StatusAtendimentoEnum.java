package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

public enum StatusAtendimentoEnum {
    AGENDADO(1, "Agendado"),
    EM_ESPERA(2, "Em Espera"),
    EM_ANDAMENTO(3, "Em Andamento"),
    CONCLUIDO(4, "Concluído"),
    CANCELADO(5, "Cancelado"),
    FALTA_PACIENTE(6, "Falta do Paciente"),
    REMARCADO(7, "Remarcado"),
    SUSPENSO(8, "Suspenso"),
    INTERROMPIDO(9, "Interrompido"),
    OUTRO(99, "Outro");

    private final Integer codigo;
    private final String descricao;

    StatusAtendimentoEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static StatusAtendimentoEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static StatusAtendimentoEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}
