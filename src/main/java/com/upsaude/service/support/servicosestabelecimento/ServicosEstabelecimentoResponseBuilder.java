package com.upsaude.service.support.servicosestabelecimento;

import com.upsaude.api.response.ServicosEstabelecimentoResponse;
import com.upsaude.entity.ServicosEstabelecimento;
import com.upsaude.mapper.ServicosEstabelecimentoMapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServicosEstabelecimentoResponseBuilder {

    private final ServicosEstabelecimentoMapper mapper;

    public ServicosEstabelecimentoResponse build(ServicosEstabelecimento entity) {
        if (entity != null) {
            Hibernate.initialize(entity.getEstabelecimento());
        }
        return mapper.toResponse(entity);
    }
}

