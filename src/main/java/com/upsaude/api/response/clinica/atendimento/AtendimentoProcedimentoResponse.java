package com.upsaude.api.response.clinica.atendimento;

import com.upsaude.api.response.referencia.sigtap.ProcedimentoSigtapSimplificadoResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtendimentoProcedimentoResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;

    private AtendimentoSimplificadoResponse atendimento;
    private ProcedimentoSigtapSimplificadoResponse sigtapProcedimento;
    private Integer quantidade;
    private BigDecimal valorUnitario;
    private BigDecimal valorTotal;

    private UUID financiamentoId;
    private UUID rubricaId;
    private String modalidadeFinanceira;

    private String cboCodigo;
    private String cidPrincipalCodigo;
    private String caraterAtendimento;
    private String cnes;
    private String observacoes;
}

