package com.upsaude.service.support.prontuarios;

import com.upsaude.api.response.ProntuariosResponse;
import com.upsaude.entity.Prontuarios;
import com.upsaude.mapper.ProntuariosMapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProntuariosResponseBuilder {

    private final ProntuariosMapper mapper;

    public ProntuariosResponse build(Prontuarios entity) {
        if (entity != null) {
            Hibernate.initialize(entity.getPaciente());
        }
        return mapper.toResponse(entity);
    }
}

