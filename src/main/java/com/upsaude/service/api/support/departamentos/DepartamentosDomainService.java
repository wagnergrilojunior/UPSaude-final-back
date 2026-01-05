package com.upsaude.service.api.support.departamentos;

import org.springframework.stereotype.Service;

import com.upsaude.entity.estabelecimento.departamento.Departamentos;
import com.upsaude.exception.BadRequestException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DepartamentosDomainService {

    public void validarPodeInativar(Departamentos entity) {
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar departamento já inativo. ID: {}", entity.getId());
            throw new BadRequestException("Departamento já está inativo");
        }
    }

    public void validarPodeDeletar(Departamentos entity) {
        // Validações adicionais podem ser adicionadas aqui se necessário
        // Por exemplo, verificar se há registros relacionados que impedem a exclusão
    }
}

