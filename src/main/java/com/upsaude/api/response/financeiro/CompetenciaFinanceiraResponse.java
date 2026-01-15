package com.upsaude.api.response.financeiro;

import com.upsaude.api.response.faturamento.DocumentoFaturamentoSimplificadoResponse;
import com.upsaude.api.response.sistema.usuario.UsuarioSistemaSimplificadoResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompetenciaFinanceiraResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;

    private String codigo;
private String tipo;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String descricao;

private String status;
    private OffsetDateTime fechadaEm;
    private UsuarioSistemaSimplificadoResponse fechadaPor;
    private String motivoFechamento;
    private String snapshotHash;

    private DocumentoFaturamentoSimplificadoResponse documentoBpaFechamento;
    private String hashMovimentacoes;
    private String hashBpa;
    private Boolean validacaoIntegridade;
}

