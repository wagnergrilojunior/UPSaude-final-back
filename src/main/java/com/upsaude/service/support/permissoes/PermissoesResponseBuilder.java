package com.upsaude.service.support.permissoes;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import com.upsaude.api.response.sistema.PermissoesResponse;
import com.upsaude.entity.sistema.Permissoes;
import com.upsaude.mapper.sistema.PermissoesMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PermissoesResponseBuilder {

    private final PermissoesMapper mapper;

    public PermissoesResponse build(Permissoes entity) {
        if (entity != null && entity.getEstabelecimento() != null) {
            Hibernate.initialize(entity.getEstabelecimento());
        }
        return mapper.toResponse(entity);
    }
}

