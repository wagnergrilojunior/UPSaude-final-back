package com.upsaude.api.request.embeddable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.upsaude.enums.GravidadeDoencaEnum;
import com.upsaude.enums.StatusDiagnosticoEnum;
import com.upsaude.util.converter.GravidadeDoencaEnumDeserializer;
import com.upsaude.util.converter.StatusDiagnosticoEnumDeserializer;
import jakarta.validation.constraints.Size;
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
public class DiagnosticoDoencaPacienteRequest {
    private LocalDate dataDiagnostico;
    private LocalDate dataInicioSintomas;
    @JsonDeserialize(using = StatusDiagnosticoEnumDeserializer.class)
    private StatusDiagnosticoEnum statusDiagnostico;
    
    @JsonDeserialize(using = GravidadeDoencaEnumDeserializer.class)
    private GravidadeDoencaEnum gravidadeAtual;
    
    @Size(max = 255, message = "Local diagnóstico deve ter no máximo 255 caracteres")
    private String localDiagnostico;
    
    @Size(max = 255, message = "Método diagnóstico deve ter no máximo 255 caracteres")
    private String metodoDiagnostico;
}
