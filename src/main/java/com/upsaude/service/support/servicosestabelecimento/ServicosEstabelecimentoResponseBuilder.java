package com.upsaude.service.support.servicosestabelecimento;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import com.upsaude.api.response.estabelecimento.ServicosEstabelecimentoResponse;
import com.upsaude.entity.estabelecimento.ServicosEstabelecimento;
import com.upsaude.mapper.estabelecimento.ServicosEstabelecimentoMapper;

import lombok.RequiredArgsConstructor;

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

