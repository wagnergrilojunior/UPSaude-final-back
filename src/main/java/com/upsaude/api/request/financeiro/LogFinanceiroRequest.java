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
@Schema(description = "Dados de log financeiro (uso restrito)")
public class LogFinanceiroRequest {

    @NotBlank(message = "Tipo da entidade é obrigatório")
    @Size(max = 50, message = "Tipo da entidade deve ter no máximo 50 caracteres")
    private String entidadeTipo;

    @NotNull(message = "ID da entidade é obrigatório")
    private UUID entidadeId;

    @NotBlank(message = "Ação é obrigatória")
    @Size(max = 50, message = "Ação deve ter no máximo 50 caracteres")
    private String acao;

    private UUID usuarioId;
    private String correlationId;
    private String payloadAntes;
    private String payloadDepois;
    private String ip;
    private String userAgent;
}

