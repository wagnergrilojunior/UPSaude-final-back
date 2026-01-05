package com.upsaude.service.api.support.templatenotificacao;

import com.upsaude.entity.sistema.notificacao.TemplateNotificacao;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TemplateNotificacaoDomainService {

    public void validarPodeInativar(TemplateNotificacao entity) {
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar template de notificação já inativo. ID: {}", entity.getId());
            throw new BadRequestException("Template de notificação já está inativo");
        }
    }

    public void validarPodeDeletar(TemplateNotificacao entity) {
        log.debug("Validando se template de notificação pode ser deletado. ID: {}", entity.getId());
        // Adicione aqui qualquer lógica de validação antes de um hard delete.
    }
}

