package com.upsaude.enums;

import com.upsaude.entity.atendimento.Atendimento;

import java.util.Arrays;
import java.util.Locale;

public enum StatusAgendamentoEnum {
    AGENDADO(1, "Agendado"),
    CONFIRMADO(2, "Confirmado"),
    AGUARDANDO(3, "Aguardando"),
    EM_ATENDIMENTO(4, "Em Atendimento"),
    CONCLUIDO(5, "Concluído"),
    CANCELADO(6, "Cancelado"),
    FALTA(7, "Falta (Não Compareceu)"),
    REAGENDADO(8, "Reagendado"),
    SUSPENSO(9, "Suspenso"),
    ENCAIXE(10, "Encaixe");

    private final Integer codigo;
    private final String descricao;

    StatusAgendamentoEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static StatusAgendamentoEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static StatusAgendamentoEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}
