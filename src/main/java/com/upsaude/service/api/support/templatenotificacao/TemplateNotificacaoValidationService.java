package com.upsaude.service.api.support.templatenotificacao;

import com.upsaude.api.request.sistema.notificacao.TemplateNotificacaoRequest;
import com.upsaude.exception.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class TemplateNotificacaoValidationService {

    public void validarObrigatorios(TemplateNotificacaoRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do template de notificação são obrigatórios");
        }
        if (!StringUtils.hasText(request.getNome())) {
            throw new BadRequestException("Nome do template é obrigatório");
        }
        if (request.getTipoNotificacao() == null) {
            throw new BadRequestException("Tipo de notificação é obrigatório");
        }
        if (request.getCanal() == null) {
            throw new BadRequestException("Canal de notificação é obrigatório");
        }
        if (!StringUtils.hasText(request.getMensagem())) {
            throw new BadRequestException("Mensagem do template é obrigatória");
        }
    }
}
