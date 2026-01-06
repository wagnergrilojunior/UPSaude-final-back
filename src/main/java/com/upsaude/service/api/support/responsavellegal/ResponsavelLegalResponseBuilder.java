package com.upsaude.service.api.support.responsavellegal;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import com.upsaude.api.response.paciente.ResponsavelLegalResponse;
import com.upsaude.entity.paciente.ResponsavelLegal;
import com.upsaude.mapper.paciente.ResponsavelLegalMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResponsavelLegalResponseBuilder {

    private final ResponsavelLegalMapper mapper;

    public ResponsavelLegalResponse build(ResponsavelLegal entity) {
        if (entity != null) {
            Hibernate.initialize(entity.getEstabelecimento());
            Hibernate.initialize(entity.getPaciente());
        }
        return mapper.toResponse(entity);
    }
}
