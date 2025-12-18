package com.upsaude.service.support.puericultura;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import com.upsaude.api.response.saude_publica.puericultura.PuericulturaResponse;
import com.upsaude.entity.saude_publica.puericultura.Puericultura;
import com.upsaude.mapper.saude_publica.puericultura.PuericulturaMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PuericulturaResponseBuilder {

    private final PuericulturaMapper mapper;

    public PuericulturaResponse build(Puericultura entity) {
        if (entity != null) {
            if (entity.getPaciente() != null) Hibernate.initialize(entity.getPaciente());
            if (entity.getProfissionalResponsavel() != null) Hibernate.initialize(entity.getProfissionalResponsavel());
            if (entity.getEquipeSaude() != null) Hibernate.initialize(entity.getEquipeSaude());
            if (entity.getEstabelecimento() != null) Hibernate.initialize(entity.getEstabelecimento());
        }
        return mapper.toResponse(entity);
    }
}
