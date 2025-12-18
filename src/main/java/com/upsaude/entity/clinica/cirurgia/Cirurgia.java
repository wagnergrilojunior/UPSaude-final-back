package com.upsaude.entity.clinica.cirurgia;
import com.upsaude.entity.profissional.EspecialidadesMedicas;
import com.upsaude.entity.profissional.Medicos;
import com.upsaude.entity.BaseEntity;

import com.upsaude.entity.profissional.ProfissionaisSaude;

import com.upsaude.entity.paciente.Paciente;

import com.upsaude.enums.StatusCirurgiaEnum;
import com.upsaude.util.converter.StatusCirurgiaEnumConverter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cirurgias", schema = "public",
       indexes = {
           @Index(name = "idx_cirurgia_paciente", columnList = "paciente_id"),
           @Index(name = "idx_cirurgia_cirurgiao", columnList = "cirurgiao_principal_id"),
           @Index(name = "idx_cirurgia_data_hora", columnList = "data_hora_prevista"),
           @Index(name = "idx_cirurgia_status", columnList = "status"),
           @Index(name = "idx_cirurgia_estabelecimento", columnList = "estabelecimento_id"),
           @Index(name = "idx_cirurgia_sala", columnList = "sala_cirurgica")
       })
@Data
@EqualsAndHashCode(callSuper = true)
public class Cirurgia extends BaseEntity {

    public Cirurgia() {
        this.equipe = new ArrayList<>();
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    @NotNull(message = "Paciente é obrigatório")
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cirurgiao_principal_id", nullable = false)
    @NotNull(message = "Cirurgião principal é obrigatório")
    private ProfissionaisSaude cirurgiaoPrincipal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_cirurgiao_id")
    private Medicos medicoCirurgiao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "especialidade_id")
    private EspecialidadesMedicas especialidade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "convenio_id")
    private Convenio convenio;

    @OneToMany(mappedBy = "cirurgia", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EquipeCirurgica> equipe = new ArrayList<>();

    @Column(name = "descricao", nullable = false, columnDefinition = "TEXT")
    @NotNull(message = "Descrição da cirurgia é obrigatória")
    private String descricao;

    @Column(name = "codigo_procedimento", length = 50)
    private String codigoProcedimento;

    @Column(name = "data_hora_prevista", nullable = false)
    @NotNull(message = "Data e hora prevista são obrigatórias")
    private OffsetDateTime dataHoraPrevista;

    @Column(name = "data_hora_inicio")
    private OffsetDateTime dataHoraInicio;

    @Column(name = "data_hora_fim")
    private OffsetDateTime dataHoraFim;

    @Column(name = "duracao_prevista_minutos")
    private Integer duracaoPrevistaMinutos;

    @Column(name = "duracao_real_minutos")
    private Integer duracaoRealMinutos;

    @Column(name = "sala_cirurgica", length = 100)
    private String salaCirurgica;

    @Column(name = "leito_centro_cirurgico", length = 50)
    private String leitoCentroCirurgico;

    @Convert(converter = StatusCirurgiaEnumConverter.class)
    @Column(name = "status", nullable = false)
    @NotNull(message = "Status da cirurgia é obrigatório")
    private StatusCirurgiaEnum status;

    @Column(name = "valor_cirurgia", precision = 10, scale = 2)
    private BigDecimal valorCirurgia;

    @Column(name = "valor_material", precision = 10, scale = 2)
    private BigDecimal valorMaterial;

    @Column(name = "valor_total", precision = 10, scale = 2)
    private BigDecimal valorTotal;

    @Column(name = "observacoes_pre_operatorio", columnDefinition = "TEXT")
    private String observacoesPreOperatorio;

    @Column(name = "observacoes_pos_operatorio", columnDefinition = "TEXT")
    private String observacoesPosOperatorio;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @Column(name = "observacoes_internas", columnDefinition = "TEXT")
    private String observacoesInternas;

    @PrePersist
    @PreUpdate
    public void validateCollections() {
        if (equipe == null) {
            equipe = new ArrayList<>();
        }
    }

    public void addMembroEquipe(EquipeCirurgica membroEquipe) {
        if (membroEquipe == null) {
            return;
        }
        if (equipe == null) {
            equipe = new ArrayList<>();
        }
        if (!equipe.contains(membroEquipe)) {
            equipe.add(membroEquipe);
            membroEquipe.setCirurgia(this);
        }
    }

    public void removeMembroEquipe(EquipeCirurgica membroEquipe) {
        if (membroEquipe == null || equipe == null) {
            return;
        }
        if (equipe.remove(membroEquipe)) {
            membroEquipe.setCirurgia(null);
        }
    }
}
