package com.upsaude.api.request.financeiro;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Dados de lançamento financeiro")
public class LancamentoFinanceiroRequest {

    @NotNull(message = "Competência financeira é obrigatória")
    private UUID competencia;

    private UUID documentoFaturamento;
    private UUID tituloReceber;
    private UUID tituloPagar;
    private UUID conciliacao;

    @NotBlank(message = "Tipo de origem é obrigatório")
    @Size(max = 50, message = "Tipo de origem deve ter no máximo 50 caracteres")
    private String origemTipo; // AGENDAMENTO | ATENDIMENTO | DOCUMENTO_FATURAMENTO | TITULO | CONCILIACAO | MANUAL | AJUSTE_SISTEMA

    private UUID origemId;

    @NotBlank(message = "Status é obrigatório")
    @Size(max = 30, message = "Status deve ter no máximo 30 caracteres")
    private String status; // PREVISTO | REALIZADO | ESTORNADO | AJUSTADO | CANCELADO_POR_REVERSAO

    @NotNull(message = "Data do evento é obrigatória")
    private OffsetDateTime dataEvento;

    @Size(max = 500, message = "Descrição deve ter no máximo 500 caracteres")
    private String descricao;

    private String motivoEstorno;
    private String referenciaEstornoTipo;
    private UUID referenciaEstornoId;

    private UUID prestadorId;
    private String prestadorTipo;

    private UUID grupoLancamento;

    @Valid
    private List<LancamentoFinanceiroItemRequest> itens;
}

