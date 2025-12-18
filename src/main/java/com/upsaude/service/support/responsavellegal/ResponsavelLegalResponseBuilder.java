package com.upsaude.service.support.responsavellegal;

import com.upsaude.api.response.paciente.ResponsavelLegalResponse;
import com.upsaude.entity.paciente.ResponsavelLegal;
import com.upsaude.mapper.ResponsavelLegalMapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

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

