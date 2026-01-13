package com.upsaude.api.request.faturamento;

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

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Dados de documento de faturamento")
public class DocumentoFaturamentoRequest {

    @NotNull(message = "Competência financeira é obrigatória")
    private UUID competencia;

    private UUID convenio;
    private UUID agendamento;
    private UUID atendimento;
    private UUID guiaAmbulatorial;

    @NotBlank(message = "Tipo é obrigatório")
    @Size(max = 50, message = "Tipo deve ter no máximo 50 caracteres")
    private String tipo; // BPA | APAC | TISS | FATURA | GUIA_AMBULATORIAL | NOTA

    @NotBlank(message = "Número é obrigatório")
    @Size(max = 100, message = "Número deve ter no máximo 100 caracteres")
    private String numero;

    @Size(max = 20, message = "Série deve ter no máximo 20 caracteres")
    private String serie;

    @NotBlank(message = "Status é obrigatório")
    @Size(max = 30, message = "Status deve ter no máximo 30 caracteres")
    private String status;

    @Size(max = 30, message = "Tipo do pagador deve ter no máximo 30 caracteres")
    private String pagadorTipo; // SUS | CONVENIO | PARTICULAR

    private String payloadLayout;

    @Valid
    private List<DocumentoFaturamentoItemRequest> itens;
}

