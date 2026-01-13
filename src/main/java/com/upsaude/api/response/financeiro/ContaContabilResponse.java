package com.upsaude.api.response.financeiro;

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
public class ContaContabilResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;

    private PlanoContasSimplificadoResponse planoContas;
    private ContaContabilSimplificadaResponse contaPai;
    private String codigo;
    private String nome;
    private String natureza;
    private Boolean aceitaLancamento;
    private Integer nivel;
    private Integer ordem;
}

