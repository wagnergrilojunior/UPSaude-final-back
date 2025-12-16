package com.upsaude.service.support.plantao;

import com.upsaude.entity.Plantao;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PlantaoDomainService {

    public void validarPeriodo(Plantao entity) {
        if (entity == null) return;
        if (entity.getDataHoraFim() != null && entity.getDataHoraInicio() != null
            && entity.getDataHoraFim().isBefore(entity.getDataHoraInicio())) {
            throw new BadRequestException("Data/hora fim não pode ser anterior à data/hora início");
        }
        if (entity.getDataHoraFimPrevisto() != null && entity.getDataHoraInicio() != null
            && entity.getDataHoraFimPrevisto().isBefore(entity.getDataHoraInicio())) {
            throw new BadRequestException("Data/hora fim previsto não pode ser anterior à data/hora início");
        }
    }

    public void validarPodeInativar(Plantao entity) {
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar plantão já inativo. ID: {}", entity.getId());
            throw new BadRequestException("Plantão já está inativo");
        }
    }
}

