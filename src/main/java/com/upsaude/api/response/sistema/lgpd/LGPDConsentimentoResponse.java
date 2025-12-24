package com.upsaude.api.response.sistema.lgpd;
import com.upsaude.api.response.paciente.PacienteResponse;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LGPDConsentimentoResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private PacienteResponse paciente;
    private Boolean autorizacaoUsoDados;
    private Boolean autorizacaoContatoWhatsApp;
    private Boolean autorizacaoContatoEmail;
    private LocalDateTime dataConsentimento;
}
