package com.upsaude.service.support.atividadeprofissional;

import com.upsaude.api.response.AtividadeProfissionalResponse;
import com.upsaude.entity.AtividadeProfissional;
import com.upsaude.mapper.AtividadeProfissionalMapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AtividadeProfissionalResponseBuilder {

    private final AtividadeProfissionalMapper mapper;

    public AtividadeProfissionalResponse build(AtividadeProfissional entity) {
        if (entity != null) {
            if (entity.getProfissional() != null) Hibernate.initialize(entity.getProfissional());
            if (entity.getMedico() != null) Hibernate.initialize(entity.getMedico());
            if (entity.getPaciente() != null) Hibernate.initialize(entity.getPaciente());
            if (entity.getAtendimento() != null) Hibernate.initialize(entity.getAtendimento());
            if (entity.getCirurgia() != null) Hibernate.initialize(entity.getCirurgia());
            if (entity.getEstabelecimento() != null) Hibernate.initialize(entity.getEstabelecimento());
        }
        return mapper.toResponse(entity);
    }
}

