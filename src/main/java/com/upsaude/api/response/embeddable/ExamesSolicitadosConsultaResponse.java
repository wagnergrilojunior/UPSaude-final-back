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
public class ExamesSolicitadosConsultaResponse {
    private String examesSolicitados;
    private String examesLaboratoriais;
    private String examesImagem;
    private String examesOutros;
    private Boolean urgenciaExames;
}
