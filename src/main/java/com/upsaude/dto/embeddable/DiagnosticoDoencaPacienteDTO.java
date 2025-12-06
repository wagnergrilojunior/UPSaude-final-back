package com.upsaude.dto.embeddable;

import com.upsaude.enums.GravidadeDoencaEnum;
import com.upsaude.enums.StatusDiagnosticoEnum;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiagnosticoDoencaPacienteDTO {
    private LocalDate dataDiagnostico;
    private LocalDate dataInicioSintomas;
    private StatusDiagnosticoEnum statusDiagnostico;
    private GravidadeDoencaEnum gravidadeAtual;
    private String localDiagnostico;
    private String metodoDiagnostico;
}
