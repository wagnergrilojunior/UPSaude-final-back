package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

public enum ModalidadeConvenioEnum {
    INDIVIDUAL(1, "Individual"),
    FAMILIAR(2, "Familiar"),
    EMPRESARIAL(3, "Empresarial"),
    COLETIVO_POR_ADESAO(4, "Coletivo por Adesão"),
    COLETIVO_EMPRESARIAL(5, "Coletivo Empresarial"),
    COLETIVO_EMPRESARIAL_GRANDE_PORTE(6, "Coletivo Empresarial Grande Porte"),
    COLETIVO_EMPRESARIAL_PEQUENO_PORTE(7, "Coletivo Empresarial Pequeno Porte"),
    COLETIVO_EMPRESARIAL_MEDIO_PORTE(8, "Coletivo Empresarial Médio Porte"),
    COLETIVO_EMPRESARIAL_MICRO_EMPRESA(9, "Coletivo Empresarial Microempresa"),
    COLETIVO_EMPRESARIAL_MEI(10, "Coletivo Empresarial MEI"),
    COLETIVO_EMPRESARIAL_ME(11, "Coletivo Empresarial ME"),
    COLETIVO_EMPRESARIAL_EPP(12, "Coletivo Empresarial EPP"),
    COLETIVO_EMPRESARIAL_EPE(13, "Coletivo Empresarial EPE"),
    COLETIVO_EMPRESARIAL_EP(14, "Coletivo Empresarial EP"),
    COLETIVO_EMPRESARIAL_EPP_GRANDE_PORTE(15, "Coletivo Empresarial EPP Grande Porte"),
    COLETIVO_EMPRESARIAL_EPP_MEDIO_PORTE(16, "Coletivo Empresarial EPP Médio Porte"),
    COLETIVO_EMPRESARIAL_EPP_PEQUENO_PORTE(17, "Coletivo Empresarial EPP Pequeno Porte"),
    COLETIVO_EMPRESARIAL_EPP_MICRO_EMPRESA(18, "Coletivo Empresarial EPP Microempresa"),
    COLETIVO_EMPRESARIAL_EPP_MEI(19, "Coletivo Empresarial EPP MEI"),
    COLETIVO_EMPRESARIAL_EPP_ME(20, "Coletivo Empresarial EPP ME"),
    COLETIVO_EMPRESARIAL_EPP_EPP(21, "Coletivo Empresarial EPP EPP"),
    COLETIVO_EMPRESARIAL_EPP_EPE(22, "Coletivo Empresarial EPP EPE"),
    COLETIVO_EMPRESARIAL_EPP_EP(23, "Coletivo Empresarial EPP EP"),
    COLETIVO_EMPRESARIAL_EPE_GRANDE_PORTE(24, "Coletivo Empresarial EPE Grande Porte"),
    COLETIVO_EMPRESARIAL_EPE_MEDIO_PORTE(25, "Coletivo Empresarial EPE Médio Porte"),
    COLETIVO_EMPRESARIAL_EPE_PEQUENO_PORTE(26, "Coletivo Empresarial EPE Pequeno Porte"),
    COLETIVO_EMPRESARIAL_EPE_MICRO_EMPRESA(27, "Coletivo Empresarial EPE Microempresa"),
    COLETIVO_EMPRESARIAL_EPE_MEI(28, "Coletivo Empresarial EPE MEI"),
    COLETIVO_EMPRESARIAL_EPE_ME(29, "Coletivo Empresarial EPE ME"),
    COLETIVO_EMPRESARIAL_EPE_EPP(30, "Coletivo Empresarial EPE EPP"),
    COLETIVO_EMPRESARIAL_EPE_EPE(31, "Coletivo Empresarial EPE EPE"),
    COLETIVO_EMPRESARIAL_EPE_EP(32, "Coletivo Empresarial EPE EP"),
    COLETIVO_EMPRESARIAL_EP_GRANDE_PORTE(33, "Coletivo Empresarial EP Grande Porte"),
    COLETIVO_EMPRESARIAL_EP_MEDIO_PORTE(34, "Coletivo Empresarial EP Médio Porte"),
    COLETIVO_EMPRESARIAL_EP_PEQUENO_PORTE(35, "Coletivo Empresarial EP Pequeno Porte"),
    COLETIVO_EMPRESARIAL_EP_MICRO_EMPRESA(36, "Coletivo Empresarial EP Microempresa"),
    COLETIVO_EMPRESARIAL_EP_MEI(37, "Coletivo Empresarial EP MEI"),
    COLETIVO_EMPRESARIAL_EP_ME(38, "Coletivo Empresarial EP ME"),
    COLETIVO_EMPRESARIAL_EP_EPP(39, "Coletivo Empresarial EP EPP"),
    COLETIVO_EMPRESARIAL_EP_EPE(40, "Coletivo Empresarial EP EPE"),
    COLETIVO_EMPRESARIAL_EP_EP(41, "Coletivo Empresarial EP EP"),
    OUTRO(99, "Outro");

    private final Integer codigo;
    private final String descricao;

    ModalidadeConvenioEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static ModalidadeConvenioEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static ModalidadeConvenioEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}
