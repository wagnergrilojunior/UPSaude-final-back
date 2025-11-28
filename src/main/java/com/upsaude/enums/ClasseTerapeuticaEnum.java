package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

/**
 * Enum para classificação de classes terapêuticas conforme padrão SUS/SIGTAP.
 * Baseado na Classificação Anatômica, Terapêutica e Química (ATC) adaptada para o Brasil.
 */
public enum ClasseTerapeuticaEnum {
    ANALGESICO_ANTIPIRETICO(1, "Analgésico/Antipirético"),
    ANTIINFLAMATORIO(2, "Anti-inflamatório"),
    ANTIBIOTICO(3, "Antibiótico"),
    ANTIVIRAL(4, "Antiviral"),
    ANTIFUNGICO(5, "Antifúngico"),
    ANTIPARASITARIO(6, "Antiparasitário"),
    ANTIHISTAMINICO(7, "Anti-histamínico"),
    BRONCODILATADOR(8, "Broncodilatador"),
    CORTICOIDE(9, "Corticoide"),
    ANTIDEPRESSIVO(10, "Antidepressivo"),
    ANSIOLITICO(11, "Ansiolítico"),
    ANTIPSICOTICO(12, "Antipsicótico"),
    ANTICONVULSIVANTE(13, "Anticonvulsivante"),
    ANTIACIDO(14, "Antiácido"),
    LAXANTE(15, "Laxante"),
    ANTIDIARREICO(16, "Antidiarreico"),
    VITAMINA(17, "Vitamina"),
    MINERAL(18, "Mineral"),
    HORMONIO(19, "Hormônio"),
    ANTIDIABETICO(20, "Antidiabético"),
    ANTIHIPERTENSIVO(21, "Anti-hipertensivo"),
    DIURETICO(22, "Diurético"),
    CARDIOTONICO(23, "Cardiotônico"),
    ANTICOAGULANTE(24, "Anticoagulante"),
    ANTIARRITMICO(25, "Antiarrítmico"),
    OUTROS(99, "Outros");

    private final Integer codigo;
    private final String descricao;

    ClasseTerapeuticaEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static ClasseTerapeuticaEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static ClasseTerapeuticaEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}

