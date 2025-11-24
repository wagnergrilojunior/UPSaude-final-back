package com.upsaude.dto;

import lombok.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VisitasDomiciliaresDTO {
    private UUID id;
    private UUID pacienteId;
    private OffsetDateTime dataVisita;
    private String motivo;
    private String observacoes;
    private Boolean active;
}

