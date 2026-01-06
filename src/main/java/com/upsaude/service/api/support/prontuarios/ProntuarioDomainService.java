package com.upsaude.service.api.support.prontuarios;

import com.upsaude.entity.clinica.prontuario.Prontuario;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProntuarioDomainService {

    public void validarPodeInativar(Prontuario entity) {
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar prontuário já inativo. ID: {}", entity.getId());
            throw new BadRequestException("Prontuário já está inativo");
        }
    }

    public void validarPodeDeletar(Prontuario entity) {

    }
}

