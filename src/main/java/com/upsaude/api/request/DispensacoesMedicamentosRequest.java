package com.upsaude.api.request;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de dispensacoes medicamentos")
public class DispensacoesMedicamentosRequest {
    private UUID paciente;
    private UUID medicacao;
    private Integer quantidade;
    private OffsetDateTime dataDispensacao;
    private String observacoes;
}
