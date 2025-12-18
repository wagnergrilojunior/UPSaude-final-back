package com.upsaude.service.support.templatenotificacao;

import com.upsaude.entity.notificacao.TemplateNotificacao;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TemplateNotificacaoDomainService {

    public void validarPodeInativar(TemplateNotificacao entity) {
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar template já inativo. ID: {}", entity.getId());
            throw new BadRequestException("Template de notificação já está inativo");
        }
    }
}

