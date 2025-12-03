package com.upsaude.api.request;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeficienciasPacienteRequest {
    private UUID paciente;
    private UUID deficiencia;
    private LocalDate dataDiagnostico;
    private String observacoes;
}
