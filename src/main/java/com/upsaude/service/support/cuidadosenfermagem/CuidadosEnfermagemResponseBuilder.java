package com.upsaude.service.support.cuidadosenfermagem;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import com.upsaude.api.response.enfermagem.CuidadosEnfermagemResponse;
import com.upsaude.entity.enfermagem.CuidadosEnfermagem;
import com.upsaude.mapper.enfermagem.CuidadosEnfermagemMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CuidadosEnfermagemResponseBuilder {

    private final CuidadosEnfermagemMapper mapper;

    public CuidadosEnfermagemResponse build(CuidadosEnfermagem entity) {
        if (entity != null) {
            if (entity.getPaciente() != null) Hibernate.initialize(entity.getPaciente());
            if (entity.getProfissional() != null) Hibernate.initialize(entity.getProfissional());
            if (entity.getAtendimento() != null) Hibernate.initialize(entity.getAtendimento());
        }
        return mapper.toResponse(entity);
    }
}
