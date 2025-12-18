package com.upsaude.service.support.consultapuericultura;

import com.upsaude.api.response.atendimento.ConsultaPuericulturaResponse;
import com.upsaude.entity.atendimento.ConsultaPuericultura;
import com.upsaude.mapper.ConsultaPuericulturaMapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsultaPuericulturaResponseBuilder {

    private final ConsultaPuericulturaMapper mapper;

    public ConsultaPuericulturaResponse build(ConsultaPuericultura entity) {
        if (entity != null) {
            Hibernate.initialize(entity.getPuericultura());
            Hibernate.initialize(entity.getProfissional());
            Hibernate.initialize(entity.getEstabelecimento());
        }
        return mapper.toResponse(entity);
    }
}

