package com.upsaude.service.support.equipamentosestabelecimento;

import com.upsaude.entity.EquipamentosEstabelecimento;
import org.springframework.stereotype.Service;

@Service
public class EquipamentosEstabelecimentoDomainService {

    public void aplicarDefaults(EquipamentosEstabelecimento entity) {
        if (entity == null) return;
        if (entity.getActive() == null) {
            entity.setActive(true);
        }
        if (entity.getQuantidade() == null) {
            entity.setQuantidade(1);
        }
    }
}
