package com.upsaude.service.support.notificacao;

import com.upsaude.entity.Notificacao;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificacaoDomainService {

    public void validarPodeInativar(Notificacao entity) {
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar notificação já inativa. ID: {}", entity.getId());
            throw new BadRequestException("Notificação já está inativa");
        }
    }
}

