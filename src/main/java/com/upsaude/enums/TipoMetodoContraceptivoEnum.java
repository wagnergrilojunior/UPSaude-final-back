package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

public enum TipoMetodoContraceptivoEnum {

    PILULA_COMBINADA(1, "Pílula Combinada"),
    PILULA_MINIPILULA(2, "Minipílula"),
    INJETAVEL_MENSAL(3, "Injetável Mensal"),
    INJETAVEL_TRIMESTRAL(4, "Injetável Trimestral"),
    IMPLANTE_SUBDERMICO(5, "Implante Subdérmico"),
    ANEL_VAGINAL(6, "Anel Vaginal"),
    ADESIVO_HORMONAL(7, "Adesivo Hormonal"),

    DIU_COBRE(10, "DIU de Cobre"),
    DIU_HORMONAL(11, "DIU Hormonal (Mirena)"),

    PRESERVATIVO_MASCULINO(20, "Preservativo Masculino"),
    PRESERVATIVO_FEMININO(21, "Preservativo Feminino"),
    DIAFRAGMA(22, "Diafragma"),
    ESPERMICIDA(23, "Espermicida"),

    TABELINHA(30, "Tabelinha (Ogino-Knaus)"),
    METODO_BILLINGS(31, "Método Billings (Muco Cervical)"),
    TEMPERATURA_BASAL(32, "Temperatura Basal"),
    COITO_INTERROMPIDO(33, "Coito Interrompido"),
    LAM(34, "LAM (Lactação e Amenorreia)"),

    LAQUEADURA(40, "Laqueadura Tubária"),
    VASECTOMIA(41, "Vasectomia"),

    PILULA_DIA_SEGUINTE(50, "Pílula do Dia Seguinte"),

    NENHUM(90, "Nenhum"),
    OUTRO(99, "Outro");

    private final Integer codigo;
    private final String descricao;

    TipoMetodoContraceptivoEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoMetodoContraceptivoEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static TipoMetodoContraceptivoEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}
