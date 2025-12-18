package com.upsaude.service.support.consultas;

import com.upsaude.entity.clinica.atendimento.Consultas;
import org.springframework.stereotype.Service;

@Service
public class ConsultasDomainService {

    public void aplicarDefaults(Consultas entity) {
        if (entity == null) return;
        if (entity.getActive() == null) {
            entity.setActive(true);
        }
    }
}
