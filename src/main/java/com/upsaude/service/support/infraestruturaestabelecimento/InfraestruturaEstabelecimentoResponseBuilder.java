package com.upsaude.service.support.infraestruturaestabelecimento;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import com.upsaude.api.response.estabelecimento.InfraestruturaEstabelecimentoResponse;
import com.upsaude.entity.estabelecimento.InfraestruturaEstabelecimento;
import com.upsaude.mapper.estabelecimento.InfraestruturaEstabelecimentoMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InfraestruturaEstabelecimentoResponseBuilder {

    private final InfraestruturaEstabelecimentoMapper mapper;

    public InfraestruturaEstabelecimentoResponse build(InfraestruturaEstabelecimento entity) {
        if (entity != null) {
            if (entity.getEstabelecimento() != null) Hibernate.initialize(entity.getEstabelecimento());
        }
        return mapper.toResponse(entity);
    }
}
