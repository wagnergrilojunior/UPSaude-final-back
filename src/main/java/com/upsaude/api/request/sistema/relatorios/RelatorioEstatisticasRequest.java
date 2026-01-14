package com.upsaude.api.request.sistema.relatorios;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de relatorio estatisticas")
public class RelatorioEstatisticasRequest {
    private LocalDate dataInicio;
    private LocalDate dataFim;
    
    @Schema(description = "ID do estabelecimento para filtrar (opcional)")
    private UUID estabelecimentoId;
    
    @Schema(description = "ID do médico para filtrar (opcional)")
    private UUID medicoId;
    
    @Schema(description = "ID da especialidade para filtrar (opcional)")
    private UUID especialidadeId;
    
    // Campos legados mantidos para compatibilidade
    private Long totalAtendimentos;
    private Long totalConsultas;
    private Long totalExames;
    private Long totalProcedimentos;
    private Long totalAgendamentos;
    private Long totalPacientes;
    private Long totalVisitasDomiciliares;
    private Map<String, Long> atendimentosPorTipo;
    private Map<String, Long> atendimentosPorEspecialidade;
    private Map<String, Long> examesPorTipo;
    private Map<String, Long> procedimentosPorTipo;
    private Map<String, Long> atendimentosPorProfissional;
    private Map<String, BigDecimal> indicadoresSaude;
}
