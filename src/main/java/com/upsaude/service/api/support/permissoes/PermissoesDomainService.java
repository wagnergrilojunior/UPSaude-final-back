package com.upsaude.service.api.support.permissoes;

import com.upsaude.entity.sistema.usuario.Permissoes;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PermissoesDomainService {

    public void validarPodeInativar(Permissoes entity) {
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar permissão já inativa. ID: {}", entity.getId());
            throw new BadRequestException("Permissão já está inativa");
        }
    }
}

