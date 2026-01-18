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

    
    private Long totalAtendimentos;
    private Long totalConsultas;
    private Long totalAgendamentos;
    private Long totalPacientes;

    
    private List<DashboardTenantResponse.EvolucaoTemporal> evolucaoTemporal;

    
    private List<DashboardTenantResponse.ItemTopProcedimento> topProcedimentos;

    
    private List<DashboardTenantResponse.ItemTopCid> topCids;

    
    private DashboardTenantResponse.ComparacaoPeriodo comparacaoPeriodoAnterior;

    
    private Map<String, BigDecimal> indicadores;
}
