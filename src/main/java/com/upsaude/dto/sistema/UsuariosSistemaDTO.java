package com.upsaude.dto.sistema;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;
import com.upsaude.dto.profissional.MedicosDTO;
import com.upsaude.dto.paciente.PacienteDTO;
import com.upsaude.dto.profissional.ProfissionaisSaudeDTO;


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
