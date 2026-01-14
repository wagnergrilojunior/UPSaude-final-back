package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

public enum CategoriaAnexoEnum {
    LAUDO(1, "Laudo"),
    EXAME(2, "Exame"),
    DOCUMENTO(3, "Documento"),
    IMAGEM(4, "Imagem"),
    RECEITA(5, "Receita"),
    ATESTADO(6, "Atestado"),
    ENCAMINHAMENTO(7, "Encaminhamento"),
    OUTROS(99, "Outros");

    private final Integer codigo;
    private final String descricao;

    CategoriaAnexoEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static CategoriaAnexoEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static CategoriaAnexoEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}
