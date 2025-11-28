package com.upsaude.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * DTO (Data Transfer Object) para Atendimento.
 *
 * @author UPSaúde
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtendimentoDTO {
    private UUID id;
    private UUID pacienteId;
    private UUID profissionalId;
    private OffsetDateTime dataHora;
    private String tipoAtendimento;
    private String motivo;
    private UUID cidPrincipalId;
    private String anotacoes;
    private Boolean active;
}

