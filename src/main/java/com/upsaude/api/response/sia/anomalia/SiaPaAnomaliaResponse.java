package com.upsaude.api.response.sia.anomalia;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SiaPaAnomaliaResponse {

    private UUID id;
    private OffsetDateTime criadoEm;

    private String competencia;
    private String uf;

    private String tipoAnomalia;
    private String severidade;
    private String chave;
    private UUID registroId;

    private String descricao;

    private BigDecimal valorAtual;
    private BigDecimal valorReferencia;
    private BigDecimal delta;
    private BigDecimal deltaPct;

    private String dados;
}

