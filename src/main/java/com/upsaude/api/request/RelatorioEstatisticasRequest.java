package com.upsaude.api.request;

import lombok.*;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioEstatisticasRequest {
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private UUID estabelecimentoId;
    private UUID profissionalId;
    private UUID especialidadeId;
}

