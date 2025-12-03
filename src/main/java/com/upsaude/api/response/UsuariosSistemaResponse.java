package com.upsaude.api.response;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuariosSistemaResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private UUID userId;
    private ProfissionaisSaudeResponse profissionalSaude;
    private MedicosResponse medico;
    private PacienteResponse paciente;
    private Boolean adminTenant;
    private String nomeExibicao;
    private String user;
    private String fotoUrl;
}
