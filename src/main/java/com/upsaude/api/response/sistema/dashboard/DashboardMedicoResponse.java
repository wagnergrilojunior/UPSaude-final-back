package com.upsaude.api.response.sistema.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Response DTO para dashboard de médico.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardMedicoResponse {

    private UUID medicoId;
    private String medicoNome;
    private String medicoCrm;
    private LocalDate dataInicio;
    private LocalDate dataFim;

    // KPIs principais
    private Long totalAtendimentos;
    private Long totalConsultas;
    private Long totalAgendamentos;
    private Long totalPacientes;

    // Evolução temporal
    private List<DashboardTenantResponse.EvolucaoTemporal> evolucaoTemporal;

    // Top procedimentos realizados
    private List<DashboardTenantResponse.ItemTopProcedimento> topProcedimentos;

    // Top CID diagnosticados
    private List<DashboardTenantResponse.ItemTopCid> topCids;

    // Comparação com período anterior
    private DashboardTenantResponse.ComparacaoPeriodo comparacaoPeriodoAnterior;

    // Metas e indicadores
    private Map<String, BigDecimal> indicadores;
}
