package com.upsaude.api.request.deficiencia;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Dados de deficiências paciente")
public class DeficienciasPacienteRequest {
    private UUID paciente;
    private UUID deficiencia;
    private Boolean possuiLaudo;
    private LocalDate dataDiagnostico;
    private String observacoes;
}
