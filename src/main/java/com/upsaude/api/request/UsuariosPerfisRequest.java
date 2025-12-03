package com.upsaude.api.request;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuariosPerfisRequest {
    private UUID usuario;
    private UUID papel;
    private String escopo;
}
