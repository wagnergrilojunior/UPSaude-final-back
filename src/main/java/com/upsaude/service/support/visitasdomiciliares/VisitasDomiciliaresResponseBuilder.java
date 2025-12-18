package com.upsaude.service.support.visitasdomiciliares;

import com.upsaude.api.response.visita.VisitasDomiciliaresResponse;
import com.upsaude.entity.visita.VisitasDomiciliares;
import com.upsaude.mapper.VisitasDomiciliaresMapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

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

