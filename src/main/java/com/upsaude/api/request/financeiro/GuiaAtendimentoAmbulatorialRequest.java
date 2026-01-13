package com.upsaude.api.request.financeiro;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Dados de Guia de Atendimento Ambulatorial (GAA)")
public class GuiaAtendimentoAmbulatorialRequest {

    @NotNull(message = "Competência financeira é obrigatória")
    private UUID competencia;

    private UUID agendamento;
    private UUID atendimento;

    @NotNull(message = "Paciente é obrigatório")
    private UUID paciente;

    private UUID estabelecimento;
    private UUID documentoFaturamento;

    @NotBlank(message = "Número é obrigatório")
    @Size(max = 100, message = "Número deve ter no máximo 100 caracteres")
    private String numero;

    @NotBlank(message = "Status é obrigatório")
    @Size(max = 30, message = "Status deve ter no máximo 30 caracteres")
    private String status; // RASCUNHO | EMITIDA | CANCELADA | INTEGRADA_BPA

    private String observacoes;
}

