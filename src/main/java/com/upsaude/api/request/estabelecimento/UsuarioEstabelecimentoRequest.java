package com.upsaude.api.request.estabelecimento;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de usuário estabelecimento")
public class UsuarioEstabelecimentoRequest {
    private UUID usuario;
    private UUID estabelecimento;
}
