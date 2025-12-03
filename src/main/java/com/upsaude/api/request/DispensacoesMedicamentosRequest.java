package com.upsaude.api.request;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DispensacoesMedicamentosRequest {
    private UUID paciente;
    private UUID medicacao;
    private Integer quantidade;
    private OffsetDateTime dataDispensacao;
    private String observacoes;
}
