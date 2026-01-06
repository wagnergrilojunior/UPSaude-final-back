package com.upsaude.entity.clinica.prontuario;

import com.upsaude.entity.BaseEntity;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.profissional.ProfissionaisSaude;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "prontuario", schema = "public",
       indexes = {
           @Index(name = "idx_prontuario_paciente", columnList = "paciente_id")
       })
@Data
@EqualsAndHashCode(callSuper = true)
public class Prontuario extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false, unique = true)
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "criado_por")
    private ProfissionaisSaude profissionalCriador;

    @Column(name = "data_abertura")
    private OffsetDateTime dataAbertura;

    @OneToMany(mappedBy = "prontuario", fetch = FetchType.LAZY)
    private List<AlergiaPaciente> alergias = new ArrayList<>();

    @OneToMany(mappedBy = "prontuario", fetch = FetchType.LAZY)
    private List<VacinacaoPaciente> vacinacoes = new ArrayList<>();

    @OneToMany(mappedBy = "prontuario", fetch = FetchType.LAZY)
    private List<ExamePaciente> exames = new ArrayList<>();

    @OneToMany(mappedBy = "prontuario", fetch = FetchType.LAZY)
    private List<DoencaPaciente> doencas = new ArrayList<>();
}

