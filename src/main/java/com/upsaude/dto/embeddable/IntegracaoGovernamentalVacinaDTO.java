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
public class IntegracaoGovernamentalVacinaDTO {
    private String codigoSiPni;
    private String codigoESus;
    private Boolean sincronizarPni;
    private OffsetDateTime ultimaSincronizacaoPni;
}
