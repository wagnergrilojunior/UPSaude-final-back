package com.upsaude.api.request;

import com.upsaude.enums.TipoResponsavelEnum;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponsavelLegalRequest {
    private UUID paciente;
    private String nome;
    private String cpf;
    private String telefone;
    private TipoResponsavelEnum tipoResponsavel;
}
