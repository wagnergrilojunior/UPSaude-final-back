package com.upsaude.api.request;

import com.upsaude.enums.TipoUsuarioSistemaEnum;
import java.time.OffsetDateTime;
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
    private TipoUsuarioSistemaEnum tipoUsuario;
    private String nomeExibicao;
    private String user;
    private String fotoUrl;
}
