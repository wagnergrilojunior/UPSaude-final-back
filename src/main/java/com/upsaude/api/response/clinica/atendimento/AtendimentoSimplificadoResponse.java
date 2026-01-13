package com.upsaude.api.response.clinica.atendimento;

import com.upsaude.enums.StatusAtendimentoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtendimentoSimplificadoResponse {
    private UUID id;
    private OffsetDateTime dataHora;
    private StatusAtendimentoEnum statusAtendimento;
}

