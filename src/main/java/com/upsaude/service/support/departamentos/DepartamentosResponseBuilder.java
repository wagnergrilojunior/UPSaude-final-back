package com.upsaude.service.support.departamentos;

import com.upsaude.api.response.departamento.DepartamentosResponse;
import com.upsaude.entity.departamento.Departamentos;
import com.upsaude.mapper.DepartamentosMapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

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

