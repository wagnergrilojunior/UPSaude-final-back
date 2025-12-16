package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

public enum NaturezaJuridicaEnum {
    ADMINISTRACAO_PUBLICA_DIRETA(1, "Administração Pública Direta"),
    ADMINISTRACAO_PUBLICA_INDIRETA(2, "Administração Pública Indireta"),
    ENTIDADE_EMPRESARIAL_PUBLICA(3, "Entidade Empresarial Pública"),
    SOCIEDADE_ECONOMIA_MISTA(4, "Sociedade de Economia Mista"),
    ORGANIZACAO_SOCIAL(5, "Organização Social"),
    ENTIDADE_FILANTROPICA(6, "Entidade Filantrópica"),
    EMPRESA_PRIVADA(7, "Empresa Privada"),
    COOPERATIVA(8, "Cooperativa"),
    ASSOCIACAO(9, "Associação"),
    FUNDACAO(10, "Fundação"),
    OUTRO(99, "Outro");

    private final Integer codigo;
    private final String descricao;

    NaturezaJuridicaEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() { return codigo; }
    public String getDescricao() { return descricao; }

    public static NaturezaJuridicaEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values()).filter(v -> v.codigo.equals(codigo)).findFirst().orElse(null);
    }

    public static NaturezaJuridicaEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values()).filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d)).findFirst().orElse(null);
    }
}
