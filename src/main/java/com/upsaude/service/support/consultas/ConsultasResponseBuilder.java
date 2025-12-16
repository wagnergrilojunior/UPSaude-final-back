package com.upsaude.service.support.consultas;

import com.upsaude.api.response.ConsultasResponse;
import com.upsaude.entity.Consultas;
import com.upsaude.mapper.ConsultasMapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsultasResponseBuilder {

    private final ConsultasMapper mapper;

    public ConsultasResponse build(Consultas entity) {
        if (entity != null) {
            Hibernate.initialize(entity.getPaciente());
            Hibernate.initialize(entity.getMedico());
            Hibernate.initialize(entity.getProfissionalSaude());
            Hibernate.initialize(entity.getEspecialidade());
            Hibernate.initialize(entity.getConvenio());
            Hibernate.initialize(entity.getCidPrincipal());
            Hibernate.initialize(entity.getEstabelecimento());
        }
        return mapper.toResponse(entity);
    }
}
