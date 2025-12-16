package com.upsaude.service.support.prenatal;

import com.upsaude.entity.PreNatal;
import com.upsaude.enums.StatusPreNatalEnum;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PreNatalDomainService {

    public void aplicarDefaultsNaCriacao(PreNatal entity) {
        if (entity.getStatusPreNatal() == null) {
            entity.setStatusPreNatal(StatusPreNatalEnum.EM_ACOMPANHAMENTO);
        }
    }

    public void validarPodeInativar(PreNatal entity) {
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar pré-natal já inativo. ID: {}", entity.getId());
            throw new BadRequestException("Pré-natal já está inativo");
        }
    }
}

