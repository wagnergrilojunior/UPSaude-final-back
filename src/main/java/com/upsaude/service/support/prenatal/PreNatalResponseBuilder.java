package com.upsaude.service.support.prenatal;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import com.upsaude.api.response.saude_publica.planejamento.PreNatalResponse;
import com.upsaude.entity.saude_publica.planejamento.PreNatal;
import com.upsaude.mapper.saude_publica.planejamento.PreNatalMapper;

import lombok.RequiredArgsConstructor;

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

