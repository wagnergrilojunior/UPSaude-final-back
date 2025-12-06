package com.upsaude.dto.embeddable;

import java.time.OffsetDateTime;
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
public class IntegracaoGovernamentalConvenioDTO {
    private Boolean sincronizarAns;
    private OffsetDateTime ultimaSincronizacaoAns;
    private Boolean sincronizarTiss;
    private OffsetDateTime ultimaSincronizacaoTiss;
    private Boolean sincronizarSus;
    private OffsetDateTime ultimaSincronizacaoSus;
    private String codigoSistemaExterno;
    private String urlApi;
    private String tokenApi;
    private String chaveApi;
}
