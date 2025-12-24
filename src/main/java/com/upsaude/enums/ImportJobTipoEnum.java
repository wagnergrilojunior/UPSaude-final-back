package com.upsaude.enums;

/**
 * Tipos de importação suportados pelo sistema.
 */
public enum ImportJobTipoEnum {
    SIA_PA("SIA_PA", "SIA-SUS Produção Ambulatorial"),
    SIGTAP("SIGTAP", "SIGTAP"),
    CID10("CID10", "CID-10");

    private final String codigo;
    private final String descricao;

    ImportJobTipoEnum(String codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static ImportJobTipoEnum fromCodigo(String codigo) {
        if (codigo == null) return null;
        for (ImportJobTipoEnum tipo : values()) {
            if (tipo.codigo.equals(codigo)) {
                return tipo;
            }
        }
        return null;
    }
}

