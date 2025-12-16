package com.upsaude.service.support.templatenotificacao;

import com.upsaude.api.request.TemplateNotificacaoRequest;
import com.upsaude.exception.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class TemplateNotificacaoValidationService {

    public void validarObrigatorios(TemplateNotificacaoRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do template de notificação são obrigatórios");
        }
        if (request.getNome() == null || request.getNome().isBlank()) {
            throw new BadRequestException("Nome do template é obrigatório");
        }
        if (request.getTipoNotificacao() == null) {
            throw new BadRequestException("Tipo de notificação é obrigatório");
        }
        if (request.getCanal() == null) {
            throw new BadRequestException("Canal é obrigatório");
        }
        if (request.getMensagem() == null || request.getMensagem().isBlank()) {
            throw new BadRequestException("Mensagem do template é obrigatória");
        }
    }
}

