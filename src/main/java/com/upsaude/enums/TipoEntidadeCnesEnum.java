package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

public enum TipoEntidadeCnesEnum {
    ESTABELECIMENTO(1, "ESTABELECIMENTO", "Estabelecimento de Saúde"),
    PROFISSIONAL(2, "PROFISSIONAL", "Profissional de Saúde"),
    EQUIPE(3, "EQUIPE", "Equipe de Saúde"),
    VINCULACAO(4, "VINCULACAO", "Vinculação Profissional"),
    EQUIPAMENTO(5, "EQUIPAMENTO", "Equipamento"),
    LEITO(6, "LEITO", "Leito");

    private final Integer codigo;
    private final String nome;
    private final String descricao;

    TipoEntidadeCnesEnum(Integer codigo, String nome, String descricao) {
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

    public static TipoEntidadeCnesEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static TipoEntidadeCnesEnum fromNome(String nome) {
        if (nome == null) return null;
        String n = nome.trim().toUpperCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.nome.equals(n))
                .findFirst()
                .orElse(null);
    }
}

