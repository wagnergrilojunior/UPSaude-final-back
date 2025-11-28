package com.upsaude.api.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntegracaoGovRequest {
    private UUID pacienteId;
    private UUID uuidRnds;
    private String idIntegracaoGov;
    private LocalDateTime dataSincronizacaoGov;
    private String ineEquipe;
    private String microarea;
    private String cnesEstabelecimentoOrigem;
    private String origemCadastro;
}

