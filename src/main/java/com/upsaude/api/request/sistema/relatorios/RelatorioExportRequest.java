package com.upsaude.api.request.sistema.relatorios;

import com.upsaude.api.request.sistema.relatorios.RelatorioComparativoRequest;
import com.upsaude.api.request.sistema.relatorios.RelatorioEstatisticasRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Request DTO para exportação de relatórios.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados para exportação de relatório")
public class RelatorioExportRequest {

    @Schema(description = "Tipo de relatório a ser exportado", required = true)
    private TipoRelatorio tipoRelatorio;

    @Schema(description = "Formato de exportação", required = true)
    private FormatoExportacao formatoExportacao;

    @Schema(description = "Dados do relatório de estatísticas (se tipoRelatorio = ESTATISTICAS)")
    private RelatorioEstatisticasRequest relatorioEstatisticas;

    @Schema(description = "Dados do relatório comparativo (se tipoRelatorio = COMPARATIVO)")
    private RelatorioComparativoRequest relatorioComparativo;

    @Schema(description = "ID do estabelecimento para relatórios específicos")
    private UUID estabelecimentoId;

    @Schema(description = "ID do médico para relatórios específicos")
    private UUID medicoId;

    public enum TipoRelatorio {
        ESTATISTICAS,
        COMPARATIVO,
        DASHBOARD_TENANT,
        DASHBOARD_ESTABELECIMENTO,
        DASHBOARD_MEDICO,
        SIA_KPI_GERAL,
        SIA_KPI_ESTABELECIMENTO,
        SIA_RELATORIO_PRODUCAO_MENSAL,
        SIA_RELATORIO_TOP_PROCEDIMENTOS,
        SIA_RELATORIO_TOP_CID
    }

    public enum FormatoExportacao {
        PDF,
        EXCEL,
        CSV
    }
}
