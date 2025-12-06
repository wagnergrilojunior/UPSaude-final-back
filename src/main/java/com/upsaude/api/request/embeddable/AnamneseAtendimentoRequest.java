package com.upsaude.api.request.embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnamneseAtendimentoRequest {
    private String queixaPrincipal;
    private String historiaDoencaAtual;
    private String antecedentesRelevantes;
    private String medicamentosUso;
    private String alergiasConhecidas;
    private String exameFisico;
    private String sinaisVitais;
    private String observacoesAnamnese;
}
