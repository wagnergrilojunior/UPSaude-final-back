package com.upsaude.service.impl.api.support.relatorios;

import com.upsaude.repository.profissional.MedicosRepository;
import com.upsaude.service.api.support.relatorios.MedicoFilterHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MedicoFilterHelperImpl implements MedicoFilterHelper {

    private final MedicosRepository medicosRepository;

    @Override
    public boolean validarMedicoPertenceAoTenant(UUID medicoId, UUID tenantId) {
        log.debug("Validando se médico {} pertence ao tenant {}", medicoId, tenantId);
        
        if (medicoId == null || tenantId == null) {
            return false;
        }
        
        boolean pertence = medicosRepository.findByIdAndTenant(medicoId, tenantId)
                .isPresent();
        
        log.debug("Médico {} {} ao tenant {}", medicoId, pertence ? "pertence" : "não pertence", tenantId);
        return pertence;
    }
}
