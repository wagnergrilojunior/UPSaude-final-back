package com.upsaude.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TratamentosProcedimentosDTO {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private TratamentosOdontologicosDTO tratamento;
    private ProcedimentosOdontologicosDTO procedimento;
    private String dente;
    private String faces;
    private Integer quantidade;
    private BigDecimal custo;
    private OffsetDateTime dataExecucao;
    private ProfissionaisSaudeDTO profissional;
    private String observacoes;
}
