package com.upsaude.api.response.farmacia;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
public class DispensacaoResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private UUID pacienteId;
    private String pacienteNome;
    private UUID farmaciaId;
    private String farmaciaNome;
    private UUID receitaId;
    private String numeroReceita;
    private UUID profissionalSaudeId;
    private String profissionalNome;
    private String numeroDispensacao;
    private LocalDateTime dataDispensacao;
    private String tipoDispensacao;
    private String observacoes;
    
    @Builder.Default
    private List<DispensacaoItemResponse> itens = new ArrayList<>();
}

