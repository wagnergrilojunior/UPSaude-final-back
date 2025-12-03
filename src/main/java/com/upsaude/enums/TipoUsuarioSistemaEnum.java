package com.upsaude.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

/**
 * Enum para tipos de usuário do sistema.
 * Controla as permissões e funcionalidades disponíveis para cada tipo de usuário.
 */
@Getter
public enum TipoUsuarioSistemaEnum {
    
    // Administrativo
    ADMIN_TENANT(1, "ADMIN_TENANT", "Administrador do Tenant", "Acesso total ao tenant com permissões administrativas completas"),
    GESTOR(2, "GESTOR", "Gestor", "Gestão operacional e administrativa do estabelecimento"),
    
    // Atendimento e Recepção
    RECEPCIONISTA(10, "RECEPCIONISTA", "Recepcionista", "Atendimento ao público e agendamentos"),
    ATENDENTE(11, "ATENDENTE", "Atendente", "Atendimento geral ao público"),
    
    // Profissionais de Saúde
    MEDICO(20, "MEDICO", "Médico", "Profissional médico com permissões clínicas completas"),
    ENFERMEIRO(21, "ENFERMEIRO", "Enfermeiro", "Profissional de enfermagem"),
    TECNICO_ENFERMAGEM(22, "TECNICO_ENFERMAGEM", "Técnico de Enfermagem", "Técnico de enfermagem com permissões técnicas"),
    FARMACEUTICO(23, "FARMACEUTICO", "Farmacêutico", "Profissional farmacêutico"),
    FISIOTERAPEUTA(24, "FISIOTERAPEUTA", "Fisioterapeuta", "Profissional de fisioterapia"),
    PSICOLOGO(25, "PSICOLOGO", "Psicólogo", "Profissional de psicologia"),
    PROFISSIONAL_SAUDE(26, "PROFISSIONAL_SAUDE", "Profissional de Saúde", "Outros profissionais de saúde"),
    
    // Paciente
    PACIENTE(30, "PACIENTE", "Paciente", "Usuário paciente do sistema"),
    
    // Outros
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

    /**
     * Busca um tipo de usuário pelo código numérico
     */
    public static Optional<TipoUsuarioSistemaEnum> fromCodigo(Integer codigo) {
        if (codigo == null) {
            return Optional.empty();
        }
        return Arrays.stream(values())
                .filter(tipo -> tipo.codigo.equals(codigo))
                .findFirst();
    }

    /**
     * Busca um tipo de usuário pelo slug
     */
    public static Optional<TipoUsuarioSistemaEnum> fromSlug(String slug) {
        if (slug == null || slug.trim().isEmpty()) {
            return Optional.empty();
        }
        return Arrays.stream(values())
                .filter(tipo -> tipo.slug.equalsIgnoreCase(slug))
                .findFirst();
    }

    /**
     * Busca um tipo de usuário pelo nome do enum
     */
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

    /**
     * Verifica se é um tipo administrativo
     */
    public boolean isAdministrativo() {
        return this == ADMIN_TENANT || this == GESTOR;
    }

    /**
     * Verifica se é um profissional de saúde
     */
    public boolean isProfissionalSaude() {
        return this == MEDICO || this == ENFERMEIRO || this == TECNICO_ENFERMAGEM ||
               this == FARMACEUTICO || this == FISIOTERAPEUTA || this == PSICOLOGO ||
               this == PROFISSIONAL_SAUDE;
    }

    /**
     * Verifica se é paciente
     */
    public boolean isPaciente() {
        return this == PACIENTE;
    }

    @Override
    public String toString() {
        return this.slug;
    }
}
