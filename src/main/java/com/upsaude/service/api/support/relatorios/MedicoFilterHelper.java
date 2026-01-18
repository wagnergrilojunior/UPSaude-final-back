package com.upsaude.service.api.support.relatorios;

import java.util.UUID;


public interface MedicoFilterHelper {

    
    boolean validarMedicoPertenceAoTenant(UUID medicoId, UUID tenantId);
}
