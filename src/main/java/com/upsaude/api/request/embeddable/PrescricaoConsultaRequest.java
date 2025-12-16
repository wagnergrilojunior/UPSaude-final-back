package com.upsaude.api.request.embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de prescricao consulta")
public class PrescricaoConsultaRequest {
    private String medicamentosPrescritos;
    private String orientacoes;
    private String dieta;
    private String atividadeFisica;
    private String repouso;
    private String outrasOrientacoes;
}
