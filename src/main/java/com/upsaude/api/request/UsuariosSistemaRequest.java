package com.upsaude.api.request;

import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuariosSistemaRequest {
    private UUID userId;
    private UUID profissionalSaude;
    private UUID medico;
    private UUID paciente;
    private Boolean adminTenant;
    private String nomeExibicao;
    private String user;
    private String fotoUrl;
}
