package com.upsaude.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Classe de resposta para Atendimento.
 *
 * @author UPSaúde
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtendimentoResponse {
    private UUID id;
    private UUID pacienteId;
    private String pacienteNome;
    private UUID profissionalId;
    private String profissionalNome;
    private OffsetDateTime dataHora;
    private String tipoAtendimento;
    private String motivo;
    private UUID cidPrincipalId;
    private String cidPrincipalCodigo;
    private String cidPrincipalDescricao;
    private String anotacoes;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
}

