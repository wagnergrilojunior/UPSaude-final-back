package com.upsaude.service.api.support.responsavellegal;

import com.upsaude.entity.paciente.ResponsavelLegal;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ResponsavelLegalDomainService {

    public void validarPodeInativar(ResponsavelLegal entity) {
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar responsável legal já inativo. ID: {}", entity.getId());
            throw new BadRequestException("Responsável legal já está inativo");
        }
    }

    public void validarPodeDeletar(ResponsavelLegal entity) {

    }
}
