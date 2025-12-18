package com.upsaude.service.support.dadosclinicosbasicos;

import com.upsaude.entity.paciente.DadosClinicosBasicos;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DadosClinicosBasicosDomainService {

    public void validarPodeInativar(DadosClinicosBasicos entity) {
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar dados clínicos básicos já inativos. ID: {}", entity.getId());
            throw new BadRequestException("Dados clínicos básicos já estão inativos");
        }
    }
}

