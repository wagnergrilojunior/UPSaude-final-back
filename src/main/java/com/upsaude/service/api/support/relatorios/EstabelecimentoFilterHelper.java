package com.upsaude.service.api.support.relatorios;

import java.util.UUID;


public interface EstabelecimentoFilterHelper {

    
    boolean validarEstabelecimentoPertenceAoTenant(UUID estabelecimentoId, UUID tenantId);
}
