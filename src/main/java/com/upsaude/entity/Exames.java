package com.upsaude.entity;

import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "exames", schema = "public",
       indexes = {
           @Index(name = "idx_exame_paciente", columnList = "paciente_id"),
           @Index(name = "idx_exame_catalogo", columnList = "catalogo_exame_id"),
           @Index(name = "idx_exame_data", columnList = "data_exame"),
           @Index(name = "idx_exame_atendimento", columnList = "atendimento_id"),
           @Index(name = "idx_exame_consulta", columnList = "consulta_id"),
           @Index(name = "idx_exame_estabelecimento", columnList = "estabelecimento_id")
       })
@Data
@EqualsAndHashCode(callSuper = true)
public class Exames extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    @NotNull(message = "Paciente é obrigatório")
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "catalogo_exame_id")
    private CatalogoExames catalogoExame;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atendimento_id")
    private Atendimento atendimento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consulta_id")
    private Consultas consulta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_solicitante_id")
    private ProfissionaisSaude profissionalSolicitante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_solicitante_id")
    private Medicos medicoSolicitante;

    @Column(name = "tipo_exame", length = 100)
    private String tipoExame;

    @Column(name = "nome_exame", length = 255)
    private String nomeExame;

    @Column(name = "data_solicitacao")
    private OffsetDateTime dataSolicitacao;

    @Column(name = "data_exame")
    private OffsetDateTime dataExame;

    @Column(name = "data_resultado")
    private OffsetDateTime dataResultado;

    @Column(name = "unidade_laboratorio", length = 255)
    private String unidadeLaboratorio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estabelecimento_realizador_id")
    private Estabelecimentos estabelecimentoRealizador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_responsavel_id")
    private ProfissionaisSaude profissionalResponsavel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_responsavel_id")
    private Medicos medicoResponsavel;

    @Column(name = "resultados", columnDefinition = "jsonb")
    private String resultados;

    @Column(name = "laudo", columnDefinition = "TEXT")
    private String laudo;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
}
