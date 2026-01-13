package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

public enum PrioridadeAtendimentoEnum {
    CRITICA(1, "Crítica", "stat"),
    ALTA(2, "Alta", "asap"),
    MEDIA(3, "Média", "urgent"),
    BAIXA(4, "Baixa", "routine"),
    ROTINA(5, "Rotina", "routine");

    private final Integer codigo;
    private final String descricao;
    private final String codigoRnds;

    PrioridadeAtendimentoEnum(Integer codigo, String descricao, String codigoRnds) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.codigoRnds = codigoRnds;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getCodigoRnds() {
        return codigoRnds;
    }

    public static PrioridadeAtendimentoEnum fromCodigo(Integer codigo) {
        if (codigo == null)
            return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static PrioridadeAtendimentoEnum fromDescricao(String descricao) {
        if (descricao == null)
            return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}
