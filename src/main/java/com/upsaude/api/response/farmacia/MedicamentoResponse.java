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
public class MedicamentoResponse {
    private UUID id;
    private String codigoFhir;
    private String nome;
    private String apresentacao;
    private String concentracao;
    private String formaFarmaceutica;
    private PrincipioAtivoResponse principioAtivo;
    private String registroAnvisa;
    private String codigoEan;
    private String fabricante;
    private Boolean ativo;
    private OffsetDateTime dataSincronizacao;
}
