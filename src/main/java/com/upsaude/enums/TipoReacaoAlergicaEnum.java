package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

/**
 * Enum para classificação de tipos de reações alérgicas.
 *
 * @author UPSaúde
 */
public enum TipoReacaoAlergicaEnum {
    URTICARIA(1, "Urticária"),
    ANGIOEDEMA(2, "Angioedema"),
    RINITE(3, "Rinite"),
    CONJUNTIVITE(4, "Conjuntivite"),
    ASMA(5, "Asma"),
    DERMATITE_CONTATO(6, "Dermatite de Contato"),
    DERMATITE_ATOPICA(7, "Dermatite Atópica"),
    ANAFILAXIA(8, "Anafilaxia"),
    CHOQUE_ANAFILATICO(9, "Choque Anafilático"),
    NAUSEA(10, "Náusea"),
    VOMITO(11, "Vômito"),
    DIARREIA(12, "Diarreia"),
    DOR_ABDOMINAL(13, "Dor Abdominal"),
    PRURIDO(14, "Prurido"),
    ERITEMA(15, "Eritema"),
    EDEMA(16, "Edema"),
    DISPNEIA(17, "Dispneia"),
    TOSSE(18, "Tosse"),
    ESPIRROS(19, "Espirros"),
    LACRIMEJAMENTO(20, "Lacrimejamento"),
    OUTRO(99, "Outro");

    private final Integer codigo;
    private final String descricao;

    TipoReacaoAlergicaEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoReacaoAlergicaEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static TipoReacaoAlergicaEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}

