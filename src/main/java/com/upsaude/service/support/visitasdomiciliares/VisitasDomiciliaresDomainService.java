package com.upsaude.service.support.visitasdomiciliares;

import com.upsaude.entity.visita.VisitasDomiciliares;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class VisitasDomiciliaresDomainService {

    public void validarPodeInativar(VisitasDomiciliares entity) {
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar visita domiciliar já inativa. ID: {}", entity.getId());
            throw new BadRequestException("Visita domiciliar já está inativa");
        }
    }
}

