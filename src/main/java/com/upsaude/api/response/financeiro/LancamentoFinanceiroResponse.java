package com.upsaude.api.response.financeiro;

import com.upsaude.api.response.faturamento.DocumentoFaturamentoSimplificadoResponse;
import com.upsaude.api.response.sistema.usuario.UsuarioSistemaSimplificadoResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LancamentoFinanceiroResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;

    private CompetenciaFinanceiraResponse competencia;
    private DocumentoFaturamentoSimplificadoResponse documentoFaturamento;
    private TituloReceberSimplificadoResponse tituloReceber;
    private TituloPagarSimplificadoResponse tituloPagar;

    private String origemTipo;
    private UUID origemId;
    private String status;
    private OffsetDateTime dataEvento;
    private String descricao;

    private Boolean travado;
    private OffsetDateTime travadoEm;
    private UsuarioSistemaSimplificadoResponse travadoPor;
    private UUID grupoLancamento;

    private String motivoEstorno;
    private String referenciaEstornoTipo;
    private UUID referenciaEstornoId;

    private UUID prestadorId;
    private String prestadorTipo;

    @Builder.Default
    private List<LancamentoFinanceiroItemResponse> itens = new ArrayList<>();
}

