package com.upsaude.enums;

import com.upsaude.entity.clinica.atendimento.Atendimento;

import com.upsaude.entity.paciente.Paciente;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum TipoUsuarioSistemaEnum {

    ADMIN_TENANT(1, "ADMIN_TENANT", "Administrador da Organização", "Acesso total à organização com permissões administrativas completas"),
    GERENTE(2, "GERENTE", "Gerente da Organização", "Gestão operacional e administrativa da organização"),
    ADMIN_ESTABELECIMENTO(3, "ADMIN_ESTABELECIMENTO", "Administrador do Estabelecimento", "Acesso administrativo completo ao estabelecimento"),
    GERENTE_ESTABELECIMENTO(4, "GERENTE_ESTABELECIMENTO", "Gerente do Estabelecimento", "Gestão operacional do estabelecimento"),

    RECEPCIONISTA(10, "RECEPCIONISTA", "Recepcionista", "Atendimento ao público e agendamentos"),
    ATENDENTE(11, "ATENDENTE", "Atendente", "Atendimento geral ao público"),

    MEDICO(20, "MEDICO", "Médico", "Profissional médico com permissões clínicas completas"),
    ENFERMEIRO(21, "ENFERMEIRO", "Enfermeiro", "Profissional de enfermagem"),
    TECNICO_ENFERMAGEM(22, "TECNICO_ENFERMAGEM", "Técnico de Enfermagem", "Técnico de enfermagem com permissões técnicas"),
    FARMACEUTICO(23, "FARMACEUTICO", "Farmacêutico", "Profissional farmacêutico"),
    FISIOTERAPEUTA(24, "FISIOTERAPEUTA", "Fisioterapeuta", "Profissional de fisioterapia"),
    PSICOLOGO(25, "PSICOLOGO", "Psicólogo", "Profissional de psicologia"),
    NUTRICIONISTA(26, "NUTRICIONISTA", "Nutricionista", "Profissional de nutrição"),
    DENTISTA(27, "DENTISTA", "Dentista", "Cirurgião dentista"),
    TERAPEUTA_OCUPACIONAL(28, "TERAPEUTA_OCUPACIONAL", "Terapeuta Ocupacional", "Profissional de terapia ocupacional"),
    FONOAUDIOLOGO(29, "FONOAUDIOLOGO", "Fonoaudiólogo", "Profissional de fonoaudiologia"),
    BIOMEDICO(30, "BIOMEDICO", "Biomédico", "Profissional biomédico"),
    ASSISTENTE_SOCIAL(31, "ASSISTENTE_SOCIAL", "Assistente Social", "Profissional de serviço social"),
    PROFISSIONAL_SAUDE(32, "PROFISSIONAL_SAUDE", "Profissional de Saúde", "Outros profissionais de saúde"),

    PACIENTE(40, "PACIENTE", "Paciente", "Usuário paciente do sistema"),

    OUTRO(99, "OUTRO", "Outro", "Outros tipos de usuário");

    private final Integer codigo;

    @JsonValue
    private final String slug;

    private final String descricao;
    private final String detalhes;

    TipoUsuarioSistemaEnum(Integer codigo, String slug, String descricao, String detalhes) {
        this.codigo = codigo;
        this.slug = slug;
        this.descricao = descricao;
        this.detalhes = detalhes;
    }

    public static Optional<TipoUsuarioSistemaEnum> fromCodigo(Integer codigo) {
        if (codigo == null) {
            return Optional.empty();
        }
        return Arrays.stream(values())
                .filter(tipo -> tipo.codigo.equals(codigo))
                .findFirst();
    }

    public static Optional<TipoUsuarioSistemaEnum> fromSlug(String slug) {
        if (slug == null || slug.trim().isEmpty()) {
            return Optional.empty();
        }
        return Arrays.stream(values())
                .filter(tipo -> tipo.slug.equalsIgnoreCase(slug))
                .findFirst();
    }

    public static Optional<TipoUsuarioSistemaEnum> fromName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return Optional.empty();
        }
        try {
            return Optional.of(valueOf(name.toUpperCase()));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    public boolean isAdministrativo() {
        return this == ADMIN_TENANT || this == GERENTE ||
               this == ADMIN_ESTABELECIMENTO || this == GERENTE_ESTABELECIMENTO;
    }

    public boolean isProfissionalSaude() {
        return this == MEDICO || this == ENFERMEIRO || this == TECNICO_ENFERMAGEM ||
               this == FARMACEUTICO || this == FISIOTERAPEUTA || this == PSICOLOGO ||
               this == NUTRICIONISTA || this == DENTISTA || this == TERAPEUTA_OCUPACIONAL ||
               this == FONOAUDIOLOGO || this == BIOMEDICO || this == ASSISTENTE_SOCIAL ||
               this == PROFISSIONAL_SAUDE;
    }

    public boolean isPaciente() {
        return this == PACIENTE;
    }

    @Override
    public String toString() {
        return this.slug;
    }
}
