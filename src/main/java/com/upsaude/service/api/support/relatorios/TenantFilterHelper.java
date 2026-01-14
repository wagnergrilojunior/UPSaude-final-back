package com.upsaude.service.api.support.relatorios;

import java.util.List;
import java.util.UUID;

/**
 * Helper service para filtragem de dados por tenant em relatórios.
 */
public interface TenantFilterHelper {

    /**
     * Retorna lista de CNES dos estabelecimentos do tenant.
     * 
     * @param tenantId ID do tenant
     * @return Lista de CNES (códigos de 7 dígitos)
     */
    List<String> obterCnesDoTenant(UUID tenantId);

    /**
     * Valida se um CNES pertence ao tenant.
     * 
     * @param cnes Código CNES (7 dígitos)
     * @param tenantId ID do tenant
     * @return true se o CNES pertence ao tenant, false caso contrário
     */
    boolean validarCnesPertenceAoTenant(String cnes, UUID tenantId);

    /**
     * Retorna lista de IDs de estabelecimentos do tenant.
     * 
     * @param tenantId ID do tenant
     * @return Lista de UUIDs dos estabelecimentos
     */
    List<UUID> obterEstabelecimentosIdsDoTenant(UUID tenantId);
}
