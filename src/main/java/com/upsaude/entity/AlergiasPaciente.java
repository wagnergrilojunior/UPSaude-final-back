package com.upsaude.entity;

import com.upsaude.enums.SeveridadeAlergiaEnum;
import com.upsaude.util.converter.SeveridadeAlergiaEnumConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "alergias_paciente", schema = "public")
@Data
public class AlergiasPaciente extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alergia_id", nullable = false)
    private Alergias alergia;

    @Convert(converter = SeveridadeAlergiaEnumConverter.class)
    @Column(name = "severidade")
    private SeveridadeAlergiaEnum severidade;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @Column(name = "data_diagnostico")
    private java.time.LocalDate dataDiagnostico;
}
