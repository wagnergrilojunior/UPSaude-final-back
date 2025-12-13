package com.upsaude.service.support.prenatal;

import com.upsaude.api.response.PreNatalResponse;
import com.upsaude.entity.PreNatal;
import com.upsaude.mapper.PreNatalMapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PreNatalResponseBuilder {

    private final PreNatalMapper mapper;

    public PreNatalResponse build(PreNatal entity) {
        if (entity != null) {
            if (entity.getPaciente() != null) Hibernate.initialize(entity.getPaciente());
            if (entity.getProfissionalResponsavel() != null) Hibernate.initialize(entity.getProfissionalResponsavel());
            if (entity.getEquipeSaude() != null) Hibernate.initialize(entity.getEquipeSaude());
            if (entity.getEstabelecimento() != null) Hibernate.initialize(entity.getEstabelecimento());
        }
        return mapper.toResponse(entity);
    }
}

