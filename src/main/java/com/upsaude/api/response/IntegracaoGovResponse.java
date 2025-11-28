package com.upsaude.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntegracaoGovResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private UUID pacienteId;
    private UUID uuidRnds;
    private String idIntegracaoGov;
    private LocalDateTime dataSincronizacaoGov;
    private String ineEquipe;
    private String microarea;
    private String cnesEstabelecimentoOrigem;
    private String origemCadastro;
}

