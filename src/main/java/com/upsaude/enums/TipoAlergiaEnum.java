package com.upsaude.enums;

public enum TipoAlergiaEnum {
    MEDICAMENTO,
    ALIMENTO,
    AMBIENTAL,
    CONTATO,
    INSETO,
    OUTRO;

    public String getDescricao() {
        switch (this) {
            case MEDICAMENTO:
                return "Medicamento";
            case ALIMENTO:
                return "Alimento";
            case AMBIENTAL:
                return "Ambiental";
            case CONTATO:
                return "Contato";
            case INSETO:
                return "Inseto";
            case OUTRO:
                return "Outro";
            default:
                return name();
        }
    }
}
