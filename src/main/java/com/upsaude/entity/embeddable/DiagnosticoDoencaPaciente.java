package com.upsaude.entity.embeddable;

import com.upsaude.enums.GravidadeDoencaEnum;
import com.upsaude.enums.StatusDiagnosticoEnum;
import com.upsaude.util.converter.GravidadeDoencaEnumConverter;
import com.upsaude.util.converter.StatusDiagnosticoEnumConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Embeddable
@Data
@Builder
@AllArgsConstructor
public class DiagnosticoDoencaPaciente {

    public DiagnosticoDoencaPaciente() {
        this.localDiagnostico = "";
        this.metodoDiagnostico = "";
    }

    @Column(name = "data_diagnostico")
    private LocalDate dataDiagnostico;

    @Column(name = "data_inicio_sintomas")
    private LocalDate dataInicioSintomas;

    @Convert(converter = StatusDiagnosticoEnumConverter.class)
    @Column(name = "status_diagnostico")
    private StatusDiagnosticoEnum statusDiagnostico;

    @Convert(converter = GravidadeDoencaEnumConverter.class)
    @Column(name = "gravidade_atual")
    private GravidadeDoencaEnum gravidadeAtual;

    @Column(name = "local_diagnostico", length = 255)
    private String localDiagnostico;

    @Column(name = "metodo_diagnostico", length = 255)
    private String metodoDiagnostico;
}
