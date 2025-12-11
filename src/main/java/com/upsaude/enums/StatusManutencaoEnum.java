package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

public enum StatusManutencaoEnum {
    OPERACIONAL(1, "Operacional"),
    MANUTENCAO_PREVENTIVA(2, "Em Manutenção Preventiva"),
    MANUTENCAO_CORRETIVA(3, "Em Manutenção Corretiva"),
    AGUARDANDO_MANUTENCAO(4, "Aguardando Manutenção"),
    INATIVO(5, "Inativo"),
    DESATIVADO(6, "Desativado"),
    AGUARDANDO_PECAS(7, "Aguardando Peças"),
    CALIBRACAO_PENDENTE(8, "Calibração Pendente");

    private final Integer codigo;
    private final String descricao;

    StatusManutencaoEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static StatusManutencaoEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static StatusManutencaoEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}
