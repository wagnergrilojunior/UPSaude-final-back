package com.upsaude.dto.vacinacao;

import java.time.OffsetDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReacaoRequest {

    @NotBlank(message = "Tipo de reação é obrigatório")
    private String tipoReacao;

    @NotBlank(message = "Gravidade é obrigatória")
    private String gravidade;

    @NotNull(message = "Data de ocorrência é obrigatória")
    private OffsetDateTime dataOcorrencia;

    private String descricao;
    private String tratamento;
    private String evolucao;
}
