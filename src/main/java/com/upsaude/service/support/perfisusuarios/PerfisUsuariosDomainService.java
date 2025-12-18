package com.upsaude.service.support.perfisusuarios;

import com.upsaude.entity.sistema.PerfisUsuarios;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PerfisUsuariosDomainService {

    public void validarPodeInativar(PerfisUsuarios entity) {
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar perfil de usuário já inativo. ID: {}", entity.getId());
            throw new BadRequestException("Perfil de usuário já está inativo");
        }
    }
}

