package com.upsaude.api.response;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TratamentosProcedimentosResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private TratamentosOdontologicosResponse tratamento;
    private ProcedimentosOdontologicosResponse procedimento;
    private String dente;
    private String faces;
    private Integer quantidade;
    private BigDecimal custo;
    private OffsetDateTime dataExecucao;
    private ProfissionaisSaudeResponse profissional;
    private String observacoes;
}
