package com.upsaude.api.request.embeddable;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de esquema vacinal")
public class EsquemaVacinalRequest {
    @NotNull(message = "Número de doses é obrigatório")
    @Builder.Default
    private Integer numeroDoses = 1;

    private Integer intervaloDosesDias;

    @NotNull(message = "Dose reforço é obrigatório")
    @Builder.Default
    private Boolean doseReforco = false;

    private Integer intervaloReforcoAnos;

    private BigDecimal doseMl;

    @Size(max = 100, message = "Local aplicação padrão deve ter no máximo 100 caracteres")
    private String localAplicacaoPadrao;
}
