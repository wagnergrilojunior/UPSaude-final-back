package com.upsaude.enums;

public enum ClasseAtendimentoEnum {
    AMBULATORIAL("AMB", "Ambulatorial"),
    EMERGENCIA("EMER", "Emergência"),
    DOMICILIAR("HH", "Atendimento Domiciliar"),
    VIRTUAL("VR", "Atendimento Virtual"),
    INTERNACAO("IMP", "Internação"),
    NAO_AGUDA("NONAC", "Não Aguda (Longa Permanência)"),
    OBSERVACAO("OBSENC", "Observação"),
    PRE_ADMISSAO("PRENC", "Pré-Admissão");

    private final String codigoRnds;
    private final String descricao;

    ClasseAtendimentoEnum(String codigoRnds, String descricao) {
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
