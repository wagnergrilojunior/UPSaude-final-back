package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

public enum TipoChavePixEnum {
    CPF(1, "CPF", "CPF"),
    CNPJ(2, "CNPJ", "CNPJ"),
    EMAIL(3, "EMAIL", "E-mail"),
    TELEFONE(4, "TELEFONE", "Telefone"),
    ALEATORIA(5, "ALEATORIA", "Chave Aleatória");

    private final Integer codigo;
    private final String nome;
    private final String descricao;

    TipoChavePixEnum(Integer codigo, String nome, String descricao) {
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

    public static TipoChavePixEnum fromCodigo(Integer codigo) {
        if (codigo == null)
            return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static TipoChavePixEnum fromNome(String nome) {
        if (nome == null)
            return null;
        String n = nome.trim().toUpperCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.nome.equals(n))
                .findFirst()
                .orElse(null);
    }
}
