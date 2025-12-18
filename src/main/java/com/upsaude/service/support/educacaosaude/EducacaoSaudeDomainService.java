package com.upsaude.service.support.educacaosaude;

import com.upsaude.entity.educacao.EducacaoSaude;
import org.springframework.stereotype.Service;

@Service
public class EducacaoSaudeDomainService {

    public void aplicarDefaults(EducacaoSaude entity) {
        if (entity == null) return;
        if (entity.getActive() == null) {
            entity.setActive(true);
        }
        if (entity.getAtividadeRealizada() == null) {
            entity.setAtividadeRealizada(false);
        }
    }
}
