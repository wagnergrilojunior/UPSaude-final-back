package com.upsaude.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "prontuarios", schema = "public")
@Data
public class Prontuarios extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @Column(name = "tipo_registro", length = 100)
    private String tipoRegistro;

    @Column(name = "conteudo", columnDefinition = "jsonb")
    private String conteudo;

    @Column(name = "criado_por")
    private java.util.UUID criadoPor;
}
