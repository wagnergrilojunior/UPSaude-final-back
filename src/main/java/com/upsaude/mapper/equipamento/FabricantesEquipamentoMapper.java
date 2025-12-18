package com.upsaude.mapper.equipamento;

import com.upsaude.api.request.equipamento.FabricantesEquipamentoRequest;
import com.upsaude.api.response.equipamento.FabricantesEquipamentoResponse;
import com.upsaude.dto.FabricantesEquipamentoDTO;
import com.upsaude.entity.equipamento.FabricantesEquipamento;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface FabricantesEquipamentoMapper extends EntityMapper<FabricantesEquipamento, FabricantesEquipamentoDTO> {

    @Mapping(target = "active", ignore = true)
    FabricantesEquipamento toEntity(FabricantesEquipamentoDTO dto);

    FabricantesEquipamentoDTO toDTO(FabricantesEquipamento entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    FabricantesEquipamento fromRequest(FabricantesEquipamentoRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateFromRequest(FabricantesEquipamentoRequest request, @MappingTarget FabricantesEquipamento entity);

    FabricantesEquipamentoResponse toResponse(FabricantesEquipamento entity);
}
