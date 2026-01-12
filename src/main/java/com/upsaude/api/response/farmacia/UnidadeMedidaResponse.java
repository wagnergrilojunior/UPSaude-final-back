package com.upsaude.api.response.farmacia;

import java.time.OffsetDateTime;
import java.util.UUID;
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
public class UnidadeMedidaResponse {
    private UUID id;
    private String codigoFhir;
    private String nome;
    private String sigla;
    private Boolean ativo;
    private OffsetDateTime dataSincronizacao;
}
