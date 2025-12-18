package com.upsaude.dto.prontuario;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProntuariosDTO {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private PacienteDTO paciente;
    private String tipoRegistro;
    private String conteudo;
    private UUID criadoPor;
}
