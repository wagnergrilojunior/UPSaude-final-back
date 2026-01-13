package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

public enum TipoRecursoIntegracaoEnum {
    APPOINTMENT(1, "APPOINTMENT", "Agendamento (FHIR Appointment)"),
    ENCOUNTER(2, "ENCOUNTER", "Atendimento (FHIR Encounter)"),
    CONSULTA(3, "CONSULTA", "Consulta médica");

    private final Integer codigo;
    private final String nome;
    private final String descricao;

    TipoRecursoIntegracaoEnum(Integer codigo, String nome, String descricao) {
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoRecursoIntegracaoEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static TipoRecursoIntegracaoEnum fromNome(String nome) {
        if (nome == null) return null;
        String n = nome.trim().toUpperCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.nome.equals(n))
                .findFirst()
                .orElse(null);
    }
}
