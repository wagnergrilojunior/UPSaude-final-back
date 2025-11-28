package com.upsaude.api.response;

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
public class DispensacoesMedicamentosResponse {
    private UUID id;
    private UUID estabelecimentoId;
    private String estabelecimentoNome;
    private UUID pacienteId;
    private String pacienteNome;
    private UUID medicamentoId;
    private String medicamentoNome;
    private Integer quantidade;
    private OffsetDateTime dataDispensacao;
    private String observacoes;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
}

