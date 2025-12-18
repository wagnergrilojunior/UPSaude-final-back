package com.upsaude.api.response.deficiencia;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeficienciasPacienteResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private PacienteResponse paciente;
    private DeficienciasResponse deficiencia;
    private Boolean possuiLaudo;
    private LocalDate dataDiagnostico;
    private String observacoes;
}
