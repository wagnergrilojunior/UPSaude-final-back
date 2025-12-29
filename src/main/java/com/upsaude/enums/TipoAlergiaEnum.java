package com.upsaude.enums;

/**
 * Enum representando a categoria geral da alergia.
 * Modelo simplificado e conceitualmente correto para prontuário eletrônico.
 * Alergia é informação clínica declarada do paciente, não diagnóstico CID.
 */
public enum TipoAlergiaEnum {
    MEDICAMENTO,
    ALIMENTO,
    AMBIENTAL,
    CONTATO,
    INSETO,
    OUTRO;

    /**
     * Retorna a descrição formatada do tipo de alergia.
     * @return Descrição em português
     */
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
