package com.upsaude.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExamesResponse {
    private UUID id;
    private UUID estabelecimentoId;
    private String estabelecimentoNome;
    private UUID pacienteId;
    private String pacienteNome;
    private String tipoExame;
    private OffsetDateTime dataExame;
    private String resultados;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
}

