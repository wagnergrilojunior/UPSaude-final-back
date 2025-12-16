package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

public enum TipoAtendimentoEnum {
    CONSULTA_MEDICA(1, "Consulta Médica"),
    CONSULTA_ENFERMAGEM(2, "Consulta de Enfermagem"),
    CONSULTA_ODONTOLOGICA(3, "Consulta Odontológica"),
    CONSULTA_PSICOLOGICA(4, "Consulta Psicológica"),
    CONSULTA_FISIOTERAPIA(5, "Consulta Fisioterapia"),
    CONSULTA_NUTRICIONAL(6, "Consulta Nutricional"),
    CONSULTA_FONOAUDIOLOGIA(7, "Consulta Fonoaudiologia"),
    CONSULTA_TERAPIA_OCUPACIONAL(8, "Consulta Terapia Ocupacional"),
    ATENDIMENTO_DOMICILIAR(9, "Atendimento Domiciliar"),
    VISITA_DOMICILIAR(10, "Visita Domiciliar"),
    ATENDIMENTO_URGENCIA(11, "Atendimento de Urgência"),
    ATENDIMENTO_EMERGENCIA(12, "Atendimento de Emergência"),
    ATENDIMENTO_AMBULATORIAL(13, "Atendimento Ambulatorial"),
    PROCEDIMENTO(14, "Procedimento"),
    CURATIVO(15, "Curativo"),
    VACINACAO(16, "Vacinação"),
    COLETA_EXAME(17, "Coleta de Exame"),
    ENTREGA_RESULTADO(18, "Entrega de Resultado"),
    ORIENTACAO(19, "Orientação"),
    ACOLHIMENTO(20, "Acolhimento"),
    TRIAGEM(21, "Triagem"),
    ATENDIMENTO_GRUPO(22, "Atendimento em Grupo"),
    ATENDIMENTO_COLETIVO(23, "Atendimento Coletivo"),
    ATENDIMENTO_TELEMEDICINA(24, "Atendimento Telemedicina"),
    ATENDIMENTO_TELEFONICO(25, "Atendimento Telefônico"),
    OUTRO(99, "Outro");

    private final Integer codigo;
    private final String descricao;

    TipoAtendimentoEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoAtendimentoEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static TipoAtendimentoEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}
