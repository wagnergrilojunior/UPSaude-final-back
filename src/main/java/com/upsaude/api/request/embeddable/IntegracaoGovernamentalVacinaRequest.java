package com.upsaude.api.request.embeddable;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class IntegracaoGovernamentalVacinaRequest {
    @Size(max = 50, message = "Código SI PNI deve ter no máximo 50 caracteres")
    private String codigoSiPni;
    
    @Size(max = 50, message = "Código e-SUS deve ter no máximo 50 caracteres")
    private String codigoESus;
    
    @NotNull(message = "Sincronizar PNI é obrigatório")
    @Builder.Default
    private Boolean sincronizarPni = false;
    
    private OffsetDateTime ultimaSincronizacaoPni;
}
