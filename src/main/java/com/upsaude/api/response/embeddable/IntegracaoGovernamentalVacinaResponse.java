package com.upsaude.api.response.embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntegracaoGovernamentalVacinaResponse {
    private String codigoSiPni;
    private String codigoESus;
    private Boolean sincronizarPni;
    private OffsetDateTime ultimaSincronizacaoPni;
}
