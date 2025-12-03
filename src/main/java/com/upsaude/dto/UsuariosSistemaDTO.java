package com.upsaude.dto;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuariosSistemaDTO {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private UUID userId;
    private ProfissionaisSaudeDTO profissionalSaude;
    private MedicosDTO medico;
    private PacienteDTO paciente;
    private Boolean adminTenant;
    private String nomeExibicao;
    private String user;
    private String fotoUrl;
}
