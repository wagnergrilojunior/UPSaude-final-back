package com.upsaude.service.api.sistema.dashboard;

import com.upsaude.api.response.sistema.dashboard.DashboardEstabelecimentoResponse;
import com.upsaude.api.response.sistema.dashboard.DashboardMedicoResponse;
import com.upsaude.api.response.sistema.dashboard.DashboardTenantResponse;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Service para geração de dashboards contextuais.
 */
public interface DashboardService {

    /**
     * Gera dashboard consolidado do tenant (prefeitura).
     * 
     * @param tenantId ID do tenant
     * @param dataInicio Data de início do período
     * @param dataFim Data de fim do período
     * @return Dashboard com dados agregados do tenant
     */
    DashboardTenantResponse dashboardTenant(UUID tenantId, LocalDate dataInicio, LocalDate dataFim);

    /**
     * Gera dashboard de uma unidade de saúde (estabelecimento).
     * 
     * @param estabelecimentoId ID do estabelecimento
     * @param dataInicio Data de início do período
     * @param dataFim Data de fim do período
     * @return Dashboard com dados agregados do estabelecimento
     */
    DashboardEstabelecimentoResponse dashboardEstabelecimento(UUID estabelecimentoId, LocalDate dataInicio, LocalDate dataFim);

    /**
     * Gera dashboard de um médico/profissional.
     * 
     * @param medicoId ID do médico
     * @param dataInicio Data de início do período
     * @param dataFim Data de fim do período
     * @return Dashboard com dados agregados do médico
     */
    DashboardMedicoResponse dashboardMedico(UUID medicoId, LocalDate dataInicio, LocalDate dataFim);
}
