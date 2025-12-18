package com.upsaude.api.response.clinica.prontuario;
import com.upsaude.api.response.paciente.PacienteResponse;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProntuariosResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private PacienteResponse paciente;
    private String tipoRegistro;
    private String conteudo;
    private UUID criadoPor;
}
