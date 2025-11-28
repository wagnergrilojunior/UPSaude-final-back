package com.upsaude.api.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DispensacoesMedicamentosRequest {

    @NotNull(message = "ID do estabelecimento é obrigatório")
    private UUID estabelecimentoId;

    @NotNull(message = "ID do paciente é obrigatório")
    private UUID pacienteId;

    @NotNull(message = "ID do medicamento é obrigatório")
    private UUID medicamentoId;

    private Integer quantidade;

    private OffsetDateTime dataDispensacao;

    private String observacoes;
}

