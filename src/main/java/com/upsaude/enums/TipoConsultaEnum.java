package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

public enum TipoConsultaEnum {
    PRIMEIRA_VEZ(1, "Primeira Vez"),
    RETORNO(2, "Retorno"),
    CONSULTA_ROTINA(3, "Consulta de Rotina"),
    CONSULTA_EMERGENCIA(4, "Consulta de Emergência"),
    CONSULTA_URGENCIA(5, "Consulta de Urgência"),
    CONSULTA_AMBULATORIAL(6, "Consulta Ambulatorial"),
    CONSULTA_DOMICILIAR(7, "Consulta Domiciliar"),
    CONSULTA_TELEMEDICINA(8, "Consulta Telemedicina"),
    CONSULTA_PREVENTIVA(9, "Consulta Preventiva"),
    CONSULTA_PEDIATRICA(10, "Consulta Pediátrica"),
    CONSULTA_GERIATRICA(11, "Consulta Geriátrica"),
    CONSULTA_OBSTETRICA(12, "Consulta Obstétrica"),
    CONSULTA_PSICOLOGICA(13, "Consulta Psicológica"),
    CONSULTA_FISIOTERAPIA(14, "Consulta Fisioterapia"),
    CONSULTA_NUTRICIONAL(15, "Consulta Nutricional"),
    CONSULTA_FONOAUDIOLOGIA(16, "Consulta Fonoaudiologia"),
    CONSULTA_TERAPIA_OCUPACIONAL(17, "Consulta Terapia Ocupacional"),
    CONSULTA_ODONTOLOGICA(18, "Consulta Odontológica"),
    CONSULTA_ESPECIALIZADA(19, "Consulta Especializada"),
    CONSULTA_SEGUIMENTO(20, "Consulta de Seguimento"),
    CONSULTA_REAVALIACAO(21, "Consulta de Reavaliação"),
    CONSULTA_PRE_OPERATORIA(22, "Consulta Pré-operatória"),
    CONSULTA_POS_OPERATORIA(23, "Consulta Pós-operatória"),
    CONSULTA_ALTA(24, "Consulta de Alta"),
    OUTRA(99, "Outra");

    private final Integer codigo;
    private final String descricao;

    TipoConsultaEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoConsultaEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static TipoConsultaEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}
