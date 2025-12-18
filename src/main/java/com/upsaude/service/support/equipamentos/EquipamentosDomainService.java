package com.upsaude.service.support.equipamentos;

import com.upsaude.entity.equipamento.Equipamentos;
import org.springframework.stereotype.Service;

@Service
public class EquipamentosDomainService {

    public void aplicarDefaults(Equipamentos entity) {
        if (entity == null) return;
        if (entity.getActive() == null) {
            entity.setActive(true);
        }
        if (entity.getRequerCalibracao() == null) {
            entity.setRequerCalibracao(false);
        }
        if (entity.getRequerManutencaoPreventiva() == null) {
            entity.setRequerManutencaoPreventiva(false);
        }
        if (entity.getDisponivelUso() == null) {
            entity.setDisponivelUso(true);
        }
    }
}
