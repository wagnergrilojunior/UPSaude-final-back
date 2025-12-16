package com.upsaude.service.support.cuidadosenfermagem;

import com.upsaude.api.response.CuidadosEnfermagemResponse;
import com.upsaude.entity.CuidadosEnfermagem;
import com.upsaude.mapper.CuidadosEnfermagemMapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CuidadosEnfermagemResponseBuilder {

    private final CuidadosEnfermagemMapper mapper;

    public CuidadosEnfermagemResponse build(CuidadosEnfermagem entity) {
        if (entity != null) {
            Hibernate.initialize(entity.getPaciente());
            Hibernate.initialize(entity.getProfissional());
            Hibernate.initialize(entity.getAtendimento());
            Hibernate.initialize(entity.getEstabelecimento());
        }
        return mapper.toResponse(entity);
    }
}
