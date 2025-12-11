package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

public enum FormaFarmaceuticaEnum {
    COMPRIMIDO(1, "Comprimido"),
    CAPSULA(2, "Cápsula"),
    SOLUCAO_ORAL(3, "Solução Oral"),
    SUSPENSAO_ORAL(4, "Suspensão Oral"),
    XAROPE(5, "Xarope"),
    GOTAS(6, "Gotas"),
    PO_PARA_SOLUCAO(7, "Pó para Solução"),
    INJETAVEL(8, "Injetável"),
    CREME(9, "Creme"),
    POMADA(10, "Pomada"),
    GEL(11, "Gel"),
    LOCAO(12, "Loção"),
    SPRAY(13, "Spray"),
    AEROSOL(14, "Aerossol"),
    SUPPOSITORIO(15, "Supositório"),
    ENEMA(16, "Enema"),
    COLIRIO(17, "Colírio"),
    OTICO(18, "Ótico"),
    NASAL(19, "Nasal"),
    ADESIVO(20, "Adesivo"),
    INALACAO(21, "Inalação"),
    OUTROS(99, "Outros");

    private final Integer codigo;
    private final String descricao;

    FormaFarmaceuticaEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static FormaFarmaceuticaEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static FormaFarmaceuticaEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}
