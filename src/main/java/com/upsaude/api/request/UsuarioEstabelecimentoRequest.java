package com.upsaude.api.request;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioEstabelecimentoRequest {
    private UUID usuario;
    private UUID estabelecimento;
}
