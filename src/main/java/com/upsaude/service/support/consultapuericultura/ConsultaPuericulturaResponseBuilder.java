package com.upsaude.service.support.consultapuericultura;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import com.upsaude.api.response.clinica.atendimento.ConsultaPuericulturaResponse;
import com.upsaude.entity.clinica.atendimento.ConsultaPuericultura;
import com.upsaude.mapper.clinica.atendimento.ConsultaPuericulturaMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConsultaPuericulturaResponseBuilder {

    private final ConsultaPuericulturaMapper mapper;

    public ConsultaPuericulturaResponse build(ConsultaPuericultura entity) {
        if (entity != null) {
            if (entity.getPuericultura() != null) Hibernate.initialize(entity.getPuericultura());
            if (entity.getProfissional() != null) Hibernate.initialize(entity.getProfissional());
            if (entity.getEstabelecimento() != null) Hibernate.initialize(entity.getEstabelecimento());
        }
        return mapper.toResponse(entity);
    }
}
