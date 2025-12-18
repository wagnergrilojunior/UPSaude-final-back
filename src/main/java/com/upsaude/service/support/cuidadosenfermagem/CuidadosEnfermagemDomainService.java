package com.upsaude.service.support.cuidadosenfermagem;

import com.upsaude.entity.enfermagem.CuidadosEnfermagem;
import org.springframework.stereotype.Service;

@Service
public class CuidadosEnfermagemDomainService {

    public void aplicarDefaults(CuidadosEnfermagem entity) {
        if (entity == null) return;
        if (entity.getActive() == null) {
            entity.setActive(true);
        }
    }
}
