package com.upsaude.service.api.support.notificacao;

import com.upsaude.api.request.sistema.notificacao.NotificacaoRequest;
import com.upsaude.exception.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class NotificacaoValidationService {

    public void validarObrigatorios(NotificacaoRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados da notificação são obrigatórios");
        }
        if (request.getTipoNotificacao() == null) {
            throw new BadRequestException("Tipo de notificação é obrigatório");
        }
        if (request.getCanal() == null) {
            throw new BadRequestException("Canal de notificação é obrigatório");
        }
        if (!StringUtils.hasText(request.getMensagem())) {
            throw new BadRequestException("Mensagem é obrigatória");
        }
    }
}
