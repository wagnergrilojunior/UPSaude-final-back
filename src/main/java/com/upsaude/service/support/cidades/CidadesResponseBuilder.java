package com.upsaude.service.support.cidades;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import com.upsaude.api.response.referencia.geografico.CidadesResponse;
import com.upsaude.entity.referencia.geografico.Cidades;
import com.upsaude.mapper.referencia.geografico.CidadesMapper;

import lombok.RequiredArgsConstructor;

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

