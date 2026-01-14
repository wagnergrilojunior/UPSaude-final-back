package com.upsaude.service.api.support.relatorios;

import java.util.UUID;

/**
 * Helper service para validação de estabelecimentos em relatórios.
 */
public interface EstabelecimentoFilterHelper {

    /**
     * Valida se um estabelecimento pertence ao tenant.
     * 
     * @param estabelecimentoId ID do estabelecimento
     * @param tenantId ID do tenant
     * @return true se o estabelecimento pertence ao tenant, false caso contrário
     */
    boolean validarEstabelecimentoPertenceAoTenant(UUID estabelecimentoId, UUID tenantId);
}
