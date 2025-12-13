package com.upsaude.service.support.notificacao;

import com.upsaude.api.response.NotificacaoResponse;
import com.upsaude.entity.Notificacao;
import com.upsaude.mapper.NotificacaoMapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificacaoResponseBuilder {

    private final NotificacaoMapper mapper;

    public NotificacaoResponse build(Notificacao entity) {
        if (entity != null) {
            Hibernate.initialize(entity.getEstabelecimento());
            Hibernate.initialize(entity.getPaciente());
            Hibernate.initialize(entity.getProfissional());
            Hibernate.initialize(entity.getAgendamento());
            Hibernate.initialize(entity.getTemplate());
        }
        return mapper.toResponse(entity);
    }
}

