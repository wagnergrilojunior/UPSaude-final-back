package com.upsaude.api.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LGPDConsentimentoRequest {
    private UUID paciente;
    private Boolean autorizacaoUsoDados;
    private Boolean autorizacaoContatoWhatsApp;
    private Boolean autorizacaoContatoEmail;
    private LocalDateTime dataConsentimento;
}
