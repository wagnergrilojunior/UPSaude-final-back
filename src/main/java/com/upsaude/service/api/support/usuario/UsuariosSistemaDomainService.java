package com.upsaude.service.api.support.usuario;

import com.upsaude.entity.sistema.usuario.UsuariosSistema;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UsuariosSistemaDomainService {

    public void validarPodeInativar(UsuariosSistema entity) {
        if (Boolean.FALSE.equals(entity.getAtivo())) {
            log.warn("Tentativa de inativar usuário já inativo. ID: {}", entity.getId());
            throw new BadRequestException("Usuário já está inativo");
        }
    }

    public void validarPodeDeletar(UsuariosSistema entity) {
        
    }
}
