package com.upsaude.api.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeficienciasPacienteRequest {
    private UUID paciente;
    private UUID deficiencia;
    private Boolean possuiLaudo;
    private LocalDate dataDiagnostico;
    private String observacoes;
}
