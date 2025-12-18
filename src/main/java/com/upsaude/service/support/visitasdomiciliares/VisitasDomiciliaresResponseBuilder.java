package com.upsaude.service.support.visitasdomiciliares;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import com.upsaude.api.response.saude_publica.visita.VisitasDomiciliaresResponse;
import com.upsaude.entity.saude_publica.visita.VisitasDomiciliares;
import com.upsaude.mapper.saude_publica.visita.VisitasDomiciliaresMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VisitasDomiciliaresResponseBuilder {

    private final VisitasDomiciliaresMapper mapper;

    public VisitasDomiciliaresResponse build(VisitasDomiciliares entity) {
        if (entity != null) {
            Hibernate.initialize(entity.getEstabelecimento());
            Hibernate.initialize(entity.getPaciente());
            Hibernate.initialize(entity.getProfissional());
            Hibernate.initialize(entity.getEquipeSaude());
        }
        return mapper.toResponse(entity);
    }
}

