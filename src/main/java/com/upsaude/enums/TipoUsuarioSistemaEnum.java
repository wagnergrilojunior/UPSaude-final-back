package com.upsaude.enums;

public enum TipoUsuarioSistemaEnum {
    ADMIN_TENANT("Administrador do tenant"),
    GESTOR("Gestor"),
    RECEPCIONISTA("Recepcionista"),
    ATENDENTE("Atendente"),
    MEDICO("Médico"),
    PROFISSIONAL_SAUDE("Profissional de saúde"),
    FARMACEUTICO("Farmacêutico"),
    ENFERMEIRO("Enfermeiro"),
    TECNICO_ENFERMAGEM("Técnico de enfermagem"),
    FISIOTERAPEUTA("Fisioterapeuta"),
    PSICOLOGO("Psicólogo"),
    PACIENTE("Paciente"),
    OUTRO("Outro");

    private final String descricao;

    TipoUsuarioSistemaEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
