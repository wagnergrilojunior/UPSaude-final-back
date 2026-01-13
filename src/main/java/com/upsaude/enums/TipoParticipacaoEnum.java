package com.upsaude.enums;

public enum TipoParticipacaoEnum {
    EXECUTANTE("PPRF", "Executante Principal"),
    SOLICITANTE("REF", "Solicitante"),
    ACOMPANHANTE("ATND", "Acompanhante"),
    RESPONSAVEL("RESP", "Responsável"),
    CONSULTOR("CON", "Consultor"),
    ADMITIDOR("ADM", "Admitidor"),
    ALTA("DIS", "Responsável pela Alta");

    private final String codigoRnds;
    private final String descricao;

    TipoParticipacaoEnum(String codigoRnds, String descricao) {
        this.codigoRnds = codigoRnds;
        this.descricao = descricao;
    }

    public String getCodigoRnds() {
        return codigoRnds;
    }

    public String getDescricao() {
        return descricao;
    }
}
