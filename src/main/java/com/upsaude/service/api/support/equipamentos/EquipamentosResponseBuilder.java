package com.upsaude.service.api.support.equipamentos;

import org.springframework.stereotype.Service;

import com.upsaude.api.response.estabelecimento.equipamento.EquipamentosResponse;
import com.upsaude.entity.estabelecimento.equipamento.Equipamentos;
import com.upsaude.mapper.estabelecimento.equipamento.EquipamentosMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EquipamentosResponseBuilder {

    private final EquipamentosMapper mapper;

    public EquipamentosResponse build(Equipamentos entity) {
        return mapper.toResponse(entity);
    }
}
