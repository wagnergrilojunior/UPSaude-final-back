package com.upsaude.api.request.sistema.relatorios;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Request DTO para relatórios comparativos.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados para geração de relatório comparativo")
public class RelatorioComparativoRequest {

    @Schema(description = "Data de início do período atual", required = true)
    private LocalDate periodoAtualInicio;

    @Schema(description = "Data de fim do período atual", required = true)
    private LocalDate periodoAtualFim;

    @Schema(description = "Data de início do período anterior. Se não informado, será calculado automaticamente")
    private LocalDate periodoAnteriorInicio;

    @Schema(description = "Data de fim do período anterior. Se não informado, será calculado automaticamente")
    private LocalDate periodoAnteriorFim;

    @Schema(description = "ID do estabelecimento para comparação (opcional)")
    private UUID estabelecimentoId;

    @Schema(description = "ID do médico para comparação (opcional)")
    private UUID medicoId;

    @Schema(description = "ID da especialidade para comparação (opcional)")
    private UUID especialidadeId;

    @Schema(description = "Tipo de comparação", example = "PERIODO_TEMPORAL")
    private TipoComparacao tipoComparacao;

    public enum TipoComparacao {
        PERIODO_TEMPORAL,
        ESTABELECIMENTO,
        MEDICO,
        ESPECIALIDADE
    }
}
