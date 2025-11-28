package com.upsaude.api.response;

import lombok.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Classe de resposta para Deficiências de Paciente.
 *
 * @author UPSaúde
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeficienciasPacienteResponse {
    private UUID id;
    private UUID pacienteId;
    private UUID deficienciaId;
    private String deficienciaNome;
    private Boolean possuiLaudo;
    private LocalDate dataDiagnostico;
    private String observacoes;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
}

