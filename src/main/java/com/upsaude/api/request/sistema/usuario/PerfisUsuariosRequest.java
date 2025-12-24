package com.upsaude.api.request.sistema.usuario;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de perfis usuários")
public class PerfisUsuariosRequest {
    private UUID usuarioId;
}
