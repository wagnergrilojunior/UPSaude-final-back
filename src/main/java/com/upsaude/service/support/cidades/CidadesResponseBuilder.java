package com.upsaude.service.support.cidades;

import com.upsaude.api.response.geografico.CidadesResponse;
import com.upsaude.entity.geografico.Cidades;
import com.upsaude.mapper.CidadesMapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CidadesResponseBuilder {

    private final CidadesMapper mapper;

    public CidadesResponse build(Cidades entity) {
        if (entity != null && entity.getEstado() != null) {
            Hibernate.initialize(entity.getEstado());
        }
        return mapper.toResponse(entity);
    }
}

