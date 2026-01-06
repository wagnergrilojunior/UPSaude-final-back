package com.upsaude.api.request.clinica.prontuario;

import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de prontuário")
public class ProntuarioRequest {
    @NotNull(message = "Paciente é obrigatório")
    private UUID paciente;

    private UUID profissionalCriador;

    private OffsetDateTime dataAbertura;
}

