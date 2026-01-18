package com.upsaude.service.api.support.relatorios;

import java.util.List;
import java.util.UUID;


public interface TenantFilterHelper {

    
    List<String> obterCnesDoTenant(UUID tenantId);

    
    boolean validarCnesPertenceAoTenant(String cnes, UUID tenantId);

    
    List<UUID> obterEstabelecimentosIdsDoTenant(UUID tenantId);
}
