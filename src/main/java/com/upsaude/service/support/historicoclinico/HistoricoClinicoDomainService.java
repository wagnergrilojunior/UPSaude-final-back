package com.upsaude.service.support.historicoclinico;

import com.upsaude.entity.prontuario.HistoricoClinico;
import org.springframework.stereotype.Service;

@Service
public class HistoricoClinicoDomainService {

    public void aplicarDefaults(HistoricoClinico entity) {
        if (entity.getVisivelParaPaciente() == null) {
            entity.setVisivelParaPaciente(false);
        }
        if (entity.getCompartilhadoOutrosEstabelecimentos() == null) {
            entity.setCompartilhadoOutrosEstabelecimentos(false);
        }
    }
}
