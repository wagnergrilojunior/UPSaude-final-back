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
@Table(name = "exames", schema = "public",
       indexes = {
           @Index(name = "idx_exame_prontuario", columnList = "prontuario_id"),
           @Index(name = "idx_exame_data_exame", columnList = "data_exame")
       })
@Data
@EqualsAndHashCode(callSuper = true)
public class ExamePaciente extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prontuario_id", nullable = false)
    private Prontuario prontuario;

    @Column(name = "tipo_exame")
    private String tipoExame;

    @Column(name = "nome_exame")
    private String nomeExame;

    @Column(name = "data_solicitacao")
    private OffsetDateTime dataSolicitacao;

    @Column(name = "data_exame")
    private OffsetDateTime dataExame;

    @Column(name = "data_resultado")
    private OffsetDateTime dataResultado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_solicitante_id")
    private ProfissionaisSaude profissionalSolicitante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_responsavel_id")
    private ProfissionaisSaude profissionalResponsavel;

    @Column(name = "laudo", columnDefinition = "TEXT")
    private String laudo;

    @Column(name = "resultados", columnDefinition = "jsonb")
    private String resultados;

    @Column(name = "unidade_laboratorio")
    private String unidadeLaboratorio;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sigtap_procedimento_id")
    private SigtapProcedimento procedimento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cid10_subcategorias_id")
    private Cid10Subcategorias diagnosticoRelacionado;
}

