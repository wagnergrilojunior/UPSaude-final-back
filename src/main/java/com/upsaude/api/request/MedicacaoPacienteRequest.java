package com.upsaude.api.request;

import com.upsaude.enums.FrequenciaMedicacaoEnum;
import com.upsaude.enums.ViaAdministracaoEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Classe de requisição para criação e atualização de Medicações de Paciente.
 *
 * @author UPSaúde
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicacaoPacienteRequest {

    @NotNull(message = "ID do paciente é obrigatório")
    private UUID pacienteId;

    @NotNull(message = "ID da medicação é obrigatório")
    private UUID medicacaoId;

    private String dose;

    private FrequenciaMedicacaoEnum frequencia;

    private ViaAdministracaoEnum via;

    private UUID cidRelacionadoId;

    private LocalDate dataInicio;

    private LocalDate dataFim;

    private Boolean medicacaoAtiva;

    private String observacoes;
}

