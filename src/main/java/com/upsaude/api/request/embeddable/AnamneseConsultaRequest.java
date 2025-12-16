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
@Schema(description = "Dados de anamnese consulta")
public class AnamneseConsultaRequest {
    private String queixaPrincipal;
    private String historiaDoencaAtual;
    private String antecedentesPessoais;
    private String antecedentesFamiliares;
    private String medicamentosUso;
    private String alergias;
    private String habitosVida;
    private String exameFisico;
    private String sinaisVitais;
}
