package com.upsaude.service.support.controleponto;

import com.upsaude.entity.equipe.ControlePonto;
import org.springframework.stereotype.Service;

@Service
public class ControlePontoDomainService {

    public void aplicarDefaults(ControlePonto entity) {
        if (entity == null) return;
        if (entity.getActive() == null) {
            entity.setActive(true);
        }
    }
}
