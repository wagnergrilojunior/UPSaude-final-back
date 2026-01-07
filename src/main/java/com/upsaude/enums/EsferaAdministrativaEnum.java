package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

public enum EsferaAdministrativaEnum {
    FEDERAL(1, "FEDERAL", "Federal"),
    ESTADUAL(2, "ESTADUAL", "Estadual"),
    MUNICIPAL(3, "MUNICIPAL", "Municipal"),
    PRIVADO(4, "PRIVADO", "Privado");

    private final Integer codigo;
    private final String nome;
    private final String descricao;

    EsferaAdministrativaEnum(Integer codigo, String nome, String descricao) {
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

    public static EsferaAdministrativaEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static EsferaAdministrativaEnum fromNome(String nome) {
        if (nome == null) return null;
        String n = nome.trim().toUpperCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.nome.equals(n))
                .findFirst()
                .orElse(null);
    }
}

