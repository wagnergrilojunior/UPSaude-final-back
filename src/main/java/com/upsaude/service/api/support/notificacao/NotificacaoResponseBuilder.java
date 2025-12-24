package com.upsaude.service.api.support.notificacao;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import com.upsaude.api.response.sistema.notificacao.NotificacaoResponse;
import com.upsaude.entity.sistema.notificacao.Notificacao;
import com.upsaude.mapper.sistema.notificacao.NotificacaoMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificacaoResponseBuilder {

    private final NotificacaoMapper mapper;

    public NotificacaoResponse build(Notificacao entity) {
        if (entity != null) {
            if (entity.getPaciente() != null) Hibernate.initialize(entity.getPaciente());
            if (entity.getProfissional() != null) Hibernate.initialize(entity.getProfissional());
            if (entity.getAgendamento() != null) Hibernate.initialize(entity.getAgendamento());
            if (entity.getTemplate() != null) Hibernate.initialize(entity.getTemplate());
            if (entity.getEstabelecimento() != null) Hibernate.initialize(entity.getEstabelecimento());
        }
        return mapper.toResponse(entity);
    }
}
