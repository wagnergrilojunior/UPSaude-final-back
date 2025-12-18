package com.upsaude.service.support.equipamentosestabelecimento;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import com.upsaude.api.response.estabelecimento.EquipamentosEstabelecimentoResponse;
import com.upsaude.entity.estabelecimento.EquipamentosEstabelecimento;
import com.upsaude.mapper.estabelecimento.EquipamentosEstabelecimentoMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EquipamentosEstabelecimentoResponseBuilder {

    private final EquipamentosEstabelecimentoMapper mapper;

    public EquipamentosEstabelecimentoResponse build(EquipamentosEstabelecimento entity) {
        if (entity != null) {
            if (entity.getEstabelecimento() != null) Hibernate.initialize(entity.getEstabelecimento());
            if (entity.getEquipamento() != null) Hibernate.initialize(entity.getEquipamento());
        }
        return mapper.toResponse(entity);
    }
}
