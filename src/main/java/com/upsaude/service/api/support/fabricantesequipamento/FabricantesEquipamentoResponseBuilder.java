package com.upsaude.service.api.support.fabricantesequipamento;

import org.springframework.stereotype.Service;

import com.upsaude.api.response.estabelecimento.equipamento.FabricantesEquipamentoResponse;
import com.upsaude.entity.estabelecimento.equipamento.FabricantesEquipamento;
import com.upsaude.mapper.estabelecimento.equipamento.FabricantesEquipamentoMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FabricantesEquipamentoResponseBuilder {

    private final FabricantesEquipamentoMapper mapper;

    public FabricantesEquipamentoResponse build(FabricantesEquipamento entity) {
        return mapper.toResponse(entity);
    }
}
