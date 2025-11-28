package com.upsaude.api.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LGPDConsentimentoRequest {
    private UUID pacienteId;
    private Boolean autorizacaoUsoDados;
    private Boolean autorizacaoContatoWhatsApp;
    private Boolean autorizacaoContatoEmail;
    private LocalDateTime dataConsentimento;
}

