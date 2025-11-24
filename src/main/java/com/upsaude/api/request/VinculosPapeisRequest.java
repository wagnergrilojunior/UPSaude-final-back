package com.upsaude.api.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VinculosPapeisRequest {
    private UUID estabelecimentoId;
    private UUID departamentoId;
    private UUID papelId;
}

