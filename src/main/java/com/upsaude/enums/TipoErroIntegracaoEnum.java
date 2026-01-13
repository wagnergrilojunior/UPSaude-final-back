package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

public enum TipoErroIntegracaoEnum {
    VALIDACAO(1, "VALIDACAO", "Erro de validação - dados incompletos ou inválidos"),
    COMUNICACAO(2, "COMUNICACAO", "Erro de comunicação - timeout, rede, etc"),
    AUTENTICACAO(3, "AUTENTICACAO", "Erro de autenticação/autorização");

    private final Integer codigo;
    private final String nome;
    private final String descricao;

    TipoErroIntegracaoEnum(Integer codigo, String nome, String descricao) {
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoErroIntegracaoEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static TipoErroIntegracaoEnum fromNome(String nome) {
        if (nome == null) return null;
        String n = nome.trim().toUpperCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.nome.equals(n))
                .findFirst()
                .orElse(null);
    }
}
