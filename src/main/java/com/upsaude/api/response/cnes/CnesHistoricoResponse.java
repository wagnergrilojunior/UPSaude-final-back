package com.upsaude.api.response.cnes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CnesHistoricoResponse {
    private UUID id;
    private UUID estabelecimentoId;
    private String competencia;
    private String dadosJsonb;
    private OffsetDateTime dataSincronizacao;
    private OffsetDateTime createdAt;
}

