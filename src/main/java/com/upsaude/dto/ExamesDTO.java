package com.upsaude.dto;

import lombok.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExamesDTO {
    private UUID id;
    private UUID estabelecimentoId;
    private UUID pacienteId;
    private String tipoExame;
    private OffsetDateTime dataExame;
    private String resultados;
    private Boolean active;
}

