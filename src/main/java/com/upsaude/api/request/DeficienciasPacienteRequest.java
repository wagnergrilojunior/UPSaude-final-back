package com.upsaude.api.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Classe de requisição para criação e atualização de Deficiências de Paciente.
 *
 * @author UPSaúde
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeficienciasPacienteRequest {

    @NotNull(message = "ID do paciente é obrigatório")
    private UUID pacienteId;

    @NotNull(message = "ID da deficiência é obrigatório")
    private UUID deficienciaId;

    private Boolean possuiLaudo;

    private LocalDate dataDiagnostico;

    private String observacoes;
}

