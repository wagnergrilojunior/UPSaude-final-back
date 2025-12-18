package com.upsaude.api.response.sistema;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioEstatisticasResponse {
    private LocalDate dataInicio;
    private LocalDate dataFim;
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
