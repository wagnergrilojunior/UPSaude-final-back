package com.upsaude.api.request.sistema;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Dados de consentimento LGPD")
public class LGPDConsentimentoRequest {
    private UUID paciente;
    private Boolean autorizacaoUsoDados;
    private Boolean autorizacaoContatoWhatsApp;
    private Boolean autorizacaoContatoEmail;
    private LocalDateTime dataConsentimento;
}
