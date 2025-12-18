package com.upsaude.service.support.perfisusuarios;

import com.upsaude.api.response.sistema.PerfisUsuariosResponse;
import com.upsaude.entity.sistema.PerfisUsuarios;
import com.upsaude.mapper.PerfisUsuariosMapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PerfisUsuariosResponseBuilder {

    private final PerfisUsuariosMapper mapper;

    public PerfisUsuariosResponse build(PerfisUsuarios entity) {
        if (entity != null && entity.getEstabelecimento() != null) {
            Hibernate.initialize(entity.getEstabelecimento());
        }
        return mapper.toResponse(entity);
    }
}

