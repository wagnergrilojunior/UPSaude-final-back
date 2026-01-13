package com.upsaude.api.request.clinica.atendimento;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Procedimento executado no atendimento")
public class AtendimentoProcedimentoRequest {

    @NotNull(message = "Atendimento é obrigatório")
    private UUID atendimento;

    private UUID sigtapProcedimento;

    @NotNull(message = "Quantidade é obrigatória")
    @Min(value = 1, message = "Quantidade deve ser no mínimo 1")
    private Integer quantidade;

    private BigDecimal valorUnitario;
    private BigDecimal valorTotal;

    private UUID financiamentoId;
    private UUID rubricaId;

    @Size(max = 50, message = "Modalidade financeira deve ter no máximo 50 caracteres")
    private String modalidadeFinanceira;

    @Size(max = 6, message = "CBO deve ter no máximo 6 caracteres")
    private String cboCodigo;

    @Size(max = 10, message = "CID principal deve ter no máximo 10 caracteres")
    private String cidPrincipalCodigo;

    @Size(max = 2, message = "Caráter do atendimento deve ter no máximo 2 caracteres")
    private String caraterAtendimento;

    @Size(max = 7, message = "CNES deve ter no máximo 7 caracteres")
    private String cnes;

    private String observacoes;
}

