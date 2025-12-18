package com.upsaude.service.support.escalatrabalho;

import com.upsaude.entity.equipe.EscalaTrabalho;
import org.springframework.stereotype.Service;

@Service
public class EscalaTrabalhoDomainService {

    public void aplicarDefaults(EscalaTrabalho entity) {
        if (entity == null) return;
        if (entity.getActive() == null) {
            entity.setActive(true);
        }
    }
}
