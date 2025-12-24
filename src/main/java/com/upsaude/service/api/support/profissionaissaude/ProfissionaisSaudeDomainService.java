package com.upsaude.service.api.support.profissionaissaude;

import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProfissionaisSaudeDomainService {

    public void validarPodeInativar(ProfissionaisSaude profissional) {
        if (Boolean.FALSE.equals(profissional.getActive())) {
            log.warn("Tentativa de inativar profissional de saúde já inativo. ID: {}", profissional.getId());
            throw new BadRequestException("Profissional de saúde já está inativo");
        }
    }

    public void validarPodeDeletar(ProfissionaisSaude profissional) {
        log.debug("Validando se profissional de saúde pode ser deletado. ID: {}", profissional.getId());
    }
}
