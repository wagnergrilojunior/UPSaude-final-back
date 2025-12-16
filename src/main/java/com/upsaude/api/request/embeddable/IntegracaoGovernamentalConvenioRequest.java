package com.upsaude.api.request.embeddable;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de integracao governamental convênio")
public class IntegracaoGovernamentalConvenioRequest {
    @NotNull(message = "Sincronizar ANS é obrigatório")
    @Builder.Default
    private Boolean sincronizarAns = false;

    private OffsetDateTime ultimaSincronizacaoAns;

    @NotNull(message = "Sincronizar TISS é obrigatório")
    @Builder.Default
    private Boolean sincronizarTiss = false;

    private OffsetDateTime ultimaSincronizacaoTiss;

    @NotNull(message = "Sincronizar SUS é obrigatório")
    @Builder.Default
    private Boolean sincronizarSus = false;

    private OffsetDateTime ultimaSincronizacaoSus;

    @Size(max = 50, message = "Código sistema externo deve ter no máximo 50 caracteres")
    private String codigoSistemaExterno;

    @Size(max = 255, message = "URL API deve ter no máximo 255 caracteres")
    private String urlApi;

    @Size(max = 255, message = "Token API deve ter no máximo 255 caracteres")
    private String tokenApi;

    @Size(max = 255, message = "Chave API deve ter no máximo 255 caracteres")
    private String chaveApi;
}
