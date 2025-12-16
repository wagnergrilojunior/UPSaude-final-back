package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

public enum TipoServicoSaudeEnum {
    CONSULTA_MEDICA(1, "Consulta Médica"),
    CONSULTA_ENFERMAGEM(2, "Consulta de Enfermagem"),
    PRE_NATAL(3, "Pré-Natal"),
    PUERICULTURA(4, "Puericultura - Saúde da Criança"),
    PLANEJAMENTO_FAMILIAR(5, "Planejamento Familiar"),
    SAUDE_BUCAL(6, "Saúde Bucal"),
    EXAME_RAPIDO(7, "Exame Rápido"),
    TRIAGEM(8, "Triagem"),
    PROCEDIMENTO(9, "Procedimento"),
    CUIDADO_ENFERMAGEM(10, "Cuidado de Enfermagem"),
    CURATIVO(11, "Curativo"),
    VACINACAO(12, "Vacinação"),
    CONTROLE_DOENCA_CRONICA(13, "Controle de Doença Crônica"),
    EDUCACAO_SAUDE(14, "Educação em Saúde"),
    VISITA_DOMICILIAR(15, "Visita Domiciliar"),
    PROMOCAO_SAUDE(16, "Promoção de Saúde"),
    PREVENCAO(17, "Prevenção"),
    COLETA_EXAME(18, "Coleta de Exame"),
    ACOLHIMENTO(19, "Acolhimento"),
    SAUDE_MENTAL(20, "Saúde Mental"),
    SAUDE_IDOSO(21, "Saúde do Idoso"),
    SAUDE_MULHER(22, "Saúde da Mulher"),
    SAUDE_HOMEM(23, "Saúde do Homem"),
    NUTRICAO(24, "Nutrição"),
    FISIOTERAPIA(25, "Fisioterapia"),
    DISPENSACAO_MEDICAMENTO(26, "Dispensação de Medicamento"),
    IMUNIZACAO(27, "Imunização"),
    ATENDIMENTO_URGENCIA(28, "Atendimento de Urgência"),
    ATENDIMENTO_EMERGENCIA(29, "Atendimento de Emergência"),
    OUTRO(99, "Outro");

    private final Integer codigo;
    private final String descricao;

    TipoServicoSaudeEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoServicoSaudeEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static TipoServicoSaudeEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}
