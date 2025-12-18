package com.upsaude.service.support.templatenotificacao;

import com.upsaude.api.response.notificacao.TemplateNotificacaoResponse;
import com.upsaude.entity.notificacao.TemplateNotificacao;
import com.upsaude.mapper.TemplateNotificacaoMapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TemplateNotificacaoResponseBuilder {

    private final TemplateNotificacaoMapper mapper;

    public TemplateNotificacaoResponse build(TemplateNotificacao entity) {
        if (entity != null) {
            Hibernate.initialize(entity.getEstabelecimento());
        }
        return mapper.toResponse(entity);
    }
}

