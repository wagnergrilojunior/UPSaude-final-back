package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

public enum StatusLeitoEnum {
    DISPONIVEL(1, "DISPONIVEL", "Disponível"),
    OCUPADO(2, "OCUPADO", "Ocupado"),
    MANUTENCAO(3, "MANUTENCAO", "Em manutenção"),
    INATIVO(4, "INATIVO", "Inativo");

    private final Integer codigo;
    private final String nome;
    private final String descricao;

    StatusLeitoEnum(Integer codigo, String nome, String descricao) {
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

    public static StatusLeitoEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static StatusLeitoEnum fromNome(String nome) {
        if (nome == null) return null;
        String n = nome.trim().toUpperCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.nome.equals(n))
                .findFirst()
                .orElse(null);
    }
}

