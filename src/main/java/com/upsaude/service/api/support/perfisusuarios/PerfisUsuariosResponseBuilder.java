package com.upsaude.service.api.support.perfisusuarios;

import com.upsaude.api.response.sistema.usuario.PerfisUsuariosResponse;
import com.upsaude.entity.sistema.usuario.PerfisUsuarios;
import com.upsaude.mapper.sistema.usuario.PerfisUsuariosMapper;
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

