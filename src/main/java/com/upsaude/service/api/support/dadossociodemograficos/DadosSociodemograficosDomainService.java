package com.upsaude.service.api.support.dadossociodemograficos;

import com.upsaude.entity.paciente.DadosSociodemograficos;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DadosSociodemograficosDomainService {

    public void validarPodeInativar(DadosSociodemograficos entity) {
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar dados sociodemográficos já inativos. ID: {}", entity.getId());
            throw new BadRequestException("Dados sociodemográficos já estão inativos");
        }
    }
}

