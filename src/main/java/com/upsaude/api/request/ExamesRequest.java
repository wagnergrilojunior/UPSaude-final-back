package com.upsaude.api.request;

import jakarta.validation.constraints.NotNull;
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
public class ExamesRequest {

    @NotNull(message = "ID do estabelecimento é obrigatório")
    private UUID estabelecimentoId;

    @NotNull(message = "ID do paciente é obrigatório")
    private UUID pacienteId;

    private String tipoExame;

    private OffsetDateTime dataExame;

    private String resultados;
}

