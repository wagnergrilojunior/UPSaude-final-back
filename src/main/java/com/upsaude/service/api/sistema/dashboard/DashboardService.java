package com.upsaude.service.api.sistema.dashboard;

import com.upsaude.api.response.sistema.dashboard.DashboardEstabelecimentoResponse;
import com.upsaude.api.response.sistema.dashboard.DashboardMedicoResponse;
import com.upsaude.api.response.sistema.dashboard.DashboardTenantResponse;

import java.time.LocalDate;
import java.util.UUID;


public interface DashboardService {

    
    DashboardTenantResponse dashboardTenant(UUID tenantId, LocalDate dataInicio, LocalDate dataFim);

    
    DashboardEstabelecimentoResponse dashboardEstabelecimento(UUID estabelecimentoId, LocalDate dataInicio, LocalDate dataFim);

    
    DashboardMedicoResponse dashboardMedico(UUID medicoId, LocalDate dataInicio, LocalDate dataFim);
}
