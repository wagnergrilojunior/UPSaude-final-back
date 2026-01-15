package com.upsaude.service.api.support.relatorios;

import java.util.UUID;

/**
 * Helper service para validação de médicos em relatórios.
 */
public interface MedicoFilterHelper {

    /**
     * Valida se um médico pertence ao tenant.
     * 
     * @param medicoId ID do médico
     * @param tenantId ID do tenant
     * @return true se o médico pertence ao tenant, false caso contrário
     */
    boolean validarMedicoPertenceAoTenant(UUID medicoId, UUID tenantId);
}
