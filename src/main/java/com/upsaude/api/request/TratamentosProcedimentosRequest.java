package com.upsaude.api.request;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de tratamentos procedimentos")
public class TratamentosProcedimentosRequest {
    private UUID tratamento;
    private UUID procedimento;
    private String dente;
    private String faces;
    private Integer quantidade;
    private BigDecimal custo;
    private OffsetDateTime dataExecucao;
    private UUID profissional;
    private String observacoes;
}
