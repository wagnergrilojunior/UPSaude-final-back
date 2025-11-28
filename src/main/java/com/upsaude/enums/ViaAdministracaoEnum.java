package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

/**
 * Enum para classificação de vias de administração de medicamentos conforme padrão médico e ANVISA.
 */
public enum ViaAdministracaoEnum {
    ORAL(1, "Oral"),
    INTRAMUSCULAR(2, "Intramuscular"),
    INTRAVENOSA(3, "Intravenosa"),
    SUBCUTANEA(4, "Subcutânea"),
    TOPICA(5, "Tópica"),
    RETAL(6, "Retal"),
    NASAL(7, "Nasal"),
    OFTALMICA(8, "Oftálmica"),
    OTICA(9, "Ótica"),
    INALATORIA(10, "Inalatória"),
    TRANSDERMICA(11, "Transdérmica"),
    VAGINAL(12, "Vaginal"),
    SUBLINGUAL(13, "Sublingual"),
    OUTRAS(99, "Outras");

    private final Integer codigo;
    private final String descricao;

    ViaAdministracaoEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static ViaAdministracaoEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static ViaAdministracaoEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}

