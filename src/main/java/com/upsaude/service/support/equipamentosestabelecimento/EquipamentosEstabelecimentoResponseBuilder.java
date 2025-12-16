package com.upsaude.service.support.equipamentosestabelecimento;

import com.upsaude.api.response.EquipamentosEstabelecimentoResponse;
import com.upsaude.entity.EquipamentosEstabelecimento;
import com.upsaude.mapper.EquipamentosEstabelecimentoMapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EquipamentosEstabelecimentoResponseBuilder {

    private final EquipamentosEstabelecimentoMapper mapper;

    public EquipamentosEstabelecimentoResponse build(EquipamentosEstabelecimento entity) {
        if (entity != null) {
            Hibernate.initialize(entity.getEstabelecimento());
            Hibernate.initialize(entity.getEquipamento());
            if (entity.getEquipamento() != null) {
                Hibernate.initialize(entity.getEquipamento().getFabricante());
            }
        }
        return mapper.toResponse(entity);
    }
}
