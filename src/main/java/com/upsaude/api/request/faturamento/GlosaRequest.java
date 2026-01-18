package com.upsaude.api.request.faturamento;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
@Schema(description = "Dados de glosa")
public class GlosaRequest {

    @NotNull(message = "Documento é obrigatório")
    private UUID documento;

    private UUID item;

    @NotBlank(message = "Tipo é obrigatório")
    @Size(max = 20, message = "Tipo deve ter no máximo 20 caracteres")
    private String tipo; 

    @NotNull(message = "Valor glosado é obrigatório")
    @Positive(message = "Valor glosado deve ser maior que zero")
    private BigDecimal valorGlosado;

    @Size(max = 50, message = "Motivo código deve ter no máximo 50 caracteres")
    private String motivoCodigo;

    private String motivoDescricao;

    @NotBlank(message = "Status é obrigatório")
    @Size(max = 30, message = "Status deve ter no máximo 30 caracteres")
    private String status; 
}

