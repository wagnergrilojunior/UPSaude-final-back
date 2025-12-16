package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

public enum StatusConsultaEnum {
    AGENDADA(1, "Agendada"),
    CONFIRMADA(2, "Confirmada"),
    EM_ANDAMENTO(3, "Em Andamento"),
    CONCLUIDA(4, "Concluída"),
    CANCELADA(5, "Cancelada"),
    FALTA_PACIENTE(6, "Falta do Paciente"),
    REMARCADA(7, "Remarcada"),
    SUSPENSA(8, "Suspensa"),
    EM_ESPERA(9, "Em Espera"),
    ATENDIDA(10, "Atendida"),
    NAO_COMPARECEU(11, "Não Compareceu"),
    OUTRO(99, "Outro");

    private final Integer codigo;
    private final String descricao;

    StatusConsultaEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static StatusConsultaEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static StatusConsultaEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}
