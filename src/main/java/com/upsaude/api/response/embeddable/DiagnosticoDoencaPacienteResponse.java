package com.upsaude.api.response.embeddable;

import com.upsaude.enums.GravidadeDoencaEnum;
import com.upsaude.enums.StatusDiagnosticoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiagnosticoDoencaPacienteResponse {
    private LocalDate dataDiagnostico;
    private LocalDate dataInicioSintomas;
    private StatusDiagnosticoEnum statusDiagnostico;
    private GravidadeDoencaEnum gravidadeAtual;
    private String localDiagnostico;
    private String metodoDiagnostico;
}
