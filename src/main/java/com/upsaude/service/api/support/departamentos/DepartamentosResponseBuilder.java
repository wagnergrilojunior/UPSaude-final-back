package com.upsaude.service.api.support.departamentos;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import com.upsaude.api.response.estabelecimento.departamento.DepartamentosResponse;
import com.upsaude.entity.estabelecimento.departamento.Departamentos;
import com.upsaude.mapper.estabelecimento.departamento.DepartamentosMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartamentosResponseBuilder {

    private final DepartamentosMapper mapper;

    public DepartamentosResponse build(Departamentos entity) {
        if (entity != null && entity.getEstabelecimento() != null) {
            Hibernate.initialize(entity.getEstabelecimento());
        }
        return mapper.toResponse(entity);
    }
}

