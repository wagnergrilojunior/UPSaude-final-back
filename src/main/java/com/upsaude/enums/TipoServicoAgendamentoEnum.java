package com.upsaude.enums;

public enum TipoServicoAgendamentoEnum {
    CONSULTA("Consulta"),
    EXAME("Exame"),
    PROCEDIMENTO("Procedimento"),
    VACINACAO("Vacinação"),
    RETORNO("Retorno"),
    CIRURGIA("Cirurgia"),
    OUTRO("Outro");

    private final String descricao;

    TipoServicoAgendamentoEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
