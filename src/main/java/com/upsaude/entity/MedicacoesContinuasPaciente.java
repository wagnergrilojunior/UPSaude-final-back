package com.upsaude.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "medicacoes_continuas_paciente", schema = "public")
@Data
public class MedicacoesContinuasPaciente extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicacao_continua_id", nullable = false)
    private MedicacoesContinuas medicacaoContinua;

    @Column(name = "dosagem_atual", length = 100)
    private String dosagemAtual;

    @Column(name = "frequencia_administracao", length = 100)
    private String frequenciaAdministracao;

    @Column(name = "data_inicio")
    private java.time.LocalDate dataInicio;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
}
