package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

public enum TipoAlergiaEnum {
    MEDICAMENTO(1, "Medicamento"),
    ALIMENTO(2, "Alimento"),
    INALANTE(3, "Inalante"),
    CONTATO(4, "Contato"),
    INSETO(5, "Inseto"),
    LATEX(6, "Látex"),
    METAL(7, "Metal"),
    COSMETICO(8, "Cosmético"),
    PRODUTO_QUIMICO(9, "Produto Químico"),
    POEIRA(10, "Poeira"),
    POLEN(11, "Pólen"),
    FUNGO(12, "Fungo/Mofo"),
    ANIMAL(13, "Animal"),
    SOL(14, "Sol"),
    FRIO(15, "Frio"),
    CALOR(16, "Calor"),
    AGUA(17, "Água"),
    EXERCICIO(18, "Exercício"),
    ESTRESSE(19, "Estresse"),
    OUTRO(99, "Outro");

    private final Integer codigo;
    private final String descricao;

    TipoAlergiaEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoAlergiaEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static TipoAlergiaEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}
