package com.upsaude.service.support.fabricantesequipamento;

import com.upsaude.api.response.equipamento.FabricantesEquipamentoResponse;
import com.upsaude.entity.equipamento.FabricantesEquipamento;
import com.upsaude.mapper.FabricantesEquipamentoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FabricantesEquipamentoResponseBuilder {

    private final FabricantesEquipamentoMapper mapper;

    public FabricantesEquipamentoResponse build(FabricantesEquipamento entity) {
        return mapper.toResponse(entity);
    }
}

