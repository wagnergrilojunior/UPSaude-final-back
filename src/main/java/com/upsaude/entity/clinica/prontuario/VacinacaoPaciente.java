package com.upsaude.entity.clinica.prontuario;

import com.upsaude.entity.BaseEntity;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.entity.referencia.cid.Cid10Subcategorias;
import com.upsaude.entity.referencia.sigtap.SigtapProcedimento;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.OffsetDateTime;

@Entity
@Table(name = "vacinacoes", schema = "public",
       indexes = {
           @Index(name = "idx_vacinacao_prontuario", columnList = "prontuario_id"),
           @Index(name = "idx_vacinacao_data", columnList = "data_aplicacao")
       })
@Data
@EqualsAndHashCode(callSuper = true)
public class VacinacaoPaciente extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prontuario_id", nullable = false)
    private Prontuario prontuario;

    @Column(name = "vacina_id", nullable = false)
    private java.util.UUID vacinaId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_aplicador")
    private ProfissionaisSaude profissionalAplicador;

    @Column(name = "data_aplicacao", nullable = false)
    private OffsetDateTime dataAplicacao;

    @Column(name = "numero_dose", nullable = false)
    private Integer numeroDose;

    @Column(name = "local_aplicacao")
    private String localAplicacao;

    @Column(name = "lote")
    private String lote;

    @Column(name = "reacao_adversa", columnDefinition = "TEXT")
    private String reacaoAdversa;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sigtap_procedimento_id")
    private SigtapProcedimento procedimento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cid10_subcategorias_id")
    private Cid10Subcategorias diagnosticoRelacionado;
}

