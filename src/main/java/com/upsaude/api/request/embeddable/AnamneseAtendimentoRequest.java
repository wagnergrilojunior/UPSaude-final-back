package com.upsaude.api.request.embeddable;

import com.upsaude.api.request.clinica.atendimento.SinalVitalRequest;

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
@Schema(description = "Dados de anamnese atendimento")
public class AnamneseAtendimentoRequest {
    private String queixaPrincipal;
    private String historiaDoencaAtual;
    private String antecedentesRelevantes;
    private String medicamentosUso;
    private String alergiasConhecidas;
    private String exameFisico;
    private String sinaisVitais;
    private SinalVitalRequest sinalVitalRecord;
    private String observacoesAnamnese;
}
