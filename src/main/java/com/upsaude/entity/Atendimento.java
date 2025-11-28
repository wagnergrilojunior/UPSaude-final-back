package com.upsaude.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.OffsetDateTime;

/**
 * Entidade que representa um atendimento realizado por um profissional de saúde a um paciente.
 * Armazena informações sobre consultas, atendimentos domiciliares, emergências, etc.
 *
 * @author UPSaúde
 */
@Entity
@Table(name = "atendimentos", schema = "public")
@Data
@EqualsAndHashCode(callSuper = true)
public class Atendimento extends BaseEntity {

    /**
     * Relacionamento ManyToOne com Paciente.
     * O paciente que recebeu o atendimento.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    @NotNull(message = "Paciente é obrigatório")
    private Paciente paciente;

    /**
     * Relacionamento ManyToOne com ProfissionaisSaude.
     * O profissional de saúde que realizou o atendimento.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_id", nullable = false)
    @NotNull(message = "Profissional de saúde é obrigatório")
    private ProfissionaisSaude profissional;

    /**
     * Data e hora do atendimento.
     * Registra quando o atendimento foi realizado.
     */
    @Column(name = "data_hora", nullable = false)
    @NotNull(message = "Data e hora do atendimento são obrigatórias")
    private OffsetDateTime dataHora;

    /**
     * Tipo de atendimento.
     * Exemplos: "consulta", "domiciliar", "emergencia".
     */
    @Column(name = "tipo_atendimento", length = 50)
    @Size(max = 50, message = "Tipo de atendimento deve ter no máximo 50 caracteres")
    private String tipoAtendimento;

    /**
     * Motivo da consulta ou atendimento.
     * Descrição do motivo que levou o paciente a buscar o atendimento.
     */
    @Column(name = "motivo", length = 255)
    @Size(max = 255, message = "Motivo deve ter no máximo 255 caracteres")
    private String motivo;

    /**
     * Relacionamento ManyToOne com CidDoencas.
     * CID-10 do diagnóstico principal do atendimento.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cid_principal_id")
    private CidDoencas cidPrincipal;

    /**
     * Anotações ou observações da consulta.
     * Campo de texto livre para anotações adicionais sobre o atendimento.
     */
    @Column(name = "anotacoes", columnDefinition = "TEXT")
    private String anotacoes;
}

