package com.upsaude.api.response.embeddable;

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
public class ClassificacaoRiscoAtendimentoResponse {
    private String classificacaoRisco;
    private String prioridade;
    private String gravidade;
    private Boolean necessitaObservacao;
    private Boolean necessitaInternacao;
}
