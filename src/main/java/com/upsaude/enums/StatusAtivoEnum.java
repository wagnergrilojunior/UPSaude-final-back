package com.upsaude.enums;

public enum StatusAtivoEnum {
    ATIVO("Ativo"),
    INATIVO("Inativo");

    private final String descricao;

    StatusAtivoEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static StatusAtivoEnum fromBoolean(Boolean ativo) {
        return ativo != null && ativo ? ATIVO : INATIVO;
    }

    public Boolean toBoolean() {
        return this == ATIVO;
    }
}