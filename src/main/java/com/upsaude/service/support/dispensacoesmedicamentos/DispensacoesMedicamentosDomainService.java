package com.upsaude.service.support.dispensacoesmedicamentos;

import com.upsaude.entity.medicacao.DispensacoesMedicamentos;
import org.springframework.stereotype.Service;

@Service
public class DispensacoesMedicamentosDomainService {

    public void aplicarDefaults(DispensacoesMedicamentos entity) {
        if (entity == null) return;
        if (entity.getActive() == null) {
            entity.setActive(true);
        }
    }
}
