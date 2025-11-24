package com.upsaude.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VacinacoesResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private UUID estabelecimentoId;
    private UUID pacienteId;
    private UUID vacinaId;
    private UUID fabricanteId;
    private String lote;
    private Integer numeroDose;
    private OffsetDateTime dataAplicacao;
    private String localAplicacao;
    private UUID profissionalAplicador;
    private String reacaoAdversa;
    private String observacoes;
}

