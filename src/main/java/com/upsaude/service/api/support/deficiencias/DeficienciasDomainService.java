package com.upsaude.service.api.support.deficiencias;

import com.upsaude.entity.paciente.deficiencia.Deficiencias;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DeficienciasDomainService {

    public void validarPodeInativar(Deficiencias entity) {
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar deficiência já inativa. ID: {}", entity.getId());
            throw new BadRequestException("Deficiência já está inativa");
        }
    }

    public void validarPodeDeletar(Deficiencias entity) {

    }
}
