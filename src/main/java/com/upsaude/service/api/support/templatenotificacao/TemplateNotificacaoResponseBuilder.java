package com.upsaude.service.api.support.templatenotificacao;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import com.upsaude.api.response.sistema.notificacao.TemplateNotificacaoResponse;
import com.upsaude.entity.sistema.notificacao.TemplateNotificacao;
import com.upsaude.mapper.sistema.notificacao.TemplateNotificacaoMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TemplateNotificacaoResponseBuilder {

    private final TemplateNotificacaoMapper mapper;

    public TemplateNotificacaoResponse build(TemplateNotificacao entity) {
        if (entity != null) {
            if (entity.getEstabelecimento() != null) Hibernate.initialize(entity.getEstabelecimento());
        }
        return mapper.toResponse(entity);
    }
}
