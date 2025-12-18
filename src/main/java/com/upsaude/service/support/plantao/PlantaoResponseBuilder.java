package com.upsaude.service.support.plantao;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import com.upsaude.api.response.profissional.equipe.PlantaoResponse;
import com.upsaude.entity.profissional.equipe.Plantao;
import com.upsaude.mapper.profissional.equipe.PlantaoMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlantaoResponseBuilder {

    private final PlantaoMapper mapper;

    public PlantaoResponse build(Plantao entity) {
        if (entity != null) {
            if (entity.getProfissional() != null) Hibernate.initialize(entity.getProfissional());
            if (entity.getMedico() != null) Hibernate.initialize(entity.getMedico());
            if (entity.getEstabelecimento() != null) Hibernate.initialize(entity.getEstabelecimento());
        }
        return mapper.toResponse(entity);
    }
}
