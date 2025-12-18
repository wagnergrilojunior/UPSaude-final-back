package com.upsaude.enums;

import com.upsaude.entity.agendamento.Agendamento;

import com.upsaude.entity.atendimento.Atendimento;

import java.util.Arrays;
import java.util.Locale;

public enum TipoNotificacaoEnum {
    AGENDAMENTO_CRIADO(1, "Agendamento Criado"),
    AGENDAMENTO_CONFIRMADO(2, "Agendamento Confirmado"),
    AGENDAMENTO_CANCELADO(3, "Agendamento Cancelado"),
    AGENDAMENTO_REAGENDADO(4, "Agendamento Reagendado"),
    LEMBRETE_24H(5, "Lembrete 24 Horas Antes"),
    LEMBRETE_1H(6, "Lembrete 1 Hora Antes"),
    RESULTADO_EXAME(7, "Resultado de Exame"),
    RESULTADO_DISPONIVEL(8, "Resultado Disponível"),
    ATENDIMENTO_CONCLUIDO(9, "Atendimento Concluído"),
    PRESCRICAO_DISPONIVEL(10, "Prescrição Disponível"),
    RECEITA_DISPONIVEL(11, "Receita Disponível"),
    FALTA_AVISO(12, "Aviso de Falta"),
    FILA_ESPERA(13, "Vaga Disponível na Fila de Espera"),
    ATENDIMENTO_INICIADO(14, "Atendimento Iniciado"),
    OUTRO(99, "Outro");

    private final Integer codigo;
    private final String descricao;

    TipoNotificacaoEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoNotificacaoEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static TipoNotificacaoEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}
