package com.upsaude.api.response;

import com.upsaude.enums.TipoResponsavelEnum;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponsavelLegalResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private PacienteResponse paciente;
    private String nome;
    private String cpf;
    private String telefone;
    private TipoResponsavelEnum tipoResponsavel;
}
