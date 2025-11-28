package com.upsaude.dto;

import lombok.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DispensacoesMedicamentosDTO {
    private UUID id;
    private UUID estabelecimentoId;
    private UUID pacienteId;
    private UUID medicamentoId;
    private Integer quantidade;
    private OffsetDateTime dataDispensacao;
    private String observacoes;
    private Boolean active;
}

