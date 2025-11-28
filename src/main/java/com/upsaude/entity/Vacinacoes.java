package com.upsaude.entity;

import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "vacinacoes", schema = "public")
@Data
public class Vacinacoes extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vacina_id", nullable = false)
    private Vacinas vacina;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fabricante_id")
    private FabricantesVacina fabricante;

    @Column(name = "lote", length = 100)
    private String lote;

    @Column(name = "numero_dose", nullable = false)
    private Integer numeroDose;

    @Column(name = "data_aplicacao", nullable = false)
    private OffsetDateTime dataAplicacao;

    @Column(name = "local_aplicacao", length = 100)
    private String localAplicacao;

    @Column(name = "profissional_aplicador")
    private java.util.UUID profissionalAplicador;

    @Column(name = "reacao_adversa", columnDefinition = "TEXT")
    private String reacaoAdversa;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
}
