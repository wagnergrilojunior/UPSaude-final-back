package com.upsaude.api.response.farmacia;

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
public class FarmaciaResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    
    private String nome;
    private String codigoCnes;
    private String codigoFarmaciaInterno;
    private String responsavelTecnico;
    private String crfResponsavel;
    private String telefone;
    private String email;
    private String observacoes;
    
    private UUID tenantId;
    private UUID estabelecimentoId;
}

