package com.upsaude.service.support.equipamentos;

import com.upsaude.api.response.equipamento.EquipamentosResponse;
import com.upsaude.entity.equipamento.Equipamentos;
import com.upsaude.mapper.EquipamentosMapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EquipamentosResponseBuilder {

    private final EquipamentosMapper mapper;

    public EquipamentosResponse build(Equipamentos entity) {
        if (entity != null) {
            Hibernate.initialize(entity.getFabricante());
            Hibernate.initialize(entity.getEstabelecimento());
        }
        return mapper.toResponse(entity);
    }
}
