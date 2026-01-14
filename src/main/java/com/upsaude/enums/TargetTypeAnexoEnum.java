package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

public enum TargetTypeAnexoEnum {
    PACIENTE("PACIENTE", "Paciente"),
    AGENDAMENTO("AGENDAMENTO", "Agendamento"),
    ATENDIMENTO("ATENDIMENTO", "Atendimento"),
    CONSULTA("CONSULTA", "Consulta Médica"),
    PRONTUARIO_EVENTO("PRONTUARIO_EVENTO", "Evento do Prontuário"),
    PROFISSIONAL_SAUDE("PROFISSIONAL_SAUDE", "Profissional de Saúde"),
    USUARIO_SISTEMA("USUARIO_SISTEMA", "Usuário do Sistema"),
    FINANCEIRO_FATURAMENTO("FINANCEIRO_FATURAMENTO", "Financeiro/Faturamento");

    private final String codigo;
    private final String descricao;

    TargetTypeAnexoEnum(String codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TargetTypeAnexoEnum fromCodigo(String codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equalsIgnoreCase(codigo))
                .findFirst()
                .orElse(null);
    }

    public static TargetTypeAnexoEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}
