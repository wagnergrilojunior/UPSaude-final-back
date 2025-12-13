package com.upsaude.service.support.atividadeprofissional;

import com.upsaude.entity.AtividadeProfissional;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AtividadeProfissionalDomainService {

    public void validarPodeInativar(AtividadeProfissional entity) {
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar atividade profissional já inativa. ID: {}", entity.getId());
            throw new BadRequestException("Atividade profissional já está inativa");
        }
    }
}

