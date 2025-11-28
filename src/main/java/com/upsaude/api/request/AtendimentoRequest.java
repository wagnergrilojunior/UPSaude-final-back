package com.upsaude.api.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Classe de requisição para criação e atualização de Atendimento.
 *
 * @author UPSaúde
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtendimentoRequest {

    @NotNull(message = "ID do paciente é obrigatório")
    private UUID pacienteId;

    @NotNull(message = "ID do profissional de saúde é obrigatório")
    private UUID profissionalId;

    @NotNull(message = "Data e hora do atendimento são obrigatórias")
    private OffsetDateTime dataHora;

    private String tipoAtendimento;

    private String motivo;

    private UUID cidPrincipalId;

    private String anotacoes;
}

