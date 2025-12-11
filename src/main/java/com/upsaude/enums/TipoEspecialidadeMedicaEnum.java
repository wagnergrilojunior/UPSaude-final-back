package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

public enum TipoEspecialidadeMedicaEnum {
    CLINICA_MEDICA(1, "Clínica Médica"),
    CIRURGIA_GERAL(2, "Cirurgia Geral"),
    PEDIATRIA(3, "Pediatria"),
    GINECOLOGIA_OBSTETRICIA(4, "Ginecologia e Obstetrícia"),
    ORTOPEDIA(5, "Ortopedia"),
    CARDIOLOGIA(6, "Cardiologia"),
    DERMATOLOGIA(7, "Dermatologia"),
    OFTALMOLOGIA(8, "Oftalmologia"),
    OTORRINOLARINGOLOGIA(9, "Otorrinolaringologia"),
    PSIQUIATRIA(10, "Psiquiatria"),
    NEUROLOGIA(11, "Neurologia"),
    UROLOGIA(12, "Urologia"),
    ANESTESIOLOGIA(13, "Anestesiologia"),
    RADIOLOGIA(14, "Radiologia"),
    PATOLOGIA(15, "Patologia"),
    MEDICINA_FAMILIAR(16, "Medicina de Família e Comunidade"),
    MEDICINA_URGENCIA(17, "Medicina de Urgência"),
    MEDICINA_INTENSIVA(18, "Medicina Intensiva"),
    MEDICINA_PREVENTIVA(19, "Medicina Preventiva"),
    MEDICINA_DO_TRABALHO(20, "Medicina do Trabalho"),
    MEDICINA_LEGAL(21, "Medicina Legal"),
    MEDICINA_ESPORTIVA(22, "Medicina Esportiva"),
    GERIATRIA(23, "Geriatria"),
    ENDOCRINOLOGIA(24, "Endocrinologia"),
    GASTROENTEROLOGIA(25, "Gastroenterologia"),
    PNEUMOLOGIA(26, "Pneumologia"),
    NEFROLOGIA(27, "Nefrologia"),
    HEMATOLOGIA(28, "Hematologia"),
    ONCOLOGIA(29, "Oncologia"),
    REUMATOLOGIA(30, "Reumatologia"),
    INFECTOLOGIA(31, "Infectologia"),
    ALERGOLOGIA(32, "Alergologia"),
    OUTRO(99, "Outro");

    private final Integer codigo;
    private final String descricao;

    TipoEspecialidadeMedicaEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoEspecialidadeMedicaEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static TipoEspecialidadeMedicaEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}
