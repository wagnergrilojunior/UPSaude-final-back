package com.upsaude.enums;

public enum TipoAgendamentoEnum {
    ROTINA("ROUTINE", "Rotina"),
    DEMANDA_ESPONTANEA("WALKIN", "Demanda Espontânea"),
    CHECKUP("CHECKUP", "Check-up"),
    ACOMPANHAMENTO("FOLLOWUP", "Acompanhamento"),
    EMERGENCIA("EMERGENCY", "Emergência");

    private final String codigoRnds;
    private final String descricao;

    TipoAgendamentoEnum(String codigoRnds, String descricao) {
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
